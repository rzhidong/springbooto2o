package com.o2o.dto;

import java.util.List;

import com.o2o.entity.Shop;
import com.o2o.enums.ShopStateEnum;

public class ShopExecution {

	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 店铺数量
	private int count;

	// 操作的shop(增删改店铺的时候用到)
	private Shop shop;

	// shop列表(查询店铺列表的时候使用)
	private List<Shop> shopList;

	public ShopExecution() {
		// TODO Auto-generated constructor stub
	}

	// 店铺操作失败的时候使用的构造器
	public ShopExecution(ShopStateEnum stateEnum) {
		// TODO Auto-generated constructor stub
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 店铺操作成功的时候使用的构造器
	public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
		// TODO Auto-generated constructor stub
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}

	// 店铺操作成功的时候使用的构造器
	public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
		// TODO Auto-generated constructor stub
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	@Override
	public String toString() {
		return "ShopExecution [state=" + state + ", stateInfo=" + stateInfo + ", count=" + count + ", shop=" + shop
				+ ", shopList=" + shopList + "]";
	}
	
}
