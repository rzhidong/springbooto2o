package com.o2o.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.entity.ShopCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryServiceTest{
	
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@Test
	public void testGetShopCategoryList() {
		ShopCategory shopCategoryCondition = new ShopCategory();
		System.out.println(shopCategoryService.getShopCategoryList(shopCategoryCondition));
		
		System.out.println(shopCategoryService.getShopCategoryList(null));
		
		ShopCategory testCategory = new ShopCategory();
		ShopCategory parentCategory = new ShopCategory();
		parentCategory.setShopCategoryId(12L);
		testCategory.setParent(parentCategory);
		System.out.println(shopCategoryService.getShopCategoryList(testCategory));
	}
}
