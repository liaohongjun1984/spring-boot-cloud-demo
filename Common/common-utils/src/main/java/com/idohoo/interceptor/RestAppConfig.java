package com.idohoo.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.idohoo.interceptor.RestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


public class RestAppConfig extends WebMvcConfigurerAdapter {

	private final List<RestInterceptor> interceptors = new ArrayList<RestInterceptor>();
	private static ApplicationContext applicationContext;

	public void configurePathMatch(PathMatchConfigurer matcher) {
		matcher.setUseRegisteredSuffixPatternMatch(true);
	}

	public void addInterceptors(InterceptorRegistry registry) {
		for (RestInterceptor item : this.interceptors) {
			InterceptorRegistration res = registry.addInterceptor(item);
			if (item.includePath().length > 0) {
				res.addPathPatterns(item.includePath());
			}

			if (item.excludePath().length > 0)
				res.excludePathPatterns(item.excludePath());
		}
	}

	public void addInterceptor(RestInterceptor... item) {
		if (item != null) {
			this.interceptors.addAll(Arrays.asList(item));
		}
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	protected static void runApp(Class app, String[] args) {
		applicationContext = SpringApplication.run(app, args);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
