package com.iwfm.plugin.websocketOnline;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import net.sf.json.JSONObject;

import org.java_websocket.WebSocket;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * ClassName: OnlineChatServer 
 * @Description: 在线管理和发送消息等
 * @author yk
 * @date 2018年4月27日
 */
public class OnlineChatServer extends WebSocketServer{

	public OnlineChatServer(int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
	}

	public OnlineChatServer(InetSocketAddress address) {
		super(address);
	}

	/**
	 * @Title:onOpen
	 * @Description: 触发连接事件
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param conn
	 * @param @param handshake   
	 * @throws
	 */
	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {
		//this.sendToAll( "new connection: " + handshake.getResourceDescriptor() );
		//System.out.println("===" + conn.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	/**
	 * @Title:onClose
	 * @Description: 触发关闭事件
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param conn
	 * @param @param code
	 * @param @param reason
	 * @param @param remote   
	 * @throws
	 */
	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		userLeave(conn);
	}

	
	/**
	 * @Title:onMessage
	 * @Description: 客户端发送消息到服务器时触发事件
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param conn
	 * @param @param message   
	 * @throws
	 */
	@Override
	public void onMessage(WebSocket conn, String message){
		message = message.toString();
		
		if(null != message && message.startsWith("[join]")){
			this.userjoin(message.replaceFirst("\\[join\\]", ""),conn);
		}else if(null != message && message.startsWith("[goOut]")){
			this.goOut(message.replaceFirst("\\[goOut\\]", ""));
		}else if(null != message && message.startsWith("[senSmsNoMsg]")){
			this.senSmsNoMsg(message.replaceFirst("\\[senSmsNoMsg\\]", ""));
		}else if(null != message && message.startsWith("[senSmsNoMsgNoTitle]")){
			this.senSmsNoMsgNoTitle(message.replaceFirst("\\[senSmsNoMsgNoTitle\\]", ""));
		}else if(null != message && message.startsWith("[senSmsWithMsg]")){
			message=message.replaceFirst("\\[senSmsWithMsg\\]","");
			String userMsg[]=message.split("\\[IWFMPEPS\\]");
			this.senSmsWithMsg(userMsg[0],userMsg[1]);
		}else if(null != message && message.startsWith("[task]")){
			this.senTask(message.replaceFirst("\\[task\\]", ""));
		}else if(null != message && message.startsWith("[leave]")){
			this.userLeave(conn);
		}else if(null != message && message.startsWith("[count]")){
			this.getUserCount(conn);
		}else if(null != message && message.startsWith("[onLineUserList]")){
			OnlineChatServerPool.setAdmin(conn);
			this.getUserList();
		}else{
			OnlineChatServerPool.sendMessageToUser(conn, message);//同时向本人发送消息
		}
	}

	public void onFragment( WebSocket conn, Framedata fragment ) {
	}

	/**
	 * @Title:onError
	 * @Description: 触发异常事件
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param conn
	 * @param @param ex   
	 * @throws
	 */
	@Override
	public void onError( WebSocket conn, Exception ex ) {
		//ex.printStackTrace();
		if( conn != null ) {
			//some errors like port binding failed may not be assignable to a specific websocket
		}
	}

	/**
	 * @Title:userjoin
	 * @Description: 用户登录处理
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param user
	 * @param @param conn   
	 * @return void  
	 * @throws
	 */
	public void userjoin(String user, WebSocket conn){
		onlineMaganger(1,user,conn);
	}
	
	
	/**
	 * @Title:senSmsNoMsg
	 * @Description: 通知某人，只是提醒，没有消息
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param user   
	 * @return void  
	 * @throws
	 */
	public void senSmsNoMsg(String user){
		JSONObject result = new JSONObject();
		result.element("type", "senSmsNoMsg");
		OnlineChatServerPool.sendMessageToUser(OnlineChatServerPool.getWebSocketByUser(user),result.toString());	
	}
	
	/**
	 * 
	 * @Title: senSmsNoMsgNoTitle
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年11月7日
	 * @param: @param user   
	 * @return: void  
	 * @throws:
	 */
	public void senSmsNoMsgNoTitle(String user){
		JSONObject result = new JSONObject();
		result.element("type", "senSmsNoMsgNoTitle");
		OnlineChatServerPool.sendMessageToUser(OnlineChatServerPool.getWebSocketByUser(user),result.toString());	
	}
	
	/**
	 * @Title:senSmsWithMsg
	 * @Description: 通知某人，包含提醒的消息
	 * @author yk
	 * @date 2018年4月27日
	 * @param    
	 * @return void  
	 * @throws
	 */
	public void senSmsWithMsg(String user,String msg){
		JSONObject result = new JSONObject();
		result.element("type", "senSmsWithMsg");
		result.element("msg", msg);
		OnlineChatServerPool.sendMessageToUser(OnlineChatServerPool.getWebSocketByUser(user),result.toString());
	}
	
	/**
	 * @Title:senTask
	 * @Description: 新待办任务提醒
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param user   
	 * @return void  
	 * @throws
	 */
	public void senTask(String user){
		JSONObject result=new JSONObject();
		result.element("type","task");
		WebSocket ws=OnlineChatServerPool.getWebSocketByUser(user);
		if(ws!=null){//当前代办人是具体的人时发送给起任务通知
			result.element("RNUMBER","no");
			OnlineChatServerPool.sendMessageToUser(ws, result.toString());
		}else{//当代办人不在线或者代办人是角色是发给所有的在线用户，客户端去过滤出所拥有起角色的用户，从而获得新任务的通知
			result.element("RNUMBER", user);
			OnlineChatServerPool.sendMessage(result.toString());
		}
	}
	
	/**
	 * @Title:goOut
	 * @Description: 强制默认下线
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param user   
	 * @return void  
	 * @throws
	 */
	public void goOut(String user){
		this.goOut(OnlineChatServerPool.getWebSocketByUser(user),"thegoout");	
	}
	
	
	/**
	 * @Title:goOut
	 * @Description: 强制用户下线
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param conn
	 * @param @param type   
	 * @return void  
	 * @throws
	 */
	public void goOut(WebSocket conn,String type){
		JSONObject result = new JSONObject();
		result.element("type", type);
		result.element("msg", "goOut");
		OnlineChatServerPool.sendMessageToUser(conn,result.toString());	
	}
	
	/**
	 * @Title:userLeave
	 * @Description: 用户下线处理
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param conn   
	 * @return void  
	 * @throws
	 */
	public void userLeave(WebSocket conn){
		onlineMaganger(2,null,conn);
	}

	/**
	 * @Title:getUserCount
	 * @Description: 获取用户在线总数
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param conn   
	 * @return void  
	 * @throws
	 */
	public void getUserCount(WebSocket conn){
		JSONObject result = new JSONObject();
		result.element("type", "count");
		result.element("msg", OnlineChatServerPool.getUserCount());
		OnlineChatServerPool.sendMessageToUser(conn,result.toString());					
	}
	
	/**
	 * @Title:getUserList
	 * @Description: 获取用户在线列表
	 * @author yk
	 * @date 2018年4月27日
	 * @param    
	 * @return void  
	 * @throws
	 */
	public void getUserList(){
		WebSocket conn =  OnlineChatServerPool.getAdmin();
		if(null == conn){return;}
		JSONObject result = new JSONObject();
		result.element("type", "userlist");
		result.element("list", OnlineChatServerPool.getOnlineUser());
		OnlineChatServerPool.sendMessageToUser(conn,result.toString());					
	}
	
	/**
	 * @Title:onlineMaganger
	 * @Description: 用户上下线管理
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param type 1：上线；2：下线
	 * @param @param user
	 * @param @param conn   
	 * @return void  
	 * @throws
	 */
	public synchronized void onlineMaganger(int type,String user, WebSocket conn){
		if(type == 1){
			if(null == OnlineChatServerPool.getWebSocketByUser(user)){		//判断用户是否在其它终端登录
				OnlineChatServerPool.addUser(user,conn);					//向连接池添加当前的连接对象
				addUserToAdmin(user);
			}else{
				//goOut(conn,"goOut"); //不能重复登录
				
				/* 挤掉对方 */
				goOut(user);		
				OnlineChatServerPool.addUser(user,conn);					
				addUserToAdmin(user);
			}
		}else{
			OnlineChatServerPool.removeUser(conn);							//在连接池中移除连接
			this.getUserList();
		}
	}
	
	/**
	 * @Title:addUserToAdmin
	 * @Description: 有用户登录系统,加入在线列表
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param user   
	 * @return void  
	 * @throws
	 */
	public void addUserToAdmin(String user){
		WebSocket conn =  OnlineChatServerPool.getAdmin();
		if(null == conn){return;}
		JSONObject result = new JSONObject();
		result.element("type", "addUser");
		result.element("user", user);
		OnlineChatServerPool.sendMessageToUser(conn,result.toString());	
	}
	
}

