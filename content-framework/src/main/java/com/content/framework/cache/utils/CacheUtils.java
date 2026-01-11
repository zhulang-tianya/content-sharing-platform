package com.content.framework.cache.utils;

import com.content.framework.cache.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CacheUtils {

    @Autowired
    private RedisCacheService redisCacheService;

    public static final String CACHE_PREFIX = "content:";
    
    public static final String USER_CACHE_PREFIX = CACHE_PREFIX + "user:";
    
    public static final String ARTICLE_CACHE_PREFIX = CACHE_PREFIX + "article:";
    
    public static final String CATEGORY_CACHE_PREFIX = CACHE_PREFIX + "category:";
    
    public static final String TAG_CACHE_PREFIX = CACHE_PREFIX + "tag:";
    
    public static final String COMMENT_CACHE_PREFIX = CACHE_PREFIX + "comment:";
    
    public static final String DICT_CACHE_PREFIX = CACHE_PREFIX + "dict:";
    
    public static final String CONFIG_CACHE_PREFIX = CACHE_PREFIX + "config:";
    
    public static final String MENU_CACHE_PREFIX = CACHE_PREFIX + "menu:";
    
    public static final String ROLE_CACHE_PREFIX = CACHE_PREFIX + "role:";
    
    public static final String PERMISSION_CACHE_PREFIX = CACHE_PREFIX + "permission:";
    
    public static final String DEPT_CACHE_PREFIX = CACHE_PREFIX + "dept:";
    
    public static final String POST_CACHE_PREFIX = CACHE_PREFIX + "post:";
    
    public static final String LOG_CACHE_PREFIX = CACHE_PREFIX + "log:";
    
    public static final String STAT_CACHE_PREFIX = CACHE_PREFIX + "stat:";
    
    public static final String HOT_CACHE_PREFIX = CACHE_PREFIX + "hot:";
    
    public static final String RECOMMEND_CACHE_PREFIX = CACHE_PREFIX + "recommend:";
    
    public static final String SEARCH_CACHE_PREFIX = CACHE_PREFIX + "search:";
    
    public static final String CAPTCHA_CACHE_PREFIX = CACHE_PREFIX + "captcha:";
    
    public static final String VERIFY_CODE_CACHE_PREFIX = CACHE_PREFIX + "verify_code:";
    
    public static final String LOGIN_FAILURE_CACHE_PREFIX = CACHE_PREFIX + "login_failure:";
    
    public static final String RATE_LIMIT_CACHE_PREFIX = CACHE_PREFIX + "rate_limit:";
    
    public static final String BLACKLIST_CACHE_PREFIX = CACHE_PREFIX + "blacklist:";
    
    public static final String WHITELIST_CACHE_PREFIX = CACHE_PREFIX + "whitelist:";
    
    public static final String ONLINE_USER_CACHE_PREFIX = CACHE_PREFIX + "online_user:";
    
    public static final String SESSION_CACHE_PREFIX = CACHE_PREFIX + "session:";
    
    public static final String TOKEN_CACHE_PREFIX = CACHE_PREFIX + "token:";
    
    public static final String REFRESH_TOKEN_CACHE_PREFIX = CACHE_PREFIX + "refresh_token:";
    
    public static final long DEFAULT_CACHE_TIMEOUT = 3600;
    
    public static final long SHORT_CACHE_TIMEOUT = 600;
    
    public static final long LONG_CACHE_TIMEOUT = 86400;
    
    public <T> void set(String key, T value) {
        redisCacheService.set(key, value);
    }
    
    public <T> void set(String key, T value, long timeout) {
        redisCacheService.set(key, value, timeout, TimeUnit.SECONDS);
    }
    
    public <T> T get(String key) {
        return redisCacheService.get(key);
    }
    
    public void remove(String key) {
        redisCacheService.remove(key);
    }
    
    public void removePattern(String pattern) {
        redisCacheService.removePattern(pattern);
    }
    
    public boolean containsKey(String key) {
        return redisCacheService.containsKey(key);
    }
    
    public void clear() {
        redisCacheService.clear();
    }
    
    // 用户缓存
    public <T> void setUserCache(Long userId, String key, T value) {
        redisCacheService.set(USER_CACHE_PREFIX + userId + ":" + key, value, DEFAULT_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getUserCache(Long userId, String key) {
        return redisCacheService.get(USER_CACHE_PREFIX + userId + ":" + key);
    }
    
    public void removeUserCache(Long userId, String key) {
        redisCacheService.remove(USER_CACHE_PREFIX + userId + ":" + key);
    }
    
    public void removeUserCache(Long userId) {
        redisCacheService.removePattern(USER_CACHE_PREFIX + userId + ":*");
    }
    
    // 文章缓存
    public <T> void setArticleCache(Long articleId, T value) {
        redisCacheService.set(ARTICLE_CACHE_PREFIX + articleId, value, DEFAULT_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getArticleCache(Long articleId) {
        return redisCacheService.get(ARTICLE_CACHE_PREFIX + articleId);
    }
    
    public void removeArticleCache(Long articleId) {
        redisCacheService.remove(ARTICLE_CACHE_PREFIX + articleId);
    }
    
    public void removeArticleCache() {
        redisCacheService.removePattern(ARTICLE_CACHE_PREFIX + "*");
    }
    
    // 分类缓存
    public <T> void setCategoryCache(Long categoryId, T value) {
        redisCacheService.set(CATEGORY_CACHE_PREFIX + categoryId, value, LONG_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getCategoryCache(Long categoryId) {
        return redisCacheService.get(CATEGORY_CACHE_PREFIX + categoryId);
    }
    
    public void removeCategoryCache(Long categoryId) {
        redisCacheService.remove(CATEGORY_CACHE_PREFIX + categoryId);
    }
    
    public void removeCategoryCache() {
        redisCacheService.removePattern(CATEGORY_CACHE_PREFIX + "*");
    }
    
    // 标签缓存
    public <T> void setTagCache(Long tagId, T value) {
        redisCacheService.set(TAG_CACHE_PREFIX + tagId, value, LONG_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getTagCache(Long tagId) {
        return redisCacheService.get(TAG_CACHE_PREFIX + tagId);
    }
    
    public void removeTagCache(Long tagId) {
        redisCacheService.remove(TAG_CACHE_PREFIX + tagId);
    }
    
    public void removeTagCache() {
        redisCacheService.removePattern(TAG_CACHE_PREFIX + "*");
    }
    
    // 评论缓存
    public <T> void setCommentCache(Long commentId, T value) {
        redisCacheService.set(COMMENT_CACHE_PREFIX + commentId, value, DEFAULT_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getCommentCache(Long commentId) {
        return redisCacheService.get(COMMENT_CACHE_PREFIX + commentId);
    }
    
    public void removeCommentCache(Long commentId) {
        redisCacheService.remove(COMMENT_CACHE_PREFIX + commentId);
    }
    
    public void removeCommentCache() {
        redisCacheService.removePattern(COMMENT_CACHE_PREFIX + "*");
    }
    
    // 字典缓存
    public <T> void setDictCache(String dictType, T value) {
        redisCacheService.set(DICT_CACHE_PREFIX + dictType, value, LONG_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getDictCache(String dictType) {
        return redisCacheService.get(DICT_CACHE_PREFIX + dictType);
    }
    
    public void removeDictCache(String dictType) {
        redisCacheService.remove(DICT_CACHE_PREFIX + dictType);
    }
    
    public void removeDictCache() {
        redisCacheService.removePattern(DICT_CACHE_PREFIX + "*");
    }
    
    // 配置缓存
    public <T> void setConfigCache(String configKey, T value) {
        redisCacheService.set(CONFIG_CACHE_PREFIX + configKey, value, LONG_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getConfigCache(String configKey) {
        return redisCacheService.get(CONFIG_CACHE_PREFIX + configKey);
    }
    
    public void removeConfigCache(String configKey) {
        redisCacheService.remove(CONFIG_CACHE_PREFIX + configKey);
    }
    
    public void removeConfigCache() {
        redisCacheService.removePattern(CONFIG_CACHE_PREFIX + "*");
    }
    
    // 菜单缓存
    public <T> void setMenuCache(Long menuId, T value) {
        redisCacheService.set(MENU_CACHE_PREFIX + menuId, value, LONG_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getMenuCache(Long menuId) {
        return redisCacheService.get(MENU_CACHE_PREFIX + menuId);
    }
    
    public void removeMenuCache(Long menuId) {
        redisCacheService.remove(MENU_CACHE_PREFIX + menuId);
    }
    
    public void removeMenuCache() {
        redisCacheService.removePattern(MENU_CACHE_PREFIX + "*");
    }
    
    // 角色缓存
    public <T> void setRoleCache(Long roleId, T value) {
        redisCacheService.set(ROLE_CACHE_PREFIX + roleId, value, LONG_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getRoleCache(Long roleId) {
        return redisCacheService.get(ROLE_CACHE_PREFIX + roleId);
    }
    
    public void removeRoleCache(Long roleId) {
        redisCacheService.remove(ROLE_CACHE_PREFIX + roleId);
    }
    
    public void removeRoleCache() {
        redisCacheService.removePattern(ROLE_CACHE_PREFIX + "*");
    }
    
    // 权限缓存
    public <T> void setPermissionCache(String permission, T value) {
        redisCacheService.set(PERMISSION_CACHE_PREFIX + permission, value, LONG_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getPermissionCache(String permission) {
        return redisCacheService.get(PERMISSION_CACHE_PREFIX + permission);
    }
    
    public void removePermissionCache(String permission) {
        redisCacheService.remove(PERMISSION_CACHE_PREFIX + permission);
    }
    
    public void removePermissionCache() {
        redisCacheService.removePattern(PERMISSION_CACHE_PREFIX + "*");
    }
    
    // 部门缓存
    public <T> void setDeptCache(Long deptId, T value) {
        redisCacheService.set(DEPT_CACHE_PREFIX + deptId, value, LONG_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getDeptCache(Long deptId) {
        return redisCacheService.get(DEPT_CACHE_PREFIX + deptId);
    }
    
    public void removeDeptCache(Long deptId) {
        redisCacheService.remove(DEPT_CACHE_PREFIX + deptId);
    }
    
    public void removeDeptCache() {
        redisCacheService.removePattern(DEPT_CACHE_PREFIX + "*");
    }
    
    // 岗位缓存
    public <T> void setPostCache(Long postId, T value) {
        redisCacheService.set(POST_CACHE_PREFIX + postId, value, LONG_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getPostCache(Long postId) {
        return redisCacheService.get(POST_CACHE_PREFIX + postId);
    }
    
    public void removePostCache(Long postId) {
        redisCacheService.remove(POST_CACHE_PREFIX + postId);
    }
    
    public void removePostCache() {
        redisCacheService.removePattern(POST_CACHE_PREFIX + "*");
    }
    
    // 热点缓存
    public <T> void setHotCache(String key, T value) {
        redisCacheService.set(HOT_CACHE_PREFIX + key, value, SHORT_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getHotCache(String key) {
        return redisCacheService.get(HOT_CACHE_PREFIX + key);
    }
    
    public void removeHotCache(String key) {
        redisCacheService.remove(HOT_CACHE_PREFIX + key);
    }
    
    public void removeHotCache() {
        redisCacheService.removePattern(HOT_CACHE_PREFIX + "*");
    }
    
    // 推荐缓存
    public <T> void setRecommendCache(String key, T value) {
        redisCacheService.set(RECOMMEND_CACHE_PREFIX + key, value, DEFAULT_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getRecommendCache(String key) {
        return redisCacheService.get(RECOMMEND_CACHE_PREFIX + key);
    }
    
    public void removeRecommendCache(String key) {
        redisCacheService.remove(RECOMMEND_CACHE_PREFIX + key);
    }
    
    public void removeRecommendCache() {
        redisCacheService.removePattern(RECOMMEND_CACHE_PREFIX + "*");
    }
    
    // 搜索缓存
    public <T> void setSearchCache(String key, T value) {
        redisCacheService.set(SEARCH_CACHE_PREFIX + key, value, DEFAULT_CACHE_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public <T> T getSearchCache(String key) {
        return redisCacheService.get(SEARCH_CACHE_PREFIX + key);
    }
    
    public void removeSearchCache(String key) {
        redisCacheService.remove(SEARCH_CACHE_PREFIX + key);
    }
    
    public void removeSearchCache() {
        redisCacheService.removePattern(SEARCH_CACHE_PREFIX + "*");
    }
    
    // 验证码缓存
    public void setCaptchaCache(String uuid, String code) {
        redisCacheService.set(CAPTCHA_CACHE_PREFIX + uuid, code, 300, TimeUnit.SECONDS);
    }
    
    public String getCaptchaCache(String uuid) {
        return redisCacheService.get(CAPTCHA_CACHE_PREFIX + uuid);
    }
    
    public void removeCaptchaCache(String uuid) {
        redisCacheService.remove(CAPTCHA_CACHE_PREFIX + uuid);
    }
    
    // 验证码缓存
    public void setVerifyCodeCache(String key, String code) {
        redisCacheService.set(VERIFY_CODE_CACHE_PREFIX + key, code, 300, TimeUnit.SECONDS);
    }
    
    public String getVerifyCodeCache(String key) {
        return redisCacheService.get(VERIFY_CODE_CACHE_PREFIX + key);
    }
    
    public void removeVerifyCodeCache(String key) {
        redisCacheService.remove(VERIFY_CODE_CACHE_PREFIX + key);
    }
    
    // 登录失败缓存
    public void setLoginFailureCache(String username, Integer count) {
        redisCacheService.set(LOGIN_FAILURE_CACHE_PREFIX + username, count, 3600, TimeUnit.SECONDS);
    }
    
    public Integer getLoginFailureCache(String username) {
        return redisCacheService.get(LOGIN_FAILURE_CACHE_PREFIX + username);
    }
    
    public void removeLoginFailureCache(String username) {
        redisCacheService.remove(LOGIN_FAILURE_CACHE_PREFIX + username);
    }
    
    // 限流缓存
    public boolean tryAcquire(String key, long limit, long timeout) {
        Long count = redisCacheService.increment(key);
        if (count == 1) {
            redisCacheService.expire(key, timeout, TimeUnit.SECONDS);
        }
        return count <= limit;
    }
    
    // 令牌缓存
    public void setTokenCache(String token, Object value) {
        redisCacheService.set(TOKEN_CACHE_PREFIX + token, value, 7200, TimeUnit.SECONDS);
    }
    
    public Object getTokenCache(String token) {
        return redisCacheService.get(TOKEN_CACHE_PREFIX + token);
    }
    
    public void removeTokenCache(String token) {
        redisCacheService.remove(TOKEN_CACHE_PREFIX + token);
    }
    
    // 刷新令牌缓存
    public void setRefreshTokenCache(String refreshToken, Object value) {
        redisCacheService.set(REFRESH_TOKEN_CACHE_PREFIX + refreshToken, value, 14400, TimeUnit.SECONDS);
    }
    
    public Object getRefreshTokenCache(String refreshToken) {
        return redisCacheService.get(REFRESH_TOKEN_CACHE_PREFIX + refreshToken);
    }
    
    public void removeRefreshTokenCache(String refreshToken) {
        redisCacheService.remove(REFRESH_TOKEN_CACHE_PREFIX + refreshToken);
    }
    
    // 在线用户缓存
    public void setOnlineUserCache(String sessionId, Object value) {
        redisCacheService.set(ONLINE_USER_CACHE_PREFIX + sessionId, value, 1800, TimeUnit.SECONDS);
    }
    
    public Object getOnlineUserCache(String sessionId) {
        return redisCacheService.get(ONLINE_USER_CACHE_PREFIX + sessionId);
    }
    
    public void removeOnlineUserCache(String sessionId) {
        redisCacheService.remove(ONLINE_USER_CACHE_PREFIX + sessionId);
    }
    
    public void removeOnlineUserCache() {
        redisCacheService.removePattern(ONLINE_USER_CACHE_PREFIX + "*");
    }
}
