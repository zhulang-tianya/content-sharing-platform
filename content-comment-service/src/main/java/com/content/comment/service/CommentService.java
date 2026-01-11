package com.content.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.content.comment.entity.Comment;
import java.util.List;

/**
 * 评论服务接口
 */
public interface CommentService extends IService<Comment> {
    /**
     * 根据内容ID获取评论列表
     *
     * @param contentId 内容ID
     * @return 评论列表
     */
    List<Comment> getByContentId(Long contentId);

    /**
     * 根据用户ID获取评论列表
     *
     * @param userId 用户ID
     * @return 评论列表
     */
    List<Comment> getByUserId(Long userId);

    /**
     * 根据父评论ID获取子评论列表
     *
     * @param parentId 父评论ID
     * @return 子评论列表
     */
    List<Comment> getByParentId(Long parentId);

    /**
     * 增加点赞数
     *
     * @param id 评论ID
     * @return 更新结果
     */
    boolean incrementLikes(Long id);
}