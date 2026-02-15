# Content Comment Service Module

## 功能介绍
content-comment-service是内容分享平台的评论服务，负责评论的发布、查询、回复等功能，是系统的重要组成部分。主要提供以下功能：

1. **评论发布**：用户发布评论
2. **评论查询**：根据内容ID查询评论列表
3. **评论回复**：回复他人评论
4. **评论点赞**：点赞评论
5. **评论状态管理**：评论的审核、删除等

## 目录结构
```
content-comment-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── content/
│   │   │           └── comment/
│   │   │               ├── config/
│   │   │               │   └── MyBatisPlusConfig.java   # MyBatis Plus配置
│   │   │               ├── controller/
│   │   │               │   └── CommentController.java    # 评论控制器
│   │   │               ├── entity/
│   │   │               │   └── Comment.java              # 评论实体类
│   │   │               ├── mapper/
│   │   │               │   └── CommentMapper.java        # 评论Mapper
│   │   │               ├── service/
│   │   │               │   ├── CommentService.java       # 评论服务接口
│   │   │               │   └── impl/
│   │   │               │       └── CommentServiceImpl.java # 评论服务实现
│   │   │               └── ContentCommentApplication.java # 应用启动类
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

### Comment实体
| 字段名 | 类型 | 描述 |
|-------|------|------|
| id | Long | 评论ID |
| contentId | Long | 内容ID |
| userId | Long | 评论用户ID |
| parentId | Long | 父评论ID（0表示顶级评论） |
| content | String | 评论内容 |
| likeCount | Integer | 点赞数 |
| status | Integer | 状态（0-待审核，1-已审核，2-已删除） |
| createTime | LocalDateTime | 创建时间 |
| updateTime | LocalDateTime | 更新时间 |

## API接口

### 1. 发布评论
- **URL**：`POST /comment`
- **请求体**：`Comment`对象
- **响应**：发布结果

### 2. 获取内容评论列表
- **URL**：`GET /comment/content/{contentId}`
- **查询参数**：`page`（页码）、`size`（每页条数）
- **响应**：分页评论列表

### 3. 获取评论详情
- **URL**：`GET /comment/{id}`
- **响应**：评论详细信息

### 4. 删除评论
- **URL**：`DELETE /comment/{id}`
- **响应**：删除结果

### 5. 评论点赞
- **URL**：`PUT /comment/like/{id}`
- **响应**：点赞结果

## 启动说明
1. 确保Nacos服务已启动
2. 确保MySQL数据库已启动，且创建了对应的数据库
3. 运行ContentCommentApplication.java
4. 服务默认端口：8083

## API文档
服务集成了Knife4j，可通过以下地址访问API文档：
- http://localhost:8083/doc.html
