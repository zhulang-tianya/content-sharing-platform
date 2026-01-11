package com.content.queue.producer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ 生产者类
 * 用于发送消息到消息队列
 */
@Slf4j
@Component
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * 
     * @param exchange 交换机名称
     * @param routingKey 路由键
     * @param message 消息内容
     */
    public void sendMessage(String exchange, String routingKey, Object message) {
        log.debug("发送消息到交换机，exchange: {}, routingKey: {}, message: {}", exchange, routingKey, message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        log.debug("消息发送成功");
    }

    /**
     * 发送内容相关消息
     * 
     * @param routingKey 路由键
     * @param message 消息内容
     */
    public void sendContentMessage(String routingKey, Object message) {
        log.debug("发送内容相关消息，routingKey: {}, message: {}", routingKey, message);
        rabbitTemplate.convertAndSend("content_exchange", routingKey, message);
        log.debug("内容相关消息发送成功");
    }

    /**
     * 发送用户相关消息
     * 
     * @param routingKey 路由键
     * @param message 消息内容
     */
    public void sendUserMessage(String routingKey, Object message) {
        log.debug("发送用户相关消息，routingKey: {}, message: {}", routingKey, message);
        rabbitTemplate.convertAndSend("content_exchange", routingKey, message);
        log.debug("用户相关消息发送成功");
    }

    /**
     * 发送评论相关消息
     * 
     * @param routingKey 路由键
     * @param message 消息内容
     */
    public void sendCommentMessage(String routingKey, Object message) {
        log.debug("发送评论相关消息，routingKey: {}, message: {}", routingKey, message);
        rabbitTemplate.convertAndSend("content_exchange", routingKey, message);
        log.debug("评论相关消息发送成功");
    }

    /**
     * 发送通知相关消息
     * 
     * @param routingKey 路由键
     * @param message 消息内容
     */
    public void sendNotificationMessage(String routingKey, Object message) {
        log.debug("发送通知相关消息，routingKey: {}, message: {}", routingKey, message);
        rabbitTemplate.convertAndSend("content_exchange", routingKey, message);
        log.debug("通知相关消息发送成功");
    }
}
