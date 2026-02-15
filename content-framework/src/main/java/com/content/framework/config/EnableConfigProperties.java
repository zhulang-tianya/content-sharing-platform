package com.content.framework.config;

import com.content.common.generator.config.CodeGeneratorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置属性启用类
 * 用于启用配置属性绑定
 */
@Configuration
@EnableConfigurationProperties(CodeGeneratorProperties.class)
public class EnableConfigProperties {
}