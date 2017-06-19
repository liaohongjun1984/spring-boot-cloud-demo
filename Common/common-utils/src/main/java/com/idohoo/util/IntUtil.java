package com.idohoo.util;

/**
 *
 * Integer 处理类
 *
 */
public class IntUtil {

	public static boolean isZero(Object value)
	{
		if(toInt(value) == 0){
			return true;
		}
		return false;
	}

	public static int toInt(Integer value)
	{
		if(value == null){
			return 0;
		}
		return value;
	}

	public static Integer toInteger(Integer value){
		if(value == null){
			return 0;
		}
		return value;
	}

	public static Integer toInteger(String value){
		if(value == null){
			return null;
		}
		return toInt(value);
	}

	public static int toInt(Object value)
	{
		if(value == null || "".equals(value)){
			return 0;
		}
		try{
			return Integer.valueOf(value.toString());
		}catch(Exception e){
			return 0;
		}
	}

	public static boolean isNotZero(Integer value)
	{
		return !isZero(value);
	}
}
