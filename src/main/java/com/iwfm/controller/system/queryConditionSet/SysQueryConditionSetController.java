package com.iwfm.controller.system.queryConditionSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.controller.base.BaseController;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.queryConditionSet.SysQueryConditionSetManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;

/**
 * ClassName: SysQueryConditionSetController 
 * @Description: 查询条件设置
 * @author yk
 * @date 2018年2月7日
 */
@Controller
@RequestMapping(value="/sysQueryConditionSet")
public class SysQueryConditionSetController extends BaseController{
	
	@Resource(name="sysQueryConditionSetService")
	private SysQueryConditionSetManager sysQueryConditionSetService;
	
	@Resource(name="logService")
	private LogManager logService;
	
	/**
	 * @Title:getQueryCondition
	 * @Description: 获取查询条件
	 * @author yk
	 * @date 2018年2月7日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/getQueryCondition")
	public ModelAndView getQueryCondition()throws Exception{
		PageData pd=new PageData();
		ModelAndView mv=this.getModelAndView();
		try {
			pd=this.getPageData();
			String userName=Jurisdiction.getUsername();
			pd.put("userName",userName);
			
			List<PageData> pdLst=sysQueryConditionSetService.getQueryCondition(pd);
			
			mv.addObject("pdLst", pdLst);
			mv.setViewName("system/queryConditionSet/queryCondition_select");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	
	/**
	 * @Title:queryConditionSet
	 * @Description: 查询条件设置
	 * @author yk
	 * @date 2018年2月7日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/queryConditionSet")
	public ModelAndView queryConditionSet()throws Exception{
		PageData pd=new PageData();
		ModelAndView mv=this.getModelAndView();
		try {
			pd=this.getPageData();
			String userName=Jurisdiction.getUsername();
			pd.put("userName",userName);
			List<PageData> pdLst=sysQueryConditionSetService.getQueryConditionSetList(pd);
			mv.addObject("pdLst", pdLst);
			
			if(pdLst!=null && pdLst.size()>0){
				mv.addObject("pdLstSize", pdLst.size()+1);
			}else{
				mv.addObject("pdLstSize","0");
			}
			mv.setViewName("system/queryConditionSet/queryCondition_set");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
		
	}
	
	/**
	 * @Title:saveQueryConditionSet
	 * @Description: 保存查询条件设置
	 * @author yk
	 * @date 2018年2月7日
	 * @param @return
	 * @param @throws Exception   
	 * @return Object  
	 * @throws
	 */
	
	@RequestMapping(value="/saveQueryConditionSet",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object saveQueryConditionSet()throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"设置查询条件");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		PageData pd2 = new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			String userName=Jurisdiction.getUsername();
			pd.put("userName",userName);
			
			String isChecked=pd.getString("isChecked");
			if(StringUtils.isNotEmpty(isChecked)){
				if(isChecked.equals("true")){//设置查询条件，先判断有没有，有的话就不添加
					pd2=sysQueryConditionSetService.findBySysData(pd);
					if(pd2!=null){
					
					}else{//没有添加一个信息的
						pd.put("sysQueryConSetId", this.get32UUID());
						sysQueryConditionSetService.save(pd);
						
					}
				}
				if(isChecked.equals("false")){//不设置为查询条件，直接删除
					sysQueryConditionSetService.deleteBySysData(pd);
				}
			}
			rtnInfo="success";
			map.put("result", rtnInfo);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			rtnInfo="error";
			map.put("result", rtnInfo);
			
			throw new RuntimeException(e);
		}
		
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * @Title:listUserForStaff
	 * @Description: 获取特殊的下拉列表
	 * @author yk
	 * @date 2018年3月29日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value="/specialSelect")
	@ResponseBody
	public String listUserForStaff(){
		PageData pd=new PageData();
		String json="";
		try {
			pd=this.getPageData();
			List<PageData> list=sysQueryConditionSetService.specialSelect(pd);
			json=JsonUtil.list2json(list);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return json;
	}
	
	
}
