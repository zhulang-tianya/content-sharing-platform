package com.content.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台内容服务应用
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.content.content.mapper")
public class ContentContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentContentApplication.class, args);
    }
}