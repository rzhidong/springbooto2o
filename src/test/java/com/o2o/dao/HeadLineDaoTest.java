package com.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.entity.HeadLine;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeadLineDaoTest {

	@Autowired
	private HeadLineDao headLineDao;

	@Test
	public void testQueryHeadLine() {
		HeadLine headLineCondition = new HeadLine();
		headLineCondition.setEnableStatus(1);
		List<HeadLine> headLineList = headLineDao.queryHeadLine(headLineCondition);
		if (headLineList != null) {
			for (HeadLine headLine : headLineList) {
				System.out.println(headLine);
			}
		}
	}

}
