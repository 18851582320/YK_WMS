package com.iwfm.util;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.iwfm.entity.system.Menu;
import com.iwfm.util.Const;
import com.iwfm.util.RightsHelper;


/**
 * ClassName: Jurisdiction 
 * @Description: 权限处理
 * @author yk
 * @date 2017年8月1日
 */
public class Jurisdiction {
	
	/**
	 * @Title: hasJurisdiction
	 * @Description: TODO
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param menuUrl
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasJurisdiction(String menuUrl) {
		//判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
		/**
		 * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
		 * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
		 */
		String USERNAME=getUsername();//获取当前登录这用户名
		Session session=getSession();
		List<Menu> menuList=(List<Menu>)session.getAttribute(USERNAME+Const.SESSION_allmenuList);
		return readMenu(menuList,menuUrl,session,USERNAME);
	}
	
	
	/**
	 * @Title: readMenu
	 * @Description: 校验菜单权限并初始按钮权限用于页面按钮显示与否(递归处理)
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param menuList:传入的总菜单(设置菜单时，.do前面的不要重复)
	 * @param @param menuUrl:菜单路径  
	 * @param @param session
	 * @param @param USERNAME
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean readMenu(List<Menu> menuList,String menuUrl,Session session,String USERNAME){
		for(int i=0;i<menuList.size();i++){
			if(menuList.get(i).getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])){ //访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)
				if(!menuList.get(i).isHasMenu()){				//判断有无此菜单权限
					return false;
				}else{											//按钮判断
					/*
					Map<String, String> map = (Map<String, String>)session.getAttribute(USERNAME + Const.SESSION_QX);//按钮权限(增删改查)
					map.remove("add");
					map.remove("del");
					map.remove("edit");
					map.remove("cha");
					
					String MENU_ID =  menuList.get(i).getMENU_ID();
					Boolean isAdmin = "admin".equals(USERNAME);
					
					map.put("add", (RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin?"1":"0");
					map.put("del", RightsHelper.testRights(map.get("dels"), MENU_ID) || isAdmin?"1":"0");
					map.put("edit", RightsHelper.testRights(map.get("edits"), MENU_ID) || isAdmin?"1":"0");
					map.put("cha", RightsHelper.testRights(map.get("chas"), MENU_ID) || isAdmin?"1":"0");
					session.removeAttribute(USERNAME + Const.SESSION_QX);
					session.setAttribute(USERNAME + Const.SESSION_QX, map);	//重新分配按钮权限
					*/
					return true;
				}
			}else{
				if(!readMenu(menuList.get(i).getSubMenu(),menuUrl,session,USERNAME)){//继续排查其子菜单
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @Title: buttonJurisdiction
	 * @Description: 按钮权限(方法中校验)
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param menuUrl:菜单路径
	 * @param @param type:类型(add、del、edit、cha)
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static boolean buttonJurisdiction(String menuUrl, String type){
		//判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
		/**
		 * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
		 * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
		 */
		String USERNAME = getUsername();	//获取当前登录者loginname
		Session session = getSession();
		List<Menu> menuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList); //获取菜单列表
		return readMenuButton(menuList,menuUrl,session,USERNAME,type);
	}
	
	/**
	 * @Title: readMenuButton
	 * @Description: 校验按钮权限(递归处理)
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param menuList:传入的总菜单(设置菜单时，.do前面的不要重复)
	 * @param @param menuUrl:菜单路径
	 * @param @param session
	 * @param @param USERNAME
	 * @param @param type
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static boolean readMenuButton(List<Menu> menuList,String menuUrl,Session session,String USERNAME, String type){
		for(int i=0;i<menuList.size();i++){
			if(menuList.get(i).getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])){ //访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)
				if(!menuList.get(i).isHasMenu()){				//判断有无此菜单权限
					return false;
				}else{											//按钮判断
					Map<String, String> map = (Map<String, String>)session.getAttribute(USERNAME + Const.SESSION_QX);//按钮权限(增删改查)
					String MENU_ID =  menuList.get(i).getMENU_ID();
					Boolean isAdmin = "admin".equals(USERNAME);
					if("add".equals(type)){
						return ((RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin);
					}else if("del".equals(type)){
						return ((RightsHelper.testRights(map.get("dels"), MENU_ID)) || isAdmin);
					}else if("edit".equals(type)){
						return ((RightsHelper.testRights(map.get("edits"), MENU_ID)) || isAdmin);
					}else if("cha".equals(type)){
						return ((RightsHelper.testRights(map.get("chas"), MENU_ID)) || isAdmin);
					}
				}
			}else{
				if(!readMenuButton(menuList.get(i).getSubMenu(),menuUrl,session,USERNAME,type)){//继续排查其子菜单
					return false;
				};
			}
		}
		return true;
	}
	
	
	/**
	 * @Title: getUsername
	 * @Description: 获取当前登录的用户名
	 * @author yk
	 * @date 2017年8月1日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getUsername(){
		return getSession().getAttribute(Const.SESSION_USERNAME).toString();
	}
	
	/**
	 * @Title:getUserId
	 * @Description: 获取当前登录的id
	 * @author yk
	 * @date 2018年4月18日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getUserId(){
		return getSession().getAttribute(Const.SESSION_USER_ID).toString();
	}
	
	/**
	 * 
	 * @Title: getPerformance
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年11月15日
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	public static String getPerformance(){
		return getSession().getAttribute(Const.Performance).toString();
	}
	
	
	/**
	 * @Title:getSTAFF_ID
	 * @Description: 获取员工的id
	 * @author yk
	 * @date 2018年2月24日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getSTAFF_ID(){
		return getSession().getAttribute(Const.STAFF_ID).toString();
	}
	
	/**
	 * @Title:getSTAFF_NAME
	 * @Description: 获取员工姓名
	 * @author yk
	 * @date 2018年2月24日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getSTAFF_NAME(){
		return getSession().getAttribute(Const.STAFF_NAME).toString();
	}
	
	/**
	 * @Title:getSTAFF_DEPARTMENT_ID
	 * @Description: 获取员工所属部门id
	 * @author yk
	 * @date 2018年2月24日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getSTAFF_DEPARTMENT_ID(){
		return getSession().getAttribute(Const.STAFF_DEPARTMENT_ID).toString();
	}
	
	/**
	 * @Title:getSTAFF_DEPARTMENT_NAME
	 * @Description: 获取员工所属部门名称
	 * @author yk
	 * @date 2018年2月24日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getSTAFF_DEPARTMENT_NAME(){
		return getSession().getAttribute(Const.STAFF_DEPARTMENT_NAME).toString();
	}
	
	/**
	 * @Title:getRnumbers
	 * @Description: 获取角色编码
	 * @author yk
	 * @date 2018年3月27日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getRolenumbers(){
		return getSession().getAttribute(Const.SESSION_RoleNUMBERS).toString();
	}
	
	/**
	 * @Title: getDEPARTMENT_IDS
	 * @Description: 获取用户的最高组织机构权限集合
	 * @author yk
	 * @date 2017年8月1日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getDEPARTMENT_IDS(){
		return getSession().getAttribute(Const.DEPARTMENT_IDS).toString();
	}
	
	/**
	 * @Title: getDEPARTMENT_ID
	 * @Description: 获取用户的最高组织机构权限
	 * @author yk
	 * @date 2017年8月1日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getDEPARTMENT_ID(){
		return getSession().getAttribute(Const.DEPARTMENT_ID).toString();
	}
	
	/**
	 * @Title: getHC
	 * @Description: 获取当前按钮权限(增删改查)
	 * @author yk
	 * @date 2017年8月1日
	 * @param @return
	 * @return Map<String,String>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getHC(){
		return (Map<String, String>)getSession().getAttribute(getUsername() + Const.SESSION_QX);
	}
	
	
	/**
	 * @Title: getSession
	 * @Description: shiro管理的session
	 * @author yk
	 * @date 2017年8月1日
	 * @param @return
	 * @return Session
	 * @throws
	 */
	public static Session getSession(){
		return SecurityUtils.getSubject().getSession();
	}
	/**
	 * 
	 * @Title:getStation_ID
	 * @Description: 取得工位id
	 * @author yk
	 * @date 2018年4月8日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getStation_ID(){
		return getSession().getAttribute(Const.STATION_ID).toString();
	}
	
	public static String getLanguage(){
		return getSession().getAttribute(Const.SESSION_language).toString();
	}
	
}
