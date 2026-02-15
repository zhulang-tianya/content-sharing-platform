package com.content.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 消息队列服务主应用类
 * 用于管理内容分享平台的消息队列
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.content")
@EnableDiscoveryClient
@EnableScheduling
public class ContentQueueApplication {

    public static void main(String[] args) {
        log.info("队列服务启动中...");
        SpringApplication.run(ContentQueueApplication.class, args);
        log.info("队列服务启动成功！");
    }

}