
package com.iwfm.service.system.uploadFile.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.oa.staff.StaffManager;
import com.iwfm.service.system.uploadFile.UploadFileManager;
import com.iwfm.util.Const;
import com.iwfm.util.DateUtil;
import com.iwfm.util.FileUpload;
import com.iwfm.util.PageData;
import com.iwfm.util.PathUtil;
import com.iwfm.util.UuidUtil;

/**
 * ClassName: UploadFileService 
 * @Description: 系统附件
 * @author yk
 * @date 2018年4月18日
 */
@Service(value="uploadFileService")
public class UploadFileService implements UploadFileManager{
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="staffService")
	private StaffManager staffService;
	
	
	/**
	 * @Title:save
	 * @Description: 增加
	 * @author yk
	 * @date 2018年4月18日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UploadFileMapper.save", pd);
	}
	
	
	
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
	public Map<String, String> saveAppFile(PageData pd,MultipartHttpServletRequest mRequest)throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		String rtnInfo="success";
		
		String account=pd.getString("account");
		PageData staffPd=new PageData();
		staffPd.put("USERNAME",account);
		staffPd=staffService.findByUSERNAME(staffPd);
		if(staffPd!=null) {
			String STAFF_ID=staffPd.getString("STAFF_ID");
			
			Iterator<String> files = mRequest.getFileNames();
	        PageData filePd=new PageData();
	        while (files.hasNext()) {
	            MultipartFile mFile = mRequest.getFile(files.next());
	            filePd=new PageData();
	            String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;	  //文件上传路径  
				filePd.put("filePath",filePath);
				filePd.put("filePathTable",Const.FILEPATHFILE);
				filePd.put("userId",STAFF_ID);
				filePd.put("staffId",STAFF_ID);
				filePd.put("tableName",pd.getString("tableName"));
				filePd.put("tableKeyValue",pd.getString("tableKeyValue"));
	            
				this.saveUploadSingleFile(filePd, mFile);
	        }
			
		}else {
			map.put("errmsg","未查询到人员信息");
			rtnInfo="error";
			map.put("result", rtnInfo);
		}
		
		map.put("result", rtnInfo);
		return map;
	}
	
	
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
	public void saveUploadFile(PageData pd,CommonsMultipartFile []files)throws Exception{
		String filePath=pd.getString("filePath");
		PageData pd2=new PageData();
		for(int i=0;i<files.length;i++){
			String originalFileName=files[i].getOriginalFilename();
			if(StringUtils.isNotEmpty(originalFileName)){
				String fileNamePre=UuidUtil.get32UUID();
				String fileName =  FileUpload.fileUp(files[i], filePath, fileNamePre);//执行上传
				
				pd2.put("upFileId", UuidUtil.get32UUID());
				pd2.put("tableName",pd.getString("tableName"));
				pd2.put("tableKeyValue",pd.getString("tableKeyValue"));
				pd2.put("filePath",pd.getString("filePathTable"));
				pd2.put("fileRealName", fileName);
				pd2.put("fileName", originalFileName);
				pd2.put("fileSize", "");
				if(StringUtils.isNotEmpty(pd.getString("fileType"))) {
					pd2.put("fileType",pd.getString("fileType"));
				}else {
					pd2.put("fileType", "0");
				}
				pd2.put("uploadTime",DateUtil.getTime());
				pd2.put("userId",pd.getString("userId"));
				pd2.put("staffId",pd.getString("staffId"));
				
				dao.save("UploadFileMapper.save", pd2);
			}
		}
		
	}
	
	
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
	public void saveUploadSingleFile(PageData pd,MultipartFile file)throws Exception{
		String filePath=pd.getString("filePath");
		PageData pd2=new PageData();
		String originalFileName=file.getOriginalFilename();
		if(StringUtils.isNotEmpty(originalFileName)){
			String fileNamePre=UuidUtil.get32UUID();
			String fileName =  FileUpload.fileUp(file, filePath, fileNamePre);//执行上传
			
			pd2.put("upFileId", UuidUtil.get32UUID());
			pd2.put("tableName",pd.getString("tableName"));
			pd2.put("tableKeyValue",pd.getString("tableKeyValue"));
			pd2.put("filePath",pd.getString("filePathTable"));
			pd2.put("fileRealName", fileName);
			pd2.put("fileName", originalFileName);
			pd2.put("fileSize", "");
			pd2.put("uploadTime",DateUtil.getTime());
			pd2.put("userId",pd.getString("userId"));
			pd2.put("staffId",pd.getString("staffId"));
			
			dao.save("UploadFileMapper.save", pd2);
		}
		
	}
	
	
	/**
	 * @Title:delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年4月18日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("UploadFileMapper.delete", pd);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> querylistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UploadFileMapper.querylistPage", page);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> downloadlistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UploadFileMapper.downloadlistPage", page);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> downloadlistPageByStageId(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UploadFileMapper.downloadlistPageByStageId", page);
	}
	
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UploadFileMapper.listAll", pd);
	}
	
	/**
	 * @Title: listAllById
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年3月1日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllById(String[] ArrayDATA_IDS) throws Exception{
		return (List<PageData>)dao.findForList("UploadFileMapper.listAllById", ArrayDATA_IDS);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listByKey(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UploadFileMapper.listByKey", pd);
	}
	
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listByKeyValue(String[] ArrayDATA_IDS)throws Exception{
		return (List<PageData>)dao.findForList("UploadFileMapper.listByKeyValue", ArrayDATA_IDS);
	}
	
	
	/**
	 * @Title:findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2018年4月18日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UploadFileMapper.findById", pd);
	}
	

	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年4月18日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UploadFileMapper.deleteAll", ArrayDATA_IDS);
	}
	
}
