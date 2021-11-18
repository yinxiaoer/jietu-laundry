package com.jietu.app.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jietu.common.service.RedisService;
import com.jietu.common.utils.HttpClientUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信工具类
 * @author: 印修河
 * @date: 2019/2/28 17:32
 */
public class WeixinUtil {

	private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

	/**
	 * 获取AccessToken
	 * @param appid
	 * @param appsecret
	 * @return
	 * @throws Exception
	 */
	public static String getAccessToken(String appid, String appsecret) {
		logger.info("=====开始获取微信 accessToken=====");
		long startTime = System.currentTimeMillis();
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		// 把常量的url替换参数
		url = url.replace("APPID", appid).replace("APPSECRET", appsecret);
		logger.info("请求url:{}", url);
		// 发送请求
		String jsonStr = com.jietu.common.utils.HttpClientUtil.httpGet(url);
		logger.info("===获取微信accessToken耗时：{} ms===", System.currentTimeMillis()-startTime);
		logger.info("===响应结果：{}===", jsonStr);
		if (jsonStr != null) {
			// 解析json得到accessToken
			JSONObject json = JSON.parseObject(jsonStr);
			return json.getString("access_token");
		}
		return null;
	}

	/**
	 * 小程序用户鉴权
	 * @param appid
	 * @param appsecret
	 * @param code
	 * @return
	 */
	public static JSONObject authorization(String appid, String appsecret, String code){
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
		url = url.replace("APPID", appid)
				.replace("SECRET", appsecret)
				.replace("JSCODE", code);
		logger.info("微信授权请求：{}", url);
		String httpResult = HttpClientUtil.httpGet(url);
		logger.info("微信授权响应：{}", httpResult);
		if(StringUtils.isBlank(httpResult)){
			throw new MyException(ErrorCode.FAIL, "请求微信服务器返回为空");
		}
		JSONObject json = JSONObject.parseObject(httpResult);
		String errcode = json.getString("errcode");
		if(StringUtils.isNotBlank(errcode) && !"0".equals(errcode)){
			String msg = "授权失败";
			if("40029".equals(errcode)){
				msg = "code 无效";
			} else if("45011".equals(errcode)){
				msg = "频率限制";
			}
			throw new MyException(ErrorCode.FAIL, msg);
		}
		return json;
	}

	/**
	 * 获取accessToken缓存值
	 * @return
	 */
	public static String getAccessTokenCache(){
		return (String) SpringUtils.getBean(RedisService.class).get(Constants.WEIXIN_ACCESSTOKEN_CACHE_KEY);
	}

	/**
	 * 解密微信数据
	 * @param appId
	 * @param encryptedData
	 * @param sessionKey
	 * @param iv
	 * @return
	 */
	public static String decrypt(String appId, String encryptedData, String sessionKey, String iv){
		String result = "";
		try {
			AES aes = new AES();
			byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
			if(null != resultByte && resultByte.length > 0){
				result = new String(WxPKCS7Encoder.decode(resultByte));
				JSONObject jsonObject = JSONObject.parseObject(result);
				String decryptAppid = jsonObject.getJSONObject("watermark").getString("appid");
				if(!appId.equals(decryptAppid)){
					result = "";
				}
			}
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}
}
