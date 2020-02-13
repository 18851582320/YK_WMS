package com.iwfm.controller.system.menu;

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
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.service.system.menu.MenuManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;

@Controller
@RequestMapping(value="/menu")
public class MenuController extends BaseController{
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	
	/**
	 * @Title: listAllMenu
	 * @Description: 菜单主页
	 * @author yk
	 * @date 2017年8月18日
	 * @param @param model
	 * @param @param MENU_ID
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping({"/listAllMenu"})
	public ModelAndView listAllMenu(Model model, String MENU_ID)
	{
	    ModelAndView mv = this.getModelAndView();
	    try {
	    	
	    	List<Menu> menuList = menuService.listAllMenuQx("0");
	    	
	    	JSONArray jsonAry=new JSONArray();
			JSONObject jo=null;
			Menu menu=null;
			
			if(menuList!=null && menuList.size()>0){
				for(int i=0;i<menuList.size();i++){
					menu=menuList.get(i);
					jo=new JSONObject();
					jo.put("id",menu.getMENU_ID());
					jo.put("text",menu.getMENU_NAME());
					
					if(menu.getSubMenu()!=null && menu.getSubMenu().size()>0){
						jo.put("children", this.resolveMenu(menu.getSubMenu()));
					}
					
					jsonAry.add(jo);
				}
			}
	    	
	    	model.addAttribute("treeNodes", jsonAry.toString());
	    	mv.addObject("MENU_ID", MENU_ID);
	    }
	    catch (Exception e)
	    {
	      this.logger.error(e.toString(), e);
	    }
	    mv.setViewName("system/menu/menu_index");
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
		if(menuList!=null && menuList.size()>0){
			Menu menu=null;
			for(int i=0;i<menuList.size();i++){
				menu=menuList.get(i);
				
				jo=new JSONObject();
				jo.put("id",menu.getMENU_ID());
				jo.put("text",menu.getMENU_NAME());
				
				if(menu.getSubMenu()!=null && menu.getSubMenu().size()>0){
					jo.put("children", this.resolveMenu(menu.getSubMenu()));
				}									
					
				jsonAry.add(jo);
			}
		}
		return jsonAry;
	}
	
	

	/**
	 * @Title: menuListIndex
	 * @Description: 菜单datagrid页面
	 * @author yk
	 * @date 2017年8月18日
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping({"/menuListIndex"})
	public ModelAndView menuListIndex()throws Exception
	{
	    ModelAndView mv = getModelAndView();
	    //获取隐藏列
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("sys_menu","sys_menu");
		mv.addObject("hideLst", columnHideLst);
	    mv.setViewName("system/menu/menu_list");
	    return mv;
	}

	/**
	 * @Title: menulistPage
	 * @Description: TODO
	 * @author yk
	 * @date 2017年8月18日
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 */
	@RequestMapping({"/menulistPage"})
	@ResponseBody
	public String menulistPage()
	{
		PageData pd = new PageData();
		Page page = new Page();
		try {
				pd = this.getPageData();
				if ((pd.get("MENU_ID") == null) || (!StringUtils.isNotEmpty(pd.get("MENU_ID").toString())))
				{
					pd.put("MENU_ID", "0");
				}

				String pageNumber = pd.getString("page");
				if (Tools.notEmpty(pageNumber)) {
					page.setCurrentPage(Integer.valueOf(pageNumber).intValue());
				}
				String pageSize = pd.getString("rows");
				if (Tools.notEmpty(pageSize)) {
					page.setShowCount(Integer.valueOf(pageSize).intValue());
				}

				page.setPd(pd);
				List<Menu> menuList = this.menuService.menulistPage(page);

			    String json = JsonUtil.list2json(menuList);
			    JSONObject jo = new JSONObject();
			    jo.put("total", Integer.valueOf(page.getTotalResult()));
			   jo.put("rows", json);

			   return jo.toString();
			}
			catch (Exception localException)
			{
			}
		return null;
	}
	
	
	/**
	 * @Title:querylistPage
	 * @Description: 菜单列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月9日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	@RequestMapping({"/querylistPage"})
	@ResponseBody
	public String querylistPage()
	{
		PageData pd = new PageData();
		Page page = new Page();
		try {
			pd = this.getPageData();
			if ((pd.get("MENU_ID") == null) || (!StringUtils.isNotEmpty(pd.get("MENU_ID").toString())))
			{
				pd.put("MENU_ID", "0");
			}
			
			String pageNumber = pd.getString("page");
			if (Tools.notEmpty(pageNumber)) {
				page.setCurrentPage(Integer.valueOf(pageNumber).intValue());
			}
			String pageSize = pd.getString("rows");
			if (Tools.notEmpty(pageSize)) {
				page.setShowCount(Integer.valueOf(pageSize).intValue());
			}
			
			page.setPd(pd);
			List<Menu> menuList = this.menuService.querylistPage(page);
			
			String json = JsonUtil.list2json(menuList);
			JSONObject jo = new JSONObject();
			jo.put("total", Integer.valueOf(page.getTotalResult()));
			jo.put("rows", json);
			
			return jo.toString();
		}
		catch (Exception localException)
		{
		}
		return null;
	}
	  
	  /**
	   * @Title: toAdd
	   * @Description: 请求新增菜单页面
	   * @author yk
	   * @date 2017年8月18日
	   * @param @return
	   * @param @throws Exception
	   * @return ModelAndView
	   * @throws
	   */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(){
		ModelAndView mv = this.getModelAndView();
		try{
			PageData pd = new PageData();
			pd = this.getPageData();
			String MENU_ID = (null == pd.get("MENU_ID") || "".equals(pd.get("MENU_ID").toString()))?"0":pd.get("MENU_ID").toString();//接收传过来的上级菜单ID,如果上级为顶级就取值“0”
			pd.put("MENU_ID",MENU_ID);
			mv.addObject("pds", menuService.getMenuById(pd));	//传入父菜单所有信息
			mv.addObject("MENU_ID", MENU_ID);					//传入菜单ID，作为子菜单的父菜单ID用
			mv.addObject("method", "add");						//执行状态 add 为添加
			mv.setViewName("system/menu/menu_edit");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	 
	
	/**
	 * @Title: add
	 * @Description: 保存菜单
	 * @author yk
	 * @date 2017年8月18日
	 * @param @param menu
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object add(Menu menu){
		logBefore(logger, Jurisdiction.getUsername()+"保存菜单");
		Map<String,String > map=new HashMap<String,String>();
		PageData pd = new PageData();
		String rtnInfo="";
		try{
			pd = this.getPageData();
			menu.setMENU_ID(String.valueOf(Integer.parseInt(menuService.findMaxId(pd).get("MID").toString())+1));
			menu.setMENU_ICON("icon-caidan5");//默认菜单图标
			menuService.saveMenu(menu); //保存菜单
			
			rtnInfo="success";
			map.put("result",rtnInfo);
			
		} catch(Exception e){
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result",rtnInfo);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * @Title: toEdit
	 * @Description: 请求编辑界面
	 * @author yk
	 * @date 2017年8月18日
	 * @param @param id
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit(String MENU_ID){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("MENU_ID",MENU_ID);			//接收过来的要修改的ID
			pd = menuService.getMenuById(pd);	//读取此ID的菜单数据
			mv.addObject("pd", pd);				//放入视图容器
			
			pd.put("MENU_ID",pd.get("PARENT_ID").toString());			//用作读取父菜单信息
			mv.addObject("pds", menuService.getMenuById(pd));			//传入父菜单所有信息
			mv.addObject("MENU_ID", pd.get("PARENT_ID").toString());	//传入父菜单ID，作为子菜单的父菜单ID用
			mv.addObject("method", "edit");
			pd.put("MENU_ID",MENU_ID);			//复原本菜单ID
			
			mv.setViewName("system/menu/menu_edit");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * @Title: edit
	 * @Description: 编辑保存
	 * @author yk
	 * @date 2017年8月18日
	 * @param @param menu
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object edit(Menu menu){
		logBefore(logger, Jurisdiction.getUsername()+"修改菜单");
		Map<String,String > map=new HashMap<String,String>();
		PageData pd = new PageData();
		String rtnInfo="";
		try{
			pd = this.getPageData();
			menuService.edit(menu);
			rtnInfo="success";
			map.put("result",rtnInfo);
		} catch(Exception e){
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result",rtnInfo);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	
	
	/**
	 * 删除菜单
	 * @param MENU_ID
	 * @param out
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object delete(@RequestParam String MENU_IDS){
		logBefore(logger, Jurisdiction.getUsername()+"删除菜单");
		Map<String,String> map = new HashMap<String,String>();
		String rtnInfo = "";
		boolean tag=false;
		try{
			
			if(StringUtils.isNotEmpty(MENU_IDS)){
				String ArrayMENU_IDS[] = MENU_IDS.split(",");
				List<Menu> menuList=null;
				for(int i=0;i<ArrayMENU_IDS.length;i++){
					menuList=null;
					menuList=menuService.listSubMenuByParentId(ArrayMENU_IDS[i]);
					if(menuList!=null && menuList.size()>0){
						tag=true;
						break;
					}
				}
				
				if(tag){
					rtnInfo="canNotDelete";
				}else{
					menuService.deleteMenuByIdArray(ArrayMENU_IDS);
					rtnInfo="success";
				}
				
			}
			
			map.put("result",rtnInfo);
			
		} catch(Exception e){
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result",rtnInfo);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * @Title: toIcon
	 * @Description: 访问图标设置
	 * @author yk
	 * @date 2017年8月28日
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/toIcon")
	public ModelAndView toIcon(){
		PageData pd=new PageData();
		pd=this.getPageData();
		ModelAndView mv = getModelAndView();
		try {
			pd = menuService.getMenuById(pd);	//读取此ID的菜单数据
			pd.put("MENU_ICON",pd.get("MENU_ICON").toString());
			mv.addObject("pd", pd);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
	    mv.setViewName("system/menu/menu_icon");
	    return mv;
	}
	
	/**
	 * @Title: icon
	 * @Description: 修改图标
	 * @author yk
	 * @date 2017年8月29日
	 * @param @return
	 * @return Object
	 * @throws
	 */
	@RequestMapping(value="/icon",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object icon(){
		logBefore(logger, Jurisdiction.getUsername()+"修改图标");
		Map<String,String > map=new HashMap<String,String>();
		PageData pd = new PageData();
		String rtnInfo="";
		try{
			pd = this.getPageData();
			menuService.editicon(pd);
			rtnInfo="success";
			map.put("result",rtnInfo);
		} catch(Exception e){
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result",rtnInfo);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * @Title:listForSelect
	 * @Description: TODO
	 * @author yk
	 * @date 2018年5月31日
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
			List<PageData> list=menuService.listForSelect(pd);
			json=JsonUtil.list2json(list);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return json;
	}
	
	
}
