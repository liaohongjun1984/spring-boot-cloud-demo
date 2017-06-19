package com.gjjf.framework.rest;


/**
 * <p>定义 service 层的返回值</p>
 *
 */
public class ResultCode {

	/** 成功 */
	public static final int SUCCESS = 0;

	/** 失败 */
	public static final int FAILURE = -1;

	/** 参数错误 */
	public static final int PARAMETER_ERROR = 1;

	/** 其他错误 */
	public static final int OTHER_ERROR = -999999;

}
