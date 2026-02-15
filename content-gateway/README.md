# Content Gateway Module

## 功能介绍
content-gateway是内容分享平台的API网关，负责处理所有外部请求的路由、负载均衡、限流等功能，是系统的统一入口。主要提供以下功能：

1. **请求路由**：将外部请求路由到对应的微服务
2. **负载均衡**：对多个服务实例进行负载均衡
3. **请求过滤**：对请求进行前置处理和后置处理
4. **统一认证**：提供统一的认证入口
5. **限流熔断**：保护后端服务，防止过载

## 目录结构
```
content-gateway/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── content/
│   │   │           └── gateway/
│   │   │               └── ContentGatewayApplication.java  # 网关应用启动类
│   │   └── resources/
│   │       └── application.yml                           # 网关配置文件
│   └── test/
└── pom.xml                                                # Maven配置文件
```

## 技术栈
- Spring Boot 3.2.5
- Spring Cloud Gateway 4.1.3
- Spring Cloud Alibaba Nacos 2023.0.1.0

## 主要配置

### 1. 服务发现配置
```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
        namespace: public
```

### 2. 路由配置
网关配置了以下路由规则：
- `/api/user/**` → content-user-service
- `/api/content/**` → content-content-service
- `/api/comment/**` → content-comment-service
- `/api/recommend/**` → content-recommend-service
- `/api/pay/**` → content-pay-service
- `/api/message/**` → content-message-service
- `/api/search/**` → content-search-service
- `/api/stat/**` → content-stat-service

## 启动说明
1. 确保Nacos服务已启动
2. 运行ContentGatewayApplication.java
3. 网关服务默认端口：8080

## API文档
网关集成了Knife4j，可通过以下地址访问API文档：
- http://localhost:8080/doc.html
