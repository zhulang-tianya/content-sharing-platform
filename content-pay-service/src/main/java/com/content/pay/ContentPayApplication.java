package com.content.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台支付服务应用
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.content.pay.mapper")
public class ContentPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentPayApplication.class, args);
    }
}