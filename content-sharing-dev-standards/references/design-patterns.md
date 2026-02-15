# 设计模式实施指南

## 目录

1. [设计模式总览](#设计模式总览)
2. [表现层设计模式](#表现层设计模式)
3. [业务逻辑层设计模式](#业务逻辑层设计模式)
4. [数据访问层设计模式](#数据访问层设计模式)
5. [跨横切关注点设计模式](#跨横切关注点设计模式)
6. [模式组合应用](#模式组合应用)

---

## 设计模式总览

### 各层推荐模式

| 层次 | 推荐模式 | 用途 |
|-----|---------|------|
| 表现层 | DTO模式、VO模式、控制器模式 | 数据传输、视图展示、请求处理 |
| 业务逻辑层 | 策略模式、模板方法、责任链、状态模式 | 业务规则、流程控制 |
| 数据访问层 | Repository模式、DAO模式、数据映射器 | 数据持久化 |
| 跨横切关注点 | AOP、装饰器、代理模式 | 日志、事务、权限 |

---

## 表现层设计模式

### DTO（数据传输对象）模式

**适用场景**：接口入参，封装多个参数

```java
/**
 * 用户数据传输对象
 * <p>
 * 用于接收前端请求参数，与实体类解耦。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 */
@Data
public class UserDTO implements Serializable {

    @NotNull(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50之间")
    private String userName;

    @NotNull(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20之间")
    private String password;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    private List<Long> roleIds;
}
```

### VO（视图对象）模式

**适用场景**：接口出参，封装返回数据

```java
/**
 * 用户视图对象
 * <p>
 * 用于向前端返回用户信息，可包含计算字段和格式化数据。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 */
@Data
public class UserVO implements Serializable {

    private Long id;
    private String userName;
    private String email;
    private String phone;
    private Integer status;
    private String statusDesc;
    private LocalDateTime createTime;
    private String createTimeDesc;

    /**
     * 获取状态描述
     */
    public String getStatusDesc() {
        return UserStatus.getDesc(this.status);
    }

    /**
     * 获取格式化的创建时间
     */
    public String getCreateTimeDesc() {
        return createTime != null 
            ? createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            : null;
    }
}
```

### 控制器模式

**适用场景**：处理HTTP请求，协调服务调用

```java
/**
 * 用户控制器
 * <p>
 * 处理用户相关的HTTP请求，遵循RESTful设计规范。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户相关接口")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情")
    public Result<UserVO> getUser(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }

    @GetMapping
    @Operation(summary = "分页查询用户列表")
    public Result<PageResult<UserVO>> listUsers(UserQuery query) {
        return Result.success(userService.pageUsers(query));
    }

    @PostMapping
    @Operation(summary = "创建用户")
    public Result<Long> createUser(@RequestBody @Valid UserDTO userDTO) {
        return Result.success(userService.saveUser(userDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户")
    public Result<Boolean> updateUser(@PathVariable Long id, 
                                       @RequestBody @Valid UserDTO userDTO) {
        userDTO.setId(id);
        return Result.success(userService.updateUser(userDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public Result<Boolean> deleteUser(@PathVariable Long id) {
        return Result.success(userService.deleteUser(id));
    }
}
```

---

## 业务逻辑层设计模式

### 策略模式

**适用场景**：多种算法或业务规则可互换

```java
/**
 * 支付策略接口
 */
public interface PaymentStrategy {
    
    /**
     * 执行支付
     *
     * @param order 订单信息
     * @return 支付结果
     */
    PaymentResult pay(Order order);
    
    /**
     * 获取支付类型
     */
    PaymentType getType();
}

/**
 * 支付宝支付策略
 */
@Component
public class AlipayStrategy implements PaymentStrategy {
    
    @Override
    public PaymentResult pay(Order order) {
        // 支付宝支付逻辑
        return PaymentResult.success("alipay_transaction_id");
    }
    
    @Override
    public PaymentType getType() {
        return PaymentType.ALIPAY;
    }
}

/**
 * 微信支付策略
 */
@Component
public class WechatPayStrategy implements PaymentStrategy {
    
    @Override
    public PaymentResult pay(Order order) {
        // 微信支付逻辑
        return PaymentResult.success("wechat_transaction_id");
    }
    
    @Override
    public PaymentType getType() {
        return PaymentType.WECHAT;
    }
}

/**
 * 支付策略工厂
 */
@Component
@RequiredArgsConstructor
public class PaymentStrategyFactory {
    
    private final List<PaymentStrategy> strategies;
    
    private Map<PaymentType, PaymentStrategy> strategyMap;
    
    @PostConstruct
    public void init() {
        strategyMap = strategies.stream()
            .collect(Collectors.toMap(
                PaymentStrategy::getType,
                Function.identity()
            ));
    }
    
    public PaymentStrategy getStrategy(PaymentType type) {
        PaymentStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new BusinessException("不支持的支付方式: " + type);
        }
        return strategy;
    }
}

/**
 * 支付服务
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    
    private final PaymentStrategyFactory strategyFactory;
    
    @Override
    public PaymentResult pay(Order order) {
        PaymentStrategy strategy = strategyFactory.getStrategy(order.getPaymentType());
        return strategy.pay(order);
    }
}
```

### 模板方法模式

**适用场景**：固定流程，部分步骤可变

```java
/**
 * 数据导入模板
 */
public abstract class DataImportTemplate<T> {
    
    /**
     * 导入数据（模板方法）
     */
    public final ImportResult importData(MultipartFile file) {
        // 1. 校验文件
        validateFile(file);
        
        // 2. 解析数据
        List<T> dataList = parseData(file);
        
        // 3. 校验数据
        List<T> validData = validateData(dataList);
        
        // 4. 保存数据
        int successCount = saveData(validData);
        
        // 5. 发送通知
        sendNotification(successCount, dataList.size() - successCount);
        
        return new ImportResult(successCount, dataList.size() - successCount);
    }
    
    protected void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ParamException("文件不能为空");
        }
    }
    
    protected abstract List<T> parseData(MultipartFile file);
    
    protected List<T> validateData(List<T> dataList) {
        return dataList.stream()
            .filter(this::isValid)
            .toList();
    }
    
    protected abstract boolean isValid(T data);
    
    protected abstract int saveData(List<T> dataList);
    
    protected void sendNotification(int successCount, int failCount) {
        log.info("数据导入完成，成功{}条，失败{}条", successCount, failCount);
    }
}

/**
 * 用户导入实现
 */
@Component
public class UserImportTemplate extends DataImportTemplate<UserDTO> {
    
    @Autowired
    private UserService userService;
    
    @Override
    protected List<UserDTO> parseData(MultipartFile file) {
        // 解析Excel文件
        return ExcelUtils.parse(file, UserDTO.class);
    }
    
    @Override
    protected boolean isValid(UserDTO data) {
        return StringUtils.isNotBlank(data.getUserName());
    }
    
    @Override
    protected int saveData(List<UserDTO> dataList) {
        return userService.batchSave(dataList);
    }
}
```

### 责任链模式

**适用场景**：多步骤处理，每步可中断

```java
/**
 * 审批处理器接口
 */
public interface ApprovalHandler {
    
    /**
     * 处理审批
     *
     * @param context 审批上下文
     * @return 是否继续处理
     */
    boolean handle(ApprovalContext context);
    
    /**
     * 设置下一个处理器
     */
    void setNext(ApprovalHandler handler);
}

/**
 * 抽象审批处理器
 */
public abstract class AbstractApprovalHandler implements ApprovalHandler {
    
    protected ApprovalHandler next;
    
    @Override
    public void setNext(ApprovalHandler handler) {
        this.next = handler;
    }
    
    protected boolean processNext(ApprovalContext context) {
        if (next != null) {
            return next.handle(context);
        }
        return true;
    }
}

/**
 * 内容审核处理器
 */
@Component
@Order(1)
public class ContentAuditHandler extends AbstractApprovalHandler {
    
    @Override
    public boolean handle(ApprovalContext context) {
        // 内容审核逻辑
        if (containsSensitiveWords(context.getContent())) {
            context.reject("内容包含敏感词");
            return false;
        }
        return processNext(context);
    }
    
    private boolean containsSensitiveWords(String content) {
        // 敏感词检测
        return false;
    }
}

/**
 * 权限检查处理器
 */
@Component
@Order(2)
public class PermissionCheckHandler extends AbstractApprovalHandler {
    
    @Override
    public boolean handle(ApprovalContext context) {
        // 权限检查逻辑
        if (!hasPermission(context.getUserId(), context.getResourceId())) {
            context.reject("无权限操作");
            return false;
        }
        return processNext(context);
    }
    
    private boolean hasPermission(Long userId, Long resourceId) {
        return true;
    }
}

/**
 * 审批链构建器
 */
@Component
@RequiredArgsConstructor
public class ApprovalChainBuilder {
    
    private final List<ApprovalHandler> handlers;
    
    public ApprovalHandler buildChain() {
        if (handlers.isEmpty()) {
            return null;
        }
        
        ApprovalHandler first = handlers.get(0);
        ApprovalHandler current = first;
        
        for (int i = 1; i < handlers.size(); i++) {
            current.setNext(handlers.get(i));
            current = handlers.get(i);
        }
        
        return first;
    }
}
```

### 状态模式

**适用场景**：对象行为随状态变化

```java
/**
 * 订单状态接口
 */
public interface OrderState {
    
    /**
     * 支付
     */
    void pay(OrderContext context);
    
    /**
     * 发货
     */
    void ship(OrderContext context);
    
    /**
     * 收货
     */
    void receive(OrderContext context);
    
    /**
     * 取消
     */
    void cancel(OrderContext context);
    
    /**
     * 获取状态描述
     */
    String getDesc();
}

/**
 * 待支付状态
 */
@Component
public class PendingPaymentState implements OrderState {
    
    @Override
    public void pay(OrderContext context) {
        context.setState(new PaidState());
        log.info("订单{}支付成功", context.getOrderId());
    }
    
    @Override
    public void ship(OrderContext context) {
        throw new BusinessException("待支付订单不能发货");
    }
    
    @Override
    public void receive(OrderContext context) {
        throw new BusinessException("待支付订单不能收货");
    }
    
    @Override
    public void cancel(OrderContext context) {
        context.setState(new CanceledState());
        log.info("订单{}已取消", context.getOrderId());
    }
    
    @Override
    public String getDesc() {
        return "待支付";
    }
}

/**
 * 已支付状态
 */
@Component
public class PaidState implements OrderState {
    
    @Override
    public void pay(OrderContext context) {
        throw new BusinessException("订单已支付");
    }
    
    @Override
    public void ship(OrderContext context) {
        context.setState(new ShippedState());
        log.info("订单{}已发货", context.getOrderId());
    }
    
    @Override
    public void receive(OrderContext context) {
        throw new BusinessException("已支付订单不能直接收货");
    }
    
    @Override
    public void cancel(OrderContext context) {
        context.setState(new CanceledState());
        log.info("订单{}已取消，开始退款", context.getOrderId());
    }
    
    @Override
    public String getDesc() {
        return "已支付";
    }
}

/**
 * 订单上下文
 */
@Data
public class OrderContext {
    
    private Long orderId;
    private OrderState state;
    
    public void pay() {
        state.pay(this);
    }
    
    public void ship() {
        state.ship(this);
    }
    
    public void receive() {
        state.receive(this);
    }
    
    public void cancel() {
        state.cancel(this);
    }
}
```

---

## 数据访问层设计模式

### Repository模式

**适用场景**：封装数据访问逻辑

```java
/**
 * 用户仓储接口
 */
public interface UserRepository {
    
    User findById(Long id);
    
    User findByUserName(String userName);
    
    List<User> findByIds(List<Long> ids);
    
    void save(User user);
    
    void update(User user);
    
    void deleteById(Long id);
    
    PageResult<User> findByCondition(UserQuery query);
}

/**
 * 用户仓储实现
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    
    private final UserMapper userMapper;
    
    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }
    
    @Override
    public User findByUserName(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, userName);
        return userMapper.selectOne(wrapper);
    }
    
    @Override
    public List<User> findByIds(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(ids);
    }
    
    @Override
    public void save(User user) {
        userMapper.insert(user);
    }
    
    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }
    
    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }
    
    @Override
    public PageResult<User> findByCondition(UserQuery query) {
        Page<User> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<User> wrapper = buildWrapper(query);
        Page<User> result = userMapper.selectPage(page, wrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(),
            result.getCurrent(), result.getSize());
    }
    
    private LambdaQueryWrapper<User> buildWrapper(UserQuery query) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getUserName()), 
                User::getUserName, query.getUserName())
            .eq(query.getStatus() != null, User::getStatus, query.getStatus())
            .orderByDesc(User::getCreateTime);
        return wrapper;
    }
}
```

### 数据映射器模式

**适用场景**：对象与数据表映射转换

```java
/**
 * 用户映射器
 */
@Component
public class UserMapper {
    
    /**
     * DTO转Entity
     */
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        return user;
    }
    
    /**
     * Entity转VO
     */
    public UserVO toVO(User entity) {
        if (entity == null) {
            return null;
        }
        UserVO vo = new UserVO();
        vo.setId(entity.getId());
        vo.setUserName(entity.getUserName());
        vo.setEmail(entity.getEmail());
        vo.setPhone(entity.getPhone());
        vo.setStatus(entity.getStatus());
        vo.setCreateTime(entity.getCreateTime());
        return vo;
    }
    
    /**
     * 批量转换
     */
    public List<UserVO> toVOList(List<User> entities) {
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream()
            .map(this::toVO)
            .toList();
    }
    
    /**
     * 更新Entity
     */
    public void updateEntity(User entity, UserDTO dto) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
    }
}
```

---

## 跨横切关注点设计模式

### AOP（面向切面编程）

**适用场景**：日志、事务、权限等横切关注点

```java
/**
 * 操作日志切面
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    
    @Around("@annotation(logAnnotation)")
    public Object around(ProceedingJoinPoint point, Log logAnnotation) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        String className = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        
        log.info("开始执行 {}.{}，参数：{}", className, methodName, args);
        
        try {
            Object result = point.proceed();
            long costTime = System.currentTimeMillis() - startTime;
            log.info("执行成功 {}.{}，耗时：{}ms", className, methodName, costTime);
            return result;
        } catch (Exception e) {
            long costTime = System.currentTimeMillis() - startTime;
            log.error("执行失败 {}.{}，耗时：{}ms，异常：{}", 
                className, methodName, costTime, e.getMessage());
            throw e;
        }
    }
}

/**
 * 日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    
    String value() default "";
    
    BusinessType businessType() default BusinessType.OTHER;
}

/**
 * 数据权限切面
 */
@Aspect
@Component
@RequiredArgsConstructor
public class DataScopeAspect {
    
    private final UserService userService;
    
    @Before("@annotation(dataScope)")
    public void doBefore(JoinPoint point, DataScope dataScope) {
        handleDataScope(point, dataScope);
    }
    
    private void handleDataScope(JoinPoint point, DataScope dataScope) {
        Object param = point.getArgs()[0];
        if (param instanceof BaseQuery query) {
            User currentUser = userService.getCurrentUser();
            if (currentUser != null && !currentUser.isAdmin()) {
                query.setDataScope(buildDataScope(currentUser, dataScope));
            }
        }
    }
    
    private String buildDataScope(User user, DataScope dataScope) {
        return " AND create_by = " + user.getId();
    }
}
```

### 代理模式

**适用场景**：控制对象访问、添加额外功能

```java
/**
 * 缓存代理
 */
@Component
@RequiredArgsConstructor
public class UserServiceProxy {
    
    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 获取用户（带缓存）
     */
    public User getUserById(Long id) {
        String key = "user:" + id;
        User user = (User) redisTemplate.opsForValue().get(key);
        if (user != null) {
            return user;
        }
        
        user = userService.getUserById(id);
        if (user != null) {
            redisTemplate.opsForValue().set(key, user, 1, TimeUnit.HOURS);
        }
        return user;
    }
    
    /**
     * 更新用户（清除缓存）
     */
    public void updateUser(User user) {
        userService.updateUser(user);
        String key = "user:" + user.getId();
        redisTemplate.delete(key);
    }
}
```

### 装饰器模式

**适用场景**：动态添加功能

```java
/**
 * 数据源接口
 */
public interface DataSource {
    
    Object getData(String key);
    
    void setData(String key, Object value);
}

/**
 * 基础数据源
 */
@Component
public class BaseDataSource implements DataSource {
    
    private final Map<String, Object> data = new ConcurrentHashMap<>();
    
    @Override
    public Object getData(String key) {
        return data.get(key);
    }
    
    @Override
    public void setData(String key, Object value) {
        data.put(key, value);
    }
}

/**
 * 缓存装饰器
 */
public class CacheDataSource implements DataSource {
    
    private final DataSource delegate;
    private final RedisTemplate<String, Object> redisTemplate;
    
    public CacheDataSource(DataSource delegate, RedisTemplate<String, Object> redisTemplate) {
        this.delegate = delegate;
        this.redisTemplate = redisTemplate;
    }
    
    @Override
    public Object getData(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return value;
        }
        
        value = delegate.getData(key);
        if (value != null) {
            redisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS);
        }
        return value;
    }
    
    @Override
    public void setData(String key, Object value) {
        delegate.setData(key, value);
        redisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS);
    }
}

/**
 * 日志装饰器
 */
@Slf4j
public class LoggingDataSource implements DataSource {
    
    private final DataSource delegate;
    
    public LoggingDataSource(DataSource delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public Object getData(String key) {
        log.info("获取数据: {}", key);
        Object value = delegate.getData(key);
        log.info("获取数据结果: {} = {}", key, value);
        return value;
    }
    
    @Override
    public void setData(String key, Object value) {
        log.info("设置数据: {} = {}", key, value);
        delegate.setData(key, value);
    }
}
```

---

## 模式组合应用

### 订单处理完整示例

```java
/**
 * 订单服务实现
 * <p>
 * 组合使用多种设计模式：
 * - 策略模式：支付方式选择
 * - 状态模式：订单状态管理
 * - 责任链模式：订单验证
 * - 模板方法模式：订单处理流程
 * </p>
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final PaymentStrategyFactory paymentStrategyFactory;
    private final ApprovalChainBuilder approvalChainBuilder;
    private final OrderMapper orderMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(OrderDTO orderDTO) {
        // 1. 责任链验证
        ApprovalContext context = new ApprovalContext();
        context.setUserId(orderDTO.getUserId());
        context.setContent(orderDTO.getRemark());
        
        ApprovalHandler chain = approvalChainBuilder.buildChain();
        if (!chain.handle(context)) {
            throw new BusinessException(context.getRejectReason());
        }
        
        // 2. 创建订单
        Order order = orderMapper.toEntity(orderDTO);
        order.setStatus(OrderStatus.PENDING_PAYMENT.getCode());
        order.setOrderNo(generateOrderNo());
        orderRepository.save(order);
        
        return order.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(Long orderId, PaymentType paymentType) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new NotFoundException("订单不存在");
        }
        
        // 状态模式：检查是否可支付
        OrderContext orderContext = new OrderContext();
        orderContext.setOrderId(orderId);
        orderContext.setState(getState(order.getStatus()));
        orderContext.pay();
        
        // 策略模式：选择支付方式
        PaymentStrategy strategy = paymentStrategyFactory.getStrategy(paymentType);
        PaymentResult result = strategy.pay(order);
        
        // 更新订单状态
        order.setStatus(OrderStatus.PAID.getCode());
        order.setPaymentType(paymentType.getCode());
        order.setTransactionId(result.getTransactionId());
        orderRepository.update(order);
    }
    
    private OrderState getState(Integer status) {
        return switch (status) {
            case 0 -> new PendingPaymentState();
            case 1 -> new PaidState();
            case 2 -> new ShippedState();
            default -> throw new BusinessException("未知订单状态");
        };
    }
    
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + 
            String.format("%04d", new Random().nextInt(10000));
    }
}
```

### 设计模式选择指南

| 场景 | 推荐模式 | 说明 |
|-----|---------|------|
| 多种算法可互换 | 策略模式 | 支付方式、排序算法 |
| 固定流程可变步骤 | 模板方法 | 数据导入、审批流程 |
| 多步骤处理链 | 责任链模式 | 审批、验证、过滤 |
| 状态影响行为 | 状态模式 | 订单状态、用户状态 |
| 横切关注点 | AOP | 日志、事务、权限 |
| 控制对象访问 | 代理模式 | 缓存、权限控制 |
| 动态添加功能 | 装饰器模式 | 缓存、日志、压缩 |
| 封装数据访问 | Repository模式 | 数据持久化 |
| 对象数据映射 | 数据映射器 | DTO/Entity/VO转换 |
