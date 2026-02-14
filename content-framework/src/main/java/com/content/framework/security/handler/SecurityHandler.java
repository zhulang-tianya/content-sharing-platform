package com.content.framework.security.handler;

import com.content.common.result.Result;
import com.content.common.result.ResultCode;
import com.content.common.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class SecurityHandler implements AuthenticationSuccessHandler, 
                                         AuthenticationFailureHandler, 
                                         AccessDeniedHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                         HttpServletResponse response, 
                                         org.springframework.security.core.Authentication authentication) 
            throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(JsonUtils.toJson(Result.success("登录成功")));
        out.flush();
        out.close();
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, 
                                         HttpServletResponse response, 
                                         AuthenticationException exception) 
            throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        String message = "登录失败";
        if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误";
        } else if (exception instanceof InsufficientAuthenticationException) {
            message = "认证信息不足";
        }
        out.write(JsonUtils.toJson(
            Result.fail(ResultCode.UNAUTHORIZED.getCode(), message)));
        out.flush();
        out.close();
    }

    @Override
    public void handle(HttpServletRequest request, 
                       HttpServletResponse response, 
                       AccessDeniedException accessDeniedException) 
            throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(JsonUtils.toJson(
            Result.fail(ResultCode.FORBIDDEN.getCode(), "没有权限访问")));
        out.flush();
        out.close();
    }
}
