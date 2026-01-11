package com.content.comment.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.content.common.result.Result;
import com.content.comment.entity.Comment;
import com.content.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/comment")
@Tag(name = "评论管理", description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取评论列表
     *
     * @return 评论列表
     */
    @GetMapping
    @Operation(summary = "获取评论列表", description = "获取所有评论信息")
    public Result<List<Comment>> getCommentList() {
        List<Comment> commentList = commentService.list();
        return Result.success(commentList);
    }

    /**
     * 分页获取评论列表
     *
     * @param page 当前页码
     * @param size 每页大小
     * @return 分页评论列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页获取评论列表", description = "分页获取评论信息")
    public Result<Page<Comment>> getCommentPage(@RequestParam(defaultValue = "1") Integer page, 
                                               @RequestParam(defaultValue = "10") Integer size) {
        Page<Comment> commentPage = commentService.page(new Page<>(page, size));
        return Result.success(commentPage);
    }

    /**
     * 根据ID获取评论信息
     *
     * @param id 评论ID
     * @return 评论信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取评论信息", description = "根据评论ID获取评论详情")
    public Result<Comment> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getById(id);
        return Result.success(comment);
    }

    /**
     * 根据内容ID获取评论列表
     *
     * @param contentId 内容ID
     * @return 评论列表
     */
    @GetMapping("/content/{contentId}")
    @Operation(summary = "根据内容ID获取评论列表", description = "根据内容ID获取评论列表")
    public Result<List<Comment>> getCommentByContentId(@PathVariable Long contentId) {
        List<Comment> commentList = commentService.getByContentId(contentId);
        return Result.success(commentList);
    }

    /**
     * 根据用户ID获取评论列表
     *
     * @param userId 用户ID
     * @return 评论列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "根据用户ID获取评论列表", description = "根据用户ID获取评论列表")
    public Result<List<Comment>> getCommentByUserId(@PathVariable Long userId) {
        List<Comment> commentList = commentService.getByUserId(userId);
        return Result.success(commentList);
    }

    /**
     * 根据父评论ID获取子评论列表
     *
     * @param parentId 父评论ID
     * @return 子评论列表
     */
    @GetMapping("/parent/{parentId}")
    @Operation(summary = "根据父评论ID获取子评论列表", description = "根据父评论ID获取子评论列表")
    public Result<List<Comment>> getCommentByParentId(@PathVariable Long parentId) {
        List<Comment> commentList = commentService.getByParentId(parentId);
        return Result.success(commentList);
    }

    /**
     * 创建评论
     *
     * @param comment 评论信息
     * @return 创建结果
     */
    @PostMapping
    @Operation(summary = "创建评论", description = "创建新评论")
    public Result<Boolean> createComment(@RequestBody Comment comment) {
        boolean result = commentService.save(comment);
        return Result.success(result);
    }

    /**
     * 更新评论
     *
     * @param id      评论ID
     * @param comment 评论信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新评论", description = "根据评论ID更新评论信息")
    public Result<Boolean> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        comment.setId(id);
        boolean result = commentService.updateById(comment);
        return Result.success(result);
    }

    /**
     * 删除评论
     *
     * @param id 评论ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论", description = "根据评论ID删除评论")
    public Result<Boolean> deleteComment(@PathVariable Long id) {
        boolean result = commentService.removeById(id);
        return Result.success(result);
    }

    /**
     * 增加点赞数
     *
     * @param id 评论ID
     * @return 更新结果
     */
    @PostMapping("/{id}/like")
    @Operation(summary = "增加点赞数", description = "根据评论ID增加点赞数")
    public Result<Boolean> likeComment(@PathVariable Long id) {
        boolean result = commentService.incrementLikes(id);
        return Result.success(result);
    }
}