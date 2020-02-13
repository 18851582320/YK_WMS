package com.iwfm.controller.system.onlinemanager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.controller.base.BaseController;

@Controller
@RequestMapping(value="/onlinemanager")
public class OnlineManagerController extends BaseController {
	@RequestMapping(value="/list")
	public ModelAndView list()throws Exception{
		logBefore(logger, "列表OnlineManager");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/onlinemanager/onlinemanager_list");
		return mv;
	}
}
