package com.idohoo.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Spring context util 获得 Spring context 的 context
 *
 */
public class SpringContextUtil
{
    private static Logger log = LoggerFactory.getLogger(SpringContextUtil.class);

    private static ApplicationContext context = null;

    public static ApplicationContext getContext()
    {
        return context;
    }

    public static void setContext(ApplicationContext context)
    {
        SpringContextUtil.context = context;
        String[] names = context.getBeanDefinitionNames();
        for (String name : names)
        {
            log.info("============ " + name + " ==============");
        }
    }
    public static Object getBean(String beanId)
    {
    	log.info("============ " + context + " ==============");
        return context.getBean(beanId);
    }
    
    public static <T> Object getBean(Class<T> bean)
    {
    	log.info("============ " + context + " ==============");
        return context.getBean(bean);
    }
}
