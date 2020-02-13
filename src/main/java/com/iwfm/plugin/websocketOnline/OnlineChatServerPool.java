package com.iwfm.plugin.websocketOnline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.java_websocket.WebSocket;

/**
 * ClassName: OnlineChatServerPool 
 * @Description: 在线管理和发送消息等
 * @author yk
 * @date 2018年4月27日
 */
public class OnlineChatServerPool {

	private static final Map<WebSocket,String> userconnections = new HashMap<WebSocket,String>();
	
	private static WebSocket admin = null;;
	
	/**
	 * @Title:getUserByKey
	 * @Description: 获取用户名
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param conn
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getUserByKey(WebSocket conn){
		return userconnections.get(conn);
	}
	
	/**
	 * @Title:getUserCount
	 * @Description: 获取在线总数
	 * @author yk
	 * @date 2018年4月27日
	 * @param @return   
	 * @return int  
	 * @throws
	 */
	public static int getUserCount(){
		return userconnections.size();
	}
	
	/**
	 * @Title:getWebSocketByUser
	 * @Description: 获取WebSocket
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param user
	 * @param @return   
	 * @return WebSocket  
	 * @throws
	 */
	public static WebSocket getWebSocketByUser(String user){
		Set<WebSocket> keySet = userconnections.keySet();
		synchronized (keySet) {
			for (WebSocket conn : keySet) {
				String cuser = userconnections.get(conn);
				if(cuser.equals(user)){
					return conn;
				}
			}
		}
		return null;
	}
	
	/**
	 * @Title:addUser
	 * @Description: 向连接池中添加连接
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param user
	 * @param @param conn   
	 * @return void  
	 * @throws
	 */
	public static void addUser(String user, WebSocket conn){
		userconnections.put(conn,user);	//添加连接
	}
	
	/**
	 * @Title:getOnlineUser
	 * @Description: 获取所有的在线用户
	 * @author yk
	 * @date 2018年4月27日
	 * @param @return   
	 * @return Collection<String>  
	 * @throws
	 */
	public static Collection<String> getOnlineUser(){
		List<String> setUsers = new ArrayList<String>();
		Collection<String> setUser = userconnections.values();
		for(String u:setUser){
			setUsers.add(u);
		}
		return setUsers;
	}
	
	/**
	 * @Title:removeUser
	 * @Description: 移除连接池中的连接
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param conn
	 * @param @return   
	 * @return boolean  
	 * @throws
	 */
	public static boolean removeUser(WebSocket conn){
		if(userconnections.containsKey(conn)){
			userconnections.remove(conn);	//移除连接
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @Title:sendMessageToUser
	 * @Description: 向特定的用户发送数据
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param conn
	 * @param @param message   
	 * @return void  
	 * @throws
	 */
	public static void sendMessageToUser(WebSocket conn,String message){
		if(null != conn){
			conn.send(message);
		}
	}
	
	/**
	 * @Title:sendMessage
	 * @Description: 向所有的用户发送消息
	 * @author yk
	 * @date 2018年4月27日
	 * @param @param message   
	 * @return void  
	 * @throws
	 */
	public static void sendMessage(String message){
		Set<WebSocket> keySet = userconnections.keySet();
		synchronized (keySet) {
			for (WebSocket conn : keySet) {
				String user = userconnections.get(conn);
				if(user != null){
					conn.send(message);
				}
			}
		}
	}

	public static WebSocket getAdmin() {
		return admin;
	}

	public static void setAdmin(WebSocket admin) {
		OnlineChatServerPool.admin = admin;
	}
}
