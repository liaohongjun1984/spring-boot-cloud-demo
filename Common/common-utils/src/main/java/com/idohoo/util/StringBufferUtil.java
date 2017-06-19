package com.idohoo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * stringBuffer操作常用方法类.
 * <p>
 *
 */

public class StringBufferUtil {

	/**
	 * 字符串拼接
	 * @Description
	 * @author henser
	 * @date 2016年8月20日 上午10:27:55
	 * @param strs
	 * @return
	 * @lastModifier
	 */
	public static String appendToString(String... strs) {

		StringBuffer buffer = new StringBuffer();

		if(strs.length <= 0){
			return "";
		}

		for(String str : strs) {
			buffer.append(str);
		}

		return buffer.toString();

	}

	/**
	 * 整形转字符串拼接
	 * @Description
	 * @author henser
	 * @date 2016年8月24日 下午9:51:24
	 * @param num
	 * @param strs
	 * @return
	 * @lastModifier
	 */
	public static String intAppendString(Integer num,String... strs) {

		StringBuffer buffer = new StringBuffer();

		if(strs.length <= 0){
			return "";
		}

		if(num == null) {
			num = 0;
		}

		buffer.append(num);

		for(String str : strs) {
			buffer.append(str);
		}

		return buffer.toString();

	}

	/**
	 * 整形转字符串
	 * @Description
	 * @author henser
	 * @date 2016年8月25日 下午2:48:33
	 * @param num
	 * @param strs
	 * @return
	 * @lastModifier
	 */
	public static String intToString(Integer num) {

		if(num == null) {
			num = 0;
		}

		StringBuffer buffer = new StringBuffer();

		buffer.append(num);

		return buffer.toString();

	}


	public static String doubleToString(Double num) {

		if(num == null) {
			num = 0.0;
		}

		StringBuffer buffer = new StringBuffer();

		buffer.append(num);

		return buffer.toString();

	}

	/**
	 * json字符串首字母大写变成小写
	 * @Description
	 * @author henser
	 * @date 2016年11月19日 下午5:24:49
	 * @param json
	 * @return
	 * @lastModifier
	 */
	public static String jsonStringToLowerCase(String json) {


		if(StringUtil.isEmpty(json)) {
			return "";
		}

		String regex = "([^\"])[a-zA-Z]+([$\"])+:";

        Pattern pattern = Pattern.compile(regex);
        StringBuffer sb = new StringBuffer();

        Matcher m = pattern.matcher(json);
        while (m.find()) {

        	String str = m.group();
        	char chars[] = str.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);

            m.appendReplacement(sb, new String(chars));
        }
        m.appendTail(sb);
        String msg = sb.toString();

	    return msg;

	}

	public static void main(String[] arg) {

		String str_array = "[{\"ID\":\"\",\"orgId\":\"\",\"MemberId\":\"19\",\"Style\":\"J004\",\"CreatedBy\":\"Admin\",\"CreatedDate\":\"2016-11-19 14:45:38\",\"LastUpdatedBy\":\"Admin\",\"LastUpdatedDate\":\"2016-11-19 14:45:38\"},{\"ID\":\"\",\"OrgId\":\"\",\"MemberId\":\"19\",\"Style\":\"J003\",\"CreatedBy\":\"Admin\",\"CreatedDate\":\"2016-11-19 14:45:38\",\"LastUpdatedBy\":\"Admin\",\"LastUpdatedDate\":\"2016-11-19 14:45:38\"}]";

		String regex = "([^\"])[a-zA-Z]+([$\"])+:";

        Pattern pattern = Pattern.compile(regex);
        StringBuffer sb = new StringBuffer();

        Matcher m = pattern.matcher(str_array);
        while (m.find()) {
        	System.out.println("matcher: " + m.group(0).charAt(0));

        	String str = m.group();
        	System.out.println("str: " + str);
        	char chars[] = str.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);

            m.appendReplacement(sb, new String(chars));
        }
        m.appendTail(sb);
        String msg = sb.toString();

        String jsonString = jsonStringToLowerCase(str_array);

		System.out.println("msg:"+msg);

		System.out.println("jsonString:"+jsonString);

	}

}
