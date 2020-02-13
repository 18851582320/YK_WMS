package com.iwfm.controller.system.login;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.controller.base.BaseController;
import com.iwfm.entity.system.Menu;
import com.iwfm.entity.system.User;
import com.iwfm.service.oa.staff.StaffManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.menu.MenuManager;
import com.iwfm.service.system.role.RoleManager;
import com.iwfm.service.system.user.UserManager;
import com.iwfm.service.system.userRole.UserRoleManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.Const;
import com.iwfm.util.DateUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.RightsHelper;
import com.iwfm.util.Tools;

/**
 * ClassName: LoginController 
 * @Description: 系统登录总入口
 * @author yk
 * @date 2017年8月2日
 */

@Controller
public class LoginController extends BaseController{
	
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="roleService")
	private RoleManager roleService;
	private LogManager logService;
	@Resource(name="staffService")
	private StaffManager staffService;
	@Resource(name="userRoleService")
	private UserRoleManager userRoleService;
	
	/**
	 * @Title: toLogin
	 * @Description: 访问登录主页
	 * @author yk
	 * @date 2017年8月2日
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/login_toLogin")
	public ModelAndView toLogin()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd=this.setLoginPd(pd);
		mv.setViewName("system/index/login");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * @Title:toLogin
	 * @Description: 出错
	 * @author yk
	 * @date 2018年4月25日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/login_errorCode")
	public ModelAndView login_errorCode()throws Exception{
		ModelAndView mv=this.getModelAndView();
		mv.setViewName("system/index/error");
		return mv;
	}
	
	
	
	
	/**
	 * @Title: login
	 * @Description: 登陆验证
	 * @author yk
	 * @date 2017年8月2日
	 * @param @return
	 * @return Object
	 * @throws
	 */
	@RequestMapping(value="/login_login",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object login(){
		Map<String,String > map=new HashMap<String,String>();
		PageData pd=new PageData();
		PageData pd2=new PageData();
		pd=this.getPageData();
		try {
			String rtnInfo="";
			
			String USERNAME = pd.getString("loginname"); // 登录用户名
			String PASSWORD = pd.getString("password"); // 登录密码
			
			if(StringUtils.isNotEmpty(USERNAME) && StringUtils.isNotEmpty(PASSWORD)){
				Session session=Jurisdiction.getSession();//获取shiro管理的session
				
				pd.put("USERNAME", USERNAME);
				String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString(); // 密码加密
				
				pd.put("PASSWORD", passwd);
				
				pd = userService.getUserByNameAndPwd(pd); //根据用户名和密码去读取用户信息
				if (pd != null) {
					
					pd2.put("USERNAME", USERNAME);
					pd2=staffService.findByUSERNAME(pd2);
					if(pd2!=null && pd2.size()>0){
						session.setAttribute(Const.STAFF_ID,pd2.getString("STAFF_ID"));                      //把员工id放session中
						session.setAttribute(Const.STAFF_NAME,pd2.getString("NAME"));                        //把员工姓名放session中
						session.setAttribute(Const.STAFF_DEPARTMENT_ID,pd2.getString("DEPARTMENT_ID"));      //把员工所在部门id放session中
						session.setAttribute(Const.STAFF_DEPARTMENT_NAME,pd2.getString("DEPARTMENT_NAME"));  //把员工所在部门名称放session中
						
						pd.put("LAST_LOGIN", DateUtil.getTime().toString());
						userService.updateLastLogin(pd);
						
						User user = new User();
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));
						user.setROLE_ID(pd.getString("ROLE_ID"));
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						user.setUserLanguage(pd.getString("userLanguage"));
						
						session.setAttribute(Const.SESSION_USER, user); // 把用户信息放session中
						session.setAttribute(Const.SESSION_USER_ID,pd.getString("USER_ID"));
						
						
						// shiro加入身份验证
						Subject subject = SecurityUtils.getSubject();
						UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
						try {
							subject.login(token);
						} catch (AuthenticationException e) {
							token.clear();
							rtnInfo = "身份验证失败！";
						}
						
					}else{
						rtnInfo = "stafferror"; //未绑定员工
					}
					
				} else {
					rtnInfo = "usererror"; // 用户名或密码有误
					logBefore(logger, USERNAME + "登录系统密码或用户名错误");
				}
				if (Tools.isEmpty(rtnInfo)) {
					rtnInfo = "success"; // 验证成功
					logBefore(logger, USERNAME + "登录系统");
				}

				map.put("result", rtnInfo);
				return AppUtil.returnObject(new PageData(), map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put("result", "expError");
			map.put("errorInfo", e.getMessage());
		}
		
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	/**
	 * @Title: login_index
	 * @Description: 访问系统主页
	 * @author yk
	 * @date 2017年8月9日
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/main/index")
	public ModelAndView login_index(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if(user!=null){
				String USERNAME = user.getUSERNAME();
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);
				
				//设置国际化语言
				if(StringUtils.isNotEmpty(user.getUserLanguage())){
					session.setAttribute(Const.SESSION_language,user.getUserLanguage());
				}else{
					session.setAttribute(Const.SESSION_language,"zh_CN");
				}
				
				pd=this.getPageData();
				pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
				mv.addObject("pd",pd);
			}else{
				mv.setViewName("system/index/login");
			}
		} catch(Exception e){
			mv.setViewName("system/index/login");
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/index/main");
		return mv;
	}
	
	
	/**
	 * @Title:main_home
	 * @Description: 访问首页
	 * @author yk
	 * @date 2018年4月12日
	 * @param @return   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/main/home")
	public ModelAndView main_home(){
		ModelAndView mv=this.getModelAndView();
		
		//是否启用绩效管理
		String performance=Jurisdiction.getPerformance();
		mv.addObject("performance",performance);
		
		
		mv.setViewName("system/index/home");
		return mv;
	}
	
	
	

	/**
	 * @Title: getAttributeMenu
	 * @Description:菜单缓存
	 * @author yk
	 * @date 2017年8月15日
	 * @param @param session
	 * @param @param USERNAME
	 * @param @param roleRights
	 * @param @return
	 * @param @throws Exception
	 * @return List<Menu>
	 * @throws
	 */
	public List<Menu> getAttributeMenu(Session session, String USERNAME,List<PageData> userRoleLst,String MENU_TYPE) throws Exception{
		List<Menu> allmenuList = new ArrayList<Menu>();
		
		PageData pd=new PageData();
		pd.put("parentId","0");
		pd.put("MENU_TYPE",MENU_TYPE);
		
		allmenuList = menuService.listPlatMenuByMenuType(pd);					//获取所有菜单（MENU_TYPE:1 MES平台，2：工位机）
		if(userRoleLst!=null && userRoleLst.size()>0){
			allmenuList = this.readMenu(allmenuList, userRoleLst,USERNAME);				//根据角色权限获取本权限的菜单列表
		}
		session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);//菜单权限放入session中
		return allmenuList;
	}
	
	/**
	 * @Title: login_menu
	 * @Description: TODO
	 * @author yk
	 * @date 2017年8月10日
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/main/menu")
	@ResponseBody
	public String login_menu() {
		
		PageData pd=new PageData();
		
		JSONArray jsonAry=new JSONArray();
		try {
			
			pd=this.getPageData();
			String MENU_TYPE=pd.getString("MENU_TYPE");//菜单分类：1 MES平台  2：工位机
			
			Session session=Jurisdiction.getSession();
			User user=(User)session.getAttribute(Const.SESSION_USER);
			
			if(user!=null){
				String USERNAME = user.getUSERNAME();
				
				//获取角色信息（一个用户可以拥有多个角色）
				PageData pdRole=new PageData();
				pdRole.put("userId", user.getUSER_ID());
				List<PageData> userRoleLst=userRoleService.listAllByUserId(pdRole);
				if(userRoleLst!=null && userRoleLst.size()>0){
					String RNUMBER="";
					for(int i=0;i<userRoleLst.size();i++){
						pdRole=userRoleLst.get(i);
						if(i==0){
							RNUMBER=pdRole.getString("RNUMBER");
						}else{
							RNUMBER=RNUMBER+pdRole.getString("RNUMBER");
						}
					}
					session.setAttribute(Const.SESSION_RoleNUMBERS,pdRole.get("RNUMBER")); //角色编码
					session.setAttribute(Const.SESSION_ROLE_RIGHTS,userRoleLst); //用户角色信息
				}
				
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);						//放入用户名到session
				
				
				String performance=""; //是否启用绩效管理
				String MENU_ID="";
				String PerformanceSet = Tools.readTxtFile(Const.PerformanceSet);//读取WEBSOCKET配置
				if(StringUtils.isNotEmpty(PerformanceSet)){
					String strIW[] = PerformanceSet.split("=");
					if(strIW.length == 2){
						performance=strIW[1];
						session.setAttribute(Const.Performance, performance);						//放入用户名到session
					}
				}
				
				
				List<Menu> allmenuList = new ArrayList<Menu>();	
				allmenuList = this.getAttributeMenu(session, USERNAME, userRoleLst,MENU_TYPE);			//菜单缓存
				
				this.setLoginIp(USERNAME);	//更新登录IP
				
				if(allmenuList!=null && allmenuList.size()>0){
					Menu menu=null;
					
					JSONArray jArr=new JSONArray();
					JSONObject jo=null;
					
					
					for(int i=0;i<allmenuList.size();i++){
						menu=allmenuList.get(i);
						
						//不启用绩效管理，有些菜单不显示
						MENU_ID=menu.getMENU_ID();
						if(performance.equals("noStartUse")) {
							if(MENU_ID.equals("394") || MENU_ID.equals("397") || MENU_ID.equals("398") || MENU_ID.equals("407")  || MENU_ID.equals("410")  || MENU_ID.equals("411")  || MENU_ID.equals("414")  || MENU_ID.equals("415")) {
								continue;
							}
						}
						
						
						if(menu.isHasMenu() && menu.getMENU_STATE().equals("1")){//当前人员有权限
							jo=new JSONObject();
							jo.put("id",menu.getMENU_ID());
							jo.put("leaf", false);
							jo.put("parent", "root");
							jo.put("text",menu.getMENU_NAME());
							jo.put("iconfont",menu.getMENU_ICON());
							
							if(menu.getSubMenu()!=null){//有子菜单
								jo.put("responseText",resolveTwoLevelMenu(menu.getSubMenu(),menu.getMENU_ID()));
								jArr = (JSONArray) jo.get("responseText");
								if(jArr!=null && jArr.size()!=0){
									jsonAry.add(jo);
								}
							}
							
						}
					}
				}
			}
			else{
				return "userNull";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "error";
		}
		
		return jsonAry.toString();
	}
	
	/**
	 * @Title: logout
	 * @Description: 退出
	 * @author yk
	 * @date 2017年8月21日
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	@RequestMapping(value="/logout",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object logout(){
		Map<String,String > map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			String USERNAME = Jurisdiction.getUsername();	//当前登录的用户名
			logBefore(logger, USERNAME+"退出系统");
			logService.save(USERNAME, "退出");
			this.removeSession(USERNAME);//请缓存
			//shiro销毁登录
			Subject subject = SecurityUtils.getSubject(); 
			subject.logout();
			
			rtnInfo="success";
			map.put("result", rtnInfo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			rtnInfo="error";
			map.put("result", rtnInfo);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	/**
	 * @Title: removeSession
	 * @Description: 退出清除session
	 * @author yk
	 * @date 2017年8月21日
	 * @param @param USERNAME
	 * @return void
	 * @throws
	 */
	public void removeSession(String USERNAME){
		Session session = Jurisdiction.getSession();	//以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_USERROLE);
		session.removeAttribute(Const.STAFF_ID);
		session.removeAttribute(Const.STAFF_DEPARTMENT_ID);
		session.removeAttribute(Const.STAFF_NAME);
		session.removeAttribute(Const.STAFF_DEPARTMENT_NAME);
		session.removeAttribute("changeMenu");
		session.removeAttribute("DEPARTMENT_IDS");
		session.removeAttribute("DEPARTMENT_ID");
	}
	
	/**
	 * @Title: resolveTwoLevelMenu
	 * @Description: 二级菜单
	 * @author yk
	 * @date 2017年8月10日
	 * @param @param twoLevelMenuLst
	 * @param @return
	 * @return JSONArray
	 * @throws
	 */
	private JSONArray resolveTwoLevelMenu(List<Menu> twoLevelMenuLst,String parentId) {
		JSONArray jsonAry=new JSONArray();
		JSONObject jo=null;
		if(twoLevelMenuLst!=null && twoLevelMenuLst.size()>0){
			Menu menu=null;
			String menu_url="";
			
			String performance=Jurisdiction.getPerformance(); //是否启用绩效管理
			String MENU_ID="";
			
			
			for(int i=0;i<twoLevelMenuLst.size();i++){
				menu=twoLevelMenuLst.get(i);
				
				
				//不启用绩效管理，有些菜单不显示
				MENU_ID=menu.getMENU_ID();
				if(performance.equals("noStartUse")) {
					if(MENU_ID.equals("394") || MENU_ID.equals("397") || MENU_ID.equals("398") || MENU_ID.equals("407")  || MENU_ID.equals("410")  || MENU_ID.equals("411")  || MENU_ID.equals("414")  || MENU_ID.equals("415")) {
						continue;
					}
				}
				
				
				if(menu.isHasMenu() && menu.getMENU_STATE().equals("1")){//当前人员有权限
					jo=new JSONObject();
					
					jo.put("id",menu.getMENU_ID());
					jo.put("leaf", false);
					//jo.put("parent",menu.getPARENT_ID());
					jo.put("parent",parentId);
					jo.put("text",menu.getMENU_NAME());
					jo.put("iconfont",menu.getMENU_ICON());
					
					if(StringUtils.isNotEmpty(menu.getMENU_URL())){
						/*有的会出现空格*/
						menu_url=menu.getMENU_URL();
						if(!"#".equals(menu_url)){
							jo.put("url",menu_url);
						}else{
							jo.put("responseText", resolveThreeLevelMenu(menu.getSubMenu(), menu.getMENU_ID()));
						}
					}else{
						jo.put("responseText", resolveThreeLevelMenu(menu.getSubMenu(), menu.getMENU_ID()));
					}
					
					jsonAry.add(jo);
				}
			}
		}
		return jsonAry;
	}
	
	/**
	 * @Title: resolveThreeLevelMenu
	 * @Description: 三级菜单
	 * @author yk
	 * @date 2017年8月10日
	 * @param @param twoLevelMenuLst
	 * @param @param parentId
	 * @param @return
	 * @return JSONArray
	 * @throws
	 */
	private JSONArray resolveThreeLevelMenu(List<Menu> threeLevelMenuLst,String parentId) {
		JSONArray jsonAry=new JSONArray();
		JSONObject jo=null;
		if(threeLevelMenuLst!=null && threeLevelMenuLst.size()>0){
			Menu menu=null;
			
			String performance=Jurisdiction.getPerformance(); //是否启用绩效管理
			String MENU_ID="";
			
			
			for(int i=0;i<threeLevelMenuLst.size();i++){
				menu=threeLevelMenuLst.get(i);
				
				//不启用绩效管理，有些菜单不显示
				MENU_ID=menu.getMENU_ID();
				if(performance.equals("noStartUse")) {
					if(MENU_ID.equals("394") || MENU_ID.equals("397") || MENU_ID.equals("398") || MENU_ID.equals("407")  || MENU_ID.equals("410")  || MENU_ID.equals("411")  || MENU_ID.equals("414")  || MENU_ID.equals("415")) {
						continue;
					}
				}
				
				
				if(menu.isHasMenu()&& menu.getMENU_STATE().equals("1")){//当前人员有权限
					jo=new JSONObject();
					
					jo.put("id",menu.getMENU_ID());
					jo.put("leaf", false);
					//jo.put("parent",menu.getPARENT_ID());
					jo.put("parent",parentId);
					jo.put("text",menu.getMENU_NAME());
					jo.put("iconfont",menu.getMENU_ICON());
					jo.put("url",menu.getMENU_URL());
					
					jsonAry.add(jo);
					
				}
			}
		}
		return jsonAry;
	}
	
	
	
	/**
	 * @Title: setLoginIp
	 * @Description: 更新登录用户的IP
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param USERNAME
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void setLoginIp(String USERNAME) throws Exception {  
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {  
			ip = request.getRemoteAddr();  
	    }else{
	    	ip = request.getHeader("x-forwarded-for");  
	    }
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}  
	
	
	
	/**
	 * @Title: readMenu
	 * @Description: 根据角色权限获取本权限的菜单列表(递归处理)
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param menuList:传入的总菜单
	 * @param @param roleRights:加密的权限字符串
	 * @param @return
	 * @return List<Menu>
	 * @throws
	 */
	public List<Menu> readMenu(List<Menu> menuList,List<PageData> userRoleLst,String USERNAME){
		for(int i=0;i<menuList.size();i++){
			
			String roleRights="";
			if(StringUtils.isNotEmpty(USERNAME) && USERNAME.equals("admin")) {//管理员拥有所有权限
				menuList.get(i).setHasMenu(true);
			}else {
				for(int z=0;z<userRoleLst.size();z++){
					if(menuList.get(i).isHasMenu())break;
					roleRights=userRoleLst.get(z).getString("RIGHTS");
					menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
				}
			}
			
			if(menuList.get(i).isHasMenu()){		//判断是否有此菜单权限
				this.readMenu(menuList.get(i).getSubMenu(),userRoleLst,USERNAME);//是：继续排查其子菜单
			}
		}
		return menuList;
	}
	
	/**
	 * @Title: setLoginPd
	 * @Description: 设置登录页面的配置参数
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param pd
	 * @param @return
	 * @return PageData
	 * @throws
	 */
	public PageData setLoginPd(PageData pd){
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); 	//读取MES系统名称
		return pd;
	}
	
	
}
