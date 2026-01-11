# 内容分享平台后端服务

## 1. 项目简介

内容分享平台后端服务是基于Spring Cloud Alibaba构建的微服务架构平台，提供小说、音频、视频等多种内容形式的分享服务，包括内容管理、用户管理、评论互动、推荐系统等核心功能模块，支持多种内容类型的扩展和集成。

## 2. 技术栈

| 分类 | 技术 | 版本 | 说明 |
|------|------|------|------|
| 核心框架 | Spring Boot | 3.2.5 | 应用开发框架 |
| 微服务框架 | Spring Cloud | 2023.0.2 | 微服务开发框架 |
| 微服务组件 | Spring Cloud Alibaba | 2023.0.1.0 | 阿里微服务生态组件 |
| 安全框架 | Spring Security | 6.2.4 | 安全认证与授权框架 |
| 认证方案 | JWT | - | 无状态认证方案 |
| 服务注册与配置 | Nacos | 2.4.1 | 服务注册发现与配置中心 |
| API网关 | Spring Cloud Gateway | 4.1.3 | API网关服务 |
| RPC框架 | Dubbo | 3.3.0 | 高性能RPC框架 |
| 文档工具 | Knife4j | 4.5.0 | API文档生成工具 |
| 数据库连接池 | Druid | 1.2.20 | 高性能数据库连接池 |
| ORM框架 | MyBatis Plus | 3.5.5 | 增强型ORM框架 |
| 数据库 | MySQL | 8.3.0 | 关系型数据库 |
| 缓存 | Redis | 7.2.4 | 分布式缓存 |
| 消息队列 | RocketMQ | 5.2.0 | 分布式消息队列 |
| 流量控制 | Sentinel | 1.9.8 | 流量控制与熔断降级 |
| 分布式事务 | Seata | 1.7.2 | 分布式事务解决方案 |

## 3. 系统架构

### 3.1 架构图

```
┌─────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                      客户端层                                                 │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────────┐                       │
│  │  Web    │  │  App    │  │  H5     │  │  管理后台 │  │  内容创作者后台  │                   │
│  └─────────┘  └─────────┘  └─────────┘  └─────────┘  └─────────────┘                       │
└───────────────────────────────────┬─────────────────────────────────────────────────────────┘
                                    │
┌───────────────────────────────────▼─────────────────────────────────────────────────────────┐
│                                      API网关层                                             │
│  ┌───────────────────────────────────────────────────────────────────────────────────┐     │
│  │                     content-gateway                                            │     │
│  │  - 请求路由           - 负载均衡           - 限流熔断           - 灰度发布     │     │
│  └───────────────────────────────────────────────────────────────────────────────────┘     │
└───────────────────────────────────┬─────────────────────────────────────────────────────────┘
                                    │
┌───────────────────────────────────▼─────────────────────────────────────────────────────────┐
│                                      服务层                                               │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐       │
│  │   用户服务      │  │   内容服务      │  │   评论服务      │  │   推荐服务      │       │
│  │ content-user-service │ content-content-service │ content-comment-service │ content-recommend-service │       │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  └─────────────────┘       │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐       │
│  │   支付服务      │  │   消息服务      │  │   搜索服务      │  │   统计服务      │       │
│  │ content-pay-service │ content-message-service │ content-search-service │ content-stat-service │       │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  └─────────────────┘       │
└───────────────────────────────────┬─────────────────────────────────────────────────────────┘
                                    │
┌───────────────────────────────────▼─────────────────────────────────────────────────────────┐
│                                      中间件层                                           │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐ │
│  │  Nacos │  │Sentinel │  │RocketMQ │  │  Seata  │  │  MySQL  │  │ Elasticsearch │  │ Redis  │ │
│  └─────────┘  └─────────┘  └─────────┘  └─────────┘  └─────────┘  └───────────────┘  └─────────┘ │
└─────────────────────────────────────────────────────────────────────────────────────────────────┘
```

### 3.2 模块划分

| 模块 | 名称 | 端口 | 说明 |
|------|------|------|------|
| content-framework | 核心框架模块 | - | 安全配置、切面、拦截器、工具类等 |
| content-common | 公共模块 | - | 常量、枚举、异常、工具类等 |
| content-gateway | API网关 | 8080 | 请求路由、负载均衡、限流熔断、灰度发布 |
| content-user-service | 用户服务 | 8081 | 用户管理、认证授权、权限管理 |
| content-content-service | 内容服务 | 8082 | 小说、音频、视频等内容管理 |
| content-comment-service | 评论服务 | 8083 | 评论管理、互动功能 |
| content-recommend-service | 推荐服务 | 8084 | 个性化推荐、内容推荐 |
| content-pay-service | 支付服务 | 8085 | 支付管理、订单管理 |
| content-message-service | 消息服务 | 8086 | 消息通知、推送服务 |
| content-search-service | 搜索服务 | 8087 | 全文搜索、模糊搜索 |
| content-stat-service | 统计服务 | 8088 | 数据统计、报表分析 |

## 4. 环境准备

### 4.1 基础环境

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Nacos 2.4.1+
- RocketMQ 5.2.0+
- Seata 1.7.2+
- Redis 7.0+
- Elasticsearch 7.17+ (可选，用于搜索服务)

### 4.2 核心功能

- **用户管理**：用户注册、登录、权限管理、个人信息管理
- **内容管理**：小说、音频、视频等多种内容形式的管理
- **评论互动**：用户评论、点赞、收藏等互动功能
- **推荐系统**：基于用户行为的个性化内容推荐
- **支付系统**：内容付费、会员订阅等支付功能
- **消息系统**：站内信、通知推送等消息功能
- **搜索系统**：全文搜索、模糊搜索等搜索功能
- **统计系统**：数据统计、报表分析等统计功能

### 4.2 环境变量

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| NACOS_ADDR | Nacos地址 | localhost:8848 |
| NACOS_NAMESPACE | Nacos命名空间 | public |
| NACOS_GROUP | Nacos配置分组 | DEFAULT_GROUP |
| DB_HOST | 数据库地址 | localhost |
| DB_PORT | 数据库端口 | 3306 |
| DB_NAME | 数据库名称 | content_sharing |
| DB_USERNAME | 数据库用户名 | root |
| DB_PASSWORD | 数据库密码 | root |
| REDIS_HOST | Redis地址 | localhost |
| REDIS_PORT | Redis端口 | 6379 |
| ES_HOST | Elasticsearch地址 | localhost |
| ES_PORT | Elasticsearch端口 | 9200 |

## 5. 快速开始

### 5.1 编译项目

```bash
# 进入项目根目录
cd context-platform

# 编译项目
mvn clean install -DskipTests
```

### 5.2 启动中间件

```bash
# 启动Nacos服务
nacos/bin/startup.sh -m standalone

# 启动MySQL服务
# 启动Redis服务
# 启动Elasticsearch服务
# 启动RocketMQ服务
```

### 5.3 启动服务

按照以下顺序启动服务：

1. **API网关服务**
```bash
cd content-gateway
mvn spring-boot:run
```

2. **用户服务**
```bash
cd content-user-service
mvn spring-boot:run
```

3. **内容服务**
```bash
cd content-content-service
mvn spring-boot:run
```

4. **评论服务**
```bash
cd content-comment-service
mvn spring-boot:run
```

5. **推荐服务**
```bash
cd content-recommend-service
mvn spring-boot:run
```

6. **其他服务（可选）**
```bash
# 启动支付服务
cd content-pay-service
mvn spring-boot:run

# 启动消息服务
cd content-message-service
mvn spring-boot:run

# 启动搜索服务
cd content-search-service
mvn spring-boot:run

# 启动统计服务
cd content-stat-service
mvn spring-boot:run
```

### 5.4 访问服务

- API网关地址：http://localhost:8080
- Knife4j文档：http://localhost:8080/doc.html
- Nacos控制台：http://localhost:8848/nacos

## 6. 配置说明

### 6.1 Nacos配置

所有服务的配置都通过Nacos统一管理，配置文件格式为YAML，命名规则：

```
{service-name}-{profile}.yaml
```

例如：
- content-gateway-dev.yaml
- content-user-service-dev.yaml
- content-content-service-dev.yaml

### 6.2 数据库配置

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:content_sharing}?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:root}
    druid:
      initial-size: 5
      max-active: 20
      min-idle: 5
      max-wait: 60000
      validation-query: SELECT 1
      test-on-borrow: false
      test-on-return: false
      test-while-idle: true
      time-between-eviction-runs-millis: 60000
      min-evictable-idle-time-millis: 300000
      filters: stat,wall,slf4j
      pool-prepared-statements: true
      max-pool-prepared-statement-per-connection-size: 20
      use-global-data-source-stat: true
```

### 6.3 Dubbo配置

```yaml
dubbo:
  application:
    name: ${spring.application.name}
  protocol:
    name: dubbo
    port: -1
  registry:
    address: nacos://${NACOS_ADDR:localhost:8848}?namespace=${NACOS_NAMESPACE:public}
  scan:
    base-packages: com.content.**.dubbo
```

## 7. API文档

### 7.1 Knife4j访问

启动服务后，访问以下地址查看API文档：

```
http://localhost:8080/doc.html
```

### 7.2 API分组

- 公共API：无需认证即可访问
- 用户API：需要用户登录认证
- 管理员API：需要管理员权限
- 创作者API：需要内容创作者权限

## 8. 开发规范

### 8.1 代码规范

- 遵循Java编码规范
- 类名采用大驼峰命名法
- 方法名、变量名采用小驼峰命名法
- 常量名全部大写，单词间用下划线分隔
- 方法参数和返回值添加文档注释
- 包名采用小写，单词间用点分隔

### 8.2 微服务规范

- 每个服务独立部署、独立扩展
- 服务间通过Dubbo或HTTP REST通信
- 服务接口设计遵循RESTful规范
- 服务异常统一处理，返回标准格式
- 服务接口版本化管理

### 8.3 数据库规范

- 表名采用下划线命名法，前缀为模块名称
- 字段名采用下划线命名法
- 主键采用自增ID或UUID
- 索引设计合理，避免全表扫描
- 外键约束明确

## 9. 部署说明

### 9.1 单机部署

1. 安装并启动Nacos、MySQL、Redis、Elasticsearch、RocketMQ等中间件
2. 编译项目，生成jar包
3. 依次启动各个服务

### 9.2 Docker部署

```bash
# 构建镜像
docker build -t content-sharing-platform .

# 启动容器
docker run -d -p 8080:8080 content-sharing-platform
```

### 9.3 Kubernetes部署

```bash
# 部署中间件
kubectl apply -f k8s/nacos.yaml
kubectl apply -f k8s/mysql.yaml
kubectl apply -f k8s/redis.yaml
kubectl apply -f k8s/elasticsearch.yaml
kubectl apply -f k8s/rocketmq.yaml

# 部署服务
kubectl apply -f k8s/content-gateway.yaml
kubectl apply -f k8s/content-user-service.yaml
kubectl apply -f k8s/content-content-service.yaml
kubectl apply -f k8s/content-comment-service.yaml
kubectl apply -f k8s/content-recommend-service.yaml
```

## 10. 监控与日志

### 10.1 服务监控

- 使用Spring Boot Actuator暴露监控端点
- 集成Prometheus和Grafana进行监控
- 监控指标包括：CPU、内存、磁盘、网络、请求量、响应时间等
- 使用Sentinel Dashboard进行流量监控和规则配置

### 10.2 日志管理

- 使用SLF4J + Logback进行日志管理
- 日志级别：ERROR > WARN > INFO > DEBUG > TRACE
- 日志格式：包含时间、日志级别、线程名、类名、日志内容、请求ID等
- 日志文件按天滚动，保留7天
- 接入ELK进行日志收集和分析

## 11. 常见问题

### 11.1 服务无法注册到Nacos

- 检查Nacos服务是否正常运行
- 检查服务配置中的Nacos地址是否正确
- 检查服务命名空间和分组是否正确

### 11.2 API文档无法访问

- 检查API网关服务是否正常运行
- 检查Knife4j配置是否正确
- 检查防火墙是否开放端口

### 11.3 数据库连接失败

- 检查数据库服务是否正常运行
- 检查数据库连接配置是否正确
- 检查数据库用户权限是否正确

### 11.4 Elasticsearch连接失败

- 检查Elasticsearch服务是否正常运行
- 检查Elasticsearch配置是否正确
- 检查防火墙是否开放端口

## 12. 贡献指南

1. Fork本项目
2. 创建特性分支：`git checkout -b feature/xxx`
3. 提交代码：`git commit -m 'Add some feature'`
4. 推送分支：`git push origin feature/xxx`
5. 提交Pull Request

## 13. 版本历史

| 版本 | 日期 | 说明 |
|------|------|------|
| 1.0.0-SNAPSHOT | 2026-01-03 | 初始版本 |

## 14. 许可证

本项目采用Apache License 2.0许可证，详见LICENSE文件。

## 15. 联系方式

- 项目地址：https://github.com/content-sharing
- 问题反馈：https://github.com/content-sharing/issues
- 邮箱：contact@contentsharing.com