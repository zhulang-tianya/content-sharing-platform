# 命名规范

## 目录

1. [命名风格总则](#命名风格总则)
2. [类与接口命名](#类与接口命名)
3. [方法命名](#方法命名)
4. [变量命名](#变量命名)
5. [常量命名](#常量命名)
6. [包命名](#包命名)
7. [数据库命名](#数据库命名)
8. [长度限制](#长度限制)
9. [特殊前缀后缀规则](#特殊前缀后缀规则)
10. [禁止使用的命名模式](#禁止使用的命名模式)
11. [命名示例汇总](#命名示例汇总)

---

## 命名风格总则

| 标识符类型 | 命名风格 | 示例 |
|-----------|---------|------|
| 类名 | 大驼峰（PascalCase） | `UserController` |
| 接口名 | 大驼峰（PascalCase） | `UserService` |
| 方法名 | 小驼峰（camelCase） | `getUserById` |
| 变量名 | 小驼峰（camelCase） | `userName` |
| 常量名 | 全大写下划线（UPPER_SNAKE_CASE） | `MAX_RETRY_COUNT` |
| 包名 | 全小写 | `com.content.user` |
| 数据库表名 | 小写下划线 | `sys_user` |
| 数据库字段名 | 小写下划线 | `user_name` |

---

## 类与接口命名

### 基本规则

- 使用**大驼峰命名法**（PascalCase）
- 名词或名词短语，体现类职责
- 见名知意，避免无意义缩写

### 类名后缀约定

| 类型 | 后缀 | 示例 |
|-----|------|------|
| 控制器 | Controller | `UserController` |
| 服务接口 | Service | `UserService` |
| 服务实现 | ServiceImpl | `UserServiceImpl` |
| 数据访问层 | Mapper | `UserMapper` |
| 实体类 | 无特定后缀 | `User` |
| 数据传输对象 | DTO | `UserDTO` |
| 视图对象 | VO | `UserVO` |
| 查询对象 | Query | `UserQuery` |
| 配置类 | Config | `RedisConfig` |
| 工具类 | Utils | `StringUtils` |
| 异常类 | Exception | `BusinessException` |
| 枚举类 | 无特定后缀 | `UserStatus` |
| 常量类 | Constants | `SecurityConstants` |

### 接口命名规则

```java
// 接口：使用名词或形容词
public interface UserService {}
public interface Comparable {}

// 抽象类：使用Abstract前缀
public abstract class AbstractUserService {}

// 实现类：使用Impl后缀
public class UserServiceImpl implements UserService {}
```

### 示例

```java
// 正确示例
public class UserController {}
public class UserServiceImpl implements UserService {}
public class UserMapper extends BaseMapper<User> {}
public class UserDTO implements Serializable {}
public class BusinessException extends BaseException {}

// 错误示例
public class userController {}     // 首字母应大写
public class UserControllerImpl {} // 实现类应使用Impl后缀
public class UController {}        // 缩写无意义
public class user_service {}       // 不应使用下划线
```

---

## 方法命名

### 基本规则

- 使用**小驼峰命名法**（camelCase）
- 动词或动词短语开头
- 第一个单词尽量体现方法功能

### 方法名前缀约定

| 操作类型 | 前缀 | 示例 | 说明 |
|---------|------|------|------|
| 查询单个 | get | `getUserById` | 返回单个对象 |
| 查询列表 | list | `listUsers` | 返回列表 |
| 查询分页 | page | `pageUsers` | 返回分页结果 |
| 新增 | save/add/create | `saveUser` | 新增操作 |
| 修改 | update/modify | `updateUser` | 修改操作 |
| 删除 | delete/remove | `deleteUser` | 删除操作 |
| 统计 | count | `countUsers` | 统计数量 |
| 判断 | is/has/can | `isValid` | 返回boolean |
| 转换 | to/convert | `toDTO` | 类型转换 |
| 校验 | validate/check | `validateUser` | 校验操作 |

### 示例

```java
// 正确示例
public User getUserById(Long id) {}
public List<User> listUsers(UserQuery query) {}
public PageResult<User> pageUsers(UserQuery query) {}
public void saveUser(User user) {}
public void updateUser(User user) {}
public void deleteUser(Long id) {}
public boolean isValid(String value) {}
public UserDTO toDTO(User entity) {}

// 错误示例
public User GetUserById(Long id) {}  // 首字母应小写
public User user(Long id) {}         // 缺少动词前缀
public User get_user(Long id) {}     // 不应使用下划线
public User getUser(Long id) {}      // 缺少查询条件描述
```

---

## 变量命名

### 基本规则

- 使用**小驼峰命名法**（camelCase）
- 名词或名词短语
- 见名知意，避免单字母命名（循环变量除外）

### 成员变量

```java
// 正确示例
private String userName;
private Integer userAge;
private List<User> userList;
private Map<String, Object> userMap;

// 错误示例
private String username;     // 多单词应使用驼峰
private String userNameStr;  // 不应使用类型后缀
private String s;            // 无意义命名
private String user_name;    // 不应使用下划线
```

### 局部变量

```java
// 正确示例
String userName = user.getName();
int totalCount = list.size();
User currentUser = getCurrentUser();

// 循环变量可使用单字母
for (int i = 0; i < list.size(); i++) {
    User user = list.get(i);
}

// 或使用更清晰的命名
for (User user : userList) {
    processUser(user);
}
```

### 集合变量

```java
// 正确示例 - 使用复数形式或后缀
List<User> users = new ArrayList<>();
List<User> userList = new ArrayList<>();
Map<Long, User> userMap = new HashMap<>();
Set<String> userIdSet = new HashSet<>();

// 错误示例
List<User> user = new ArrayList<>();  // 应使用复数
List<User> userArrayList = new ArrayList<>();  // 不应包含实现类名
```

### 布尔变量

```java
// 正确示例 - 使用is/has/can前缀
private boolean isValid;
private boolean hasPermission;
private boolean canEdit;
private boolean deleted;  // 或使用形容词

// 错误示例
private boolean valid;     // 缺少is前缀
private boolean isDelete;  // 应使用形容词或has前缀
private boolean flag;      // 无意义命名
```

---

## 常量命名

### 基本规则

- 使用**全大写下划线命名法**（UPPER_SNAKE_CASE）
- 单词间用下划线分隔
- 使用`static final`修饰

### 示例

```java
// 正确示例
public static final String MAX_RETRY_COUNT = "3";
public static final int DEFAULT_PAGE_SIZE = 10;
public static final long MAX_FILE_SIZE = 10 * 1024 * 1024L;
public static final String CHARSET_UTF8 = "UTF-8";

// 错误示例
public static final String maxRetryCount = "3";  // 应全大写
public static final String MAXRETRYCOUNT = "3";  // 缺少下划线分隔
public static final String MAX_RETRY_COUNT_STR = "3";  // 不应使用类型后缀
```

### 常量类组织

```java
public class SecurityConstants {
    private SecurityConstants() {}
    
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final long TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000L;
}
```

---

## 包命名

### 基本规则

- 使用**全小写**
- 单词间用点分隔
- 使用有意义的单词，避免缩写

### 包结构规范

```
com.content.{module}/
├── controller/     # 控制器层
├── service/        # 服务层
│   └── impl/       # 服务实现
├── mapper/         # 数据访问层
├── entity/         # 实体类
├── dto/            # 数据传输对象
├── vo/             # 视图对象
├── query/          # 查询对象
├── config/         # 配置类
├── utils/          # 工具类
├── constants/      # 常量类
├── enums/          # 枚举类
├── exception/      # 异常类
└── aspect/         # 切面类
```

### 示例

```java
// 正确示例
package com.content.user.controller;
package com.content.user.service.impl;
package com.content.common.utils;

// 错误示例
package com.content.User.Controller;  // 应全小写
package com.content.user_controller;  // 不应使用下划线
package com.content.usr.ctrl;         // 不应使用缩写
```

---

## 数据库命名

### 表命名规则

- 使用**小写下划线命名法**
- 使用模块前缀
- 使用单数形式

| 模块 | 前缀 | 示例 |
|-----|------|------|
| 系统模块 | sys_ | `sys_user`, `sys_role` |
| 内容模块 | content_ | `content_article`, `content_chapter` |
| 评论模块 | comment_ | `comment_article`, `comment_reply` |
| 支付模块 | pay_ | `pay_order`, `pay_record` |

### 字段命名规则

- 使用**小写下划线命名法**
- 使用有意义的字段名
- 布尔类型使用`is_`前缀

```sql
-- 正确示例
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY,
    user_name VARCHAR(50),
    user_age INT,
    is_deleted TINYINT(1),
    create_time DATETIME,
    update_time DATETIME
);

-- 错误示例
CREATE TABLE SysUser (        -- 应全小写
    userId BIGINT,            -- 应使用下划线
    userName VARCHAR(50),     -- 应使用下划线
    deleted TINYINT(1),       -- 布尔应使用is_前缀
    t_create DATETIME         -- 不应使用无意义前缀
);
```

---

## 长度限制

| 标识符类型 | 最小长度 | 最大长度 | 建议 |
|-----------|---------|---------|------|
| 类名 | 3 | 50 | 15-30字符 |
| 接口名 | 3 | 50 | 15-30字符 |
| 方法名 | 3 | 50 | 10-30字符 |
| 变量名 | 2 | 30 | 5-20字符 |
| 常量名 | 3 | 50 | 10-30字符 |
| 包名 | 3 | 30 | 5-20字符 |
| 数据库表名 | 3 | 30 | 10-25字符 |
| 数据库字段名 | 2 | 30 | 5-20字符 |

---

## 特殊前缀后缀规则

### 必须使用的前缀

| 场景 | 前缀 | 示例 |
|-----|------|------|
| 抽象类 | Abstract | `AbstractUserService` |
| 测试类 | 无（使用Test后缀） | `UserServiceTest` |

### 必须使用的后缀

| 场景 | 后缀 | 示例 |
|-----|------|------|
| 服务实现类 | Impl | `UserServiceImpl` |
| 数据传输对象 | DTO | `UserDTO` |
| 视图对象 | VO | `UserVO` |
| 查询对象 | Query | `UserQuery` |
| 配置类 | Config | `RedisConfig` |
| 工具类 | Utils | `StringUtils` |
| 异常类 | Exception | `BusinessException` |
| 测试类 | Test | `UserServiceTest` |

### 禁止使用的前缀后缀

| 禁止项 | 错误示例 | 正确示例 |
|-------|---------|---------|
| 类型后缀 | `userNameStr`, `userIdInt` | `userName`, `userId` |
| 实现类名后缀 | `userArrayList` | `userList` |
| 无意义前缀 | `mUserName`, `_userName` | `userName` |
| 数字后缀 | `userName1`, `userName2` | `oldUserName`, `newUserName` |

---

## 禁止使用的命名模式

### 1. 拼音与英文混合

```java
// 错误示例
private String mingZi;        // 纯拼音
private String userNameMing;  // 英文拼音混合
private String userXingMing;  // 英文拼音混合

// 正确示例
private String userName;
private String realName;
```

### 2. 无意义缩写

```java
// 错误示例
private String un;    // userName缩写
private int ua;       // userAge缩写
private User u;       // user缩写
private List<User> ul; // userList缩写

// 正确示例
private String userName;
private int userAge;
private User user;
private List<User> userList;
```

### 3. 保留字使用

```java
// 错误示例
private int class;     // 保留字
private String default; // 保留字
private int int;       // 保留字

// 正确示例
private int classType;
private String defaultValue;
private int intValue;
```

### 4. 无意义命名

```java
// 错误示例
private String a;
private String temp;
private String flag;
private String data;
private String obj;

// 正确示例
private String userName;
private String tempFileName;
private boolean isValid;
private String responseData;
private Object result;
```

### 5. 大小写不规范

```java
// 错误示例
public class usercontroller {}    // 类名应大驼峰
private String UserName;          // 变量应小驼峰
public static final String MaxSize = "10"; // 常量应全大写

// 正确示例
public class UserController {}
private String userName;
public static final String MAX_SIZE = "10";
```

---

## 命名示例汇总

### 完整类示例

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
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现类
 *
 * @author content-platform
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public User getUserById(Long userId) {
        if (userId == null || userId <= 0) {
            throw new BusinessException("用户ID无效");
        }
        return userMapper.selectById(userId);
    }

    @Override
    public List<User> listUsers(UserQuery query) {
        return userMapper.selectList(query);
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = toEntity(userDTO);
        userMapper.insert(user);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user = toEntity(userDTO);
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userMapper.deleteById(userId);
    }

    private User toEntity(UserDTO dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setUserAge(dto.getUserAge());
        return user;
    }
}
```
