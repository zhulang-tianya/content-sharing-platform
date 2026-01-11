# Content Common Module

## 功能介绍
content-common是内容分享平台的公共模块，包含了各个微服务之间共享的代码和配置，主要提供以下功能：

1. **公共结果返回封装**：统一的API响应格式
2. **异常处理机制**：定义了基础异常和业务异常
3. **公共依赖管理**：集中管理各个微服务共用的依赖

## 目录结构
```
content-common/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── content/
│                   └── common/
│                       ├── exception/
│                       │   ├── BaseException.java      # 基础异常类
│                       │   └── BusinessException.java  # 业务异常类
│                       └── result/
│                           ├── Result.java             # 统一结果返回类
│                           └── ResultCode.java         # 结果状态码
└── pom.xml                                             # Maven配置文件
```

## 主要组件

### 1. 结果返回封装
- **Result**：统一的API响应格式，包含状态码、消息和数据
- **ResultCode**：定义了常用的结果状态码，如成功、失败、参数错误等

### 2. 异常处理
- **BaseException**：所有自定义异常的基类
- **BusinessException**：业务异常类，用于处理业务逻辑中的异常情况

## 使用说明
其他微服务模块可以通过Maven依赖引入此模块：

```xml
<dependency>
    <groupId>com.content</groupId>
    <artifactId>content-common</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
