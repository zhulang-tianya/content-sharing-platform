package com.content.framework.cache.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheEvict {
    
    String key();
    
    String condition() default "";
    
    boolean allEntries() default false;
    
    String pattern() default "";
}
