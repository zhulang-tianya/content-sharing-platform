package com.content.framework.security.service;

import com.content.common.constant.SecurityConstants;
import com.content.common.exception.ForbiddenException;
import com.content.common.result.ResultCode;
import com.content.framework.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ss")
public class SecurityService {

    @Autowired
    private SecurityPermissionService securityPermissionService;

    public boolean hasPermi(String permission) {
        boolean hasPermission = securityPermissionService.hasPermi(permission);
        if (!hasPermission) {
            throw new ForbiddenException("Permission denied");
        }
        return true;
    }

    public boolean hasRole(String role) {
        boolean hasRole = securityPermissionService.hasRole(role);
        if (!hasRole) {
            throw new ForbiddenException("Permission denied");
        }
        return true;
    }

    public boolean hasAnyPermi(String... permissions) {
        boolean hasAnyPermission = securityPermissionService.hasAnyPermi(permissions);
        if (!hasAnyPermission) {
            throw new ForbiddenException("Permission denied");
        }
        return true;
    }

    public boolean hasAnyRole(String... roles) {
        boolean hasAnyRole = securityPermissionService.hasAnyRole(roles);
        if (!hasAnyRole) {
            throw new ForbiddenException("Permission denied");
        }
        return true;
    }

    public boolean isAdmin() {
        return SecurityUtils.isAdmin(SecurityUtils.getUserId());
    }
}
