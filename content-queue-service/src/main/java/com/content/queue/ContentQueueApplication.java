package com.content.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 消息队列服务主应用类
 * 用于管理内容分享平台的消息队列
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class ContentQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentQueueApplication.class, args);
    }

}