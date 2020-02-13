package com.iwfm.service.system.folder.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.folder.FolderManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.util.Const;
import com.iwfm.util.DateUtil;
import com.iwfm.util.FileUtil;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;

/**
 * ClassName: FolderService 
 * @Description: TODO
 * @author yk
 * @date 2018年8月2日
 */

@Service("folderService")
public class FolderService implements FolderManager{
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="logService")
	private LogManager logService;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;

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
	public void save(PageData pd) throws Exception {
		String parentFolderId=pd.getString("parentFolderId");//父文件夹id
		String folderName=pd.getString("folderName");//文件夹名称
		String directory=folderName;
		directory=getDir(parentFolderId, directory);
		
		boolean Tag=FileUtil.createDir(directory);
		if(Tag){
			dao.save("FolderMapper.save", pd);
		}
	}
	
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
	public String getDir(String parentFolderId,String directory)throws Exception{
		PageData pd=new PageData();
		pd.put("folderId", parentFolderId);
		pd=findById(pd);
		String folderId=pd.getString("folderId");
		parentFolderId=pd.getString("parentFolderId");
		if(folderId.equals("1")){
			directory=pd.getString("folderMemo")+"/"+directory;//如果是顶级文件夹，去特殊值，如：D:/
		}else{
			directory=pd.getString("folderName")+"/"+directory;
			directory=getDir(parentFolderId, directory);
		}
		return directory;
	}
	
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
	public String getDirByFolderId(String folderId,String directory)throws Exception{
		PageData pd=new PageData();
		pd.put("folderId", folderId);
		pd=findById(pd);
		folderId=pd.getString("folderId");
		String parentFolderId=pd.getString("parentFolderId");
		if(folderId.equals("1")){
			directory=pd.getString("folderMemo")+"/"+directory;//如果是顶级文件夹，去特殊值，如：D:/
		}else{
			directory=pd.getString("folderName")+"/"+directory;
			directory=getDirByFolderId(parentFolderId, directory);
		}
		return directory;
	}
	
	
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
	public void saveInit(PageData pd) throws Exception {
		String folderSetName=Tools.readTxtFile(Const.FolderSet);
		String folderName=folderSetName.substring(folderSetName.lastIndexOf(":/")+2,folderSetName.length());
		boolean Tag=FileUtil.createDir(folderSetName);
		if(Tag){
			pd.put("folderId", "1");
			pd.put("folderName", folderName);
			pd.put("folderMemo", folderSetName);
			pd.put("parentFolderId", "0");
			pd.put("createUser", "admin");
			pd.put("createTime", DateUtil.getTime());
			pd.put("updateUser", "admin");
			pd.put("updateTime", DateUtil.getTime());
			dao.save("FolderMapper.save", pd);
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
		PageData pdYuan=new PageData();
		pdYuan.put("folderId",pd.getString("folderId"));
		pdYuan=findById(pdYuan);
		
		String parentFolderId=pd.getString("parentFolderId");//父文件夹id
		String folderName=pd.getString("folderName");//文件夹名称
		String directory=folderName;
		directory=getDir(parentFolderId, directory);
		
		folderName=pdYuan.getString("folderName");//文件夹名称
		String yuanDirectory=folderName;
		yuanDirectory=getDir(parentFolderId, yuanDirectory);
		boolean tag=FileUtil.fileReName(yuanDirectory, directory);
		if(tag){
			dao.update("FolderMapper.edit", pd);
		}
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
		return (List<PageData>)dao.findForList("FolderMapper.querylistPage", page);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listByParentFolderId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FolderMapper.listByParentFolderId", pd);
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
		return (PageData)dao.findForObject("FolderMapper.findById", pd);
	}
	
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
	public PageData findByFolderName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FolderMapper.findByFolderName", pd);
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
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("FolderMapper.deleteAll", ArrayDATA_IDS);
		
	}
}
