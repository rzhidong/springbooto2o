package com.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {

	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random random = new Random();
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	// 缩略图长宽是200*200
	private static final int THUMBNAIL_WIDTH = 200;
	private static final int THUMBNAIL_HEIGHT = 200;
	private static final float THUMBNAIL_QUALITY = 0.8f;

	// 详情图长宽是337*640
	private static final int NORMAL_WIDTH = 337;
	private static final int NORMAL_HEIGHT = 640;
	private static final float NORMAL_QUALITY = 0.9f;

	/*
	 * 测试
	 */
	public static void main(String[] args) {
		// System.out.println(System.getProperty("user.dir"));
		// https://github.com/coobird/thumbnailator/wiki/Examples

		String thumPath = System.getProperty("user.dir") + "\\src\\main\\resources\\";

		File inputfile = new File(thumPath + "Thumbnailator\\java.jpg");
		File markFile = new File(thumPath + "watermark.jpg");

		// String basePath =
		// Thread.currentThread().getContextClassLoader().getResource("").getPath();
		File imgFile = new File(basePath + "/Thumbnailator/java.jpg");
		File watermarkFile = new File(basePath + "watermark.jpg");

		try {
			Thumbnails.of(inputfile).size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(markFile), 0.25f)
					.outputQuality(0.8f).toFile(thumPath + "/Thumbnailator/java_new.jpg");

			Thumbnails.of(imgFile).size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermarkFile), 0.25f)
					.outputQuality(0.8f).toFile(basePath + "java_new.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 将CommonsMultipartFile转换成File类
	 * 
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
		File newFile = new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}

	/**
	 * 处理缩略图，并返回新生成图片的相对值路径
	 * 
	 * @param thumbnail
	 *            文件流
	 * @param targetAddr
	 *            图片存储路径
	 * @return
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		// 获取不重复的随机名
		String realFileName = getRandomFileName();
		// 获取文件的扩展名如png,jpg等
		String extension = getFileExension(thumbnail.getImageName());
		// 如果目标路径不存在，则自动创建
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is :" + relativeAddr);
		// 获取文件要保存到的目标路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("watermark location: " + basePath + "watermark.jpg");
		// 调用Thumbnails生成带有水印的图片
		try {
			Thumbnails.of(thumbnail.getImageInputStream()).size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
					.outputQuality(THUMBNAIL_QUALITY).toFile(dest);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new RuntimeException("创建缩图片失败：" + e.toString());
		}
		// 返回图片相对路径地址
		return relativeAddr;
	}
	
	/**
	 * 处理详情图，并返回新生成图片的相对值路径
	 * 
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		// 获取不重复的随机名
		String realFileName = getRandomFileName();
		// 获取文件的扩展名如png,jpg等
		String extension = getFileExension(thumbnail.getImageName());
		// 如果目标路径不存在，则自动创建
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is :" + relativeAddr);
		// 获取文件要保存到的目标路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("watermark location: " + basePath + "watermark.jpg");
		// 调用Thumbnails生成带有水印的图片
		try {
			Thumbnails.of(thumbnail.getImageInputStream()).size(NORMAL_WIDTH, NORMAL_HEIGHT)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
					.outputQuality(NORMAL_QUALITY).toFile(dest);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new RuntimeException("创建详情图片失败：" + e.toString());
		}
		// 返回图片相对路径地址
		return relativeAddr;
	}

	/**
	 * 创建目标路径所涉及到的目录，即/home/image/xxx.jpg, 那么 home image 这两个文件夹都得自动创建
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		// 图片绝对路径
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取输入文件流的扩展名
	 * 
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExension(String fileName) {
		// TODO Auto-generated method stub
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		// 获取随机的五位数
		int rannum = random.nextInt(89999) + 10000;

		String nowTimeStr = sDateFormat.format(new Date());

		return nowTimeStr + rannum;
	}

	/**
	 * storePath是文件的路径还是目录的路径 如果storePath是文件路径则删除该文件 如果storePath是目录路径则删除该目录下的所有文件
	 * 
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				File[] files = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}
}
