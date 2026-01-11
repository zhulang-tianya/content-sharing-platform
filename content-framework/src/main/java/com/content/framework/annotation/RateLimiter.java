package com.content.framework.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    int time() default 60;

    int count() default 100;

    String limitType() default LimitType.DEFAULT;
}
