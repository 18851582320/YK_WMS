package com.iwfm.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.iwfm.util.PublicUtil;

public class PathUtil {

	/**
	 * 图片访问路径
	 * @param pathType
	 *            图片类型 visit-访问；save-保存
	 * @param pathCategory
	 *            图片类别，如：话题图片-topic、话题回复图片-reply、商家图片
	 * @return
	 */
	
	/**
	 * @Title:getPicturePath
	 * @Description: 图片访问路径
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param pathType 图片类型 ：visit-访问；save-保存
	 * @param @param pathCategory  图片类别，如：话题图片-topic、话题回复图片-reply、商家图片
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getPicturePath(String pathType, String pathCategory) {
		String strResult = "";
		@SuppressWarnings("unused")
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		StringBuffer strBuf = new StringBuffer();
		if ("visit".equals(pathType)) {
		
		} else if ("save".equals(pathType)) {
			String projectPath = PublicUtil.getPorjectPath().replaceAll("\\\\","/");
			projectPath = splitString(projectPath, "bin/");
			strBuf.append(projectPath);
			strBuf.append("webapps/ROOT/");
		}
		strResult = strBuf.toString();
		return strResult;
	}

	private static String splitString(String str, String param) {
		String result = str;
		if (str.contains(param)) {
			int start = str.indexOf(param);
			result = str.substring(0, start);
		}

		return result;
	}
	
	/**
	 * @Title:getClasspath
	 * @Description: 获取classpath1
	 * @author yk
	 * @date 2018年1月25日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getClasspath(){
		String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../").replaceAll("file:/", "").replaceAll("%20", " ").trim();	
		if(path.indexOf(":") != 1){
			path = File.separator + path;
		}
		return path;
	}
	
	/**
	 * @Title:getClassResources
	 * @Description: 获取classpath2
	 * @author yk
	 * @date 2018年1月25日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getClassResources(){
		String path =  (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();	
		if(path.indexOf(":") != 1){
			path = File.separator + path;
		}
		return path;
	}
	
	public static String PathAddress() {
		String strResult = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		StringBuffer strBuf = new StringBuffer();
		strBuf.append(request.getScheme() + "://");
		strBuf.append(request.getServerName() + ":");
		strBuf.append(request.getServerPort() + "");
		strBuf.append(request.getContextPath() + "/");
		strResult = strBuf.toString();// +"ss/";//加入项目的名称
		return strResult;
	}
	
}
