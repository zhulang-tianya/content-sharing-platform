package com.content.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.content.entity.Content;
import com.content.content.mapper.ContentMapper;
import com.content.content.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容服务实现类
 * 负责内容相关的业务逻辑实现
 */
@Service
@Slf4j
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {

    /**
     * 根据分类ID获取内容列表
     * @param categoryId 分类ID
     * @return 内容列表
     */
    @Override
    public List<Content> getByCategoryId(Long categoryId) {
        log.debug("根据分类ID获取内容列表: {}", categoryId);
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        List<Content> contentList = baseMapper.selectList(queryWrapper);
        log.debug("根据分类ID获取内容列表结果: {}条记录", contentList.size());
        return contentList;
    }

    /**
     * 根据作者ID获取内容列表
     * @param authorId 作者ID
     * @return 内容列表
     */
    @Override
    public List<Content> getByAuthorId(Long authorId) {
        log.debug("根据作者ID获取内容列表: {}", authorId);
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id", authorId);
        List<Content> contentList = baseMapper.selectList(queryWrapper);
        log.debug("根据作者ID获取内容列表结果: {}条记录", contentList.size());
        return contentList;
    }

    /**
     * 根据内容类型获取内容列表
     * @param type 内容类型
     * @return 内容列表
     */
    @Override
    public List<Content> getByType(Integer type) {
        log.debug("根据内容类型获取内容列表: {}", type);
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        List<Content> contentList = baseMapper.selectList(queryWrapper);
        log.debug("根据内容类型获取内容列表结果: {}条记录", contentList.size());
        return contentList;
    }

    /**
     * 增加浏览量
     * @param id 内容ID
     * @return 是否成功
     */
    @Override
    public boolean incrementViews(Long id) {
        log.debug("增加内容浏览量: {}", id);
        boolean result = baseMapper.update(null, new UpdateWrapper<Content>().setSql("views = views + 1").eq("id", id)) > 0;
        log.debug("增加内容浏览量结果: {}", result);
        return result;
    }

    /**
     * 增加点赞数
     * @param id 内容ID
     * @return 是否成功
     */
    @Override
    public boolean incrementLikes(Long id) {
        log.debug("增加内容点赞数: {}", id);
        boolean result = baseMapper.update(null, new UpdateWrapper<Content>().setSql("likes = likes + 1").eq("id", id)) > 0;
        log.debug("增加内容点赞数结果: {}", result);
        return result;
    }

    /**
     * 增加评论数
     * @param id 内容ID
     * @return 是否成功
     */
    @Override
    public boolean incrementComments(Long id) {
        log.debug("增加内容评论数: {}", id);
        boolean result = baseMapper.update(null, new UpdateWrapper<Content>().setSql("comments = comments + 1").eq("id", id)) > 0;
        log.debug("增加内容评论数结果: {}", result);
        return result;
    }
}