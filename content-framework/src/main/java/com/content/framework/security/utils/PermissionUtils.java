package com.content.framework.security.utils;

import com.content.common.constant.SecurityConstants;
import com.content.framework.security.SecurityUtils;

import java.util.Set;

public class PermissionUtils {

    public static String getPermissionKey(String module, String action) {
        return module + SecurityConstants.PERMISSION_SEPARATOR + action;
    }

    public static String getRoleKey(String roleName) {
        return SecurityConstants.PERMISSION_PREFIX + roleName.toUpperCase();
    }

    public static boolean containsPermission(Set<String> permissions, String permission) {
        if (permissions == null || permissions.isEmpty()) {
            return false;
        }
        return permissions.contains(permission);
    }

    public static boolean containsRole(Set<String> roles, String role) {
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        return roles.contains(role);
    }

    public static boolean hasPermission(String permission) {
        return SecurityUtils.isAdmin(SecurityUtils.getUserId());
    }

    public static boolean hasRole(String role) {
        return SecurityUtils.isAdmin(SecurityUtils.getUserId());
    }

    public static boolean hasAnyPermission(Set<String> permissions, String... requiredPermissions) {
        if (requiredPermissions == null || requiredPermissions.length == 0) {
            return true;
        }
        for (String permission : requiredPermissions) {
            if (containsPermission(permissions, permission)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAnyRole(Set<String> roles, String... requiredRoles) {
        if (requiredRoles == null || requiredRoles.length == 0) {
            return true;
        }
        for (String role : requiredRoles) {
            if (containsRole(roles, role)) {
                return true;
            }
        }
        return false;
    }
}
