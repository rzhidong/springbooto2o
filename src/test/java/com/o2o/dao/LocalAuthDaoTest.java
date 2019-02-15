package com.o2o.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.entity.LocalAuth;
import com.o2o.entity.PersonInfo;
import com.o2o.util.MD5;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAuthDaoTest {

	@Autowired
	private LocalAuthDao localAuthDao;

	@Test
	public void testQueryLocalByUserId() {
		System.out.println(localAuthDao.queryLocalByUserId(1L));
	}

	@Test
	public void testQueryLocalByUserNameAndPwd() {
		System.out.println(localAuthDao.queryLocalByUserNameAndPwd("localtest", MD5.getMd5("123456")));
	}

	@Test
	public void testUpdateLocalAuth() {
		long userId = 1L;
		String username = "testbind";
		String newPassword = "123456";
		String password = "123456";
		Date lastEditTime = new Date();
		System.out.println(localAuthDao.updateLocalAuth(userId, username, MD5.getMd5(password), MD5.getMd5(newPassword),
				lastEditTime));
	}

	@Test
	public void testInsertLocalAuth() {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(2L);
		LocalAuth localAuth = new LocalAuth();
		localAuth.setPersonInfo(personInfo);

		localAuth.setUsername("localtest");
		localAuth.setPassword(MD5.getMd5("123456"));
		localAuth.setCreateTime(new Date());
		localAuth.setLastEditTime(new Date());
		System.out.println(localAuthDao.insertLocalAuth(localAuth));
	}

}
