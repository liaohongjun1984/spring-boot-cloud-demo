package com.idohoo.aspect;

import com.idohoo.Thread.ThreadLocalUtil;
import com.idohoo.util.GsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by henser on 17-5-23.
 */
@Order(1)
@Aspect
@Component
public class UuidSetAspect {

    private static Logger logger =LoggerFactory.getLogger(UuidSetAspect.class);

    //匹配所有 UuidSet 注解的接口
    @Pointcut("@annotation(com.idohoo.annotation.UuidSet)")
    public void annotationPointCut(){

    }

    @Before("annotationPointCut()")
    public void doBefore(JoinPoint joinPoint) throws RuntimeException {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String url = "";
        String params = "";
        if(attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
            logger.info("URL : {}" , request.getRequestURL().toString());
            logger.info("HTTP_METHOD : {}" , request.getMethod());
            logger.info("IP : {}" , request.getRemoteAddr());
            logger.info("CLASS_METHOD : {} . {}" , joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            logger.info("params : {}" , request.getParameterMap().toString());

            url = request.getRequestURL().toString();
            params = request.getParameterMap().toString();

        }else {

            url = joinPoint.getSignature().getDeclaringTypeName();

            Object[] objects = joinPoint.getArgs(); // 获取被切函数 的参数

            if(objects != null && objects.length > 0) {
                for(Object object : objects) {
                    if(object instanceof HttpServletRequest) {
                        continue;
                    }

                    logger.info("获取函数传入参数 params : {}", GsonUtils.toJson(object));

                    params = GsonUtils.toJson(object);
                }
            }

            if(params.length() > 200) {
                params = params.substring(0,200);
            }
        }

        logger.info("URL : {} ，获取函数传入参数 params : {}",url,params);

        logger.info("当前线程名称 thread name ：{}",Thread.currentThread().getName());

        //生成 trace_id
        String trace_id = UUID.randomUUID().toString();
        //生成 span_id
        String span_id = UUID.randomUUID().toString();

        logger.info("生成 trace_id : {},span_id : {}",trace_id,span_id);

        //保存幂等处理的参数到线程局部变量中
        ThreadLocalUtil.setThreadLocal(trace_id,span_id,url,params);

    }


}
