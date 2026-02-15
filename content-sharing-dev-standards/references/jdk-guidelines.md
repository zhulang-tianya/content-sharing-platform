# JDK版本规范

## 目录

1. [JDK版本要求](#jdk版本要求)
2. [允许使用的语法特性](#允许使用的语法特性)
3. [禁止使用的过时特性](#禁止使用的过时特性)
4. [版本兼容性处理](#版本兼容性处理)
5. [最佳实践指南](#最佳实践指南)

---

## JDK版本要求

### 项目JDK版本

| 项目 | 版本 | 说明 |
|-----|------|------|
| JDK版本 | **22** | 项目必须使用JDK 22 |
| Maven编译版本 | 22 | pom.xml中配置 |
| 目标字节码版本 | 22 | 编译输出版本 |

### Maven配置

```xml
<properties>
    <java.version>22</java.version>
    <maven.compiler.source>22</maven.compiler.source>
    <maven.compiler.target>22</maven.compiler.target>
</properties>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.12.1</version>
            <configuration>
                <source>22</source>
                <target>22</target>
                <compilerArgs>
                    <arg>--enable-preview</arg>
                </compilerArgs>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Jakarta EE规范

项目使用 **Jakarta EE** 规范（非旧的javax）：

```xml
<!-- Jakarta Servlet -->
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.1.0</version>
</dependency>

<!-- Jakarta Validation -->
<dependency>
    <groupId>jakarta.validation</groupId>
    <artifactId>jakarta.validation-api</artifactId>
    <version>3.1.0</version>
</dependency>

<!-- Jakarta Persistence -->
<dependency>
    <groupId>jakarta.persistence</groupId>
    <artifactId>jakarta.persistence-api</artifactId>
    <version>3.2.0</version>
</dependency>
```

---

## 允许使用的语法特性

### JDK 8 特性（基础）

#### Lambda表达式

```java
// 正确：使用Lambda表达式
list.stream()
    .filter(item -> item.isValid())
    .map(Item::getName)
    .forEach(System.out::println);

// 正确：方法引用
list.stream()
    .map(User::getName)
    .forEach(System.out::println);

// 正确：构造函数引用
List<User> users = ids.stream()
    .map(User::new)
    .toList();
```

#### Stream API

```java
// 正确：使用Stream处理集合
List<String> names = users.stream()
    .filter(user -> user.getAge() > 18)
    .map(User::getName)
    .sorted()
    .collect(Collectors.toList());

// 正确：使用并行流处理大数据
long count = bigList.parallelStream()
    .filter(this::expensiveOperation)
    .count();

// 正确：使用分组
Map<Integer, List<User>> groupByAge = users.stream()
    .collect(Collectors.groupingBy(User::getAge));
```

#### Optional类

```java
// 正确：使用Optional避免NPE
Optional<User> userOpt = Optional.ofNullable(userMapper.selectById(id));

String name = userOpt
    .map(User::getName)
    .filter(n -> !n.isEmpty())
    .orElse("默认名称");

// 正确：使用Optional进行链式调用
userOpt
    .filter(u -> u.getStatus() == UserStatus.NORMAL.getCode())
    .ifPresent(this::sendNotification);

// 正确：使用Optional处理异常情况
User user = userOpt
    .orElseThrow(() -> new NotFoundException("用户不存在"));
```

#### 默认方法

```java
// 正确：接口中使用默认方法
public interface UserService {
    
    User getUserById(Long id);
    
    default String getUserName(Long id) {
        User user = getUserById(id);
        return user != null ? user.getUserName() : null;
    }
}
```

### JDK 9-11 特性

#### 模块化系统（Jigsaw）

```java
// module-info.java
module com.content.user {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires mybatis.plus;
    
    exports com.content.user.service;
    exports com.content.user.dto;
    exports com.content.user.vo;
}
```

#### 私有接口方法

```java
// 正确：接口中使用私有方法
public interface UserService {
    
    default void processUser(Long id) {
        User user = getUserById(id);
        validateUser(user);
        doProcess(user);
    }
    
    private void validateUser(User user) {
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }
    }
    
    private void doProcess(User user) {
        // 处理逻辑
    }
}
```

#### 不可变集合工厂方法

```java
// 正确：使用工厂方法创建不可变集合
List<String> list = List.of("a", "b", "c");
Set<String> set = Set.of("a", "b", "c");
Map<String, Integer> map = Map.of("a", 1, "b", 2);

// 正确：使用Map.ofEntries创建大型Map
Map<String, Integer> largeMap = Map.ofEntries(
    Map.entry("a", 1),
    Map.entry("b", 2),
    Map.entry("c", 3)
);
```

#### var局部变量类型推断

```java
// 正确：使用var简化声明
var user = userMapper.selectById(id);
var users = userMapper.selectList(query);
var map = new HashMap<String, User>();

// 正确：在循环中使用var
for (var entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// 错误：var不适合复杂类型
var result = complexMethod(); // 类型不明确，应显式声明
```

#### String增强

```java
// 正确：使用lines()方法
String text = "line1\nline2\nline3";
text.lines().forEach(System.out::println);

// 正确：使用isBlank()方法
if (str.isBlank()) {
    // 字符串为空白
}

// 正确：使用strip()方法
String trimmed = str.strip(); // 比trim()更智能

// 正确：使用repeat()方法
String repeated = "ab".repeat(3); // "ababab"
```

### JDK 12-17 特性

#### Switch表达式

```java
// 正确：使用switch表达式
String status = switch (user.getStatus()) {
    case 0 -> "正常";
    case 1 -> "禁用";
    case 2 -> "锁定";
    default -> "未知";
};

// 正确：带yield的switch表达式
int score = switch (level) {
    case "A" -> 90;
    case "B" -> 80;
    case "C" -> {
        System.out.println("需要改进");
        yield 70;
    }
    default -> 60;
};
```

#### 文本块（Text Blocks）

```java
// 正确：使用文本块
String json = """
    {
        "name": "张三",
        "age": 25,
        "status": "active"
    }
    """;

// 正确：使用文本块编写SQL
String sql = """
    SELECT u.id, u.user_name, u.status
    FROM sys_user u
    WHERE u.status = %d
    ORDER BY u.create_time DESC
    """.formatted(status);
```

#### Records（记录类）

```java
// 正确：使用Record定义不可变数据类
public record UserDTO(
    Long id,
    String userName,
    Integer age
) {
    // 自动生成构造函数、getter、equals、hashCode、toString
}

// 正确：Record中添加方法
public record UserVO(
    Long id,
    String userName,
    Integer status
) {
    public String getStatusDesc() {
        return switch (status) {
            case 0 -> "正常";
            case 1 -> "禁用";
            default -> "未知";
        };
    }
}

// 正确：Record实现接口
public record UserDTO(Long id, String userName) 
    implements Serializable {
}
```

#### 密封类（Sealed Classes）

```java
// 正确：使用sealed限制继承
public sealed interface Result<T> 
    permits Success, Failure, Pending {
}

public record Success<T>(T data) implements Result<T> {}
public record Failure<T>(String message) implements Result<T> {}
public record Pending<T>() implements Result<T> {}

// 正确：使用模式匹配
String result = switch (r) {
    case Success<T> s -> "成功: " + s.data();
    case Failure<T> f -> "失败: " + f.message();
    case Pending<T> p -> "处理中";
};
```

#### instanceof模式匹配

```java
// 正确：使用模式匹配
if (obj instanceof String str) {
    System.out.println(str.toUpperCase());
}

// 正确：结合条件判断
if (obj instanceof User user && user.getAge() > 18) {
    process(user);
}
```

#### 空指针异常增强

```java
// JDK 14+ 提供更详细的NPE信息
// 旧版本：NullPointerException
// 新版本：Cannot invoke "String.length()" because "str" is null
```

### JDK 18-22 特性

#### 虚拟线程（Virtual Threads）- JDK 21正式版

```java
// 正确：使用虚拟线程
Thread.ofVirtual().start(() -> {
    // 任务逻辑
});

// 正确：使用Executors创建虚拟线程池
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    IntStream.range(0, 10000).forEach(i -> {
        executor.submit(() -> {
            Thread.sleep(Duration.ofSeconds(1));
            return i;
        });
    });
}

// 正确：在Spring Boot中使用虚拟线程
@Configuration
public class VirtualThreadConfig {
    
    @Bean
    public TomcatProtocolHandlerCustomizer<?> virtualThreadExecutor() {
        return protocolHandler -> {
            protocolHandler.setExecutor(
                Executors.newVirtualThreadPerTaskExecutor()
            );
        };
    }
}
```

#### 结构化并发（Structured Concurrency）- JDK 21预览

```java
// 正确：使用结构化并发
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    Future<String> user = scope.fork(() -> findUser());
    Future<Integer> order = scope.fork(() -> fetchOrder());
    
    scope.join();
    scope.throwIfFailed();
    
    return new Response(user.resultNow(), order.resultNow());
}
```

#### 未命名类和实例主方法（JDK 21预览）

```java
// 正确：简化的主方法
void main() {
    System.out.println("Hello, World!");
}

// 旧写法
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

#### 字符串模板（JDK 21预览）

```java
// 正确：使用字符串模板
String name = "张三";
int age = 25;
String message = STR."用户\{name}，年龄\{age}岁";

// 格式化
String formatted = STR."%.2f".formatted(3.14159);
```

#### 集合转数组增强

```java
// 正确：使用toArray
List<String> list = List.of("a", "b", "c");
String[] array = list.toArray(String[]::new);
```

---

## 禁止使用的过时特性

### @Deprecated注解标识的特性

#### 已移除的特性

| 特性 | 移除版本 | 替代方案 |
|-----|---------|---------|
| Nashorn JavaScript引擎 | JDK 15 | GraalVM |
| CMS垃圾收集器 | JDK 14 | G1GC |
| Pack200工具 | JDK 14 | jlink |
| Java EE模块 | JDK 11 | Jakarta EE |

#### 废弃的特性

```java
// 错误：使用已废弃的Date构造函数
Date date = new Date(2024, 1, 1);

// 正确：使用LocalDate
LocalDate date = LocalDate.of(2024, 1, 1);

// 错误：使用已废弃的String构造BigDecimal
BigDecimal bd = new BigDecimal("0.1");

// 正确：使用valueOf
BigDecimal bd = BigDecimal.valueOf(0.1);
```

### 禁止使用的javax包

```java
// 错误：使用javax包
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;

// 正确：使用jakarta包
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Entity;
```

### 禁止使用的过时API

```java
// 错误：使用过时的日期时间API
Date date = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Calendar calendar = Calendar.getInstance();

// 正确：使用java.time API
LocalDate date = LocalDate.now();
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

// 错误：使用过时的集合初始化
Vector<String> vector = new Vector<>();
Hashtable<String, String> table = new Hashtable<>();

// 正确：使用现代集合
List<String> list = new ArrayList<>();
Map<String, String> map = new HashMap<>();

// 错误：使用过时的IO流
new FileInputStream(new File("test.txt"));

// 正确：使用NIO
Files.newInputStream(Paths.get("test.txt"));
```

### 禁止使用的编码模式

```java
// 错误：使用原始类型
List list = new ArrayList();

// 正确：使用泛型
List<String> list = new ArrayList<>();

// 错误：使用显式类型转换
List<String> list = (List<String>) someObject;

// 正确：使用泛型方法或类型检查
if (someObject instanceof List<?> list) {
    List<String> typedList = list.stream()
        .map(String.class::cast)
        .toList();
}

// 错误：使用finalize方法
@Override
protected void finalize() throws Throwable {
    // 清理资源
}

// 正确：使用try-with-resources或Cleaner
try (Resource r = getResource()) {
    // 使用资源
}
```

---

## 版本兼容性处理

### 向后兼容策略

```java
// 正确：使用接口默认方法保持兼容
public interface UserService {
    
    User getUserById(Long id);
    
    // 新增方法使用默认实现
    default UserVO getUserVOById(Long id) {
        User user = getUserById(id);
        return user != null ? toVO(user) : null;
    }
}
```

### 多版本JAR

```xml
<!-- Maven配置多版本JAR -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <executions>
        <execution>
            <id>compile-java-22</id>
            <goals>
                <goal>compile</goal>
            </goals>
            <configuration>
                <release>22</release>
                <compileSourceRoots>
                    <compileSourceRoot>${project.basedir}/src/main/java22</compileSourceRoot>
                </compileSourceRoots>
                <multiReleaseOutput>true</multiReleaseOutput>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### 条件编译

```java
// 正确：使用静态常量进行条件编译
public class FeatureFlags {
    
    public static final boolean JAVA_22_ENABLED = 
        Runtime.version().feature() >= 22;
    
    public static void process() {
        if (JAVA_22_ENABLED) {
            processWithVirtualThreads();
        } else {
            processWithPlatformThreads();
        }
    }
}
```

---

## 最佳实践指南

### 现代Java编程风格

```java
// 推荐：使用Record作为DTO
public record UserDTO(Long id, String userName, Integer age) {}

// 推荐：使用sealed接口定义领域模型
public sealed interface PaymentResult 
    permits PaymentSuccess, PaymentFailure, PaymentPending {}

// 推荐：使用模式匹配处理业务逻辑
public String processPayment(PaymentResult result) {
    return switch (result) {
        case PaymentSuccess s -> "支付成功: " + s.transactionId();
        case PaymentFailure f -> "支付失败: " + f.reason();
        case PaymentPending p -> "支付处理中";
    };
}

// 推荐：使用虚拟线程处理IO密集型任务
@Service
public class AsyncUserService {
    
    @Async
    public CompletableFuture<User> getUserAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            return userMapper.selectById(id);
        }, Executors.newVirtualThreadPerTaskExecutor());
    }
}
```

### 性能优化建议

```java
// 推荐：使用String.concat或StringBuilder
String result = str1 + str2; // 简单拼接
String result = str1.concat(str2); // 更高效

// 推荐：使用集合工厂方法
List<String> list = List.of("a", "b", "c"); // 不可变，高效

// 推荐：使用Stream并行处理大数据
long count = bigList.parallelStream()
    .filter(this::expensiveOperation)
    .count();

// 推荐：使用虚拟线程处理高并发IO
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    // 处理大量IO任务
}
```

### 代码迁移检查清单

- [ ] 将`javax.*`包替换为`jakarta.*`
- [ ] 将`Date/Calendar`替换为`java.time`
- [ ] 将`Vector/Hashtable`替换为`ArrayList/HashMap`
- [ ] 将原始类型替换为泛型
- [ ] 将匿名内部类替换为Lambda
- [ ] 将复杂switch替换为switch表达式
- [ ] 将DTO类替换为Record
- [ ] 考虑使用虚拟线程优化IO密集型任务
