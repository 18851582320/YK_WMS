package com.iwfm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.iwfm.util.DateUtil;
import com.iwfm.util.MD5;

/**
 * ClassName: Tools 
 * @Description: 工具类
 * @author yk
 * @date 2017年8月1日
 */
public class Tools {
	/**
	 * @Title: getRandomNum
	 * @Description: 随机生成六位数验证码 
	 * @author yk
	 * @date 2017年8月1日
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int getRandomNum(){
		 Random r = new Random();
		 return r.nextInt(900000)+100000;//(Math.random()*(999999-100000)+100000)
	}
	
	/**
	 * @Title: notEmpty
	 * @Description: 检测字符串是否不为空(null,"","null")
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param s
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	
	/**
	 * @Title:fillPrefix
	 * @Description: 字符串按长度补足文字，如12补足为0012等
	 * @author yk
	 * @date 2018年1月22日
	 * @param @param StringToFix 需要补足的字符串
	 * @param @param fixChar     补充的字符
	 * @param @param targetLen   补充后的总长度
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static final String fillPrefix(String StringToFix, String fixChar, int targetLen)
	{
		int len = StringToFix.length();
		for (int i = 0; i < targetLen - len; i += fixChar.length())
			StringToFix = fixChar + StringToFix;
		return StringToFix;
	}

	
	
	/**
	 * @Title: isEmpty
	 * @Description: 检测字符串是否为空(null,"","null")
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param s
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	/**
	 * @Title: str2StrArray
	 * @Description: 字符串转换为字符串数组
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param str
	 * @param @param splitRegex
	 * @param @return
	 * @return String[]
	 * @throws
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * @Title: str2StrArray
	 * @Description: 用默认的分隔符(,)将字符串转换为字符串数组
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param str
	 * @param @return
	 * @return String[]
	 * @throws
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}
	
	/**
	 * @Title: date2Str
	 * @Description: 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param date
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String date2Str(Date date){
		return date2Str(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * @Title: str2Date
	 * @Description: 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param date
	 * @param @return
	 * @return Date
	 * @throws
	 */
	public static Date str2Date(String date){
		if(notEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		}else{
			return null;
		}
	}
	
	/**
	 * @Title: date2Str
	 * @Description: 按照参数format的格式，日期转字符串
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param date
	 * @param @param format
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String date2Str(Date date,String format){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * @Title: getTimes
	 * @Description: 把时间根据时、分、秒转换为时间段
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param StrDate
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getTimes(String StrDate){
		String resultTimes = "";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    java.util.Date now;
	    
	    try {
	    	now = new Date();
	    	java.util.Date date=df.parse(StrDate);
	    	long times = now.getTime()-date.getTime();
	    	long day  =  times/(24*60*60*1000);
	    	long hour = (times/(60*60*1000)-day*24);
	    	long min  = ((times/(60*1000))-day*24*60-hour*60);
	    	long sec  = (times/1000-day*24*60*60-hour*60*60-min*60);
	        
	    	StringBuffer sb = new StringBuffer();
	    	if(hour>0 ){
	    		sb.append(hour+"小时前");
	    	} else if(min>0){
	    		sb.append(min+"分钟前");
	    	} else{
	    		sb.append(sec+"秒前");
	    	}
	    		
	    	resultTimes = sb.toString();
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	    
	    return resultTimes;
	}
	
	/**
	 * 
	 * @Title: writeFile
	 * @Description: 写txt里的单行内容
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param fileP
	 * @param @param content
	 * @return void
	 * @throws
	 */
	public static void writeFile(String fileP,String content){
		String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
		filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
		if(filePath.indexOf(":") != 1){
			filePath = File.separator + filePath;
		}
		try {
	        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath),"utf-8");      
	        BufferedWriter writer=new BufferedWriter(write);          
	        writer.write(content);      
	        writer.close(); 

	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title: checkEmail
	 * @Description: 验证邮箱
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param email
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	 public static boolean checkEmail(String email){
	  boolean flag = false;
	  try{
	    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(email);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	
	 /**
	  * @Title: checkMobileNumber
	  * @Description: 验证手机号码
	  * @author yk
	  * @date 2017年8月1日
	  * @param @param mobileNumber
	  * @param @return
	  * @return boolean
	  * @throws
	  */
	 public static boolean checkMobileNumber(String mobileNumber){
	  boolean flag = false;
	  try{
	    Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
	    Matcher matcher = regex.matcher(mobileNumber);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	 
	 /**
	  * @Title: checkKey
	  * @Description: 检测KEY是否正确
	  * @author yk
	  * @date 2017年8月1日
	  * @param @param paraname
	  * @param @param FKEY
	  * @param @return
	  * @return boolean
	  * @throws
	  */
	public static boolean checkKey(String paraname, String FKEY){
		paraname = (null == paraname)? "":paraname;
		return MD5.md5(paraname+DateUtil.getYYYYMMDD()+",fh,").equals(FKEY);
	}
	 
	/**
	 * @Title: readTxtFile
	 * @Description: 读取txt里的单行内容
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param fileP
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String readTxtFile(String fileP) {
		try {
			
			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if(filePath.indexOf(":") != 1){
				filePath = File.separator + filePath;
			}
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 		// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), encoding);	// 考虑到编码格式
				@SuppressWarnings("resource")
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			}else{
				System.out.println("找不到指定的文件,查看此路径是否正确:"+filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return "";
	}
	
	/**
	 * @Title:readTxtFileAll
	 * @Description: 读取text中的信息
	 * @author yk
	 * @date 2018年3月28日
	 * @param @param fileP
	 * @param @param encoding
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String readTxtFileAll(String fileP, String encoding) {
		StringBuffer fileContent = new StringBuffer(); 
		try {
			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if(filePath.indexOf(":") != 1){
				filePath = File.separator + filePath;
			}
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 		// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), encoding);	// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					fileContent.append(lineTxt);
					fileContent.append("\n");
				}
				read.close();
			}else{
				System.out.println("找不到指定的文件,查看此路径是否正确:"+filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return fileContent.toString();
	}
	
}
