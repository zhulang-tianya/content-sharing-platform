package com.content.framework.aspectj;

import com.content.framework.annotation.DataScope;
import com.content.framework.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class DataScopeAspect {

    @Before("@annotation(dataScope)")
    public void doBefore(JoinPoint point, DataScope dataScope) {
        handleDataScope(point, dataScope);
    }

    protected void handleDataScope(final JoinPoint point, DataScope dataScope) {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            return;
        }

        if (SecurityUtils.isAdmin(userId)) {
            return;
        }

        String deptAlias = dataScope.deptAlias();
        String userAlias = dataScope.userAlias();

        log.debug("数据权限过滤 - 用户ID：{}，部门别名：{}，用户别名：{}", userId, deptAlias, userAlias);

        // 获取方法签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        // 获取参数
        Object[] args = point.getArgs();

        // 在实际应用中，这里需要根据用户的角色和权限，构建数据权限过滤条件
        // 例如，根据用户所属部门和角色，生成部门ID列表，然后在SQL中添加WHERE条件

        // 示例：假设我们有一个BaseEntity类，包含createBy字段，我们可以根据用户ID过滤
        for (Object arg : args) {
            if (arg != null) {
                // 这里可以根据实际的实体类结构，设置数据权限过滤条件
                // 例如：if (arg instanceof BaseEntity) { ((BaseEntity) arg).setDataScope(deptIds); }
            }
        }
    }
}
