package com.iwfm.controller.system.role;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.controller.base.BaseController;
import com.iwfm.entity.Page;
import com.iwfm.entity.system.Menu;
import com.iwfm.entity.system.Role;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.service.system.menu.MenuManager;
import com.iwfm.service.system.role.RoleManager;
import com.iwfm.service.system.user.UserManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.RightsHelper;
import com.iwfm.util.Tools;

/**
 * ClassName: RoleController 
 * @Description: 角色权限管理
 * @author yk
 * @date 2017年8月11日
 */
@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	String menuUrl="/role.do";   //菜单地址(设置)
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
	
	/**
	 * @Title: list
	 * @Description: 角色主页
	 * @author yk
	 * @date 2017年8月11日
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping
	public ModelAndView list() throws Exception{
		ModelAndView mv=this.getModelAndView();
		//获取隐藏列
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("sys_role","sys_role");
		mv.addObject("hideLst", columnHideLst);
		mv.setViewName("system/role/role_list");
		return mv;
	}
	
	
	/**
	 * @Title: rolelistPage
	 * @Description: 角色信息（分页）
	 * @author yk
	 * @date 2017年8月14日
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/rolelistPage")
	@ResponseBody
	public String rolelistPage(){
		PageData pd=new PageData();
		Page page=new Page();
		try {
			pd=this.getPageData();
			
			String pageNumber=pd.getString("page");//当前页
			if(Tools.notEmpty(pageNumber)){
				page.setCurrentPage(Integer.valueOf(pageNumber));
			}
			String pageSize=pd.getString("rows");//每页数量
			if(Tools.notEmpty(pageSize)){
				page.setShowCount(Integer.valueOf(pageSize));
			}
			
			page.setPd(pd);
			
			List<Role> roleList=roleService.rolelistPage(page);//获取分页数据（当前）
			
			String json=JsonUtil.list2json(roleList);
			JSONObject jo=new JSONObject();
			jo.put("total", Integer.valueOf(page.getTotalResult()));
			jo.put("rows", json);
			
			return jo.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
	/**
	 * @Title:querylistPage
	 * @Description: 角色列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月9日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value="/querylistPage")
	@ResponseBody
	public String querylistPage(){
		PageData pd=new PageData();
		Page page=new Page();
		try {
			pd=this.getPageData();
			
			String pageNumber=pd.getString("page");//当前页
			if(Tools.notEmpty(pageNumber)){
				page.setCurrentPage(Integer.valueOf(pageNumber));
			}
			String pageSize=pd.getString("rows");//每页数量
			if(Tools.notEmpty(pageSize)){
				page.setShowCount(Integer.valueOf(pageSize));
			}
			
			page.setPd(pd);
			
			List<Role> roleList=roleService.querylistPage(page);//获取分页数据（当前）
			
			String json=JsonUtil.list2json(roleList);
			JSONObject jo=new JSONObject();
			jo.put("total", Integer.valueOf(page.getTotalResult()));
			jo.put("rows", json);
			
			return jo.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * @Title: toAdd
	 * @Description: 角色增加页面
	 * @author yk
	 * @date 2017年8月14日
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd() throws Exception{
		ModelAndView mv=this.getModelAndView();
		
		//获取编码规则
		String code[]=codeRuleService.getFormCodeByRule("sys_role","RNUMBER");
		mv.addObject("codeRuleType", code[0]);
		mv.addObject("code", code[1]);	
		
		mv.addObject("method","add");
		mv.setViewName("system/role/role_edit");
		return mv;
	}
	

	/**
	 * @Title: add
	 * @Description: 保存
	 * @author yk
	 * @date 2017年8月14日
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object add(){
		Map<String,String > map=new HashMap<String,String>();
		logBefore(logger, Jurisdiction.getUsername()+":新增角色");
		PageData pd=new PageData();
		String rtnInfo = "";
		try {
			pd=this.getPageData();
			
			pd.put("PARENT_ID", "1"); //默认父为1
			pd.put("RIGHTS", "");     //默认权限为空
			pd.put("ROLE_ID", this.get32UUID());//主键
			pd.put("ADD_QX", "0");	//初始菜单新增权限为否
			pd.put("DEL_QX", "0");	//初始菜单删除权限为否
			pd.put("EDIT_QX", "0");	//初始菜单修改权限为否
			pd.put("CHA_QX", "0");	//初始菜单查看权限为否
			
			if(roleService.findByRNUMBER(pd)!=null){
				map.put("errmsg","角色编码不能重复！");
				rtnInfo="error";
				map.put("result",rtnInfo);
			}else{
				roleService.save(pd);
				rtnInfo="success";
				map.put("result",rtnInfo);
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result",rtnInfo);
		}
		
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * @Title: deleteRole
	 * @Description: 批量删除角色
	 * @author yk
	 * @date 2017年8月14日
	 * @param @param KEY_IDS
	 * @param @return
	 * @return Object
	 * @throws
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object deleteRole(){
		logBefore(logger, Jurisdiction.getUsername()+"批量删除角色");
		Map<String, String> map=new HashMap<String,String>();
		PageData pd=new PageData();
		boolean tag=false;
		String rtnInfo = "";
		try {
			pd=this.getPageData();
			String ROLE_IDS=pd.getString("ROLE_IDS");
			if(StringUtils.isNotEmpty(ROLE_IDS)){
				String ArrayROLE_IDS[] = ROLE_IDS.split(",");
				List<PageData> userlist=null;
				for(int i=0;i<ArrayROLE_IDS.length;i++){
					userlist=null;
					pd.put("ROLE_ID", ArrayROLE_IDS[i]);
					userlist=userService.listAllUserByRoldId(pd);//判断角色下的用户，有用户不能删除
					
					if(userlist!=null && userlist.size()>0){
						tag=true;
						break;
					}
				}
				
				if(tag){
					rtnInfo="canNotDelete";
				}else{
					roleService.deleteRolesByIds(ArrayROLE_IDS);
					rtnInfo="success";
				}
				
				map.put("result",rtnInfo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result",rtnInfo);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * @Title: toEdit
	 * @Description: 请求编辑
	 * @author yk
	 * @date 2017年8月17日
	 * @param @param ROLE_ID
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit(@RequestParam String ROLE_ID ){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("ROLE_ID", ROLE_ID);
			pd = roleService.findObjectById(pd);
			mv.addObject("method", "edit");
			mv.addObject("pd", pd);
			mv.setViewName("system/role/role_edit");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * @Title: edit
	 * @Description: 保存修改
	 * @author yk
	 * @date 2017年8月17日
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object edit(){
		logBefore(logger, Jurisdiction.getUsername()+"修改角色");
		PageData pd = new PageData();
		Map<String,String > map=new HashMap<String,String>();
		String rtnInfo="";
		try{
			pd = this.getPageData();
			roleService.edit(pd);
			rtnInfo="success";
			map.put("result",rtnInfo);
		} catch(Exception e){
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="failed";
			map.put("result",rtnInfo);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * @Title: roleButtonRight
	 * @Description: 设置角色按钮权限（增、删、改、查）
	 * @author yk
	 * @date 2017年8月17日
	 * @param @param ROLE_ID
	 * @param @param QxType
	 * @param @param model
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/roleButtonRight")
	public ModelAndView roleButtonRight(@RequestParam String ROLE_ID,@RequestParam String QxType,Model model){
		ModelAndView mv=this.getModelAndView();
		try {
			
			List<Menu> menuList = menuService.listAllMenuQx("0"); //获取所有菜单
			Role role = roleService.getRoleById(ROLE_ID);		  //根据角色ID获取角色对象
			String roleRights = "";
			if("add_qx".equals(QxType)){
				roleRights = role.getADD_QX();	//新增权限
			}else if("del_qx".equals(QxType)){
				roleRights = role.getDEL_QX();	//删除权限
			}else if("edit_qx".equals(QxType)){
				roleRights = role.getEDIT_QX();	//修改权限
			}else if("cha_qx".equals(QxType)){
				roleRights = role.getCHA_QX();	//查看权限
			}else if("import_qx".equals(QxType)){
				roleRights=role.getIMPORT_QX();
			}else if("export_qx".equals(QxType)){
				roleRights=role.getEXPORT_QX();
			}
			
			menuList = this.readMenu(menuList, roleRights);		//根据角色权限处理菜单权限状态(递归处理)
			
			String performance=Jurisdiction.getPerformance();
			String MENU_ID="";
			
			JSONArray jsonAry=new JSONArray();
			JSONObject jo=null;
			Menu menu=null;
			if(menuList!=null && menuList.size()>0){
				for(int i=0;i<menuList.size();i++){
					menu=menuList.get(i);
					
					if(!menu.getMENU_STATE().equals("1")) {
						continue;
					}
					
					MENU_ID=menu.getMENU_ID();
					if(performance.equals("noStartUse")) {
						if(MENU_ID.equals("394") || MENU_ID.equals("397") || MENU_ID.equals("398") || MENU_ID.equals("407")  || MENU_ID.equals("410")  || MENU_ID.equals("411")  || MENU_ID.equals("414")  || MENU_ID.equals("415")) {
							continue;
						}
					}
					
					jo=new JSONObject();
					jo.put("id",menu.getMENU_ID());
					jo.put("text",menu.getMENU_NAME());
					jo.put("checked", menu.isHasMenu());
					
					if(menu.getSubMenu()!=null && menu.getSubMenu().size()>0){
						jo.put("children", this.resolveMenu(menu.getSubMenu()));
					}
					jsonAry.add(jo);
				}
			}
			model.addAttribute("treeNodes", jsonAry.toString());
			
			mv.addObject("ROLE_ID",ROLE_ID);
			mv.addObject("QxType", QxType);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/role/role_ButtonRights");
		return mv;
		
	}
	

	/**
	 * @Title: saveRoleButtonRights
	 * @Description: 保存角色按钮权限
	 * @author yk
	 * @date 2017年8月17日
	 * @param @param ROLE_ID
	 * @param @param menuIds
	 * @param @param QxType
	 * @param @param out
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	@RequestMapping(value="/saveRoleButtonRights",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object saveRoleButtonRights(@RequestParam String ROLE_ID,@RequestParam String menuIds,@RequestParam String QxType){
		logBefore(logger, Jurisdiction.getUsername()+"修改"+QxType+"权限");
		PageData pd = new PageData();
		Map<String,String > map=new HashMap<String,String>();
		String rtnInfo="";
		try{
			pd = this.getPageData();
			
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
				pd.put("value",rights.toString());
			}else{
				pd.put("value","");
			}
			pd.put("ROLE_ID", ROLE_ID);
			roleService.saveRoleButtonRights(QxType, pd);
			rtnInfo="success";
			map.put("result", rtnInfo);
		} catch(Exception e){
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result", rtnInfo);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * @Title: listAllMenu
	 * @Description: 角色菜单权限
	 * @author yk
	 * @date 2017年8月17日
	 * @param @param model
	 * @param @param ROLE_ID
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/menuqx")
	public ModelAndView listAllMenu(Model model,String ROLE_ID){
		ModelAndView mv = this.getModelAndView();
		try{
			Role role = roleService.getRoleById(ROLE_ID);			//根据角色ID获取角色对象
			String roleRights = role.getRIGHTS();					//取出本角色菜单权限
			List<Menu> menuList = menuService.listAllMenuQx("0");	//获取所有菜单
			menuList = this.readMenu(menuList, roleRights);			//根据角色权限处理菜单权限状态(递归处理)
			
			String performance=Jurisdiction.getPerformance();
			String MENU_ID="";
			
			JSONArray jsonAry=new JSONArray();
			JSONObject jo=null;
			Menu menu=null;
			if(menuList!=null && menuList.size()>0){
				for(int i=0;i<menuList.size();i++){
					menu=menuList.get(i);
					
					if(!menu.getMENU_STATE().equals("1")) {
						continue;
					}
					
					MENU_ID=menu.getMENU_ID();
					if(performance.equals("noStartUse")) {
						if(MENU_ID.equals("394") || MENU_ID.equals("397") || MENU_ID.equals("398") || MENU_ID.equals("407")  || MENU_ID.equals("410")  || MENU_ID.equals("411")  || MENU_ID.equals("414")  || MENU_ID.equals("415")) {
							continue;
						}
					}
					
					jo=new JSONObject();
					jo.put("id",menu.getMENU_ID());
					jo.put("text",menu.getMENU_NAME());
					jo.put("checked", menu.isHasMenu());
					
					if(menu.getSubMenu()!=null && menu.getSubMenu().size()>0){
						jo.put("children", this.resolveMenu(menu.getSubMenu()));
					}
					
					jsonAry.add(jo);
				}
			}
			model.addAttribute("treeNodes", jsonAry.toString());
			
			mv.addObject("ROLE_ID",ROLE_ID);
			mv.setViewName("system/role/role_menuRights");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * @Title:resolveMenu
	 * @Description: 递归转换为tree
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param menuList
	 * @param @return   
	 * @return JSONArray  
	 * @throws
	 */
	private JSONArray resolveMenu(List<Menu> menuList) {
		JSONArray jsonAry=new JSONArray();
		JSONObject jo=null;
		
		String performance=Jurisdiction.getPerformance();
		String MENU_ID="";
		
		if(menuList!=null && menuList.size()>0){
			Menu menu=null;
			for(int i=0;i<menuList.size();i++){
				menu=menuList.get(i);
				
				if(!menu.getMENU_STATE().equals("1")) {
					continue;
				}
				
				MENU_ID=menu.getMENU_ID();
				if(performance.equals("noStartUse")) {
					if(MENU_ID.equals("394") || MENU_ID.equals("397") || MENU_ID.equals("398") || MENU_ID.equals("407")  || MENU_ID.equals("410")  || MENU_ID.equals("411")  || MENU_ID.equals("414")  || MENU_ID.equals("415")) {
						continue;
					}
				}
				
				jo=new JSONObject();
				jo.put("id",menu.getMENU_ID());
				jo.put("text",menu.getMENU_NAME());
				jo.put("checked",menu.isHasMenu());
				
				if(menu.getSubMenu()!=null && menu.getSubMenu().size()>0){
					jo.put("children", this.resolveMenu(menu.getSubMenu()));
				}									
					
				jsonAry.add(jo);
			}
		}
		return jsonAry;
	}
	
	
	
	/**
	 * @Title: saveMenuRights
	 * @Description: 保存角色菜单权限
	 * @author yk
	 * @date 2017年8月17日
	 * @param @param ROLE_ID
	 * @param @param menuIds
	 * @param @param out
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	@RequestMapping(value="/saveMenuRights",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object saveMenuRights(@RequestParam String ROLE_ID,@RequestParam String menuIds){
		logBefore(logger, Jurisdiction.getUsername()+"修改菜单权限");
		PageData pd = new PageData();
		Map<String,String > map=new HashMap<String,String>();
		String rtnInfo="";
		try{
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));//用菜单ID做权处理
				Role role = roleService.getRoleById(ROLE_ID);	//通过id获取角色对象
				role.setRIGHTS(rights.toString());
				roleService.updateRoleRights(role);				//更新当前角色菜单权限
				pd.put("rights",rights.toString());
			}else{
				Role role = new Role();
				role.setRIGHTS("");
				role.setROLE_ID(ROLE_ID);
				roleService.updateRoleRights(role);				//更新当前角色菜单权限(没有任何勾选)
				pd.put("rights","");
			}
			pd.put("ROLE_ID", ROLE_ID);
			rtnInfo="success";
			map.put("result", rtnInfo);
		} catch(Exception e){
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result", rtnInfo);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * @Title: readMenu
	 * @Description: 根据角色权限处理权限状态(递归处理)
	 * @author yk
	 * @date 2017年8月17日
	 * @param @param menuList:总菜单
	 * @param @param roleRights:权限字符
	 * @param @return
	 * @return List<Menu>
	 * @throws
	 */
	public List<Menu> readMenu(List<Menu> menuList,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			this.readMenu(menuList.get(i).getSubMenu(), roleRights);					//是：继续排查其子菜单
		}
		return menuList;
	}
	
	/**
	 * @Title:listForSelect
	 * @Description: 下拉列表
	 * @author yk
	 * @date 2018年5月28日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value="/listForSelect")
	@ResponseBody
	public String listForSelect(){
		PageData pd=new PageData();
		String json="";
		try {
			pd=this.getPageData();
			List<PageData> list=roleService.listForSelect(pd);
			json=JsonUtil.list2json(list);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return json;
	}
	
}
