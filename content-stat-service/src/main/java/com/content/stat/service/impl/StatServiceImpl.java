package com.content.stat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.stat.entity.Stat;
import com.content.stat.mapper.StatMapper;
import com.content.stat.service.StatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计服务实现
 */
@Slf4j
@Service
public class StatServiceImpl extends ServiceImpl<StatMapper, Stat> implements StatService {
    
    /**
     * 记录统计数据
     * 
     * @param stat 统计数据对象
     * @return 是否记录成功
     */
    @Override
    public boolean recordStat(Stat stat) {
        log.debug("记录统计数据，stat: {}", stat);
        // 检查是否已存在该类型和日期的统计数据
        Stat existingStat = getByTypeAndDate(stat.getType(), stat.getStatDate());
        if (existingStat != null) {
            // 如果已存在，更新数值
            log.debug("已存在该类型和日期的统计数据，更新数值");
            existingStat.setValue(existingStat.getValue() + stat.getValue());
            boolean result = updateById(existingStat);
            log.debug("更新统计数据结果: {}", result);
            return result;
        } else {
            // 如果不存在，添加新记录
            log.debug("不存在该类型和日期的统计数据，添加新记录");
            stat.setCreateTime(LocalDateTime.now());
            boolean result = save(stat);
            log.debug("添加统计数据结果: {}", result);
            return result;
        }
    }
    
    /**
     * 根据类型和日期范围获取统计数据
     * 
     * @param type 统计类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计数据列表
     */
    @Override
    public List<Stat> getByTypeAndDateRange(Integer type, LocalDate startDate, LocalDate endDate) {
        log.debug("根据类型和日期范围获取统计数据，type: {}, startDate: {}, endDate: {}", type, startDate, endDate);
        LambdaQueryWrapper<Stat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Stat::getType, type)
                    .between(Stat::getStatDate, startDate, endDate)
                    .orderByAsc(Stat::getStatDate);
        List<Stat> stats = baseMapper.selectList(queryWrapper);
        log.debug("查询到统计数据数量: {}", stats != null ? stats.size() : 0);
        return stats;
    }
    
    /**
     * 根据类型和日期获取统计数据
     * 
     * @param type 统计类型
     * @param statDate 统计日期
     * @return 统计数据
     */
    @Override
    public Stat getByTypeAndDate(Integer type, LocalDate statDate) {
        log.debug("根据类型和日期获取统计数据，type: {}, statDate: {}", type, statDate);
        LambdaQueryWrapper<Stat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Stat::getType, type)
                    .eq(Stat::getStatDate, statDate);
        Stat stat = baseMapper.selectOne(queryWrapper);
        log.debug("查询到统计数据: {}", stat != null ? "存在" : "不存在");
        return stat;
    }
    
    /**
     * 根据类型和日期范围获取统计数据汇总
     * 
     * @param type 统计类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计数据汇总值
     */
    @Override
    public Long getSumByTypeAndDateRange(Integer type, LocalDate startDate, LocalDate endDate) {
        log.debug("根据类型和日期范围获取统计数据汇总，type: {}, startDate: {}, endDate: {}", type, startDate, endDate);
        LambdaQueryWrapper<Stat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Stat::getType, type)
                    .between(Stat::getStatDate, startDate, endDate);
        Long sum = baseMapper.selectObjs(queryWrapper)
                .stream()
                .mapToLong(obj -> ((Stat) obj).getValue())
                .sum();
        log.debug("统计数据汇总值: {}", sum);
        return sum;
    }
    
    /**
     * 获取多类型统计数据
     * 
     * @param types 统计类型列表
     * @param statDate 统计日期
     * @return 统计数据映射，key为统计类型，value为统计数值
     */
    @Override
    public Map<Integer, Long> getMultiTypeStats(List<Integer> types, LocalDate statDate) {
        log.debug("获取多类型统计数据，types: {}, statDate: {}", types, statDate);
        LambdaQueryWrapper<Stat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Stat::getType, types)
                    .eq(Stat::getStatDate, statDate);
        
        List<Stat> statList = baseMapper.selectList(queryWrapper);
        Map<Integer, Long> resultMap = new HashMap<>();
        
        // 初始化所有类型的统计值为0
        for (Integer type : types) {
            resultMap.put(type, 0L);
        }
        
        // 填充实际统计值
        for (Stat stat : statList) {
            resultMap.put(stat.getType(), stat.getValue());
        }
        
        log.debug("获取到多类型统计数据数量: {}", resultMap.size());
        return resultMap;
    }
}