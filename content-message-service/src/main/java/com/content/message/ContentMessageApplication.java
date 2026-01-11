package com.content.message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台消息服务应用
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.content.message.mapper")
public class ContentMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentMessageApplication.class, args);
    }
}