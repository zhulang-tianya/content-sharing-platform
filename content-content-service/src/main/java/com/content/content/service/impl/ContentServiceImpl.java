package com.content.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.content.entity.Content;
import com.content.content.mapper.ContentMapper;
import com.content.content.service.ContentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容服务实现类
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {

    @Override
    public List<Content> getByCategoryId(Long categoryId) {
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Content> getByAuthorId(Long authorId) {
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id", authorId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Content> getByType(Integer type) {
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean incrementViews(Long id) {
        return baseMapper.update(null, new UpdateWrapper<Content>().setSql("views = views + 1").eq("id", id)) > 0;
    }

    @Override
    public boolean incrementLikes(Long id) {
        return baseMapper.update(null, new UpdateWrapper<Content>().setSql("likes = likes + 1").eq("id", id)) > 0;
    }

    @Override
    public boolean incrementComments(Long id) {
        return baseMapper.update(null, new UpdateWrapper<Content>().setSql("comments = comments + 1").eq("id", id)) > 0;
    }
}