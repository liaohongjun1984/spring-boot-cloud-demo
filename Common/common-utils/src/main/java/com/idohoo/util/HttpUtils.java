package com.idohoo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





/**
 * HTTP请求处理帮助类
 *
 */
public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * 默认超时时间的GET请求
	 * @param url
	 * @param map
	 * @return
	 */
	public static HttpRes doHttpGet(String url){
		return doHttpGet(url,1000,1000);
	}
	/**
	 * 默认超时时间的POST请求
	 * @param url
	 * @param param
	 * @return
	 */
	public static HttpRes doHttpPost(String url, String param ) {
		return doHttpPost( url, param, 1000, 1000);
	}

	/**
	 * 发起HTTP GET请求
	 * @param url
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 */
	public static HttpRes doHttpGet(String url, int connectTimeout, int readTimeout){
		if(url.endsWith("/")){
			url = url.substring(0, url.length() - 1);
		}
		StringBuffer requestHeader = new StringBuffer();
		StringBuffer responseHeader = new StringBuffer();
		String       response = null;
		long         period  = 0;
		boolean      isExcep = false;

		Header[] reqHd = null;
		Header[] respHd = null;

		HttpClientParams params = new HttpClientParams();
		HttpClient client = new HttpClient();
		logger.info("GET URL: "+url+" 连接超时时间："+connectTimeout+" 读取数据超时时间："+readTimeout);

		HttpMethod method = new GetMethod(url);

		client.setParams(params);

		HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();

		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(connectTimeout);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(readTimeout);
		long start = 0;
		int code = 200;
		try {
			 start = System.currentTimeMillis();

			client.executeMethod(method);

			reqHd = method.getRequestHeaders();

			respHd = method.getResponseHeaders();

			InputStream resStream = method.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = "";
			while((resTemp = br.readLine()) != null){
	           resBuffer.append(resTemp);
			}
			response = resBuffer.toString();

			period = System.currentTimeMillis()-start;
			code = method.getStatusCode();
		}catch(SocketTimeoutException e){
			response= "网络连接超时 "+e.getMessage()+" 设置的超时时间为："+connectTimeout+"毫秒";
			period  = System.currentTimeMillis()-start;
			isExcep = false;
			//经常出现这种异常，需要做分布式的检查，同时需要把报警逻辑拆分到另一个进程做处理,现在简单做一个sleep操作
			//注意：sleep容易引发任务执行不完的异常
			try{
				Thread.sleep(5000);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}catch(ConnectTimeoutException e){
			response= "连接超时 "+e.getMessage()+" 设置的超时时间为："+connectTimeout+"毫秒";
			period  = System.currentTimeMillis()-start;
			isExcep = false;
		}catch(UnknownHostException e){
			response= "不能解析的域名："+e.getMessage();
			period  = System.currentTimeMillis()-start;
			isExcep = true;
			logger.info("URI exception : "+e.getMessage(), e);
		}catch(ConnectException e){
			response= "拒绝连接的域名 "+e.getMessage();
			period  = System.currentTimeMillis()-start;
			isExcep = true;
			logger.info("URI exception : "+e.getMessage(), e);
		}catch(Exception e) {
			response= e.getClass().getSimpleName()+" "+e.getMessage()+" 设置的超时时间为："+connectTimeout+"毫秒";
			period  = System.currentTimeMillis()-start;
			isExcep = true;
			logger.info("URI exception : "+e.getMessage(), e);
		}finally {
			method.releaseConnection();
		}

		if(reqHd!=null){
			for(int i=0;i<reqHd.length;i++){
				requestHeader.append(reqHd[i].toString()+"\n");
			}
		}else{
			requestHeader.append("");
		}

		if(respHd!=null){
			for(int i=0;i<respHd.length;i++){
				responseHeader.append(respHd[i].toString()+"\n");
			}
		}else{
			responseHeader.append("");
		}

		HttpRes res = new HttpRes();
		res.setURL(url);
		res.setReq_time(start);
		res.setResp_period(period);
		res.setExcep(isExcep);
		res.setRequestHeader(requestHeader.toString());
		res.setResponse(response);
		res.setResponseHeader(requestHeader.toString());
		res.setResponseCode(code);
		return res;
	}

	/**
	 * 发起HTTP post请求
	 * @param url
	 * @param param
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 */
	public static HttpRes doHttpPost(String url, String param, int connectTimeout, int readTimeout) {
		if(url.endsWith("/")){
			url = url.substring(0, url.length() - 1);
		}

		StringBuilder  sb = new StringBuilder();
		BufferedReader in = null;
		long           period  = 0;
		boolean        isExcep = false;

		String     respHd  = null;
		long       start   = 0;
		int code = 200;

		try {
			logger.info("get data url :"+url+"?"+param.toString()+" 连接超时时间："+connectTimeout+" 读取数据超时时间："+readTimeout);

		    start = (new Date()).getTime();
			URL postURL = new URL(url);
			HttpURLConnection con = (HttpURLConnection) postURL.openConnection();

			con.setUseCaches(false); // do not use cache
			con.setDoOutput(true); // use for output
			con.setDoInput(true); // use for Input
			con.setConnectTimeout(connectTimeout);
			con.setReadTimeout(readTimeout);
			con.setRequestMethod("POST"); // use the POST method to submit the

			PrintWriter out = new PrintWriter(con.getOutputStream());

			out.print(param.toString()); // send to server

			out.close(); // close outputstream

			in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

			String aline;
			while (null != (aline = in.readLine())) {
				sb.append(aline).append('\n');
			}
			code = con.getResponseCode();
			respHd = con.getResponseMessage();

			long end = (new Date()).getTime();
			period   = end-start;
		}catch(SocketTimeoutException e){
			sb.append("网络连接超时 "+e.getMessage()+" 设置的超时时间为："+connectTimeout+"毫秒");
			period  = System.currentTimeMillis()-start;
			isExcep = false;
			//经常出现这种异常，需要做分布式的检查，同时需要把报警逻辑拆分到另一个进程做处理,现在简单做一个sleep操作
			//注意：sleep容易引发任务执行不完的异常
			try{
				Thread.sleep(5000);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}catch(ConnectTimeoutException e){
			sb.append("连接超时 "+e.getMessage()+" 设置的超时时间为："+connectTimeout+"毫秒");
			period  = System.currentTimeMillis()-start;
			isExcep = false;
		}catch(UnknownHostException e){
			sb.append("不能解析的域名 "+e.getMessage());
			period  = System.currentTimeMillis()-start;
			isExcep = true;
			logger.info("URI exception : "+e.getMessage(), e);
		}catch(ConnectException e){
			sb.append("拒绝连接的域名 "+e.getMessage());
			period  = System.currentTimeMillis()-start;
			isExcep = true;
			logger.info("URI exception : "+e.getMessage(), e);
		}catch(Exception e) {
			sb.append(e.getClass().getSimpleName()+" "+e.getMessage()+" 设置的超时时间为："+connectTimeout+"毫秒");
			period  = connectTimeout + readTimeout;
			isExcep = true;
			logger.error(url, e);
		}finally {
			if (in != null) {
				try {
					in.close();
				}catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
				in = null;
			}
		}

		HttpRes res = new HttpRes();
		res.setURL(url+"?"+param);
		res.setReq_time(start);
		res.setResp_period(period);
		res.setExcep(isExcep);
		res.setRequestHeader("method:POST");
		res.setResponse(sb.toString());
		res.setResponseHeader(respHd);
		res.setResponseCode(code);
		return res;
	}

	public static BufferedReader doGetForReader(String strurl, int connectTimeout, int readTimeout) throws SocketTimeoutException {
		String host = null;
		return doGetForReader(strurl, host, connectTimeout, readTimeout);
	}

	public static BufferedReader doGetForReader(String strurl, String host, int connectTimeout, int readTimeout) throws SocketTimeoutException {
		try {
			URL url = new URL(strurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			if (host != null) {
				logger.info("Host:" + host);
				conn.setRequestProperty("Host", host);
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			return in;
		}
		catch (SocketTimeoutException e) {
			throw e;
		}
		catch (MalformedURLException e) {
			logger.info(host+e.getMessage(), e);
		}
		catch (IOException e) {
			logger.info(host+e.getMessage(), e);
		}
		finally {

		}
		return null;
	}
	public static String doGet(String url,Map<String,String> map, int connectTimeout, int readTimeout){
		StringBuilder param = new StringBuilder();
		if (map != null) {
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				if (param.length() > 0) {
					param.append('&');
				}

				Entry<String, String> entry = iterator.next();
				String key = entry.getKey();
				String value = entry.getValue();
				try {
					 if(value!=null){
						 param.append(key).append('=').append(URLEncoder.encode(value, "UTF-8"));
					 }
				}

				catch (UnsupportedEncodingException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}
		HttpClientParams params = new HttpClientParams();
		String response = null;
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpClient client = new HttpClient(connectionManager);
		logger.info("GET URL: "+url+"?"+param.toString());

		HttpMethod method = new GetMethod(url+"?"+param.toString());
		method.setRequestHeader("Connection", "close");
		client.setParams(params);

		HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();

		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(connectTimeout);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(readTimeout);

		try {
			client.executeMethod(method);

			InputStream resStream = method.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(resStream,"utf-8"));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = "";
			while((resTemp = br.readLine()) != null){
	           resBuffer.append(resTemp);
			}
			response = resBuffer.toString();
		}
		catch (URIException e) {
			logger.info(e.getMessage(), e);
			return null;
		}
		catch (IOException e) {
			logger.info(e.getMessage(), e);
		}
		finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return response;
	}
	/**
	 * 默认超时时间的请求
	 * @param url
	 * @param map
	 * @return
	 */
	public static String doGet(String url,Map<String,String> map){
		return doGet(url,map,1000,1000);
	}
	/**
	 * 超时时间的请求
	 * @param url
	 * @param map
	 * @param timeout
	 * @return
	 */
	public static String doGet(String url,Map<String,String> map,int timeout){
		return doGet(url,map,timeout,timeout);
	}
	
	public static String doPost(String url, Map<String, String> map) {
		StringBuilder param = new StringBuilder();
		if (map != null) {
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				if (param.length() > 0) {
					param.append('&');
				}

				Entry<String, String> entry = iterator.next();
				String key = entry.getKey();
				String value = entry.getValue();
				try {
					 if(value!=null){
						 param.append(key).append('=').append(URLEncoder.encode(value, "UTF-8"));
					 }
				}

				catch (UnsupportedEncodingException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		BufferedReader in = null;
		logger.debug("get data url :"+url+"?"+param.toString());
		try {
			URL postURL = new URL(url);
			HttpURLConnection con = (HttpURLConnection) postURL.openConnection();

			con.setUseCaches(false); // do not use cache
			con.setDoOutput(true); // use for output
			con.setDoInput(true); // use for Input
			con.setConnectTimeout(1000);
			con.setReadTimeout(1000);
			con.setRequestMethod("POST"); // use the POST method to submit the

			PrintWriter out = new PrintWriter(con.getOutputStream());

			out.print(param.toString()); // send to server

			out.close(); // close outputstream

			in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

			String aline;
			while (null != (aline = in.readLine())) {
				sb.append(aline).append('\n');
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			logger.error(url+"|"+e.getMessage(), e);
			return "";
		}
		finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (IOException e) {
					logger.info(e.getMessage(), e);
				}
				in = null;
			}
		}

		return sb.toString();
	}
	/**
	 * 设置超时时间
	 * @param url
	 * @param map
	 * @param timeout
	 * @return
	 */
	public static String doPost(String url, Map<String, String> map,int timeout) {
		StringBuilder param = new StringBuilder();
		if (map != null) {
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				if (param.length() > 0) {
					param.append('&');
				}

				Entry<String, String> entry = iterator.next();
				String key = entry.getKey();
				String value = entry.getValue();
				try {
				   if(value!=null){
					   param.append(key).append('=').append(URLEncoder.encode(value, "UTF-8"));
				   }
				}

				catch (UnsupportedEncodingException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		BufferedReader in = null;
		try {
			logger.info("get data url :"+url+"?"+param.toString());
			URL postURL = new URL(url);
			HttpURLConnection con = (HttpURLConnection) postURL.openConnection();

			con.setUseCaches(false); // do not use cache
			con.setDoOutput(true); // use for output
			con.setDoInput(true); // use for Input
			con.setConnectTimeout(timeout);
			con.setReadTimeout(timeout);
			con.setRequestMethod("POST"); // use the POST method to submit the

			PrintWriter out = new PrintWriter(con.getOutputStream());

			out.print(param.toString()); // send to server

			out.close(); // close outputstream

			in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

			String aline;
			while (null != (aline = in.readLine())) {
				sb.append(aline).append('\n');
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			logger.error(url+"|"+e.getMessage(), e);
			return "";
		}
		finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (IOException e) {
					logger.info(e.getMessage(), e);
				}
				in = null;
			}
		}

		return sb.toString();
	}
	
	public static String doPost(String url, Map<String, String> map,int connectionTimeout,int readTimeOut) {
		StringBuilder param = new StringBuilder();
		
		HttpPost httpPost = null;

			httpPost = new HttpPost(url);
			httpPost.setHeader("Content-type","application/json;charset=UTF-8");
			httpPost.setHeader("Accept", "application/json, text/javascript, */*");
			httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0");
		
		if (map != null) {
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				if (param.length() > 0) {
					param.append('&');
				}

				Entry<String, String> entry = iterator.next();
				String key = entry.getKey();
				String value = entry.getValue();
				try {
					if(value!=null){
						param.append(key).append('=').append(URLEncoder.encode(value, "UTF-8"));
					}
				}

				catch (UnsupportedEncodingException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		BufferedReader in = null;
		PrintWriter out   = null;
		try {
			logger.info("get data url :"+url+"?"+param.toString());
			URL postURL = new URL(url);
			HttpURLConnection con = (HttpURLConnection) postURL.openConnection();

			con.setUseCaches(false); // do not use cache
			con.setDoOutput(true); // use for output
			con.setDoInput(true); // use for Input
			con.setConnectTimeout(connectionTimeout);
			con.setReadTimeout(readTimeOut);
			con.setRequestMethod("POST"); // use the POST method to submit the

			out = new PrintWriter(con.getOutputStream());

			out.print(param.toString()); // send to server

			out.close(); // close outputstream

			in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

			String aline;
			while (null != (aline = in.readLine())) {
				sb.append(aline).append('\n');
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			logger.error(url+"|"+e.getMessage(), e);
			return "";
		}
		finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (IOException e) {
					logger.info(e.getMessage(), e);
				}
				in = null;
			}
			if(out!=null){
				try{out.close();}catch(Exception e){e.printStackTrace();};
			}
		}

		return sb.toString();
	}
	
	/** 
     * 模拟请求 
     *  
     * @param url       资源地址 
     * @param map   参数列表 
     * @param encoding  编码 
     * @return 
     * @throws ParseException 
     * @throws IOException 
     */  
    public static String sendPostByForm(String url, Map<String,String> map) throws ParseException, IOException{  
        
    	String body = "";  
  
        //创建httpclient对象  
        CloseableHttpClient client = HttpClients.createDefault();  
        //创建post方式请求对象  
        HttpPost httpPost = new HttpPost(url);  
          
        //装填参数  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        if(map!=null){  
            for (Entry<String, String> entry : map.entrySet()) {  
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
            }  
        }  
        //设置参数到请求对象中  
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
  
        logger.info("请求地址："+url);  
        logger.info("请求参数："+nvps.toString());  
          
        //设置header信息  
        //指定报文头【Content-type】、【User-Agent】  
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
          
        //执行请求操作，并拿到结果（同步阻塞）  
        CloseableHttpResponse response = client.execute(httpPost);  
        //获取结果实体  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            //按指定编码转换结果实体为String类型  
            body = EntityUtils.toString(entity, "UTF-8");  
        }  
        EntityUtils.consume(entity);  
        //释放链接  
        response.close();  
        return body;  
    }  
    
	/**
	 * POST方式提交Json数据
	 * @param url
	 *            待请求的URL
	 * @param jsonDataStr
	 *            要提交的Json字符串数据
	 * @return 响应结果
	 */
	public static String doPostJson(String url, String jsonDataStr) {
		String response = "";

		HttpPost httpPost = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
			org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();

			httpPost = new HttpPost(url);
			httpPost.setHeader("Content-type","application/json;charset=UTF-8");
			httpPost.setHeader("Accept", "application/json, text/javascript, */*");
			httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0");

			// 将XML数据放入httpPost中
			if(StringUtils.isNotEmpty(jsonDataStr))
			{
				StringEntity entity = new StringEntity(jsonDataStr, "UTF-8");
				httpPost.setEntity(entity);
			}

			// 执行请求
			HttpResponse res = httpClient.execute(httpPost);
			int statusCode = res.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				response = EntityUtils.toString(res.getEntity(), "UTF-8");
			} else {
				System.err.println("响应状态码 = " + statusCode);
				System.err.println("响应内容：\n" + EntityUtils.toString(res.getEntity(), "UTF-8"));
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题:" + e.getMessage(), e);
		} catch (IOException e) {
			logger.error("发生网络异常" + e.getMessage(), e);
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
				httpPost = null;
			}
		}
		return response;
	}

}
