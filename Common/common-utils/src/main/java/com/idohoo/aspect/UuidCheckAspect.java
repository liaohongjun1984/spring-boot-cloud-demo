package com.idohoo.aspect;

import com.idohoo.Thread.ThreadLocalUtil;
import com.idohoo.feign.FeignHeaderMap;
import com.idohoo.util.StringUtil;
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

/**
 * Created by henser on 17-5-23.
 */
@Order(1)
@Aspect
@Component
public class UuidCheckAspect {

    private static Logger logger =LoggerFactory.getLogger(UuidCheckAspect.class);

    //匹配所有UuidCheck注解的接口
    @Pointcut("@annotation(com.idohoo.annotation.UuidCheck)")
    public void annotationPointCut(){

    }

    @Before("annotationPointCut()")
    public void doBefore(JoinPoint joinPoint) throws RuntimeException {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : {}" , request.getRequestURL().toString());
        logger.info("HTTP_METHOD : {}" , request.getMethod());
        logger.info("IP : {}" , request.getRemoteAddr());
        logger.info("CLASS_METHOD : {} . {}" , joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        logger.info("params : {}" , request.getParameterMap().toString());

        //获取 trace_id
        String trace_id = (String)request.getHeader("trace_id");

        //获取 span_id
        String span_id = (String)request.getHeader("span_id");

        logger.info("获取 trace_id : {},span_id :{}",trace_id,span_id);

        if(StringUtil.isNotEmpty(trace_id)
                && StringUtil.isNotEmpty(span_id)) {

            //trace_id 和 span_id 组合成key
            String key_map = String.format(FeignHeaderMap.key,trace_id,span_id);
            String value = FeignHeaderMap.initMap().get(key_map);
            logger.info("获取 key_map : {},value : {}",key_map,value);

            if(StringUtil.isNotEmpty(value)) {
                throw new RuntimeException("请求重复提交，幂等处理，已存在 trace_id");
            }

            if(StringUtil.isEmpty(value)) {

                logger.info("map 的 size : {}",FeignHeaderMap.initMap().size());

                // map 的大小大于 1000, 数据清空处理，防止对象的值越来越大
                if(FeignHeaderMap.initMap().size() > 1000) {
                    FeignHeaderMap.initMap().clear();
                }

                FeignHeaderMap.initMap().put(key_map,trace_id);
            }

            //保存幂等处理的参数到线程局部变量中
            ThreadLocalUtil.setThreadLocal(trace_id,span_id,request.getRequestURL().toString(),request.getParameterMap().toString());

        }

    }


}
