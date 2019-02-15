package com.o2o.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dto.ProductCategoryExecution;
import com.o2o.entity.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceTest{
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Test
	public void testGetProductCategoryList() {
		List<ProductCategory> list = productCategoryService.getProductCategoryList(23L);
		System.out.println(list);
	}
	
	@Test
	public void testBatchInsertProductCategory() {
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		ProductCategory productCategory = new ProductCategory();
		productCategory.setShopId(23L);
		productCategory.setProductCategoryName("商品类别7");
		productCategory.setPriority(0);
		productCategory.setCreateTime(new Date());
		productCategoryList.add(productCategory);
		
		ProductCategory productCategory1 = new ProductCategory();
		productCategory1.setShopId(23L);
		productCategory1.setProductCategoryName("商品类别8");
		productCategory1.setPriority(6);
		productCategory1.setCreateTime(new Date());
		productCategoryList.add(productCategory1);
		System.out.println(productCategoryList);
		
		ProductCategoryExecution productCategoryExecution = productCategoryService.batchAddProductCategory(productCategoryList);
		System.out.println(productCategoryExecution.getProductCategoryList());
	}

}
