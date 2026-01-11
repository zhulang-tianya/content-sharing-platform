package com.content.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.content.common.result.Result;
import com.content.user.entity.User;
import com.content.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    @GetMapping
    @Operation(summary = "获取用户列表", description = "获取所有用户信息")
    public Result<List<User>> getUserList() {
        List<User> userList = userService.list();
        return Result.success(userList);
    }

    /**
     * 分页获取用户列表
     *
     * @param page 当前页码
     * @param size 每页大小
     * @return 分页用户列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页获取用户列表", description = "分页获取用户信息")
    public Result<Page<User>> getUserPage(@RequestParam(defaultValue = "1") Integer page, 
                                          @RequestParam(defaultValue = "10") Integer size) {
        Page<User> userPage = userService.page(new Page<>(page, size));
        return Result.success(userPage);
    }

    /**
     * 根据ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取用户信息", description = "根据用户ID获取用户详情")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    /**
     * 创建用户
     *
     * @param user 用户信息
     * @return 创建结果
     */
    @PostMapping
    @Operation(summary = "创建用户", description = "创建新用户")
    public Result<Boolean> createUser(@RequestBody User user) {
        boolean result = userService.save(user);
        return Result.success(result);
    }

    /**
     * 更新用户
     *
     * @param id   用户ID
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新用户", description = "根据用户ID更新用户信息")
    public Result<Boolean> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        boolean result = userService.updateById(user);
        return Result.success(result);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "根据用户ID删除用户")
    public Result<Boolean> deleteUser(@PathVariable Long id) {
        boolean result = userService.removeById(id);
        return Result.success(result);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/username/{username}")
    @Operation(summary = "根据用户名获取用户信息", description = "根据用户名查询用户详情")
    public Result<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return Result.success(user);
    }
}