package com.idohoo.util;

import org.springframework.context.ApplicationContext;

public class AppUtils {

	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		AppUtils.applicationContext = applicationContext;
	}

	public static <T> T getBean(Class<T> clazz) {
		if (applicationContext == null) {
			return null;
		}
		return applicationContext.getBean(clazz);
	}

	public static <T> T getBean(String beanId) {
		return (T) applicationContext.getBean(beanId);
	}

}
