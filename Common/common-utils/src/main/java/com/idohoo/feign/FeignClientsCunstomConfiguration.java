package com.idohoo.feign;

import com.idohoo.Thread.ThreadLocalContext;
import com.idohoo.util.StringUtil;
import feign.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by henser on 17-5-23.
 */
@Configuration
public class FeignClientsCunstomConfiguration {

    private static Logger logger = LoggerFactory.getLogger(FeignClientsCunstomConfiguration.class);

    @Value("${feignclient.connectTimeoutMillis}")
    private int connectTimeoutMillis;

    @Value("${feignclient.readTimeoutMillis}")
    private int readTimeoutMillis;

    @Value("${feignclient.retry}")
    private int retry;


    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

    @Bean
    public RequestInterceptor headerInterceptor() {

        return new RequestInterceptor() {

            public void apply(RequestTemplate requestTemplate) {

                logger.info("当前线程名称 thread name ：{}",Thread.currentThread().getName());

                String trace_id = ThreadLocalContext.getTraceId();

                if(StringUtil.isNotEmpty(trace_id)) {
                    //获取 span_id
                    String url = requestTemplate.url();
                    String key_map = String.format(FeignHeaderMap.key,trace_id,url);
                    logger.info("获取 url : {}, key_map : {}",url,key_map);
                    String span_id = FeignHeaderMap.initMap().get(key_map);
                    logger.info("获取 span_id : {}",span_id);
                    if(StringUtil.isEmpty(span_id)) {
                        //生成 space_id
                        span_id = UUID.randomUUID().toString();
                        logger.info("生成 span_id : {}",span_id);

                        logger.info("map 的 size : {}",FeignHeaderMap.initMap().size());

                        // map 的大小大于 1000, 数据清空处理，防止对象的值越来越大
                        if(FeignHeaderMap.initMap().size() > 1000) {
                            FeignHeaderMap.initMap().clear();
                        }

                        FeignHeaderMap.initMap().put(key_map,span_id);
                    }
                    logger.info("获取 trace_id : {}, span_id : {}",trace_id,span_id);
                    requestTemplate.header("trace_id",trace_id);
                    requestTemplate.header("span_id",span_id);
                }

            }
        };
    }

    @Bean
    Request.Options feignOptions() {
        /**
         * connectTimeoutMillis
         * readTimeoutMillis
         */
        return new Request.Options(connectTimeoutMillis,readTimeoutMillis);
    }

    @Bean
    Retryer feignRetryer() {
        return FeignRetryerImpl.getInstance(100, SECONDS.toMillis(1), retry);
    }
}
