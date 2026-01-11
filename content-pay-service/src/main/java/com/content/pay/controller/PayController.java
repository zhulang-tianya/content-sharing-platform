package com.content.pay.controller;

import com.content.common.result.Result;
import com.content.pay.entity.Pay;
import com.content.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付记录控制器
 */
@RestController
@RequestMapping("/pay")
public class PayController {
    
    @Autowired
    private PayService payService;
    
    /**
     * 根据用户ID获取支付记录列表
     * @param userId 用户ID
     * @return 支付记录列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<Pay>> getByUserId(@PathVariable Long userId) {
        List<Pay> list = payService.getByUserId(userId);
        return Result.success(list);
    }
    
    /**
     * 创建支付订单
     * @param pay 支付订单对象
     * @return 操作结果
     */
    @PostMapping
    public Result<Boolean> createPay(@RequestBody Pay pay) {
        boolean success = payService.createPay(pay);
        return Result.success(success);
    }
    
    /**
     * 更新支付状态
     * @param orderNo 支付订单号
     * @param status 支付状态
     * @param tradeNo 交易流水号
     * @return 操作结果
     */
    @PutMapping("/status")
    public Result<Boolean> updatePayStatus(@RequestParam String orderNo, 
                                           @RequestParam Integer status, 
                                           @RequestParam(required = false) String tradeNo) {
        boolean success = payService.updatePayStatus(orderNo, status, tradeNo);
        return Result.success(success);
    }
    
    /**
     * 根据订单号获取支付记录
     * @param orderNo 支付订单号
     * @return 支付记录
     */
    @GetMapping("/order/{orderNo}")
    public Result<Pay> getByOrderNo(@PathVariable String orderNo) {
        Pay pay = payService.getByOrderNo(orderNo);
        return Result.success(pay);
    }
    
    /**
     * 获取所有支付记录列表
     * @return 支付记录列表
     */
    @GetMapping
    public Result<List<Pay>> getAll() {
        List<Pay> list = payService.list();
        return Result.success(list);
    }
}