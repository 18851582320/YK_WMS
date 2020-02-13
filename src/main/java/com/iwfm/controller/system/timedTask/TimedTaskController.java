package com.iwfm.controller.system.timedTask;

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
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.service.system.timedTask.TimedTaskManager;
import com.iwfm.task.entity.ScheduleJob;
import com.iwfm.util.AppUtil;
import com.iwfm.util.DateUtil;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;

/**
 * ClassName: TimedTaskController 
 * @Description: 定时任务
 * @author yk
 * @date 2018年3月29日
 */
@Controller
@RequestMapping(value="/timedTask")
public class TimedTaskController extends BaseController{
	
	@Resource(name="timedTaskService")
	private TimedTaskManager timedTaskService;
	
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	
	/**
	 * @Title:list
	 * @Description: 访问列表主页
	 * @author yk
	 * @date 2018年3月29日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/list")
	public ModelAndView list()throws Exception{
		ModelAndView mv=this.getModelAndView();
		//获取隐藏列
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("SYS_timedTask","SYS_timedTask");
		mv.addObject("hideLst", columnHideLst);
		mv.setViewName("system/timedTask/timedTask_list");
		return mv;
	}
	
	/**
	 * @Title:querylistPage
	 * @Description: TODO
	 * @author yk
	 * @date 2018年3月29日
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
			
			List<PageData> list=timedTaskService.querylistPage(page);
			
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
	 * @Description: 增加页面
	 * @author yk
	 * @date 2018年3月29日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		mv.addObject("pd",pd);
		mv.addObject("method", "add");
		mv.setViewName("system/timedTask/timedTask_edit");
		return mv;
	}
	
	
	/**
	 * @Title:add
	 * @Description: 新增保存
	 * @author yk
	 * @date 2018年3月29日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object add(){
		logBefore(logger, Jurisdiction.getUsername()+"新增职务");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();			
			pd.put("timedTaskId", this.get32UUID());	//主键
			pd.put("createTime",DateUtil.getTime());
			timedTaskService.save(pd);
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
	 * @Title:toEdit
	 * @Description: 访问编辑页面
	 * @author yk
	 * @date 2018年3月29日
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
			
			pd=timedTaskService.findById(pd);			
			mv.addObject("pd",pd);
			mv.addObject("method","edit");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("system/timedTask/timedTask_edit");
		return mv;
	}
	
	/**
	 * @Title:edit
	 * @Description: 修改保存
	 * @author yk
	 * @date 2018年3月29日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/edit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object edit(){
		logBefore(logger, Jurisdiction.getUsername()+"修改职务");
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			pd.put("updateTime",DateUtil.getTime());
			timedTaskService.edit(pd);
			
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
	 * @Title:delete
	 * @Description: 删除批量删除
	 * @author yk
	 * @date 2018年3月29日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object delete(){
		logBefore(logger, Jurisdiction.getUsername()+"删除职务");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			String timedTaskIdS = pd.getString("timedTaskIdS");
			if(StringUtils.isNotEmpty(timedTaskIdS)){
				String ArraytimedTaskIdS[] = timedTaskIdS.split(",");
					timedTaskService.deleteAll(ArraytimedTaskIdS);
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
	 * @Title:runOnce
	 * @Description: 运行一次任务
	 * @author yk
	 * @date 2018年3月29日
	 * @param @param o
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/runOnce",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object runOnce(ScheduleJob o){
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			int res = timedTaskService.runOnce(o);
			if (res == 1){
				rtnInfo="success";
			}
			if(res == 2){
				
			}
			map.put("result", rtnInfo);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result", rtnInfo);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * @Title:resumeJob
	 * @Description: 启动任务
	 * @author yk
	 * @date 2018年3月29日
	 * @param @param o
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/resumeJob",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object resumeJob(ScheduleJob o){
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			int res = timedTaskService.resumeJob(o);
			if (res == 1){
				rtnInfo="success";
			}
			if(res == 2){
				
			}
			map.put("result", rtnInfo);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result", rtnInfo);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * @Title:pauseJob
	 * @Description: 暂停任务
	 * @author yk
	 * @date 2018年3月29日
	 * @param @param o
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/pauseJob",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object pauseJob(ScheduleJob o){
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			int res = timedTaskService.pauseJob(o);
			if (res == 1){
				rtnInfo="success";
			}
			if(res == 2){
				rtnInfo="notStart";
			}
			map.put("result", rtnInfo);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result", rtnInfo);
		}
		return AppUtil.returnObject(pd, map);
	}
	
}
