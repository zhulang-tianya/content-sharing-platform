# Content Queue Service Module

## 功能介绍
content-queue-service是内容分享平台的队列服务，负责处理系统中的异步消息队列，确保消息的可靠传递和处理，提高系统的并发能力和稳定性。主要提供以下功能：

1. **消息发送**：发送消息到RabbitMQ队列
2. **消息接收**：从RabbitMQ队列接收消息并处理
3. **队列配置**：配置RabbitMQ队列、交换机和绑定关系
4. **消息管理**：管理消息的发送、接收和处理状态

## 目录结构
```
content-queue-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── content/
│   │   │           └── queue/
│   │   │               ├── config/
│   │   │               │   └── RabbitMQConfig.java       # RabbitMQ配置
│   │   │               ├── consumer/
│   │   │               │   └── RabbitMQConsumer.java      # RabbitMQ消费者
│   │   │               ├── controller/
│   │   │               │   └── QueueController.java       # 队列控制器
│   │   │               ├── producer/
│   │   │               │   └── RabbitMQProducer.java      # RabbitMQ生产者
│   │   │               └── ContentQueueApplication.java   # 应用启动类
│   │   └── resources/
│   │       └── application.yml                            # 配置文件
│   └── test/
└── pom.xml                                                 # Maven配置文件
```

## 技术栈
- Spring Boot 3.2.5
- Spring Cloud Alibaba Nacos 2023.0.1.0
- RabbitMQ 3.13.0
- Spring AMQP

## 主要配置
- **队列名称**：content.queue
- **交换机名称**：content.exchange
- **路由键**：content.routing.key

## API接口

### 1. 发送消息
- **URL**：`POST /queue/send`
- **请求体**：`{ "message": "消息内容" }`
- **响应**：发送结果

### 2. 发送延迟消息
- **URL**：`POST /queue/send/delay`
- **请求体**：`{ "message": "消息内容", "delay": 延迟时间（毫秒） }`
- **响应**：发送结果

## 启动说明
1. 确保Nacos服务已启动
2. 确保RabbitMQ服务已启动
3. 运行ContentQueueApplication.java
4. 服务默认端口：8089

## API文档
服务集成了Knife4j，可通过以下地址访问API文档：
- http://localhost:8089/doc.html
