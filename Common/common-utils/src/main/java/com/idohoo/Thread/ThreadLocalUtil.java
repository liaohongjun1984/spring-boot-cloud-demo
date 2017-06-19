package com.idohoo.Thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by henser on 17-6-1.
 */
public class ThreadLocalUtil {

    private static Logger logger =LoggerFactory.getLogger(ThreadLocalUtil.class);

    /**
     * 保存幂等处理的参数到线程局部变量中
     * @param trace_id
     * @param span_id
     * @param url
     * @param params
     */
    public static void setThreadLocal(String trace_id,String span_id,String url,String params) {


        logger.info("ThreadLocalUtil trace_id :{},span_id :{},url:{},params:{}",
                trace_id,span_id,url,params);

        //线程局部变量清除
        ThreadLocalContext.clear();

        //线程局部变量保存 trace_id
        ThreadLocalContext.setTraceId(trace_id);
        //线程局部变量保存 span_id
        ThreadLocalContext.setSpanId(span_id);
        //线程局部变量保存 url
        ThreadLocalContext.setUrl(url);
        //线程局部变量保存 params
        ThreadLocalContext.setParams(params);

    }

}
