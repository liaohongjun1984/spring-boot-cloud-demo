package com.idohoo.Thread;

/**
 *  线程 专属的threadLocal 保存线程的局部变量
 */
public class ThreadLocalContext {

	/** 保存当前线程 uuid */
	private static ThreadLocal<String> uuidHolder = new ThreadLocal<String>();

	/** 保存当前线程 trace_id */
	private static ThreadLocal<String> traceIdHolder = new ThreadLocal<String>();

	/** 保存当前线程 span_id */
	private static ThreadLocal<String> spanIdHolder = new ThreadLocal<String>();

	/** 保存当前线程 请求 url */
	private static ThreadLocal<String> urlHolder = new ThreadLocal<String>();

	/** 保存当前线程 请求 params */
	private static ThreadLocal<String> paramsHolder = new ThreadLocal<String>();

	/**
	 * 获得 uuid
	 * @return
	 */
	public static String getUuid() {
		return uuidHolder.get();
	}

	/**
	 * 设置 uuid
	 * @param uuid
	 */
	public static void setUuid(String uuid) {
		uuidHolder.set(uuid);
	}

	/**
	 * 获得 traceId
	 * @return
	 */
	public static String getTraceId() {
		return traceIdHolder.get();
	}

	/**
	 * 设置 traceId
	 * @param traceId
	 */
	public static void setTraceId(String traceId) {
		traceIdHolder.set(traceId);
	}

	/**
	 * 获得 spanId
	 * @return
	 */
	public static String getSpanId() {
		return spanIdHolder.get();
	}

	/**
	 * 设置 spanId
	 * @param spanId
	 */
	public static void setSpanId(String spanId) {
		spanIdHolder.set(spanId);
	}

	/**
	 * 获得 url
	 * @return
	 */
	public static String getUrl() {
		return urlHolder.get();
	}

	/**
	 * 设置 url
	 * @param url
	 */
	public static void setUrl(String url) {
		urlHolder.set(url);
	}

	/**
	 * 获得 params
	 * @return
	 */
	public static String getParams() {
		return paramsHolder.get();
	}

	/**
	 * 设置 params
	 * @param params
	 */
	public static void setParams(String params) {
		paramsHolder.set(params);
	}

	// --------------------------- clear
	/**
	 * 清空所有数据
	 */
	public static void clear() {
		uuidHolder.set(null);
		traceIdHolder.set(null);
		spanIdHolder.set(null);
		urlHolder.set(null);
		paramsHolder.set(null);
	}

	/**
	 * 局部线程变量资源清理
	 */
	public static void remove(){
		uuidHolder.remove();
		traceIdHolder.remove();
		spanIdHolder.remove();
		urlHolder.remove();
		paramsHolder.remove();
	}
}
