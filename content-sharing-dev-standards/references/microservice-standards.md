# 微服务开发规范

## 目录

1. [AI开发常见问题汇总](#ai开发常见问题汇总)
2. [模块职责划分规范](#模块职责划分规范)
3. [Bean命名规范](#bean命名规范)
4. [依赖注入规范](#依赖注入规范)
5. [服务层开发规范](#服务层开发规范)
6. [安全框架开发规范](#安全框架开发规范)
7. [开发流程规范](#开发流程规范)

---

## AI开发常见问题汇总

### 问题1：Bean名称冲突

**错误现象**：
```
Annotation-specified bean name 'permissionController' for bean class 
[com.content.framework.security.controller.PermissionController] 
conflicts with existing, non-compatible bean definition
```

**原因分析**：
- `content-framework` 和 `content-user-service` 中存在同名Controller
- Spring容器无法区分两个同名的Bean

**解决方案**：
```
❌ 错误做法：在framework中定义业务Controller

content-framework/
└── security/controller/
    └── PermissionController.java  # 与user-service冲突

✅ 正确做法：业务Controller只在业务模块定义

content-framework/
└── security/controller/
    └── AuthController.java        # 只保留框架级Controller

content-user-service/
└── controller/
    └── PermissionController.java  # 业务Controller
```

**规范要点**：
- 框架模块只定义框架级Controller（如认证、健康检查）
- 业务Controller只在业务服务模块定义
- 如需在框架提供默认实现，使用 `@ConditionalOnMissingBean`

---

### 问题2：跨模块Mapper依赖

**错误现象**：
```
Field userRoleMapper in SecurityPermissionService required a bean of type 
'com.content.framework.security.mapper.UserRoleMapper' that could not be found.
```

**原因分析**：
- `SecurityPermissionService` 在 `content-framework` 中
- 但依赖了 `UserRoleMapper`，而Mapper应该在业务模块
- 框架模块不应该直接访问数据库

**解决方案**：
```java
// ❌ 错误做法：框架服务直接依赖Mapper
@Service
public class SecurityPermissionService {
    @Autowired
    private UserRoleMapper userRoleMapper;  // 不应该有
    
    @Autowired
    private PermissionMapper permissionMapper;  // 不应该有
}

// ✅ 正确做法：框架服务从LoginUser获取权限信息
@Service
public class SecurityPermissionService {
    // 权限信息在登录时已加载到LoginUser中
    public boolean hasPermi(String permission) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        return loginUser.hasPermission(permission);
    }
}
```

**规范要点**：
- 框架模块不直接访问数据库
- 权限信息在登录时加载到用户上下文
- 框架服务从用户上下文获取数据

---

### 问题3：实体类分散

**错误现象**：
```
import com.content.framework.security.entity.Role;  // Role在framework
import com.content.user.entity.User;                 // User在user-service
```

**原因分析**：
- 实体类分散在多个模块
- 导致跨模块引用问题
- 违背单一职责原则

**解决方案**：
```
创建独立的实体模块：

content-entity/
└── src/main/java/com/content/entity/
    ├── User.java
    ├── Role.java
    ├── Dept.java
    ├── Menu.java
    ├── Permission.java
    ├── UserRole.java        # 关联实体
    └── RolePermission.java  # 关联实体
```

**规范要点**：
- 所有共享实体类放在 `content-entity` 模块
- 业务特定实体可放在业务模块
- 关联实体（如UserRole）也放在entity模块

---

### 问题4：构造函数缺失

**错误现象**：
```
无法将类 LoginUser 中的构造器 LoginUser 应用到给定类型
需要: 没有参数
找到: Long, String, <nulltype>, ArrayList<Object>
```

**原因分析**：
- 使用 `@Data` 注解时，只生成无参构造函数
- 代码中使用有参构造函数但未定义

**解决方案**：
```java
@Data
public class LoginUser implements UserDetails {
    // ... 字段定义
    
    // 必须显式定义无参构造函数（Lombok @Data会生成，但最好显式声明）
    public LoginUser() {
    }
    
    // 如需有参构造函数，使用 @AllArgsConstructor 或显式定义
    public LoginUser(Long userId, String username, String password, 
                     Collection<GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
```

**规范要点**：
- 实体类必须有无参构造函数
- 需要有参构造函数时显式定义
- 或使用 `@NoArgsConstructor` + `@AllArgsConstructor`

---

### 问题5：Mapper方法缺失

**错误现象**：
```
找不到符号: 方法 selectByUsername(java.lang.String)
位置: 类型为 UserMapper 的变量 userMapper
```

**原因分析**：
- Mapper接口继承了 `BaseMapper` 但未定义自定义方法
- 代码中调用了不存在的方法

**解决方案**：
```java
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    // 自定义方法必须显式定义
    @Select("SELECT * FROM sys_user WHERE username = #{username} AND deleted = 0")
    User selectByUsername(@Param("username") String username);
    
    @Select("SELECT * FROM sys_user WHERE phone = #{phone} AND deleted = 0")
    User selectByPhone(@Param("phone") String phone);
}
```

**规范要点**：
- 自定义查询方法必须在Mapper接口定义
- 使用 `@Select` 注解或XML映射文件
- BaseMapper只提供基础CRUD方法

---

### 问题6：实体字段缺失

**错误现象**：
```
找不到符号: 方法 getGender()
位置: 类型为 User 的变量 user
```

**原因分析**：
- 实体类定义时遗漏了某些字段
- 数据库表有字段但实体类未映射

**解决方案**：
```java
@Data
@TableName("sys_user")
public class User implements Serializable {
    // ... 其他字段
    
    // 确保实体类字段与数据库表一致
    private Integer gender;      // 性别
    private String avatar;       // 头像
    private Integer type;        // 用户类型
}
```

**规范要点**：
- 实体类字段必须与数据库表结构一致
- 新增数据库字段时同步更新实体类
- 使用代码生成器保持一致性

---

## 模块职责划分规范

### 模块职责表

| 模块 | 职责 | 可包含 | 不可包含 |
|------|------|--------|----------|
| content-entity | 实体定义 | Entity, DTO, VO, Query | 业务逻辑、数据库访问 |
| content-common | 公共工具 | 常量、枚举、异常、工具类 | 业务逻辑、框架依赖 |
| content-framework | 框架配置 | 安全配置、切面、缓存、过滤器 | 业务Controller、业务Service |
| content-user-service | 用户业务 | Controller, Service, Mapper | 其他业务的代码 |

### 各模块包结构规范

```
content-entity/
└── com.content.entity/
    ├── User.java
    ├── Role.java
    └── ...

content-common/
└── com.content.common/
    ├── constant/          # 常量
    ├── enums/             # 枚举
    ├── exception/         # 异常
    ├── result/            # 结果封装
    └── utils/             # 工具类

content-framework/
└── com.content.framework/
    ├── security/          # 安全配置
    │   ├── config/
    │   ├── filter/
    │   ├── handler/
    │   └── service/       # 框架级服务
    ├── aspectj/           # 切面
    ├── cache/             # 缓存配置
    └── handler/           # 全局处理器

content-user-service/
└── com.content.user/
    ├── controller/        # 控制器
    ├── service/           # 业务服务
    │   └── impl/
    ├── mapper/            # 数据访问
    └── config/            # 业务配置
```

---

## Bean命名规范

### 命名冲突避免策略

```java
// 策略1：使用不同的Bean名称
@Service("securityPermissionService")  // 框架中的权限服务
public class SecurityPermissionService {}

@Service  // 默认名称: permissionService
public class PermissionService implements com.content.user.service.PermissionService {}

// 策略2：使用 @ConditionalOnMissingBean
@Service
@ConditionalOnMissingBean(PermissionService.class)
public class DefaultPermissionService implements PermissionService {}

// 策略3：不同模块使用不同包名
// framework: com.content.framework.security.service.PermissionService
// user-service: com.content.user.service.PermissionService
```

### Bean命名约定

| 类型 | 命名规则 | 示例 |
|------|----------|------|
| Controller | {实体}Controller | UserController |
| Service接口 | {实体}Service | UserService |
| Service实现 | {实体}ServiceImpl | UserServiceImpl |
| Mapper | {实体}Mapper | UserMapper |
| 框架服务 | {功能}Service | TokenService, SecurityPermissionService |

---

## 依赖注入规范

### 注入方式选择

```java
// ✅ 推荐：构造函数注入（用于强制依赖）
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
}

// ✅ 可选：Setter注入（用于可选依赖）
@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    
    @Autowired(required = false)
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}

// ⚠️ 谨慎使用：字段注入（不推荐）
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;  // 不利于测试
}
```

### 循环依赖避免

```java
// ❌ 错误：循环依赖
@Service
public class UserService {
    @Autowired
    private RoleService roleService;
}

@Service
public class RoleService {
    @Autowired
    private UserService userService;
}

// ✅ 正确：使用事件驱动或接口解耦
@Service
public class UserService {
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    public void updateUser(User user) {
        // 更新用户
        eventPublisher.publishEvent(new UserUpdatedEvent(user));
    }
}

@Service
public class RoleService {
    @EventListener
    public void onUserUpdated(UserUpdatedEvent event) {
        // 处理用户更新事件
    }
}
```

---

## 服务层开发规范

### Service接口设计

```java
/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户实体
     */
    User getById(Long id);
    
    /**
     * 分页查询用户列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<User> pageUsers(UserQuery query);
    
    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 是否成功
     * @throws BusinessException 当用户名已存在时抛出
     */
    boolean save(User user);
}
```

### Service实现规范

```java
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    
    @Override
    public User getById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException("用户ID无效");
        }
        return userMapper.selectById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(User user) {
        // 参数校验
        validateUser(user);
        
        // 业务逻辑
        if (checkUsernameExists(user.getUsername(), user.getTenantId())) {
            throw new BusinessException("用户名已存在");
        }
        
        // 设置默认值
        user.setDeleted(0);
        user.setCreateTime(LocalDateTime.now());
        
        // 保存
        int result = userMapper.insert(user);
        
        log.info("新增用户成功，userId={}, username={}", user.getId(), user.getUsername());
        return result > 0;
    }
}
```

---

## 安全框架开发规范

### 权限加载时机

```
用户登录流程：
1. UsernamePasswordAuthenticationFilter 接收登录请求
2. UserDetailsServiceImpl.loadUserByUsername()
   ├── 查询用户信息
   ├── 查询用户角色
   ├── 查询用户权限
   └── 构建 LoginUser 对象
3. 返回 Authentication（包含 LoginUser）
4. TokenService 生成 JWT Token
5. 返回 Token 给客户端

后续请求：
1. JwtAuthenticationFilter 解析 Token
2. 从 Token 获取用户信息
3. SecurityPermissionService 校验权限
   └── 从 LoginUser 获取权限信息（不查数据库）
```

### 权限校验服务设计

```java
/**
 * 安全权限服务（框架内部使用）
 * 
 * 职责：从用户上下文获取权限信息进行校验
 * 不直接访问数据库
 */
@Service("securityPermissionService")
public class SecurityPermissionService {
    
    public boolean hasPermi(String permission) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null) {
            return false;
        }
        if (loginUser.isAdmin()) {
            return true;
        }
        return loginUser.hasPermission(permission);
    }
}
```

---

## 开发流程规范

### 新增功能开发流程

```
1. 需求分析
   ├── 确定功能属于哪个模块
   ├── 确定是否需要新增实体
   └── 确定是否需要新增表

2. 数据库设计
   ├── 设计表结构
   ├── 添加字段注释
   └── 创建索引

3. 实体类开发
   ├── 在 content-entity 创建实体类
   ├── 添加字段注释
   └── 使用 Lombok 注解

4. Mapper开发
   ├── 在业务模块创建 Mapper 接口
   ├── 定义自定义方法
   └── 编写 SQL（注解或XML）

5. Service开发
   ├── 定义 Service 接口
   ├── 实现 ServiceImpl
   └── 添加事务注解

6. Controller开发
   ├── 定义 REST 接口
   ├── 添加权限注解
   └── 添加 Swagger 注解

7. 测试验证
   ├── 单元测试
   ├── 接口测试
   └── 集成测试

8. 代码提交
   ├── 检查代码规范
   ├── 编写提交信息
   └── 推送到远程
```

### 代码审查清单

```
□ 实体类是否放在 content-entity 模块
□ Service 是否定义接口
□ 是否使用构造函数注入
□ 是否有事务注解
□ 异常处理是否规范
□ 日志记录是否完整
□ 权限注解是否添加
□ 接口文档是否完整
□ 代码是否有注释
□ 是否有单元测试
```
