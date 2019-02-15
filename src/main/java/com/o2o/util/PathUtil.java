package com.o2o.util;

public class PathUtil {
	
	private static String seperator = System.getProperty("file.separator");
	
	/**
	 * 返回项目根路径
	 * @return
	 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");//Windows 7
		
		String basePath = "";
		
		if (os.toLowerCase().startsWith("win")) {
			basePath="D:/image";
		}else {
			basePath="/opt/image";
		}
		
		basePath = basePath.replace("/", seperator);
		
		return basePath;
	}
	
	/**
	 * 返回店铺图片
	 * @param shopId
	 * @return
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/images/item/shop/" + shopId + "/";
		return imagePath.replace("/", seperator);
	}

}
