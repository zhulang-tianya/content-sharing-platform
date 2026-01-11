# content-framework 核心框架模块

## 模块概述

content-framework是内容分享平台的核心框架模块，基于RuoYi架构设计思想，封装了安全认证、权限管理、日志记录、数据权限等核心功能，为各个微服务提供统一的框架支撑。

## 核心功能

### 1. 安全认证

#### 1.1 JWT认证
- **JwtUtils**: JWT工具类，提供令牌生成、解析、验证等功能
- **JwtAuthenticationFilter**: JWT认证过滤器，拦截请求并验证令牌
- **LoginUser**: 登录用户信息封装类
- **SecurityUtils**: 安全工具类，获取当前登录用户信息

#### 1.2 密码加密
- **PasswordUtils**: 密码工具类，使用BCrypt算法进行密码加密和验证

### 2. 权限管理

#### 2.1 注解支持
- **@Log**: 操作日志注解，用于记录用户操作
- **@DataScope**: 数据权限注解，用于控制数据访问范围
- **@RateLimiter**: 限流注解，用于接口限流控制

#### 2.2 切面实现
- **LogAspect**: 日志切面，拦截@Log注解的方法，记录操作日志
- **DataScopeAspect**: 数据权限切面，拦截@DataScope注解的方法，应用数据权限过滤

### 3. 全局异常处理

#### 3.1 异常处理器
- **GlobalExceptionHandler**: 全局异常处理器，统一处理各类异常
  - 业务异常
  - 参数校验异常
  - 约束校验异常
  - 系统异常

### 4. 配置类

#### 4.1 安全配置
- **SecurityConfig**: Spring Security配置类
  - 禁用CSRF
  - 无状态会话管理
  - JWT认证过滤器配置
  - 请求授权配置

#### 4.2 数据库配置
- **MybatisPlusConfig**: MyBatis Plus配置类
  - 分页插件
  - 乐观锁插件
  - 防止全表更新插件

#### 4.3 缓存配置
- **RedisConfig**: Redis配置类
  - RedisTemplate配置
  - 序列化配置

### 5. 工具类

#### 5.1 Servlet工具
- **ServletUtils**: Servlet工具类
  - 获取HttpServletRequest
  - 获取请求参数
  - 获取请求头
  - 获取客户端IP地址
  - 获取请求URI和方法

## 使用说明

### 1. 添加依赖

在需要使用框架功能的微服务pom.xml中添加依赖：

```xml
<dependency>
    <groupId>com.content</groupId>
    <artifactId>content-framework</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 2. 启用安全认证

在启动类上添加@EnableWebSecurity注解：

```java
@SpringBootApplication
@EnableWebSecurity
public class ContentUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentUserServiceApplication.class, args);
    }
}
```

### 3. 使用操作日志

在Controller方法上添加@Log注解：

```java
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public Result<User> addUser(@RequestBody User user) {
        return Result.success(userService.addUser(user));
    }
}
```

### 4. 使用数据权限

在Service方法上添加@DataScope注解：

```java
@Service
public class UserService {
    
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }
}
```

### 5. 使用限流

在Controller方法上添加@RateLimiter注解：

```java
@RestController
@RequestMapping("/api")
public class ApiController {
    
    @RateLimiter(time = 60, count = 100)
    @GetMapping("/data")
    public Result<?> getData() {
        return Result.success(dataService.getData());
    }
}
```

### 6. 获取当前用户信息

使用SecurityUtils工具类获取当前登录用户信息：

```java
@RestController
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/info")
    public Result<UserInfo> getUserInfo() {
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        return Result.success(userService.getUserInfo(userId));
    }
}
```

## 配置说明

### JWT配置

在application.yml中配置JWT相关参数：

```yaml
jwt:
  secret: your-secret-key-at-least-256-bits-long
  expiration: 7200
```

### Redis配置

在application.yml中配置Redis连接信息：

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password:
      database: 0
```

## 注意事项

1. **JWT密钥**: 生产环境请使用强密钥，至少256位
2. **Token过期**: 默认过期时间为7200秒（2小时），可根据需求调整
3. **数据权限**: 数据权限切面需要结合具体的业务逻辑实现
4. **异常处理**: 全局异常处理器会捕获所有异常，确保业务异常使用BusinessException抛出
5. **日志记录**: 操作日志会记录到控制台，可扩展为写入数据库

## 扩展说明

### 自定义UserDetailsService

需要实现UserDetailsService接口，用于加载用户信息和权限：

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new LoginUser(user.getId(), user.getUsername(), user.getPassword(), getAuthorities(user));
    }
}
```

### 自定义数据权限

扩展DataScopeAspect切面，实现具体的数据权限过滤逻辑：

```java
@Aspect
@Component
public class CustomDataScopeAspect extends DataScopeAspect {
    
    @Override
    protected void handleDataScope(final JoinPoint point, DataScope dataScope) {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            return;
        }
        
        if (SecurityUtils.isAdmin(userId)) {
            return;
        }
        
        String deptAlias = dataScope.deptAlias();
        String userAlias = dataScope.userAlias();
        
        String sqlFilter = buildDataScopeSql(userId, deptAlias, userAlias);
        DataScopeHelper.setDataScope(sqlFilter);
    }
}
```

## 技术栈

- Spring Boot 3.2.5
- Spring Security
- JWT (jjwt)
- MyBatis Plus 3.5.7
- Redis
- AOP (AspectJ)
- Lombok
- Hutool

## 版本历史

### v1.0.0-SNAPSHOT
- 初始版本
- 实现JWT认证
- 实现操作日志
- 实现数据权限
- 实现全局异常处理
- 实现限流功能
