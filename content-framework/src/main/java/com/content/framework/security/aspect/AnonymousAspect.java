package com.content.framework.security.aspect;

import com.content.framework.security.annotation.Anonymous;
import com.content.framework.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class AnonymousAspect {

    @Pointcut("@annotation(com.content.framework.security.annotation.Anonymous)")
    public void anonymousPointcut() {
    }

    @Pointcut("@within(com.content.framework.security.annotation.Anonymous)")
    public void anonymousTypePointcut() {
    }

    @Around("anonymousPointcut() || anonymousTypePointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ServletUtils.getRequest();
        if (request != null) {
            request.setAttribute("anonymous", true);
        }
        return point.proceed();
    }

    public static boolean isAnonymous() {
        HttpServletRequest request = ServletUtils.getRequest();
        return request != null && Boolean.TRUE.equals(request.getAttribute("anonymous"));
    }

    public static boolean isAnonymous(Method method) {
        Anonymous anonymous = AnnotationUtils.findAnnotation(method, Anonymous.class);
        return anonymous != null;
    }

    public static boolean isAnonymous(Class<?> clazz) {
        Anonymous anonymous = AnnotationUtils.findAnnotation(clazz, Anonymous.class);
        return anonymous != null;
    }

    public static boolean isAnonymous(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        return isAnonymous(method) || isAnonymous(point.getTarget().getClass());
    }
}
