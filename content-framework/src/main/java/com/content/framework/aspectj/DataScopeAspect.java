package com.content.framework.aspectj;

import com.content.framework.annotation.DataScope;
import com.content.framework.security.LoginUser;
import com.content.framework.security.SecurityUtils;
import com.content.framework.security.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据权限切面
 * <p>
 * 基于部门的数据权限过滤，支持以下数据范围：
 * 1. 全部数据权限
 * 2. 自定义数据权限
 * 3. 本部门数据权限
 * 4. 本部门及以下数据权限
 * 5. 仅本人数据权限
 * </p>
 */
@Slf4j
@Aspect
@Component
public class DataScopeAspect {

    @Before("@annotation(dataScope)")
    public void doBefore(JoinPoint point, DataScope dataScope) {
        handleDataScope(point, dataScope);
    }

    protected void handleDataScope(JoinPoint point, DataScope dataScope) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null) {
            return;
        }

        Long userId = loginUser.getUserId();
        if (userId == null) {
            return;
        }

        if (loginUser.isAdmin()) {
            return;
        }

        String deptAlias = dataScope.deptAlias();
        String userAlias = dataScope.userAlias();

        log.debug("数据权限过滤 - userId={}, deptAlias={}, userAlias={}", 
            userId, deptAlias, userAlias);

        StringBuilder sqlString = new StringBuilder();
        Set<Role> roles = loginUser.getRoles();

        if (roles == null || roles.isEmpty()) {
            sqlString.append(" AND 1=0 ");
        } else {
            for (Role role : roles) {
                String dsType = role.getDataScope();
                if (dsType == null) {
                    continue;
                }
                
                String dsScope = buildDataScopeSql(dsType, deptAlias, userAlias, loginUser);
                if (dsScope != null) {
                    sqlString.append(dsScope);
                }
            }
        }

        if (sqlString.length() > 0) {
            Object params = point.getArgs()[0];
            if (params != null) {
                try {
                    setDataScopeParam(params, sqlString.toString());
                } catch (Exception e) {
                    log.warn("设置数据权限参数失败", e);
                }
            }
        }
    }

    /**
     * 构建数据权限SQL
     */
    private String buildDataScopeSql(String dataScope, String deptAlias, 
                                       String userAlias, LoginUser loginUser) {
        String deptId = String.valueOf(loginUser.getDeptId());
        String userId = String.valueOf(loginUser.getUserId());
        
        return switch (dataScope) {
            case "1" -> "";
            case "2" -> {
                yield String.format(" OR %s.dept_id IN (SELECT dept_id FROM sys_role_dept WHERE role_id = %s) ", 
                    deptAlias, "%roleId%");
            }
            case "3" -> String.format(" OR %s.dept_id = %s ", deptAlias, deptId);
            case "4" -> {
                yield String.format(" OR %s.dept_id IN (SELECT dept_id FROM sys_dept WHERE dept_id = %s OR find_in_set(%s, ancestors)) ", 
                    deptAlias, deptId, deptId);
            }
            case "5" -> String.format(" OR %s.user_id = %s ", userAlias, userId);
            default -> "";
        };
    }

    /**
     * 设置数据权限参数
     */
    private void setDataScopeParam(Object params, String dataScope) {
        try {
            Method setDataScope = params.getClass().getDeclaredMethod("setDataScope", String.class);
            setDataScope.invoke(params, dataScope);
        } catch (NoSuchMethodException e) {
            log.debug("参数对象没有setDataScope方法");
        } catch (Exception e) {
            log.warn("设置数据权限参数异常", e);
        }
    }
}
