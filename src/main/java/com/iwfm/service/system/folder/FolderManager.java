package com.iwfm.service.system.folder;

import java.util.List;

import com.iwfm.entity.Page;
import com.iwfm.util.PageData;

/**
 * ClassName: FolderManager 
 * @Description: TODO
 * @author yk
 * @date 2018年8月2日
 */
public interface FolderManager {
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
	public void save(PageData pd)throws Exception;
	
	/**
	 * @Title:getDir
	 * @Description: 获取目录
	 * @author yk
	 * @date 2018年8月2日
	 * @param @param directory
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 */
	public String getDir(String parentFolderId,String directory)throws Exception;
	
	/**
	 * @Title:getDirByFolderId
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月3日
	 * @param @param folderId
	 * @param @param path
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 */
	public String getDirByFolderId(String folderId,String directory)throws Exception;
	
	
	/**
	 * @Title:saveInit
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void saveInit(PageData pd)throws Exception;
	
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
	 * @Title:listByParentFolderId
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listByParentFolderId(PageData pd)throws Exception;	
	
	
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
	 * @Title:findByFolderName
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月3日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByFolderName(PageData pd)throws Exception;

	
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
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
}
