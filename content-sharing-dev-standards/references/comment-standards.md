# 注释标准

## 目录

1. [Javadoc规范](#javadoc规范)
2. [类/接口注释](#类接口注释)
3. [方法注释](#方法注释)
4. [字段注释](#字段注释)
5. [行内注释](#行内注释)
6. [注释格式规范](#注释格式规范)
7. [注释示例汇总](#注释示例汇总)

---

## Javadoc规范

### 强制内容要求

所有**公共类、接口、公共方法**必须编写Javadoc注释。

| 元素 | 强制性 | 说明 |
|-----|-------|------|
| 类/接口描述 | 强制 | 完整描述类/接口的职责和功能 |
| @author | 强制 | 作者标识 |
| @version | 推荐 | 版本信息 |
| @since | 推荐 | 起始版本 |
| @param | 强制 | 所有参数必须说明 |
| @return | 强制 | 返回值说明（void除外） |
| @throws | 强制 | 所有可能抛出的异常 |

### Javadoc标签顺序

```java
/**
 * 描述内容
 * <p>详细说明（可选）</p>
 *
 * @author 作者
 * @version 版本
 * @since 起始版本
 * @param 参数名 参数说明
 * @return 返回值说明
 * @throws 异常类型 异常说明
 * @see 相关类/方法
 * @deprecated 废弃说明
 */
```

### 格式要求

| 规则 | 说明 |
|-----|------|
| 缩进 | 描述内容与`*`之间保留一个空格 |
| 换行 | 每行不超过80字符，自动换行 |
| 空行 | 描述与标签之间、不同类型标签之间保留空行 |
| 标点 | 描述内容以句号结尾 |

---

## 类/接口注释

### 必须包含的内容

1. **完整描述**：类/接口的职责和功能
2. **作者标识**：使用`@author`
3. **版本信息**：使用`@version`（推荐）
4. **起始版本**：使用`@since`（推荐）

### 类注释模板

```java
/**
 * 用户服务实现类
 * <p>
 * 提供用户注册、登录、信息管理、权限验证等功能。
 * 支持多租户数据隔离，集成Spring Security安全框架。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 * @since 2024-01-01
 * @see UserService
 * @see UserMapper
 */
@Service
public class UserServiceImpl implements UserService {
    // 类实现
}
```

### 接口注释模板

```java
/**
 * 用户服务接口
 * <p>
 * 定义用户相关的业务操作，包括用户CRUD、认证授权等功能。
 * 所有方法均支持多租户数据隔离。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface UserService {

    /**
     * 根据ID获取用户信息
     *
     * @param userId 用户ID，不能为空，必须大于0
     * @return 用户信息对象，如果不存在返回null
     * @throws BusinessException 当用户不存在时抛出
     */
    User getUserById(Long userId);
}
```

### 枚举类注释模板

```java
/**
 * 用户状态枚举
 * <p>
 * 定义用户账号的状态类型，用于控制用户账号的可用性。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 * @since 2024-01-01
 */
@Getter
@AllArgsConstructor
public enum UserStatus {

    /**
     * 正常状态
     */
    NORMAL(0, "正常"),

    /**
     * 禁用状态
     */
    DISABLED(1, "禁用"),

    /**
     * 锁定状态
     */
    LOCKED(2, "锁定");

    private final Integer code;
    private final String desc;
}
```

### 常量类注释模板

```java
/**
 * 安全相关常量
 * <p>
 * 定义JWT令牌、认证头、权限标识等安全相关的常量值。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 * @since 2024-01-01
 */
public final class SecurityConstants {

    private SecurityConstants() {}

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 认证请求头名称
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    /**
     * 令牌过期时间（毫秒），默认24小时
     */
    public static final long TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000L;
}
```

---

## 方法注释

### 必须包含的内容

1. **方法描述**：简洁说明方法功能
2. **@param**：所有参数的说明，包括含义、取值范围、约束条件
3. **@return**：返回值说明（void除外）
4. **@throws**：所有可能抛出的异常及触发场景

### 方法注释模板

```java
/**
 * 分页查询用户列表
 * <p>
 * 根据查询条件分页获取用户列表，支持按用户名、状态、创建时间等条件筛选。
 * 结果按创建时间倒序排列。
 * </p>
 *
 * @param query 查询条件对象，不能为null
 *              - pageNum: 页码，必须大于0，默认为1
 *              - pageSize: 每页大小，必须大于0且不超过100，默认为10
 *              - userName: 用户名，支持模糊查询，可为null
 *              - status: 用户状态，可为null表示查询所有状态
 * @return 分页结果对象，包含用户列表和分页信息，不会返回null
 * @throws BusinessException 当查询条件不合法时抛出
 * @throws AuthException 当无权限访问时抛出
 */
@Override
public PageResult<UserVO> pageUsers(UserQuery query) {
    // 方法实现
}
```

### 参数说明规范

```java
/**
 * 更新用户状态
 *
 * @param userId 用户ID，不能为null，必须大于0
 * @param status 目标状态，取值范围：
 *               - 0: 正常
 *               - 1: 禁用
 *               - 2: 锁定
 * @param operatorId 操作人ID，用于记录操作日志，不能为null
 * @return 更新是否成功，true表示成功，false表示失败
 * @throws BusinessException 当用户不存在或状态转换非法时抛出
 */
public boolean updateUserStatus(Long userId, Integer status, Long operatorId) {
    // 方法实现
}
```

### 简单方法注释

对于简单的getter/setter或一目了然的方法，可简化注释：

```java
/**
 * 获取用户ID
 *
 * @return 用户ID
 */
public Long getUserId() {
    return userId;
}

/**
 * 设置用户名
 *
 * @param userName 用户名
 */
public void setUserName(String userName) {
    this.userName = userName;
}
```

### 重写方法注释

重写方法可以简化注释，但必须保留核心说明：

```java
/**
 * {@inheritDoc}
 * <p>
 * 本实现支持多租户数据隔离。
 * </p>
 */
@Override
public User getUserById(Long userId) {
    // 方法实现
}
```

---

## 字段注释

### 公共字段注释

公共字段必须添加Javadoc注释：

```java
/**
 * 用户ID，主键，自增
 */
private Long id;

/**
 * 用户名，唯一，长度3-50字符
 */
private String userName;

/**
 * 用户年龄，取值范围0-150
 */
private Integer userAge;

/**
 * 用户状态，参考{@link UserStatus}
 */
private Integer status;

/**
 * 创建时间，自动设置，不允许修改
 */
private LocalDateTime createTime;
```

### 私有字段注释

私有字段可以使用行内注释或Javadoc：

```java
/** 用户ID */
private Long id;

/** 用户名 */
private String userName;

// 用户状态：0-正常，1-禁用，2-锁定
private Integer status;
```

### 使用Lombok时的注释

使用Lombok时，字段注释会自动生成到getter方法：

```java
/**
 * 用户实体类
 *
 * @author content-platform
 * @version 1.0.0
 */
@Data
@TableName("sys_user")
public class User {

    /**
     * 用户ID，主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名，唯一标识
     */
    private String userName;

    /**
     * 用户状态
     * @see UserStatus
     */
    private Integer status;
}
```

---

## 行内注释

### 适用场景

| 场景 | 说明 | 示例 |
|-----|------|------|
| 复杂逻辑说明 | 解释复杂的业务逻辑或算法 | 计算公式、业务规则 |
| 特殊处理说明 | 说明特殊处理的原因 | 兼容性处理、边界情况 |
| 临时解决方案 | 标记临时的解决方案 | TODO、FIXME |
| 重要提示 | 提醒注意事项 | 性能敏感、线程安全 |
| 代码意图说明 | 解释代码的意图而非实现 | 为什么这样做 |

### 位置规范

```java
// 正确：注释在代码上方，与代码之间保留一个空行
// 计算用户积分，积分 = 基础分 + 活动分 * 倍率
int score = baseScore + activityScore * multiplier;

// 正确：短注释可以在代码右侧
int maxRetry = 3;  // 最大重试次数

// 错误：注释不应与代码在同一行（短注释除外）
int score = baseScore + activityScore * multiplier; // 计算用户积分
```

### 内容要求

```java
// 正确：解释意图和原因
// 使用双重检查锁定确保单例模式的线程安全
if (instance == null) {
    synchronized (Singleton.class) {
        if (instance == null) {
            instance = new Singleton();
        }
    }
}

// 错误：描述实现细节（代码本身已经说明）
// 判断instance是否为null
if (instance == null) {
    // 创建新的实例
    instance = new Singleton();
}
```

### TODO和FIXME规范

```java
// TODO: 待实现的功能
// TODO(author): 2024-01-01 添加缓存支持

// FIXME: 需要修复的问题
// FIXME: 在高并发下可能存在线程安全问题

// HACK: 临时的解决方案
// HACK: 临时处理，等待上游系统修复后移除

// XXX: 需要改进的地方
// XXX: 性能较差，需要优化算法
```

### 复杂逻辑注释示例

```java
/**
 * 计算用户等级
 * <p>
 * 等级计算规则：
 * 1. 基础等级 = 积分 / 1000
 * 2. VIP用户等级 +1
 * 3. 等级上限为10级
 * </p>
 */
public int calculateUserLevel(User user) {
    // 基础等级计算：每1000积分为1级
    int baseLevel = user.getScore() / 1000;
    
    // VIP用户额外增加1级
    if (user.isVip()) {
        baseLevel += 1;
    }
    
    // 等级上限为10级
    return Math.min(baseLevel, 10);
}
```

---

## 注释格式规范

### 缩进规则

```java
/**
 * 类描述
 * <p>
 * 详细说明内容。
 * </p>
 * <ul>
 *   <li>列表项1</li>
 *   <li>列表项2</li>
 * </ul>
 *
 * @author 作者
 */
public class Example {
    // 方法内容
}
```

### 换行标准

```java
/**
 * 这是一个很长的方法描述，当描述内容超过80个字符时，
 * 应该进行换行处理，保持代码的可读性。
 * <p>
 * 详细说明也可以分行书写，每行不超过80字符。
 * </p>
 *
 * @param param1 参数1的说明，如果说明很长也应该换行
 * @param param2 参数2的说明
 * @return 返回值说明
 */
public String method(String param1, String param2) {
    return param1 + param2;
}
```

### HTML标签使用

```java
/**
 * 用户服务类
 * <p>
 * 提供以下功能：
 * </p>
 * <ul>
 *   <li>用户注册</li>
 *   <li>用户登录</li>
 *   <li>信息管理</li>
 * </ul>
 * <p>
 * 使用示例：
 * <pre>{@code
 * UserService userService = new UserServiceImpl();
 * User user = userService.getUserById(1L);
 * }</pre>
 * </p>
 *
 * @author content-platform
 * @see User
 * @see UserMapper
 */
public class UserServiceImpl implements UserService {
}
```

---

## 注释示例汇总

### 完整类注释示例

```java
package com.content.user.service.impl;

import com.content.common.exception.BusinessException;
import com.content.user.dto.UserDTO;
import com.content.user.entity.User;
import com.content.user.mapper.UserMapper;
import com.content.user.query.UserQuery;
import com.content.user.service.UserService;
import com.content.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现类
 * <p>
 * 提供用户相关的业务操作，包括：
 * </p>
 * <ul>
 *   <li>用户注册与登录</li>
 *   <li>用户信息管理</li>
 *   <li>用户状态管理</li>
 *   <li>用户权限验证</li>
 * </ul>
 * <p>
 * 本实现支持多租户数据隔离，集成Spring Cache缓存，
 * 所有公共方法均已添加事务控制。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 * @since 2024-01-01
 * @see UserService
 * @see UserMapper
 * @see UserDTO
 * @see UserVO
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    /**
     * 根据ID获取用户信息
     * <p>
     * 优先从缓存获取，缓存未命中时查询数据库并写入缓存。
     * </p>
     *
     * @param userId 用户ID，不能为null，必须大于0
     * @return 用户信息对象，如果不存在返回null
     * @throws BusinessException 当userId为null或小于等于0时抛出
     */
    @Override
    @Cacheable(value = "user", key = "#userId")
    public User getUserById(Long userId) {
        if (userId == null || userId <= 0) {
            throw new BusinessException("用户ID无效");
        }
        return userMapper.selectById(userId);
    }

    /**
     * 分页查询用户列表
     *
     * @param query 查询条件，不能为null
     * @return 分页结果，不会返回null
     */
    @Override
    public PageResult<UserVO> pageUsers(UserQuery query) {
        // 参数校验
        validateQuery(query);
        
        // 执行查询
        List<User> users = userMapper.selectPage(query);
        long total = userMapper.selectCount(query);
        
        // 转换结果
        List<UserVO> voList = users.stream()
            .map(this::toVO)
            .toList();
        
        return new PageResult<>(voList, total, query.getPageNum(), query.getPageSize());
    }

    /**
     * 保存用户信息
     * <p>
     * 新增用户时自动设置创建时间和初始状态。
     * </p>
     *
     * @param userDTO 用户信息DTO，不能为null
     * @return 保存成功返回true，否则返回false
     * @throws BusinessException 当用户名已存在时抛出
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", allEntries = true)
    public boolean saveUser(UserDTO userDTO) {
        // 检查用户名是否已存在
        if (existsByUserName(userDTO.getUserName())) {
            throw new BusinessException("用户名已存在");
        }
        
        User user = toEntity(userDTO);
        user.setStatus(UserStatus.NORMAL.getCode());
        
        return userMapper.insert(user) > 0;
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 目标状态，参考{@link UserStatus}
     * @return 更新是否成功
     * @throws BusinessException 当用户不存在或状态转换非法时抛出
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", key = "#userId")
    public boolean updateUserStatus(Long userId, Integer status) {
        User user = getUserById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 状态转换校验
        validateStatusTransition(user.getStatus(), status);
        
        user.setStatus(status);
        return userMapper.updateById(user) > 0;
    }

    // ==================== 私有方法 ====================

    /**
     * 校验查询参数
     */
    private void validateQuery(UserQuery query) {
        if (query.getPageNum() == null || query.getPageNum() <= 0) {
            query.setPageNum(1);
        }
        if (query.getPageSize() == null || query.getPageSize() <= 0) {
            query.setPageSize(10);
        }
        // 限制每页最大数量
        if (query.getPageSize() > 100) {
            query.setPageSize(100);
        }
    }

    /**
     * 检查用户名是否已存在
     */
    private boolean existsByUserName(String userName) {
        return userMapper.selectByUserName(userName) != null;
    }

    /**
     * 校验状态转换是否合法
     */
    private void validateStatusTransition(Integer currentStatus, Integer targetStatus) {
        // 禁用状态不能直接转为正常状态
        if (UserStatus.DISABLED.getCode().equals(currentStatus) 
            && UserStatus.NORMAL.getCode().equals(targetStatus)) {
            throw new BusinessException("禁用用户需先解锁才能启用");
        }
    }

    /**
     * DTO转Entity
     */
    private User toEntity(UserDTO dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setUserAge(dto.getUserAge());
        return user;
    }

    /**
     * Entity转VO
     */
    private UserVO toVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUserName(user.getUserName());
        vo.setUserAge(user.getUserAge());
        vo.setStatus(user.getStatus());
        return vo;
    }
}
```
