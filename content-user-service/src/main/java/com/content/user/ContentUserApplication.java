package com.content.user;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台用户服务应用
 * 负责用户服务的启动和配置
 */
@SpringBootApplication(scanBasePackages = "com.content")
@EnableDiscoveryClient
@MapperScan("com.content.user.mapper")
@Slf4j
public class ContentUserApplication {
    public static void main(String[] args) {
        log.info("内容分享平台用户服务启动中...");
        SpringApplication.run(ContentUserApplication.class, args);
        log.info("内容分享平台用户服务启动成功！");
    }
}