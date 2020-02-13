package com.iwfm.service.system.role;

import java.util.List;

import com.iwfm.entity.Page;
import com.iwfm.entity.system.Role;
import com.iwfm.util.PageData;

/**
 * ClassName: RoleManager 
 * @Description: 角色接口类
 * @author yk
 * @date 2017年8月9日
 */
public interface RoleManager {
	
	/**
	 * @Title: deleteRolesByIds
	 * @Description: 批量删除角色
	 * @author yk
	 * @date 2017年8月14日
	 * @param @param ROLE_IDS
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void deleteRolesByIds(String[] ROLE_IDS)throws Exception;
	
	
	/**
	 * @Title: getUserCount
	 * @Description: TODO
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param value
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData getCount(String value)throws Exception;
	
	
	/**
	 * @Title: rolelistPage
	 * @Description: 
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return List<Role>
	 * @throws
	 */
	public List<Role> rolelistPage(Page page)throws Exception;
	
	/**
	 * @Title:querylistPage
	 * @Description: 角色列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月9日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<Role>  
	 * @throws
	 */
	public List<Role> querylistPage(Page page)throws Exception;
	
	/**
	 * @Title:listForSelect
	 * @Description: 
	 * @author yk
	 * @date 2018年5月28日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listForSelect(PageData pd)throws Exception;
	
	
	/**
	 * @Title: listAllRolesByPId
	 * @Description: 列出此组下级角色
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return List<Role>
	 * @throws
	 */
	public List<Role> listAllRolesByPId(PageData pd) throws Exception;
	
	/**
	 * @Title: findObjectById
	 * @Description: 通过id查找
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData findObjectById(PageData pd) throws Exception;
	
	/**
	 * @Title:findByRoleName
	 * @Description: 通过名称查找数据
	 * @author yk
	 * @date 2018年1月26日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByRoleName(PageData pd)throws Exception;
	
	/**
	 * @Title:findByRNUMBER
	 * @Description: 通过角色编码查找数据
	 * @author yk
	 * @date 2018年3月29日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByRNUMBER(PageData pd)throws Exception;
	
	/**
	 * @Title: add
	 * @Description: 添加
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void save(PageData pd) throws Exception;
	
	/**
	 * @Title: edit
	 * @Description: 保存修改
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void edit(PageData pd) throws Exception;
	
	/**
	 * @Title: deleteRoleById
	 * @Description: 删除角色
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param ROLE_ID
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void deleteRoleById(String ROLE_ID) throws Exception;
	
	/**
	 * @Title: updateRoleRights
	 * @Description: 给当前角色附加菜单权限
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param role
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void updateRoleRights(Role role) throws Exception;
	
	/**
	 * @Title: getRoleById
	 * @Description: 通过id查找
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param ROLE_ID
	 * @param @return
	 * @param @throws Exception
	 * @return Role
	 * @throws
	 */
	public Role getRoleById(String ROLE_ID) throws Exception;
	
	
	
	/**
	 * @Title: setAllRights
	 * @Description: 给全部子角色加菜单权限
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void setAllRights(PageData pd) throws Exception;
	
	/**
	 * @Title: saveRoleButtonRights
	 * @Description: 权限(增删改查)
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param msg
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void saveRoleButtonRights(String QxType,PageData pd) throws Exception;
}
