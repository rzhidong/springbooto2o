package com.o2o.service;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dto.WechatAuthExecution;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.WechatAuth;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthServiceTest{
	
	@Autowired
	private WechatAuthService wechatAuthService;
	
	@Test
	public void testRegister() {
		WechatAuth wechatAuth = new WechatAuth();
		wechatAuth.setOpenId(UUID.randomUUID().toString().replace("-", ""));
		PersonInfo personInfo =  new PersonInfo();
		//personInfo.setUserId(1L);
		personInfo.setName("test");
		personInfo.setGender("女");
		personInfo.setUserType(1);
		wechatAuth.setPersonInfo(personInfo);
		WechatAuthExecution wechatAuthExecution = wechatAuthService.register(wechatAuth);
		System.out.println(wechatAuthExecution);
	}
	
	@Test
	public void testGetWechatAuthByOpenId() {
		System.out.println(wechatAuthService.getWechatAuthByOpenId("oeU_L5pxZPLT_N9heZgvnAqvdq30"));
	}
}
