package com.iwfm.service.system.uploadFile;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.iwfm.entity.Page;
import com.iwfm.util.PageData;

/**
 * ClassName: UploadFileManager 
 * @Description: 系统附件
 * @author yk
 * @date 2018年4月18日
 */
public interface UploadFileManager {

	/**
	 * @Title:save
	 * @Description: 新增
	 * @author yk
	 * @date 2018年4月18日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void save(PageData pd)throws Exception;
	
	
	/**
	 * 
	 * @Title: saveAppFile
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月11日
	 * @param: @param pd
	 * @param: @param mRequest
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: Map<String,String>  
	 * @throws:
	 */
	public Map<String, String> saveAppFile(PageData pd,MultipartHttpServletRequest mRequest)throws Exception;
	
	
	/**
	 * @Title:saveUploadFile
	 * @Description: 保存上传文件
	 * @author yk
	 * @date 2018年4月18日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void saveUploadFile(PageData pd,CommonsMultipartFile []files)throws Exception;
	
	/**
	 * 
	 * @Title: saveUploadSingleFile
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月23日
	 * @param: @param pd
	 * @param: @param file
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	public void saveUploadSingleFile(PageData pd,MultipartFile file)throws Exception;
	
	/**
	 * @Title:delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年4月18日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void delete(PageData pd)throws Exception;
	
	
	/**
	 * @Title:querylistPage
	 * @Description: 列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年4月18日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> querylistPage(Page page)throws Exception;
	
	/**
	 * 
	 * @Title: downloadlistPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年10月9日
	 * @param: @param page
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> downloadlistPage(Page page)throws Exception;
	
	/**
	 * 
	 * @Title: downloadlistPageByStageId
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年11月13日
	 * @param: @param page
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> downloadlistPageByStageId(Page page)throws Exception;
	
	/**
	 * @Title:listAll
	 * @Description: TODO
	 * @author yk
	 * @date 2018年5月17日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * @Title: listAllById
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年3月1日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws: 
	 */
	public List<PageData> listAllById(String[] ArrayDATA_IDS) throws Exception;
	
	/**
	 * 
	 * @Title: listByKey
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> listByKey(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title:listByKeyValue
	 * @Description: TODO
	 * @author yk
	 * @date 2018年9月26日
	 * @param @param ArrayDATA_IDS
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listByKeyValue(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * @Title:findById
	 * @Description: 通过id查找数据
	 * @author yk
	 * @date 2018年4月18日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception;
	
	
	
	
	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年4月18日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
}
