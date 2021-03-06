package com.o2o.web.wechat;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.o2o.util.wechat.SignUtil;

@Controller
@RequestMapping("wechat")
public class WechatController {

	private static Logger log = LoggerFactory.getLogger(WechatController.class);

	@RequestMapping(method = { RequestMethod.GET })
	private void doGet(HttpServletRequest request, HttpServletResponse response) {
		log.debug("weixin get...");
		// 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		log.info("微信加密签名: " + signature + "\t时间戳: " + timestamp + "\t随机数: " + nonce + "\t随机字符串: " + echostr);

		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				log.debug("wechat get success....");
				out.print(echostr);
			}
		} catch (Exception e) {
			log.error("wechat get error!");
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
