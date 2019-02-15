package com.o2o.web.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.o2o.dto.UserAccessToken;
import com.o2o.dto.WechatAuthExecution;
import com.o2o.dto.WechatUser;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.WechatAuth;
import com.o2o.enums.WechatAuthStateEnum;
import com.o2o.service.PersonInfoService;
import com.o2o.service.WechatAuthService;
import com.o2o.util.wechat.WechatUtil;

@Controller
@RequestMapping("wechatlogin")
public class WechatLoginController {

	private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);
	
	private static final String FRONTEND = "1";
	private static final String SHOPEND = "2";
	
	@Autowired
	private PersonInfoService personInfoService;
	
	@Autowired
	private WechatAuthService wechatAuthService;

	/**
	 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7f94a7e302b1b44c&redirect_uri=http://47.92.71.111/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
	 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
	 * 
	 */
	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		log.debug("weixin login get...");

		// 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
		String code = request.getParameter("code");
		log.debug("weixin login code:" + code);

		// 这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
		String roleType = request.getParameter("state");

		WechatUser wechatUser = null;
		String openId = null;
		WechatAuth wechatAuth = null;
		if (null != code) {
			UserAccessToken token;
			try {
				// 通过code获取access_token
				token = WechatUtil.geUserAccessToken(code);
				log.debug("weixin login token:" + token.toString());
				
				// 通过token获取accessToken
				String accessToken = token.getAccessToken();
				// 通过token获取openId
				openId = token.getOpenId();
				
				// 通过access_token和openId获取用户昵称等信息
				wechatUser = WechatUtil.getUserInfo(accessToken, openId);
				log.debug("weixin login user:" + wechatUser.toString());
				
				request.getSession().setAttribute("openId", openId);
				
				wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
			} catch (Exception e) {
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                e.printStackTrace();
			}
		}
		/** 
		 * 已完成
		 * ======todo begin======
		 * 前面咱们获取到openId后，可以通过它去数据库判断该微信帐号是否在我们网站里有对应的帐号了，
		 * 没有的话这里可以自动创建上，直接实现微信与咱们网站的无缝对接。
		 * ======todo end======
		 */
		// 若微信帐号为空则需要注册微信帐号，同时注册用户信息
		if (wechatAuth == null) {
			PersonInfo personInfo = WechatUtil.getPersonInfoFromRequest(wechatUser);
			wechatAuth = new WechatAuth();
			wechatAuth.setOpenId(openId);
			
			if (FRONTEND.equals(roleType)) {
				personInfo.setUserType(1);
			}
			if (SHOPEND.equals(roleType)){
				personInfo.setUserType(2);
			}
			
			wechatAuth.setPersonInfo(personInfo);
			WechatAuthExecution wechatAuthExecution = wechatAuthService.register(wechatAuth);
			if (wechatAuthExecution.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
				return null;
			}else {
				personInfo = personInfoService.getPersonInfoById(wechatAuth.getPersonInfo().getUserId());
				request.getSession().setAttribute("user", personInfo);
			}
		}else {
			request.getSession().setAttribute("user", wechatAuth.getPersonInfo());
		}
		
		// 若用户点击的是前端展示系统按钮则进入前端展示系统
		if (SHOPEND.equals(roleType)) {
			return "shop/shoplist";
		}else {
			return "frontend/index";
		}
	}

}
