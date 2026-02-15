package com.content.stat.controller;

import com.content.common.result.Result;
import com.content.stat.entity.Stat;
import com.content.stat.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 统计控制器
 */
@RestController
@RequestMapping("/stat")
public class StatController {
    
    @Autowired
    private StatService statService;
    
    /**
     * 记录统计数据
     * @param stat 统计数据对象
     * @return 操作结果
     */
    @PostMapping
    public Result<Boolean> recordStat(@RequestBody Stat stat) {
        boolean success = statService.recordStat(stat);
        return Result.success(success);
    }
    
    /**
     * 根据统计类型和日期范围获取统计数据
     * @param type 统计类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计数据列表
     */
    @GetMapping("/range")
    public Result<List<Stat>> getByTypeAndDateRange(@RequestParam Integer type,
                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<Stat> list = statService.getByTypeAndDateRange(type, startDate, endDate);
        return Result.success(list);
    }
    
    /**
     * 根据统计类型和日期获取统计数据
     * @param type 统计类型
     * @param statDate 统计日期
     * @return 统计数据
     */
    @GetMapping
    public Result<Stat> getByTypeAndDate(@RequestParam Integer type,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate statDate) {
        Stat stat = statService.getByTypeAndDate(type, statDate);
        return Result.success(stat);
    }
    
    /**
     * 获取统计数据汇总
     * @param type 统计类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计数据汇总值
     */
    @GetMapping("/sum")
    public Result<Long> getSumByTypeAndDateRange(@RequestParam Integer type,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Long sum = statService.getSumByTypeAndDateRange(type, startDate, endDate);
        return Result.success(sum);
    }
    
    /**
     * 获取多类型统计数据
     * @param types 统计类型列表
     * @param statDate 统计日期
     * @return 统计数据映射，key为统计类型，value为统计数值
     */
    @PostMapping("/multi")
    public Result<Map<Integer, Long>> getMultiTypeStats(@RequestBody List<Integer> types,
                                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate statDate) {
        Map<Integer, Long> statsMap = statService.getMultiTypeStats(types, statDate);
        return Result.success(statsMap);
    }
}