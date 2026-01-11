package com.content.recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.recommend.entity.Recommend;
import com.content.recommend.mapper.RecommendMapper;
import com.content.recommend.service.RecommendService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 推荐内容服务实现
 */
@Service
public class RecommendServiceImpl extends ServiceImpl<RecommendMapper, Recommend> implements RecommendService {
    
    @Override
    public List<Recommend> getByType(Integer type) {
        LambdaQueryWrapper<Recommend> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recommend::getType, type)
                    .eq(Recommend::getStatus, 1)
                    .orderByAsc(Recommend::getSort);
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public boolean addRecommend(Recommend recommend) {
        recommend.setCreateTime(LocalDateTime.now());
        recommend.setUpdateTime(LocalDateTime.now());
        recommend.setStatus(1);
        return save(recommend);
    }
    
    @Override
    public boolean updateRecommend(Recommend recommend) {
        recommend.setUpdateTime(LocalDateTime.now());
        return updateById(recommend);
    }
    
    @Override
    public boolean deleteRecommend(Long id) {
        return removeById(id);
    }
}