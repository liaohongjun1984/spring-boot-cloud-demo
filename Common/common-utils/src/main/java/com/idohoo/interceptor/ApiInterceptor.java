package com.idohoo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiInterceptor extends RestIntercetorWraper {

	private static Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);
	private static final String TOKEN = "Token";
	private static final String SUBJECT_TEMP_CACHE_NAME = "SubjectTempCache";
	private static Class<?> tokenServiceClass;


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("---preHandle---");
		String uuid = request.getHeader(TOKEN);
		if (uuid == null || "".equals(uuid)) {
			uuid = request.getParameter(TOKEN);
		}
		if (uuid != null && !"".equals(uuid)) {
			bindSubject(uuid);
		}
		return true;
	}

	private void bindSubject(String token) {
		boolean hasCache = false;
		Cache tokenCache = null;
	}

	private boolean verifyToken(String token) {

		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("---afterCompletion---");
		
		// 如果是checked异常，直接抛出
        if (! (ex instanceof RuntimeException) && (ex instanceof Exception)) {
        	logger.info("---业务异常的信息---",ex);
        }else{
        	logger.info("---系统异常的信息---",ex);
        }
		
	}

	static {
		try {
			tokenServiceClass = Class.forName("com.gjjf.common.biz.TokenService");
		} catch (ClassNotFoundException e) {
			logger.info("通常情况下忽略此日志", e.getMessage());
		}
	}
	
}
