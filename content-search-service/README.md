# Content Search Service Module

## 功能介绍
content-search-service是内容分享平台的搜索服务，负责内容的搜索、搜索历史记录管理、热门搜索词统计等功能，提升用户查找内容的效率。主要提供以下功能：

1. **内容搜索**：支持按关键词搜索内容
2. **搜索历史管理**：记录和管理用户搜索历史
3. **热门搜索词**：统计和展示热门搜索词
4. **搜索结果统计**：统计搜索结果数量

## 目录结构
```
content-search-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── content/
│   │   │           └── search/
│   │   │               ├── config/
│   │   │               │   └── MyBatisPlusConfig.java       # MyBatis Plus配置
│   │   │               ├── controller/
│   │   │               │   └── SearchController.java        # 搜索控制器
│   │   │               ├── entity/
│   │   │               │   └── Search.java                  # 搜索实体类
│   │   │               ├── mapper/
│   │   │               │   └── SearchMapper.java            # 搜索Mapper
│   │   │               ├── service/
│   │   │               │   ├── SearchService.java           # 搜索服务接口
│   │   │               │   └── impl/
│   │   │               │       └── SearchServiceImpl.java   # 搜索服务实现
│   │   │               └── ContentSearchApplication.java    # 应用启动类
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
- Elasticsearch (可扩展)

## 主要实体

### Search实体
| 字段名 | 类型 | 描述 |
|-------|------|------|
| id | Long | 搜索ID |
| userId | Long | 用户ID（0表示匿名搜索） |
| keyword | String | 搜索关键词 |
| type | Integer | 搜索类型（1-内容搜索 2-用户搜索 3-标签搜索） |
| resultCount | Integer | 搜索结果数量 |
| createTime | LocalDateTime | 创建时间 |

## API接口

### 1. 保存搜索历史
- **URL**：`POST /search`
- **请求体**：`Search`对象
- **响应**：保存结果

### 2. 根据用户ID获取搜索历史
- **URL**：`GET /search/history/{userId}`
- **响应**：搜索历史列表

### 3. 获取热门搜索词
- **URL**：`GET /search/hot`
- **请求参数**：`limit`（限制数量，默认10）
- **响应**：热门搜索词列表

### 4. 清空用户搜索历史
- **URL**：`DELETE /search/history/{userId}`
- **响应**：清空结果

### 5. 搜索内容
- **URL**：`GET /search/content`
- **请求参数**：`keyword`（搜索关键词）、`type`（搜索类型，默认1）
- **响应**：搜索结果数量

## 启动说明
1. 确保Nacos服务已启动
2. 确保MySQL数据库已启动，且创建了对应的数据库
3. 运行ContentSearchApplication.java
4. 服务默认端口：8087

## API文档
服务集成了Knife4j，可通过以下地址访问API文档：
- http://localhost:8087/doc.html
