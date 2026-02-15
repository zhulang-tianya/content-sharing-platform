package com.content.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.content.pay.entity.Pay;

import java.util.List;

/**
 * 支付记录服务
 */
public interface PayService extends IService<Pay> {
    
    /**
     * 根据用户ID获取支付记录列表
     * @param userId 用户ID
     * @return 支付记录列表
     */
    List<Pay> getByUserId(Long userId);
    
    /**
     * 创建支付订单
     * @param pay 支付订单对象
     * @return 是否成功
     */
    boolean createPay(Pay pay);
    
    /**
     * 更新支付状态
     * @param orderNo 支付订单号
     * @param status 支付状态
     * @param tradeNo 交易流水号
     * @return 是否成功
     */
    boolean updatePayStatus(String orderNo, Integer status, String tradeNo);
    
    /**
     * 根据订单号获取支付记录
     * @param orderNo 支付订单号
     * @return 支付记录
     */
    Pay getByOrderNo(String orderNo);
}