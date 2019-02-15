package com.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.Area;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;
import com.o2o.enums.ShopStateEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopServiceTest{
	
	@Autowired
	private ShopService shopService;
	
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(1);
		shopCategory.setShopCategoryId(1L);
		
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		
		shop.setShopName("店铺test");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("phone");
		shop.setPriority(1);
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("advice");
		
		File shopImg = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Thumbnailator\\java.jpg");
		InputStream shopImgInputStream = new FileInputStream(shopImg);
		ImageHolder thumbnail = new ImageHolder(shopImg.getName(), shopImgInputStream);
		ShopExecution shopExecution = shopService.addShop(shop, thumbnail);
		assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
	}
	
	@Test
	@Ignore
	public void testGetByShopId() {
		System.out.println(shopService.getByShopId(1L));
	}
	
	@Test
	public void testModifyShop()throws Exception {
		Shop shop = shopService.getByShopId(24L);
		System.out.println(shop);
		shop.setShopName("修改后的店铺名称");
		//File shopImg = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Thumbnailator\\java.jpg");
		//InputStream shopImgInputStream = new FileInputStream(shopImg);
		//ImageHolder thumbnail = new ImageHolder(shopImg.getName(), shopImgInputStream);
		ShopExecution shopExecution = shopService.modifyShop(shop, null);
		System.out.println(shopExecution.getStateInfo());
	}
	
	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		shopCondition.setShopName("te");
		ShopExecution se = shopService.getShopList(shopCondition, 2, 5);
		List<Shop> shopList = se.getShopList();
		System.out.println(se+"\n"+shopList.size());
	}

}
