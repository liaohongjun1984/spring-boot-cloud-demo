package com.idohoo.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * 字符串操作常用方法类.
 * <p>
 *
 */

public class StringUtil {

	public static final long INVALID_LONG_VALUE = Long.MIN_VALUE;

	/**
	 * 删除字符
	 *
	 * @param content
	 * @param str
	 * @return NONE
	 */
	public static String deleteTail(String content, String str) {
		if (content == null || str == null) {
			return content;
		}
		int minSize = str.length();
		if (content.length() >= minSize) {
			content = content.substring(0, content.length() - minSize);
		}
		return content;
	}

	/**
	 * 转换正则表达式特殊字符
	 *
	 * @param content
	 *            String
	 * @return String
	 */
	public static String escapePattern(String content) {
		// 这个不要使用正则表达式,或者要测试一下正则表达式的性能
		if (content == null) {
			return null;
		}
		content = content.replaceAll("\\\\", "\\\\\\\\");
		content = content.replaceAll("\\?", "\\\\?");
		content = content.replaceAll("\\+", "\\\\+");
		content = content.replaceAll("\\*", "\\\\*");
		content = content.replaceAll("\\[", "\\\\[");
		content = content.replaceAll("\\]", "\\\\]");
		content = content.replaceAll("\\{", "\\\\{");
		content = content.replaceAll("\\}", "\\\\}");
		content = content.replaceAll("\\(", "\\\\(");
		content = content.replaceAll("\\)", "\\\\)");
		content = content.replaceAll("\\-", "\\\\-");
		content = content.replaceAll("\\$", "\\\\\\$");
		return content;
	}

	/**
	 * 固定位数
	 *
	 * @param number
	 *            int
	 * @param minlen
	 *            int
	 * @return String
	 */
	public static String fixed(int number, int minlen) {
		String result = String.valueOf(number);
		while (result.length() < minlen) {
			result = "0" + result;
		}
		return result;
	}

	/**
	 * 在 str 中查找 s1 出现的次数
	 *
	 * @param str
	 * @param s1
	 * @return
	 */
	public static final int find(String str, String s1) {
		int count = 0;
		int fromindex = -1;
		while ((fromindex = str.indexOf(s1, fromindex + 1)) > -1) {
			count++;
		}
		return count;
	}

	/**
	 * 字符串为空时返回默认值，否则返回字符串本身
	 *
	 * @param str
	 *            - 字符串
	 * @param defaultValue
	 *            - 默认值
	 * @return
	 */
	public static String getDefault(String str, String defaultValue) {
		if (StringUtil.isNotEmpty(str)) {
			return str;
		}
		return defaultValue;
	}

	/**
	 * 过滤特殊字符，返回<code>String</code>类型.
	 * <p>
	 *
	 * 遇到特殊字符就中断截取,值只能是[a-zA-Z0-9].
	 *
	 * @param string
	 * @return 没有该参数则返回空字符串，不返回null.
	 */
	public String getSimpleString(String string) {
		if (string == null) {
			return "";
		}
		String regex = "[a-zA-Z0-9]+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(string);
		if (m.find()) {
			return m.group();
		}
		return "";
	}

	/**
	 * 判断字符串是否为 null 或 空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 经过 trim 后是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isTrimEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * @Description:把list转换为一个用逗号分隔的字符串
	 */
	public static String listToString(List list) {
	    StringBuilder sb = new StringBuilder();
	    if (list != null && list.size() > 0) {
	        for (int i = 0; i < list.size(); i++) {
	            if (i < list.size() - 1) {
	                sb.append(list.get(i) + ",");
	            } else {
	                sb.append(list.get(i));
	            }
	        }
	    }
	    return sb.toString();
	}

	/**
	 * 判断字符串是否不为 null 和 空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}

	/**
	 * 判断字符串是否不为 null 和空
	 *
	 * @param str
	 * @deprecated
	 * @return
	 */
	public static boolean isFine(String str) {
		return isNotEmpty(str);
	}

	/**
	 * 把数组拼接成字符串
	 *
	 * @param strs
	 * @param split
	 *            - 分隔符
	 * @return
	 */
	public static String join(String[] strs, String split) {
		if (strs == null || strs.length == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(strs[0]);
		for (int i = 1; i < strs.length; i++) {
			sb.append(split).append(strs[i]);
		}
		return sb.toString();
	}

	/**
	 * 把数组拼接成字符串
	 *
	 * @param strs
	 * @param split
	 *            - 分隔符
	 * @return
	 */
	public static String join(List<String> list,String split)
	{
		return join(list.toArray(),split);
	}

	/**
	 * 把数组拼接成字符串
	 *
	 * @param strs
	 * @param split
	 *            - 分隔符
	 * @return
	 */
	public static String join(Object[] arr,String split)
	{
		StringBuilder result = new StringBuilder();
		for(int i=0;i<arr.length;i++){
			if(i!=arr.length-1){
			    result.append(arr[i].toString()).append(split);
			}else{
				result.append(arr[i].toString());
			}
		}
		return result.toString();
	}

	/**
	 * 把数组拼接成字符串
	 *
	 * @param strs
	 * @param split
	 *            - 分隔符
	 * @return
	 */
	public static String join(Object[] arr)
	{
		return join(arr,",");
	}

	/**
	 * 把数组拼接成字符串
	 *
	 * @param strs
	 * @param split
	 *            - 分隔符
	 * @return
	 */
	public static String join(List<String> list)
	{
		return join(list.toArray());
	}

	/**
	 * 如果 str 为 null，返回空字符串（""），否则返回 str
	 *
	 * @param str
	 * @return
	 */
	public static String getNotNull(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}

	public static double parseDouble(String str) {
		double v = 0;
		try {
			v = Double.parseDouble(str);
		} catch (Exception e) {
			v = INVALID_LONG_VALUE;
		}
		return v;
	}

	public static int parseInt(double num) {
		return new Double(num).intValue();
	}

	public static int parseInt(String str) {
		return parseInt(str, Integer.MIN_VALUE);
	}

	public static int parseInt(String str, int custom) {
		return parseInt(str, Integer.MIN_VALUE, custom);
	}

	public static int parseInt(String str, int min, int custom) {
		int result = custom;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			// e.printStackTrace();
			result = custom;
		}
		// result = Math.abs(result);
		if (result < min) {
			result = min;
		}
		return result;
	}

	public static long parseLong(String str) {
		long v = 0;
		try {
			v = Long.parseLong(str);
		} catch (Exception e) {
			v = INVALID_LONG_VALUE;
		}
		return v;
	}

	/**
	 * 获得字符串的子字符串，考虑中文为 2 个字符
	 *
	 * @param str
	 * @param len
	 * @return
	 */
	public static String subString(String str, int len) {
		return subString(str, 0, len);
	}

	/**
	 * 获得字符串的子字符串，考虑中文为 2 个字符
	 *
	 * @param str
	 * @param start
	 * @param len
	 * @return
	 */
	private static String subString(String str, int start, int len) {
		if (str == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		int counter = 0;
		for (int i = start; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < 255) {
				counter++;
			} else {
				counter = counter + 2;
			}
			if (counter > len) {
				break;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * s中的s1替换成s2
	 *
	 * @param s
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static final String replace(String s, String s1, String s2) {
		if (s == null)
			return null;
		int i = 0;
		if ((i = s.indexOf(s1, i)) >= 0) {
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int j = s1.length();
			StringBuffer sb = new StringBuffer(ac.length);
			sb.append(ac, 0, i).append(ac1);
			i += j;
			int k;
			for (k = i; (i = s.indexOf(s1, i)) > 0; k = i) {
				sb.append(ac, k, i - k).append(ac1);
				i += j;
			}
			sb.append(ac, k, ac.length - k);
			return sb.toString();
		} else {
			return s;
		}
	}

	/**
	 * 转换成 '1','2','3' 这样的形式
	 */
	public static String joinString(String strs[]) {
		if (strs == null) {
			return null;
		}

		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			if (i > 0) {
				buf.append(',');
			}
			buf.append('\'');
			buf.append(strs[i]);
			buf.append('\'');
		}
		return buf.toString();
	}

	/**
	 * 分割为 int 数组，默认以 , 作分隔符
	 *
	 * @param content
	 * @return
	 */
	public static int[] toInts(String content) {
		return toInts(content, ",");
	}

	public static int[] toInts(String content, String split) {
		if (isEmpty(content) || split == null) {
			return null;
		}
		String[] strs = content.split(split);
		if (strs.length == 0) {
			return null;
		}
		int[] re = new int[strs.length];
		for (int i = 0; i < re.length; i++) {
			re[i] = parseInt(strs[i]);
		}

		return re;
	}

	/**
	 * 将字符串分解成字符串数组.
	 * <p>
	 *
	 * @param str
	 * @return NONE
	 */
	public static String[] split(String str) {
		if (isEmpty(str)) {
			return null;
		}
		int SIZE = 125;
		int pages = (str.length() + SIZE - 1) / SIZE;
		String[] contents = new String[pages];
		int start = 0;
		for (int i = 0; i < pages; i++) {
			if (i == (pages - 1)) {
				contents[i] = str.substring(start);
			} else {
				contents[i] = str.substring(start, start + SIZE);
			}
			start += SIZE;
		}
		return contents;
	}

	/**
	 * 分段
	 *
	 * @param content
	 * @param coptem
	 * @return
	 */
	public static String[] patchMa(String content, String coptem) {
		StringBuffer contentBatch = new StringBuffer();
		Pattern pa = Pattern.compile(coptem);
		Matcher ma = pa.matcher(content);
		int pos = 0;
		int ii = 0;

		while (ma.find(pos)) {
			contentBatch.append(ma.group(1));
			contentBatch.append("#!#");
			ii = ii + 1;
			pos = ma.end();
		}
		return contentBatch.toString().split("#!#");
	}

	/**
	 * 在数字前补零
	 *
	 * @param num
	 *            数字
	 * @param length
	 *            输出位数
	 */
	@Deprecated
	public static String addzero(int num, int length) {
		String str = "";
		if (num < Math.pow(10, length - 1)) {
			for (int i = 0; i < (length - (num + "").length()); i++) {
				str += "0";
			}
		}
		str = str + num;
		return str;
	}

	/**
	  * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
	  * @param sourceData
	  * @param length
	  * @return 重组后的数据
	  */
	 public static String addWithZore(int sourceData,int length) {
		 /*
		  * 0 指前面补充零
		  * length 字符总长度为 length
		  * d 代表为正数。
		  */
		 return String.format("%0"+length+"d", sourceData);
	}
	 
	public static boolean isEmail(String email) {
		if (isTrimEmpty(email)) {
			return false;
		}
		// String pattern = "^\\w+\\.*\\w+@\\w+\\.\\w+$";
		String pattern = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})+$";

		if (email.matches(pattern)) {
			return true;
		}
		return false;
	}

	/**
	 * 检查是否为数字
	 *
	 * @param sNum
	 * @return
	 */
	public static boolean isNum(String sNum) {
		if (isTrimEmpty(sNum)) {
			return false;
		}
		if (sNum.matches("[0-9]*")) {
			return true;
		}
		return false;
	}

	/**
	 * 获得一个字符串有多少个字节
	 *
	 * @param s
	 *            - 字符串
	 * @return 长度
	 */
	public static int getByteLength(String s) {
		return (s == null ? 0 : s.getBytes().length);
	}

	// public static boolean isEmpty(String str){
	// return (null == str || str.equals("") || str.isEmpty());
	// }
	/**
	 * 全角半角互转方法
	 *
	 * @param str
	 *            要转换的字符串
	 * @param flag
	 *            标记，为false时半转全，为true时全转半
	 * @return
	 */
	public static String dbc2sbc(String str, boolean flag) {
		// 如果传入的字符串为空串，则直接返回
		if (str.length() <= 0)
			return "";
		char[] inputStr = str.toCharArray();
		for (int i = 0; i < inputStr.length; i++) {
			int str1 = inputStr[i];
			// 全角空格12288，半角空格为32,全角中文句号12290,全角英文句号65294，半角句号46
			// 其他字符半角(33-126)与全角(65281-65347)的对应关系，均相差65248
			// 半角转全角
			if (!flag) {
				if (str1 == 32 || (str1 >= 33 && str1 <= 126))
					str1 = str1 + 65248;
				if (str1 == 46)
					str1 = 65294;
			}
			// 全角转半角
			else {
				if (str1 == 12288 || (str1 >= 65281 && str1 <= 65347))
					str1 = str1 - 65248;
				if (str1 == 12290 || str1 == 65294)
					str1 = 46;
			}
			inputStr[i] = (char) str1;
		}
		return new String(inputStr);
	}

	/**
	 * 全角转半角
	 *
	 * @param str
	 * @return
	 */
	public static String fullToHalf(String str) {
		return dbc2sbc(str, true);
	}

	/**
	 * 半角转全角
	 *
	 * @param str
	 * @return
	 */
	public static String halfToFull(String str) {
		return dbc2sbc(str, false);
	}

	/**
	 * input如果长度不够length则未尾加空格补齐到length长度
	 *
	 * @param input
	 * @param length
	 * @return
	 */
	public static String addBlankWord(String input, int length) {
		if (input.length() >= length)
			return input;
		StringBuilder sb = new StringBuilder(input);
		for (int i = input.length(); i < length; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * 把字符串转换成ascii码
	 *
	 * @param str
	 * @return
	 */
	public static byte[] strToAscii(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char[] c = str.toCharArray();
		byte[] b = new byte[c.length];
		for (int i = 0; i < c.length; i++)
			b[i] = (byte) (c[i] & 0x007F);
		return b;
	}

	public static String strToAscii(String str, String fix) {
		byte[] b = strToAscii(str);
		if (b == null) {
			return "";
		}
		String s = "";
		for (int i = 0; i < b.length; i++) {
			s = s + b[i] + fix;
		}
		return s.substring(0, s.length() - 1);
	}

	/**
	 * ASCII码转字符串
	 *
	 * @param ascii
	 * @param fix
	 * @return
	 */
	public static String asciiToStr(String ascii, String fix) {
		String[] asciiArr = ascii.split(fix);
		int length = asciiArr.length;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append((char) Integer.parseInt(asciiArr[i]));
		}
		return sb.toString();
	}

	/**
	 * 将中文进行ASCII编码 : "线双线" -> "\u897f\u5b89\u53cc\u7ebf"
	 *
	 * @param str
	 * @return
	 * @author liuzifeng
	 */
	public static String toAsciiString(String str) {
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char ch : chars) {
			String tmp = ch + "";
			if(tmp.length() == tmp.getBytes().length) {
				sb.append(tmp);
			} else {
				String hex = "\\u" + Integer.toHexString((int)ch);
				sb.append(hex);
			}
		}
		return sb.toString();
	}

	/**
	 * 将ASCII码字符串转换成中文 : "\u897f\u5b89\u53cc\u7ebf" -> "西安双线"
	 * <br>(能同时转换 "\u8888" 和 "\\u8888" )
	 *
	 * @param str
	 * @return
	 * @author liuzifeng
	 */
	public static String toNormalString(String str) {
		StringBuffer sb = new StringBuffer();
		boolean begin = true;
		String[] ss = str.split("\\\\\\\\u");
		if(ss.length == 1) ss = str.split("\\\\u");
		for(String s : ss) {
			if(begin) {
				begin = false;
				sb.append(s);
				continue;
			}
			if(s.length()<4){
				continue;
			}
			String ascii = s.substring(0, 4), tmp = s.substring(4);
			sb.append((char)Integer.valueOf(ascii, 16).intValue()).append(tmp);
		}
		return sb.toString();
	}

	/**
	 * wangxichun add escapeCmd(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 *
	 * @param ascii
	 * @param fix
	 * @return
	 */
	public static String escapeCmd(String content) {
		if (content == null || "".equals(content)) {
			return "";
		}
		content = StringUtils.replace(content, "\"", "\\\\\\\"");
		content = StringUtils.replace(content, "'", "\\'");
		content = StringUtils.replace(content, " ", "\\ ");
		return content;
	}

	public static String toString(String[] str, String seperator) {
		if (str == null || str.length == 0)
			return "";
		StringBuffer buf = new StringBuffer();
		for (int i = 0, n = str.length; i < n; i++) {
			if (i != 0)
				buf.append(seperator);
			buf.append(str[i]);
		}
		return buf.toString();
	}

	/**
	 * 解码
	 *
	 * @param str
	 * @return string
	 */
	public static byte[] decodeBase64(String str) {
		Base64 base64 = new Base64();
		byte[] bytes = null;
		bytes = base64.decode(str.getBytes());

		return bytes;
	}

	/**
	 * 编码
	 *
	 * @param str
	 * @return string
	 */
	public static byte[] encodeBase64(String str) {
		Base64 base64 = new Base64();
		byte[] bytes = null;
		bytes = base64.encode(str.getBytes());

		return bytes;
	}

	public static boolean check() {
		int versionType = 1;
		String version = "2.2.2";
		List<String> versionList = new ArrayList<String>();
		versionList.add("2.2.3");
		versionList.add("2.2.4");
		versionList.add("2.2.5");
		versionList.add("2.2.1");
		versionList.add("2.2.7");

		for (String v : versionList) {
			if (versionType == 0) {
				if (Integer.parseInt(v) < Integer.parseInt(version)) {
					return true;
				}
			} else {
				String[] versions = version.split("\\.");
				String[] preVersions = v.split("\\.");
				System.out.println(versions.length);
				if (Integer.parseInt(preVersions[0]) < Integer
						.parseInt(versions[0]))
					return true;
				if (Integer.parseInt(preVersions[1]) < Integer
						.parseInt(versions[1]))
					return true;
				if (Integer.parseInt(preVersions[2]) < Integer
						.parseInt(versions[2]))
					return true;
			}
		}
		return false;
	}

	public static boolean isInt(String str) {
	    return str.matches("\\d+");
	  }

	public static int getInt(String str, int defaultValue) {
		if (str == null)
			return defaultValue;
		if (isInt(str)) {
			return Integer.parseInt(str);
		} else {
			return defaultValue;
		}
	}
	/**
	 * 获取数据库操作的in语句
	 * @param ids
	 * @return
	 */
	public static String sqlIds(List<Integer> ids){
		StringBuilder sbStr = new StringBuilder("");
		for(Integer id : ids){
			sbStr.append(id).append(",");
		}
		if(sbStr.length()>1){
			sbStr.delete(sbStr.length()-1, sbStr.length());
		}else{
			return null;
		}
		return sbStr.toString();
	}

	public static String trans(String str){
		if(str == null || str.length() == 0){
			return "无";
		}
		return str;
	}

	/**
	 * 用0补全数字前面的位数
	 * @param flowid
	 * @param length
	 * @return
	 */
	@Deprecated
	public static String getAddZerro(int flowid,int length){
		String flowIdStr = String.valueOf(flowid);
		for(int i=flowIdStr.length();i<length;i++){
			flowIdStr = "0"+flowIdStr;
		}
		return flowIdStr;
	}

	/**
	 * 使用正则表达式获取字符串中的数字
	 * @param String
	 * @return
	 */
	public static int getInt(String str){

		Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(str);
        String all = matcher.replaceAll("");
        int num  = Integer.parseInt(all);

        return num;
	}

	/**
	 * 解析参数地址
	 * @param search
	 * @return
	 */
	public static Map<String,String> parseSearch(String search){
		Map<String,String> paramMap = new HashMap<String,String>();
		for(String param : search.split("&")){
			String[] para = param.split("=");
			paramMap.put(para[0], para[1]);
		}
		return paramMap;
	}

	/**
	 * 用0补全数字前面的位数
	 * @param flowid
	 * @param length
	 * @return
	 */
	public static String getSplit(String code,int length,String flag){

		int len = code.length()/length;
		String codes = "";
		for(int i = 0;i < len; i++){
			codes += code.substring(i*length,(i+1)*length);
			if(i+1 < len){
				codes += flag;
			}
		}

		return codes;

	}

	/**
	 *
	 * @Title: stringToIntegerList
	 * @author henser  henser123@163.com
	 * @date 2016年6月13日 下午8:53:35
	 * @Description:  字符串转换成整数list
	 * @param str
	 * @param split
	 * @return
	 * @throws
	 */
	public static List<Integer> stringToIntegerList(String str,String split) {

		String[] idArray = StringUtils.split(str, split);
		List<Integer> idList = new ArrayList<Integer>();
		if(idArray.length > 0){
			for(String id:idArray){
				if(StringUtil.isNotEmpty(id)){
					idList.add(Integer.parseInt(id));
				}
			}
		}

		return idList;

	}
	
	/**
	 * 整形价格转为2位小数的字符串
	 * @Description 
	 * @author TanJY
	 * @date 2016年8月15日 下午2:45:15
	 * @param price
	 * @return
	 * @lastModifier
	 */
	public static String priceIntToString(Integer price){
	    if(price==null){
	        return "";
	    }
	    DecimalFormat df2=(DecimalFormat) DecimalFormat.getInstance(); 
	    df2.applyPattern("0.00"); 
	    return df2.format(price.doubleValue()/100);
	}
	
	/**
	 * 返回字符串布尔值
	 * @Description 
	 * @author TanJY
	 * @date 2016年8月16日 上午9:22:44
	 * @param str
	 * @return
	 * @lastModifier
	 */
	public static boolean booleanValue(String str){
	    if(str!=null){
	        if(str.toLowerCase().equals("yes")||str.equals("1")||str.toLowerCase().equals("true")){
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * 返回字符串值
	 * @Description 
	 * @author TanJY
	 * @date 2016年8月16日 上午10:40:12
	 * @param obj
	 * @return
	 * @lastModifier
	 */
	public static String stringValue(Object obj){
	    if(obj!=null){
	        return String.valueOf(obj);
	    }else{
	        return "";
	    }
	}
	
	/**
	 * 返回字符串值,若为空则返回默认值
	 * @Description 
	 * @author TanJY
	 * @date 2016年8月16日 上午10:40:51
	 * @param obj
	 * @param def
	 * @return
	 * @lastModifier
	 */
    public static String stringValue(Object obj, String def){
        if (obj != null){
            return String.valueOf(obj);
        }else{
            return def;
        }
    }
    
    /**
     * 返回状态(NORMAL,FORBIDDEN)
     * @Description 
     * @author TanJY
     * @date 2016年9月5日 下午3:53:18
     * @param str
     * @return
     * @lastModifier
     */
    public static String getStatus(String str){
        if(!isEmpty(str)){
            if("是".equals(str)||"NORMAL".equals(str)||"未禁用".equals(str)){
                return "NORMAL";
            }
        }
        return "FORBIDDEN";
    }
    
}
