package com.content.comment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台评论服务应用
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.content.comment.mapper")
public class ContentCommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentCommentApplication.class, args);
    }
}