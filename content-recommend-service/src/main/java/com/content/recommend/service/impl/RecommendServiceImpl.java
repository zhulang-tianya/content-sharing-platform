package com.content.recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.recommend.entity.Recommend;
import com.content.recommend.mapper.RecommendMapper;
import com.content.recommend.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 推荐内容服务实现
 */
@Slf4j
@Service
public class RecommendServiceImpl extends ServiceImpl<RecommendMapper, Recommend> implements RecommendService {
    
    /**
     * 根据推荐类型获取推荐内容列表
     * 
     * @param type 推荐类型（1-热门推荐 2-最新推荐 3-分类推荐 4-个性化推荐）
     * @return 推荐内容列表
     */
    @Override
    public List<Recommend> getByType(Integer type) {
        log.debug("根据推荐类型获取推荐内容，type: {}", type);
        LambdaQueryWrapper<Recommend> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recommend::getType, type)
                    .eq(Recommend::getStatus, 1)
                    .orderByAsc(Recommend::getSort);
        List<Recommend> recommends = baseMapper.selectList(queryWrapper);
        log.debug("查询到推荐内容数量: {}", recommends != null ? recommends.size() : 0);
        return recommends;
    }
    
    /**
     * 添加推荐内容
     * 
     * @param recommend 推荐内容对象
     * @return 是否添加成功
     */
    @Override
    public boolean addRecommend(Recommend recommend) {
        log.debug("添加推荐内容，recommend: {}", recommend);
        recommend.setCreateTime(LocalDateTime.now());
        recommend.setUpdateTime(LocalDateTime.now());
        recommend.setStatus(1);
        boolean result = save(recommend);
        log.debug("添加推荐内容结果: {}", result);
        return result;
    }
    
    /**
     * 更新推荐内容
     * 
     * @param recommend 推荐内容对象
     * @return 是否更新成功
     */
    @Override
    public boolean updateRecommend(Recommend recommend) {
        log.debug("更新推荐内容，recommend: {}", recommend);
        recommend.setUpdateTime(LocalDateTime.now());
        boolean result = updateById(recommend);
        log.debug("更新推荐内容结果: {}", result);
        return result;
    }
    
    /**
     * 删除推荐内容
     * 
     * @param id 推荐内容ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteRecommend(Long id) {
        log.debug("删除推荐内容，id: {}", id);
        boolean result = removeById(id);
        log.debug("删除推荐内容结果: {}", result);
        return result;
    }
}