# 内容分享平台部署方案

## 一、架构概览

### 1.1 服务清单

| 服务名称 | 端口 | 说明 | 依赖 |
|----------|------|------|------|
| content-gateway | 8080 | API网关 | Nacos |
| content-user-service | 8081 | 用户服务 | MySQL, Redis, Nacos |
| content-content-service | 8082 | 内容服务 | MySQL, Redis, Nacos |
| content-comment-service | 8083 | 评论服务 | MySQL, Redis, Nacos |
| content-recommend-service | 8084 | 推荐服务 | MySQL, Redis, Nacos |
| content-pay-service | 8085 | 支付服务 | MySQL, Redis, Nacos |
| content-message-service | 8086 | 消息服务 | MySQL, Redis, Nacos |
| content-search-service | 8087 | 搜索服务 | MySQL, Redis, Nacos |
| content-stat-service | 8088 | 统计服务 | MySQL, Redis, Nacos |
| content-queue-service | 8089 | 队列服务 | RabbitMQ, Nacos |

### 1.2 基础设施依赖

| 组件 | 版本 | 说明 |
|------|------|------|
| JDK | 22 | 运行环境 |
| MySQL | 8.0+ | 主数据库 |
| Redis | 7.0+ | 缓存/Session |
| Nacos | 2.3+ | 配置中心/注册中心 |
| RabbitMQ | 3.12+ | 消息队列 |
| Nginx | 1.24+ | 反向代理/负载均衡 |

### 1.3 架构图

```
                                    ┌─────────────────────────────────────┐
                                    │            外部访问                  │
                                    └─────────────────┬───────────────────┘
                                                      │
                                                      ▼
                                    ┌─────────────────────────────────────┐
                                    │          Nginx (负载均衡)            │
                                    │         80/443 (SSL终止)            │
                                    └─────────────────┬───────────────────┘
                                                      │
                                    ┌─────────────────▼───────────────────┐
                                    │        content-gateway:8080         │
                                    │           (API网关)                  │
                                    └─────────────────┬───────────────────┘
                                                      │
                    ┌─────────────────────────────────┼─────────────────────────────────┐
                    │                                 │                                 │
        ┌───────────▼───────────┐       ┌───────────▼───────────┐       ┌───────────▼───────────┐
        │   user-service:8081   │       │  content-service:8082 │       │  comment-service:8083 │
        └───────────┬───────────┘       └───────────┬───────────┘       └───────────┬───────────┘
                    │                                 │                                 │
        ┌───────────▼───────────┐       ┌───────────▼───────────┐       ┌───────────▼───────────┐
        │ recommend-service:8084│       │   pay-service:8085    │       │ message-service:8086  │
        └───────────┬───────────┘       └───────────┬───────────┘       └───────────┬───────────┘
                    │                                 │                                 │
        ┌───────────▼───────────┐       ┌───────────▼───────────┐       ┌───────────▼───────────┐
        │  search-service:8087  │       │   stat-service:8088   │       │  queue-service:8089   │
        └───────────┬───────────┘       └───────────┬───────────┘       └───────────┬───────────┘
                    │                                 │                                 │
                    └─────────────────────────────────┼─────────────────────────────────┘
                                                      │
                                    ┌─────────────────▼───────────────────┐
                                    │           Nacos Cluster             │
                                    │      (配置中心 + 服务注册发现)        │
                                    └─────────────────┬───────────────────┘
                                                      │
                    ┌─────────────────────────────────┼─────────────────────────────────┐
                    │                                 │                                 │
        ┌───────────▼───────────┐       ┌───────────▼───────────┐       ┌───────────▼───────────┐
        │     MySQL Cluster     │       │     Redis Cluster     │       │     RabbitMQ          │
        │      (主数据库)        │       │      (缓存集群)        │       │    (消息队列)          │
        └───────────────────────┘       └───────────────────────┘       └───────────────────────┘
```

---

## 二、环境准备

### 2.1 服务器配置要求

#### 开发/测试环境

| 角色 | 配置 | 数量 | 说明 |
|------|------|------|------|
| 应用服务器 | 4C8G | 1 | 部署所有服务 |
| 数据库服务器 | 4C8G | 1 | MySQL + Redis |
| 中间件服务器 | 2C4G | 1 | Nacos + RabbitMQ |

#### 生产环境

| 角色 | 配置 | 数量 | 说明 |
|------|------|------|------|
| 网关服务器 | 4C8G | 2 | Gateway集群 |
| 应用服务器 | 8C16G | 3+ | 业务服务集群 |
| 数据库主 | 8C32G | 1 | MySQL Master |
| 数据库从 | 8C32G | 2 | MySQL Slave |
| 缓存服务器 | 8C16G | 3 | Redis Sentinel |
| 配置中心 | 4C8G | 3 | Nacos集群 |
| 消息队列 | 4C8G | 3 | RabbitMQ镜像队列 |

### 2.2 基础软件安装

```bash
# JDK 22 安装
wget https://download.oracle.com/java/22/latest/jdk-22_linux-x64_bin.tar.gz
tar -zxvf jdk-22_linux-x64_bin.tar.gz -C /usr/local/
ln -s /usr/local/jdk-22 /usr/local/java

# 环境变量配置
cat >> /etc/profile << 'EOF'
export JAVA_HOME=/usr/local/java
export PATH=$JAVA_HOME/bin:$PATH
EOF
source /etc/profile

# 验证
java -version
```

---

## 三、Docker Compose 部署（开发/测试环境）

### 3.1 目录结构

```
deploy/
├── docker-compose.yml
├── .env
├── mysql/
│   ├── init/
│   │   └── init.sql
│   └── conf/
│       └── my.cnf
├── redis/
│   └── redis.conf
├── nacos/
│   └── application.properties
├── rabbitmq/
│   └── enabled_plugins
├── nginx/
│   ├── nginx.conf
│   └── conf.d/
│       └── default.conf
└── services/
    ├── gateway/
    ├── user-service/
    └── ...
```

### 3.2 docker-compose.yml

```yaml
version: '3.8'

services:
  # ==================== 基础设施 ====================
  
  mysql:
    image: mysql:8.0
    container_name: content-mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      MYSQL_DATABASE: content_sharing
      TZ: Asia/Shanghai
    ports:
      - "3306:3306"
    volumes:
      - ./mysql/data:/var/lib/mysql
      - ./mysql/init:/docker-entrypoint-initdb.d
      - ./mysql/conf/my.cnf:/etc/mysql/conf.d/my.cnf
    command: --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
    networks:
      - content-network
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

  redis:
    image: redis:7.0-alpine
    container_name: content-redis
    restart: always
    ports:
      - "6379:6379"
    volumes:
      - ./redis/redis.conf:/etc/redis/redis.conf
      - ./redis/data:/data
    command: redis-server /etc/redis/redis.conf
    networks:
      - content-network
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 10s
      timeout: 5s
      retries: 5

  nacos:
    image: nacos/nacos-server:v2.3.0
    container_name: content-nacos
    restart: always
    environment:
      MODE: standalone
      SPRING_DATASOURCE_PLATFORM: mysql
      MYSQL_SERVICE_HOST: mysql
      MYSQL_SERVICE_PORT: 3306
      MYSQL_SERVICE_DB_NAME: nacos_config
      MYSQL_SERVICE_USER: root
      MYSQL_SERVICE_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      JVM_XMS: 512m
      JVM_XMX: 512m
    ports:
      - "8848:8848"
      - "9848:9848"
    volumes:
      - ./nacos/logs:/home/nacos/logs
    networks:
      - content-network
    depends_on:
      mysql:
        condition: service_healthy

  rabbitmq:
    image: rabbitmq:3.12-management-alpine
    container_name: content-rabbitmq
    restart: always
    environment:
      RABBITMQ_DEFAULT_USER: ${RABBITMQ_USER}
      RABBITMQ_DEFAULT_PASS: ${RABBITMQ_PASSWORD}
    ports:
      - "5672:5672"
      - "15672:15672"
    volumes:
      - ./rabbitmq/data:/var/lib/rabbitmq
      - ./rabbitmq/enabled_plugins:/etc/rabbitmq/enabled_plugins
    networks:
      - content-network

  # ==================== 业务服务 ====================

  gateway:
    image: ${REGISTRY}/content-gateway:${VERSION}
    container_name: content-gateway
    restart: always
    environment:
      SPRING_PROFILES_ACTIVE: prod
      NACOS_SERVER_ADDR: nacos:8848
      NACOS_NAMESPACE: ${NACOS_NAMESPACE}
      NACOS_USERNAME: nacos
      NACOS_PASSWORD: nacos
      JVM_XMS: 256m
      JVM_XMX: 512m
    ports:
      - "8080:8080"
    networks:
      - content-network
    depends_on:
      - nacos
      - redis
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3

  user-service:
    image: ${REGISTRY}/content-user-service:${VERSION}
    container_name: content-user-service
    restart: always
    environment:
      SPRING_PROFILES_ACTIVE: prod
      NACOS_SERVER_ADDR: nacos:8848
      NACOS_NAMESPACE: ${NACOS_NAMESPACE}
      JVM_XMS: 256m
      JVM_XMX: 512m
    ports:
      - "8081:8081"
    networks:
      - content-network
    depends_on:
      - nacos
      - mysql
      - redis

  content-service:
    image: ${REGISTRY}/content-content-service:${VERSION}
    container_name: content-content-service
    restart: always
    environment:
      SPRING_PROFILES_ACTIVE: prod
      NACOS_SERVER_ADDR: nacos:8848
      NACOS_NAMESPACE: ${NACOS_NAMESPACE}
      JVM_XMS: 256m
      JVM_XMX: 512m
    ports:
      - "8082:8082"
    networks:
      - content-network
    depends_on:
      - nacos
      - mysql
      - redis

  comment-service:
    image: ${REGISTRY}/content-comment-service:${VERSION}
    container_name: content-comment-service
    restart: always
    environment:
      SPRING_PROFILES_ACTIVE: prod
      NACOS_SERVER_ADDR: nacos:8848
      NACOS_NAMESPACE: ${NACOS_NAMESPACE}
      JVM_XMS: 256m
      JVM_XMX: 512m
    ports:
      - "8083:8083"
    networks:
      - content-network
    depends_on:
      - nacos
      - mysql
      - redis

  recommend-service:
    image: ${REGISTRY}/content-recommend-service:${VERSION}
    container_name: content-recommend-service
    restart: always
    environment:
      SPRING_PROFILES_ACTIVE: prod
      NACOS_SERVER_ADDR: nacos:8848
      NACOS_NAMESPACE: ${NACOS_NAMESPACE}
      JVM_XMS: 256m
      JVM_XMX: 512m
    ports:
      - "8084:8084"
    networks:
      - content-network
    depends_on:
      - nacos
      - mysql
      - redis

  pay-service:
    image: ${REGISTRY}/content-pay-service:${VERSION}
    container_name: content-pay-service
    restart: always
    environment:
      SPRING_PROFILES_ACTIVE: prod
      NACOS_SERVER_ADDR: nacos:8848
      NACOS_NAMESPACE: ${NACOS_NAMESPACE}
      JVM_XMS: 256m
      JVM_XMX: 512m
    ports:
      - "8085:8085"
    networks:
      - content-network
    depends_on:
      - nacos
      - mysql
      - redis

  message-service:
    image: ${REGISTRY}/content-message-service:${VERSION}
    container_name: content-message-service
    restart: always
    environment:
      SPRING_PROFILES_ACTIVE: prod
      NACOS_SERVER_ADDR: nacos:8848
      NACOS_NAMESPACE: ${NACOS_NAMESPACE}
      JVM_XMS: 256m
      JVM_XMX: 512m
    ports:
      - "8086:8086"
    networks:
      - content-network
    depends_on:
      - nacos
      - mysql
      - redis

  search-service:
    image: ${REGISTRY}/content-search-service:${VERSION}
    container_name: content-search-service
    restart: always
    environment:
      SPRING_PROFILES_ACTIVE: prod
      NACOS_SERVER_ADDR: nacos:8848
      NACOS_NAMESPACE: ${NACOS_NAMESPACE}
      JVM_XMS: 256m
      JVM_XMX: 512m
    ports:
      - "8087:8087"
    networks:
      - content-network
    depends_on:
      - nacos
      - mysql
      - redis

  stat-service:
    image: ${REGISTRY}/content-stat-service:${VERSION}
    container_name: content-stat-service
    restart: always
    environment:
      SPRING_PROFILES_ACTIVE: prod
      NACOS_SERVER_ADDR: nacos:8848
      NACOS_NAMESPACE: ${NACOS_NAMESPACE}
      JVM_XMS: 256m
      JVM_XMX: 512m
    ports:
      - "8088:8088"
    networks:
      - content-network
    depends_on:
      - nacos
      - mysql
      - redis

  queue-service:
    image: ${REGISTRY}/content-queue-service:${VERSION}
    container_name: content-queue-service
    restart: always
    environment:
      SPRING_PROFILES_ACTIVE: prod
      NACOS_SERVER_ADDR: nacos:8848
      NACOS_NAMESPACE: ${NACOS_NAMESPACE}
      JVM_XMS: 256m
      JVM_XMX: 512m
    ports:
      - "8089:8089"
    networks:
      - content-network
    depends_on:
      - nacos
      - rabbitmq

  # ==================== 负载均衡 ====================

  nginx:
    image: nginx:1.24-alpine
    container_name: content-nginx
    restart: always
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx/nginx.conf:/etc/nginx/nginx.conf
      - ./nginx/conf.d:/etc/nginx/conf.d
      - ./nginx/ssl:/etc/nginx/ssl
      - ./nginx/logs:/var/log/nginx
    networks:
      - content-network
    depends_on:
      - gateway

networks:
  content-network:
    driver: bridge
```

### 3.3 环境变量文件 (.env)

```properties
# MySQL
MYSQL_ROOT_PASSWORD=Content@2024#Root

# RabbitMQ
RABBITMQ_USER=content
RABBITMQ_PASSWORD=Content@2024#Mq

# Nacos
NACOS_NAMESPACE=content-prod

# 镜像仓库
REGISTRY=registry.cn-hangzhou.aliyuncs.com/content-sharing

# 版本号
VERSION=1.0.0
```

### 3.4 Nginx 配置

```nginx
# nginx.conf
user nginx;
worker_processes auto;
error_log /var/log/nginx/error.log warn;
pid /var/run/nginx.pid;

events {
    worker_connections 10240;
    use epoll;
    multi_accept on;
}

http {
    include /etc/nginx/mime.types;
    default_type application/octet-stream;

    log_format main '$remote_addr - $remote_user [$time_local] "$request" '
                    '$status $body_bytes_sent "$http_referer" '
                    '"$http_user_agent" "$http_x_forwarded_for" '
                    'rt=$request_time uct="$upstream_connect_time" '
                    'uht="$upstream_header_time" urt="$upstream_response_time"';

    access_log /var/log/nginx/access.log main;

    sendfile on;
    tcp_nopush on;
    tcp_nodelay on;
    keepalive_timeout 65;
    types_hash_max_size 2048;

    # Gzip压缩
    gzip on;
    gzip_vary on;
    gzip_proxied any;
    gzip_comp_level 6;
    gzip_types text/plain text/css text/xml application/json application/javascript 
               application/rss+xml application/atom+xml image/svg+xml;

    # 请求体大小限制
    client_max_body_size 50m;
    client_body_buffer_size 256k;

    # 代理缓冲
    proxy_buffer_size 128k;
    proxy_buffers 4 256k;
    proxy_busy_buffers_size 256k;

    # 上游服务器
    upstream gateway_cluster {
        least_conn;
        server gateway:8080 weight=1 max_fails=3 fail_timeout=30s;
        # 生产环境添加更多网关节点
        # server gateway-2:8080 weight=1 max_fails=3 fail_timeout=30s;
        keepalive 32;
    }

    include /etc/nginx/conf.d/*.conf;
}
```

```nginx
# conf.d/default.conf
server {
    listen 80;
    server_name _;

    # 强制HTTPS (生产环境启用)
    # return 301 https://$host$request_uri;

    # 健康检查
    location /health {
        access_log off;
        return 200 "OK\n";
        add_header Content-Type text/plain;
    }

    # API代理
    location /api/ {
        proxy_pass http://gateway_cluster/api/;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Connection "";

        # 超时配置
        proxy_connect_timeout 60s;
        proxy_send_timeout 60s;
        proxy_read_timeout 60s;
    }

    # 静态资源 (前端)
    location / {
        root /usr/share/nginx/html;
        index index.html;
        try_files $uri $uri/ /index.html;
    }

    # 错误页面
    error_page 500 502 503 504 /50x.html;
    location = /50x.html {
        root /usr/share/nginx/html;
    }
}

# HTTPS配置 (生产环境)
# server {
#     listen 443 ssl http2;
#     server_name your-domain.com;
#
#     ssl_certificate /etc/nginx/ssl/cert.pem;
#     ssl_certificate_key /etc/nginx/ssl/key.pem;
#     ssl_session_timeout 1d;
#     ssl_session_cache shared:SSL:50m;
#     ssl_session_tickets off;
#
#     ssl_protocols TLSv1.2 TLSv1.3;
#     ssl_ciphers ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES128-GCM-SHA256;
#     ssl_prefer_server_ciphers off;
#
#     # HSTS
#     add_header Strict-Transport-Security "max-age=63072000" always;
#
#     # 其他配置同上...
# }
```

---

## 四、Kubernetes 部署（生产环境）

### 4.1 Namespace 配置

```yaml
# namespace.yaml
apiVersion: v1
kind: Namespace
metadata:
  name: content-sharing
  labels:
    app: content-sharing
    environment: production
```

### 4.2 ConfigMap 配置

```yaml
# configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: content-config
  namespace: content-sharing
data:
  NACOS_SERVER_ADDR: "nacos-headless:8848"
  NACOS_NAMESPACE: "content-prod"
  SPRING_PROFILES_ACTIVE: "prod"
  TZ: "Asia/Shanghai"
```

### 4.3 Secret 配置

```yaml
# secret.yaml
apiVersion: v1
kind: Secret
metadata:
  name: content-secret
  namespace: content-sharing
type: Opaque
stringData:
  MYSQL_ROOT_PASSWORD: "Content@2024#Root"
  REDIS_PASSWORD: "Content@2024#Redis"
  RABBITMQ_USER: "content"
  RABBITMQ_PASSWORD: "Content@2024#Mq"
  NACOS_USERNAME: "nacos"
  NACOS_PASSWORD: "nacos"
```

### 4.4 业务服务 Deployment

```yaml
# gateway-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: content-gateway
  namespace: content-sharing
  labels:
    app: content-gateway
spec:
  replicas: 2
  selector:
    matchLabels:
      app: content-gateway
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1
      maxUnavailable: 0
  template:
    metadata:
      labels:
        app: content-gateway
    spec:
      containers:
      - name: gateway
        image: registry.cn-hangzhou.aliyuncs.com/content-sharing/content-gateway:1.0.0
        imagePullPolicy: Always
        ports:
        - containerPort: 8080
          name: http
        envFrom:
        - configMapRef:
            name: content-config
        - secretRef:
            name: content-secret
        env:
        - name: JVM_XMS
          value: "512m"
        - name: JVM_XMX
          value: "1g"
        resources:
          requests:
            cpu: "200m"
            memory: "512Mi"
          limits:
            cpu: "1000m"
            memory: "1Gi"
        livenessProbe:
          httpGet:
            path: /actuator/health/liveness
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 10
          timeoutSeconds: 5
          failureThreshold: 3
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
          timeoutSeconds: 5
          failureThreshold: 3
        volumeMounts:
        - name: logs
          mountPath: /app/logs
      volumes:
      - name: logs
        emptyDir: {}
      affinity:
        podAntiAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
          - weight: 100
            podAffinityTerm:
              labelSelector:
                matchExpressions:
                - key: app
                  operator: In
                  values:
                  - content-gateway
              topologyKey: kubernetes.io/hostname
---
apiVersion: v1
kind: Service
metadata:
  name: content-gateway
  namespace: content-sharing
spec:
  type: ClusterIP
  selector:
    app: content-gateway
  ports:
  - port: 8080
    targetPort: 8080
    name: http
---
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: content-gateway-hpa
  namespace: content-sharing
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: content-gateway
  minReplicas: 2
  maxReplicas: 10
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70
  - type: Resource
    resource:
      name: memory
      target:
        type: Utilization
        averageUtilization: 80
```

### 4.5 Ingress 配置

```yaml
# ingress.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: content-ingress
  namespace: content-sharing
  annotations:
    kubernetes.io/ingress.class: nginx
    nginx.ingress.kubernetes.io/ssl-redirect: "true"
    nginx.ingress.kubernetes.io/proxy-body-size: "50m"
    nginx.ingress.kubernetes.io/proxy-connect-timeout: "60"
    nginx.ingress.kubernetes.io/proxy-send-timeout: "60"
    nginx.ingress.kubernetes.io/proxy-read-timeout: "60"
    cert-manager.io/cluster-issuer: "letsencrypt-prod"
spec:
  tls:
  - hosts:
    - api.content-sharing.com
    secretName: content-tls-secret
  rules:
  - host: api.content-sharing.com
    http:
      paths:
      - path: /api
        pathType: Prefix
        backend:
          service:
            name: content-gateway
            port:
              number: 8080
```

### 4.6 一键部署脚本

```bash
#!/bin/bash
# deploy-k8s.sh

set -e

NAMESPACE="content-sharing"
VERSION=${1:-"1.0.0"}
REGISTRY="registry.cn-hangzhou.aliyuncs.com/content-sharing"

echo "=== 开始部署内容分享平台 v${VERSION} ==="

# 创建命名空间
kubectl apply -f namespace.yaml

# 应用配置
kubectl apply -f configmap.yaml
kubectl apply -f secret.yaml

# 部署基础设施
kubectl apply -f mysql-statefulset.yaml
kubectl apply -f redis-statefulset.yaml
kubectl apply -f nacos-statefulset.yaml
kubectl apply -f rabbitmq-statefulset.yaml

# 等待基础设施就绪
echo "等待基础设施启动..."
kubectl wait --for=condition=ready pod -l app=mysql -n ${NAMESPACE} --timeout=300s
kubectl wait --for=condition=ready pod -l app=redis -n ${NAMESPACE} --timeout=300s
kubectl wait --for=condition=ready pod -l app=nacos -n ${NAMESPACE} --timeout=300s

# 部署业务服务
SERVICES=("gateway" "user-service" "content-service" "comment-service" 
          "recommend-service" "pay-service" "message-service" 
          "search-service" "stat-service" "queue-service")

for SERVICE in "${SERVICES[@]}"; do
    echo "部署 ${SERVICE}..."
    sed "s/\${VERSION}/${VERSION}/g" ${SERVICE}-deployment.yaml | kubectl apply -f -
done

# 部署Ingress
kubectl apply -f ingress.yaml

echo "=== 部署完成 ==="
kubectl get pods -n ${NAMESPACE}
```

---

## 五、CI/CD 流程

### 5.1 GitLab CI 配置

```yaml
# .gitlab-ci.yml
stages:
  - build
  - test
  - package
  - deploy

variables:
  MAVEN_OPTS: "-Dmaven.repo.local=.m2/repository"
  REGISTRY: registry.cn-hangzhou.aliyuncs.com/content-sharing
  VERSION: "${CI_COMMIT_SHORT_SHA}"

cache:
  paths:
    - .m2/repository/

# 构建阶段
build:
  stage: build
  image: maven:3.9-eclipse-temurin-22
  script:
    - mvn clean compile -DskipTests -P${CI_ENVIRONMENT_NAME:-dev}
  artifacts:
    paths:
      - "**/target/"
    expire_in: 1 hour

# 测试阶段
test:
  stage: test
  image: maven:3.9-eclipse-temurin-22
  script:
    - mvn test -P${CI_ENVIRONMENT_NAME:-dev}
  coverage: '/Total.*?([0-9]{1,3})%/'
  artifacts:
    reports:
      junit:
        - "**/target/surefire-reports/TEST-*.xml"

# 打包阶段
package:
  stage: package
  image: docker:24
  services:
    - docker:24-dind
  script:
    - docker login -u ${REGISTRY_USER} -p ${REGISTRY_PASSWORD} ${REGISTRY}
    - |
      for SERVICE in gateway user-service content-service comment-service \
                     recommend-service pay-service message-service \
                     search-service stat-service queue-service; do
        docker build -t ${REGISTRY}/content-${SERVICE}:${VERSION} \
                     -t ${REGISTRY}/content-${SERVICE}:latest \
                     -f Dockerfile.${SERVICE} .
        docker push ${REGISTRY}/content-${SERVICE}:${VERSION}
        docker push ${REGISTRY}/content-${SERVICE}:latest
      done
  only:
    - main
    - develop

# 部署到开发环境
deploy:dev:
  stage: deploy
  image: bitnami/kubectl:latest
  script:
    - kubectl config use-context dev-cluster
    - ./deploy-k8s.sh ${VERSION}
  environment:
    name: development
    url: https://dev-api.content-sharing.com
  only:
    - develop

# 部署到生产环境
deploy:prod:
  stage: deploy
  image: bitnami/kubectl:latest
  script:
    - kubectl config use-context prod-cluster
    - ./deploy-k8s.sh ${VERSION}
  environment:
    name: production
    url: https://api.content-sharing.com
  when: manual
  only:
    - main
```

### 5.2 Dockerfile 模板

```dockerfile
# Dockerfile.gateway
FROM eclipse-temurin:22-jre-alpine

LABEL maintainer="content-sharing-team"
LABEL service="content-gateway"

# 设置时区
RUN apk add --no-cache tzdata curl && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone && \
    apk del tzdata

# 创建非root用户
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# 复制jar包
COPY content-gateway/target/*.jar app.jar

# 设置权限
RUN chown -R appuser:appgroup /app

USER appuser

# JVM参数
ENV JVM_XMS=256m
ENV JVM_XMX=512m
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Xms${JVM_XMS} -Xmx${JVM_XMX} -jar app.jar"]
```

---

## 六、监控告警

### 6.1 Prometheus 配置

```yaml
# prometheus.yml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

alerting:
  alertmanagers:
  - static_configs:
    - targets:
      - alertmanager:9093

rule_files:
  - /etc/prometheus/rules/*.yml

scrape_configs:
  # Spring Boot Actuator
  - job_name: 'spring-boot-services'
    metrics_path: '/actuator/prometheus'
    static_configs:
    - targets:
      - gateway:8080
      - user-service:8081
      - content-service:8082
      - comment-service:8083
      - recommend-service:8084
      - pay-service:8085
      - message-service:8086
      - search-service:8087
      - stat-service:8088
      - queue-service:8089

  # MySQL Exporter
  - job_name: 'mysql'
    static_configs:
    - targets: ['mysql-exporter:9104']

  # Redis Exporter
  - job_name: 'redis'
    static_configs:
    - targets: ['redis-exporter:9121']

  # RabbitMQ
  - job_name: 'rabbitmq'
    static_configs:
    - targets: ['rabbitmq:15692']

  # Nacos
  - job_name: 'nacos'
    static_configs:
    - targets: ['nacos:8848']
```

### 6.2 告警规则

```yaml
# rules/alert-rules.yml
groups:
- name: content-sharing-alerts
  rules:
  # 服务宕机告警
  - alert: ServiceDown
    expr: up == 0
    for: 1m
    labels:
      severity: critical
    annotations:
      summary: "服务 {{ $labels.job }} 宕机"
      description: "服务 {{ $labels.instance }} 已经宕机超过1分钟"

  # 高CPU使用率
  - alert: HighCPUUsage
    expr: process_cpu_usage > 0.8
    for: 5m
    labels:
      severity: warning
    annotations:
      summary: "服务 {{ $labels.job }} CPU使用率过高"
      description: "CPU使用率 {{ $value | humanizePercentage }}"

  # 高内存使用率
  - alert: HighMemoryUsage
    expr: jvm_memory_used_bytes{area="heap"} / jvm_memory_max_bytes{area="heap"} > 0.85
    for: 5m
    labels:
      severity: warning
    annotations:
      summary: "服务 {{ $labels.job }} 堆内存使用率过高"
      description: "堆内存使用率 {{ $value | humanizePercentage }}"

  # HTTP错误率
  - alert: HighErrorRate
    expr: sum(rate(http_server_requests_seconds_count{status=~"5.."}[5m])) / sum(rate(http_server_requests_seconds_count[5m])) > 0.05
    for: 2m
    labels:
      severity: critical
    annotations:
      summary: "HTTP错误率过高"
      description: "5xx错误率 {{ $value | humanizePercentage }}"

  # 响应时间过长
  - alert: SlowResponse
    expr: histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[5m])) by (le)) > 2
    for: 5m
    labels:
      severity: warning
    annotations:
      summary: "响应时间过长"
      description: "P95响应时间 {{ $value }}s"
```

### 6.3 Grafana Dashboard

```json
{
  "dashboard": {
    "title": "内容分享平台监控",
    "panels": [
      {
        "title": "服务状态",
        "type": "stat",
        "targets": [
          {
            "expr": "up{job=~\"spring-boot-services\"}",
            "legendFormat": "{{ job }}"
          }
        ]
      },
      {
        "title": "请求QPS",
        "type": "graph",
        "targets": [
          {
            "expr": "sum(rate(http_server_requests_seconds_count[1m])) by (service)",
            "legendFormat": "{{ service }}"
          }
        ]
      },
      {
        "title": "响应时间(P95)",
        "type": "graph",
        "targets": [
          {
            "expr": "histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[5m])) by (le, service))",
            "legendFormat": "{{ service }}"
          }
        ]
      },
      {
        "title": "JVM堆内存",
        "type": "graph",
        "targets": [
          {
            "expr": "jvm_memory_used_bytes{area=\"heap\"}",
            "legendFormat": "{{ service }}"
          }
        ]
      }
    ]
  }
}
```

---

## 七、运维指南

### 7.1 日常运维命令

```bash
# 查看服务状态
kubectl get pods -n content-sharing
kubectl get svc -n content-sharing

# 查看日志
kubectl logs -f deployment/content-gateway -n content-sharing

# 进入容器
kubectl exec -it deployment/content-gateway -n content-sharing -- /bin/sh

# 重启服务
kubectl rollout restart deployment/content-gateway -n content-sharing

# 扩缩容
kubectl scale deployment/content-gateway --replicas=3 -n content-sharing

# 查看资源使用
kubectl top pods -n content-sharing
kubectl top nodes

# 查看事件
kubectl get events -n content-sharing --sort-by='.lastTimestamp'
```

### 7.2 备份策略

```bash
#!/bin/bash
# backup.sh

DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_DIR="/backup/${DATE}"

mkdir -p ${BACKUP_DIR}

# MySQL备份
kubectl exec -n content-sharing mysql-0 -- mysqldump -u root -p${MYSQL_PASSWORD} \
    --all-databases --single-transaction --quick --lock-tables=false \
    > ${BACKUP_DIR}/mysql_full.sql

# Redis备份
kubectl exec -n content-sharing redis-0 -- redis-cli BGSAVE
kubectl cp content-sharing/redis-0:/data/dump.rdb ${BACKUP_DIR}/redis_dump.rdb

# Nacos配置备份
kubectl exec -n content-sharing nacos-0 -- \
    curl -s "http://localhost:8848/nacos/v1/cs/configs?export=true&dataId=&group=&tenant=${NACOS_NAMESPACE}" \
    > ${BACKUP_DIR}/nacos_config.zip

# 上传到OSS
aliyun oss cp -r ${BACKUP_DIR} oss://content-backup/${DATE}/

# 清理30天前的备份
find /backup -type d -mtime +30 -exec rm -rf {} \;

echo "备份完成: ${BACKUP_DIR}"
```

### 7.3 故障排查

| 问题 | 可能原因 | 排查步骤 |
|------|----------|----------|
| 服务无法启动 | 配置错误/依赖未就绪 | 检查日志、环境变量、Nacos配置 |
| 服务注册失败 | Nacos连接问题 | 检查Nacos状态、网络连通性 |
| 数据库连接失败 | 连接池耗尽/网络问题 | 检查连接数、网络延迟 |
| 响应超时 | 服务负载过高 | 检查CPU/内存、扩容 |
| 消息堆积 | 消费者处理慢 | 检查RabbitMQ队列、增加消费者 |

### 7.4 性能调优

```yaml
# JVM调优参数
JAVA_OPTS: >
  -XX:+UseG1GC
  -XX:MaxGCPauseMillis=200
  -XX:+UseStringDeduplication
  -XX:+PrintGCDetails
  -XX:+PrintGCDateStamps
  -Xloggc:/app/logs/gc.log
  -XX:+HeapDumpOnOutOfMemoryError
  -XX:HeapDumpPath=/app/logs/heapdump.hprof

# 连接池配置
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      idle-timeout: 300000
      connection-timeout: 30000
      max-lifetime: 1200000

# Redis连接池
spring:
  data:
    redis:
      lettuce:
        pool:
          max-active: 16
          max-idle: 8
          min-idle: 2
```

---

## 八、安全加固

### 8.1 网络安全

```yaml
# NetworkPolicy
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: content-network-policy
  namespace: content-sharing
spec:
  podSelector: {}
  policyTypes:
  - Ingress
  - Egress
  ingress:
  - from:
    - namespaceSelector:
        matchLabels:
          name: content-sharing
    - podSelector:
        matchLabels:
          app: content-gateway
  egress:
  - to:
    - namespaceSelector:
        matchLabels:
          name: content-sharing
```

### 8.2 安全配置清单

| 项目 | 配置 |
|------|------|
| 容器安全 | 使用非root用户运行 |
| 镜像安全 | 使用最小化基础镜像 |
| 网络隔离 | NetworkPolicy限制访问 |
| 密钥管理 | 使用K8s Secret或Vault |
| 日志脱敏 | 敏感信息不记录日志 |
| API安全 | JWT认证 + 权限控制 |
| 数据加密 | 传输TLS + 存储加密 |

---

**文档版本：** v1.0  
**更新日期：** 2024-01-01  
**维护团队：** 内容分享平台运维组
