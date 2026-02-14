package com.content.user.controller;

import com.content.common.result.Result;
import com.content.framework.security.LoginUser;
import com.content.framework.security.SecurityUtils;
import com.content.framework.security.annotation.PreAuthorizePermi;
import com.content.user.entity.Menu;
import com.content.user.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理控制器
 */
@RestController
@RequestMapping("/menu")
@Tag(name = "菜单管理", description = "菜单相关接口")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{id}")
    @Operation(summary = "获取菜单详情")
    @PreAuthorizePermi(hasPermi = "system:menu:query")
    public Result<Menu> getById(@PathVariable Long id) {
        return Result.success(menuService.getById(id));
    }

    @GetMapping("/list")
    @Operation(summary = "获取所有菜单")
    @PreAuthorizePermi(hasPermi = "system:menu:list")
    public Result<List<Menu>> listAll() {
        return Result.success(menuService.listAll());
    }

    @GetMapping("/tree")
    @Operation(summary = "获取菜单树")
    @PreAuthorizePermi(hasPermi = "system:menu:list")
    public Result<List<Menu>> treeMenus() {
        return Result.success(menuService.treeMenus());
    }

    @GetMapping("/routers")
    @Operation(summary = "获取路由菜单")
    public Result<List<Menu>> getRouters() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser != null ? loginUser.getUserId() : null;
        return Result.success(menuService.getRouters(userId));
    }

    @PostMapping
    @Operation(summary = "新增菜单")
    @PreAuthorizePermi(hasPermi = "system:menu:add")
    public Result<Boolean> save(@RequestBody Menu menu) {
        return Result.success(menuService.save(menu));
    }

    @PutMapping
    @Operation(summary = "更新菜单")
    @PreAuthorizePermi(hasPermi = "system:menu:edit")
    public Result<Boolean> update(@RequestBody Menu menu) {
        return Result.success(menuService.update(menu));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除菜单")
    @PreAuthorizePermi(hasPermi = "system:menu:delete")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(menuService.delete(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "修改菜单状态")
    @PreAuthorizePermi(hasPermi = "system:menu:edit")
    public Result<Boolean> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        return Result.success(menuService.updateStatus(id, status));
    }

    @GetMapping("/role/{roleId}")
    @Operation(summary = "获取角色菜单ID列表")
    @PreAuthorizePermi(hasPermi = "system:menu:query")
    public Result<List<Long>> getMenuIdsByRoleId(@PathVariable Long roleId) {
        return Result.success(menuService.getMenuIdsByRoleId(roleId));
    }

    @PostMapping("/role/{roleId}")
    @Operation(summary = "分配角色菜单")
    @PreAuthorizePermi(hasPermi = "system:menu:edit")
    public Result<Boolean> assignRoleMenus(@PathVariable Long roleId, 
                                             @RequestBody List<Long> menuIds) {
        return Result.success(menuService.assignRoleMenus(roleId, menuIds));
    }
}
