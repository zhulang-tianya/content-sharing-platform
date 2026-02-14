package com.content.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.content.common.result.Result;
import com.content.framework.security.annotation.PreAuthorizePermi;
import com.content.user.entity.Role;
import com.content.user.query.RoleQuery;
import com.content.user.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/role")
@Tag(name = "角色管理", description = "角色相关接口")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{id}")
    @Operation(summary = "获取角色详情")
    @PreAuthorizePermi(hasPermi = "system:role:query")
    public Result<Role> getById(@PathVariable Long id) {
        return Result.success(roleService.getById(id));
    }

    @GetMapping("/list")
    @Operation(summary = "获取所有启用的角色")
    @PreAuthorizePermi(hasPermi = "system:role:list")
    public Result<List<Role>> listAllEnabled() {
        return Result.success(roleService.listAllEnabled());
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询角色列表")
    @PreAuthorizePermi(hasPermi = "system:role:list")
    public Result<Page<Role>> pageRoles(RoleQuery query) {
        return Result.success(roleService.pageRoles(query));
    }

    @PostMapping
    @Operation(summary = "新增角色")
    @PreAuthorizePermi(hasPermi = "system:role:add")
    public Result<Boolean> save(@RequestBody Role role) {
        return Result.success(roleService.save(role));
    }

    @PutMapping
    @Operation(summary = "更新角色")
    @PreAuthorizePermi(hasPermi = "system:role:edit")
    public Result<Boolean> update(@RequestBody Role role) {
        return Result.success(roleService.update(role));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    @PreAuthorizePermi(hasPermi = "system:role:delete")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(roleService.delete(id));
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除角色")
    @PreAuthorizePermi(hasPermi = "system:role:delete")
    public Result<Boolean> deleteBatch(@RequestBody List<Long> ids) {
        return Result.success(roleService.deleteBatch(ids));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "修改角色状态")
    @PreAuthorizePermi(hasPermi = "system:role:edit")
    public Result<Boolean> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        return Result.success(roleService.updateStatus(id, status));
    }

    @GetMapping("/{id}/permissions")
    @Operation(summary = "获取角色权限ID列表")
    @PreAuthorizePermi(hasPermi = "system:role:query")
    public Result<List<Long>> getPermissionIds(@PathVariable Long id) {
        return Result.success(roleService.getPermissionIds(id));
    }

    @PostMapping("/{id}/permissions")
    @Operation(summary = "分配角色权限")
    @PreAuthorizePermi(hasPermi = "system:role:edit")
    public Result<Boolean> assignPermissions(@PathVariable Long id, 
                                               @RequestBody List<Long> permissionIds) {
        return Result.success(roleService.assignPermissions(id, permissionIds));
    }
}
