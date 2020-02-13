package com.iwfm.service.system.columnHideSet;

import java.util.List;

import com.iwfm.entity.Page;
import com.iwfm.util.PageData;


/**
 * ClassName: SysColumnShowSetManager 
 * @Description: 列表显示设置
 * @author yk
 * @date 2018年2月11日
 */
public interface SysColumnHideSetManager {
	
	/**
	 * @Title:save
	 * @Description: 新增
	 * @author yk
	 * @date 2018年2月11日
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
	 * @date 2018年2月11日
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
	 * @date 2018年2月11日
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
	 * @date 2018年2月11日
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
	 * @date 2018年2月11日
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
	 * @date 2018年2月11日
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
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findBySysData(PageData pd)throws Exception;
	
	/**
	 * @Title:getColumnHide
	 * @Description: 获取隐藏列
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> getColumnHide(String sysDataIndex,String sysDataTableName)throws Exception;
	
	/**
	 * @Title:getColumnHideSetList
	 * @Description: 隐藏列设置
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> getColumnHideSetList(PageData pd)throws Exception;

	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年2月11日
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
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteBySysData(PageData pd)throws Exception;
}
