package com.iwfm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * ClassName: NumericUtil 
 * @Description: 数字处理类
 * @author yk
 * @date 2018年1月31日
 */
public class NumericUtil {
	/**
	 * 
	 * @Title:isMatch
	 * @Description: 判断值是否与正则表达式匹配
	 * @author yk
	 * @date 2018年2月1日
	 * @param @param regex
	 * @param @param orginal
	 * @param @return   
	 * @return boolean  
	 * @throws
	 */
	private static boolean isMatch(String regex, String orginal){ 
	    if (orginal == null || orginal.trim().equals("")) { 
	      return false; 
	    } 
	    Pattern pattern = Pattern.compile(regex); 
	    Matcher isNum = pattern.matcher(orginal); 
	    return isNum.matches(); 
	  } 
/**
 * 
 * @Title:isNumeric
 * @Description: 判断是否是数字类型
 * @author yk
 * @date 2018年1月31日
 * @param @param str
 * @param @return   
 * @return boolean  
 * @throws
 */
	public static boolean isNumeric(String str){
		   //Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");//("-?[0-9]+.?[0-9]+");  整数compile("^[-\\+]?[\\d]*$")
		    return isMatch("-?[0-9]+\\.?[0-9]*", str); 
		   
		}
	/**
	   * 
	   * @Title:isPositiveInteger
	   * @Description: 判断时不是正整数
	   * @author yk
	   * @date 2018年2月12日
	   * @param @param orginal
	   * @param @return   
	   * @return boolean  
	   * @throws
	   */
	  public static boolean isPositiveInteger(String orginal) { 
	    return isMatch("^\\+{0,1}[1-9]\\d*", orginal); 
	  } 
	  /**
	   * 
	   * @Title:isPositiveDecimal
	   * @Description: 判断是不是正的小数
	   * @author yk
	   * @date 2018年2月12日
	   * @param @param orginal
	   * @param @return   
	   * @return boolean  
	   * @throws
	   */
	  public static boolean isPositiveDecimal(String orginal){ 
	    return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal); 
	  } 
	/**
	 * 
	 * @Title:isBooleanNumber
	 * @Description:  判断是不是boolean类型 01
	 * @author yk
	 * @date 2018年2月1日
	 * @param @param orginal
	 * @param @return   
	 * @return boolean  
	 * @throws
	 */
	
	  public static boolean isBooleanNumber(String orginal) { 
		    return isMatch("{0,1}[0-1]", orginal); 
		  } 
}
