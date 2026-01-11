package com.content.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.comment.entity.Comment;
import com.content.comment.mapper.CommentMapper;
import com.content.comment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论服务实现类
 */
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    /**
     * 根据内容ID查询评论列表
     * 
     * @param contentId 内容ID
     * @return 评论列表
     */
    @Override
    public List<Comment> getByContentId(Long contentId) {
        log.debug("根据内容ID查询评论列表，contentId: {}", contentId);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("content_id", contentId);
        queryWrapper.orderByDesc("create_time");
        List<Comment> comments = baseMapper.selectList(queryWrapper);
        log.debug("查询到评论数量: {}", comments != null ? comments.size() : 0);
        return comments;
    }

    /**
     * 根据用户ID查询评论列表
     * 
     * @param userId 用户ID
     * @return 评论列表
     */
    @Override
    public List<Comment> getByUserId(Long userId) {
        log.debug("根据用户ID查询评论列表，userId: {}", userId);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("create_time");
        List<Comment> comments = baseMapper.selectList(queryWrapper);
        log.debug("查询到评论数量: {}", comments != null ? comments.size() : 0);
        return comments;
    }

    /**
     * 根据父评论ID查询回复列表
     * 
     * @param parentId 父评论ID
     * @return 回复列表
     */
    @Override
    public List<Comment> getByParentId(Long parentId) {
        log.debug("根据父评论ID查询回复列表，parentId: {}", parentId);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        queryWrapper.orderByDesc("create_time");
        List<Comment> comments = baseMapper.selectList(queryWrapper);
        log.debug("查询到回复数量: {}", comments != null ? comments.size() : 0);
        return comments;
    }

    /**
     * 增加评论点赞数
     * 
     * @param id 评论ID
     * @return 是否增加成功
     */
    @Override
    public boolean incrementLikes(Long id) {
        log.debug("增加评论点赞数，id: {}", id);
        boolean result = baseMapper.update(null, new UpdateWrapper<Comment>().setSql("likes = likes + 1").eq("id", id)) > 0;
        log.debug("增加点赞数结果: {}", result);
        return result;
    }
}