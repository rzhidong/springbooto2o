package com.o2o.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.entity.PersonInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonInfoDaoTest{

	@Autowired
	private PersonInfoDao personInfoDao;
	
	@Test
	public void testQueryPersonInfoById() {
		System.out.println(personInfoDao.queryPersonInfoById(1L));
	}
	
	@Test
	public void testInsertPersonInfo() {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setCreateTime(new Date());
		personInfo.setLastEditTime(new Date());
		personInfo.setName("hello");
		personInfo.setGender("女");
		personInfo.setUserType(1);
		personInfo.setEnableStatus(1);
		int effectedNum = personInfoDao.insertPersonInfo(personInfo);
		System.out.println(effectedNum);
		
	}
}
