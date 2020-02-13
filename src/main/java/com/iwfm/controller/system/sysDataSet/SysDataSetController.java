package com.iwfm.controller.system.sysDataSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.sysDataSet.SysDataSetManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;

import net.sf.json.JSONObject;

/**
 * 
 * ClassName: SysDataSetController 
 * @Description: TODO
 * @author yk
 * @date: 2019年1月24日
 */
@Controller
@RequestMapping(value="/sysDataSet")
public class SysDataSetController extends BaseController{
	
	@Resource(name="sysDataSetService")
	private SysDataSetManager sysDataSetService;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
	@Resource(name="logService")
	private LogManager logService;
	
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	
	@RequestMapping(value="/list")
	public ModelAndView list()throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();
		ModelAndView mv=this.getModelAndView();
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("SYS_dataSet","SYS_dataSet");
		mv.addObject("hideLst", columnHideLst);
		mv.addObject("pd",pd);
		mv.setViewName("system/sysDataSet/sysDataSet_list");
		return mv;
	}
	
	
	/**
	 * 
	 * @Title: querylistPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月24日
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
			
			pd.put("exportToExcelTag","0");//非导出
			pd.put("sysDataIndex","SYS_dataSet");//查询索引
			
			
			String pageNumber=pd.getString("page");//当前页
			if(Tools.notEmpty(pageNumber)){
				page.setCurrentPage(Integer.valueOf(pageNumber));
			}
			String pageSize=pd.getString("rows");//每页数量
			if(Tools.notEmpty(pageSize)){
				page.setShowCount(Integer.valueOf(pageSize));
			}
			
			page.setPd(pd);
			
			List<PageData> list=sysDataSetService.querylistPage(page);
			
			
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
	 * @Title: listAll
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年1月24日
	 * @param: @return   
	 * @return: String  
	 * @throws: 
	 */
	@RequestMapping(value="/listAll")
	@ResponseBody
	public String listAll(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			List<PageData> list=sysDataSetService.listAll(pd);
			String json=JsonUtil.list2json(list);
			return json;
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
	 * @date: 2019年1月24日
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: ModelAndView  
	 * @throws:
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		mv.addObject("method", "add");
		mv.addObject("pd",pd);
		mv.setViewName("system/sysDataSet/sysDataSet_edit");
		return mv;
	}
	
	
	/**
	 * 
	 * @Title: add
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月24日
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
			pd.put("dataSetId", this.get32UUID());	//主键
			map=sysDataSetService.save(pd,files);
			
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
	 * @date: 2019年1月24日
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
			pd=sysDataSetService.findById(pd);
			mv.addObject("pd",pd);
			mv.addObject("method","edit");
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("system/sysDataSet/sysDataSet_edit");
		return mv;
	}
	
	
	/**
	 * 
	 * @Title: edit
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月24日
	 * @param: @param files
	 * @param: @param request
	 * @param: @return   
	 * @return: Object  
	 * @throws:
	 */
	@RequestMapping(value="/edit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object edit(@RequestParam(value="uploadFile",required=false) CommonsMultipartFile []files,HttpServletRequest request){
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageDataByReq(request);
			map=sysDataSetService.edit(pd,files);
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
	 * @date: 2019年1月24日
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
					sysDataSetService.deleteAll(ArrayKeyIdS);
					rtnInfo="success";
				}
				else{
					rtnInfo="canNotDelete";
					map.put("errmsg","已下达的数据不能删除!");
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
