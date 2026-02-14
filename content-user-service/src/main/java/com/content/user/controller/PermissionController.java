package com.content.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.content.common.result.Result;
import com.content.framework.security.annotation.PreAuthorizePermi;
import com.content.entity.Permission;
import com.content.user.query.PermissionQuery;
import com.content.user.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理控制器
 */
@RestController
@RequestMapping("/permission")
@Tag(name = "权限管理", description = "权限相关接口")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("/{id}")
    @Operation(summary = "获取权限详情")
    @PreAuthorizePermi(hasPermi = "system:permission:query")
    public Result<Permission> getById(@PathVariable Long id) {
        return Result.success(permissionService.getById(id));
    }

    @GetMapping("/list")
    @Operation(summary = "获取所有启用的权限")
    @PreAuthorizePermi(hasPermi = "system:permission:list")
    public Result<List<Permission>> listAllEnabled() {
        return Result.success(permissionService.listAllEnabled());
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询权限列表")
    @PreAuthorizePermi(hasPermi = "system:permission:list")
    public Result<Page<Permission>> pagePermissions(PermissionQuery query) {
        return Result.success(permissionService.pagePermissions(query));
    }

    @GetMapping("/tree")
    @Operation(summary = "获取权限树")
    @PreAuthorizePermi(hasPermi = "system:permission:list")
    public Result<List<Permission>> treePermissions() {
        return Result.success(permissionService.treePermissions());
    }

    @PostMapping
    @Operation(summary = "新增权限")
    @PreAuthorizePermi(hasPermi = "system:permission:add")
    public Result<Boolean> save(@RequestBody Permission permission) {
        return Result.success(permissionService.save(permission));
    }

    @PutMapping
    @Operation(summary = "更新权限")
    @PreAuthorizePermi(hasPermi = "system:permission:edit")
    public Result<Boolean> update(@RequestBody Permission permission) {
        return Result.success(permissionService.update(permission));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除权限")
    @PreAuthorizePermi(hasPermi = "system:permission:delete")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(permissionService.delete(id));
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除权限")
    @PreAuthorizePermi(hasPermi = "system:permission:delete")
    public Result<Boolean> deleteBatch(@RequestBody List<Long> ids) {
        return Result.success(permissionService.deleteBatch(ids));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "修改权限状态")
    @PreAuthorizePermi(hasPermi = "system:permission:edit")
    public Result<Boolean> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        return Result.success(permissionService.updateStatus(id, status));
    }
}
