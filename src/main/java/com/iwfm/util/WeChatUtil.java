package com.iwfm.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import net.sf.json.JSONObject;

/**
 * ClassName: WeChatUtil 
 * @Description: 微信管理工具
 * @author yk
 * @date 2018年5月9日
 */
public class WeChatUtil {
	public static final String access_token_url="https://api.weixin.qq.com/cgi-bin/token?" + "grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static final String guanZhu_url="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=";
	public static final String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	/**
	 * @Title:getAccessToken
	 * @Description: 获取公众号的全局唯一接口调用凭证
	 * @author yk
	 * @date 2018年5月9日
	 * @param @param appid  第三方用户唯一凭证
	 * @param @param appsecret  第三方用户唯一凭证密钥
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getAccess_Token(String appid,String appsecret){
		String access_token="";
		try {
			String requestUrl=access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
			JSONObject jsonObject=httpRequest(requestUrl, "GET",null);
			if(jsonObject!=null){
				access_token=jsonObject.getString("access_token");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return access_token;
	}
	
	/**
	 * @Title:httpRequest
	 * @Description: TODO
	 * @author yk
	 * @date 2018年5月9日
	 * @param @param requestUrl
	 * @param @param requsetMethod
	 * @param @param outPutStr
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 */
	public static JSONObject httpRequest(String requestUrl,String requestMethod,String outPutStr){
		JSONObject jsonObject=null;
		StringBuffer buffer=new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager [] tm={new MyX509TrustManager()};
			SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
			sslcontext.init(null, tm, new java.security.SecureRandom());
			
			// 从上述SSLContext对象中得到SSLSocktFactory对象
			SSLSocketFactory ssf = sslcontext.getSocketFactory();
			
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outPutStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outPutStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream=httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader=new InputStreamReader(inputStream, "utf-8");//从字节流转换成字符流
			BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
			String str=null;
			while((str=bufferedReader.readLine())!=null){
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce ) {
			// TODO: handle exception
		}catch(Exception e){
			
		}
		
		return jsonObject;
	}
	
}
