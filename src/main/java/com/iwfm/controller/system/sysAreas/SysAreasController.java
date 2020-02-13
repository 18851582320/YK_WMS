package com.iwfm.controller.system.sysAreas;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iwfm.controller.base.BaseController;
import com.iwfm.service.system.sysAreas.SysAreasManager;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.PageData;


@Controller
@RequestMapping("/sysAreas")
public class SysAreasController extends BaseController {
	
	@Resource(name="sysAreasService")
	private SysAreasManager sysAreasService;
	
	@RequestMapping(value="/initProvince")
	@ResponseBody
	public String initProvince(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			List<PageData> list=sysAreasService.initProvince(pd);
			
			String json=JsonUtil.list2json(list);
			return json;
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/getCity")
	@ResponseBody
	public String getCity(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			List<PageData> list=sysAreasService.getCity(pd);
			
			String json=JsonUtil.list2json(list);
			return json;
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/getArea")
	@ResponseBody
	public String getArea(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			
			List<PageData> list=sysAreasService.getArea(pd);
			
			String json=JsonUtil.list2json(list);
			return json;
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return null;
	}

}
