package com.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.entity.ShopCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryDaoTest{
	
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Test
	public void testQueryShopCategory() {
		//获取一级列表
		List<ShopCategory> list1 = shopCategoryDao.queryShopCategory(null);
		System.out.println(list1);
		
		//获取所有列表
		List<ShopCategory> list2 = shopCategoryDao.queryShopCategory(new ShopCategory());
		System.out.println(list2);
		
		ShopCategory testCategory = new ShopCategory();
		ShopCategory parentCategory = new ShopCategory();
		parentCategory.setShopCategoryId(12L);
		testCategory.setParent(parentCategory);
		System.out.println(shopCategoryDao.queryShopCategory(testCategory));
	}

}
