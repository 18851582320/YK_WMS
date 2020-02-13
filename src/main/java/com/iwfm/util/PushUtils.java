package com.iwfm.util;

import java.net.UnknownHostException;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.iwfm.plugin.websocketOnline.OnlineChatServer;

public class PushUtils {
	 //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    private static String appId = "pDdyNVs2mpALbfEWd4FDE7";
    private static String appKey = "QvHuwA0zQV9E8iXXQaLrC5";
    private static String masterSecret = "Zy7QaaEget7Gqn9vYsayl4";

   
    //别名推送方式
    // static String Alias = "";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    /**
     * 
     * @Title: PushtoSingle
     * @Description: 推送消息
     * @author: HB-PC-042
     * @date: 2018年10月26日
     * @param: @param token
     * @param: @param clientid
     * @param: @param title
     * @param: @param content
     * @param: @return   
     * @return: String  
     * @throws:
     */
    public static String PushtoSingle(String token,String clientid,String title,String content,String taskId) {
    	IGtPush push = new IGtPush(host, appKey, masterSecret);
       // LinkTemplate template = linkTemplateDemo(title,content);
    	NotificationTemplate template = notificationTemplateDemo(title,content,taskId);
        SingleMessage message = new SingleMessage();
        String rtn="";
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(clientid);
        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            //e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
            rtn=ret.getResponse().get("result").toString();
        } else {
            System.out.println("服务器响应异常");
            rtn="error";
        }
        
        return rtn;
        
    }
    
    
    /**
     * 
     * @Title: PushLinktoSingle
     * @Description: 推送连接消息
     * @author: HB-PC-042
     * @date: 2018年10月26日
     * @param: @param token
     * @param: @param clientid
     * @param: @param title
     * @param: @param content
     * @param: @return   
     * @return: String  
     * @throws:
     */
    public static String PushLinktoSingle(String token,String clientid,String title,String content,String taskId) {
    	IGtPush push = new IGtPush(host, appKey, masterSecret);
        LinkTemplate template = linkTemplateDemo(title,content,taskId);
        SingleMessage message = new SingleMessage();
        String rtn="";
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(clientid);
        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            //e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
            rtn=ret.getResponse().get("result").toString();
        } else {
            System.out.println("服务器响应异常");
            rtn="error";
        }
        
        return rtn;
        
    }
    
    /**
     * 
     * @Title: sendToPc
     * @Description: TODO
     * @author: HB-PC-042
     * @date: 2018年10月31日
     * @param: @param UserName   
     * @return: void  
     * @throws:
     */
    public static void sendToPc(String UserName) {
    	OnlineChatServer s;
		String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);//读取WEBSOCKET配置,获取端口配置
		if(null != strWEBSOCKET && !"".equals(strWEBSOCKET)){
			String strIW[] = strWEBSOCKET.split(",IWFM,");
			if(strIW.length == 5){
				try {
					s = new OnlineChatServer(Integer.parseInt(strIW[3]));
					s.start();
					s.senSmsWithMsg(UserName,"您有新的任务请注意查收！");
				} catch (NumberFormatException e) {
					
				} catch (UnknownHostException e) {
					
				}
			}
		}
    }
    
    
    /**
     * 
     * @Title: sendToPcSMS
     * @Description: TODO
     * @author yk
     * @date: 2019年2月27日
     * @param: @param UserName   
     * @return: void  
     * @throws:
     */
    public static void sendToPcSMS(String UserName) {
    	OnlineChatServer s;
		String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);//读取WEBSOCKET配置,获取端口配置
		if(null != strWEBSOCKET && !"".equals(strWEBSOCKET)){
			String strIW[] = strWEBSOCKET.split(",IWFM,");
			if(strIW.length == 5){
				try {
					s = new OnlineChatServer(Integer.parseInt(strIW[3]));
					s.start();
					s.senSmsWithMsg(UserName,"您有新的消息请注意查看！");
				} catch (NumberFormatException e) {
					
				} catch (UnknownHostException e) {
					
				}
			}
		}
    }
    
    
    /**
     * 
     * @Title: sendToPcRefresh
     * @Description: TODO
     * @author: HB-PC-042
     * @date: 2018年11月7日
     * @param: @param UserName   
     * @return: void  
     * @throws:
     */
    public static void sendToPcRefresh(String UserName) {
    	OnlineChatServer s;
		String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);//读取WEBSOCKET配置,获取端口配置
		if(null != strWEBSOCKET && !"".equals(strWEBSOCKET)){
			String strIW[] = strWEBSOCKET.split(",IWFM,");
			if(strIW.length == 5){
				try {
					s = new OnlineChatServer(Integer.parseInt(strIW[3]));
					s.start();
					s.senSmsNoMsgNoTitle(UserName);
				} catch (NumberFormatException e) {
					
				} catch (UnknownHostException e) {
					
				}
			}
		}
    }
    
    
    public static void main(String[] args) throws Exception {
    	/*IGtPush push = new IGtPush(host, appKey, masterSecret);
        LinkTemplate template = linkTemplateDemo();
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId("eb15cc984bb6e2f9cb2e57adb1dc2131");
        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            //System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }*/
    	
    	
    	String rtn= PushtoSingle("","eb15cc984bb6e2f9cb2e57adb1dc2131","新的任务提醒测试","测试","04e44474faf246dc98ac31b279d34125");
    	System.out.println(rtn);
    }
    
    
    public static LinkTemplate linkTemplateDemo(String title,String content,String taskId) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(content);
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        // 设置打开的网址地址
       template.setUrl("http://www.baidu.com");
        return template;
    }
    
    
    public static NotificationTemplate notificationTemplateDemo(String title,String content,String taskId) {
        
        String pushText="{title:'"+title+"',content:'"+content+"',payload:'{'type':'task','taskId':'"+taskId+"'}'}";
        
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent(pushText);  
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(content);
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        return template;

    }
    
    

}
