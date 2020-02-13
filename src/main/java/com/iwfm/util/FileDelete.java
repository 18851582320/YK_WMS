package com.iwfm.util;

import java.io.File;

/**
 * ClassName: FileDelete 
 * @Description: java删除文件和文件夹
 * @author yk
 * @date 2018年3月30日
 */
public class FileDelete {

	/**
	 * @Title:delFolder
	 * @Description: 
	 * @author yk
	 * @date 2018年8月3日
	 * @param @param folderPath   文件路径 (只删除此路径的最末路径下所有文件和文件夹)
	 * @return void  
	 * @throws
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); 	// 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); 		// 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title:delAllFile
	 * @Description: 删除指定文件夹下所有文件
	 * @author yk
	 * @date 2018年8月3日
	 * @param @param path 文件夹完整绝对路径
	 * @param @return   
	 * @return boolean  
	 * @throws
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);	// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);	// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * @Title:delFile
	 * @Description: 删除单个文件
	 * @author yk
	 * @date 2018年8月3日
	 * @param @param path
	 * @param @return   
	 * @return boolean  
	 * @throws
	 */
	public static boolean delFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return true;
		}else if(file.exists()){
			flag=file.delete();
		}
		return flag;
	}
	
}
