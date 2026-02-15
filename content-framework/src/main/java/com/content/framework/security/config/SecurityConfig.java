package com.content.framework.security.config;

import com.content.framework.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 通用安全配置
 * 供所有微服务使用的统一安全配置
 * 
 * 说明：此配置只提供基础的安全bean，不提供具体的认证配置
 * 具体的认证配置（如UserDetailsService）由各个业务服务自行配置
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}
