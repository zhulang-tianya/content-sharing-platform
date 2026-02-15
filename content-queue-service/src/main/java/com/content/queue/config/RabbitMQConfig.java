package com.content.queue.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 配置类
 * 用于声明队列、交换机和绑定关系
 */
@Configuration
public class RabbitMQConfig {

    // 交换机名称
    public static final String CONTENT_EXCHANGE = "content_exchange";
    
    // 队列名称
    public static final String CONTENT_QUEUE = "content_queue";
    public static final String USER_QUEUE = "user_queue";
    public static final String COMMENT_QUEUE = "comment_queue";
    public static final String NOTIFICATION_QUEUE = "notification_queue";
    
    // 路由键
    public static final String CONTENT_ROUTING_KEY = "content.#";
    public static final String USER_ROUTING_KEY = "user.#";
    public static final String COMMENT_ROUTING_KEY = "comment.#";
    public static final String NOTIFICATION_ROUTING_KEY = "notification.#";

    /**
     * 声明交换机
     */
    @Bean
    public TopicExchange contentExchange() {
        return new TopicExchange(CONTENT_EXCHANGE, true, false);
    }

    /**
     * 声明内容队列
     */
    @Bean
    public Queue contentQueue() {
        return QueueBuilder.durable(CONTENT_QUEUE).build();
    }

    /**
     * 声明用户队列
     */
    @Bean
    public Queue userQueue() {
        return QueueBuilder.durable(USER_QUEUE).build();
    }

    /**
     * 声明评论队列
     */
    @Bean
    public Queue commentQueue() {
        return QueueBuilder.durable(COMMENT_QUEUE).build();
    }

    /**
     * 声明通知队列
     */
    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE).build();
    }

    /**
     * 绑定内容队列到交换机
     */
    @Bean
    public Binding contentBinding(Queue contentQueue, TopicExchange contentExchange) {
        return BindingBuilder.bind(contentQueue).to(contentExchange).with(CONTENT_ROUTING_KEY);
    }

    /**
     * 绑定用户队列到交换机
     */
    @Bean
    public Binding userBinding(Queue userQueue, TopicExchange contentExchange) {
        return BindingBuilder.bind(userQueue).to(contentExchange).with(USER_ROUTING_KEY);
    }

    /**
     * 绑定评论队列到交换机
     */
    @Bean
    public Binding commentBinding(Queue commentQueue, TopicExchange contentExchange) {
        return BindingBuilder.bind(commentQueue).to(contentExchange).with(COMMENT_ROUTING_KEY);
    }

    /**
     * 绑定通知队列到交换机
     */
    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange contentExchange) {
        return BindingBuilder.bind(notificationQueue).to(contentExchange).with(NOTIFICATION_ROUTING_KEY);
    }
}
