package com.content.stat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台统计服务应用
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.content.stat.mapper")
public class ContentStatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentStatApplication.class, args);
    }
}