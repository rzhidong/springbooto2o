package com.o2o.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.entity.LocalAuth;
import com.o2o.entity.PersonInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAuthServiceTest {

	@Autowired
	private LocalAuthService localAuthService;

	@Test
	public void testBindLocalAuth() {
		LocalAuth localAuth = new LocalAuth();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(2L);
		localAuth.setPersonInfo(personInfo);

		localAuth.setUsername("localtest");
		localAuth.setPassword("123456");
		localAuth.setCreateTime(new Date());
		localAuth.setLastEditTime(new Date());

		System.out.println(localAuthService.bindLocalAuth(localAuth));
	}

	@Test
	public void testModifyLocalAuth() {
		long userId = 2L;
		String username = "localtest";
		String password = "123456";
		String newPassword = "1234567";
		System.out.println(localAuthService.modifyLocalAuth(userId, username, password, newPassword));
	}

}
