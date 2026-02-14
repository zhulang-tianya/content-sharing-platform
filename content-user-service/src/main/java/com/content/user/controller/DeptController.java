package com.content.user.controller;

import com.content.common.result.Result;
import com.content.framework.security.annotation.PreAuthorizePermi;
import com.content.user.entity.Dept;
import com.content.user.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器
 */
@RestController
@RequestMapping("/dept")
@Tag(name = "部门管理", description = "部门相关接口")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;

    @GetMapping("/{id}")
    @Operation(summary = "获取部门详情")
    @PreAuthorizePermi(hasPermi = "system:dept:query")
    public Result<Dept> getById(@PathVariable Long id) {
        return Result.success(deptService.getById(id));
    }

    @GetMapping("/list")
    @Operation(summary = "获取所有部门")
    @PreAuthorizePermi(hasPermi = "system:dept:list")
    public Result<List<Dept>> listAll() {
        return Result.success(deptService.listAll());
    }

    @GetMapping("/tree")
    @Operation(summary = "获取部门树")
    @PreAuthorizePermi(hasPermi = "system:dept:list")
    public Result<List<Dept>> treeDepts() {
        return Result.success(deptService.treeDepts());
    }

    @PostMapping
    @Operation(summary = "新增部门")
    @PreAuthorizePermi(hasPermi = "system:dept:add")
    public Result<Boolean> save(@RequestBody Dept dept) {
        return Result.success(deptService.save(dept));
    }

    @PutMapping
    @Operation(summary = "更新部门")
    @PreAuthorizePermi(hasPermi = "system:dept:edit")
    public Result<Boolean> update(@RequestBody Dept dept) {
        return Result.success(deptService.update(dept));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除部门")
    @PreAuthorizePermi(hasPermi = "system:dept:delete")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(deptService.delete(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "修改部门状态")
    @PreAuthorizePermi(hasPermi = "system:dept:edit")
    public Result<Boolean> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        return Result.success(deptService.updateStatus(id, status));
    }
}
