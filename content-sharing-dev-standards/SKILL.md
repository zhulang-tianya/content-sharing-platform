---
name: content-sharing-dev-standards
description: 内容分享平台开发规范与技能指南。用于编写、审查或重构Java代码时确保编码实践的一致性和规范性。触发场景：(1) 创建新的Java类、接口或方法；(2) 进行代码审查；(3) 重构现有代码；(4) 编写异常处理逻辑；(5) 设计类结构或包结构；(6) 添加注释或文档。涵盖命名规范、注释标准、异常处理、编程规范、JDK版本要求和设计模式实施指南。
---

# 内容分享平台开发规范

## 概述

本技能为内容分享平台项目提供完整的开发规范指南，确保所有开发人员遵循统一的编码标准。项目基于 **Spring Cloud Alibaba** 微服务架构，使用 **JDK 22** 和 **Spring Boot 3.2.5**。

## 快速参考

| 规范类别 | 参考文档 |
|---------|---------|
| 命名规范 | [references/naming-conventions.md](references/naming-conventions.md) |
| 注释标准 | [references/comment-standards.md](references/comment-standards.md) |
| 异常处理 | [references/exception-handling.md](references/exception-handling.md) |
| 编程规范 | [references/coding-standards.md](references/coding-standards.md) |
| JDK规范 | [references/jdk-guidelines.md](references/jdk-guidelines.md) |
| 设计模式 | [references/design-patterns.md](references/design-patterns.md) |
| 架构设计 | [references/architecture-standards.md](references/architecture-standards.md) |
| 微服务规范 | [references/microservice-standards.md](references/microservice-standards.md) |
| Git规范 | [references/git-standards.md](references/git-standards.md) |

## 核心规范速查

### 命名规范要点

```java
// 类名：大驼峰
public class UserController {}

// 方法名/变量名：小驼峰
private String userName;
public void getUserById() {}

// 常量：全大写下划线
public static final String MAX_RETRY_COUNT = "3";

// 包名：全小写
package com.content.user.service;

// 数据库表名：下划线，模块前缀
// sys_user, content_article
```

### 注释标准要点

```java
/**
 * 用户服务接口
 * <p>提供用户注册、登录、信息管理等功能</p>
 *
 * @author content-platform
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface UserService {

    /**
     * 根据ID获取用户信息
     *
     * @param userId 用户ID，不能为空，必须大于0
     * @return 用户信息对象，如果不存在返回null
     * @throws BusinessException 当用户不存在时抛出
     */
    User getUserById(Long userId);
}
```

### 异常处理要点

```java
// 自定义异常继承BaseException
public class BusinessException extends BaseException {
    public BusinessException(String message) {
        super(ResultCode.BUSINESS_ERROR, message);
    }
}

// 日志记录规范
log.error("用户登录失败，userId={}，原因={}", userId, e.getMessage(), e);
```

### 编程规范要点

```java
// 单个方法不超过80行
// 单个类不超过1000行
// 使用try-with-resources
try (InputStream is = new FileInputStream(file)) {
    // 处理逻辑
}

// 使用Optional避免NPE
Optional.ofNullable(user)
    .map(User::getName)
    .orElse("未知用户");
```

## 项目技术栈

| 组件 | 版本 | 用途 |
|-----|------|-----|
| JDK | 22 | Java运行环境 |
| Spring Boot | 3.2.5 | 应用框架 |
| Spring Cloud | 2023.0.2 | 微服务框架 |
| Spring Cloud Alibaba | 2023.0.1.0 | 微服务组件 |
| Spring Security | 6.2.4 | 安全框架 |
| MyBatis Plus | 3.5.7 | ORM框架 |
| Nacos | 2.4.1 | 配置中心/注册中心 |
| Dubbo | 3.3.4 | RPC框架 |

## 模块架构

```
content-sharing-platform/
├── content-entity/          # 实体模块：所有共享实体类
├── content-common/          # 公共模块：常量、枚举、异常、工具类
├── content-framework/       # 框架模块：安全配置、切面、缓存
├── content-gateway/         # API网关 (8080)
├── content-user-service/    # 用户服务 (8081)
├── content-content-service/ # 内容服务 (8082)
├── content-comment-service/ # 评论服务 (8083)
├── content-recommend-service/ # 推荐服务 (8084)
├── content-pay-service/     # 支付服务 (8085)
├── content-message-service/ # 消息服务 (8086)
├── content-search-service/  # 搜索服务 (8087)
├── content-stat-service/    # 统计服务 (8088)
├── content-queue-service/   # 队列服务 (8089)
└── content-notification-service/ # 通知服务 (8090)
```

## 模块依赖关系

```
业务服务层 (content-user-service, content-content-service, ...)
        ↓ 依赖
框架层 (content-framework)
        ↓ 依赖
实体层 (content-entity)
        ↓ 依赖
基础层 (content-common)
```

**依赖原则**：
- 业务服务层 可依赖 框架层、实体层、基础层
- 框架层 可依赖 实体层、基础层，不可依赖业务服务层
- 实体层 可依赖 基础层，不可依赖框架层、业务服务层
- 基础层 不依赖任何内部模块

## 代码检查工具

使用 `scripts/code-check.py` 进行代码规范检查：

```bash
# 检查单个文件
python scripts/code-check.py path/to/File.java

# 检查整个模块
python scripts/code-check.py content-user-service/src/main/java
```

## 规范执行流程

1. **编码前**：查阅相关规范文档，了解命名、注释、异常处理要求
2. **编码中**：遵循规范编写代码，使用代码检查工具验证
3. **编码后**：进行代码审查，确保符合规范
4. **持续改进**：定期更新规范，建立监督机制

## 参考文档详细内容

详细规范内容请查阅 `references/` 目录下的各参考文档：

- **naming-conventions.md**: 完整的命名规范，包括各类标识符命名风格、长度限制、禁止模式
- **comment-standards.md**: Javadoc强制内容、格式要求、行内注释规范
- **exception-handling.md**: 异常分类、自定义异常实现、日志记录标准
- **coding-standards.md**: 代码结构、语句规范、长度限制、语言特性使用
- **jdk-guidelines.md**: JDK 22版本要求、允许/禁止的语法特性
- **design-patterns.md**: 各层推荐的设计模式及实现示例
- **architecture-standards.md**: 模块设计原则、实体类设计规范、常见架构问题解决方案
- **git-standards.md**: 分支管理、提交规范、合并规范、标签规范
