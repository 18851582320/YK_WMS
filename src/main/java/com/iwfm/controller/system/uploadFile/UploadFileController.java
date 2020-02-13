package com.iwfm.controller.system.uploadFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.service.system.uploadFile.UploadFileManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.Const;
import com.iwfm.util.FileDownload;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.PathUtil;
import com.iwfm.util.Tools;

/**
 * ClassName: UploadFileController 
 * @Description: 系统附件
 * @author yk
 * @date 2018年4月18日
 */
@Controller
@RequestMapping(value="/uploadFile")
public class UploadFileController extends BaseController{
	
	@Resource(name="uploadFileService")
	private UploadFileManager uploadFileService;
	
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	/**
	 * 
	 * @Title: toAddFile
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年11月13日
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: ModelAndView  
	 * @throws:
	 */
	@RequestMapping(value="/toAddFile")
	public ModelAndView toAddFile()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		mv.addObject("method", "add");
		mv.addObject("pd",pd);
		
		mv.setViewName("system/uploadFile/uploadFile_add");
		return mv;
	}
	
	
	/**
	 * 
	 * @Title: toAddTemplateFile
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月29日
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: ModelAndView  
	 * @throws:
	 */
	@RequestMapping(value="/toAddTemplateFile")
	public ModelAndView toAddTemplateFile()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		mv.addObject("method", "add");
		mv.addObject("pd",pd);
		
		mv.setViewName("system/uploadFile/uploadFile_addTemplate");
		return mv;
	}
	
	
	
	/**
	 * 
	 * @Title: add
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年11月13日
	 * @param: @param files
	 * @param: @param request
	 * @param: @return   
	 * @return: Object  
	 * @throws:
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object add(@RequestParam(value="uploadFile",required=false) CommonsMultipartFile []files,HttpServletRequest request){
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageDataByReq(request);
			
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;	  //文件上传路径  
			pd.put("filePath",filePath);
			pd.put("filePathTable",Const.FILEPATHFILE);
			pd.put("userId",Jurisdiction.getUserId());
			pd.put("staffId",Jurisdiction.getSTAFF_ID());
			
			uploadFileService.saveUploadFile(pd, files);
			rtnInfo="success";
			map.put("result", rtnInfo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			if(map.size()==0)
			{
				map.put("errmsg",e.getMessage());
				rtnInfo="error";
				map.put("result", rtnInfo);
			}
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * @Title:list
	 * @Description: 访问列表主页
	 * @author yk
	 * @date 2018年4月18日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/list")
	public ModelAndView list()throws Exception{
		ModelAndView mv=this.getModelAndView();
		//获取隐藏列
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("SYS_uploadFile","SYS_uploadFile");
		mv.addObject("hideLst", columnHideLst);
		mv.setViewName("system/uploadFile/uploadFile_list");
		return mv;
	}
	
	
	/**
	 * 
	 * @Title: downloadlist
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年10月9日
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: ModelAndView  
	 * @throws:
	 */
	@RequestMapping(value="/downloadlist")
	public ModelAndView downloadlist()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("SYS_uploadFile_download","SYS_uploadFile_download");
		mv.addObject("hideLst", columnHideLst);
		mv.addObject("pd",pd);
		mv.setViewName("system/uploadFile/uploadFile_download");
		return mv;
	}
	
	
	/**
	 * 
	 * @Title: downloadlistPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年10月9日
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	@RequestMapping(value="/downloadlistPage")
	@ResponseBody
	public String downloadlistPage(){
		PageData pd=new PageData();
		Page page=new Page();
		try {
			pd=this.getPageData();
			
			pd.put("exportToExcelTag","0");//非导出
			pd.put("sysDataIndex","SYS_uploadFile_download");//查询索引
			
			String pageNumber=pd.getString("page");//当前页
			if(Tools.notEmpty(pageNumber)){
				page.setCurrentPage(Integer.valueOf(pageNumber));
			}
			String pageSize=pd.getString("rows");//每页数量
			if(Tools.notEmpty(pageSize)){
				page.setShowCount(Integer.valueOf(pageSize));
			}
			
			page.setPd(pd);
			
			List<PageData> list=uploadFileService.downloadlistPage(page);
			
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
	 * @Title: downloadlistPageByStageId
	 * @Description: 项目的阶段文件
	 * @author: HB-PC-042
	 * @date: 2018年11月13日
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	@RequestMapping(value="/downloadlistPageByStageId")
	@ResponseBody
	public String downloadlistPageByStageId(){
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
			
			List<PageData> list=uploadFileService.downloadlistPageByStageId(page);
			
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
	 * @Title:querylistPage
	 * @Description: TODO
	 * @author yk
	 * @date 2018年4月18日
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
			
			List<PageData> list=uploadFileService.querylistPage(page);
			
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
	 * @Title:toAdd
	 * @Description: 选择上传页面
	 * @author yk
	 * @date 2018年4月18日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/toUpload")
	public ModelAndView toAdd()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		
		String controllerName=pd.getString("controllerName");
		mv.addObject("pd", pd);
		mv.addObject("controllerName", controllerName);
		mv.setViewName("system/uploadFile/uploadFile");
		return mv;
	}
	
	/**
	 * @Title:uploadFileSave
	 * @Description: 上传附件保存
	 * @author yk
	 * @date 2018年4月18日
	 * @param @param file
	 * @param @return
	 * @param @throws Exception   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/uploadFileSave",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object uploadFileSave(@RequestParam(value="uploadFile",required=false) CommonsMultipartFile []files,@RequestParam(value="tableName",required=false) String tableName,@RequestParam(value="tableKeyValue",required=false) String tableKeyValue) throws Exception{
		Map<String,String > map=new HashMap<String,String>();
		String rtnInfo = "";
		PageData pd = new PageData();
		if (null != files && files.length>0)//文件不为空 
		{
			try {
				
				
				String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;	  //文件上传路径  (后面做成可配置)
				pd.put("filePath",filePath);
				pd.put("filePathTable",Const.FILEPATHFILE);
				pd.put("userId",Jurisdiction.getUserId());
				pd.put("staffId",Jurisdiction.getSTAFF_ID());
				pd.put("tableName",tableName);
				pd.put("tableKeyValue",tableKeyValue);
				
				uploadFileService.saveUploadFile(pd, files);
				
				rtnInfo="success";
				map.put("result", rtnInfo);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				map.put("errmsg",e.getMessage());
				rtnInfo="error";
				map.put("result",rtnInfo);
			}
		}
		
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * @Title:preview
	 * @Description: 图片预览
	 * @author yk
	 * @date 2018年5月17日
	 * @param @return   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="preview")
	public ModelAndView preview(){
		PageData pd=this.getPageData();
		ModelAndView mv=this.getModelAndView();
		mv.addObject("pd",pd);
		mv.setViewName("system/uploadFile/uploadFile_show");
		return mv;
	}
	
	
	/**
	 * @Title:previewPage
	 * @Description: 文件预览分页数据
	 * @author yk
	 * @date 2018年5月17日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="previewPage")
	@ResponseBody
	public Object previewPage(){
		JSONArray jsonArray=new JSONArray();
		PageData pd=new PageData();
		try {
			
			pd=this.getPageData();
			//方式1：通过主键浏览附件
			String upFileIdS=pd.getString("upFileIdS");  
			
			//方式2： 通过tableName和tableKeyValue浏览附件
			String tableName=pd.getString("tableName");
			String tableKeyValue=pd.getString("tableKeyValue");
			
			//方式3：其他的通过查询类型【queryType】
			String queryType=pd.getString("queryType");
			String queryKey=pd.getString("queryKey");
			
			if(StringUtils.isNotEmpty(upFileIdS)){//方式1
				String  upFileIdArry[]=upFileIdS.split(",");
				
				List<PageData> pdLst=uploadFileService.listAllById(upFileIdArry);
				if(pdLst!=null && pdLst.size()>0){
					jsonArray=JSONArray.fromObject(pdLst);
				}
			}else if(StringUtils.isNotEmpty(tableName)){//方式2
				
			}else if(StringUtils.isNotEmpty(queryType)){//方式3
				if(queryType.equals("ceshi")){
					List<PageData> pdLst=uploadFileService.listAll(pd);
					if(pdLst!=null && pdLst.size()>0){
						jsonArray=JSONArray.fromObject(pdLst);
					}
				}
				if(queryType.equals("productFile")){
					List<PageData> pdLst=null;
					String typeTag=pd.getString("typeTag");
					
					if(pdLst!=null && pdLst.size()>0){
						
						String[] keyValueArr=new String[pdLst.size()];
						for(int i=0;i<pdLst.size();i++){
							keyValueArr[i]=pdLst.get(i).getString("fileManageId");
						}
						
						pdLst=null;
						pdLst=uploadFileService.listByKeyValue(keyValueArr);
						jsonArray=JSONArray.fromObject(pdLst);
					}
						
				}
					
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	/**
	 * @Title:preview
	 * @Description: 文件预览
	 * @author yk
	 * @date 2018年5月18日
	 * @param @return   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="previewFile")
	public ModelAndView previewFile(){
		PageData pd=new PageData();
		ModelAndView mv=this.getModelAndView();
		try {
			pd=this.getPageData();
			String fileRealName=pd.getString("fileRealName");
			//String fileUrl=pd.getString("fileUrl");
			//String fileDivH=pd.getString("fileDivH");
			if(StringUtils.isNotEmpty(fileRealName)){
				String suffix=fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());
				if(suffix.equals(".avi") || suffix.equals(".rmvb") || suffix.equals(".rm") || suffix.equals(".mp4") || suffix.equals(".mkv")){
					mv.setViewName("system/uploadFile/uploadFile_movie");
				}else{
					mv.setViewName("system/uploadFile/uploadFile_file");
				}
				mv.addObject("pd",pd);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mv;
	}
	
	
	/**
	 * 
	 * @Title: downloadFile
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年10月9日
	 * @param: @param response
	 * @param: @param controllerName
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	@RequestMapping(value="/downloadFile")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response)throws Exception{
		PageData pd=new PageData();
		pd=this.getPageDataByReq(request);
		pd=uploadFileService.findById(pd);
		try {
			FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + pd.getString("fileRealName"), pd.getString("fileName"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	/**
	 * 
	 * @Title: deleteAll
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年10月10日
	 * @param: @return   
	 * @return: Object  
	 * @throws:
	 */
	@RequestMapping(value="/deleteAll",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object deleteAll(){
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			String keyIdS = pd.getString("keyIdS");
			if(StringUtils.isNotEmpty(keyIdS)){
				String ArrayKeyIdS[] = keyIdS.split(",");
				boolean checkTag=true;
				if(checkTag){
					uploadFileService.deleteAll(ArrayKeyIdS);
					rtnInfo="success";
				}
				else{
					rtnInfo="canNotDelete";
				}
			}
			
			map.put("result", rtnInfo);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result", rtnInfo);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
}
