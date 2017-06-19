package com.idohoo.payEgis;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Sig {
	/*
	 * 生成签名
	 */
	public static String getSigNewMap(String appKey, String requestTimeString, Map<String,String> parameter, String appId) throws UnsupportedEncodingException{

		String kvString = "";
		parameter.put("x-hmac-auth-date", requestTimeString);
		Set<String> listKeys = parameter.keySet();
		int length = listKeys.size();
		Iterator<String> it = listKeys.iterator();
		List<String> list = new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next());
		}
		Collections.sort(list);
		for(int i=0; i<length; i++){
			String key = list.get(i);
			if(i == length -1){
				kvString += key + "=" + parameter.get(key);
			}else{
				kvString += key + "=" + parameter.get(key) + "&";
			}
		}
		String kvStringE = URLEncoder.encode(kvString,"UTF-8");
		kvString = kvStringE.replace("*", "%2A").replace("+", "%20");
		String firstStep = kvString;

		String secretAuthKey = appKey + "&";

		String sig = "";
		byte[] encryption;
		try {
			encryption = HMACSHA1.getSignature(firstStep, secretAuthKey);
			sig = encodeBase64WithoutLinefeed(encryption);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return appId + ":" + sig;
	}
	protected static String encodeBase64WithoutLinefeed(byte[] result) throws Exception {
		return Base64.encodeBase64String(result).trim();
	}
}
