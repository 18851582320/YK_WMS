package com.iwfm.controller.system.sysSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.controller.base.BaseController;
import com.iwfm.service.system.sysSet.SysSetManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;

/**
 * ClassName: SysSetController 
 * @Description: 系统设置
 * @author yk
 * @date 2018年6月5日
 */
@Controller
@RequestMapping(value="/sysSet")
public class SysSetController extends BaseController{
	@Resource(name="sysSetService")
	private SysSetManager sysSetService;
	
	/**
	 * @Title:list
	 * @Description: 访问列表主页
	 * @author yk
	 * @date 2018年6月5日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/list")
	public ModelAndView list()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		List<PageData> pdLst=sysSetService.listAll(pd);
		
		if(pdLst!=null && pdLst.size()>0){
			for(int i=0;i<pdLst.size();i++){
				pd=pdLst.get(i);
				mv.addObject(pd.getString("setFieldCode"), pd.getString("setFieldValue"));
			}
		}
		
		mv.setViewName("system/sysSet/sysSet_list");
		return mv;
	}
	
	
	/**
	 * @Title:edit
	 * @Description: 修改保存
	 * @author yk
	 * @date 2018年6月5日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/edit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object edit(){
		logBefore(logger, Jurisdiction.getUsername()+"修改系统参数");
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			sysSetService.edit(pd);
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
	
	
	
}
