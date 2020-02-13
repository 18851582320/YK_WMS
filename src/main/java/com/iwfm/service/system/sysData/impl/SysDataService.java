
package com.iwfm.service.system.sysData.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.sysData.SysDataManager;
import com.iwfm.service.system.sysDataDetail.SysDataDetailManager;
import com.iwfm.util.PageData;

/**
 * 
 * ClasaisaiName: SysDataService 
 * @Description: TODO
 * @author yk
 * @date: 2018年9月29日
 */
@Service(value="sysDataService")
public class SysDataService implements SysDataManager{
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
	@Resource(name="logService")
	private LogManager logService;
	
	@Resource(name="sysDataDetailService")
	private SysDataDetailManager sysDataDetailService;
	
	
	/**
	 * 
	 * @Title: save
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @param pd
	 * @param: @param insertedDataList
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public Map<String, String> save(PageData pd,List<PageData> insertedDataList)throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		String rtnInfo="success";
		
		dao.save("SysDataMapper.save", pd);
		
		PageData insertPd=null;
		if(insertedDataList!=null && insertedDataList.size()>0) {
			for(int i=0;i<insertedDataList.size();i++) {
				insertPd=insertedDataList.get(i);
				insertPd.put("sysDataIndex",pd.get("sysDataIndex"));
				sysDataDetailService.save(insertPd);
			}
		}
		
		insertPd=null;
		
		map.put("result", rtnInfo);
		return map;
		
	}
	
	
	
	/**
	 * 
	 * @Title: edit
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @param pd
	 * @param: @param insertedDataList
	 * @param: @param updatedDataList
	 * @param: @param deletedDataList
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public Map<String, String> edit(PageData pd,List<PageData> insertedDataList,List<PageData> updatedDataList,List<PageData> deletedDataList)throws Exception{
		
		Map<String,String> map=new HashMap<String,String>();
		String rtnInfo="success";
		
		dao.update("SysDataMapper.edit", pd);
		
		PageData insertPd=null;
		PageData updatePd=null;
		PageData deletePd=null;
		
		//新增数据
		if(insertedDataList!=null && insertedDataList.size()>0) {
			for(int i=0;i<insertedDataList.size();i++) {
				insertPd=insertedDataList.get(i);
				insertPd.put("sysDataIndex",pd.get("sysDataIndex"));
				sysDataDetailService.save(insertPd);
			}
		}
		
		//修改的数据
		if(updatedDataList!=null && updatedDataList.size()>0) {
			for(int i=0;i<updatedDataList.size();i++) {
				updatePd=updatedDataList.get(i);
				sysDataDetailService.edit(updatePd);
			}
		}
		
		
		//删除的数据
		if(deletedDataList!=null && deletedDataList.size()>0) {
			for(int i=0;i<deletedDataList.size();i++) {
				deletePd=deletedDataList.get(i);
				sysDataDetailService.delete(deletePd);;
			}
		}
		
		insertPd=null;
		updatePd=null;
		deletePd=null;
		
		map.put("result", rtnInfo);
		return map;
		
	}
	
	
	/**
	 * @Title:querylistPage
	 * @Description: 列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> querylistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysDataMapper.querylistPage", page);
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
		return (PageData)dao.findForObject("SysDataMapper.findById", pd);
	}
	
	
	/**
	 * 
	 * @Title: findByIndex
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: PageData  
	 * @throws:
	 */
	public PageData findByIndex(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysDataMapper.findByIndex", pd);
	}
	

	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		sysDataDetailService.deleteByKeyId(ArrayDATA_IDS);
		dao.delete("SysDataMapper.deleteAll", ArrayDATA_IDS);
	}

	
}
