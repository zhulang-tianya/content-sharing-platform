package com.content.stat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.stat.entity.Stat;
import com.content.stat.mapper.StatMapper;
import com.content.stat.service.StatService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计服务实现
 */
@Service
public class StatServiceImpl extends ServiceImpl<StatMapper, Stat> implements StatService {
    
    @Override
    public boolean recordStat(Stat stat) {
        // 检查是否已存在该类型和日期的统计数据
        Stat existingStat = getByTypeAndDate(stat.getType(), stat.getStatDate());
        if (existingStat != null) {
            // 如果已存在，更新数值
            existingStat.setValue(existingStat.getValue() + stat.getValue());
            return updateById(existingStat);
        } else {
            // 如果不存在，添加新记录
            stat.setCreateTime(LocalDateTime.now());
            return save(stat);
        }
    }
    
    @Override
    public List<Stat> getByTypeAndDateRange(Integer type, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<Stat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Stat::getType, type)
                    .between(Stat::getStatDate, startDate, endDate)
                    .orderByAsc(Stat::getStatDate);
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public Stat getByTypeAndDate(Integer type, LocalDate statDate) {
        LambdaQueryWrapper<Stat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Stat::getType, type)
                    .eq(Stat::getStatDate, statDate);
        return baseMapper.selectOne(queryWrapper);
    }
    
    @Override
    public Long getSumByTypeAndDateRange(Integer type, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<Stat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Stat::getType, type)
                    .between(Stat::getStatDate, startDate, endDate);
        return baseMapper.selectObjs(queryWrapper)
                .stream()
                .mapToLong(obj -> ((Stat) obj).getValue())
                .sum();
    }
    
    @Override
    public Map<Integer, Long> getMultiTypeStats(List<Integer> types, LocalDate statDate) {
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
        
        return resultMap;
    }
}