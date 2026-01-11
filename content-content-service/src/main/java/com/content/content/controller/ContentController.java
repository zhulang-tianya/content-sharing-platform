package com.content.content.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.content.common.result.Result;
import com.content.content.entity.Content;
import com.content.content.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 内容控制器
 */
@RestController
@RequestMapping("/content")
@Tag(name = "内容管理", description = "内容相关接口")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 获取内容列表
     *
     * @return 内容列表
     */
    @GetMapping
    @Operation(summary = "获取内容列表", description = "获取所有内容信息")
    public Result<List<Content>> getContentList() {
        List<Content> contentList = contentService.list();
        return Result.success(contentList);
    }

    /**
     * 分页获取内容列表
     *
     * @param page 当前页码
     * @param size 每页大小
     * @return 分页内容列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页获取内容列表", description = "分页获取内容信息")
    public Result<Page<Content>> getContentPage(@RequestParam(defaultValue = "1") Integer page, 
                                               @RequestParam(defaultValue = "10") Integer size) {
        Page<Content> contentPage = contentService.page(new Page<>(page, size));
        return Result.success(contentPage);
    }

    /**
     * 根据ID获取内容信息
     *
     * @param id 内容ID
     * @return 内容信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取内容信息", description = "根据内容ID获取内容详情")
    public Result<Content> getContentById(@PathVariable Long id) {
        Content content = contentService.getById(id);
        // 增加浏览量
        contentService.incrementViews(id);
        return Result.success(content);
    }

    /**
     * 根据分类ID获取内容列表
     *
     * @param categoryId 分类ID
     * @return 内容列表
     */
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "根据分类ID获取内容列表", description = "根据分类ID获取内容列表")
    public Result<List<Content>> getContentByCategoryId(@PathVariable Long categoryId) {
        List<Content> contentList = contentService.getByCategoryId(categoryId);
        return Result.success(contentList);
    }

    /**
     * 根据作者ID获取内容列表
     *
     * @param authorId 作者ID
     * @return 内容列表
     */
    @GetMapping("/author/{authorId}")
    @Operation(summary = "根据作者ID获取内容列表", description = "根据作者ID获取内容列表")
    public Result<List<Content>> getContentByAuthorId(@PathVariable Long authorId) {
        List<Content> contentList = contentService.getByAuthorId(authorId);
        return Result.success(contentList);
    }

    /**
     * 根据内容类型获取内容列表
     *
     * @param type 内容类型
     * @return 内容列表
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "根据内容类型获取内容列表", description = "根据内容类型获取内容列表")
    public Result<List<Content>> getContentByType(@PathVariable Integer type) {
        List<Content> contentList = contentService.getByType(type);
        return Result.success(contentList);
    }

    /**
     * 创建内容
     *
     * @param content 内容信息
     * @return 创建结果
     */
    @PostMapping
    @Operation(summary = "创建内容", description = "创建新内容")
    public Result<Boolean> createContent(@RequestBody Content content) {
        boolean result = contentService.save(content);
        return Result.success(result);
    }

    /**
     * 更新内容
     *
     * @param id      内容ID
     * @param content 内容信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新内容", description = "根据内容ID更新内容信息")
    public Result<Boolean> updateContent(@PathVariable Long id, @RequestBody Content content) {
        content.setId(id);
        boolean result = contentService.updateById(content);
        return Result.success(result);
    }

    /**
     * 删除内容
     *
     * @param id 内容ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除内容", description = "根据内容ID删除内容")
    public Result<Boolean> deleteContent(@PathVariable Long id) {
        boolean result = contentService.removeById(id);
        return Result.success(result);
    }

    /**
     * 增加点赞数
     *
     * @param id 内容ID
     * @return 更新结果
     */
    @PostMapping("/{id}/like")
    @Operation(summary = "增加点赞数", description = "根据内容ID增加点赞数")
    public Result<Boolean> likeContent(@PathVariable Long id) {
        boolean result = contentService.incrementLikes(id);
        return Result.success(result);
    }

    /**
     * 增加评论数
     *
     * @param id 内容ID
     * @return 更新结果
     */
    @PostMapping("/{id}/comment")
    @Operation(summary = "增加评论数", description = "根据内容ID增加评论数")
    public Result<Boolean> commentContent(@PathVariable Long id) {
        boolean result = contentService.incrementComments(id);
        return Result.success(result);
    }
}