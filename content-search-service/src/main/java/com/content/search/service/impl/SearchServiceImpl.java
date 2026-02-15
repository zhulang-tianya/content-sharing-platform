package com.content.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.search.entity.Search;
import com.content.search.mapper.SearchMapper;
import com.content.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 搜索服务实现
 */
@Slf4j
@Service
public class SearchServiceImpl extends ServiceImpl<SearchMapper, Search> implements SearchService {
    
    /**
     * 保存搜索历史
     * 
     * @param search 搜索对象
     * @return 是否保存成功
     */
    @Override
    public boolean saveSearch(Search search) {
        log.debug("保存搜索历史，search: {}", search);
        search.setCreateTime(LocalDateTime.now());
        boolean result = save(search);
        log.debug("保存搜索历史结果: {}", result);
        return result;
    }
    
    /**
     * 根据用户ID获取搜索历史
     * 
     * @param userId 用户ID
     * @return 搜索历史列表
     */
    @Override
    public List<Search> getByUserId(Long userId) {
        log.debug("根据用户ID获取搜索历史，userId: {}", userId);
        LambdaQueryWrapper<Search> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Search::getUserId, userId)
                    .orderByDesc(Search::getCreateTime);
        List<Search> searches = baseMapper.selectList(queryWrapper);
        log.debug("查询到搜索历史数量: {}", searches != null ? searches.size() : 0);
        return searches;
    }
    
    /**
     * 获取热门搜索词
     * 
     * @param limit 限制数量
     * @return 热门搜索词列表
     */
    @Override
    public List<String> getHotKeywords(Integer limit) {
        log.debug("获取热门搜索词，limit: {}", limit);
        // 模拟热门搜索词，实际项目中可以根据搜索频率统计
        LambdaQueryWrapper<Search> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Search::getCreateTime)
                    .last("LIMIT " + limit);
        
        List<Search> list = baseMapper.selectList(queryWrapper);
        List<String> hotKeywords = list.stream()
                .map(Search::getKeyword)
                .distinct()
                .collect(Collectors.toList());
        log.debug("获取到热门搜索词数量: {}", hotKeywords.size());
        return hotKeywords;
    }
    
    /**
     * 清空用户搜索历史
     * 
     * @param userId 用户ID
     * @return 是否清空成功
     */
    @Override
    public boolean clearHistory(Long userId) {
        log.debug("清空用户搜索历史，userId: {}", userId);
        LambdaQueryWrapper<Search> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Search::getUserId, userId);
        boolean result = remove(queryWrapper);
        log.debug("清空用户搜索历史结果: {}", result);
        return result;
    }
    
    /**
     * 搜索内容
     * 
     * @param keyword 搜索关键词
     * @param type 搜索类型（1-内容搜索 2-用户搜索 3-标签搜索）
     * @return 搜索结果数量
     */
    @Override
    public Integer searchContent(String keyword, Integer type) {
        log.debug("搜索内容，keyword: {}, type: {}", keyword, type);
        // 模拟搜索结果数量，实际项目中可以调用搜索引擎API
        // 这里简单返回随机数作为模拟结果
        Integer resultCount = (int) (Math.random() * 100) + 1;
        log.debug("搜索结果数量: {}", resultCount);
        return resultCount;
    }
}