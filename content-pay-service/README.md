# Content Pay Service Module

## 功能介绍
content-pay-service是内容分享平台的支付服务，负责处理用户的支付订单、支付状态管理等功能，确保支付流程的安全可靠。主要提供以下功能：

1. **支付订单创建**：生成支付订单
2. **支付状态更新**：更新支付状态
3. **支付记录查询**：查询支付历史记录
4. **支付类型支持**：支持多种支付方式
5. **支付通知处理**：处理支付结果通知

## 目录结构
```
content-pay-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── content/
│   │   │           └── pay/
│   │   │               ├── config/
│   │   │               │   └── MyBatisPlusConfig.java       # MyBatis Plus配置
│   │   │               ├── controller/
│   │   │               │   └── PayController.java          # 支付控制器
│   │   │               ├── entity/
│   │   │               │   └── Pay.java                    # 支付实体类
│   │   │               ├── mapper/
│   │   │               │   └── PayMapper.java              # 支付Mapper
│   │   │               ├── service/
│   │   │               │   ├── PayService.java             # 支付服务接口
│   │   │               │   └── impl/
│   │   │               │       └── PayServiceImpl.java     # 支付服务实现
│   │   │               └── ContentPayApplication.java      # 应用启动类
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
- Alibaba Cloud Payment SDK (可扩展)

## 主要实体

### Pay实体
| 字段名 | 类型 | 描述 |
|-------|------|------|
| id | Long | 支付ID |
| userId | Long | 用户ID |
| amount | BigDecimal | 支付金额 |
| type | Integer | 支付类型（1-支付宝 2-微信 3-银联） |
| status | Integer | 支付状态（0-待支付 1-已支付 2-支付失败 3-已退款） |
| orderNo | String | 支付订单号 |
| tradeNo | String | 交易流水号 |
| payTime | LocalDateTime | 支付时间 |
| createTime | LocalDateTime | 创建时间 |
| updateTime | LocalDateTime | 更新时间 |

## API接口

### 1. 创建支付订单
- **URL**：`POST /pay`
- **请求体**：`Pay`对象
- **响应**：创建结果

### 2. 根据用户ID获取支付记录
- **URL**：`GET /pay/user/{userId}`
- **响应**：支付记录列表

### 3. 更新支付状态
- **URL**：`PUT /pay/status`
- **请求参数**：`orderNo`（订单号）、`status`（状态）、`tradeNo`（交易流水号，可选）
- **响应**：更新结果

### 4. 根据订单号获取支付记录
- **URL**：`GET /pay/order/{orderNo}`
- **响应**：支付记录详情

### 5. 获取所有支付记录
- **URL**：`GET /pay`
- **响应**：所有支付记录列表

## 启动说明
1. 确保Nacos服务已启动
2. 确保MySQL数据库已启动，且创建了对应的数据库
3. 运行ContentPayApplication.java
4. 服务默认端口：8085

## API文档
服务集成了Knife4j，可通过以下地址访问API文档：
- http://localhost:8085/doc.html
