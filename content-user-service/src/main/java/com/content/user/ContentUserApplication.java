package com.content.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台用户服务应用
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.content.user.mapper")
public class ContentUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentUserApplication.class, args);
    }
}