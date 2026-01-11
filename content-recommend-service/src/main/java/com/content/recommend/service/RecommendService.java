package com.content.recommend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.content.recommend.entity.Recommend;

import java.util.List;

/**
 * 推荐内容服务
 */
public interface RecommendService extends IService<Recommend> {
    
    /**
     * 根据推荐类型获取推荐内容列表
     * @param type 推荐类型
     * @return 推荐内容列表
     */
    List<Recommend> getByType(Integer type);
    
    /**
     * 添加推荐内容
     * @param recommend 推荐内容对象
     * @return 是否成功
     */
    boolean addRecommend(Recommend recommend);
    
    /**
     * 更新推荐内容
     * @param recommend 推荐内容对象
     * @return 是否成功
     */
    boolean updateRecommend(Recommend recommend);
    
    /**
     * 删除推荐内容
     * @param id 推荐内容ID
     * @return 是否成功
     */
    boolean deleteRecommend(Long id);
}