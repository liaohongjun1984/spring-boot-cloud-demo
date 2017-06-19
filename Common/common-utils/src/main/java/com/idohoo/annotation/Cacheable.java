package com.idohoo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 
 * 2013-4-26 下午12:00:31
 * </pre>
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {
    // 缓存的key,默认是方法签名和方法 参数做key
    String value() default "";

    // 缓存的时间,默认30分钟
    int exp() default 1800;
}
