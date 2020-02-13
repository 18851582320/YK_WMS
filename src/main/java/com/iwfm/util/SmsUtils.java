package com.iwfm.util;

import net.sf.json.JSONObject;

public class SmsUtils {
   
	private static String url = "https://open.ucpaas.com/ol/sms/sendsms";
	private static String sid = "0eed487f07cab0114a92923c54861e6a";
    private static String token = "8cde4a88d68ddf596682f4b56d878c06";
    private static String appid = "a975d016958c4bd5b03a1de06eac5080";
   
    
    /**
     * 
     * @Title: sendTaskSms
     * @Description: 发送任务通知消息
     * @author yk
     * @date: 2018年11月30日
     * @param: @param param
     * @param: @param mobile
     * @param: @return   
     * @return: String  
     * @throws:
     */
    public static String sendTaskSms(String param, String mobile) {
    	String result="";
    	
    	try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("templateid", "403766");
			jsonObject.put("param", param);
			jsonObject.put("mobile", mobile);
			jsonObject.put("uid",null);
			
			String body = jsonObject.toString();
			
			result = HttpClientUtil.postJson(url, body, null);
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
    	
    	return result;
    }
    
    
    /**
     * 
     * @Title: sendTaskSmsNoReceive
     * @Description: TODO
     * @author yk
     * @date: 2018年12月10日
     * @param: @param param
     * @param: @param mobile
     * @param: @return   
     * @return: String  
     * @throws:
     */
    public static String sendTaskSmsNoReceive(String param, String mobile) {
    	String result="";
    	
    	try {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("sid", sid);
    		jsonObject.put("token", token);
    		jsonObject.put("appid", appid);
    		jsonObject.put("templateid", "407771");
    		jsonObject.put("param", param);
    		jsonObject.put("mobile", mobile);
    		jsonObject.put("uid",null);
    		
    		String body = jsonObject.toString();
    		
    		result = HttpClientUtil.postJson(url, body, null);
    		
    	} catch (Exception e) {
    		//e.printStackTrace();
    	}
    	
    	return result;
    }
    
    
    /**
     * 
     * @Title: sendTaskSmsComplete
     * @Description: TODO
     * @author yk
     * @date: 2018年12月10日
     * @param: @param param
     * @param: @param mobile
     * @param: @return   
     * @return: String  
     * @throws:
     */
    public static String sendTaskSmsComplete(String param, String mobile) {
    	String result="";
    	
    	try {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("sid", sid);
    		jsonObject.put("token", token);
    		jsonObject.put("appid", appid);
    		jsonObject.put("templateid", "407772");
    		jsonObject.put("param", param);
    		jsonObject.put("mobile", mobile);
    		jsonObject.put("uid",null);
    		
    		String body = jsonObject.toString();
    		
    		result = HttpClientUtil.postJson(url, body, null);
    		
    	} catch (Exception e) {
    		//e.printStackTrace();
    	}
    	
    	return result;
    }
    
    
    /**
     * 
     * @Title: sendTaskSmsNoCheck
     * @Description: TODO
     * @author yk
     * @date: 2018年12月10日
     * @param: @param param
     * @param: @param mobile
     * @param: @return   
     * @return: String  
     * @throws:
     */
    public static String sendTaskSmsNoCheck(String param, String mobile) {
    	String result="";
    	
    	try {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("sid", sid);
    		jsonObject.put("token", token);
    		jsonObject.put("appid", appid);
    		jsonObject.put("templateid", "407774");
    		jsonObject.put("param", param);
    		jsonObject.put("mobile", mobile);
    		jsonObject.put("uid",null);
    		
    		String body = jsonObject.toString();
    		
    		result = HttpClientUtil.postJson(url, body, null);
    		
    	} catch (Exception e) {
    		//e.printStackTrace();
    	}
    	
    	return result;
    }
    
   
    
    
    /**
     * 
     * @Title: sendBugSms
     * @Description: TODO
     * @author yk
     * @date: 2018年11月30日
     * @param: @param param
     * @param: @param mobile
     * @param: @return   
     * @return: String  
     * @throws:
     */
    public static String sendBugSms(String param, String mobile) {
    	String result="";
    	
    	try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("templateid", "403769");
			jsonObject.put("param", param);
			jsonObject.put("mobile", mobile);
			jsonObject.put("uid",null);
			
			String body = jsonObject.toString();
			
			result = HttpClientUtil.postJson(url, body, null);
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
    	
    	return result;
    }
    
    /**
     * 
     * @Title: sendBaoXiaoSms
     * @Description: TODO
     * @author yk
     * @date: 2019年3月1日
     * @param: @param param
     * @param: @param mobile
     * @param: @return   
     * @return: String  
     * @throws:
     */
    public static String sendBaoXiaoSms(String param, String mobile) {
    	String result="";
    	
    	try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("templateid", "435182");
			jsonObject.put("param", param);
			jsonObject.put("mobile", mobile);
			jsonObject.put("uid",null);
			
			String body = jsonObject.toString();
			
			result = HttpClientUtil.postJson(url, body, null);
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
    	
    	return result;
    }
    
    
    
    /**
     * 
     * @Title: sendBugSms
     * @Description: TODO
     * @author yk
     * @date: 2019年1月17日
     * @param: @param param
     * @param: @param mobile
     * @param: @return   
     * @return: String  
     * @throws:
     */
    public static String sendSaleLead(String param, String mobile) {
    	String result="";
    	
    	try {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("sid", sid);
    		jsonObject.put("token", token);
    		jsonObject.put("appid", appid);
    		jsonObject.put("templateid", "423055");
    		jsonObject.put("param", param);
    		jsonObject.put("mobile", mobile);
    		jsonObject.put("uid",null);
    		
    		String body = jsonObject.toString();
    		
    		result = HttpClientUtil.postJson(url, body, null);
    		
    	} catch (Exception e) {
    		//e.printStackTrace();
    	}
    	
    	return result;
    }
    
    
    
    public static String sendCheckCode(String param, String mobile) {
    	String result="";
    	
    	try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("templateid", "413001");
			jsonObject.put("param", param);
			jsonObject.put("mobile", mobile);
			jsonObject.put("uid",null);
			
			String body = jsonObject.toString();
			
			result = HttpClientUtil.postJson(url, body, null);
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
    	
    	return result;
    }
    

}
