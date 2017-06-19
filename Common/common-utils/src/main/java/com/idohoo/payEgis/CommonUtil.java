package com.idohoo.payEgis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Encoder;

public class CommonUtil {
	@SuppressWarnings("deprecation")
	public static JSONObject postSSL(Map<String, String> params, String appId, String appKey, String url) {
		JSONObject json = new JSONObject();
		for (int i = 0; i < 1; i++) {
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				// ///add by 解决出现错误(peer not authenticated) lanyangli start
				SSLContext cxt = SSLContext.getInstance("TLS");
				X509TrustManager x509 = new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					}

					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					}
				};
				cxt.init(null, new TrustManager[] { x509 }, null);
				SSLSocketFactory ssl = new SSLSocketFactory(cxt);
				ssl.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				ClientConnectionManager ccm = httpclient.getConnectionManager();
				SchemeRegistry sr = ccm.getSchemeRegistry();
				sr.register(new Scheme("https", 443, ssl));
				// // add by lanyangli end
				httpclient = new DefaultHttpClient(ccm, httpclient.getParams());
				String requestTimeString = String.valueOf(System.currentTimeMillis());
				HttpPost httppost = new HttpPost(url);
				httppost.setHeader("x-hmac-auth-date", requestTimeString);
				httppost.setHeader("x-hmac-auth-signature", Sig.getSigNewMap(appKey, requestTimeString, params, appId));
				params.remove("x-hmac-auth-date");
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator<?> iterator = params.entrySet().iterator();
				while (iterator.hasNext()) {
					@SuppressWarnings("unchecked")
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
				httppost.setEntity(entity);
				HttpResponse response;
				String str = "";
				try {
					response = httpclient.execute(httppost);
					HttpEntity httpentity = response.getEntity();
					if (httpentity != null) {
						str = EntityUtils.toString(httpentity, "UTF-8");
						json.put("result", str);
					}
				} catch (ClientProtocolException e) {
					json.put("success", false);
					json.put("returnMsg", " 请求失败");
					e.printStackTrace();
				} catch (IOException e) {
					json.put("success", false);
					json.put("returnMsg", "请求失败");
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	public static JSONObject post(Map<String, String> params, String appId, String appKey, String url) {
		JSONObject json = new JSONObject();
		for (int i = 0; i < 1; i++) {
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();

				String requestTimeString = String.valueOf(System.currentTimeMillis());
				HttpPost httppost = new HttpPost(url);
				httppost.setHeader("x-hmac-auth-date", requestTimeString);
				httppost.setHeader("x-hmac-auth-signature", Sig.getSigNewMap(appKey, requestTimeString, params, appId));
				params.remove("x-hmac-auth-date");
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator<?> iterator = params.entrySet().iterator();
				while (iterator.hasNext()) {
					@SuppressWarnings("unchecked")
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
				httppost.setEntity(entity);
				HttpResponse response;
				String str = "";
				try {
					response = httpclient.execute(httppost);
					HttpEntity httpentity = response.getEntity();
					if (httpentity != null) {
						str = EntityUtils.toString(httpentity, "UTF-8");
						json.put("result", str);
					}
				} catch (ClientProtocolException e) {
					json.put("success", false);
					json.put("returnMsg", " 请求失败");
					e.printStackTrace();
				} catch (IOException e) {
					json.put("success", false);
					json.put("returnMsg", "请求失败");
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	/**
	 * @param path
	 *            图片路径
	 * @return
	 * @Descriptionmap 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @author temdy
	 * @Date 2015-01-26
	 */
	public static String imageToBase64(String path) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;
		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(path);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
}
