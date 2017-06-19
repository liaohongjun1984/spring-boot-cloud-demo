package com.idohoo.util;

public class HttpRes {
	//检查的URL
	private String URL;
	//请求时间
	private long req_time;
	//响应时间
	private long resp_period;
	//是否超时
	private boolean timeout = false;
	//是否出现异常
	private boolean isExcep;
	//请求的head头
	private String requestHeader;
	//返回的head头
	private String responseHeader;
	//返回的数据
	private String response;

	private int responseCode;

	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public long getReq_time() {
		return req_time;
	}
	public void setReq_time(long req_time) {
		this.req_time = req_time;
	}

	public long getResp_period() {
		return resp_period;
	}
	public void setResp_period(long resp_period) {
		this.resp_period = resp_period;
	}
	public boolean isTimeout() {
		return timeout;
	}
	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}
	public boolean isExcep() {
		return isExcep;
	}
	public void setExcep(boolean isExcep) {
		this.isExcep = isExcep;
	}
	public String getRequestHeader() {
		return requestHeader;
	}
	public void setRequestHeader(String requestHeader) {
		this.requestHeader = requestHeader;
	}
	public String getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(String responseHeader) {
		this.responseHeader = responseHeader;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}


}
