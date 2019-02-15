package com.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.entity.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest{
	
	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Test
	public void testBQueryProductCategoryList() {
		List<ProductCategory> list = productCategoryDao.queryProductCategoryList(23L);
		System.out.println(list);
	}
	
	@Test
	public void testABatchInsertProductCategory() {
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		ProductCategory productCategory = new ProductCategory();
		productCategory.setShopId(6L);
		productCategory.setProductCategoryName("商品类别5");
		productCategory.setPriority(0);
		productCategory.setCreateTime(new Date());
		productCategoryList.add(productCategory);
		
		ProductCategory productCategory1 = new ProductCategory();
		productCategory1.setShopId(6L);
		productCategory1.setProductCategoryName("商品类别6");
		productCategory1.setPriority(6);
		productCategory1.setCreateTime(new Date());
		productCategoryList.add(productCategory1);
		System.out.println(productCategoryList);
		
		int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
		System.out.println(effectedNum);
	}
	
	@Test
	public void testCDeleteProductCategory() {
		long shopId = 6L;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		for (ProductCategory productCategory : productCategoryList) {
			if (productCategory.getProductCategoryName().contains("商品类别")) {
				int effectedNum = productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(), shopId);
				assertEquals(1, effectedNum);
			}
		}
	}
}
