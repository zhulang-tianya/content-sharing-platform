package com.content.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.pay.entity.Pay;
import com.content.pay.mapper.PayMapper;
import com.content.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 支付记录服务实现
 */
@Slf4j
@Service
public class PayServiceImpl extends ServiceImpl<PayMapper, Pay> implements PayService {
    
    /**
     * 根据用户ID查询支付记录列表
     * 
     * @param userId 用户ID
     * @return 支付记录列表
     */
    @Override
    public List<Pay> getByUserId(Long userId) {
        log.debug("根据用户ID查询支付记录，userId: {}", userId);
        LambdaQueryWrapper<Pay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Pay::getUserId, userId)
                    .orderByDesc(Pay::getCreateTime);
        List<Pay> pays = baseMapper.selectList(queryWrapper);
        log.debug("查询到支付记录数量: {}", pays != null ? pays.size() : 0);
        return pays;
    }
    
    /**
     * 创建支付订单
     * 
     * @param pay 支付订单对象
     * @return 是否创建成功
     */
    @Override
    public boolean createPay(Pay pay) {
        log.debug("创建支付订单，pay: {}", pay);
        pay.setCreateTime(LocalDateTime.now());
        pay.setUpdateTime(LocalDateTime.now());
        pay.setStatus(0);
        boolean result = save(pay);
        log.debug("创建支付订单结果: {}", result);
        return result;
    }
    
    /**
     * 更新支付状态
     * 
     * @param orderNo 订单号
     * @param status 支付状态（0-待支付 1-已支付 2-支付失败 3-已退款）
     * @param tradeNo 交易流水号
     * @return 是否更新成功
     */
    @Override
    public boolean updatePayStatus(String orderNo, Integer status, String tradeNo) {
        log.debug("更新支付状态，orderNo: {}, status: {}, tradeNo: {}", orderNo, status, tradeNo);
        UpdateWrapper<Pay> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_no", orderNo)
                    .set("status", status)
                    .set("trade_no", tradeNo)
                    .set("update_time", LocalDateTime.now());
        
        if (status == 1) {
            updateWrapper.set("pay_time", LocalDateTime.now());
        }
        
        boolean result = update(updateWrapper);
        log.debug("更新支付状态结果: {}", result);
        return result;
    }
    
    /**
     * 根据订单号查询支付记录
     * 
     * @param orderNo 订单号
     * @return 支付记录
     */
    @Override
    public Pay getByOrderNo(String orderNo) {
        log.debug("根据订单号查询支付记录，orderNo: {}", orderNo);
        LambdaQueryWrapper<Pay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Pay::getOrderNo, orderNo);
        Pay pay = baseMapper.selectOne(queryWrapper);
        log.debug("查询到支付记录: {}", pay != null ? "存在" : "不存在");
        return pay;
    }
}