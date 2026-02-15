package com.content.recommend.controller;

import com.content.common.result.Result;
import com.content.recommend.entity.Recommend;
import com.content.recommend.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 推荐内容控制器
 */
@RestController
@RequestMapping("/recommend")
public class RecommendController {
    
    @Autowired
    private RecommendService recommendService;
    
    /**
     * 根据推荐类型获取推荐内容列表
     * @param type 推荐类型
     * @return 推荐内容列表
     */
    @GetMapping("/type/{type}")
    public Result<List<Recommend>> getByType(@PathVariable Integer type) {
        List<Recommend> list = recommendService.getByType(type);
        return Result.success(list);
    }
    
    /**
     * 添加推荐内容
     * @param recommend 推荐内容对象
     * @return 操作结果
     */
    @PostMapping
    public Result<Boolean> addRecommend(@RequestBody Recommend recommend) {
        boolean success = recommendService.addRecommend(recommend);
        return Result.success(success);
    }
    
    /**
     * 更新推荐内容
     * @param recommend 推荐内容对象
     * @return 操作结果
     */
    @PutMapping
    public Result<Boolean> updateRecommend(@RequestBody Recommend recommend) {
        boolean success = recommendService.updateRecommend(recommend);
        return Result.success(success);
    }
    
    /**
     * 删除推荐内容
     * @param id 推荐内容ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteRecommend(@PathVariable Long id) {
        boolean success = recommendService.deleteRecommend(id);
        return Result.success(success);
    }
    
    /**
     * 获取所有推荐内容列表
     * @return 推荐内容列表
     */
    @GetMapping
    public Result<List<Recommend>> getAll() {
        List<Recommend> list = recommendService.list();
        return Result.success(list);
    }
}