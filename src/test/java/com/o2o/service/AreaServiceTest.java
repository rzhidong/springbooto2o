package com.o2o.service;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.entity.Area;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest{
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private CacheService cacheService;
	
	@Test
	public void testGetAreaList() {
		List<Area> list = areaService.getAreaList();
		System.out.println(list);
		cacheService.removeFromCache(AreaService.AREALISTKEY);
		System.out.println(areaService.getAreaList());
	}

}
