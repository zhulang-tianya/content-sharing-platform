package com.content.framework.security.service;

import com.content.common.constant.SecurityConstants;
import com.content.framework.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class PermissionService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean hasPermi(String permission) {
        if (SecurityUtils.isEmpty(permission)) {
            return false;
        }
        
        // 超级管理员拥有所有权限
        if (SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            return true;
        }
        
        // 获取用户权限列表
        Set<String> permissions = getPermissionsFromCache();
        return permissions.contains(permission);
    }

    public boolean hasRole(String role) {
        if (SecurityUtils.isEmpty(role)) {
            return false;
        }
        
        // 超级管理员拥有所有角色
        if (SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            return true;
        }
        
        // 获取用户角色列表
        Set<String> roles = getRolesFromCache();
        return roles.contains(role);
    }

    public boolean hasAnyPermi(String... permissions) {
        if (permissions == null || permissions.length == 0) {
            return false;
        }
        
        // 超级管理员拥有所有权限
        if (SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            return true;
        }
        
        // 获取用户权限列表
        Set<String> userPermissions = getPermissionsFromCache();
        for (String permission : permissions) {
            if (userPermissions.contains(permission)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnyRole(String... roles) {
        if (roles == null || roles.length == 0) {
            return false;
        }
        
        // 超级管理员拥有所有角色
        if (SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            return true;
        }
        
        // 获取用户角色列表
        Set<String> userRoles = getRolesFromCache();
        for (String role : roles) {
            if (userRoles.contains(role)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin() {
        return SecurityUtils.isAdmin(SecurityUtils.getUserId());
    }

    private Set<String> getPermissionsFromCache() {
        Long userId = SecurityUtils.getUserId();
        String cacheKey = SecurityConstants.USER_PERMISSION_CACHE_KEY + userId;
        
        // 从Redis缓存获取权限列表
        Object permissionsObj = redisTemplate.opsForValue().get(cacheKey);
        if (permissionsObj != null) {
            return (Set<String>) permissionsObj;
        }
        
        // 缓存不存在，从数据库加载
        Set<String> permissions = loadPermissionsFromDatabase(userId);
        
        // 存入缓存
        if (permissions != null && !permissions.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, permissions, 3600, TimeUnit.SECONDS);
        }
        
        return permissions;
    }

    private Set<String> getRolesFromCache() {
        Long userId = SecurityUtils.getUserId();
        String cacheKey = SecurityConstants.USER_ROLE_CACHE_KEY + userId;
        
        // 从Redis缓存获取角色列表
        Object rolesObj = redisTemplate.opsForValue().get(cacheKey);
        if (rolesObj != null) {
            return (Set<String>) rolesObj;
        }
        
        // 缓存不存在，从数据库加载
        Set<String> roles = loadRolesFromDatabase(userId);
        
        // 存入缓存
        if (roles != null && !roles.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, roles, 3600, TimeUnit.SECONDS);
        }
        
        return roles;
    }

    private Set<String> loadPermissionsFromDatabase(Long userId) {
        // 模拟从数据库加载权限列表
        Set<String> permissions = new HashSet<>();
        permissions.add("user:list");
        permissions.add("user:view");
        permissions.add("article:list");
        permissions.add("article:view");
        permissions.add("comment:list");
        permissions.add("comment:view");
        return permissions;
    }

    private Set<String> loadRolesFromDatabase(Long userId) {
        // 模拟从数据库加载角色列表
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        if (userId != null && userId == 1L) {
            roles.add("ROLE_ADMIN");
        }
        return roles;
    }

    public void clearCache(Long userId) {
        String permissionCacheKey = SecurityConstants.USER_PERMISSION_CACHE_KEY + userId;
        String roleCacheKey = SecurityConstants.USER_ROLE_CACHE_KEY + userId;
        redisTemplate.delete(permissionCacheKey);
        redisTemplate.delete(roleCacheKey);
    }

    public void clearCache() {
        Long userId = SecurityUtils.getUserId();
        if (userId != null) {
            clearCache(userId);
        }
    }
}
