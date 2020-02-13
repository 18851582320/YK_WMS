package com.iwfm.service.system.sysDataSet;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.iwfm.entity.Page;
import com.iwfm.util.PageData;

/**
 * 
 * ClassName: SysDataSetManager 
 * @Description: TODO
 * @author yk
 * @date: 2019年1月24日
 */
public interface SysDataSetManager {

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
	 * @return: Map<String,String>  
	 * @throws:
	 */
	public Map<String, String> save(PageData pd,CommonsMultipartFile []files)throws Exception;
	
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
	 * @return: Map<String,String>  
	 * @throws:
	 */
	public Map<String, String> edit(PageData pd,CommonsMultipartFile []files)throws Exception;
	
	/**
	 * 
	 * @Title: delete
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	public void delete(PageData pd)throws Exception;
	
	
	/**
	 * 
	 * @Title: findById
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: PageData  
	 * @throws:
	 */
	public PageData findById(PageData pd)throws Exception;
	
	
	
	/**
	 * 
	 * @Title: querylistPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param page
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> querylistPage(Page page)throws Exception;
	
	
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
	public List<PageData> listAll(PageData pd)throws Exception;
	
	
	/**
	 * 
	 * @Title: deleteAll
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param ArrayDATA_IDS
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * @Title: findByCodeAndValue
	 * @Description: 通过编码和值查询
	 * @author: sr
	 * @date: 2019年1月25日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws: 
	 */
	public List<PageData> findByCodeAndValue(PageData pd) throws Exception;
	

	/**
	 * @Title: findByCode
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月27日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws: 
	 */
	public List<PageData> findByCode(PageData pd) throws Exception;
	
	
}
