package com.content.comment;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容分享平台评论服务应用
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.content")
@EnableDiscoveryClient
@MapperScan("com.content.comment.mapper")
public class ContentCommentApplication {
    public static void main(String[] args) {
        log.info("评论服务启动中...");
        SpringApplication.run(ContentCommentApplication.class, args);
        log.info("评论服务启动成功！");
    }
}