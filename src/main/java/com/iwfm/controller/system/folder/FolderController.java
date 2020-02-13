package com.iwfm.controller.system.folder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;







import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.controller.base.BaseController;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.service.system.folder.FolderManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.DateUtil;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;

/**
 * ClassName: FolerDocController 
 * @Description: TODO
 * @author yk
 * @date 2018年8月2日
 */
@Controller
@RequestMapping(value="/folder")
public class FolderController extends BaseController{
	
	@Resource(name="folderService")
	private FolderManager folderService;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	
	/**
	 * @Title:index
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @return   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/index")
	public ModelAndView index(){
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		try {
			pd.put("folderId", "1");
			pd=folderService.findById(pd);
			if(pd==null){//初始化第一季菜单
				pd=new PageData();
				folderService.saveInit(pd);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		mv.setViewName("system/folder/folder_index");
		return mv;
	}
	
	
	/**
	 * @Title:menuListIndex
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping({"/listIndex"})
	public ModelAndView listIndex()throws Exception
	{
		PageData pd=new PageData();
		pd=this.getPageData();
		
	    ModelAndView mv = getModelAndView();
	    //获取隐藏列
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("WD_folderDoc","WD_folderDoc");
		mv.addObject("folderId",pd.getString("folderId"));
		mv.addObject("hideLst", columnHideLst);
	    mv.setViewName("system/folder/folder_list");
	    return mv;
	}
	
	
	/**
	 * @Title:getTree
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="getTree")
	@ResponseBody
	public Object getTree(){
		JSONArray jsonArray=new JSONArray();
		JSONObject jo=new JSONObject();
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			pd.put("folderId", "1");
			pd=folderService.findById(pd);
			if(pd!=null){
				jo.put("id",pd.getString("folderId"));
				jo.put("text",pd.getString("folderName"));
				
				pd.put("parentFolderId",pd.getString("folderId"));
				
				List<PageData> pdLst=folderService.listByParentFolderId(pd);
				if(pdLst!=null && pdLst.size()>0){
					jo.put("children",this.resolveTree(pdLst));
				}
				jsonArray.add(jo);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return jsonArray;
	}
	
	
	/**
	 * @Title:loadChildFolder
	 * @Description: 获取子文件夹
	 * @author yk
	 * @date 2018年8月2日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="loadChildFolder")
	@ResponseBody
	public Object loadChildFolder(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			List<PageData> pdLst=folderService.listByParentFolderId(pd);
			String json=JsonUtil.list2json(pdLst);
			return json;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return null;
	}
	
	/**
	 * @Title:loadParentFolder
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="loadParentFolder")
	@ResponseBody
	public Object loadParentFolder(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			pd=folderService.findById(pd);
			String json=JsonUtil.object2json(pd);
			return json;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return null;
	}
	
	/**
	 * @Title:resolveBomVersion
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @param pdLst
	 * @param @return   
	 * @return JSONArray  
	 * @throws
	 */
	private JSONArray resolveTree(List<PageData> pdLst){
		JSONArray jsonArray=new JSONArray();
		JSONObject jo=null;
		PageData pd=new PageData();
		
		try {
			for(int i=0;i<pdLst.size();i++){
				jo=new JSONObject();
				pd=pdLst.get(i);
				if(pd!=null){
					
					jo.put("id",pd.getString("folderId"));
					jo.put("text",pd.getString("folderName"));
					
					pd.put("parentFolderId",pd.getString("folderId"));
					
					List<PageData> pdLstChild=folderService.listByParentFolderId(pd);
					if(pdLstChild!=null && pdLstChild.size()>0){
						jo.put("children",this.resolveTree(pdLstChild));
					}
					jsonArray.add(jo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	
	/**
	 * 
	 * @Title:toAdd
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		PageData parentPd=new PageData();
		try {
			pd=this.getPageData();
			String folderId=pd.getString("folderId");
			parentPd=folderService.findById(pd);
			
			pd.put("parentFolderId", folderId);
			pd.put("parentFolderName", parentPd.getString("folderName"));
			pd.put("createUser", Jurisdiction.getSTAFF_ID());
			pd.put("createUserName", Jurisdiction.getSTAFF_NAME());
			pd.put("createTime",DateUtil.getTime());
			
			mv.addObject("pd",pd);
			mv.addObject("method","add");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("system/folder/folder_edit");
		return mv;
	}
	
	
	/**
	 * 
	 * @Title:add
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object add(){
		logBefore(logger, Jurisdiction.getUsername()+"新增文档文件夹");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();			
			pd.put("folderId", this.get32UUID());	//主键
			
			if(folderService.findByFolderName(pd)==null){
				folderService.save(pd);
				rtnInfo="success";
				map.put("result", rtnInfo);
			}else{
				rtnInfo="namePeat";
				map.put("result", rtnInfo);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result", rtnInfo);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * 
	 * @Title:toEdit
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @return   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit(){
		
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		PageData parentPd=new PageData();
		try {
			pd=this.getPageData();
			pd=folderService.findById(pd);
			
			parentPd.put("folderId", pd.getString("parentFolderId"));
			parentPd=folderService.findById(parentPd);
			
			pd.put("parentFolderName", parentPd.getString("folderName"));
			pd.put("updateUser", Jurisdiction.getSTAFF_ID());
			pd.put("updateUserName", Jurisdiction.getSTAFF_NAME());
			pd.put("updateTime",DateUtil.getTime());
			
			mv.addObject("pd",pd);
			mv.addObject("method","edit");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("system/folder/folder_edit");
		return mv;
	}
	
	/**
	 * 
	 * @Title:edit
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/edit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object edit(){
		logBefore(logger, Jurisdiction.getUsername()+"修改文档文件夹");
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			if(folderService.findByFolderName(pd)==null){
				folderService.edit(pd);
				rtnInfo="success";
				map.put("result", rtnInfo);
			}else{
				rtnInfo="namePeat";
				map.put("result", rtnInfo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result", rtnInfo);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 
	 * @Title:delete
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object delete(){
		logBefore(logger, Jurisdiction.getUsername()+"删除文档文件夹");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			String keyIdS = pd.getString("keyIdS");
			if(StringUtils.isNotEmpty(keyIdS)){
				String ArrayKeyIdS[] = keyIdS.split(",");
				folderService.deleteAll(ArrayKeyIdS);
				rtnInfo="success";
			}
			
			map.put("result", rtnInfo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result", rtnInfo);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
}
