package com.iwfm.service.system.sysData;



import java.util.List;
import java.util.Map;

import com.iwfm.entity.Page;
import com.iwfm.util.PageData;

/**
 * 
 * ClasaisaiName: SysDataManager
 * @Description: TODO
 * @author yk
 * @date: 2018年9月29日
 */
public interface SysDataManager {

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
	 * @return: Map<String,String>  
	 * @throws:
	 */
	public Map<String, String> save(PageData pd,List<PageData> insertedDataList)throws Exception;
	
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
	 * @return: Map<String,String>  
	 * @throws:
	 */
	public Map<String, String> edit(PageData pd,List<PageData> insertedDataList,List<PageData> updatedDataList,List<PageData> deletedDataList)throws Exception;
	
	
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
	public List<PageData> querylistPage(Page page)throws Exception;
	
	
	
	/**
	 * @Title:findById
	 * @Description: 通过id查找数据
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception;
	
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
	public PageData findByIndex(PageData pd)throws Exception;
	
	
	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
}
