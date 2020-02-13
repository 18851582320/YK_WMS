
package com.iwfm.service.system.userRole.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.userRole.UserRoleManager;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;

/**
 * ClassName: UserRoleService 
 * @Description: 用户角色
 * @author yk
 * @date 2018年5月28日
 */
@Service(value="userRoleService")
public class UserRoleService implements UserRoleManager{
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
	@Resource(name="logService")
	private LogManager logService;
	
	
	/**
	 * @Title:save
	 * @Description: 增加
	 * @author yk
	 * @date 2018年5月28日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UserRoleMapper.save", pd);
	}
	
	/**
	 * @Title:saveBatch
	 * @Description: 批量保存
	 * @author yk
	 * @date 2018年5月28日
	 * @param @param pdLst
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void saveBatch(List<PageData> pdLst)throws Exception{
		for(int i=0;i<pdLst.size();i++){
			dao.save("UserRoleMapper.save", pdLst.get(i));
		}
	}
	
	
	/**
	 * @Title:delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年5月28日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("UserRoleMapper.delete", pd);
	}
	
	
	/**
	 * @Title:delete
	 * @Description: 根据用户删除角色
	 * @author yk
	 * @date 2018年5月29日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteByUserId(PageData pd)throws Exception{
		dao.delete("UserRoleMapper.deleteByUserId", pd);
	}
	
	
	
	/**
	 * @Title:listAll
	 * @Description: 
	 * @author yk
	 * @date 2018年5月28日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UserRoleMapper.listAll", pd);
	}
	
	/**
	 * @Title:listAll
	 * @Description: TODO
	 * @author yk
	 * @date 2018年5月29日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByUserId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UserRoleMapper.listAllByUserId", pd);
	}
	
	/**
	 * @Title:findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2018年5月28日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserRoleMapper.findById", pd);
	}
	
	
	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年5月28日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UserRoleMapper.deleteAll", ArrayDATA_IDS);
		logService.save(Jurisdiction.getUsername(), "删除用户角色："+ArrayDATA_IDS.toString());				
	}
	
}
