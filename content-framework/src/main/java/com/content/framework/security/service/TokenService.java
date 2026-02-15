package com.content.framework.security.service;

import com.content.common.constant.SecurityConstants;
import com.content.framework.security.LoginUser;
import com.content.framework.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Token服务
 * <p>
 * 提供Token管理、在线用户管理、Token黑名单功能
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtUtils jwtUtils;

    /**
     * 登录用户缓存Key前缀
     */
    private static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * Token黑名单Key前缀
     */
    private static final String TOKEN_BLACKLIST_KEY = "token_blacklist:";

    /**
     * 缓存用户登录信息
     *
     * @param loginUser 登录用户
     * @param token     Token
     */
    public void cacheLoginUser(LoginUser loginUser, String token) {
        String cacheKey = getLoginTokenCacheKey(token);
        loginUser.setToken(token);
        redisTemplate.opsForValue().set(cacheKey, loginUser, 
            SecurityConstants.TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        log.debug("缓存登录用户: userId={}, token={}", loginUser.getUserId(), token);
    }

    /**
     * 获取登录用户
     *
     * @param token Token
     * @return 登录用户
     */
    public LoginUser getLoginUser(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        
        // 检查黑名单
        if (isTokenBlacklisted(token)) {
            log.debug("Token已失效: {}", token);
            return null;
        }
        
        String cacheKey = getLoginTokenCacheKey(token);
        Object obj = redisTemplate.opsForValue().get(cacheKey);
        if (obj instanceof LoginUser) {
            return (LoginUser) obj;
        }
        return null;
    }

    /**
     * 刷新Token有效期
     *
     * @param loginUser 登录用户
     */
    public void refreshToken(LoginUser loginUser) {
        if (loginUser == null || loginUser.getToken() == null) {
            return;
        }
        String cacheKey = getLoginTokenCacheKey(loginUser.getToken());
        redisTemplate.expire(cacheKey, SecurityConstants.TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 删除登录用户（登出）
     *
     * @param token Token
     */
    public void deleteLoginUser(String token) {
        if (token == null || token.isEmpty()) {
            return;
        }
        
        // 添加到黑名单
        addToBlacklist(token);
        
        // 删除缓存
        String cacheKey = getLoginTokenCacheKey(token);
        redisTemplate.delete(cacheKey);
        log.info("用户登出，Token已失效: {}", token);
    }

    /**
     * 添加Token到黑名单
     *
     * @param token Token
     */
    public void addToBlacklist(String token) {
        String blacklistKey = getBlacklistKey(token);
        // 计算Token剩余有效期
        long expireTime = jwtUtils.getExpireTime(token);
        long currentTime = System.currentTimeMillis() / 1000;
        long ttl = expireTime - currentTime;
        
        if (ttl > 0) {
            redisTemplate.opsForValue().set(blacklistKey, "1", ttl, TimeUnit.SECONDS);
            log.debug("Token添加到黑名单: {}, TTL={}秒", token, ttl);
        }
    }

    /**
     * 检查Token是否在黑名单中
     *
     * @param token Token
     * @return 是否在黑名单
     */
    public boolean isTokenBlacklisted(String token) {
        String blacklistKey = getBlacklistKey(token);
        return Boolean.TRUE.equals(redisTemplate.hasKey(blacklistKey));
    }

    /**
     * 获取所有在线用户
     *
     * @return 在线用户列表
     */
    @SuppressWarnings("unchecked")
    public Collection<LoginUser> getOnlineUsers() {
        Collection<String> keys = redisTemplate.keys(LOGIN_TOKEN_KEY + "*");
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyList();
        }
        return keys.stream()
            .map(key -> (LoginUser) redisTemplate.opsForValue().get(key))
            .filter(user -> user != null)
            .toList();
    }

    /**
     * 强制用户下线
     *
     * @param token Token
     */
    public void forceLogout(String token) {
        deleteLoginUser(token);
        log.info("强制用户下线: {}", token);
    }

    /**
     * 根据用户ID强制下线
     *
     * @param userId 用户ID
     */
    public void forceLogoutByUserId(Long userId) {
        Collection<String> keys = redisTemplate.keys(LOGIN_TOKEN_KEY + "*");
        if (keys == null || keys.isEmpty()) {
            return;
        }
        
        keys.stream()
            .map(key -> (LoginUser) redisTemplate.opsForValue().get(key))
            .filter(user -> user != null && userId.equals(user.getUserId()))
            .forEach(user -> deleteLoginUser(user.getToken()));
        
        log.info("强制用户下线: userId={}", userId);
    }

    /**
     * 获取登录Token缓存Key
     */
    private String getLoginTokenCacheKey(String token) {
        return LOGIN_TOKEN_KEY + token;
    }

    /**
     * 获取黑名单Key
     */
    private String getBlacklistKey(String token) {
        return TOKEN_BLACKLIST_KEY + token;
    }
}
