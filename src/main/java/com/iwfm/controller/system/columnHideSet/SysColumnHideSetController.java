package com.iwfm.controller.system.columnHideSet;

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
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;

/**
 * ClassName: SysColumnHideSetController 
 * @Description: 系统隐藏列设置
 * @author yk
 * @date 2018年2月11日
 */
@Controller
@RequestMapping(value="/sysColumnHideSet")
public class SysColumnHideSetController extends BaseController{
	
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	/**
	 * @Title:getColumnHideSetList
	 * @Description: 获取系统列并设置隐藏列
	 * @author yk
	 * @date 2018年2月11日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/getColumnHideSet")
	public ModelAndView getColumnHideSet()throws Exception{
		PageData pd=new PageData();
		ModelAndView mv=this.getModelAndView();
		try {
			pd=this.getPageData();
			String userName=Jurisdiction.getUsername();
			pd.put("userName",userName);
			List<PageData> pdLst=sysColumnHideSetService.getColumnHideSetList(pd);
			
			if(pdLst!=null && pdLst.size()>0){
				mv.addObject("pdLstSize", pdLst.size()+1);
			}else{
				mv.addObject("pdLstSize","0");
			}
			
			mv.addObject("pdLst", pdLst);
			mv.setViewName("system/columnHideSet/columnHide_set");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
		
	}
	
	
	/**
	 * @Title:saveColumnHideSet
	 * @Description: 保存隐藏列设置
	 * @author yk
	 * @date 2018年2月11日
	 * @param @return
	 * @param @throws Exception   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/saveColumnHideSet",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object saveColumnHideSet()throws Exception{
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
				if(isChecked.equals("true")){//设置隐藏列，先判断有没有，有的话就不添加
					pd2=sysColumnHideSetService.findBySysData(pd);
					if(pd2!=null){
					
					}else{//没有添加一个信息的
						pd.put("sysColumnHideSetId", this.get32UUID());
						sysColumnHideSetService.save(pd);
					}
				}
				if(isChecked.equals("false")){//不设置为查询条件，直接删除
					sysColumnHideSetService.deleteBySysData(pd);
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
	
	
}
