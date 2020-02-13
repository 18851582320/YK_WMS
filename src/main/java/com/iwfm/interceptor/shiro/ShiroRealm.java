package com.iwfm.interceptor.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.iwfm.entity.system.Menu;
import com.iwfm.entity.system.User;
import com.iwfm.service.system.menu.MenuManager;
import com.iwfm.service.system.userRole.UserRoleManager;
import com.iwfm.util.Const;
import com.iwfm.util.PageData;
import com.iwfm.util.RightsHelper;
import com.iwfm.util.Tools;

/**
 * ClassName: ShiroRealm 
 * @Description: TODO
 * @author yk
 * @date 2017年8月1日
 */
public class ShiroRealm extends AuthorizingRealm {
	
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		 String username = (String)token.getPrincipal();  				//得到用户名 
	     String password = new String((char[])token.getCredentials()); 	//得到密码
	     if(Tools.notEmpty(username) && Tools.notEmpty(password)){
	    	 return new SimpleAuthenticationInfo(username, password, getName());
	     }else{
	    	 return null;
	     }
	}

	
	/**
	 * @Title: clearAllCachedAuthorizationInfo
	 * @Description: 清除缓存
	 * @author yk
	 * @date 2017年8月1日
	 * @param 
	 * @return void
	 * @throws
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				System.out.println(key + "====================清除");
				cache.remove(key);
			}
		}
	}
	
	/**
	 * @Title: clearAllCachedAuthorizationInfo
	 * @Description: 授权函数
	 * @author yk
	 * @date 2017年8月1日
	 * @param 
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {		
		Subject subject = SecurityUtils.getSubject();		
		SimpleAuthorizationInfo info=null;
		Session session=subject.getSession();
		// 先看权限信息是否加载过了
		if (session.getAttribute("AuthorizationInfo") == null) {			
			WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();
			MenuManager menuService = (MenuManager) webctx.getBean("menuService");
			UserRoleManager userRoleService=(UserRoleManager)webctx.getBean("userRoleService");
			try {
				
				User user = (User) session.getAttribute(Const.SESSION_USER);  // 读取session中的用户信息
				
				String USERNAME=session.getAttribute(Const.SESSION_USERNAME).toString();
				if(StringUtils.isEmpty(USERNAME)){
					USERNAME=user.getUSERNAME();
				}
				
				List<Menu> menuList=null;
				if(session.getAttribute(USERNAME + Const.SESSION_allmenuList)==null){
					menuList = menuService.listAllMenuQx("0");
				}else{
					menuList=(List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList);
				}
				Boolean isAdmin = "admin".equals(USERNAME);
				
				//菜单权限(增删改查)
				//Map<String, String> map = (Map<String, String>)session.getAttribute(USERNAME + Const.SESSION_QX);
				List<PageData> userRoleLst=null;
				if(session.getAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS)==null){
					PageData pdRole=new PageData();
					pdRole.put("userId", user.getUSER_ID());
					userRoleLst=userRoleService.listAllByUserId(pdRole);
				}else{
					userRoleLst=(List<PageData>)session.getAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
				}
				
				//根据角色权限处理菜单权限状态(递归处理)
				List<String> permissionsList = new ArrayList<String>();
				
				this.readMenu(menuList,userRoleLst,permissionsList,isAdmin);	
				
				//按钮权限
				List<PageData> buttonQXnamelist = new ArrayList<PageData>();
				PageData pd = new PageData();
				
				
				pd.put("userId",user.getUSER_ID());//用户ID
				for (int i = 0; i < buttonQXnamelist.size(); i++) {
					permissionsList.add(buttonQXnamelist.get(i).getString("QX_NAME"));
				}
				
				info = new SimpleAuthorizationInfo();
				info.addStringPermissions(permissionsList);
				subject.getSession().setAttribute("AuthorizationInfo", info);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}	//获取所有菜单
			
		} else {
			info = (SimpleAuthorizationInfo) subject.getSession().getAttribute(	"AuthorizationInfo");
		}
		return info;
	}
	
	
	/**
	 * @Title:readMenu
	 * @Description: TODO
	 * @author yk
	 * @date 2018年5月29日
	 * @param @param menuList
	 * @param @param userRoleLst
	 * @param @param permison
	 * @param @param isAdmin
	 * @param @return   
	 * @return List<String>  
	 * @throws
	 */
	public List<String> readMenu(List<Menu> menuList,List<PageData> userRoleLst,List<String> permison,boolean isAdmin){
		for(int i=0;i<menuList.size();i++){
			Menu menu = menuList.get(i);
			String AUTHORIZATION = menu.getMENU_AUTHORIZATION();
			//因为这里的权限不涉及菜单权限，所以权限码为空的菜单不进行授权
			
			String add_qx="";
			String delete_qx="";
			String cha_qx="";
			String edit_qx="";
			String import_qx="";
			String export_qx="";
			PageData userRole=null;
			
			if(Tools.notEmpty(AUTHORIZATION)){
				for(int z=0;z<userRoleLst.size();z++){
					userRole=userRoleLst.get(z);
					add_qx=userRole.getString("ADD_QX");
					delete_qx=userRole.getString("DEL_QX");
					cha_qx=userRole.getString("CHA_QX");
					edit_qx=userRole.getString("EDIT_QX");
					import_qx=userRole.getString("IMPORT_QX");
					export_qx=userRole.getString("EXPORT_QX");
					
					
					if(!permison.contains(AUTHORIZATION+".ADD")){
						//添加权限
						if(RightsHelper.testRights(add_qx, menu.getMENU_ID()) || isAdmin){
							permison.add(AUTHORIZATION+".ADD");
						}
					}
					if(!permison.contains(AUTHORIZATION+".DELETE")){
						//删除权限
						if(RightsHelper.testRights(delete_qx,menu.getMENU_ID()) || isAdmin){
							permison.add(AUTHORIZATION+".DELETE");
						}
					}
					if(!permison.contains(AUTHORIZATION+".SEARCH")){
						//查询权限
						if(RightsHelper.testRights(cha_qx,menu.getMENU_ID()) || isAdmin){
							permison.add(AUTHORIZATION+".SEARCH");
						}
					}
					if(!permison.contains(AUTHORIZATION+".EDIT")){
						//修改权限
						if(RightsHelper.testRights(edit_qx,menu.getMENU_ID()) || isAdmin){
							permison.add(AUTHORIZATION+".EDIT");
						}
					}
					if(!permison.contains(AUTHORIZATION+".IMPORT")){
						//导入权限
						if(RightsHelper.testRights(import_qx,menu.getMENU_ID()) || isAdmin){
							permison.add(AUTHORIZATION+".IMPORT");
						}
					}
					if(!permison.contains(AUTHORIZATION+".EXPORT")){
						//导出权限
						if(RightsHelper.testRights(export_qx,menu.getMENU_ID()) || isAdmin){
							permison.add(AUTHORIZATION+".EXPORT");
						}
					}
					
				}
			}
			this.readMenu(menuList.get(i).getSubMenu(),userRoleLst,permison,isAdmin);					//是：继续排查其子菜单
		}
		return permison;
	}
	
	@Override
	public String getName() {
		return getClass().getName();
	}
	
}
