package com.content.framework.security.service;

import com.content.framework.security.LoginUser;
import com.content.framework.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    private static final String TOKEN_PREFIX = "token:";
    private static final long TOKEN_EXPIRE_TIME = 24 * 60 * 60; // 24小时

    public String createToken(LoginUser loginUser) {
        // 生成JWT令牌
        String token = jwtUtils.generateToken(loginUser.getUsername(), loginUser.getUserId());
        // 保存令牌到Redis
        saveToken(token, loginUser);
        return token;
    }

    public LoginUser getLoginUser() {
        // 从SecurityContext获取登录用户
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LoginUser) {
            return (LoginUser) principal;
        }
        return null;
    }

    public void removeToken(String username) {
        // 从Redis中删除用户的令牌
        // 这里简化处理，实际应该根据用户名查询并删除所有相关令牌
        // 或者使用Redis的模糊查询删除
    }

    public void saveToken(String token, Object value) {
        redisTemplate.opsForValue().set(TOKEN_PREFIX + token, value, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    public Object getToken(String token) {
        return redisTemplate.opsForValue().get(TOKEN_PREFIX + token);
    }

    public void deleteToken(String token) {
        redisTemplate.delete(TOKEN_PREFIX + token);
    }

    public boolean existsToken(String token) {
        return redisTemplate.hasKey(TOKEN_PREFIX + token);
    }

    public void refreshToken(String token) {
        redisTemplate.expire(TOKEN_PREFIX + token, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }
}
