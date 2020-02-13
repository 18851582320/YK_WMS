package com.iwfm.controller.system.coderule;

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
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.DateUtil;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;

/**
 * ClassName: CodeRuleController 
 * @Description: 编码规则管理
 * @author yk
 * @date 2018年1月19日
 */
@Controller
@RequestMapping(value="/codeRule")
public class CodeRuleController extends BaseController{
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	
	
	/**
	 * @Title:list
	 * @Description: 方位列表主页
	 * @author yk
	 * @date 2018年1月19日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/list")
	public ModelAndView list()throws Exception{
		ModelAndView mv=this.getModelAndView();
		//获取隐藏列
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("SYS_codeRuleSet","SYS_codeRuleSet");
		mv.addObject("hideLst", columnHideLst);
		mv.setViewName("system/codeRule/codeRule_list");
		return mv;
	}
	
	/**
	 * @Title:listPage
	 * @Description: 列表
	 * @author yk
	 * @date 2018年1月19日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value="/listPage")
	@ResponseBody
	public String listPage(){
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
			
			List<PageData> list=codeRuleService.listPage(page);
			
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
	 * @Title:querylistPage
	 * @Description: 编码规则列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月9日
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
			
			List<PageData> list=codeRuleService.querylistPage(page);
			
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
	 * @date 2018年1月19日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd()throws Exception{
		ModelAndView mv=this.getModelAndView();
		mv.addObject("method", "add");
		mv.setViewName("system/codeRule/codeRule_edit");
		return mv;
	}
	
	
	/**
	 * @Title:add
	 * @Description: 新增保存
	 * @author yk
	 * @date 2018年1月19日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object add(){
		logBefore(logger, Jurisdiction.getUsername()+"新增编码规则");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			pd.put("codeRuleId", this.get32UUID());	//主键
			pd.put("seq_one","0");	
			pd.put("seq_two", "0");	
			pd.put("seq_three","0");
			
			pd.put("createUser",Jurisdiction.getSTAFF_ID());
			pd.put("createTime",DateUtil.getTime());
			
			codeRuleService.save(pd);
			
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
	 * @date 2018年1月19日
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
			
			pd=codeRuleService.findById(pd);
			
			mv.addObject("pd",pd);
			mv.addObject("method","edit");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("system/codeRule/codeRule_edit");
		return mv;
	}
	
	/**
	 * @Title:edit
	 * @Description: 修改保存
	 * @author yk
	 * @date 2018年1月19日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/edit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object edit(){
		logBefore(logger, Jurisdiction.getUsername()+"修改编码规则");
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			pd.put("updateUser",Jurisdiction.getSTAFF_ID());
			pd.put("updateTime",DateUtil.getTime());
			
			codeRuleService.edit(pd);
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
	 * @Description: 删除，批量删除
	 * @author yk
	 * @date 2018年1月19日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object delete(){
		logBefore(logger, Jurisdiction.getUsername()+"删除编码规则");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			String codeRuleIdS = pd.getString("codeRuleIdS");
			if(StringUtils.isNotEmpty(codeRuleIdS)){
				String ArrayBUTTON_IDS[] = codeRuleIdS.split(",");
				codeRuleService.deleteAll(ArrayBUTTON_IDS);
			}
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
	 * @Title:getFormCodeByRule
	 * @Description: 获取表单字段流水号
	 * @author yk
	 * @date 2018年1月24日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/formCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getFormCodeByRule(){
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			String tableName = pd.getString("tableName");
			String fieldName = pd.getString("fieldName");
			
			String codeRtn[]=codeRuleService.getFormCodeByRule(tableName, fieldName);
			
			if(codeRtn!=null){
				rtnInfo="success";
				map.put("codeRuleType", codeRtn[0]);
				map.put("code", codeRtn[1]);
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
