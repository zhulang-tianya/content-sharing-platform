# 架构设计规范

## 目录

1. [模块设计原则](#模块设计原则)
2. [实体类设计规范](#实体类设计规范)
3. [模块依赖规范](#模块依赖规范)
4. [常见架构问题与解决方案](#常见架构问题与解决方案)

---

## 模块设计原则

### 单一职责原则 (SRP)

每个模块应该只有一个引起它变化的原因。

```
✅ 正确示例：

content-entity/       # 只负责实体类定义
content-common/       # 只负责公共工具类
content-framework/    # 只负责框架配置
content-user-service/ # 只负责用户业务逻辑

❌ 错误示例：

content-framework/
├── security/
│   ├── LoginUser.java
│   ├── Role.java        # 不应该在framework中定义业务实体
│   └── ...
└── ...
```

### 依赖倒置原则 (DIP)

高层模块不应依赖低层模块，两者都应依赖其抽象。

```
✅ 正确依赖关系：

content-user-service (业务层)
        ↓ 依赖
content-framework (框架层)
        ↓ 依赖
content-entity (实体层)
        ↓ 依赖
content-common (基础层)

❌ 错误依赖关系：

content-framework (框架层)
        ↓ 依赖
content-user-service (业务层)  # 框架不应依赖业务模块
```

### 开闭原则 (OCP)

软件实体应该对扩展开放，对修改关闭。

```java
// ✅ 正确：通过扩展实现新功能
public interface PaymentStrategy {
    PaymentResult pay(Order order);
}

@Component
public class AlipayStrategy implements PaymentStrategy {
    // 新增支付方式只需添加新类，无需修改现有代码
}

// ❌ 错误：通过修改现有代码实现新功能
public class PaymentService {
    public PaymentResult pay(Order order, String type) {
        if ("alipay".equals(type)) {
            // 支付宝逻辑
        } else if ("wechat".equals(type)) {
            // 微信逻辑
        }
        // 新增支付方式需要修改这里
    }
}
```

---

## 实体类设计规范

### 实体类统一管理

**问题场景**：多个模块需要使用相同的实体类，导致实体类分散在各模块中。

**解决方案**：创建独立的实体模块统一管理。

```
优化前：
├── content-framework/
│   └── security/entity/Role.java      # Role在framework
├── content-user-service/
│   └── entity/
│       ├── User.java                   # User在user-service
│       ├── Dept.java
│       └── Permission.java

优化后：
├── content-entity/                     # 独立实体模块
│   └── src/main/java/com/content/entity/
│       ├── User.java
│       ├── Role.java
│       ├── Dept.java
│       ├── Menu.java
│       └── Permission.java
```

### 实体类设计规范

```java
/**
 * 用户实体类
 * <p>
 * 对应数据库表 sys_user
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 */
@Data
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 删除标志：0-正常，1-已删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 租户ID（多租户）
     */
    private Long tenantId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
```

### 实体类命名规范

| 类型 | 后缀 | 说明 | 示例 |
|------|------|------|------|
| 实体类 | 无后缀 | 数据库表映射 | User, Role |
| DTO | DTO | 数据传输对象 | UserDTO, LoginDTO |
| VO | VO | 视图对象 | UserVO, MenuVO |
| Query | Query | 查询条件 | UserQuery, PageQuery |

---

## 模块依赖规范

### 模块依赖层次

```
Layer 4: 业务服务层
├── content-user-service
├── content-content-service
├── content-comment-service
└── ... 其他业务服务

Layer 3: 框架层
└── content-framework (安全、缓存、切面)

Layer 2: 实体层
└── content-entity (所有共享实体)

Layer 1: 基础层
└── content-common (工具类、常量、异常)
```

### 依赖规则

| 模块 | 可依赖 | 不可依赖 |
|------|--------|----------|
| 业务服务层 | framework, entity, common | 其他业务服务 |
| 框架层 | entity, common | 业务服务层 |
| 实体层 | common | 框架层、业务服务层 |
| 基础层 | 无 | 任何模块 |

### pom.xml 配置规范

```xml
<!-- 业务服务模块依赖配置 -->
<dependencies>
    <!-- 实体模块 -->
    <dependency>
        <groupId>com.content</groupId>
        <artifactId>content-entity</artifactId>
    </dependency>
    
    <!-- 公共模块 -->
    <dependency>
        <groupId>com.content</groupId>
        <artifactId>content-common</artifactId>
    </dependency>
    
    <!-- 框架模块 -->
    <dependency>
        <groupId>com.content</groupId>
        <artifactId>content-framework</artifactId>
    </dependency>
</dependencies>
```

---

## 常见架构问题与解决方案

### 问题1：跨模块实体引用

**问题描述**：
- `content-framework` 中的 `LoginUser` 需要引用 `Role` 实体
- `Role` 原本定义在 `content-user-service` 中
- 导致 `framework` 需要依赖 `user-service`，违背依赖原则

**解决方案**：
1. 创建独立的 `content-entity` 模块
2. 将所有共享实体类迁移到 `entity` 模块
3. 更新所有模块的依赖和导入路径

```java
// 优化前
// content-framework/src/.../LoginUser.java
import com.content.user.entity.Role; // ❌ 跨模块引用

// 优化后
// content-framework/src/.../LoginUser.java
import com.content.entity.Role; // ✅ 统一实体模块
```

### 问题2：循环依赖

**问题描述**：
- 模块A依赖模块B
- 模块B依赖模块A
- 导致编译失败或运行时错误

**解决方案**：
1. 提取公共部分到独立模块
2. 使用接口解耦
3. 使用事件驱动

```
优化前：
A → B → A (循环依赖)

优化后：
A → C ← B (提取公共模块C)
```

### 问题3：实体类分散

**问题描述**：
- 同一实体类在多个模块重复定义
- 修改时需要同步修改多处
- 容易遗漏导致不一致

**解决方案**：
```
创建 content-entity 模块：

1. 创建模块目录
mkdir -p content-entity/src/main/java/com/content/entity

2. 创建 pom.xml
定义依赖：mybatis-plus, lombok

3. 迁移实体类
将 User, Role, Dept, Menu, Permission 等迁移到 entity 模块

4. 更新依赖
在需要使用实体的模块中添加 content-entity 依赖

5. 更新导入
批量更新所有文件的 import 语句
```

### 问题4：职责不清

**问题描述**：
- `content-framework` 既包含安全逻辑又包含实体类
- 违背单一职责原则
- 难以维护和扩展

**解决方案**：
```
优化前：
content-framework/
├── security/
│   ├── LoginUser.java
│   ├── TokenService.java
│   └── entity/Role.java    # 不应该在这里
└── ...

优化后：
content-framework/
├── security/
│   ├── LoginUser.java
│   └── TokenService.java
└── ...

content-entity/
└── Role.java               # 移到独立模块
```

---

## 架构设计最佳实践

### 1. 模块拆分原则

- 按职责拆分，不按功能拆分
- 高内聚，低耦合
- 单向依赖，避免循环

### 2. 实体类管理原则

- 共享实体放在独立模块
- 业务特定实体可放在业务模块
- 使用 DTO/VO 隔离实体与接口

### 3. 依赖管理原则

- 使用 dependency-management 统一版本
- 显式声明依赖，不依赖传递依赖
- 定期检查和更新依赖版本

### 4. 包结构规范

```
com.content.{module}/
├── controller/    # 控制器层
├── service/       # 服务层
├── mapper/        # 数据访问层
├── entity/        # 实体类（仅业务特定）
├── dto/           # 数据传输对象
├── vo/            # 视图对象
├── query/         # 查询对象
├── config/        # 配置类
└── utils/         # 工具类
```

### 5. 重构流程规范

```
1. 分析问题：识别架构问题及其影响范围
2. 设计方案：制定详细的重构方案
3. 创建模块：如需要，创建新模块
4. 迁移代码：逐步迁移，保持功能不变
5. 更新依赖：修改 pom.xml 和 import
6. 测试验证：确保重构后功能正常
7. 提交代码：按规范提交，说明变更内容
```
