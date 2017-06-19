package com.idohoo.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *  重写 ContextLoaderListener，把 ApplicationContext 设置SpringContextUtil以方便使用
 *
 */
public class SysopContextLoaderListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try{
			super.contextInitialized(event);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// 把ApplicationContext 设置到SpringContextUtil
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        SpringContextUtil.setContext(ctx);
	}

}
