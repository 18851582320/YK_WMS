package com.iwfm.util;

import java.math.BigInteger;

import com.iwfm.util.Tools;

/**
 * ClassName: RightsHelper 
 * @Description: 权限帮助
 * @author yk
 * @date 2017年8月1日
 */
public class RightsHelper {
	
	/**
	 * @Title: sumRights
	 * @Description: 利用BigInteger对权限进行2的权的和计算
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param rights
	 * @param @return
	 * @return BigInteger
	 * @throws
	 */
	public static BigInteger sumRights(int[] rights){
		BigInteger num = new BigInteger("0");
		for(int i=0; i<rights.length; i++){
			num = num.setBit(rights[i]);
		}
		return num;
	}
	
	/**
	 * @Title: sumRights
	 * @Description: 利用BigInteger对权限进行2的权的和计算
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param rights
	 * @param @return
	 * @return BigInteger
	 * @throws
	 */
	public static BigInteger sumRights(String[] rights){
		BigInteger num = new BigInteger("0");
		for(int i=0; i<rights.length; i++){
			num = num.setBit(Integer.parseInt(rights[i]));
		}
		return num;
	}
	
	/**
	 * @Title: testRights
	 * @Description: 测试是否具有指定编码的权限
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param sum
	 * @param @param targetRights
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean testRights(BigInteger sum,int targetRights){
		return sum.testBit(targetRights);
	}
	
	/**
	 * @Title: testRights
	 * @Description: 测试是否具有指定编码的权限
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param sum
	 * @param @param targetRights
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean testRights(String sum,int targetRights){
		if(Tools.isEmpty(sum))
			return false;
		return testRights(new BigInteger(sum),targetRights);
	}
	
	/**
	 * @Title: testRights
	 * @Description: 测试是否具有指定编码的权限
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param sum
	 * @param @param targetRights
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean testRights(String sum,String targetRights){
		if(Tools.isEmpty(sum))
			return false;
		return testRights(new BigInteger(sum),targetRights);
	}
	
	/**
	 * @Title: testRights
	 * @Description: 测试是否具有指定编码的权限
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param sum
	 * @param @param targetRights
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean testRights(BigInteger sum,String targetRights){
		return testRights(sum,Integer.parseInt(targetRights));
	}
}
