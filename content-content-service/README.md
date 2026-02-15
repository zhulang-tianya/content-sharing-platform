# Content Content Service Module

## 功能介绍
content-content-service是内容分享平台的内容服务，负责内容的发布、查询、更新、删除等功能，是系统的核心服务之一。主要提供以下功能：

1. **内容发布**：用户发布新内容
2. **内容查询**：根据不同条件查询内容
3. **内容更新**：编辑已发布的内容
4. **内容删除**：删除不需要的内容
5. **内容状态管理**：内容的审核、发布、下架等

## 目录结构
```
content-content-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── content/
│   │   │           └── content/
│   │   │               ├── config/
│   │   │               │   └── MyBatisPlusConfig.java   # MyBatis Plus配置
│   │   │               ├── controller/
│   │   │               │   └── ContentController.java    # 内容控制器
│   │   │               ├── entity/
│   │   │               │   └── Content.java              # 内容实体类
│   │   │               ├── mapper/
│   │   │               │   └── ContentMapper.java        # 内容Mapper
│   │   │               ├── service/
│   │   │               │   ├── ContentService.java       # 内容服务接口
│   │   │               │   └── impl/
│   │   │               │       └── ContentServiceImpl.java # 内容服务实现
│   │   │               └── ContentContentApplication.java # 应用启动类
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

### Content实体
| 字段名 | 类型 | 描述 |
|-------|------|------|
| id | Long | 内容ID |
| title | String | 标题 |
| content | String | 内容正文 |
| userId | Long | 作者ID |
| categoryId | Long | 分类ID |
| status | Integer | 状态（0-草稿，1-已发布，2-已下架） |
| viewCount | Integer | 浏览量 |
| likeCount | Integer | 点赞数 |
| commentCount | Integer | 评论数 |
| createTime | LocalDateTime | 创建时间 |
| updateTime | LocalDateTime | 更新时间 |

## API接口

### 1. 发布内容
- **URL**：`POST /content`
- **请求体**：`Content`对象
- **响应**：发布结果

### 2. 获取内容详情
- **URL**：`GET /content/{id}`
- **响应**：内容详细信息

### 3. 更新内容
- **URL**：`PUT /content`
- **请求体**：`Content`对象
- **响应**：更新结果

### 4. 删除内容
- **URL**：`DELETE /content/{id}`
- **响应**：删除结果

### 5. 获取内容列表
- **URL**：`GET /content/list`
- **查询参数**：`categoryId`（分类ID，可选）、`status`（状态，可选）、`page`（页码）、`size`（每页条数）
- **响应**：分页内容列表

### 6. 增加浏览量
- **URL**：`PUT /content/view/{id}`
- **响应**：更新结果

## 启动说明
1. 确保Nacos服务已启动
2. 确保MySQL数据库已启动，且创建了对应的数据库
3. 运行ContentContentApplication.java
4. 服务默认端口：8082

## API文档
服务集成了Knife4j，可通过以下地址访问API文档：
- http://localhost:8082/doc.html
