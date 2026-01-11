package com.content.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.search.entity.Search;
import com.content.search.mapper.SearchMapper;
import com.content.search.service.SearchService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 搜索服务实现
 */
@Service
public class SearchServiceImpl extends ServiceImpl<SearchMapper, Search> implements SearchService {
    
    @Override
    public boolean saveSearch(Search search) {
        search.setCreateTime(LocalDateTime.now());
        return save(search);
    }
    
    @Override
    public List<Search> getByUserId(Long userId) {
        LambdaQueryWrapper<Search> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Search::getUserId, userId)
                    .orderByDesc(Search::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<String> getHotKeywords(Integer limit) {
        // 模拟热门搜索词，实际项目中可以根据搜索频率统计
        LambdaQueryWrapper<Search> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Search::getCreateTime)
                    .last("LIMIT " + limit);
        
        List<Search> list = baseMapper.selectList(queryWrapper);
        return list.stream()
                .map(Search::getKeyword)
                .distinct()
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean clearHistory(Long userId) {
        LambdaQueryWrapper<Search> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Search::getUserId, userId);
        return remove(queryWrapper);
    }
    
    @Override
    public Integer searchContent(String keyword, Integer type) {
        // 模拟搜索结果数量，实际项目中可以调用搜索引擎API
        // 这里简单返回随机数作为模拟结果
        return (int) (Math.random() * 100) + 1;
    }
}