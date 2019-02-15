package com.o2o.util.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.UserAccessToken;
import com.o2o.dto.WechatUser;
import com.o2o.entity.PersonInfo;

/**
 * 微信工具类
 * 
 * @author ZhouCC
 *
 */
public class WechatUtil {

	private static Logger log = LoggerFactory.getLogger(WechatUtil.class);
	
	/**
	 * 将WechatUser里的信息转换成PersonInfo的信息并返回PersonInfo实体类
	 * 
	 * @param wechatUser
	 * @return
	 */
	public static PersonInfo getPersonInfoFromRequest(WechatUser wechatUser) {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setName(wechatUser.getNickName());
		personInfo.setGender(wechatUser.getSex()+"");
		personInfo.setProfileImg(wechatUser.getHeadimgurl());
		personInfo.setEnableStatus(1);
		return personInfo;
	}
	
	
	/**
	 * 获取WechatUser实体类
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static WechatUser getUserInfo(String accessToken, String openId) {
		WechatUser wechatUser = new WechatUser();
		
		// 根据传入的accessToken以及openId拼接出访问微信定义的端口并获取用户信息的URL
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
				+ "&lang=zh_CN";
		
		// 访问该URL获取用户信息json 字符串
		String userStr = httpsRequest(url, "GET", null);
		log.debug("user info :" + userStr);
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// 将json字符串转换成相应对象
			wechatUser = objectMapper.readValue(userStr, WechatUser.class);
		}  catch (JsonParseException e) {
			log.error("获取用户信息失败: " + e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			log.error("获取用户信息失败: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("获取用户信息失败: " + e.getMessage());
			e.printStackTrace();
		}
		if (wechatUser == null) {
			log.error("获取用户信息失败。");
			return null;
		}
		return wechatUser;
	}

	public static UserAccessToken geUserAccessToken(String code) {
		// 测试号信息里的appId
		String appId = "wx7f94a7e302b1b44c";
		log.debug("appId:" + appId);
		// 测试号信息里的appsecret
		String appsecret = "dd2a4c3e347017cf7fd51f4dfd839935";
		log.debug("secret:" + appsecret);

		// 根据传入的code,拼接出访问微信定义好的接口的URL
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret
				+ "&code=" + code + "&grant_type=authorization_code";
		
		// 向相应URL发送请求获取token json字符串
		String tokenStr = httpsRequest(url,"GET",null);
		log.debug("userAccessToken:" + tokenStr);
		
		UserAccessToken token = new UserAccessToken();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			token = objectMapper.readValue(tokenStr, UserAccessToken.class);
		} catch (JsonParseException e) {
			log.error("获取用户accessToken失败: " + e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			log.error("获取用户accessToken失败: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("获取用户accessToken失败: " + e.getMessage());
			e.printStackTrace();
		}
		if (token == null) {
			log.error("获取用户accessToken失败。");
			return null;
		}
		return token;
	}
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return json字符串
	 */
	private static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
			
			URL url = new URL(requestUrl);
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
			httpsURLConnection.setSSLSocketFactory(sslSocketFactory);
			
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setUseCaches(true);
			
			// 设置请求方式（GET/POST）
			httpsURLConnection.setRequestMethod(requestMethod);
			
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpsURLConnection.connect();
			}
			
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpsURLConnection.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpsURLConnection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String str = null;
			while((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpsURLConnection.disconnect();
			log.debug("https buffer:" + buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return buffer.toString();
	}
}
