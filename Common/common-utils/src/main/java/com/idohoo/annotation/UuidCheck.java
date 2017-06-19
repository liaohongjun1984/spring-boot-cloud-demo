package com.idohoo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 幂等uuid 校验处理
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface UuidCheck {

}
