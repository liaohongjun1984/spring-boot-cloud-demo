package com.idohoo.aspect;

import com.idohoo.util.GsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by henser on 17-5-23.
 */
@Order(1)
@Aspect
@Component
public class LogParamsAspect {

    private static Logger logger =LoggerFactory.getLogger(LogParamsAspect.class);

    //匹配所有 LogParams 注解的接口
    @Pointcut("@annotation(com.idohoo.annotation.LogParams)")
    public void annotationPointCut(){

    }

    @Before("annotationPointCut()")
    public void doBefore(JoinPoint joinPoint) throws RuntimeException {

        Object[] objects = joinPoint.getArgs(); // 获取被切函数 的参数

        if(objects == null || objects.length <= 0) {
            logger.info("获取被切函数的参数为空");
            return;
        }

        for(Object object : objects) {
            if(object instanceof HttpServletRequest) {
                continue;
            }
            logger.info("获取被切函数传入参数 params : {}", GsonUtils.toJson(object));
        }

    }

    @AfterReturning(pointcut ="annotationPointCut()",argNames="joinPoint,retValue",returning ="retValue")
    public void doAfterReturn(JoinPoint joinPoint,Object retValue) throws RuntimeException {


        if(retValue == null) {
            logger.info("获取被切函数返回参数为空");
            return;
        }

        logger.info("获取被切函数 返回的参数 retValue ：{}",GsonUtils.toJson(retValue));

    }


}
