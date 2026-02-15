# Content User Service Module

## 功能介绍
content-user-service是内容分享平台的用户服务，负责用户的注册、登录、信息管理等功能，是系统的核心服务之一。主要提供以下功能：

1. **用户注册**：新用户注册功能
2. **用户登录**：用户登录认证功能
3. **用户信息管理**：用户基本信息的查询、更新
4. **用户状态管理**：用户账号的启用、禁用等

## 目录结构
```
content-user-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── content/
│   │   │           └── user/
│   │   │               ├── config/
│   │   │               │   └── MyBatisPlusConfig.java   # MyBatis Plus配置
│   │   │               ├── controller/
│   │   │               │   └── UserController.java       # 用户控制器
│   │   │               ├── entity/
│   │   │               │   └── User.java                 # 用户实体类
│   │   │               ├── mapper/
│   │   │               │   └── UserMapper.java           # 用户Mapper
│   │   │               ├── service/
│   │   │               │   ├── UserService.java          # 用户服务接口
│   │   │               │   └── impl/
│   │   │               │       └── UserServiceImpl.java  # 用户服务实现
│   │   │               └── ContentUserApplication.java   # 应用启动类
│   │   └── resources/
│   │       └── application.yml                          # 配置文件
│   └── test/
└── pom.xml                                               # Maven配置文件
```

## 技术栈
- Spring Boot 3.2.5
- Spring Cloud Alibaba Nacos 2023.0.1.0
- MyBatis Plus 3.5.5
- MySQL 8.3.0
- Druid 1.2.20

## 主要实体

### User实体
| 字段名 | 类型 | 描述 |
|-------|------|------|
| id | Long | 用户ID |
| username | String | 用户名 |
| password | String | 密码 |
| nickname | String | 昵称 |
| email | String | 邮箱 |
| phone | String | 手机号 |
| avatar | String | 头像 |
| status | Integer | 状态（0-禁用，1-启用） |
| createTime | LocalDateTime | 创建时间 |
| updateTime | LocalDateTime | 更新时间 |

## API接口

### 1. 用户注册
- **URL**：`POST /user/register`
- **请求体**：`User`对象
- **响应**：注册结果

### 2. 用户登录
- **URL**：`POST /user/login`
- **请求体**：`{"username": "xxx", "password": "xxx"}`
- **响应**：登录结果，包含token

### 3. 获取用户信息
- **URL**：`GET /user/{id}`
- **响应**：用户详细信息

### 4. 更新用户信息
- **URL**：`PUT /user`
- **请求体**：`User`对象
- **响应**：更新结果

### 5. 获取用户列表
- **URL**：`GET /user/list`
- **响应**：用户列表

## 启动说明
1. 确保Nacos服务已启动
2. 确保MySQL数据库已启动，且创建了对应的数据库
3. 运行ContentUserApplication.java
4. 服务默认端口：8081

## API文档
服务集成了Knife4j，可通过以下地址访问API文档：
- http://localhost:8081/doc.html
