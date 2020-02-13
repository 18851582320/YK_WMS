package com.iwfm.controller.system.sysData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.controller.base.BaseController;
import com.iwfm.entity.Page;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.sysData.SysDataManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;

/**
 * 
 * ClassName: SysDataController
 * @Description: TODO
 * @author yk
 * @date: 2018年9月29日
 */

@Controller
@RequestMapping(value="/sysData")
public class SysDataController extends BaseController{
	
	@Resource(name="sysDataService")
	private SysDataManager sysDataService;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
	@Resource(name="logService")
	private LogManager logService;
	
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	
	/**
	 * 
	 * @Title: list
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: ModelAndView  
	 * @throws:
	 */
	@RequestMapping(value="/list")
	public ModelAndView list()throws Exception{
		ModelAndView mv=this.getModelAndView();
		mv.setViewName("system/sysData/sysData_list");
		return mv;
	}
	
	
	/**
	 * 
	 * @Title: querylistPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @return   
	 * @return: String  
	 * @throws:
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
			
			List<PageData> list=sysDataService.querylistPage(page);
			
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
	 * @Title: toAdd
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: ModelAndView  
	 * @throws:
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		
		mv.addObject("method", "add");
		mv.addObject("pd",pd);
		
		mv.setViewName("system/sysData/sysData_edit");
		return mv;
	}
	
	
	/**
	 * 
	 * @Title: add
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @return   
	 * @return: Object  
	 * @throws:
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object add(){
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		List<PageData> insertedDataList=new ArrayList<PageData>();
		String rtnInfo="";
		try {
			pd=this.getPageData();			
			
			String insertedStr=pd.getString("insertedStr");
			PageData pd2=sysDataService.findByIndex(pd);
			if(pd2!=null)
			{
				rtnInfo="codeRepeat";
				map.put("result", rtnInfo);
			}else {
				if(StringUtils.isNotEmpty(insertedStr) && !"[]".equals(insertedStr)){//增加
					insertedDataList=getListPageDataFromJsonStr(insertedStr, "inserted", "sysDataId", "", "sysDataDetailId");
				}
				map=sysDataService.save(pd,insertedDataList);
			}
			
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
	 * 
	 * @Title: toEdit
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @return   
	 * @return: ModelAndView  
	 * @throws:
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit(){
		
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			pd=sysDataService.findById(pd);
			mv.addObject("pd",pd);
			mv.addObject("method","edit");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("system/sysData/sysData_edit");
		return mv;
	}
	
	/**
	 * 
	 * @Title: edit
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @return   
	 * @return: Object  
	 * @throws:
	 */
	@RequestMapping(value="/edit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object edit(){
		logBefore(logger, Jurisdiction.getUsername()+"其他入库修改");
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		List<PageData> insertedDataList=null;
		List<PageData> deletedDataList=null;
		List<PageData> updatedDataList=null;
		try {
			pd=this.getPageData();
			String insertedStr=pd.getString("insertedStr");
			String updatedStr=pd.getString("updatedStr");
			String deletedStr=pd.getString("deletedStr");
			if(StringUtils.isNotEmpty(insertedStr) && !"[]".equals(insertedStr)){//增加
				insertedDataList=this.getListPageDataFromJsonStr(insertedStr, "inserted", "sysDataId", "", "sysDataDetailId");				
			}
			if(StringUtils.isNotEmpty(updatedStr) && !"[]".equals(updatedStr)){//修改
				updatedDataList=this.getListPageDataFromJsonStr(updatedStr, "updated", "sysDataId","", "sysDataDetailId");				
			}
			if(StringUtils.isNotEmpty(deletedStr) && !"[]".equals(deletedStr)){//删除
				deletedDataList=this.getListPageDataFromJsonStr(deletedStr, "deleted", "sysDataId","", "sysDataDetailId");
				
			}
			map=sysDataService.edit(pd,insertedDataList,updatedDataList,deletedDataList);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			if(map.size()==0){
				map.put("errmsg",e.getMessage());
				rtnInfo="error";
				map.put("result", rtnInfo);
			}
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 
	 * @Title: deleteAll
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @return   
	 * @return: Object  
	 * @throws:
	 */
	@RequestMapping(value="/deleteAll",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object deleteAll(){
		logBefore(logger, Jurisdiction.getUsername()+"删除其他入库单");
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
					sysDataService.deleteAll(ArrayKeyIdS);
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
