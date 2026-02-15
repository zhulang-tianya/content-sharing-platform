package com.content.recommend;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台推荐服务应用
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.content")
@EnableDiscoveryClient
@MapperScan("com.content.recommend.mapper")
public class ContentRecommendApplication {
    public static void main(String[] args) {
        log.info("推荐服务启动中...");
        SpringApplication.run(ContentRecommendApplication.class, args);
        log.info("推荐服务启动成功！");
    }
}