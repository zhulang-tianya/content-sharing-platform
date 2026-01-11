package com.content.queue.controller;

import com.content.common.result.Result;
import com.content.queue.producer.RabbitMQProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 消息队列控制器
 * 用于暴露消息队列服务的API接口
 */
@RestController
@RequestMapping("/api/queue")
@Tag(name = "消息队列服务", description = "消息队列服务接口")
public class QueueController {

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    /**
     * 发送消息
     * @param exchange 交换机名称
     * @param routingKey 路由键
     * @param message 消息内容
     * @return 结果
     */
    @PostMapping("/send")
    @Operation(summary = "发送消息", description = "发送消息到指定交换机和路由键")
    public Result sendMessage(@RequestParam String exchange, 
                             @RequestParam String routingKey, 
                             @RequestBody Object message) {
        rabbitMQProducer.sendMessage(exchange, routingKey, message);
        return Result.success("消息发送成功");
    }

    /**
     * 发送内容相关消息
     * @param routingKey 路由键
     * @param message 消息内容
     * @return 结果
     */
    @PostMapping("/send/content")
    @Operation(summary = "发送内容消息", description = "发送内容相关消息")
    public Result sendContentMessage(@RequestParam String routingKey, 
                                     @RequestBody Object message) {
        rabbitMQProducer.sendContentMessage(routingKey, message);
        return Result.success("内容消息发送成功");
    }

    /**
     * 发送用户相关消息
     * @param routingKey 路由键
     * @param message 消息内容
     * @return 结果
     */
    @PostMapping("/send/user")
    @Operation(summary = "发送用户消息", description = "发送用户相关消息")
    public Result sendUserMessage(@RequestParam String routingKey, 
                                 @RequestBody Object message) {
        rabbitMQProducer.sendUserMessage(routingKey, message);
        return Result.success("用户消息发送成功");
    }

    /**
     * 发送评论相关消息
     * @param routingKey 路由键
     * @param message 消息内容
     * @return 结果
     */
    @PostMapping("/send/comment")
    @Operation(summary = "发送评论消息", description = "发送评论相关消息")
    public Result sendCommentMessage(@RequestParam String routingKey, 
                                    @RequestBody Object message) {
        rabbitMQProducer.sendCommentMessage(routingKey, message);
        return Result.success("评论消息发送成功");
    }

    /**
     * 发送通知相关消息
     * @param routingKey 路由键
     * @param message 消息内容
     * @return 结果
     */
    @PostMapping("/send/notification")
    @Operation(summary = "发送通知消息", description = "发送通知相关消息")
    public Result sendNotificationMessage(@RequestParam String routingKey, 
                                         @RequestBody Object message) {
        rabbitMQProducer.sendNotificationMessage(routingKey, message);
        return Result.success("通知消息发送成功");
    }
}
