
package com.iwfm.service.system.sysDataSet.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.sysDataSet.SysDataSetManager;
import com.iwfm.service.system.uploadFile.UploadFileManager;
import com.iwfm.util.PageData;

/**
 * 
 * ClassName: SysDataSetService 
 * @Description: TODO
 * @author yk
 * @date: 2019年1月24日
 */
@SuppressWarnings("unchecked")
@Service(value="sysDataSetService")
public class SysDataSetService implements SysDataSetManager{
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
	@Resource(name="logService")
	private LogManager logService;
	
	@Resource(name="uploadFileService")
	private UploadFileManager uploadFileService;
	
	
	/**
	 * 
	 * @Title: save
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @param files
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public Map<String, String> save(PageData pd,CommonsMultipartFile []files)throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		String rtnInfo="success";
		dao.save("SysDataSetMapper.save", pd);
		map.put("result", rtnInfo);
		return map;
		
	}
	
	/**
	 * 
	 * @Title: edit
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @param files
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public Map<String, String> edit(PageData pd,CommonsMultipartFile []files)throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		String rtnInfo="success";
		dao.update("SysDataSetMapper.edit", pd);
		map.put("result", rtnInfo);
		return map;
	}
	
	
	/**
	 * 
	 * @Title: delete
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @throws
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysDataSetMapper.delete", pd);
	}
	
	
	/**
	 * 
	 * @Title: findById
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysDataSetMapper.findById", pd);
	}
	
	
	/**
	 * 
	 * @Title: querylistPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param page
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public List<PageData> querylistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysDataSetMapper.querylistPage", page);
	}
	
	
	/**
	 * 
	 * @Title: listAll
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysDataSetMapper.listAll", pd);
	}
	
	

	/**
	 * 
	 * @Title: deleteAll
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param ArrayDATA_IDS
	 * @param: @throws Exception   
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysDataSetMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> findByCodeAndValue(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SysDataSetMapper.findByCodeAndValue", pd);
	}
	

	/**
	 * @Title: findByCode
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月27日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public List<PageData> findByCode(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SysDataSetMapper.findByCode", pd);
	}
	
	
}
