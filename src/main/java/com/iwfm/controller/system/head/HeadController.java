package com.iwfm.controller.system.head;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iwfm.controller.base.BaseController;
import com.iwfm.entity.Page;
import com.iwfm.service.system.sms.SmsManager;
import com.iwfm.service.system.user.UserManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.Const;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;

/**
 * ClassName: HeadController 
 * @Description: main head信息管理
 * @author yk
 * @date 2017年9月1日
 */
@Controller
@RequestMapping(value="/head")
public class HeadController extends BaseController{
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="smsService")
	private SmsManager smsService;
	
	/**
	 * @Title: getHeadInfo
	 * @Description: 获取顶部相关信息
	 * @author yk
	 * @date 2017年9月1日
	 * @param @return
	 * @return Object
	 * @throws
	 */
	@RequestMapping(value="/getHeadInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getHeadInfo(){
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String rtnInfo="";
		try {
			pd =this.getPageData(); 
			
			List<PageData> pdList = new ArrayList<PageData>();
			Session session = Jurisdiction.getSession();
			
			PageData pds = new PageData();
			pds = (PageData)session.getAttribute(Const.SESSION_userpds);
			if(null == pds){
				pd.put("USERNAME", Jurisdiction.getUsername());//当前登录者用户名
				pds = userService.findByUsername(pd);
				session.setAttribute(Const.SESSION_userpds, pds);
			}
			pdList.add(pds);
			map.put("list", pdList);
			/*
			PageData pdPhoto = userphotoService.findById(pds);
			map.put("userPhoto", null == pdPhoto?"static/ace/avatars/user.jpg":pdPhoto.getString("PHOTO2"));//用户头像
			map.put("smsCount", smsService.findsmsCount(Jurisdiction.getUsername()).get("smsCount").toString());//站内信未读总数
			*/
			
			String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);//读取WEBSOCKET配置
			if(null != strWEBSOCKET && !"".equals(strWEBSOCKET)){
				String strIW[] = strWEBSOCKET.split(",IWFM,");
				if(strIW.length == 5){
					map.put("wimadress", strIW[0]+":"+strIW[1]);	//即时聊天服务器IP和端口
					map.put("oladress", strIW[2]+":"+strIW[3]);		//在线管理和站内信服务器IP和端口
					map.put("smsSound", strIW[4]);				//站内信提示音效配置
				}
			}
			
			rtnInfo="success";
			map.put("result", rtnInfo);
			
		} catch (Exception e) {
			logger.error(e.toString(), e);
			map.put("errmsg", e.getMessage());
			rtnInfo="error";
			map.put("result", rtnInfo);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 
	 * @Title:getRemindInfo
	 * @Description: TODO
	 * @author yk
	 * @date 2018年9月4日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="getRemindInfo")
	@ResponseBody
	public Object getRemindInfo(){
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd=this.getPageData();
			
			pd.put("USERNAME", Jurisdiction.getUsername());
			pd.put("toUser", Jurisdiction.getSTAFF_ID());
			
			List<PageData> list=null;
			
			//获取当前登录人员的待办任务数量
			/*
			List<PageData> list=runningFlowService.listByUserName(pd);
			if(list!=null && list.size()>0){
				map.put("taskNum", list.size());
			}else{
				map.put("taskNum",0);
			}*/
			
			//获取当前登录人员的未完成任务数
			pd.put("taskReveiveStaffId", Jurisdiction.getSTAFF_ID());
			
			Page page=new Page();
			page.setCurrentPage(1);
			page.setShowCount(10);
			page.setPd(pd);

			list=smsService.queryNotRead(pd);
			if(list!=null && list.size()>0){
				map.put("smsNum", list.size());
			}else{
				map.put("smsNum",0);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			map.put("errmsg", e.getMessage());
		}
		return map;
	}
	
	
}
