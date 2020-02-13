package com.iwfm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: FileUpload 
 * @Description: 文件上传
 * @author yk
 * @date 2018年1月25日
 */
public class FileUpload {
	/**
	 * @Title:fileUp
	 * @Description: 上传文件
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param file  文件对象
	 * @param @param filePath  上传路径
	 * @param @param fileName  文件名
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String fileUp(MultipartFile file, String filePath, String fileName){
		String extName = ""; // 扩展名格式：
		try {
			if (file.getOriginalFilename().lastIndexOf(".") >= 0){
				extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			}
			copyFile(file.getInputStream(), filePath, fileName+extName).replaceAll("-", "");
		} catch (IOException e) {
			System.out.println(e);
		}
		return fileName+extName;
	}
	
	/**
	 * @Title:fileUpload
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月3日
	 * @param @param file
	 * @param @param filePath
	 * @param @param fileName
	 * @param @return   
	 * @return boolean  
	 * @throws
	 */
	public static boolean fileUpload(MultipartFile file, String filePath, String fileName){
		String extName = ""; // 扩展名格式：
		try {
			if (file.getOriginalFilename().lastIndexOf(".") >= 0){
				extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			}
			copyFile(file.getInputStream(), filePath, fileName+extName).replaceAll("-", "");
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * @Title:copyFile
	 * @Description: 写文件到当前目录的upload目录中
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param in
	 * @param @param dir
	 * @param @param realName
	 * @param @return
	 * @param @throws IOException   
	 * @return String  
	 * @throws
	 */
	public static String copyFile(InputStream in, String dir, String realName)throws IOException {
		File file = mkdirsmy(dir,realName);
		FileUtils.copyInputStreamToFile(in, file);
		return realName;
	}
	
	/**
	 * @Title:mkdirsmy
	 * @Description: 判断路径是否存在，否：创建此路径
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param dir  文件路径
	 * @param @param realName  文件名
	 * @param @return
	 * @param @throws IOException   
	 * @return File  
	 * @throws
	 */
	public static File mkdirsmy(String dir, String realName) throws IOException{
		File file = new File(dir, realName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		return file;
	}
	
	
	/**下载网络图片上传到服务器上
	 * @param httpUrl 图片网络地址
	 * @param filePath	图片保存路径
	 * @param myFileName  图片文件名(null时用网络图片原名)
	 * @return	返回图片名称
	 */
	
	/**
	 * @Title:getHtmlPicture
	 * @Description: 下载网络图片上传到服务器上
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param httpUrl  图片网络地址
	 * @param @param filePath  图片保存路径
	 * @param @param myFileName 图片文件名(null时用网络图片原名)
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getHtmlPicture(String httpUrl, String filePath , String myFileName) {
		
		URL url;						//定义URL对象url
		BufferedInputStream in;			//定义输入字节缓冲流对象in
		FileOutputStream file;			//定义文件输出流对象file
		try {
			String fileName = null == myFileName?httpUrl.substring(httpUrl.lastIndexOf("/")).replace("/", ""):myFileName; //图片文件名(null时用网络图片原名)
			url = new URL(httpUrl);		//初始化url对象
			in = new BufferedInputStream(url.openStream());									//初始化in对象，也就是获得url字节流
			//file = new FileOutputStream(new File(filePath +"\\"+ fileName));
			file = new FileOutputStream(mkdirsmy(filePath,fileName));
			int t;
			while ((t = in.read()) != -1) {
				file.write(t);
			}
			file.close();
			in.close();
			return fileName;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
