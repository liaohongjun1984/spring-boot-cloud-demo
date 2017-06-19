package com.gjjf.framework.rest;

public class BaseResource {

	public <T> ApiResult<T> makeResult(int code, String msg, T res) {
		return new ApiResult<T>(code, msg, res);
	}

	public <T> ApiResult<T> makeResult(ApiExecStatus status, T res) {
		return new ApiResult<T>(status.getCode(), status.getMsg(), res);
	}

	public <T> ApiResult<T> makeResult(ApiExecStatus status, String detail) {
		return new ApiResult<T>(status.getCode(), status.getMsg() + "：" + detail, null);
	}

	public <T> ApiResult<T> success(String msg, T res) {
		return makeResult(ApiExecStatus.SUCCESS.getCode(), msg, res);
	}

	public <T> ApiResult<T> success(T res) {
		return makeResult(ApiExecStatus.SUCCESS, res);
	}

	public <T> ApiResult<T> fail(ApiExecStatus error) {
		return makeResult(error, null);
	}

	public <T> ApiResult<T> fail(ApiExecStatus error, String detail) {
		return makeResult(error, detail);
	}

	public <T> ApiResult<T> fail(int code, String msg) {
		return makeResult(code, msg, null);
	}

	public <T> ApiResult<T> fail(int code, String msg, T res) {
		return makeResult(code, msg, res);
	}

	public <T> ApiResult<T> fail(String msg) {
		return makeResult(ApiExecStatus.FAIL.getCode(), msg, null);
	}

	public <T> ApiResult<T> fail() {
		return makeResult(ApiExecStatus.FAIL, null);
	}

}
