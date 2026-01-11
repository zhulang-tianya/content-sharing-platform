package com.content.queue.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RabbitMQ 消费者类
 * 用于接收和处理消息队列中的消息
 */
@Slf4j
@Component
public class RabbitMQConsumer {

    /**
     * 处理内容相关消息
     * 
     * @param message 消息内容
     * @param channel 通道
     * @param amqpMessage 原始消息
     */
    @RabbitListener(queues = "content_queue")
    public void handleContentMessage(String message, Channel channel, Message amqpMessage) {
        log.debug("接收到内容相关消息: {}", message);
        try {
            // 处理消息
            log.info("处理内容相关消息: {}", message);
            // TODO: 实现具体的业务逻辑
            
            // 确认消息已处理
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
            log.debug("内容相关消息处理成功并确认");
        } catch (Exception e) {
            log.error("处理内容相关消息失败", e);
            try {
                // 拒绝消息并重新入队
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
                log.debug("内容相关消息处理失败，已拒绝并重新入队");
            } catch (IOException ex) {
                log.error("拒绝消息失败", ex);
            }
        }
    }

    /**
     * 处理用户相关消息
     * 
     * @param message 消息内容
     * @param channel 通道
     * @param amqpMessage 原始消息
     */
    @RabbitListener(queues = "user_queue")
    public void handleUserMessage(String message, Channel channel, Message amqpMessage) {
        log.debug("接收到用户相关消息: {}", message);
        try {
            // 处理消息
            log.info("处理用户相关消息: {}", message);
            // TODO: 实现具体的业务逻辑
            
            // 确认消息已处理
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
            log.debug("用户相关消息处理成功并确认");
        } catch (Exception e) {
            log.error("处理用户相关消息失败", e);
            try {
                // 拒绝消息并重新入队
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
                log.debug("用户相关消息处理失败，已拒绝并重新入队");
            } catch (IOException ex) {
                log.error("拒绝消息失败", ex);
            }
        }
    }

    /**
     * 处理评论相关消息
     * 
     * @param message 消息内容
     * @param channel 通道
     * @param amqpMessage 原始消息
     */
    @RabbitListener(queues = "comment_queue")
    public void handleCommentMessage(String message, Channel channel, Message amqpMessage) {
        log.debug("接收到评论相关消息: {}", message);
        try {
            // 处理消息
            log.info("处理评论相关消息: {}", message);
            // TODO: 实现具体的业务逻辑
            
            // 确认消息已处理
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
            log.debug("评论相关消息处理成功并确认");
        } catch (Exception e) {
            log.error("处理评论相关消息失败", e);
            try {
                // 拒绝消息并重新入队
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
                log.debug("评论相关消息处理失败，已拒绝并重新入队");
            } catch (IOException ex) {
                log.error("拒绝消息失败", ex);
            }
        }
    }

    /**
     * 处理通知相关消息
     * 
     * @param message 消息内容
     * @param channel 通道
     * @param amqpMessage 原始消息
     */
    @RabbitListener(queues = "notification_queue")
    public void handleNotificationMessage(String message, Channel channel, Message amqpMessage) {
        log.debug("接收到通知相关消息: {}", message);
        try {
            // 处理消息
            log.info("处理通知相关消息: {}", message);
            // TODO: 实现具体的业务逻辑
            
            // 确认消息已处理
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
            log.debug("通知相关消息处理成功并确认");
        } catch (Exception e) {
            log.error("处理通知相关消息失败", e);
            try {
                // 拒绝消息并重新入队
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
                log.debug("通知相关消息处理失败，已拒绝并重新入队");
            } catch (IOException ex) {
                log.error("拒绝消息失败", ex);
            }
        }
    }
}
