package com.content.framework.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("@permissionService.hasRole('{value}')")
public @interface PreAuthorizeRole {
    String value();
}
