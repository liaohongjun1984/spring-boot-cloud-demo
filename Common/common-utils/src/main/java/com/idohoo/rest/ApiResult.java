package com.gjjf.framework.rest;

public class ApiResult<T> {

	private final int code;
	private final String msg;
	private final T result;
	
	public ApiResult(int code, String msg) {
		this(code, msg, null);
	}
	
	public ApiResult(int code, String msg, T result) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getResult() {
		return result;
	}

}
