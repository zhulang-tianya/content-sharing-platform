package com.content.framework.aspectj;

import com.content.common.enums.BusinessType;
import com.content.common.enums.OperatorType;
import com.content.common.result.Result;
import com.content.common.utils.JsonUtils;
import com.content.framework.annotation.Log;
import com.content.framework.log.entity.OperationLog;
import com.content.framework.log.service.OperationLogService;
import com.content.framework.security.SecurityUtils;
import com.content.framework.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class LogAspect {

    private static final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Autowired
    private OperationLogService operationLogService;

    @Before("@annotation(controllerLog)")
    public void doBefore(JoinPoint joinPoint, Log controllerLog) {
        startTimeThreadLocal.set(System.currentTimeMillis());
        handleLog(joinPoint, null, null, controllerLog);
    }

    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, jsonResult, null, controllerLog);
    }

    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, null, e, controllerLog);
    }

    protected void handleLog(final JoinPoint joinPoint, final Object jsonResult, final Exception e, final Log controllerLog) {
        try {
            HttpServletRequest request = ServletUtils.getRequest();
            String ip = ServletUtils.getClientIP();
            String uri = ServletUtils.getRequestURI();
            String requestMethod = ServletUtils.getMethod();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method methodObj = signature.getMethod();
            Object[] args = joinPoint.getArgs();
            String params = argsArrayToString(args);

            long costTime = System.currentTimeMillis() - startTimeThreadLocal.get();
            startTimeThreadLocal.remove();

            String username = SecurityUtils.getUsername();
            String title = controllerLog.title();
            String businessType = controllerLog.businessType().name();
            String operatorType = controllerLog.operatorType().name();
            boolean saveRequestData = controllerLog.isSaveRequestData();
            boolean saveResponseData = controllerLog.isSaveResponseData();

            OperationLog operationLog = new OperationLog();
            operationLog.setTitle(title);
            operationLog.setBusinessType(businessType);
            operationLog.setOperatorType(operatorType);
            operationLog.setMethod(className + "." + methodName);
            operationLog.setRequestMethod(requestMethod);
            operationLog.setOperator(username);
            operationLog.setOperIp(ip);
            operationLog.setOperUri(uri);
            if (saveRequestData && params.length() > 0) {
                operationLog.setOperParam(params);
            }
            if (saveResponseData && jsonResult != null) {
                operationLog.setJsonResult(JsonUtils.toJson(jsonResult));
            }
            if (e != null) {
                operationLog.setStatus(0);
                operationLog.setErrorMsg(e.getMessage());
            } else {
                operationLog.setStatus(1);
            }
            operationLog.setCostTime(costTime);
            operationLog.setOperTime(LocalDateTime.now());

            // 保存操作日志
            operationLogService.save(operationLog);

            // 打印操作日志
            StringBuilder logMessage = new StringBuilder();
            logMessage.append("操作日志 - ");
            logMessage.append("标题：").append(title).append("，");
            logMessage.append("用户：").append(username).append("，");
            logMessage.append("IP：").append(ip).append("，");
            logMessage.append("URI：").append(uri).append("，");
            logMessage.append("方法：").append(requestMethod).append("，");
            logMessage.append("类名：").append(className).append("，");
            logMessage.append("方法名：").append(methodName).append("，");
            logMessage.append("业务类型：").append(businessType).append("，");
            logMessage.append("操作类型：").append(operatorType).append("，");
            logMessage.append("耗时：").append(costTime).append("ms，");
            logMessage.append("状态：").append(e != null ? "失败" : "成功");

            if (e != null) {
                log.error(logMessage.toString(), e);
            } else {
                log.info(logMessage.toString());
            }

        } catch (Exception exp) {
            log.error("==前置通知异常==", exp);
        }
    }

    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (isFilterObject(o)) {
                    continue;
                }
                try {
                    String jsonObj = JsonUtils.toJson(o);
                    params.append(jsonObj).append(" ");
                } catch (Exception e) {
                    log.error("参数序列化异常", e);
                }
            }
        }
        return params.toString();
    }

    private boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection<?> collection = (Collection<?>) o;
            for (Object value : collection) {
                if (value instanceof MultipartFile) {
                    return true;
                }
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map<?, ?> map = (Map<?, ?>) o;
            for (Object value : map.entrySet()) {
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) value;
                if (entry.getValue() instanceof MultipartFile) {
                    return true;
                }
            }
        }
        return o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
