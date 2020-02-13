package com.iwfm.service.system.role.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.entity.system.Role;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.role.RoleManager;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;

/**
 * ClassName: RoleService 
 * @Description: 角色
 * @author yk
 * @date 2017年8月9日
 */

@Service("roleService")
public class RoleService implements RoleManager{
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
	@Resource(name="logService")
	private LogManager logService;
	
	
	/**
	 * @Title: deleteRolesByIds
	 * @Description: 批量删除角色
	 * @author yk
	 * @date 2017年8月14日
	 * @param @param ROLE_IDS
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	@Override
	public void deleteRolesByIds(String[] ROLE_IDS) throws Exception {
		// TODO Auto-generated method stub
		dao.delete("RoleMapper.deleteRolesByIds", ROLE_IDS);
		logService.save(Jurisdiction.getUsername(), "批量删除角色："+ROLE_IDS.toString());
		
	}

	/**
	 * @Title: getRoleCount
	 * @Description: 获取角色总数
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param value
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public PageData getCount(String value)throws Exception{
		return (PageData)dao.findForObject("RoleMapper.getCount", value);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<Role> rolelistPage(Page page)throws Exception{
		return (List<Role>) dao.findForList("RoleMapper.rolelistPage", page);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<Role> querylistPage(Page page)throws Exception{
		return (List<Role>) dao.findForList("RoleMapper.querylistPage", page);
	}
	
	
	/**
	 * @Title:listForSelect
	 * @Description: TODO
	 * @author yk
	 * @date 2018年5月28日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listForSelect(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RoleMapper.listForSelect", pd);
	}
	
	/**
	 * @Title: listAllRolesByPId
	 * @Description: 列出此组下级角色
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Role> listAllRolesByPId(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesByPId", pd);
	}
	
	/**
	 * @Title: findObjectById
	 * @Description: 通过id查找
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RoleMapper.findObjectById", pd);
	}
	
	/**
	 * @Title:findByRoleName
	 * @Description: 通过名称查找数据
	 * @author yk
	 * @date 2018年1月26日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findByRoleName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoleMapper.findByRoleName", pd);
	}
	
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
	public PageData findByRNUMBER(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoleMapper.findByRNUMBER", pd);
	}
	
	
	/**
	 * @Title: add
	 * @Description: 添加
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void save(PageData pd) throws Exception {
		dao.save("RoleMapper.insert", pd);
		logService.save(Jurisdiction.getUsername(), "新增角色:"+pd.getString("ROLE_NAME"));//保存日志
		
		String codeRuleType=pd.getString("codeRuleType");
		//保存成功后反写流水号
		if(StringUtils.isNotEmpty(codeRuleType)){
			codeRuleService.updateFormCodeSerialNum("sys_role", "RNUMBER", codeRuleType);
		}
	}
	
	/**
	 * @Title: edit
	 * @Description: 保存修改
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("RoleMapper.edit", pd);
		logService.save(Jurisdiction.getUsername(), "修改角色:"+pd.getString("ROLE_NAME"));
	}
	
	/**
	 * @Title: deleteRoleById
	 * @Description: 删除角色
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param ROLE_ID
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void deleteRoleById(String ROLE_ID) throws Exception {
		dao.delete("RoleMapper.deleteRoleById", ROLE_ID);
	}
	
	/**
	 * @Title: updateRoleRights
	 * @Description: 给当前角色附加菜单权限
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param role
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void updateRoleRights(Role role) throws Exception {
		dao.update("RoleMapper.updateRoleRights", role);
	}
	
	/**
	 * @Title: getRoleById
	 * @Description: 通过id查找
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param ROLE_ID
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public Role getRoleById(String ROLE_ID) throws Exception {
		return (Role) dao.findForObject("RoleMapper.getRoleById", ROLE_ID);
	}
	
	/**
	 * @Title: setAllRights
	 * @Description: 给全部子角色加菜单权限
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param pd
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void setAllRights(PageData pd) throws Exception {
		dao.update("RoleMapper.setAllRights", pd);
	}
	
	/**
	 * @Title: saveRoleButtonRights
	 * @Description: 权限(增删改查)
	 * @author yk
	 * @date 2017年8月9日
	 * @param @param QxType
	 * @param @param pd
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void saveRoleButtonRights(String QxType,PageData pd) throws Exception {
		dao.update("RoleMapper."+QxType, pd);
	}
}
