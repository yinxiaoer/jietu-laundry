package com.jietu.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * 这个类包含使用httpclient发送请求的所有方法 get post
 * 
 * @author admin
 * 
 */
public class HttpClientUtil {

	/**
	 * 使用httpclient向传入url发送请求并返回json的字符串
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 返回的json字符串
	 */
	public static String httpGet(String url) {
		// 创建http请求的执行器
		HttpClient client = HttpClientBuilder.create().build();
		// 创建get请求
		HttpGet httpGet = new HttpGet(url);
		String jsonStr = null;
		try {
			// 执行请求并获取响应
			HttpResponse response = client.execute(httpGet);
			// 200 404 500 http的状态码
			int statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				// 拿到返回的json字符串
				HttpEntity entity = response.getEntity();
				jsonStr = EntityUtils.toString(entity);
				return jsonStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送post请求到url地址，并且传递参数
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpPost(String url, String params) {
		// 创建执行器
		HttpClient client = HttpClientBuilder.create().build();
		// 创建执行的请求
		HttpPost httpPost = new HttpPost(url);
		try {
			// 设置参数
			httpPost.setEntity(new StringEntity(params, "utf-8"));
			// 执行请求，并获取响应
			HttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				// 转换响应内容为json
				return EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
