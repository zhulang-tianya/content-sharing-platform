package com.content.framework.handler;

import com.content.common.exception.AuthException;
import com.content.common.exception.BusinessException;
import com.content.common.result.Result;
import com.content.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("业务异常：{}，请求URI：{}", e.getMessage(), request.getRequestURI());
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public Result<?> handleAuthException(AuthException e, HttpServletRequest request) {
        log.error("认证异常：{}，请求URI：{}", e.getMessage(), request.getRequestURI());
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数校验异常：{}，请求URI：{}", errorMsg, request.getRequestURI());
        return Result.fail(ResultCode.VALIDATION_ERROR.getCode(), errorMsg);
    }

    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e, HttpServletRequest request) {
        String errorMsg = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数绑定异常：{}，请求URI：{}", errorMsg, request.getRequestURI());
        return Result.fail(ResultCode.VALIDATION_ERROR.getCode(), errorMsg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        String errorMsg = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.error("约束校验异常：{}，请求URI：{}", errorMsg, request.getRequestURI());
        return Result.fail(ResultCode.VALIDATION_ERROR.getCode(), errorMsg);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("请求方法不支持：{}，请求URI：{}", e.getMessage(), request.getRequestURI());
        return Result.fail(ResultCode.METHOD_NOT_ALLOWED.getCode(), "不支持的请求方法：" + e.getMethod());
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常：{}，请求URI：{}", e.getMessage(), request.getRequestURI(), e);
        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR);
    }
}
