package com.iwfm.service.system.folderDoc;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.iwfm.entity.Page;
import com.iwfm.util.PageData;

/**
 * ClassName: FolderManager 
 * @Description: TODO
 * @author yk
 * @date 2018年8月2日
 */
public interface FolderDocManager {
	/**
	 * @Title:save
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月02日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void save(PageData pd,CommonsMultipartFile []files)throws Exception;
	
	/**
	 * @Title:edit
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月02日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title:querylistPage
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月02日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> querylistPage(Page page)throws Exception;
	
	/**
	 * @Title:listByFolderAndName
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月3日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listByFolderAndName(PageData pd)throws Exception;
	
	/**
	 * @Title:listByFolderAndName
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月3日
	 * @param @param ArrayKeyIdS
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listByFolderIds(String[] ArrayKeyIdS)throws Exception;
	
	
	/**
	 * 
	 * @Title:findById
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月02日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * @Title:findMaxId
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月3日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findMaxId(PageData pd)throws Exception;
	

	
	/**
	 * 
	 * @Title:deleteAll
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月02日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteAll(String[] ArrayKeyIds)throws Exception;
	
	/**
	 * @Title:deleteByKeys
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月3日
	 * @param @param ArrayKeyIdS
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteByKeys(String[] ArrayKeyIds) throws Exception;
}
