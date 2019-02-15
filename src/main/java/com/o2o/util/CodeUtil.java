package com.o2o.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeUtil {
	
	private static Logger logger = LoggerFactory.getLogger(CodeUtil.class);

	/**
	 * 检查验证码是否和预期相符
	 * 
	 * @param request
	 * @return
	 */
	public static boolean checkVerifyCode(HttpServletRequest request) {
		String verifyCodeExpected = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		verifyCodeExpected = verifyCodeExpected.toLowerCase();
		logger.info("verifyCodeExpected: " + verifyCodeExpected);
		
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		verifyCodeActual = verifyCodeActual.toLowerCase();
		logger.info("verifyCodeActual: " + verifyCodeActual);
		
		if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
			return false;
		}
		return true;
	}

}
