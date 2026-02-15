package com.content.framework.cache.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CachePut {
    
    String key();
    
    long timeout() default 3600;
    
    TimeUnit unit() default TimeUnit.SECONDS;
    
    String condition() default "";
    
    String unless() default "";
}
