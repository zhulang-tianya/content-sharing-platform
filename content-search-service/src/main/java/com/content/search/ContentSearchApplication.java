package com.content.search;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台搜索服务应用
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.content")
@EnableDiscoveryClient
@MapperScan("com.content.search.mapper")
public class ContentSearchApplication {
    public static void main(String[] args) {
        log.info("搜索服务启动中...");
        SpringApplication.run(ContentSearchApplication.class, args);
        log.info("搜索服务启动成功！");
    }
}