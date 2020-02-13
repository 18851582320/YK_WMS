package com.iwfm.service.system.columnHideSet.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;


/**
 * ClassName: SysColumnHideSetService 
 * @Description: 系统隐藏列设置
 * @author yk
 * @date 2018年2月11日
 */
@Service("sysColumnHideSetService")
public class SysColumnHideSetService implements SysColumnHideSetManager{
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * @Title:save
	 * @Description: 新增保存
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysColumnHideSetMapper.save", pd);
	}
	
	/**
	 * @Title:delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysColumnHideSetMapper.delete", pd);
	}
	
	/**
	 * @Title:edit
	 * @Description: 修改
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysColumnHideSetMapper.edit", pd);
	}
	
	/**
	 * @Title:listPage
	 * @Description: 列表
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysColumnHideSetMapper.datalistPage", page);
	}
	
	/**
	 * @Title:listAll
	 * @Description: 列表（全部）
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysColumnHideSetMapper.listAll", pd);
	}
	
	/**
	 * @Title:findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysColumnHideSetMapper.findById", pd);
	}
	
	/**
	 * @Title:findBySysData
	 * @Description: TODO
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findBySysData(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysColumnHideSetMapper.findBySysData", pd);
	}
	
	
	/**
	 * @Title:getColumnHide
	 * @Description: 获取隐藏列
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getColumnHide(String sysDataIndex,String sysDataTableName)throws Exception{
		PageData pd=new PageData();
		pd.put("sysDataIndex", sysDataIndex);
		pd.put("sysDataTableName", sysDataTableName);
		pd.put("userName", Jurisdiction.getUsername());
		return (List<PageData>)dao.findForList("SysColumnHideSetMapper.getColumnHide", pd);
	}
	
	/**
	 * @Title:getColumnHideSetList
	 * @Description: 系统隐藏列设置
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getColumnHideSetList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysColumnHideSetMapper.getColumnHideSetList", pd);
	}
	
	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysColumnHideSetMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * @Title:deleteBySysData
	 * @Description: TODO
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteBySysData(PageData pd)throws Exception{
		dao.delete("SysColumnHideSetMapper.deleteBySysData", pd);
	}
}
