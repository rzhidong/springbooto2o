package com.o2o.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.entity.HeadLine;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeadLineServiceTest{
	
	@Autowired
	private HeadLineService headLineService;
	
	@Test
	public void testGetHeadLineList() {
		HeadLine headLineCondition = new HeadLine();
		headLineCondition.setEnableStatus(1);
		System.out.println(headLineService.getHeadLineList(headLineCondition ));
	}
}
