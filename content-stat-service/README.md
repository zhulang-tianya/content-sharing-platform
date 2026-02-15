# Content Stat Service Module

## 功能介绍
content-stat-service是内容分享平台的统计服务，负责系统各项数据的统计、分析和报表生成，为运营决策提供数据支持。主要提供以下功能：

1. **统计数据记录**：记录日活用户数、新增用户数、内容浏览量等
2. **统计数据查询**：根据日期范围和统计类型查询统计数据
3. **统计数据汇总**：汇总特定时间段内的统计数据
4. **多类型统计**：支持同时查询多种类型的统计数据

## 目录结构
```
content-stat-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── content/
│   │   │           └── stat/
│   │   │               ├── config/
│   │   │               │   └── MyBatisPlusConfig.java       # MyBatis Plus配置
│   │   │               ├── controller/
│   │   │               │   └── StatController.java          # 统计控制器
│   │   │               ├── entity/
│   │   │               │   └── Stat.java                    # 统计实体类
│   │   │               ├── mapper/
│   │   │               │   └── StatMapper.java              # 统计Mapper
│   │   │               ├── service/
│   │   │               │   ├── StatService.java             # 统计服务接口
│   │   │               │   └── impl/
│   │   │               │       └── StatServiceImpl.java     # 统计服务实现
│   │   │               └── ContentStatApplication.java      # 应用启动类
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

### Stat实体
| 字段名 | 类型 | 描述 |
|-------|------|------|
| id | Long | 统计ID |
| statDate | LocalDate | 统计日期 |
| type | Integer | 统计类型（1-日活用户数 2-新增用户数 3-内容浏览量 4-内容发布量 5-评论数 6-点赞数） |
| value | Long | 统计数值 |
| relationId | Long | 关联ID（如内容ID、用户ID等） |
| createTime | LocalDateTime | 创建时间 |

## API接口

### 1. 记录统计数据
- **URL**：`POST /stat`
- **请求体**：`Stat`对象
- **响应**：记录结果

### 2. 根据类型和日期范围获取统计数据
- **URL**：`GET /stat/range`
- **请求参数**：`type`（统计类型）、`startDate`（开始日期，格式：yyyy-MM-dd）、`endDate`（结束日期，格式：yyyy-MM-dd）
- **响应**：统计数据列表

### 3. 根据类型和日期获取统计数据
- **URL**：`GET /stat`
- **请求参数**：`type`（统计类型）、`statDate`（统计日期，格式：yyyy-MM-dd）
- **响应**：统计数据

### 4. 获取统计数据汇总
- **URL**：`GET /stat/sum`
- **请求参数**：`type`（统计类型）、`startDate`（开始日期，格式：yyyy-MM-dd）、`endDate`（结束日期，格式：yyyy-MM-dd）
- **响应**：统计数据汇总值

### 5. 获取多类型统计数据
- **URL**：`POST /stat/multi`
- **请求体**：统计类型列表
- **请求参数**：`statDate`（统计日期，格式：yyyy-MM-dd）
- **响应**：统计数据映射，key为统计类型，value为统计数值

## 启动说明
1. 确保Nacos服务已启动
2. 确保MySQL数据库已启动，且创建了对应的数据库
3. 运行ContentStatApplication.java
4. 服务默认端口：8088

## API文档
服务集成了Knife4j，可通过以下地址访问API文档：
- http://localhost:8088/doc.html
