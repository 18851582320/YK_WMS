package com.iwfm.controller.system.sysDataDetail;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iwfm.controller.base.BaseController;
import com.iwfm.service.system.sysDataDetail.SysDataDetailManager;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.PageData;

/**
 * 
 * ClassName: SysDataDetailController
 * @Description: TODO
 * @author yk
 * @date: 2018年9月28日
 */
@Controller
@RequestMapping(value="/sysDataDetail")
public class SysDataDetailController extends BaseController{
	
	
	@Resource(name="sysDataDetailService")
	private SysDataDetailManager sysDataDetailService;
	
	/**
	 * 
	 * @Title: listAllByKeyId
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月28日
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	@RequestMapping(value="/listAllByKeyId")
	@ResponseBody
	public String listAllByKeyId(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			List<PageData> list=sysDataDetailService.listAllByKeyId(pd);
			String json=JsonUtil.list2json(list);
			return json;
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return null;
	}
	
	
}
