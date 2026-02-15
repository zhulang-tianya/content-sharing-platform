package com.content.pay;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台支付服务应用
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.content")
@EnableDiscoveryClient
@MapperScan("com.content.pay.mapper")
public class ContentPayApplication {
    public static void main(String[] args) {
        log.info("支付服务启动中...");
        SpringApplication.run(ContentPayApplication.class, args);
        log.info("支付服务启动成功！");
    }
}