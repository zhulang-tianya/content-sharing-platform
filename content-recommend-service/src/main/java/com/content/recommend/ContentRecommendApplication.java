package com.content.recommend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台推荐服务应用
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.content.recommend.mapper")
public class ContentRecommendApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentRecommendApplication.class, args);
    }
}