package com.iwfm.controller.system.folderDoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.controller.base.BaseController;
import com.iwfm.entity.Page;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.service.system.folder.FolderManager;
import com.iwfm.service.system.folderDoc.FolderDocManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.DateUtil;
import com.iwfm.util.FileDownload;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;

/**
 * ClassName: FolerDocController 
 * @Description: TODO
 * @author yk
 * @date 2018年8月2日
 */
@Controller
@RequestMapping(value="/folderDoc")
public class FolderDocController extends BaseController{
	
	@Resource(name="folderDocService")
	private FolderDocManager folderDocService;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	@Resource(name="folderService")
	private FolderManager folderService;
	
	
	/**
	 * 
	 * @Title:querylistPage
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月2日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value="/querylistPage")
	@ResponseBody
	public String querylistPage(){
		PageData pd=new PageData();
		Page page=new Page();
		try {
			pd=this.getPageData();
			
			String pageNumber=pd.getString("page");//当前页
			if(Tools.notEmpty(pageNumber)){
				page.setCurrentPage(Integer.valueOf(pageNumber));
			}
			String pageSize=pd.getString("rows");//每页数量
			if(Tools.notEmpty(pageSize)){
				page.setShowCount(Integer.valueOf(pageSize));
			}
			
			page.setPd(pd);
			
			List<PageData> list=folderDocService.querylistPage(page);
			
			String json=JsonUtil.list2json(list);
			JSONObject jo=new JSONObject();
			jo.put("total", Integer.valueOf(page.getTotalResult()));
			jo.put("rows", json);
			return jo.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return null;
		
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
		PageData folderPd=new PageData();
		try {
			pd=this.getPageData();
			String folderId=pd.getString("folderId");
			folderPd=folderService.findById(pd);
			
			pd.put("folderId", folderId);
			pd.put("folderName", folderPd.getString("folderName"));
			pd.put("createUser", Jurisdiction.getSTAFF_ID());
			pd.put("createUserName", Jurisdiction.getSTAFF_NAME());
			pd.put("createTime",DateUtil.getTime());
			
			mv.addObject("pd",pd);
			mv.addObject("method","add");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("system/folderDoc/folderDoc_edit");
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
	public Object add(@RequestParam(value="uploadFile",required=false) CommonsMultipartFile []files,HttpServletRequest request){
		logBefore(logger, Jurisdiction.getUsername()+"新增文档");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageDataByReq(request);			
			folderDocService.save(pd,files);
			rtnInfo="success";
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
		try {
			pd=this.getPageData();
			pd=folderDocService.findById(pd);			
			mv.addObject("pd",pd);
			mv.addObject("method","edit");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("system/folderDoc/folderDoc_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"修改文档");
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();			
			folderDocService.edit(pd);
			rtnInfo="success";
			map.put("result", rtnInfo);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除文档");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			String keyIdS = pd.getString("keyIdS");
			if(StringUtils.isNotEmpty(keyIdS)){
				String ArrayKeyIdS[] = keyIdS.split(",");
				folderDocService.deleteByKeys(ArrayKeyIdS);
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
	
	/**
	 * @Title:download
	 * @Description: TODO
	 * @author yk
	 * @date 2018年8月3日
	 * @param @param response
	 * @param @param folderDocId
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	@RequestMapping(value="/download")
	public void download(HttpServletResponse response,@RequestParam String folderDocId) throws Exception{
		PageData pd=new PageData();
		pd.put("folderDocId", folderDocId);
		pd=folderDocService.findById(pd);
		String directory="";
		if(pd!=null){
			String folderId=pd.getString("folderId");
			directory=folderService.getDirByFolderId(folderId, directory);
		}
		FileDownload.fileDownload(response,directory+"/"+pd.getString("folderDocRealName"),pd.getString("folderDocName"));
	}
	
}
