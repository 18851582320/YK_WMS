package com.iwfm.service.system.menu;

import java.util.List;

import com.iwfm.entity.Page;
import com.iwfm.entity.system.Menu;
import com.iwfm.util.PageData;

/**
 * ClassName: MenuManager 
 * @Description: 菜单接口
 * @author yk
 * @date 2017年8月1日
 */
public interface MenuManager {
	
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
	public List<Menu> menulistPage(Page page)throws Exception;
	
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
	public List<Menu> menulist(PageData pd)throws Exception;
	
	/**
	 * @Title:querylistPage
	 * @Description: 菜单列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月9日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<Menu>  
	 * @throws
	 */
	public List<Menu> querylistPage(Page page)throws Exception;
	
	/**
	 * @Title: listSubMenuByParentId
	 * @Description: 根据父级Id获取子菜单
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param parentId
	 * @param @return
	 * @param @throws Exception
	 * @return List<Menu>
	 * @throws
	 */
	public List<Menu> listSubMenuByParentId(String parentId)throws Exception;
	
	/**
	 * @Title:listForSelect
	 * @Description: TODO
	 * @author yk
	 * @date 2018年5月31日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listForSelect(PageData pd)throws Exception;
	
	/**
	 * @Title: getMenuById
	 * @Description: 根据Id获取菜单
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData getMenuById(PageData pd) throws Exception;
	
	
	/**
	 * @Title: saveMenu
	 * @Description: 菜单保存
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param menu
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void saveMenu(Menu menu) throws Exception;
	
	/**
	 * @Title: findMaxId
	 * @Description: 
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData findMaxId(PageData pd) throws Exception;
	
	/**
	 * @Title: deleteMenuById
	 * @Description: 删除菜单
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param MENU_ID
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void deleteMenuById(String MENU_ID) throws Exception;
	
	/**
	 * @Title:deleteMenuByIdArray
	 * @Description: 通过数组删除数据
	 * @author yk
	 * @date 2018年2月9日
	 * @param @param MENU_ID
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteMenuByIdArray(String MENU_ID[]) throws Exception;
	
	/**
	 * @Title: edit
	 * @Description: 菜单修改
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param menu
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void edit(Menu menu) throws Exception;
	
	/**
	 * @Title: editicon
	 * @Description: 
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData editicon(PageData pd) throws Exception;
	
	/**
	 * @Title: listAllMenu
	 * @Description: 获取所有菜单
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param MENU_ID
	 * @param @return
	 * @param @throws Exception
	 * @return List<Menu>
	 * @throws
	 */
	public List<Menu> listAllMenu(String MENU_ID) throws Exception;
	
	/**
	 * @Title: listAllMenuQx
	 * @Description: 获取菜单权限
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param MENU_ID
	 * @param @return
	 * @param @throws Exception
	 * @return List<Menu>
	 * @throws
	 */
	public List<Menu> listAllMenuQx(String MENU_ID) throws Exception;
	
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
	public List<Menu> listPlatMenuByMenuType(PageData pd) throws Exception;
	
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
	public List<Menu> listAllWitSubMenu(PageData pd) throws Exception;
}
