package com.content.stat;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台统计服务应用
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.content")
@EnableDiscoveryClient
@MapperScan("com.content.stat.mapper")
public class ContentStatApplication {
    public static void main(String[] args) {
        log.info("统计服务启动中...");
        SpringApplication.run(ContentStatApplication.class, args);
        log.info("统计服务启动成功！");
    }
}