package com.content.framework.security.controller;

import com.content.common.result.Result;
import com.content.entity.Permission;
import com.content.framework.security.service.PermissionService;
import com.content.framework.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/system/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public Result<List<Permission>> list() {
        return Result.success(new ArrayList<>());
    }

    @GetMapping("/user")
    public Result<Set<String>> getUserPermissions() {
        return Result.success(new HashSet<>());
    }

    @GetMapping("/user/roles")
    public Result<Set<String>> getUserRoles() {
        return Result.success(new HashSet<>());
    }

    @PostMapping("/refresh")
    public Result<Void> refreshPermissions() {
        permissionService.clearCache();
        return Result.success();
    }

    @GetMapping("/role/{roleId}")
    public Result<List<Permission>> getRolePermissions(@PathVariable Long roleId) {
        return roleService.getRolePermissions(roleId);
    }

    @PostMapping("/role/{roleId}")
    public Result<Void> assignRolePermissions(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        return roleService.assignPermissions(roleId, permissionIds);
    }
}
