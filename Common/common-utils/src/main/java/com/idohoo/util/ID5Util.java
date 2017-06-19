package com.idohoo.util;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import cn.id5.gboss.businesses.validator.service.app.QueryValidatorServices;
import cn.id5.gboss.businesses.validator.service.app.QueryValidatorServicesProxy;


public class ID5Util {
	/**
	 * message中的status
	 */
	public static final String ID5_MESSAGE_STATUS="status";
	/**
	 * message中的value
	 */
	public static final String ID5_MESSAGE_VALUE="value";
	/**
	 * 唯一标识
	 */
	public static final String ID5_NO="no";
	/**
	 * 姓名
	 */
	public static final String ID5_NAME="name";
	/**
	 * 比对状态 ：
	 * 1,服务结果:库中无此号，请到户籍所在地进行核实
	 * 2,不一致
	 * 3，一致
	 */
	public static final String ID5_COMPSTATUS="compStatus";
	/**
	 * 比对结果，与比对状态对应
	 * 
	 */
	public static final String ID5_COMPRESULT="compResult";
	/**
	 * 身份证号
	 */
	public static final String ID5_IDENTITYCARD="identitycard";
	/**
	 * policeCheckInfos中的policeCheckInfo
	 */
	public static final String ID5_POLICECHECKINFO="policeCheckInfo";
	/**
	 * policeCheckInfo中的message：表示是否查询成功
	 */
	public static final String ID5_MESSAGE="message";
	/**
	 * policeCheckInfo中的属性id号
	 */
	public static final String ID5_POLICECHECKINFO_ID="id";
	/**
	 * policeCheckInfo中的属性name
	 */
	public static final String ID5_POLICECHECKINFO_NAME="name";
	
	
	private static  String username="gzjkwebs";//用户名 
	
	private static  String password="gzjkwebs_$X2iaSxC";//密码
	
	private static  String datasource = "1A020201";//数据类型 
	
	private static  String url = "http://gboss.id5.cn/services/QueryValidatorServices";
	
	private static  String keystore = "keystore";//证书路径
	
	private static  String key="12345678";//DES加密密钥
	
	private static QueryValidatorServices service;
	
	static{
		//获得WebServices的代理对象
		QueryValidatorServicesProxy proxy = new QueryValidatorServicesProxy();
		proxy.setEndpoint(url);
		service = proxy.getQueryValidatorServices();
		System.setProperty("javax.net.ssl.trustStore",keystore); 
	}
	/**
	 * 单条查询
	 * @param params 每个参数据用,（英文下的逗号）分格，姓名,身份证号，如"张三,510104197509202629"
	 * @return 
	 */
	public static String querySingle(String params){
		//FIXME 
		//正式发布时把下面注释放开，把最下面的代码删除
		try {
			String result=service.queryBatch(Des2.encode(key,username), Des2.encode(key,password), Des2.encode(key,datasource),Des2.encode(key,params));
			return Des2.decodeValue(key,result);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 批量查询
	 * @param params  每条数据之间用;（英文下的分号）分格，如"王茜,150202198302101248;吴晨晨,36252519821201061x;"
	 * @return 
	 */
	public static String queryBatch(String params){
		try {
			String result=service.querySingle(Des2.encode(key,username), Des2.encode(key,password), Des2.encode(key,datasource),Des2.encode(key,params));
			return Des2.decodeValue(key,result);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static Element getElementOfRoot(String xml,String node) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml);
		Element rootElt = doc.getRootElement(); // 获取根节点
		Element element=(Element) rootElt.selectSingleNode(node);
		return element;
	}
	
	@SuppressWarnings("unchecked")
	private static List<Element> getChildsOfElement(Element parent,String node) throws DocumentException{
		return parent.selectNodes(node);
	}
	/**
	 * 获取返回的节点message中的status和value的值
	 * @param xml
	 * @return status和value的map,status: 0,表示处理成功
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getRootMessageMap(String xml) throws DocumentException{
		Map<String,String> map = new HashMap<String,String>();
		List<Element> elements = getElementOfRoot(xml,"message").elements();
        for (Element subElement : elements) {
        	map.put(subElement.getName(), subElement.getTextTrim());
        }
		return map;
	}
	/**
	 * 获取PoliceCheckInfos的信息
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getPoliceCheckInfos(String xml) throws DocumentException{
		List<Map<String,Object>> policeCheckInfos=new ArrayList<Map<String,Object>>();
		Element parent = getElementOfRoot(xml,"policeCheckInfos");
		List<Element> policeCheckInfo=getChildsOfElement(parent,"policeCheckInfo");
		for (Element element : policeCheckInfo) {
			Map<String,Object> policeCheckInfoMap = new HashMap<String,Object>();
			Map<String,Object> map = new HashMap<String,Object>();
			if (element.attributeCount()>0) {
				List<Attribute> attributes=element.attributes();
				for (Attribute attribute : attributes) {
					map.put(attribute.getName(), attribute.getText());
				}
			}
			policeCheckInfoMap.put(element.getName(), map);
			
			List<Element> elements=element.elements();
			for (Element element2 : elements) {
				if(element2.isTextOnly()){
					policeCheckInfoMap.put(element2.getName(), element2.getTextTrim());
				}else{
					Map<String,Object> info=new HashMap<String,Object>();
					List<Element> messages=element2.elements();
					for (Element element3 : messages) {
						info.put(element3.getName(), element3.getTextTrim());
					}
					policeCheckInfoMap.put(element2.getName(),info);
				}
			}
			policeCheckInfos.add(policeCheckInfoMap);
		}
		return policeCheckInfos;
	}
	
	public static Map<String,Object> getPoliceCheckInfo(String xml) throws DocumentException{
		return getPoliceCheckInfos(xml).get(0);
	}
	@SuppressWarnings("unchecked")
	public static Map<String,String> getPoliceCheckInfoMessage(String xml) throws DocumentException{
		return (Map<String,String>)(getPoliceCheckInfos(xml).get(0).get(ID5_MESSAGE));
	}
	public static void main(String[] args) throws UnsupportedEncodingException, DocumentException{
		String resultXML=ID5Util.querySingle("王茜,150202198302101248");
		List<Map<String,Object>> list=getPoliceCheckInfos(resultXML);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
		try {
			System.out.println(getRootMessageMap(resultXML).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
