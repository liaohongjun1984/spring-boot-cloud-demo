package com.idohoo.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;


@Component
public class AuthzInterceptor extends RestIntercetorWraper {

	private static Logger logger = LoggerFactory.getLogger(AuthzInterceptor.class);
	private static final String TOKEN = "Token";
	private static final String SUBJECT_TEMP_CACHE_NAME = "SubjectTempCache";
	private static Class<?> tokenServiceClass;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("---preHandle---");
		String token = request.getHeader(TOKEN);
		if (token == null || "".equals(token)) {
			token = request.getParameter(TOKEN);
		}
		if (token != null && !"".equals(token)) {
			bindSubject(token);
		}
		return true;
	}

	private void bindSubject(String token) {
//		AccessUtils.setUserAccessToken(token);
		boolean hasCache = false;
		Cache tokenCache = null;
//		if (cacheManager != null && cacheManager.getCache(SUBJECT_TEMP_CACHE_NAME) != null) {
//			tokenCache = cacheManager.getCache(SUBJECT_TEMP_CACHE_NAME);
//			Cache.ValueWrapper valueWrapper = tokenCache.get(token);
//			if (valueWrapper != null) {
//				Subject subject = (Subject) valueWrapper.get();
//				SubjectThreadUtils.bind(subject);
//				hasCache = true;
//			}
//		}

//		if ((!hasCache) && (verifyToken(token))) {
//			Subject subject = new Subject(token);
//			SubjectThreadUtils.bind(subject);
//			if (tokenCache != null)
//				tokenCache.put(token, subject);
//		}
	}

	private boolean verifyToken(String token) {
//		if (tokenServiceClass != null) {
//			try {
//				Method verifyToken = tokenServiceClass.getDeclaredMethod("verifyToken", new Class[] { String.class });
//				Object bean = AppUtils.getBean(tokenServiceClass);
//				Object flag = verifyToken.invoke(bean, new Object[] { token });
//				return ((Boolean) flag).booleanValue();
//			} catch (Exception e) {
//				logger.error("verifyToken", e);
//			}
//		}
		return true;
//		return tokenService.verifyToken(token);
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
