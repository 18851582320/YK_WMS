package com.iwfm.service.system.menu.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.entity.system.Menu;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.menu.MenuManager;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;

/**
 * ClassName: MenuService 
 * @Description: 菜单service
 * @author yk
 * @date 2017年8月9日
 */

@Service("menuService")
public class MenuService implements MenuManager{
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="logService")
	private LogManager logService;

	
	/**
	 * @Title: menulistPage
	 * @Description: datagrid
	 * @author yk
	 * @date 2017年8月18日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception
	 * @return List<Menu>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> menulistPage(Page page)throws Exception{
		return (List<Menu>) dao.findForList("MenuMapper.menulistPage", page);
	}
	
	/**
	 * 
	 * @Title: menulist
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年11月1日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<Menu>  
	 * @throws:
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> menulist(PageData pd)throws Exception{
		return (List<Menu>) dao.findForList("MenuMapper.menulist", pd);
	}
	
	/**
	 * @Title:querylistPage
	 * @Description: 菜单列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月9日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> querylistPage(Page page)throws Exception{
		return (List<Menu>) dao.findForList("MenuMapper.querylistPage", page);
	}
	
	
	/**
	 * @Title: listSubMenuByParentId
	 * @Description: 通过ID获取其子一级菜单
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param parentId
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> listSubMenuByParentId(String parentId) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentId", parentId);
	}
	
	/**
	 * @Title:listForSelect
	 * @Description: TODO
	 * @author yk
	 * @date 2018年5月31日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listForSelect(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MenuMapper.listForSelect", pd);
	}
	
	/**
	 * @Title:listSubMenuByParentIdAndMenuType
	 * @Description: 通过ID和菜单分类获取其子一级菜单
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<Menu>  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> listSubMenuByParentIdAndMenuType(PageData pd) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentIdAndMenuType", pd);
	}
	
	/**
	 * @Title: getMenuById
	 * @Description: 通过菜单ID获取数据
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public PageData getMenuById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.getMenuById", pd);
	}
	
	/**
	 * @Title: saveMenu
	 * @Description: 新增菜单
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param menu
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void saveMenu(Menu menu) throws Exception {
		dao.save("MenuMapper.insertMenu", menu);
		logService.save(Jurisdiction.getUsername(), "新增菜单"+menu.getMENU_NAME());
	}
	
	/**
	 * @Title: findMaxId
	 * @Description: 取最大ID
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public PageData findMaxId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.findMaxId", pd);
	}
	
	/**
	 * @Title: deleteMenuById
	 * @Description: 删除菜单
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param MENU_ID
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void deleteMenuById(String MENU_ID) throws Exception {
		dao.save("MenuMapper.deleteMenuById", MENU_ID);
	}
	
	/**
	 * @Title:deleteMenuByIdArray
	 * @Description: 通过数据删除数据
	 * @author yk
	 * @date 2018年2月9日
	 * @param @param MENU_ID
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteMenuByIdArray(String MENU_ID[]) throws Exception {
		for(int i=0;i<MENU_ID.length;i++){
			dao.save("MenuMapper.deleteMenuById", MENU_ID[i]);
		}
		logService.save(Jurisdiction.getUsername(), "删除菜单ID"+MENU_ID.toString());
	}
	
	/**
	 * @Title: edit
	 * @Description: 编辑
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param menu
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void edit(Menu menu) throws Exception {
		 dao.update("MenuMapper.updateMenu", menu);
		 logService.save(Jurisdiction.getUsername(), "修改菜单"+menu.getMENU_NAME());
	}
	
	/**
	 * @Title: editicon
	 * @Description: 保存菜单图标 
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public PageData editicon(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MenuMapper.editicon", pd);
	}
	
	/**
	 * @Title: listAllMenu
	 * @Description: 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param MENU_ID
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public List<Menu> listAllMenu(String MENU_ID) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);
		for(Menu menu : menuList){
			menu.setMENU_URL("menu/toEdit.do?MENU_ID="+menu.getMENU_ID());
			menu.setSubMenu(this.listAllMenu(menu.getMENU_ID()));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}

	/**
	 * @Title: listAllMenuQx
	 * @Description: 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param MENU_ID
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public List<Menu> listAllMenuQx(String MENU_ID) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);
		for(Menu menu : menuList){
			menu.setSubMenu(this.listAllMenuQx(menu.getMENU_ID()));
		}
		return menuList;
	}
	
	/**
	 * @Title:listPlatMenuByMenuType
	 * @Description: 工位机和MES平台菜单查询
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<Menu>  
	 * @throws
	 */
	public List<Menu> listPlatMenuByMenuType(PageData pd) throws Exception{
		List<Menu> menuList = this.listSubMenuByParentIdAndMenuType(pd);
		for(Menu menu : menuList){
			pd.put("parentId",menu.getMENU_ID());
			menu.setSubMenu(this.listPlatMenuByMenuType(pd));
		}
		return menuList;
	}
	
	/**
	 * 
	 * @Title:listAllWitSubMenu
	 * @Description: 获取工位机端所有子级菜单
	 * @author yk
	 * @date 2018年4月24日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<Menu>  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> listAllWitSubMenu(PageData pd) throws Exception{
		List<Menu> menuList = (List<Menu>) dao.findForList("MenuMapper.listAllWitSubMenu", pd);
		for(Menu menu : menuList){
			menu.setSubMenu(new ArrayList<Menu>());
		}
		return menuList;
	}
}
