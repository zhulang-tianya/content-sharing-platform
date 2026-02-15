# Content Recommend Service Module

## 功能介绍
content-recommend-service是内容分享平台的推荐服务，负责为用户推荐相关内容，提升用户体验和内容曝光率。主要提供以下功能：

1. **热门推荐**：推荐热门内容
2. **最新推荐**：推荐最新发布的内容
3. **分类推荐**：根据内容分类进行推荐
4. **个性化推荐**：基于用户行为的个性化推荐
5. **推荐管理**：管理推荐内容列表

## 目录结构
```
content-recommend-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── content/
│   │   │           └── recommend/
│   │   │               ├── config/
│   │   │               │   └── MyBatisPlusConfig.java       # MyBatis Plus配置
│   │   │               ├── controller/
│   │   │               │   └── RecommendController.java    # 推荐控制器
│   │   │               ├── entity/
│   │   │               │   └── Recommend.java              # 推荐实体类
│   │   │               ├── mapper/
│   │   │               │   └── RecommendMapper.java        # 推荐Mapper
│   │   │               ├── service/
│   │   │               │   ├── RecommendService.java       # 推荐服务接口
│   │   │               │   └── impl/
│   │   │               │       └── RecommendServiceImpl.java # 推荐服务实现
│   │   │               └── ContentRecommendApplication.java # 应用启动类
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

### Recommend实体
| 字段名 | 类型 | 描述 |
|-------|------|------|
| id | Long | 推荐ID |
| contentId | Long | 内容ID |
| type | Integer | 推荐类型（1-热门推荐 2-最新推荐 3-分类推荐 4-个性化推荐） |
| sort | Integer | 推荐排序 |
| status | Integer | 状态（0-禁用 1-启用） |
| createTime | LocalDateTime | 创建时间 |
| updateTime | LocalDateTime | 更新时间 |

## API接口

### 1. 根据推荐类型获取推荐内容
- **URL**：`GET /recommend/type/{type}`
- **响应**：推荐内容列表

### 2. 添加推荐内容
- **URL**：`POST /recommend`
- **请求体**：`Recommend`对象
- **响应**：添加结果

### 3. 更新推荐内容
- **URL**：`PUT /recommend`
- **请求体**：`Recommend`对象
- **响应**：更新结果

### 4. 删除推荐内容
- **URL**：`DELETE /recommend/{id}`
- **响应**：删除结果

### 5. 获取所有推荐内容
- **URL**：`GET /recommend`
- **响应**：所有推荐内容列表

## 启动说明
1. 确保Nacos服务已启动
2. 确保MySQL数据库已启动，且创建了对应的数据库
3. 运行ContentRecommendApplication.java
4. 服务默认端口：8084

## API文档
服务集成了Knife4j，可通过以下地址访问API文档：
- http://localhost:8084/doc.html
