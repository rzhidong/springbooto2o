package com.o2o.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest{

	@Autowired
	private ProductService productService;
	
	@Test
	public void testAddProduct() throws FileNotFoundException {
		Product product = new Product();
		// 创建shopId为6且productCategoryId为1的商品实例并给其成员变量赋值
		Shop shop = new Shop();
		shop.setShopId(6L);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		
		product.setShop(shop);
		product.setProductCategory(productCategory);
		
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1");
		product.setPriority(20);
		// 创建缩略图文件流
		File thumbnailFile = new File("G:\\BaiduNetdiskDownload\\image\\ykll (1).jpg");
		InputStream thumbnailInputStream = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), thumbnailInputStream);
		// 创建两个商品详情图文件流并将他们添加到详情图列表中
		File productImg1 = new File("G:\\BaiduNetdiskDownload\\image\\taoxi (1).jpg");
		InputStream productImgIS1 = new FileInputStream(productImg1);
		File productImg2 = new File("G:\\BaiduNetdiskDownload\\image\\xzq (1).png");
		InputStream productImgIS2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(), productImgIS1));
		productImgList.add(new ImageHolder(productImg2.getName(), productImgIS2));
		
		// 添加商品并验证
		ProductExecution productExecution = productService.addProduct(product, thumbnail, productImgList);
		System.out.println(productExecution);
	}
	
	@Test
	public void testModifyProduct() throws FileNotFoundException {
		Product product = productService.getProductById(19L);
		product.setProductName("测试商品"+new Date());
		product.setProductDesc("测试商品"+new Date());
		// 创建缩略图文件流
		File thumbnailFile = new File("G:\\BaiduNetdiskDownload\\image\\ykll (2).jpg");
		InputStream thumbnailInputStream = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), thumbnailInputStream);
		// 创建两个商品详情图文件流并将他们添加到详情图列表中
		File productImg1 = new File("G:\\BaiduNetdiskDownload\\image\\taoxi (2).jpg");
		InputStream productImgIS1 = new FileInputStream(productImg1);
		File productImg2 = new File("G:\\BaiduNetdiskDownload\\image\\xzq (2).png");
		InputStream productImgIS2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(), productImgIS1));
		productImgList.add(new ImageHolder(productImg2.getName(), productImgIS2));
		
		// 添加商品并验证
		ProductExecution productExecution = productService.modifyProduct(product, thumbnail,productImgList);
		System.out.println(productExecution);
	}
}
