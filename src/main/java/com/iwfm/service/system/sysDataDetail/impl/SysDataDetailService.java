package com.iwfm.service.system.sysDataDetail.impl;




import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.sysDataDetail.SysDataDetailManager;
import com.iwfm.util.PageData;

/**
 * 
 * ClasaisaiName: SysDataDetailService
 * @Description: TODO
 * @author yk
 * @date: 2018年9月29日
 */
@Service(value="sysDataDetailService")
public class SysDataDetailService implements SysDataDetailManager{
	
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
	 * @date 2018年9月29日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysDataDetailMapper.save", pd);
	}
	
	/**
	 * @Title:edit
	 * @Description: 修改
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysDataDetailMapper.edit", pd);
	}
	
	
	/**
	 * @Title:delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysDataDetailMapper.delete", pd);
	}
	
	
	
	/**
	 * @Title:listAllByKeyId
	 * @Description: 导出
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByKeyId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysDataDetailMapper.listAllByKeyId", pd);
	}
	
	/**
	 * @Title:findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysDataDetailMapper.findById", pd);
	}
	

	/**
	 * @Title:deleteByKeyId
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteByKeyId(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysDataDetailMapper.deleteByKeyId", ArrayDATA_IDS);
	}
	
}
