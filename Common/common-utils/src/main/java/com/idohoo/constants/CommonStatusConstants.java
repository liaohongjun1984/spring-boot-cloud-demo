package com.idohoo.constants;

public class CommonStatusConstants {


	/**
	 * 请求处理的状态：0：处理开始，1：处理失败，2：处理成功
	 */
	public interface status {

		/**
		 * 0：处理开始
		 */
		public static final int IS_DOING = 0;

		/**
		 * 1：处理失败
		 */
		public static final int IS_FAIL = 1;

		/**
		 * 2：处理成功
		 */
		public static final int IS_SUCCESS = 2;


	}


}