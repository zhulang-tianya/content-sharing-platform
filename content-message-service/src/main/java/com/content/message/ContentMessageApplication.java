package com.content.message;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台消息服务应用
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.content")
@EnableDiscoveryClient
@MapperScan("com.content.message.mapper")
public class ContentMessageApplication {
    public static void main(String[] args) {
        log.info("消息服务启动中...");
        SpringApplication.run(ContentMessageApplication.class, args);
        log.info("消息服务启动成功！");
    }
}