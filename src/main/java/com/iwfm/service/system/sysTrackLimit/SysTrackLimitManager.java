package com.iwfm.service.system.sysTrackLimit;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.iwfm.entity.Page;
import com.iwfm.util.PageData;

/**
 * 
 * ClassName: SysTrackLimitManager 
 * @Description: TODO
 * @author yk
 * @date: 2019年1月24日
 */
public interface SysTrackLimitManager {

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
	 * @Title: saveByDept
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月24日
	 * @param: @param pd
	 * @param: @param files
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: Map<String,String>  
	 * @throws:
	 */
	public Map<String, String> saveByDept(PageData pd,CommonsMultipartFile []files)throws Exception;
	
	/**
	 * 
	 * @Title: saveByUser
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月25日
	 * @param: @param pd
	 * @param: @param files
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: Map<String,String>  
	 * @throws:
	 */
	public Map<String, String> saveByUser(PageData pd,CommonsMultipartFile []files)throws Exception;
	
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
	 * @Title: findByUserAndTrackLimitCode
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月25日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: PageData  
	 * @throws:
	 */
	public PageData findByUserAndTrackLimitCode(PageData pd)throws Exception;
	
	
	
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
	 * @Title: listByMainUserAndType
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月24日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> listByMainUserAndType(PageData pd)throws Exception;
	
	
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
	
	
}
