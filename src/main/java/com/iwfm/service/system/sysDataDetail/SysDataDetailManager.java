package com.iwfm.service.system.sysDataDetail;

import java.util.List;

import com.iwfm.util.PageData;

/**
 * 
 * ClasaisaiName: SysDataManager
 * @Description: TODO
 * @author yk
 * @date: 2018年9月29日
 */
public interface SysDataDetailManager {

	/**
	 * 
	 * @Title: save
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * @Title:edit
	 * @Description: 修改
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * @Title:delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void delete(PageData pd)throws Exception;
	

	
	/**
	 * @Title:listAllByKeyId
	 * @Description: 列表全部
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listAllByKeyId(PageData pd)throws Exception;
	
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
	 * @Title:deleteByKeyId
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年9月29日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteByKeyId(String[] ArrayDATA_IDS)throws Exception;
}
