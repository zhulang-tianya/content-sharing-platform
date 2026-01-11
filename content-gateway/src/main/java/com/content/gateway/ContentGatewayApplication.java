package com.content.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台API网关应用
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@EnableDiscoveryClient
public class ContentGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentGatewayApplication.class, args);
    }
}