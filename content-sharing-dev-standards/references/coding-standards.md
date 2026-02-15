# 编程规范

## 目录

1. [代码结构规范](#代码结构规范)
2. [语句规范](#语句规范)
3. [代码长度限制](#代码长度限制)
4. [语言特性使用规范](#语言特性使用规范)
5. [集合与泛型规范](#集合与泛型规范)
6. [并发编程规范](#并发编程规范)
7. [数据库操作规范](#数据库操作规范)
8. [API设计规范](#api设计规范)
9. [代码整洁规范](#代码整洁规范)

---

## 代码结构规范

### 包结构划分

```
com.content.{module}/
├── controller/           # 控制器层 - 处理HTTP请求
│   └── XxxController.java
├── service/              # 服务层 - 业务逻辑
│   ├── XxxService.java       # 服务接口
│   └── impl/
│       └── XxxServiceImpl.java # 服务实现
├── mapper/               # 数据访问层 - 数据库操作
│   └── XxxMapper.java
├── entity/               # 实体类 - 数据库映射
│   └── Xxx.java
├── dto/                  # 数据传输对象 - 接口入参
│   └── XxxDTO.java
├── vo/                   # 视图对象 - 接口出参
│   └── XxxVO.java
├── query/                # 查询对象 - 查询条件
│   └── XxxQuery.java
├── config/               # 配置类
│   └── XxxConfig.java
├── utils/                # 工具类
│   └── XxxUtils.java
├── constants/            # 常量类
│   └── XxxConstants.java
├── enums/                # 枚举类
│   └── XxxEnum.java
├── exception/            # 异常类
│   └── XxxException.java
├── aspect/               # 切面类
│   └── XxxAspect.java
├── converter/            # 转换器
│   └── XxxConverter.java
└── event/                # 事件相关
    ├── XxxEvent.java
    └── XxxListener.java
```

### 类职责单一原则

```java
// 正确：每个类职责单一
@Controller
public class UserController {
    // 只负责处理用户相关的HTTP请求
}

@Service
public class UserServiceImpl implements UserService {
    // 只负责用户相关的业务逻辑
}

@Mapper
public class UserMapper extends BaseMapper<User> {
    // 只负责用户相关的数据库操作
}

// 错误：类职责过多
@Service
public class UserService {
    // 同时处理业务逻辑、数据库操作、缓存操作
    // 职责不单一，难以维护
}
```

### 类成员排列顺序

```java
public class ExampleClass {
    // 1. 静态常量
    public static final String CONSTANT = "constant";
    
    // 2. 静态变量
    private static int staticVar;
    
    // 3. 实例变量（按可见性排列）
    private String privateField;
    protected String protectedField;
    public String publicField;
    
    // 4. 静态代码块
    static {
        staticVar = 0;
    }
    
    // 5. 构造函数
    public ExampleClass() {}
    
    // 6. 静态方法
    public static void staticMethod() {}
    
    // 7. 公共方法
    public void publicMethod() {}
    
    // 8. 受保护方法
    protected void protectedMethod() {}
    
    // 9. 私有方法
    private void privateMethod() {}
    
    // 10. 内部类
    private static class InnerClass {}
}
```

---

## 语句规范

### 括号使用规范

```java
// 正确：大括号独占一行（推荐）
if (condition) {
    doSomething();
}

// 可接受：左大括号不换行（K&R风格）
if (condition) {
    doSomething();
}

// 错误：省略大括号
if (condition)
    doSomething();

// 正确：即使只有一行也使用大括号
if (condition) {
    return true;
}

// 错误：省略else的大括号
if (condition) {
    doSomething();
} else
    doOther();
```

### 空格缩进规范

```java
// 使用4个空格缩进，不使用Tab
public class Example {
    public void method() {
        if (condition) {
            // 4个空格缩进
            doSomething();
        }
    }
}

// 运算符两侧必须有空格
int result = a + b * c;
boolean flag = a > b && c < d;

// 逗号后面必须有空格
method(param1, param2, param3);

// 关键字与括号之间必须有空格
if (condition) {}
for (int i = 0; i < 10; i++) {}
while (condition) {}
try (Resource r = getResource()) {}

// 方法名与括号之间不能有空格
method();

// 类型参数与括号之间不能有空格
List<String> list = new ArrayList<>();
```

### 空行规则

```java
public class Example {
    // 1. 导入语句与类声明之间空一行
    // （在文件开头）
    
    // 2. 类成员之间空一行
    private String field1;
    
    private String field2;
    
    // 3. 方法之间空一行
    public void method1() {
        // 方法内逻辑块之间空一行
        init();
        
        process();
        
        cleanup();
    }
    
    public void method2() {}
    
    // 4. 注释上方空一行（除非在文件开头或类开头）
    
    /**
     * 方法说明
     */
    public void method3() {}
}
```

---

## 代码长度限制

### 长度限制总览

| 项目 | 限制 | 说明 |
|-----|------|------|
| 单个方法 | 80行 | 超过应拆分 |
| 单个类 | 1000行 | 超过应拆分 |
| 单行代码 | 120字符 | 超过应换行 |
| 方法参数 | 5个 | 超过应使用对象 |
| 方法嵌套层级 | 3层 | 超过应重构 |
| 条件嵌套层级 | 3层 | 超过应提前返回 |

### 单行代码换行规则

```java
// 正确：在运算符后换行
int result = param1 + param2 + param3
    + param4 + param5;

// 正确：在逗号后换行
method(param1, param2, param3,
    param4, param5);

// 正确：方法链换行
String result = list.stream()
    .filter(item -> item.isValid())
    .map(Item::getName)
    .collect(Collectors.joining(","));

// 正确：构造函数换行
User user = new User.Builder()
    .id(1L)
    .name("张三")
    .age(25)
    .build();

// 正确：方法调用换行
String result = someObject.someMethod(
    param1,
    param2,
    param3
);
```

### 方法拆分示例

```java
// 错误：方法过长
public void processOrder(Order order) {
    // 验证订单 - 20行
    // 计算价格 - 30行
    // 扣减库存 - 20行
    // 创建支付 - 15行
    // 发送通知 - 10行
    // 总共95行
}

// 正确：拆分为多个方法
public void processOrder(Order order) {
    validateOrder(order);
    BigDecimal amount = calculateAmount(order);
    deductStock(order);
    createPayment(order, amount);
    sendNotification(order);
}

private void validateOrder(Order order) {
    // 验证逻辑
}

private BigDecimal calculateAmount(Order order) {
    // 计算逻辑
}

private void deductStock(Order order) {
    // 扣减逻辑
}

private void createPayment(Order order, BigDecimal amount) {
    // 支付逻辑
}

private void sendNotification(Order order) {
    // 通知逻辑
}
```

---

## 语言特性使用规范

### static关键字使用规范

```java
// 正确：常量使用static final
public static final String CONSTANT = "value";

// 正确：工具方法使用static
public static String format(String template, Object... args) {
    return String.format(template, args);
}

// 正确：单例模式使用static
private static volatile Singleton instance;

// 错误：滥用static变量存储状态
public class UserService {
    private static User currentUser; // 错误：多线程不安全
}

// 正确：使用ThreadLocal或请求作用域
public class UserContext {
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();
}
```

### final修饰符使用规范

```java
// 正确：常量必须使用final
public static final int MAX_SIZE = 100;

// 正确：不可变类字段使用final
@Value
public class UserDTO {
    Long id;
    String name;
}

// 正确：方法参数不修改时使用final（可选）
public void process(final String input) {
    // input = "new value"; // 编译错误
}

// 正确：不想被子类覆盖的方法使用final
public final String getId() {
    return id;
}

// 正确：不想被继承的类使用final
public final class StringUtils {}
```

### Lambda表达式使用规范

```java
// 正确：简单逻辑使用Lambda
list.stream()
    .filter(item -> item.isValid())
    .map(Item::getName)
    .forEach(System.out::println);

// 正确：复杂逻辑使用方法引用或提取方法
list.stream()
    .filter(this::isValidItem)
    .map(this::getItemName)
    .collect(Collectors.toList());

// 正确：多行逻辑使用代码块
list.forEach(item -> {
    String name = item.getName();
    if (name != null) {
        process(name);
    }
});

// 错误：Lambda过于复杂
list.stream()
    .filter(item -> {
        // 超过5行的逻辑
        // 应提取为独立方法
        return true;
    })
    .collect(Collectors.toList());

// 正确：使用Optional避免NPE
Optional.ofNullable(user)
    .map(User::getName)
    .filter(name -> !name.isEmpty())
    .orElse("默认名称");
```

### Stream API使用规范

```java
// 正确：使用Stream进行集合操作
List<String> names = users.stream()
    .filter(user -> user.getAge() > 18)
    .map(User::getName)
    .sorted()
    .collect(Collectors.toList());

// 正确：使用parallelStream处理大数据量
long count = bigList.parallelStream()
    .filter(this::expensiveOperation)
    .count();

// 正确：使用Map进行分组
Map<Integer, List<User>> groupByAge = users.stream()
    .collect(Collectors.groupingBy(User::getAge));

// 错误：在Stream中修改外部状态
List<User> result = new ArrayList<>();
users.stream()
    .forEach(user -> result.add(user)); // 错误：副作用

// 正确：使用collect收集结果
List<User> result = users.stream()
    .collect(Collectors.toList());
```

---

## 集合与泛型规范

### 集合初始化规范

```java
// 正确：指定初始容量
List<String> list = new ArrayList<>(16);
Map<String, Object> map = new HashMap<>(32);

// 正确：使用工厂方法（Java 9+）
List<String> list = List.of("a", "b", "c");
Set<String> set = Set.of("a", "b", "c");
Map<String, Integer> map = Map.of("a", 1, "b", 2);

// 正确：使用Guava/Hutool工具
List<String> list = CollectionUtil.newArrayList();
Map<String, Object> map = MapUtil.newHashMap();

// 错误：未指定初始容量（可能触发扩容）
List<String> list = new ArrayList<>();
Map<String, Object> map = new HashMap<>();
```

### 集合判空规范

```java
// 正确：使用工具类判空
if (CollectionUtil.isEmpty(list)) {
    return Collections.emptyList();
}

// 正确：使用工具类判非空
if (CollectionUtil.isNotEmpty(list)) {
    process(list);
}

// 错误：直接判断null
if (list == null || list.size() == 0) {
    // 冗余代码
}

// 错误：只判断null
if (list != null) {
    // 可能抛出IndexOutOfBoundsException
    list.get(0);
}
```

### 泛型使用规范

```java
// 正确：显式指定泛型类型
List<String> list = new ArrayList<>();
Map<String, User> map = new HashMap<>();

// 正确：使用泛型方法
public <T> T convert(Object source, Class<T> targetClass) {
    return targetClass.cast(source);
}

// 正确：使用通配符
public void process(List<? extends Number> numbers) {
    for (Number number : numbers) {
        System.out.println(number.intValue());
    }
}

// 错误：使用原始类型
List list = new ArrayList(); // 缺少泛型参数

// 错误：泛型类型擦除问题
public <T> T[] createArray(int size) {
    return new T[size]; // 编译错误
}
```

---

## 并发编程规范

### 线程安全规范

```java
// 正确：使用线程安全的集合
ConcurrentHashMap<String, User> userCache = new ConcurrentHashMap<>();
CopyOnWriteArrayList<String> listeners = new CopyOnWriteArrayList<>();

// 正确：使用原子类
AtomicInteger counter = new AtomicInteger(0);
AtomicLong requestId = new AtomicLong(0);

// 正确：使用不可变对象
@Value
public class ImmutableUser {
    Long id;
    String name;
}

// 正确：使用ThreadLocal
private static final ThreadLocal<User> currentUser = ThreadLocal.withInitial(() -> null);

// 错误：使用非线程安全的SimpleDateFormat
private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

// 正确：使用DateTimeFormatter（线程安全）
private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
```

### 锁使用规范

```java
// 正确：使用try-finally释放锁
private final ReentrantLock lock = new ReentrantLock();

public void process() {
    lock.lock();
    try {
        // 业务逻辑
    } finally {
        lock.unlock();
    }
}

// 正确：使用synchronized
public synchronized void process() {
    // 业务逻辑
}

// 正确：双重检查锁定
private static volatile Singleton instance;

public static Singleton getInstance() {
    if (instance == null) {
        synchronized (Singleton.class) {
            if (instance == null) {
                instance = new Singleton();
            }
        }
    }
    return instance;
}

// 错误：锁对象为null
private final Object lock = null;
public void process() {
    synchronized (lock) { // NullPointerException
    }
}
```

---

## 代码整洁规范

### 无效引用清除规范

**重要性**：无效引用（未使用的import）会导致代码混乱、增加编译时间、可能引发冲突，必须定期清理。

#### IDEA清除无效引用

```
方法1：单个文件
1. 打开Java文件
2. 快捷键：Ctrl+Alt+O (Windows) / Cmd+Opt+O (Mac)
3. 或右键 → Optimize Imports

方法2：整个项目/模块
1. 菜单：Code → Optimize Imports
2. 选择范围：Whole project 或 Module
3. 点击 Run

方法3：保存时自动优化
1. Settings → Editor → General → Auto Import
2. 勾选 "Optimize imports on the fly"
```

#### Maven检查无效引用

```xml
<!-- 在pom.xml中添加checkstyle插件 -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.3.1</version>
    <configuration>
        <configLocation>google_checks.xml</configLocation>
        <includes>**\/*.java</includes>
    </configuration>
</plugin>

<!-- 执行检查 -->
mvn checkstyle:check
```

#### 无效引用规范要求

```java
// ❌ 错误：存在无效引用
import java.util.List;           // 未使用
import java.util.ArrayList;      // 未使用
import java.util.Map;            // 未使用
import com.content.entity.User;
import com.content.entity.Role;  // 未使用
import java.time.LocalDateTime;  // 未使用

public class UserService {
    public User getUser(Long id) {
        return new User();  // 只使用了User
    }
}

// ✅ 正确：只保留必要的引用
import com.content.entity.User;

public class UserService {
    public User getUser(Long id) {
        return new User();
    }
}
```

#### 通配符引用禁止

```java
// ❌ 错误：使用通配符引用
import java.util.*;
import com.content.entity.*;
import org.springframework.stereotype.*;

// ✅ 正确：明确引用具体类
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.content.entity.User;
import com.content.entity.Role;
import org.springframework.stereotype.Service;
```

#### 代码提交前检查清单

```
□ 是否清除了所有无效引用
□ 是否避免了通配符引用（import xxx.*）
□ 是否按包名顺序排列引用（java > javax > 第三方 > 本项目）
□ 是否有循环引用问题
□ 是否有重复引用
```

#### 引用排序规范

```java
// 正确的引用顺序（按包名分组，组间空行分隔）
package com.content.user.service;

// 1. java标准库
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

// 2. javax扩展库
import javax.servlet.http.HttpServletRequest;

// 3. 第三方库
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

// 4. 本项目模块
import com.content.common.exception.BusinessException;
import com.content.entity.User;
import com.content.user.mapper.UserMapper;
```

### 代码格式化规范

#### IDEA代码格式化

```
方法1：单个文件
1. 快捷键：Ctrl+Alt+L (Windows) / Cmd+Opt+L (Mac)

方法2：整个项目/模块
1. 菜单：Code → Reformat Code
2. 选择范围：Whole project 或 Module
3. 点击 Run

方法3：保存时自动格式化
1. Settings → Tools → Actions on Save
2. 勾选 "Reformat code"
3. 勾选 "Optimize imports"
```

#### 代码格式规范

```java
// 缩进：4个空格（不使用Tab）
public class Example {
    public void method() {
        if (condition) {
            doSomething();
        }
    }
}

// 行宽：不超过120字符
// 换行规范
String result = someVeryLongMethodName(param1, param2,
    param3, param4);

// 方法链式调用换行
List<UserVO> users = userList.stream()
    .filter(user -> user.getStatus() == 1)
    .map(this::toVO)
    .collect(Collectors.toList());

// 空行规范
public class Example {
    private String field1;
    
    private String field2;
    
    public void method1() {
        // ...
    }
    
    public void method2() {
        // ...
    }
}
```

### 代码注释清理规范

```java
// ❌ 错误：保留注释掉的代码
public class UserService {
    public User getUser(Long id) {
        // 旧逻辑，已废弃
        // User user = userMapper.selectById(id);
        // return user;
        
        // 新逻辑
        return cacheService.get(id);
    }
    
    // TODO: 以后再处理
    // public void oldMethod() {
    //     ...
    // }
}

// ✅ 正确：删除无用注释，保留必要文档
public class UserService {
    /**
     * 获取用户信息（带缓存）
     *
     * @param id 用户ID
     * @return 用户信息
     */
    public User getUser(Long id) {
        return cacheService.get(id);
    }
}
```

### 重复代码检测

```
IDEA检测重复代码：
1. 菜单：Analyze → Locate Duplicates
2. 选择范围：Whole project 或 Module
3. 查看重复代码报告
4. 重构重复代码为公共方法
```

### 异步编程规范

```java
// 正确：使用@Async注解
@Async
public CompletableFuture<User> getUserAsync(Long id) {
    User user = userMapper.selectById(id);
    return CompletableFuture.completedFuture(user);
}

// 正确：使用CompletableFuture
public CompletableFuture<User> getUserAsync(Long id) {
    return CompletableFuture.supplyAsync(() -> {
        return userMapper.selectById(id);
    }, executorService);
}

// 正确：设置超时时间
CompletableFuture<User> future = getUserAsync(userId)
    .orTimeout(5, TimeUnit.SECONDS)
    .exceptionally(e -> {
        log.error("获取用户超时", e);
        return null;
    });
```

---

## 数据库操作规范

### SQL编写规范

```java
// 正确：使用MyBatis Plus的Wrapper
LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
wrapper.eq(User::getStatus, UserStatus.NORMAL.getCode())
       .likeRight(User::getUserName, userName)
       .orderByDesc(User::getCreateTime);

// 正确：使用@Select注解
@Select("SELECT * FROM sys_user WHERE user_name = #{userName}")
User selectByUserName(@Param("userName") String userName);

// 正确：使用XML映射文件
// UserMapper.xml
<select id="selectUserList" resultType="User">
    SELECT * FROM sys_user
    WHERE status = #{status}
    <if test="userName != null and userName != ''">
        AND user_name LIKE CONCAT('%', #{userName}, '%')
    </if>
    ORDER BY create_time DESC
</select>

// 错误：SQL拼接（存在注入风险）
String sql = "SELECT * FROM sys_user WHERE user_name = '" + userName + "'";
```

### 事务使用规范

```java
// 正确：使用@Transactional注解
@Transactional(rollbackFor = Exception.class)
public void saveUser(UserDTO userDTO) {
    User user = toEntity(userDTO);
    userMapper.insert(user);
    userRoleMapper.insertBatch(user.getId(), userDTO.getRoleIds());
}

// 正确：只读事务
@Transactional(readOnly = true)
public User getUserById(Long id) {
    return userMapper.selectById(id);
}

// 正确：指定传播行为
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void saveLog(OperationLog log) {
    logMapper.insert(log);
}

// 错误：事务方法调用同类方法
public void methodA() {
    methodB(); // 事务不生效
}

@Transactional
public void methodB() {
    // 业务逻辑
}

// 正确：通过注入的Bean调用
@Autowired
private UserService self;

public void methodA() {
    self.methodB(); // 事务生效
}
```

### 分页查询规范

```java
// 正确：使用MyBatis Plus分页
public PageResult<UserVO> pageUsers(UserQuery query) {
    Page<User> page = new Page<>(query.getPageNum(), query.getPageSize());
    LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(User::getStatus, query.getStatus());
    
    Page<User> result = userMapper.selectPage(page, wrapper);
    
    List<UserVO> voList = result.getRecords().stream()
        .map(this::toVO)
        .toList();
    
    return new PageResult<>(voList, result.getTotal(), 
        result.getCurrent(), result.getSize());
}

// 正确：使用PageHelper分页
public PageInfo<UserVO> pageUsers(UserQuery query) {
    PageHelper.startPage(query.getPageNum(), query.getPageSize());
    List<User> users = userMapper.selectList(query);
    return new PageInfo<>(users.stream().map(this::toVO).toList());
}
```

---

## API设计规范

### RESTful API规范

```java
// 正确：使用RESTful风格
@RestController
@RequestMapping("/api/users")
public class UserController {

    // GET - 查询
    @GetMapping("/{id}")
    public Result<UserVO> getUser(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }

    // GET - 列表
    @GetMapping
    public Result<PageResult<UserVO>> listUsers(UserQuery query) {
        return Result.success(userService.pageUsers(query));
    }

    // POST - 创建
    @PostMapping
    public Result<Long> createUser(@RequestBody @Valid UserDTO userDTO) {
        return Result.success(userService.saveUser(userDTO));
    }

    // PUT - 全量更新
    @PutMapping("/{id}")
    public Result<Boolean> updateUser(@PathVariable Long id, 
                                       @RequestBody @Valid UserDTO userDTO) {
        userDTO.setId(id);
        return Result.success(userService.updateUser(userDTO));
    }

    // PATCH - 部分更新
    @PatchMapping("/{id}/status")
    public Result<Boolean> updateStatus(@PathVariable Long id, 
                                         @RequestBody StatusDTO statusDTO) {
        return Result.success(userService.updateStatus(id, statusDTO.getStatus()));
    }

    // DELETE - 删除
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteUser(@PathVariable Long id) {
        return Result.success(userService.deleteUser(id));
    }
}
```

### 参数校验规范

```java
// 正确：使用Jakarta Validation注解
@Data
public class UserDTO {

    @NotNull(message = "用户ID不能为空")
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    private String userName;

    @NotNull(message = "年龄不能为空")
    @Min(value = 0, message = "年龄不能小于0")
    @Max(value = 150, message = "年龄不能大于150")
    private Integer age;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
}

// 正确：在Controller中使用@Valid
@PostMapping
public Result<Long> createUser(@RequestBody @Valid UserDTO userDTO) {
    return Result.success(userService.saveUser(userDTO));
}

// 正确：分组校验
public interface Create {}
public interface Update {}

@Data
public class UserDTO {
    @NotNull(groups = Update.class, message = "更新时ID不能为空")
    private Long id;

    @NotBlank(groups = {Create.class, Update.class}, message = "用户名不能为空")
    private String userName;
}

@PostMapping
public Result<Long> create(@RequestBody @Validated(Create.class) UserDTO dto) {}

@PutMapping
public Result<Boolean> update(@RequestBody @Validated(Update.class) UserDTO dto) {}
```

### 响应格式规范

```java
// 正确：统一响应格式
@Data
public class Result<T> implements Serializable {
    
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }
}

// 正确：分页响应格式
@Data
public class PageResult<T> implements Serializable {
    
    private List<T> list;
    private Long total;
    private Long pageNum;
    private Long pageSize;
    private Long pages;

    public static <T> PageResult<T> of(List<T> list, Long total, Long pageNum, Long pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setList(list);
        result.setTotal(total);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setPages((total + pageSize - 1) / pageSize);
        return result;
    }
}
```
