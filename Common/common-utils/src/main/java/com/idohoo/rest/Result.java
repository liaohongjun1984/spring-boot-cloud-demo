package com.gjjf.framework.rest;

import java.io.Serializable;


/**
 * 返回值对象
 * @author liaohj
 *
 */
public class Result implements Serializable {

	private static final long serialVersionUID = -1586118647101027089L;

	public static Result getSuccResult(){
		return new Result(0, "", "", true);
	}
	
	public static Result getSuccResult(int code){
		return getSuccResult(code,"");
	}
	
	public static Result getSuccResult(String message){
		return getSuccResult(1,message);
	}
	
	public static Result getSuccResult(int code,String message){
		return getSuccResult(code, message, "");
	}
	
	public static Result getSuccResult(int code,String message,Object object){
		return new Result(code, message, object, true);
	}
	
	public static Result getFalseResult(int code,String message){
		return new Result(code, message, "", false);
	}
	
	/**
	 * 返回代码
	 */
	private int code = ResultCode.FAILURE;
	
	/**
	 * 信息
	 */
	private String message;
	
	/**
	 * 对象
	 */
	private Object object;
	
	/**
	 * 是否成功
	 */
	private boolean success = false;
	
	public Result() {
	}

	public Result(int code,String message,Object object,boolean success){
		this.code = code;
		this.message = message;
		this.object = object;
		this.success = success;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
		this.success = (code == ResultCode.SUCCESS);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
