package com.o2o.dto;

import java.io.InputStream;

public class ImageHolder {
	
	//图片名称
	private String imageName;
	
	//图片流
	private InputStream imageInputStream;
	
	public ImageHolder(String imageName, InputStream imageInputStream) {
		this.imageName = imageName;
		this.imageInputStream = imageInputStream;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public InputStream getImageInputStream() {
		return imageInputStream;
	}

	public void setImageInputStream(InputStream imageInputStream) {
		this.imageInputStream = imageInputStream;
	}

	
	
}
