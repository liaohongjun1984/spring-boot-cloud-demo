package com.idohoo.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;

public interface RestInterceptor extends HandlerInterceptor {

	public String[] includePath();

	public String[] excludePath();

	public void addIncludePaths(String... paths);

	public void addExcludePaths(String... paths);

}
