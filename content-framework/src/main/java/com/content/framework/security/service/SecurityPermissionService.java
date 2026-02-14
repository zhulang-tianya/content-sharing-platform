package com.content.framework.security.service;

import com.content.common.constant.SecurityConstants;
import com.content.framework.security.LoginUser;
import com.content.framework.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 权限校验服务（用于安全框架内部权限校验）
 * 
 * 说明：此服务只从 LoginUser 和 Redis 缓存中获取权限信息，
 * 不直接访问数据库。权限信息在用户登录时由 UserDetailsServiceImpl 加载。
 */
@Service("securityPermissionService")
public class SecurityPermissionService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean hasPermi(String permission) {
        if (SecurityUtils.isEmpty(permission)) {
            return false;
        }
        
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null) {
            return false;
        }
        
        if (loginUser.isAdmin()) {
            return true;
        }
        
        return loginUser.hasPermission(permission);
    }

    public boolean hasRole(String role) {
        if (SecurityUtils.isEmpty(role)) {
            return false;
        }
        
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null) {
            return false;
        }
        
        if (loginUser.isAdmin()) {
            return true;
        }
        
        return loginUser.hasRole(role);
    }

    public boolean hasAnyPermi(String... permissions) {
        if (permissions == null || permissions.length == 0) {
            return false;
        }
        
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null) {
            return false;
        }
        
        if (loginUser.isAdmin()) {
            return true;
        }
        
        for (String permission : permissions) {
            if (loginUser.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnyRole(String... roles) {
        if (roles == null || roles.length == 0) {
            return false;
        }
        
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null) {
            return false;
        }
        
        if (loginUser.isAdmin()) {
            return true;
        }
        
        for (String role : roles) {
            if (loginUser.hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        return loginUser != null && loginUser.isAdmin();
    }

    public void clearCache(Long userId) {
        String permissionCacheKey = SecurityConstants.USER_PERMISSION_CACHE_KEY + userId;
        String roleCacheKey = SecurityConstants.USER_ROLE_CACHE_KEY + userId;
        redisTemplate.delete(permissionCacheKey);
        redisTemplate.delete(roleCacheKey);
    }

    public void clearCache() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser != null) {
            clearCache(loginUser.getUserId());
        }
    }
}
