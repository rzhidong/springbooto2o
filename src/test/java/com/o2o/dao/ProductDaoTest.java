package com.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.ProductImg;
import com.o2o.entity.Shop;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDaoTest{

	@Autowired
	private ProductDao productDao;
	
	@Test
	public void testInsertProduct() {
		Shop shop1 = new Shop();
		shop1.setShopId(23L);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		
		// 初始化三个商品实例并添加进shopId为23的店铺里，
		// 商品类别Id为1
		Product product1 = new Product();
		product1.setProductName("测试1");
		product1.setProductDesc("测试Desc1");
		product1.setPriority(1);
		product1.setEnableStatus(1);
		product1.setCreateTime(new Date());
		product1.setLastEditTime(new Date());
		product1.setShop(shop1);
		product1.setProductCategory(productCategory);
		Product product2 = new Product();
		product2.setProductName("测试2");
		product2.setProductDesc("测试Desc2");
		product2.setImgAddr("test2");
		product2.setPriority(2);
		product2.setEnableStatus(0);
		product2.setCreateTime(new Date());
		product2.setLastEditTime(new Date());
		product2.setShop(shop1);
		product2.setProductCategory(productCategory);
		Product product3 = new Product();
		product3.setProductName("test3");
		product3.setProductDesc("测试Desc3");
		product3.setImgAddr("test3");
		product3.setPriority(3);
		product3.setEnableStatus(1);
		product3.setCreateTime(new Date());
		product3.setLastEditTime(new Date());
		product3.setShop(shop1);
		product3.setProductCategory(productCategory);
		
		// 判断添加是否成功
		int effectedNum = productDao.insertProduct(product1);
		assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product2);
		assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product3);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testQueryProductById() {
		Product product = productDao.queryProductById(15L);
		System.out.println(product);
		for (ProductImg productImg : product.getProductImgList()) {
			System.out.println(productImg);
		}
		System.out.println(product.getShop());
		System.out.println(product.getProductCategory());
	}
	
	@Test
	public void testUpdateProduct() {
		Product product = new Product();
		product.setProductId(16L);
		product.setLastEditTime(new Date());
		Shop shop = new Shop();
		shop.setShopId(6L);
		product.setShop(shop);
		product.setEnableStatus(1);
		System.out.println(productDao.updateProduct(product));
	}
	
	@Test
	public void testQueryProductList() {
		Product productCondition = new Product();
		productCondition.setProductName("商品");
		List<Product> productList = productDao.queryProductList(productCondition, 0, 5);
		for (Product product : productList) {
			System.out.println(product);
		}
		System.out.println(productDao.queryProductCount(productCondition));
		
	}
	
	@Test
	public void testUpdateProductCategoryToNull() {
		int effectedNum = productDao.updateProductCategoryToNull(1L);
		System.out.println(effectedNum);
	}
}
