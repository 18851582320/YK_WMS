package com.iwfm.controller.oa.notice;

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
import org.springframework.web.servlet.support.RequestContext;

import com.iwfm.controller.base.BaseController;
import com.iwfm.entity.Page;
import com.iwfm.service.oa.notice.NoticeManager;
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.DateUtil;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;

/**
 * ClassName: NoticeController 
 * @Description: 公司公告
 * @author yk
 * @date 2018年3月30日
 */
@Controller
@RequestMapping(value="/notice")
public class NoticeController extends BaseController{
	
	@Resource(name="noticeService")
	private NoticeManager noticeService;
	
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	
	/**
	 * @Title:list
	 * @Description: 访问列表主页
	 * @author yk
	 * @date 2018年3月30日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/list")
	public ModelAndView list()throws Exception{
		ModelAndView mv=this.getModelAndView();
		//获取隐藏列
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("OA_notice","OA_notice");
		mv.addObject("hideLst", columnHideLst);
		
		//获取国际化配置的列表头
		RequestContext req = new RequestContext(this.getRequest());
		
		mv.addObject("common_add", req.getMessage("common_add"));
		mv.addObject("common_edit", req.getMessage("common_edit"));
		mv.addObject("common_import", req.getMessage("common_import"));
		mv.addObject("common_export", req.getMessage("common_export"));
		mv.addObject("common_query", req.getMessage("common_query"));
		mv.addObject("common_delete", req.getMessage("common_delete"));
		mv.addObject("common_confirm", req.getMessage("common_confirm"));
		mv.addObject("common_confirm", req.getMessage("common_confirm"));
		mv.addObject("common_queryConditionSet", req.getMessage("common_queryConditionSet"));
		mv.addObject("common_columnHideSet", req.getMessage("common_columnHideSet"));
		mv.addObject("common_Operation", req.getMessage("common_Operation"));
		mv.addObject("common_createUser", req.getMessage("common_createUser"));
		mv.addObject("common_createTime", req.getMessage("common_createTime"));
		mv.addObject("common_updateUser", req.getMessage("common_updateUser"));
		mv.addObject("common_updateTime", req.getMessage("common_updateTime"));
		mv.addObject("common_alert", req.getMessage("common_alert"));
		mv.addObject("common_alertDelete", req.getMessage("common_alertDelete"));
		mv.addObject("common_alertRecordNum", req.getMessage("common_alertRecordNum"));
		mv.addObject("common_alertDeleteOk", req.getMessage("common_alertDeleteOk"));
		mv.addObject("common_alertDeleteError", req.getMessage("common_alertDeleteError"));
		mv.addObject("common_alertDeleteErrorGuanLian", req.getMessage("common_alertDeleteErrorGuanLian"));
		mv.addObject("common_alertDeleteNoSelect", req.getMessage("common_alertDeleteNoSelect"));
		mv.addObject("common_loadMsg", req.getMessage("common_loadMsg"));
		
		//具体业务
		mv.addObject("notice_", req.getMessage("notice_"));
		mv.addObject("notice_noticeTitle", req.getMessage("notice_noticeTitle"));
		mv.addObject("notice_isStop", req.getMessage("notice_isStop"));
		mv.addObject("notice_endTime", req.getMessage("notice_endTime"));
		mv.addObject("notice_noticeContent", req.getMessage("notice_noticeContent"));
		
		
		mv.setViewName("oa/notice/notice_list");
		
		return mv;
	}
	
	
	
	/**
	 * @Title:querylistPage
	 * @Description: TODO
	 * @author yk
	 * @date 2018年3月30日
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
			
			List<PageData> list=noticeService.querylistPage(page);
			
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
	 * @Title:listAll
	 * @Description: 获取全部数据
	 * @author yk
	 * @date 2018年3月30日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value="/listAll")
	@ResponseBody
	public String listAll(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			List<PageData> list=noticeService.listAll(pd);
			String json=JsonUtil.list2json(list);
			return json;
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
	 * @date 2018年3月30日
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
		mv.setViewName("oa/notice/notice_edit");
		return mv;
	}
	
	
	/**
	 * @Title:add
	 * @Description: 新增保存
	 * @author yk
	 * @date 2018年3月30日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object add(){
		logBefore(logger, Jurisdiction.getUsername()+"新增公告");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();			
			pd.put("noticeId", this.get32UUID());	//主键
			pd.put("createUser", Jurisdiction.getSTAFF_ID());
			pd.put("createTime",DateUtil.getTime());
			
			noticeService.save(pd);
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
	 * @date 2018年3月30日
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
			pd=noticeService.findById(pd);			
			mv.addObject("pd",pd);
			mv.addObject("method","edit");
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("oa/notice/notice_edit");
		return mv;
	}
	
	/**
	 * @Title:edit
	 * @Description: 修改保存
	 * @author yk
	 * @date 2018年3月30日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/edit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object edit(){
		logBefore(logger, Jurisdiction.getUsername()+"修改公告");
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();			
			noticeService.edit(pd);
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
	 * @date 2018年3月30日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object delete(){
		logBefore(logger, Jurisdiction.getUsername()+"删除公告");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			String noticeIdS = pd.getString("noticeIdS");
			if(StringUtils.isNotEmpty(noticeIdS)){
				String ArraynoticeIdS[] = noticeIdS.split(",");
				boolean checkTag=true;
				for(int i=0;i<ArraynoticeIdS.length;i++){
					//判断公告有没有被引用
				}
				if(checkTag){
					noticeService.deleteAll(ArraynoticeIdS);
					rtnInfo="success";
				}
				else{
					rtnInfo="canNotDelete";
				}
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
	
	
}
