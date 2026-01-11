package com.content.queue.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RabbitMQ 消费者类
 * 用于接收和处理消息队列中的消息
 */
@Component
public class RabbitMQConsumer {

    /**
     * 处理内容相关消息
     * @param message 消息内容
     * @param channel 通道
     * @param amqpMessage 原始消息
     */
    @RabbitListener(queues = "content_queue")
    public void handleContentMessage(String message, Channel channel, Message amqpMessage) {
        try {
            // 处理消息
            System.out.println("收到内容消息: " + message);
            // TODO: 实现具体的业务逻辑
            
            // 确认消息已处理
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            try {
                // 拒绝消息并重新入队
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 处理用户相关消息
     * @param message 消息内容
     * @param channel 通道
     * @param amqpMessage 原始消息
     */
    @RabbitListener(queues = "user_queue")
    public void handleUserMessage(String message, Channel channel, Message amqpMessage) {
        try {
            // 处理消息
            System.out.println("收到用户消息: " + message);
            // TODO: 实现具体的业务逻辑
            
            // 确认消息已处理
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            try {
                // 拒绝消息并重新入队
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 处理评论相关消息
     * @param message 消息内容
     * @param channel 通道
     * @param amqpMessage 原始消息
     */
    @RabbitListener(queues = "comment_queue")
    public void handleCommentMessage(String message, Channel channel, Message amqpMessage) {
        try {
            // 处理消息
            System.out.println("收到评论消息: " + message);
            // TODO: 实现具体的业务逻辑
            
            // 确认消息已处理
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            try {
                // 拒绝消息并重新入队
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 处理通知相关消息
     * @param message 消息内容
     * @param channel 通道
     * @param amqpMessage 原始消息
     */
    @RabbitListener(queues = "notification_queue")
    public void handleNotificationMessage(String message, Channel channel, Message amqpMessage) {
        try {
            // 处理消息
            System.out.println("收到通知消息: " + message);
            // TODO: 实现具体的业务逻辑
            
            // 确认消息已处理
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            try {
                // 拒绝消息并重新入队
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
