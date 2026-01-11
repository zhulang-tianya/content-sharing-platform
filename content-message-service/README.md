# Content Message Service Module

## 功能介绍
content-message-service是内容分享平台的消息服务，负责用户之间的消息传递、通知推送等功能，增强用户之间的互动和沟通。主要提供以下功能：

1. **消息发送**：发送系统消息、私信、评论回复等
2. **消息查询**：根据接收者ID查询消息列表
3. **消息状态管理**：标记消息为已读、获取未读数量
4. **消息批量操作**：批量标记已读、批量删除等

## 目录结构
```
content-message-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── content/
│   │   │           └── message/
│   │   │               ├── config/
│   │   │               │   └── MyBatisPlusConfig.java       # MyBatis Plus配置
│   │   │               ├── controller/
│   │   │               │   └── MessageController.java       # 消息控制器
│   │   │               ├── entity/
│   │   │               │   └── Message.java                 # 消息实体类
│   │   │               ├── mapper/
│   │   │               │   └── MessageMapper.java           # 消息Mapper
│   │   │               ├── service/
│   │   │               │   ├── MessageService.java          # 消息服务接口
│   │   │               │   └── impl/
│   │   │               │       └── MessageServiceImpl.java  # 消息服务实现
│   │   │               └── ContentMessageApplication.java   # 应用启动类
│   │   └── resources/
│   │       └── application.yml                            # 配置文件
│   └── test/
└── pom.xml                                                 # Maven配置文件
```

## 技术栈
- Spring Boot 3.2.5
- Spring Cloud Alibaba Nacos 2023.0.1.0
- MyBatis Plus 3.5.5
- MySQL 8.3.0
- Druid 1.2.20

## 主要实体

### Message实体
| 字段名 | 类型 | 描述 |
|-------|------|------|
| id | Long | 消息ID |
| senderId | Long | 发送者ID |
| receiverId | Long | 接收者ID |
| type | Integer | 消息类型（1-系统消息 2-私信 3-评论回复 4-点赞通知） |
| title | String | 消息标题 |
| content | String | 消息内容 |
| status | Integer | 消息状态（0-未读 1-已读） |
| relationId | Long | 关联ID（如评论ID、点赞ID等） |
| createTime | LocalDateTime | 创建时间 |
| readTime | LocalDateTime | 阅读时间 |

## API接口

### 1. 发送消息
- **URL**：`POST /message`
- **请求体**：`Message`对象
- **响应**：发送结果

### 2. 根据接收者ID获取消息列表
- **URL**：`GET /message/receiver/{receiverId}`
- **响应**：消息列表

### 3. 根据接收者ID和状态获取消息列表
- **URL**：`GET /message/receiver/{receiverId}/status/{status}`
- **响应**：消息列表

### 4. 标记消息为已读
- **URL**：`PUT /message/read/{id}`
- **响应**：操作结果

### 5. 批量标记消息为已读
- **URL**：`PUT /message/read/batch`
- **请求体**：消息ID列表
- **响应**：操作结果

### 6. 获取未读消息数量
- **URL**：`GET /message/unread/count/{receiverId}`
- **响应**：未读消息数量

### 7. 删除消息
- **URL**：`DELETE /message/{id}`
- **响应**：删除结果

## 启动说明
1. 确保Nacos服务已启动
2. 确保MySQL数据库已启动，且创建了对应的数据库
3. 运行ContentMessageApplication.java
4. 服务默认端口：8086

## API文档
服务集成了Knife4j，可通过以下地址访问API文档：
- http://localhost:8086/doc.html
