# Nacos 共用配置文件指南

请在 Nacos 控制台中创建以下配置：

## 配置信息

### 1. 开发环境配置
- Data ID: `common-dev.yml`
- Group: `DEFAULT_GROUP`
- 配置格式: `YAML`

### 2. 测试环境配置
- Data ID: `common-test.yml`
- Group: `DEFAULT_GROUP`
- 配置格式: `YAML`

### 3. 生产环境配置
- Data ID: `common-prod.yml`
- Group: `DEFAULT_GROUP`
- 配置格式: `YAML`

---

## 开发环境配置内容 (common-dev.yml)

```yaml
# Spring配置
spring:
  # 数据源配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.1.103:3306/ruoyi-flex?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: mysql654321
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      initial-size: 5
      min-idle: 5
      max-active: 20
      max-wait: 60000
      time-between-eviction-runs-millis: 60000
      min-evictable-idle-time-millis: 300000
      validation-query: SELECT 1 FROM DUAL
      test-while-idle: true
      test-on-borrow: false
      test-on-return: false
      pool-prepared-statements: true
      max-pool-prepared-statement-per-connection-size: 20
      filters: stat,wall,log4j2
      connection-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000

  # Redis配置
  data:
    redis:
      host: 192.168.1.103
      port: 6379
      password: redis654321
      database: 0
      timeout: 5000
      lettuce:
        pool:
          max-active: 20
          max-idle: 10
          min-idle: 5
          max-wait: 2000

  # RabbitMQ配置
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    listener:
      simple:
        acknowledge-mode: manual
        concurrency: 5
        max-concurrency: 10
      direct:
        acknowledge-mode: manual

# MyBatis Plus配置
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
  global-config:
    db-config:
      id-type: auto

# JWT配置
jwt:
  # 密钥
  secret: content_sharing_platform_jwt_secret_key_2026
  # 过期时间（秒）
  expiration: 7200
  # 刷新时间（秒）
  refreshTime: 14400
  # 令牌前缀
  tokenPrefix: Bearer
  # 令牌头
  tokenHeader: Authorization

# 日志配置
logging:
  level:
    root: info
    com.content: debug
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/${spring.application.name}.log
```

---

## 测试环境配置内容 (common-test.yml)

```yaml
# Spring配置
spring:
  # 数据源配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.1.103:3306/ruoyi-flex?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: mysql654321
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      initial-size: 5
      min-idle: 5
      max-active: 20
      max-wait: 60000
      time-between-eviction-runs-millis: 60000
      min-evictable-idle-time-millis: 300000
      validation-query: SELECT 1 FROM DUAL
      test-while-idle: true
      test-on-borrow: false
      test-on-return: false
      pool-prepared-statements: true
      max-pool-prepared-statement-per-connection-size: 20
      filters: stat,wall,log4j2
      connection-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000

  # Redis配置
  data:
    redis:
      host: 192.168.1.103
      port: 6379
      password: redis654321
      database: 0
      timeout: 5000
      lettuce:
        pool:
          max-active: 20
          max-idle: 10
          min-idle: 5
          max-wait: 2000

  # RabbitMQ配置
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    listener:
      simple:
        acknowledge-mode: manual
        concurrency: 5
        max-concurrency: 10
      direct:
        acknowledge-mode: manual

# MyBatis Plus配置
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
  global-config:
    db-config:
      id-type: auto

# JWT配置
jwt:
  # 密钥
  secret: content_sharing_platform_jwt_secret_key_2026_test
  # 过期时间（秒）
  expiration: 7200
  # 刷新时间（秒）
  refreshTime: 14400
  # 令牌前缀
  tokenPrefix: Bearer
  # 令牌头
  tokenHeader: Authorization

# 日志配置
logging:
  level:
    root: info
    com.content: debug
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/${spring.application.name}.log
```

---

## 生产环境配置内容 (common-prod.yml)

```yaml
# Spring配置
spring:
  # 数据源配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.1.103:3306/ruoyi-flex?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai
    username: root
    password: mysql654321
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      initial-size: 10
      min-idle: 10
      max-active: 50
      max-wait: 60000
      time-between-eviction-runs-millis: 60000
      min-evictable-idle-time-millis: 300000
      validation-query: SELECT 1 FROM DUAL
      test-while-idle: true
      test-on-borrow: false
      test-on-return: false
      pool-prepared-statements: true
      max-pool-prepared-statement-per-connection-size: 20
      filters: stat,wall,log4j2
      connection-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000

  # Redis配置
  data:
    redis:
      host: 192.168.1.103
      port: 6379
      password: redis654321
      database: 0
      timeout: 5000
      lettuce:
        pool:
          max-active: 50
          max-idle: 20
          min-idle: 10
          max-wait: 2000

  # RabbitMQ配置
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    listener:
      simple:
        acknowledge-mode: manual
        concurrency: 10
        max-concurrency: 20
      direct:
        acknowledge-mode: manual

# MyBatis Plus配置
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
  global-config:
    db-config:
      id-type: auto

# JWT配置
jwt:
  # 密钥
  secret: content_sharing_platform_jwt_secret_key_2026_prod
  # 过期时间（秒）
  expiration: 7200
  # 刷新时间（秒）
  refreshTime: 14400
  # 令牌前缀
  tokenPrefix: Bearer
  # 令牌头
  tokenHeader: Authorization

# 日志配置
logging:
  level:
    root: info
    com.content: debug
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/${spring.application.name}.log
```

---

## 使用说明

各个微服务的 application.yml 会通过 Nacos 的 `shared-configs` 自动加载对应的配置文件。

## Maven 环境切换

在 [pom.xml](file:///d:/workcode/vue/zds_vue/novel-sharing/context-platform/pom.xml) 中配置了三个环境：

- **开发环境** (默认): `mvn clean package -Pdev`
- **测试环境**: `mvn clean package -Ptest`
- **生产环境**: `mvn clean package -Pprod`

不同环境会自动加载对应的 common 配置文件：
- dev 环境 → `common-dev.yml`
- test 环境 → `common-test.yml`
- prod 环境 → `common-prod.yml`

## 环境差异说明

| 配置项 | 开发环境 | 测试环境 | 生产环境 |
|--------|---------|---------|---------|
| MySQL 地址 | 192.168.86.1:3306 | test-mysql:3306 | prod-mysql:3306 |
| Redis 地址 | 192.168.86.1:6379 | test-redis:6379 | prod-redis:6379 |
| RabbitMQ 地址 | 192.168.86.1:5672 | test-rabbitmq:5672 | prod-rabbitmq:5672 |
| SSL | false | false | true |
| 连接池大小 | 20 | 20 | 50 |
| 虚拟主机 | / | /test | /prod |
