package com.content.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台搜索服务应用
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.content.search.mapper")
public class ContentSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentSearchApplication.class, args);
    }
}