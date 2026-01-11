package com.content.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.pay.entity.Pay;
import com.content.pay.mapper.PayMapper;
import com.content.pay.service.PayService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 支付记录服务实现
 */
@Service
public class PayServiceImpl extends ServiceImpl<PayMapper, Pay> implements PayService {
    
    @Override
    public List<Pay> getByUserId(Long userId) {
        LambdaQueryWrapper<Pay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Pay::getUserId, userId)
                    .orderByDesc(Pay::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public boolean createPay(Pay pay) {
        pay.setCreateTime(LocalDateTime.now());
        pay.setUpdateTime(LocalDateTime.now());
        pay.setStatus(0);
        return save(pay);
    }
    
    @Override
    public boolean updatePayStatus(String orderNo, Integer status, String tradeNo) {
        UpdateWrapper<Pay> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_no", orderNo)
                    .set("status", status)
                    .set("trade_no", tradeNo)
                    .set("update_time", LocalDateTime.now());
        
        if (status == 1) {
            updateWrapper.set("pay_time", LocalDateTime.now());
        }
        
        return update(updateWrapper);
    }
    
    @Override
    public Pay getByOrderNo(String orderNo) {
        LambdaQueryWrapper<Pay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Pay::getOrderNo, orderNo);
        return baseMapper.selectOne(queryWrapper);
    }
}