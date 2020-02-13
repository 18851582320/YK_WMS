package com.iwfm.service.system.queryConditionSet;

import java.util.List;

import com.iwfm.entity.Page;
import com.iwfm.util.PageData;


/**
 * ClassName: SysQueryConditionSetManager 
 * @Description: 查询条件设置
 * @author yk
 * @date 2018年2月7日
 */
public interface SysQueryConditionSetManager {
	
	/**
	 * @Title:save
	 * @Description: 新增
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * @Title:delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * @Title:edit
	 * @Description: 修改
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * @Title:listPage
	 * @Description: 列表
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listPage(Page page)throws Exception;
	
	
	/**
	 * @Title:listAll
	 * @Description: 列表（全部）
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * @Title:findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * @Title:findBySysData
	 * @Description: 
	 * @author yk
	 * @date 2018年2月8日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findBySysData(PageData pd)throws Exception;
	
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
	public List<PageData> getQueryCondition(PageData pd)throws Exception;
	
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
	public List<PageData> getQueryConditionSetList(PageData pd)throws Exception;

	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年2月7日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 
	 * @Title:deleteBySysData
	 * @Description: TODO
	 * @author yk
	 * @date 2018年2月8日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteBySysData(PageData pd)throws Exception;
	
	
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
	public List<PageData> specialSelect(PageData pd)throws Exception;
}
