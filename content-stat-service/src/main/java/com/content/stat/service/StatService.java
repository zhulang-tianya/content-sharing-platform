package com.content.stat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.content.stat.entity.Stat;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 统计服务
 */
public interface StatService extends IService<Stat> {
    
    /**
     * 记录统计数据
     * @param stat 统计数据对象
     * @return 是否成功
     */
    boolean recordStat(Stat stat);
    
    /**
     * 根据统计类型和日期范围获取统计数据
     * @param type 统计类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计数据列表
     */
    List<Stat> getByTypeAndDateRange(Integer type, LocalDate startDate, LocalDate endDate);
    
    /**
     * 根据统计类型和日期获取统计数据
     * @param type 统计类型
     * @param statDate 统计日期
     * @return 统计数据
     */
    Stat getByTypeAndDate(Integer type, LocalDate statDate);
    
    /**
     * 获取统计数据汇总
     * @param type 统计类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计数据汇总值
     */
    Long getSumByTypeAndDateRange(Integer type, LocalDate startDate, LocalDate endDate);
    
    /**
     * 获取多类型统计数据
     * @param types 统计类型列表
     * @param statDate 统计日期
     * @return 统计数据映射，key为统计类型，value为统计数值
     */
    Map<Integer, Long> getMultiTypeStats(List<Integer> types, LocalDate statDate);
}