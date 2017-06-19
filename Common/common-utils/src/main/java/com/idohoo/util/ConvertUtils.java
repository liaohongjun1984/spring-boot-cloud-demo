package com.idohoo.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertUtils {

	private static final String FORMAT_DATE_LONG = "yyyy-MM-dd HH:mm:ss";
	
	public static int strToInt(String str, int defaultValue) {
		try {
			defaultValue = Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}
	public static long strToLong(String str, long defaultValue) {
		try {
			defaultValue = Long.parseLong(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}
	public static float strToFloat(String str, float defaultValue) {
		try {
			defaultValue = Float.parseFloat(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}
	public static double strToDouble(String str, double defaultValue) {
		try {
			defaultValue = Double.parseDouble(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}
	public static boolean strToBoolean(String str, boolean defaultValue) {
		try {
			defaultValue = Boolean.parseBoolean(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}
	public static Date strToDate(String str, Date defaultValue) {
		return strToDate(str, FORMAT_DATE_LONG, defaultValue);
	}
	public static Date strToDate(String str, String format, Date defaultValue) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			defaultValue = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}
	public static String dateToStr(Date date, String defaultValue) {
		return dateToStr(date, FORMAT_DATE_LONG, defaultValue);
	}
	public static String dateToStr(Date date, String format, String defaultValue) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			defaultValue = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}
	public static String strToStr(String str, String defaultValue) {
		if((str!=null) && (!str.isEmpty())) {
			defaultValue = str;
		}
		return defaultValue;
	}
	public static String objToStr(Object obj, String defaultValue) {
		if(obj!=null) {
			defaultValue = obj.toString();
		}
		return defaultValue;
	}
	public static java.sql.Date dateToSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}
	public static Date sqlDateToDate(java.sql.Date date) {
		return new Date(date.getTime());
	}
	public static Timestamp dateToSqlTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
	public static Date sqlTimestampToDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
	public static int strToAsc(String str) {
		return str.getBytes()[0];
	}
	public static char intToChar(int backnum) {
		return (char)backnum;
	}
	
}
