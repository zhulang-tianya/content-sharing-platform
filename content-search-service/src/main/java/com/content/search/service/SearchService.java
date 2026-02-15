package com.content.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.content.search.entity.Search;

import java.util.List;

/**
 * 搜索服务
 */
public interface SearchService extends IService<Search> {
    
    /**
     * 保存搜索历史
     * @param search 搜索历史对象
     * @return 是否成功
     */
    boolean saveSearch(Search search);
    
    /**
     * 根据用户ID获取搜索历史
     * @param userId 用户ID
     * @return 搜索历史列表
     */
    List<Search> getByUserId(Long userId);
    
    /**
     * 获取热门搜索词列表
     * @param limit 限制数量
     * @return 热门搜索词列表
     */
    List<String> getHotKeywords(Integer limit);
    
    /**
     * 清空用户搜索历史
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean clearHistory(Long userId);
    
    /**
     * 根据关键词搜索内容（模拟搜索功能）
     * @param keyword 搜索关键词
     * @param type 搜索类型
     * @return 搜索结果数量
     */
    Integer searchContent(String keyword, Integer type);
}