package com.iwfm.service.system.queryConditionSet.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.system.queryConditionSet.SysQueryConditionSetManager;
import com.iwfm.util.PageData;


/**
 * ClassName: SysQueryConditionSetService 
 * @Description: 查询条件配置
 * @author yk
 * @date 2018年2月7日
 */
@Service("sysQueryConditionSetService")
public class SysQueryConditionSetService implements SysQueryConditionSetManager{
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * @Title:save
	 * @Description: 新增保存
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysQueryConditionSetMapper.save", pd);
	}
	
	/**
	 * @Title:delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysQueryConditionSetMapper.delete", pd);
	}
	
	/**
	 * @Title:edit
	 * @Description: 修改
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysQueryConditionSetMapper.edit", pd);
	}
	
	/**
	 * @Title:listPage
	 * @Description: 列表
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysQueryConditionSetMapper.datalistPage", page);
	}
	
	
	/**
	 * @Title:listAll
	 * @Description: 列表（全部）
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysQueryConditionSetMapper.listAll", pd);
	}
	
	/**
	 * @Title:findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysQueryConditionSetMapper.findById", pd);
	}
	
	/**
	 * @Title:findBySysData
	 * @Description: TODO
	 * @author yk
	 * @date 2018年2月8日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findBySysData(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysQueryConditionSetMapper.findBySysData", pd);
	}
	
	
	/**
	 * @Title:getQueryCondition
	 * @Description: 获取查询条件
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getQueryCondition(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysQueryConditionSetMapper.getQueryCondition", pd);
	}
	
	/**
	 * @Title:getQueryConditionSetList
	 * @Description: 查询条件配置
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getQueryConditionSetList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysQueryConditionSetMapper.getQueryConditionSetList", pd);
	}
	
	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysQueryConditionSetMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * @Title:deleteBySysData
	 * @Description: TODO
	 * @author yk
	 * @date 2018年2月8日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteBySysData(PageData pd)throws Exception{
		dao.delete("SysQueryConditionSetMapper.deleteBySysData", pd);
	}
	
	/**
	 * @Title:listUserForStaff
	 * @Description: 获取特殊的查询条件
	 * @author yk
	 * @date 2018年3月29日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> specialSelect(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("SysSpecialSelectMapper.specialSelect", pd);
	}
}
