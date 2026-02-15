# 异常处理协议

## 目录

1. [异常分类与适用场景](#异常分类与适用场景)
2. [自定义异常规范](#自定义异常规范)
3. [错误码编码规范](#错误码编码规范)
4. [异常消息模板](#异常消息模板)
5. [日志记录规范](#日志记录规范)
6. [全局异常处理](#全局异常处理)
7. [异常处理最佳实践](#异常处理最佳实践)

---

## 异常分类与适用场景

### 受检异常 vs 非受检异常

| 类型 | 基类 | 特点 | 适用场景 |
|-----|------|------|---------|
| 受检异常 | `Exception` | 编译期强制处理 | 可恢复的业务异常 |
| 非受检异常 | `RuntimeException` | 编译期不强制处理 | 编程错误、系统异常 |

### 项目异常体系

```
Throwable
└── Exception
    └── RuntimeException (非受检异常)
        └── BaseException (项目基础异常)
            ├── BusinessException (业务异常)
            ├── AuthException (认证异常)
            ├── ForbiddenException (权限异常)
            ├── NotFoundException (资源不存在异常)
            ├── ParamException (参数异常)
            ├── FileException (文件异常)
            └── RateLimitException (限流异常)
```

### 异常选择指南

| 场景 | 异常类型 | 说明 |
|-----|---------|------|
| 业务规则校验失败 | `BusinessException` | 用户操作不符合业务规则 |
| 用户未登录或登录过期 | `AuthException` | 需要重新登录 |
| 用户无权限访问 | `ForbiddenException` | 权限不足 |
| 请求资源不存在 | `NotFoundException` | 数据不存在 |
| 参数校验失败 | `ParamException` | 参数格式或值不合法 |
| 文件操作失败 | `FileException` | 文件读写、上传、下载异常 |
| 请求频率超限 | `RateLimitException` | 触发限流 |
| 系统内部错误 | `RuntimeException` | 不可预期的系统错误 |

---

## 自定义异常规范

### 基础异常类

```java
package com.content.common.exception;

import com.content.common.result.IResultCode;
import lombok.Getter;

/**
 * 基础异常类
 * <p>
 * 所有自定义异常必须继承此类，提供统一的异常处理机制。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 */
@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 构造函数
     *
     * @param resultCode 结果码枚举
     */
    public BaseException(IResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 构造函数
     *
     * @param resultCode 结果码枚举
     * @param message 自定义错误消息
     */
    public BaseException(IResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
        this.message = message;
    }

    /**
     * 构造函数
     *
     * @param code 错误码
     * @param message 错误消息
     */
    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     *
     * @param resultCode 结果码枚举
     * @param cause 原始异常
     */
    public BaseException(IResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 构造函数
     *
     * @param code 错误码
     * @param message 错误消息
     * @param cause 原始异常
     */
    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}
```

### 业务异常类

```java
package com.content.common.exception;

import com.content.common.result.ResultCode;

/**
 * 业务异常
 * <p>
 * 用于业务规则校验失败、业务逻辑错误等场景。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     *
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(ResultCode.BUSINESS_ERROR, message);
    }

    /**
     * 构造函数
     *
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(code, message);
    }

    /**
     * 构造函数
     *
     * @param message 错误消息
     * @param cause 原始异常
     */
    public BusinessException(String message, Throwable cause) {
        super(ResultCode.BUSINESS_ERROR, message, cause);
    }
}
```

### 认证异常类

```java
package com.content.common.exception;

import com.content.common.result.ResultCode;

/**
 * 认证异常
 * <p>
 * 用于用户未登录、登录过期、令牌无效等场景。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 */
public class AuthException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public AuthException() {
        super(ResultCode.UNAUTHORIZED);
    }

    /**
     * 构造函数
     *
     * @param message 错误消息
     */
    public AuthException(String message) {
        super(ResultCode.UNAUTHORIZED, message);
    }

    /**
     * 构造函数
     *
     * @param cause 原始异常
     */
    public AuthException(Throwable cause) {
        super(ResultCode.UNAUTHORIZED, cause);
    }
}
```

### 权限异常类

```java
package com.content.common.exception;

import com.content.common.result.ResultCode;

/**
 * 权限异常
 * <p>
 * 用于用户权限不足、访问被拒绝等场景。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 */
public class ForbiddenException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public ForbiddenException() {
        super(ResultCode.FORBIDDEN);
    }

    /**
     * 构造函数
     *
     * @param message 错误消息
     */
    public ForbiddenException(String message) {
        super(ResultCode.FORBIDDEN, message);
    }
}
```

### 资源不存在异常类

```java
package com.content.common.exception;

import com.content.common.result.ResultCode;

/**
 * 资源不存在异常
 * <p>
 * 用于请求的资源不存在、数据已删除等场景。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 */
public class NotFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public NotFoundException() {
        super(ResultCode.NOT_FOUND);
    }

    /**
     * 构造函数
     *
     * @param message 错误消息
     */
    public NotFoundException(String message) {
        super(ResultCode.NOT_FOUND, message);
    }

    /**
     * 构造函数 - 指定资源类型和ID
     *
     * @param resourceType 资源类型
     * @param resourceId 资源ID
     */
    public NotFoundException(String resourceType, Object resourceId) {
        super(ResultCode.NOT_FOUND, 
              String.format("%s不存在: %s", resourceType, resourceId));
    }
}
```

### 参数异常类

```java
package com.content.common.exception;

import com.content.common.result.ResultCode;

/**
 * 参数异常
 * <p>
 * 用于参数校验失败、参数格式错误等场景。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 */
public class ParamException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     *
     * @param message 错误消息
     */
    public ParamException(String message) {
        super(ResultCode.PARAM_ERROR, message);
    }

    /**
     * 构造函数 - 指定参数名和错误原因
     *
     * @param paramName 参数名
     * @param reason 错误原因
     */
    public ParamException(String paramName, String reason) {
        super(ResultCode.PARAM_ERROR, 
              String.format("参数[%s]%s", paramName, reason));
    }
}
```

---

## 错误码编码规范

### 错误码结构

```
错误码格式: ABBCC
- A: 错误级别 (1-成功, 2-警告, 4-客户端错误, 5-服务端错误)
- BB: 模块编码 (00-公共, 01-用户, 02-内容, 03-评论, 04-支付, ...)
- CC: 具体错误序号 (01-99)
```

### 错误码定义

```java
package com.content.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一结果码枚举
 *
 * @author content-platform
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {

    // ==================== 成功 ====================
    SUCCESS(20000, "操作成功"),

    // ==================== 客户端错误 4xxxx ====================
    
    // 通用错误 400xx
    PARAM_ERROR(40001, "参数错误"),
    PARAM_MISSING(40002, "参数缺失"),
    PARAM_FORMAT_ERROR(40003, "参数格式错误"),
    
    // 认证错误 401xx
    UNAUTHORIZED(40100, "未登录或登录已过期"),
    TOKEN_INVALID(40101, "令牌无效"),
    TOKEN_EXPIRED(40102, "令牌已过期"),
    
    // 权限错误 403xx
    FORBIDDEN(40300, "无权限访问"),
    PERMISSION_DENIED(40301, "权限不足"),
    
    // 资源错误 404xx
    NOT_FOUND(40400, "资源不存在"),
    
    // 业务错误 405xx
    BUSINESS_ERROR(40500, "业务处理失败"),
    
    // 限流错误 429xx
    RATE_LIMIT_ERROR(42900, "请求过于频繁，请稍后重试"),

    // ==================== 服务端错误 5xxxx ====================
    
    // 通用错误 500xx
    INTERNAL_ERROR(50000, "系统内部错误"),
    SERVICE_UNAVAILABLE(50001, "服务暂不可用"),
    
    // 数据库错误 501xx
    DATABASE_ERROR(50100, "数据库操作失败"),
    
    // 缓存错误 502xx
    CACHE_ERROR(50200, "缓存操作失败"),
    
    // 第三方服务错误 503xx
    THIRD_PARTY_ERROR(50300, "第三方服务调用失败");

    private final Integer code;
    private final String message;
}
```

### 模块错误码分配

| 模块 | 编码范围 | 示例 |
|-----|---------|------|
| 公共模块 | 40001-40099 | 参数错误、格式错误 |
| 用户模块 | 40101-40199 | 用户不存在、密码错误 |
| 内容模块 | 40201-40299 | 内容不存在、审核失败 |
| 评论模块 | 40301-40399 | 评论不存在、评论已关闭 |
| 支付模块 | 40401-40499 | 支付失败、订单不存在 |
| 消息模块 | 40501-40599 | 消息发送失败 |
| 搜索模块 | 40601-40699 | 搜索服务异常 |

---

## 异常消息模板

### 消息模板规范

```java
package com.content.common.constant;

/**
 * 异常消息模板
 *
 * @author content-platform
 * @version 1.0.0
 */
public final class ExceptionMessages {

    private ExceptionMessages() {}

    // ==================== 用户模块 ====================
    
    public static final String USER_NOT_FOUND = "用户不存在";
    public static final String USER_DISABLED = "用户已被禁用";
    public static final String USER_LOCKED = "用户已被锁定";
    public static final String USER_NAME_EXISTS = "用户名已存在";
    public static final String PASSWORD_ERROR = "密码错误";
    public static final String OLD_PASSWORD_ERROR = "原密码错误";
    
    // ==================== 内容模块 ====================
    
    public static final String CONTENT_NOT_FOUND = "内容不存在";
    public static final String CONTENT_AUDITING = "内容审核中，暂不可访问";
    public static final String CONTENT_REJECTED = "内容审核未通过";
    public static final String CONTENT_DELETED = "内容已删除";
    
    // ==================== 支付模块 ====================
    
    public static final String ORDER_NOT_FOUND = "订单不存在";
    public static final String ORDER_PAID = "订单已支付";
    public static final String ORDER_CANCELED = "订单已取消";
    public static final String ORDER_EXPIRED = "订单已过期";
    public static final String PAY_FAILED = "支付失败";
    
    // ==================== 通用消息 ====================
    
    public static String notFound(String resourceType, Object resourceId) {
        return String.format("%s不存在: %s", resourceType, resourceId);
    }
    
    public static String paramInvalid(String paramName, String reason) {
        return String.format("参数[%s]%s", paramName, reason);
    }
    
    public static String operationFailed(String operation) {
        return String.format("%s失败", operation);
    }
}
```

---

## 日志记录规范

### 日志级别标准

| 级别 | 适用场景 | 示例 |
|-----|---------|------|
| ERROR | 影响系统正常运行的错误 | 数据库连接失败、第三方服务调用失败 |
| WARN | 潜在问题，不影响系统运行 | 参数校验失败、重试操作 |
| INFO | 重要的业务操作日志 | 用户登录、订单创建、支付成功 |
| DEBUG | 调试信息 | 方法入参出参、中间结果 |

### 日志内容要素

```java
// 必须包含的要素
// 1. 时间戳 - 由日志框架自动添加
// 2. 日志级别 - 由日志框架自动添加
// 3. 类名 - 由日志框架自动添加
// 4. 方法名 - 由日志框架自动添加
// 5. 错误码 - 业务错误码
// 6. 详细描述 - 错误详情
// 7. 堆栈信息 - 异常堆栈（ERROR级别）
```

### 日志格式规范

```java
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    // ERROR级别日志 - 系统错误
    public void handleSystemError(Long userId, Exception e) {
        log.error("用户服务处理异常，userId={}，错误码={}，原因={}", 
            userId, 
            ResultCode.INTERNAL_ERROR.getCode(),
            e.getMessage(), 
            e);
    }

    // WARN级别日志 - 业务警告
    public void handleBusinessWarning(Long userId, String reason) {
        log.warn("用户操作被拒绝，userId={}，原因={}", userId, reason);
    }

    // INFO级别日志 - 业务操作
    public void handleBusinessOperation(Long userId, String operation) {
        log.info("用户执行操作，userId={}，操作={}", userId, operation);
    }

    // DEBUG级别日志 - 调试信息
    public void debugUserInfo(User user) {
        log.debug("用户信息详情，userId={}，userName={}，status={}", 
            user.getId(), 
            user.getUserName(), 
            user.getStatus());
    }
}
```

### 异常日志记录规范

```java
// 正确示例：记录关键信息和异常堆栈
try {
    // 业务逻辑
} catch (SQLException e) {
    log.error("数据库操作失败，sql={}，参数={}，错误={}", 
        sql, params, e.getMessage(), e);
    throw new BusinessException("数据库操作失败", e);
}

// 正确示例：业务异常只记录关键信息
if (user == null) {
    log.warn("用户不存在，userId={}", userId);
    throw new NotFoundException("用户不存在");
}

// 错误示例：只记录消息，没有堆栈
try {
    // 业务逻辑
} catch (Exception e) {
    log.error("操作失败: " + e.getMessage()); // 缺少堆栈信息
    throw e;
}

// 错误示例：使用字符串拼接
log.error("用户ID: " + userId + " 操作失败"); // 性能较差
```

---

## 全局异常处理

### 全局异常处理器

```java
package com.content.framework.handler;

import com.content.common.exception.*;
import com.content.common.result.Result;
import com.content.common.result.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * <p>
 * 统一处理所有异常，返回标准化的错误响应。
 * </p>
 *
 * @author content-platform
 * @version 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==================== 业务异常处理 ====================

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.warn("业务异常，URI={}，错误码={}，消息={}", 
            request.getRequestURI(), e.getCode(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 认证异常
     */
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> handleAuthException(AuthException e, HttpServletRequest request) {
        log.warn("认证异常，URI={}，消息={}", request.getRequestURI(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 权限异常
     */
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<?> handleForbiddenException(ForbiddenException e, HttpServletRequest request) {
        log.warn("权限异常，URI={}，消息={}", request.getRequestURI(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 资源不存在异常
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<?> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        log.warn("资源不存在，URI={}，消息={}", request.getRequestURI(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 参数异常
     */
    @ExceptionHandler(ParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleParamException(ParamException e, HttpServletRequest request) {
        log.warn("参数异常，URI={}，消息={}", request.getRequestURI(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 限流异常
     */
    @ExceptionHandler(RateLimitException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Result<?> handleRateLimitException(RateLimitException e, HttpServletRequest request) {
        log.warn("限流异常，URI={}，消息={}", request.getRequestURI(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    // ==================== 框架异常处理 ====================

    /**
     * 参数校验异常 - @Valid
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
        log.warn("参数校验失败，URI={}，消息={}", request.getRequestURI(), message);
        return Result.fail(ResultCode.PARAM_ERROR, message);
    }

    /**
     * 参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleBindException(BindException e, HttpServletRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
        log.warn("参数绑定失败，URI={}，消息={}", request.getRequestURI(), message);
        return Result.fail(ResultCode.PARAM_ERROR, message);
    }

    /**
     * 参数约束违反异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleConstraintViolationException(
            ConstraintViolationException e, HttpServletRequest request) {
        String message = e.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));
        log.warn("参数约束违反，URI={}，消息={}", request.getRequestURI(), message);
        return Result.fail(ResultCode.PARAM_ERROR, message);
    }

    /**
     * 缺少请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e, HttpServletRequest request) {
        log.warn("缺少请求参数，URI={}，参数名={}", request.getRequestURI(), e.getParameterName());
        return Result.fail(ResultCode.PARAM_MISSING, 
            "缺少参数: " + e.getParameterName());
    }

    /**
     * 参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.warn("参数类型不匹配，URI={}，参数名={}，期望类型={}", 
            request.getRequestURI(), e.getName(), e.getRequiredType());
        return Result.fail(ResultCode.PARAM_FORMAT_ERROR, 
            "参数格式错误: " + e.getName());
    }

    /**
     * 请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<?> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.warn("请求方法不支持，URI={}，方法={}", request.getRequestURI(), e.getMethod());
        return Result.fail(ResultCode.PARAM_ERROR, 
            "不支持的请求方法: " + e.getMethod());
    }

    /**
     * 404异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<?> handleNoHandlerFoundException(
            NoHandlerFoundException e, HttpServletRequest request) {
        log.warn("请求路径不存在，URI={}", request.getRequestURI());
        return Result.fail(ResultCode.NOT_FOUND, "请求路径不存在");
    }

    // ==================== Security异常处理 ====================

    /**
     * Spring Security认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> handleAuthenticationException(
            AuthenticationException e, HttpServletRequest request) {
        log.warn("认证失败，URI={}，异常类型={}", request.getRequestURI(), e.getClass().getSimpleName());
        return Result.fail(ResultCode.UNAUTHORIZED, "认证失败");
    }

    /**
     * 错误凭证异常
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> handleBadCredentialsException(
            BadCredentialsException e, HttpServletRequest request) {
        log.warn("凭证错误，URI={}", request.getRequestURI());
        return Result.fail(ResultCode.UNAUTHORIZED, "用户名或密码错误");
    }

    /**
     * 访问拒绝异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<?> handleAccessDeniedException(
            AccessDeniedException e, HttpServletRequest request) {
        log.warn("访问被拒绝，URI={}", request.getRequestURI());
        return Result.fail(ResultCode.FORBIDDEN, "无权限访问");
    }

    // ==================== 兜底异常处理 ====================

    /**
     * 其他未捕获异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常，URI={}，异常类型={}，消息={}", 
            request.getRequestURI(), e.getClass().getName(), e.getMessage(), e);
        return Result.fail(ResultCode.INTERNAL_ERROR, "系统异常，请稍后重试");
    }
}
```

---

## 异常处理最佳实践

### 异常抛出规范

```java
// 正确：使用具体的异常类型
public User getUserById(Long userId) {
    if (userId == null || userId <= 0) {
        throw new ParamException("userId", "必须大于0");
    }
    User user = userMapper.selectById(userId);
    if (user == null) {
        throw new NotFoundException("用户", userId);
    }
    return user;
}

// 正确：业务异常包含错误码
public void transfer(Long fromId, Long toId, BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new BusinessException(40501, "转账金额必须大于0");
    }
    // 业务逻辑
}

// 错误：使用通用Exception
public User getUserById(Long userId) throws Exception {
    // 不应抛出通用Exception
}
```

### 异常捕获规范

```java
// 正确：捕获具体异常，记录日志后重新抛出或处理
try {
    thirdPartyService.call();
} catch (ThirdPartyException e) {
    log.error("第三方服务调用失败，service={}，错误={}", 
        "thirdPartyService", e.getMessage(), e);
    throw new BusinessException("服务暂时不可用，请稍后重试", e);
}

// 正确：多层异常捕获，分别处理
try {
    // 复杂业务逻辑
} catch (BusinessException e) {
    // 业务异常直接抛出
    throw e;
} catch (SQLException e) {
    // 数据库异常转换为业务异常
    throw new BusinessException("数据操作失败", e);
} catch (Exception e) {
    // 其他异常
    throw new BusinessException("系统异常", e);
}

// 错误：捕获异常后不处理
try {
    // 业务逻辑
} catch (Exception e) {
    // 空catch块，吞掉异常
}

// 错误：捕获Exception太宽泛
try {
    // 业务逻辑
} catch (Exception e) {
    throw new BusinessException(e.getMessage());
}
```

### 异常转换规范

```java
// 正确：将底层异常转换为业务异常
@Override
public void saveUser(UserDTO userDTO) {
    try {
        User user = toEntity(userDTO);
        userMapper.insert(user);
    } catch (DuplicateKeyException e) {
        throw new BusinessException("用户名已存在");
    } catch (DataAccessException e) {
        log.error("用户保存失败，userDTO={}", userDTO, e);
        throw new BusinessException("用户保存失败", e);
    }
}

// 正确：保留原始异常信息
public void processFile(String filePath) {
    try {
        Files.readAllBytes(Paths.get(filePath));
    } catch (IOException e) {
        throw new FileException("文件读取失败: " + filePath, e);
    }
}
```

### 资源清理规范

```java
// 正确：使用try-with-resources
public String readFile(String path) {
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        return reader.lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
        throw new FileException("文件读取失败", e);
    }
}

// 正确：在finally块中清理资源（旧写法，不推荐）
public String readFileOld(String path) {
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(path));
        return reader.lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
        throw new FileException("文件读取失败", e);
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                log.warn("文件关闭失败", e);
            }
        }
    }
}
```
