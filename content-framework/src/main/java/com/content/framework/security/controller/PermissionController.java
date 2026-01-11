package com.content.framework.security.controller;

import com.content.common.result.Result;
import com.content.framework.security.entity.Permission;
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
    public Result<List<com.content.framework.security.entity.Permission>> list() {
        // 获取所有权限列表
        // 注意：这里应该从数据库加载权限列表，暂时返回空列表
        return Result.success(new ArrayList<>());
    }

    @GetMapping("/user")
    public Result<Set<String>> getUserPermissions() {
        // 获取当前用户的权限列表
        // 注意：这里应该从PermissionService获取权限列表，暂时返回空列表
        return Result.success(new HashSet<>());
    }

    @GetMapping("/user/roles")
    public Result<Set<String>> getUserRoles() {
        // 获取当前用户的角色列表
        // 注意：这里应该从PermissionService获取角色列表，暂时返回空列表
        return Result.success(new HashSet<>());
    }

    @PostMapping("/refresh")
    public Result<Void> refreshPermissions() {
        // 刷新当前用户的权限缓存
        permissionService.clearCache();
        return Result.success();
    }

    @GetMapping("/role/{roleId}")
    public Result<List<com.content.framework.security.entity.Permission>> getRolePermissions(@PathVariable Long roleId) {
        // 获取指定角色的权限列表
        return roleService.getRolePermissions(roleId);
    }

    @PostMapping("/role/{roleId}")
    public Result<Void> assignRolePermissions(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        // 为角色分配权限
        return roleService.assignPermissions(roleId, permissionIds);
    }
}
