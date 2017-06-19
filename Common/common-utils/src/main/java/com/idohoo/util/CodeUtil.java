package com.idohoo.util;

import java.util.Date;

/**
 * 
 * @Description 编码生成工具
 * @date 2016年8月11日 上午11:10:26
 */
public class CodeUtil {
	
	/**
	 * PMS活动定义，活动编码生成
	 * @param sysCode
	 * @return
	 */
	public static String getPmsActCode(String header, long sysCode) {
		// 对10000求模
		int rest = (int) (sysCode % 10000);
		StringBuffer sb = new StringBuffer();
		sb.append(header);
		// 13位时间戳
		sb.append(DateUtil.date2Str(new Date(), "yyyyMMddHHmmss"));
		sb.append(StringUtil.addWithZore(rest, 4));
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @Description 获取认购单编号
	 * @author chensongming
	 * @date 2016年10月6日 下午10:08:28
	 * @param header
	 * @param sysCode
	 * @return
	 * @lastModifier
	 */
	public static String getSubOrderCode(String header, long sysCode) {
		
		// 对10000求模
		int rest = (int) (sysCode % 10000);
		StringBuffer sb = new StringBuffer();
		sb.append(header);
		// 13位时间戳
		sb.append(System.currentTimeMillis())
		.append(StringUtil.addWithZore(rest, 4));
		return sb.toString();
	}
	
	/**
	 * 
	 * @Description 获取认购单子项编号
	 * @author chensongming
	 * @date 2016年10月6日 下午10:08:28
	 * @param header
	 * @param sysCode
	 * @return
	 * @lastModifier
	 */
	public static String getSubOrderItemCode(String header, long sysCode) {
		
		// 对10000求模
		int rest = (int) (sysCode % 10000);
		StringBuffer sb = new StringBuffer();
		sb.append(header);
		// 13位时间戳
		sb.append(System.currentTimeMillis())
		.append(StringUtil.addWithZore(rest, 4));
		return sb.toString();
	}
	
}
