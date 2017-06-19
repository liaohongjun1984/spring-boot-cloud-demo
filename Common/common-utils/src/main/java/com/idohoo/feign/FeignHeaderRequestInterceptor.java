package com.idohoo.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by henser on 17-5-31.
 */
public class FeignHeaderRequestInterceptor implements RequestInterceptor {

    private static Logger logger =LoggerFactory.getLogger(FeignHeaderRequestInterceptor.class);

    private final String headerValue;

    public FeignHeaderRequestInterceptor(String uuid) {
        this.headerValue = uuid;
    }

    @Override
    public void apply(RequestTemplate template) {
        logger.info("当前线程名称 thread name ：{}",Thread.currentThread().getName());
        logger.info("获取 headerValue : {}",headerValue);
        template.header("uuid", headerValue);
    }

}
