package com.iwfm.service.system.folderDoc.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.folder.FolderManager;
import com.iwfm.service.system.folderDoc.FolderDocManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.util.FileDelete;
import com.iwfm.util.FileUpload;
import com.iwfm.util.PageData;
import com.iwfm.util.UuidUtil;

/**
 * ClassName: FolderDocService 
 * @Description: TODO
 * @author yk
 * @date 2018年8月2日
 */

@Service("folderDocService")
public class FolderDocService implements FolderDocManager{
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="logService")
	private LogManager logService;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;
	
	@Resource(name="folderService")
	private FolderManager folderService;

	/**
	 * 
	 * @Title:save
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void save(PageData pd,CommonsMultipartFile []files) throws Exception {
		if(files!=null && files.length>0){
			String folderDocName="";
			String folderDocRealName="";
			String name="";
			String suffix="";
			String dealType=pd.getString("dealType");
			String directory="";//文件上传路径
			int folderDocVersion=0;
			List<PageData> pdLst=null;
			
			PageData pdVersion=new PageData();
			
			directory=folderService.getDirByFolderId(pd.getString("folderId"), directory);
			boolean tag=false;
			for(int i=0;i<files.length;i++){
				pd.put("folderDocId", UuidUtil.get32UUID());	//主键
				
				folderDocName=files[i].getOriginalFilename();
				name=folderDocName.substring(0, folderDocName.lastIndexOf("."));
				suffix=folderDocName.substring(folderDocName.lastIndexOf("."),folderDocName.length());
				
				pd.put("folderDocName", folderDocName);
				
				//获取版本信息
				pdVersion=findMaxId(pd);
				folderDocVersion=Integer.parseInt(pdVersion.getString("folderDocVersion"))+1;
				
				pd.put("folderDocVersion",folderDocVersion );
				folderDocRealName=name+"-"+String.valueOf(folderDocVersion)+suffix;
				pd.put("folderDocRealName",folderDocRealName);
				
				if(dealType.equals("1")){//有相同文件时，保留先前所有版本文件，生成新版本文件
					tag=FileUpload.fileUpload(files[i], directory, folderDocRealName.substring(0, folderDocRealName.lastIndexOf(".")));
					if(tag){
						dao.save("FolderDocMapper.save", pd);
					}
					
				}else if(dealType.equals("0")){//有相同文件时，删除所有先前版本文件，生成新版本文件
					pdLst=listByFolderAndName(pd);
					String ArrayDATA_IDS[]=new String[1];
					if(pdLst!=null && pdLst.size()>0){
						PageData delPd=new PageData();
						for(int z=0;z<pdLst.size();z++){
							delPd=pdLst.get(z);
							tag=FileDelete.delFile(directory+delPd.getString("folderDocRealName"));
							if(tag){
								ArrayDATA_IDS[0]=delPd.getString("folderDocId");
								deleteAll(ArrayDATA_IDS);
							}
						}
						if(tag){
							tag=FileUpload.fileUpload(files[i], directory, folderDocRealName.substring(0, folderDocRealName.lastIndexOf(".")));
							if(tag){
								dao.save("FolderDocMapper.save", pd);
							}
						}
					}else{
						tag=FileUpload.fileUpload(files[i], directory, folderDocRealName.substring(0, folderDocRealName.lastIndexOf(".")));
						if(tag){
							dao.save("FolderDocMapper.save", pd);
						}
					}
				}
				
			}
		}
	}

	/**
	 * 
	 * @Title:edit
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("FolderDocMapper.edit", pd);
	}

	/**
	 * 
	 * @Title:querylistPage
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> querylistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("FolderDocMapper.querylistPage", page);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listByFolderAndName(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FolderDocMapper.listByFolderAndName", pd);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listByFolderIds(String[] ArrayKeyIdS)throws Exception{
		return (List<PageData>)dao.findForList("FolderDocMapper.listByFolderIds", ArrayKeyIdS);
	}

	/**
	 * 
	 * @Title:findById
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("FolderDocMapper.findById", pd);
	}
	
	
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
	public PageData findMaxId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FolderDocMapper.findMaxId", pd);
	}


	/**
	 * @Title:deleteAll
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteAll(String[] ArrayKeyIds) throws Exception {
		dao.delete("FolderDocMapper.deleteAll", ArrayKeyIds);
	}
	
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
	public void deleteByKeys(String[] ArrayKeyIds) throws Exception {
		String directory="";//文件路径
		List<PageData> pdLst=null;
		
		PageData pd=new PageData();
		pd.put("folderDocId", ArrayKeyIds[0]);
		pd=findById(pd);
		directory=folderService.getDirByFolderId(pd.getString("folderId"), directory);
		
		boolean tag=false;
		pdLst=listByFolderIds(ArrayKeyIds);
		String ArrayDATA_IDS[]=new String[1];
		
		if(pdLst!=null && pdLst.size()>0){
			PageData delPd=new PageData();
			for(int z=0;z<pdLst.size();z++){
				delPd=pdLst.get(z);
				tag=FileDelete.delFile(directory+delPd.getString("folderDocRealName"));
				if(tag){
					ArrayDATA_IDS[0]=delPd.getString("folderDocId");
					deleteAll(ArrayDATA_IDS);
				}
			}
		}
	}
	
	
}
