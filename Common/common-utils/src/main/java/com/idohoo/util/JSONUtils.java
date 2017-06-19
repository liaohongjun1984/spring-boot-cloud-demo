package com.idohoo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * json操作常用方法类.
 * <p>
 *
 * @author henser 2015/3/7, required JDK 1.6
 */
public class JSONUtils {

	/**
	 * String 转json
	 *
	 * @param str
	 * @return
	 */
	public static JSONArray stringToJson(String str) {
		//String转jsonArray
		JSONArray jsonArray = JSONArray.fromObject(str);
		return jsonArray;
	}

	/**
	 * string 转 list
	 *
	 * @param str
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <T> List<T> jsonToList(String str,Class<T> t) {

		List<T> list = null;

		if(StringUtil.isNotEmpty(str)){

			list = new ArrayList<T>();
			JSONArray jsonArray = stringToJson(str);
			list = JSONArray.toList(jsonArray,t);

		}

		return list;

	}


	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <T> List<T> jsonToCollection(String str,Class<T> t) {

		List<T> list = null;

		if(StringUtil.isNotEmpty(str)){

			list = new ArrayList<T>();
			JSONArray jsonArray = stringToJson(str);
			list = (List)JSONArray.toCollection(jsonArray, t);

		}

		return list;

	}

	/**
	 * string 转 list
	 *
	 * @param str
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <T> List<T> jsonToList(String str,Class<T> t,Map classMap) {

		List<T> list = null;

		if(StringUtil.isNotEmpty(str)){

			list = new ArrayList<T>();
			JSONArray jsonArray = stringToJson(str);
			list = JSONArray.toList(jsonArray,t,classMap);

		}

		return list;

	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <T> T toObject(String str,Class<T> t,Map classMap){
	    str = "["+str+"]";
	    return jsonToList(str,t,classMap).get(0);
	}

	public static String toJsonString(Object object)
	{
		return net.sf.json.JSONSerializer.toJSON(object).toString();
	}

	public static String listToString(Object object)
	{
		return JSONArray.fromObject(object).toString();

	}

	public static <T> T toObject(String json,Class<T> clazz)
	{
		List<T> list = jsonToList("["+json+"]",clazz);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取json的value值数组
	 * @param json
	 * @return strs
	 */
	public static String[] getJsonValue(String json){

		JSONArray jsonArray = stringToJson(json);

		String[] strs = new String[jsonArray.size()];

		for(int i = 0; i < jsonArray.size(); i++){
			JSONObject ob = (JSONObject) jsonArray.get(i);//得到json对象
			String name= ob.getString("key");
			strs[i] = name;
		}

		return strs;
	}

	/**
	 * 获取json的key值数组
	 * @param json
	 * @return strs
	 */
	public static String[] getJsonKey(String json){

		JSONArray jsonArray = stringToJson(json);

		String[] strs = new String[jsonArray.size()];

		for(int i = 0; i < jsonArray.size(); i++){
			JSONObject ob = (JSONObject) jsonArray.get(i);//得到json对象
			Iterator itt = ob.keys();
			while (itt.hasNext()){
				String key = itt.next().toString();
				strs[i] = key;
			}
		}

		return strs;
	}

	/**
	 * 获取json的value值列表
	 * @param json
	 * @return strs
	 */
	public static List<String> getJsonValueList(String json){

		JSONArray jsonArray = stringToJson(json);

		List<String> strs = new ArrayList<String>();

		for(int i = 0; i < jsonArray.size(); i++){
			JSONObject ob = (JSONObject) jsonArray.get(i);//得到json对象
			String name= ob.getString("key");
			strs.add(name);
		}

		return strs;
	}

	/**
	 * 获取json的key值列表
	 * @param json
	 * @return strs
	 */
	public static List<String> getJsonKeyList(String json){

		JSONArray jsonArray = stringToJson(json);

		List<String> strs = new ArrayList<String>();

		for(int i = 0; i < jsonArray.size(); i++){
			JSONObject ob = (JSONObject) jsonArray.get(i);//得到json对象
			Iterator itt = ob.keys();
			while (itt.hasNext()){
				String key = itt.next().toString();
				strs.add(key);
			}
		}

		return strs;
	}

	/**
	 * 获取json对象
	 * @param jsonArray
	 * @param int
	 * @return JSONObject
	 */
	public JSONObject getJSONObject(JSONArray jsonArray,int i){
		JSONObject ob = (JSONObject) jsonArray.get(i);//得到json对象
		return ob;
	}

	/**
	* @Title: JsonStrTrim
	* @author : jsw
	* @date : 2012-12-7
	* @time : 上午09:19:18
	* @Description: 传入string 类型的 json字符串 去处字符串中的属性值的空格
	* @param jsonStr
	* @return
	* @exception:(异常说明)
	*/
	public JSONObject JsonStrTrim(String jsonStr){

	    JSONObject reagobj = JSONObject.fromObject(jsonStr);
	    // 取出 jsonObject 中的字段的值的空格
	    Iterator itt = reagobj.keys();

	    while (itt.hasNext()) {

	        String key = itt.next().toString();
	        String value = reagobj.getString(key);

	        if(value == null){
	            continue ;
	        }else if("".equals(value.trim())){
	            continue ;
	        }else{
	            reagobj.put(key, value.trim());
	        }
	    }
	    return reagobj;
	}

	/**
	* @Title: JsonStrTrim
	* @author : jsw
	* @date : 2012-12-7
	* @time : 上午09:21:48
	* @Description: 传入jsonObject 去除当中的空格
	* @param jsonStr
	* @return
	* @exception:(异常说明)
	*/
	public JSONObject JsonStrTrim(JSONObject jsonStr){

	    JSONObject reagobj = jsonStr ;
	    // 取出 jsonObject 中的字段的值的空格
	    Iterator itt = reagobj.keys();

	    while (itt.hasNext()) {

	        String key = itt.next().toString();
	        String value = reagobj.getString(key);

	        if(value == null){
	            continue ;
	        }else if("".equals(value.trim())){
	            continue ;
	        }else{
	            reagobj.put(key, value.trim());
	        }
	    }
	    return reagobj;
	}


	/**
	* @Title: JsonStrTrim
	* @author : jsw
	* @date : 2012-12-7
	* @time : 上午11:48:59
	* @Description: 将 jsonarry 的jsonObject 中的value值去处前后空格
	* @param arr
	* @return
	* @exception:(异常说明)
	*/
	public JSONArray JsonStrTrim(JSONArray arr){

	    if( arr != null && arr.size() > 0){
	        for (int i = 0; i < arr.size(); i++) {

	            JSONObject obj = (JSONObject) arr.get(i);
	            // 取出 jsonObject 中的字段的值的空格
	            Iterator itt = obj.keys();

	            while (itt.hasNext()) {

	                String key = itt.next().toString();
	                String value = obj.getString(key);

	                if(value == null){
	                    continue ;
	                }else if("".equals(value.trim())){
	                    continue ;
	                }else{
	                    obj.put(key, value.trim());
	                }
	            }
	            arr.set(i,  obj );
	        }
	    }
	    return arr;
	}

	/**
	 * 把json字符串转换成MAP对象
	 * @param jsonStr:字符串
	 * @return:返回MAP对象
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	public static void main(String[] args){

		String typeName = "[{'111':'大'}]";

		JSONArray jsonArray = stringToJson(typeName);

		for(int i = 0; i < jsonArray.size(); i++){
			JSONObject ob = (JSONObject) jsonArray.get(i);//得到json对象
			Iterator itt = ob.keys();
			while (itt.hasNext()){
				String key = itt.next().toString();
				System.out.println("key:"+key);
			}
		}

	}
}
