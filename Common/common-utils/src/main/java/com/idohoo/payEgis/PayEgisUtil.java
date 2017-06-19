package com.idohoo.payEgis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

public class PayEgisUtil {
	
	public static Log log = LogFactory.getLog(PayEgisUtil.class);
	public static String payEgis_appID="2a1ocm5";
	public static String payEgis_appkey="674fb7e4-3f1c-480d-b86d-47b0071a1d77";
	public static String payEgis_URL="https://pws.payegis.com.cn/id_creditcardapi/idcard/simple_query";
	/**
	 * 验证身份信息
	 * @param idCard
	 * @param name
	 * @return
	 * @throws DocumentException
	 */
	public static Map<String, String> verifyIdentity(String idCard,String name) throws DocumentException{
		Map<String, String> param = new HashMap<String, String>();
		Map<String, String> msgMap = new HashMap<String, String>();
		param.put("idCard", idCard);
		param.put("name", name);
		JSONObject jsonObject = CommonUtil.post(param,payEgis_appID, payEgis_appkey, payEgis_URL);
		log.info("-----------通付盾的返回结果:"+jsonObject.toString());
		msgMap = getMessageMap(jsonObject.getString("result"));
		return switchStatCode(msgMap);
	}
	
	/**
	 * 将返回结果转换成map
	 * @param resultXML
	 * @return
	 * @throws DocumentException
	 */
	private static Map<String,String> getMessageMap(String resultXML) throws DocumentException{
		Map<String,String> messageMap = new HashMap<String,String>();
		if(resultXML.indexOf("升级维护")>0 || resultXML.indexOf("网站升级维护中")>0){
			messageMap.put("statCode","0");//获取身份证信息失败
			log.info("通付盾升级维护中..");
			return messageMap;
		}
		JSONObject json = JSONObject.fromObject(resultXML);
		Set<String> set = json.keySet();
		Iterator<String> it = set.iterator();
		String key = "";
		while(it.hasNext()){
			key = it.next();
			messageMap.put(key, json.getString(key));
		}
		return messageMap;
	}
	
	/**
	 * 转换成页面对应的标志码
	 * @param map
	 * @return
	 */
	private static Map<String,String> switchStatCode(Map<String,String> map){
		String personInfo = map.get("name")+map.get("idCard");
		String statCode = map.get("statCode");
		if("1100".equals(statCode)){
			map.put("statCode", "2");//姓名与身份证一致
			return map;
		}else if("1101".equals(statCode)){
			map.put("statCode", "8");
			log.info(personInfo+"结果不一致：身份证号码不一致");
			return map;
		}else if("1102".equals(statCode)){
			map.put("statCode", "9");
			log.info(personInfo+"结果不一致：身份证姓名不一致");
			return map;
		}else if("1103".equals(statCode)){
			map.put("statCode", "5");//库中无此号，请到户籍所在地进行核实
			log.info(personInfo+"库中无此号，请到户籍所在地进行核实");
			return map;
		}else if("1903".equals(statCode)){
			map.put("statCode", "10");//无效身份证号码
			log.info(personInfo+"无效身份证号码");
			return map;
		}else if("1904".equals(statCode)){
			map.put("statCode", "11");//姓名为空或长度不正确
			log.info(personInfo+"姓名为空或长度不正确");
			return map;
		}else if("1902".equals(statCode)){
			map.put("statCode", "12");//身份证号为空或长度不正确
			log.info(personInfo+"身份证号为空或者长度不正确");
			return map;
		}else{
			map.put("statCode","4");//联网获取身份证信息失败
			if("4001".equals(statCode)){
				log.info(personInfo+"参数不能为空");
			}else if("4002".equals(statCode)){
				log.info(personInfo+"签名校验失败");
			}else if("4000".equals(statCode)){
				log.info(personInfo+"余额不足");
			}
		}
		return map;
	}
}
