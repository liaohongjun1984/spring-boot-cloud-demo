package com.idohoo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *  方法输入参数和返回参数日志打印
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface LogParams {

}
