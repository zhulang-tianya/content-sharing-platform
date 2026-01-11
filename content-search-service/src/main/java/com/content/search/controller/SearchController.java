package com.content.search.controller;

import com.content.common.result.Result;
import com.content.search.entity.Search;
import com.content.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 搜索控制器
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    
    @Autowired
    private SearchService searchService;
    
    /**
     * 保存搜索历史
     * @param search 搜索历史对象
     * @return 操作结果
     */
    @PostMapping
    public Result<Boolean> saveSearch(@RequestBody Search search) {
        boolean success = searchService.saveSearch(search);
        return Result.success(success);
    }
    
    /**
     * 根据用户ID获取搜索历史
     * @param userId 用户ID
     * @return 搜索历史列表
     */
    @GetMapping("/history/{userId}")
    public Result<List<Search>> getByUserId(@PathVariable Long userId) {
        List<Search> list = searchService.getByUserId(userId);
        return Result.success(list);
    }
    
    /**
     * 获取热门搜索词列表
     * @param limit 限制数量
     * @return 热门搜索词列表
     */
    @GetMapping("/hot")
    public Result<List<String>> getHotKeywords(@RequestParam(defaultValue = "10") Integer limit) {
        List<String> hotKeywords = searchService.getHotKeywords(limit);
        return Result.success(hotKeywords);
    }
    
    /**
     * 清空用户搜索历史
     * @param userId 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/history/{userId}")
    public Result<Boolean> clearHistory(@PathVariable Long userId) {
        boolean success = searchService.clearHistory(userId);
        return Result.success(success);
    }
    
    /**
     * 搜索内容
     * @param keyword 搜索关键词
     * @param type 搜索类型
     * @return 搜索结果数量
     */
    @GetMapping("/content")
    public Result<Integer> searchContent(@RequestParam String keyword, 
                                        @RequestParam(defaultValue = "1") Integer type) {
        Integer resultCount = searchService.searchContent(keyword, type);
        // 保存搜索历史
        Search search = new Search();
        search.setKeyword(keyword);
        search.setType(type);
        search.setResultCount(resultCount);
        search.setUserId(0L); // 默认为匿名搜索
        searchService.saveSearch(search);
        return Result.success(resultCount);
    }
}