package com.iwfm.controller.oa.staff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.controller.base.BaseController;
import com.iwfm.entity.Page;
import com.iwfm.service.oa.department.DepartmentManager;
import com.iwfm.service.oa.duty.DutyManager;
import com.iwfm.service.oa.staff.StaffManager;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.service.system.role.RoleManager;
import com.iwfm.service.system.user.UserManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.Const;
import com.iwfm.util.FileUpload;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.ObjectExcelRead;
import com.iwfm.util.ObjectExcelView;
import com.iwfm.util.PageData;
import com.iwfm.util.PathUtil;
import com.iwfm.util.Tools;

/**
 * ClassName: StaffController 
 * @Description: 员工管理
 * @author yk
 * @date 2018年1月25日
 */
@Controller
@RequestMapping(value="/staff")
public class StaffController extends BaseController{
	
	@Resource(name="staffService")
	private StaffManager staffService;
	
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;
	
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="roleService")
	private RoleManager roleService;
	
	@Resource(name="dutyService")
	private DutyManager dutyService;

	/**
	 * @Title:list
	 * @Description: 访问列表主页
	 * @author yk
	 * @date 2018年1月25日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping({"/list"})
	public ModelAndView list()throws Exception
	{
	    ModelAndView mv = getModelAndView();
	    //获取隐藏列
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("oa_staff","oa_staff");
		mv.addObject("hideLst", columnHideLst);
	    mv.setViewName("oa/staff/staff_list");
	    return mv;
	}
	
	
	/**
	 * @Title:listPage
	 * @Description: datagrid
	 * @author yk
	 * @date 2018年1月25日
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
			
			List<PageData> list=staffService.listPage(page);
			
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
	 * @Description: 员工列表（datagrid分页查询）
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
			
			List<PageData> list=staffService.querylistPage(page);
			
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
	
	
	@RequestMapping(value="/getTrueNamesById",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getTrueNamesById(){
		Map<String,String > map=new HashMap<String,String>();
		PageData pd=new PageData();
		PageData pd2=new PageData();
		String rtnInfo = "";
		try {
			pd=this.getPageData();
			
			String names="";
			String ids=pd.getString("ids");
			if(StringUtils.isNotEmpty(ids)){
				String staff_ids[]=ids.split(",");
				if(staff_ids!=null && staff_ids.length>0){
					
					for(int i=0;i<staff_ids.length;i++){
						pd.put("STAFF_ID", staff_ids[i]);
						pd2=staffService.findById(pd);
						if(pd2!=null){
							if(StringUtils.isNotEmpty(names)){
								names=names+","+pd2.getString("NAME");
							}else{
								names=pd2.getString("NAME");
							}
						}
					}
				}
			}
			map.put("names",names);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			map.put("errmsg",e.getMessage());
			rtnInfo="error";
			map.put("result",rtnInfo);
		}
		
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * @Title:toAdd
	 * @Description: 增加页面
	 * @author yk
	 * @date 2018年1月25日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd()throws Exception{
		ModelAndView mv=this.getModelAndView();
		
		//获取编码规则
		String code[]=codeRuleService.getFormCodeByRule("OA_STAFF","BIANMA");
		
		mv.addObject("method", "add");
		mv.addObject("codeRuleType", code[0]);
		mv.addObject("code", code[1]);
		mv.setViewName("oa/staff/staff_edit");
		return mv;
	}
	
	
	/**
	 * @Title:add
	 * @Description: 新增保存
	 * @author yk
	 * @date 2018年1月25日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object add(@RequestParam("ROLE_ID") String ROLE_ID){
		logBefore(logger, Jurisdiction.getUsername()+"新增员工");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			String manHourRate=pd.getString("manHourRate");
			if(StringUtils.isEmpty(manHourRate)) {
				pd.put("manHourRate",null);
			}
			
			pd.put("STAFF_ID", this.get32UUID());	//主键
			pd.put("ROLE_ID",ROLE_ID);
			
			if(staffService.findByBianMa(pd) != null){
				rtnInfo="codeRepeat";
			}else if(userService.findByUsername(pd)!=null){	//判断用户名是否存在
				rtnInfo="userNameRepeat";
			}else{
				staffService.save(pd);
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
	 * @Title:toEdit
	 * @Description: 编辑页面
	 * @author yk
	 * @date 2018年1月25日
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
			pd=staffService.findById(pd);
			
			mv.addObject("pd",pd);
			mv.addObject("method","edit");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("oa/staff/staff_edit");
		return mv;
	}
	
	/**
	 * @Title:edit
	 * @Description: 修改保存
	 * @author yk
	 * @date 2018年1月25日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/edit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object edit(@RequestParam("ROLE_ID") String ROLE_ID){
		logBefore(logger, Jurisdiction.getUsername()+"修改员工");
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			String manHourRate=pd.getString("manHourRate");
			if(StringUtils.isEmpty(manHourRate)) {
				pd.put("manHourRate",null);
			}
			
			pd.put("ROLE_ID",ROLE_ID);
			staffService.edit(pd);
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
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年1月25日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object delete(){
		logBefore(logger, Jurisdiction.getUsername()+"删除员工");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			String staff_IDS = pd.getString("staff_IDS");
			if(StringUtils.isNotEmpty(staff_IDS)){
				String ArrayStaff_IDS[] = staff_IDS.split(",");
				
				boolean checkTag=true;
				
				if(checkTag){
					staffService.deleteAll(ArrayStaff_IDS);
					rtnInfo="success";
				}else{
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
	/**
	 * 
	 * @Title:selectStaff
	 * @Description: 人员选择
	 * @author dj
	 * @date 2018年2月8日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/selectStaff")
	public ModelAndView selectStaff()throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();
		String selType=pd.getString("selType");
		
		ModelAndView mv = getModelAndView();
		
		if(StringUtils.isNotEmpty(selType) && "sin".equals(selType)){
			mv.addObject("singleSelect",true);
		}else{
			mv.addObject("singleSelect",false);
		}
		
		String winType=pd.getString("winType"); //窗口类型
		mv.addObject("winType",winType);
		
		mv.setViewName("oa/staff/staff_select");
		return mv;
	}
	
	
	/**
	 * @Title:flowSelectStaff
	 * @Description: 工作流人员选择控件
	 * @author yk
	 * @date 2018年7月31日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/flowSelectStaff")
	public ModelAndView flowSelectStaff()throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();
		String selType=pd.getString("selType");
		
		ModelAndView mv = getModelAndView();
		
		if(StringUtils.isNotEmpty(selType) && "sin".equals(selType)){
			mv.addObject("singleSelect",true);
		}else{
			mv.addObject("singleSelect",false);
		}
		
		String winType=pd.getString("winType"); //窗口类型
		mv.addObject("winType",winType);
		
		mv.setViewName("oa/staff/flowStaff_select");
		return mv;
	}
	
	
	
	/**
	 * 
	 * @Title:listPage
	 * @Description: TODO
	 * @author dj
	 * @date 2018年2月8日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value="/listPageSelect")
	@ResponseBody
	public String listPageSelect(){
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
			
			List<PageData> list=staffService.listPageSelect(page);
			
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
	 * 
	 * @Title: listAll
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年10月15日
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	@RequestMapping(value="/listAll")
	@ResponseBody
	public String listAll(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			List<PageData> list=staffService.listAll(pd);
			String json=JsonUtil.list2json(list);
			return json;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return null;
		
	}
	
	
	/**
	 * 
	 * @Title: listByDept
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月24日
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	@RequestMapping(value="/listByDept")
	@ResponseBody
	public String listByDept(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			List<PageData> list=staffService.listByDept(pd);
			String json=JsonUtil.list2json(list);
			JSONObject jo=new JSONObject();
			jo.put("rows", json);
			return jo.toString();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return null;
		
	}
	
	
	/**
	 * 
	 * @Title: listForTrackLimit
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月25日
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	@RequestMapping(value="/listForTrackLimit")
	@ResponseBody
	public String listForTrackLimit(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			List<PageData> list=staffService.listForTrackLimit(pd);
			String json=JsonUtil.list2json(list);
			JSONObject jo=new JSONObject();
			jo.put("rows", json);
			return jo.toString();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return null;
		
	}
	
	
	
	
	/**
	 * @Title:readExcel
	 * @Description: 从excel中导入数据
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param file
	 * @param @return
	 * @param @throws Exception   
	 * @return Object  
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/readUploadFile",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object readExcel(@RequestParam(value="uploadFile",required=false) MultipartFile file) throws Exception{
		Map<String,String > map=new HashMap<String,String>();
		String rtnInfo = "";
		PageData pd = new PageData();
		if (null != file && !file.isEmpty())//文件不为空 
		{
			try {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;	  //文件上传路径
				
				String fileNamePre=this.get32UUID();
				String fileName =  FileUpload.fileUp(file, filePath, fileNamePre);//执行上传
				
				//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
				List<PageData> pdList = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);		
				
				
				/**
				 * var0 :员工编码
				 * var1 :姓名
				 * var2 :性别
				 * var3 :部门编码
				 * var4 :部门名称
				 * var5 :电话
				 * var6 :卡号
				 * var7 :备注
				 * var8 :用户名
				 * var9 :角色
				 */
				
				PageData pdTag=new PageData();
				PageData pdTag2=new PageData();
				PageData pd2=new PageData();
				PageData pd3=new PageData();
				PageData pd4=new PageData();
				
				//预先判断员工编码是否为空，用户名是否为空，员工编码是否重复
				String BIANMA="";
				String userRole="";
				String userName=""; //用户名
				String roleIdStr="";//多个角色
				boolean preCheckTag=true;//预先验证是否成功
				for(int i=0;i<pdList.size();i++){		
					pdTag=pdList.get(i);
					
					BIANMA=pdTag.getString("var0");
					userName=pdTag.getString("var8");
					userRole=pdTag.getString("var9");
					
					if(StringUtils.isEmpty(pdTag.getString("var0"))){
						map.put("errmsg","第"+(i+1)+"行，员工编码为空");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					if(StringUtils.isEmpty(pdTag.getString("var1"))){
						map.put("errmsg","第"+(i+1)+"行，员工姓名为空");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					if(StringUtils.isEmpty(pdTag.getString("var8"))){
						map.put("errmsg","第"+(i+1)+"行，用户名为空");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					if(StringUtils.isEmpty(pdTag.getString("var9"))){
						map.put("errmsg","第"+(i+1)+"行，角色为空");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					
					if(pdTag.getString("var9").indexOf("，")!=-1) {
						map.put("errmsg","第"+(i+1)+"行，角色包含中文逗号");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					
					for(int z=i + 1;z<pdList.size();z++){
						pdTag2=pdList.get(z);
						
						if(BIANMA.equals(pdTag2.getString("var0"))){
							map.put("errmsg","excel中存在重复员工编码："+BIANMA);
							rtnInfo="error";
							map.put("result",rtnInfo);
							preCheckTag=false;
							break;
						}
						
						if(userName.equals(pdTag2.getString("var8"))){
							map.put("errmsg","excel中存在重复用户名："+userName);
							rtnInfo="error";
							map.put("result",rtnInfo);
							preCheckTag=false;
							break;
						}
					}
						
					if(!preCheckTag)break;	
					
				}
				
				//预先验证成功,再次验证编号/用户名是否和数据库里面的重复，以及部门是否存在
				boolean CheckTag=true;
				boolean CheckTag2=true;
				List<PageData> dataList=new ArrayList<PageData>();
				if(preCheckTag)
				{
					for(int i=0;i<pdList.size();i++){		
						pdTag=pdList.get(i);
						
						pd=new PageData();
						
						pd.put("STAFF_ID", this.get32UUID());	   //主键
						
						
						pd.put("BIANMA", pdTag.getString("var0"));   //员工编码
						pd.put("NAME", pdTag.getString("var1")); //姓名
						pd.put("SEX", pdTag.getString("var2")); //性别
						pd.put("TEL", pdTag.getString("var5")); //电话
						pd.put("IdCard", pdTag.getString("var6")); //卡号
						pd.put("BZ", pdTag.getString("var7")); //备注
						pd.put("USERNAME", pdTag.getString("var8")); //用户名
						
						pd.put("manHourRate",null);
						
						userRole=pdTag.getString("var9");  //角色，可能会包含多个
						
						if(staffService.findByBianMa(pd) != null){//判断员工编码是否重复
							map.put("errmsg","员工编码:"+pdTag.getString("var0")+",和系统中已有的员工编码重复！");
							rtnInfo="error";
							map.put("result",rtnInfo);
							CheckTag=false;
							break;
						}
						
						pd4=userService.findByUsername(pd);
						if(pd4 != null){//判断绑定的用户名是否存在
							//pd.put("USER_ID",pd4.get("USER_ID")); //绑定的用户名id
							map.put("errmsg","用户名:"+pdTag.getString("var8")+",和系统中已有的用户名重复！");
							rtnInfo="error";
							map.put("result",rtnInfo);
							CheckTag=false;
							break;
							
						}
						if(StringUtils.isNotEmpty(pdTag.getString("var3")))//部门编码不为空
						{
							pd3.put("BIANMA", pdTag.getString("var3"));
							pd2=departmentService.findByBianma(pd3);
							if(pd2!=null){
								pd.put("DEPARTMENT_ID", pd2.get("DEPARTMENT_ID"));
							}else{
								map.put("errmsg","部门编码:"+pdTag.getString("var3")+",不存在！");
								rtnInfo="error";
								map.put("result",rtnInfo);
								CheckTag=false;
								break;
							}
						}
						
						
						//判断角色名是否存在
						String userRoleArr[]=userRole.split(",");
						if(userRoleArr!=null && userRoleArr.length>0){
							roleIdStr="";
							pd4=new PageData();
							pd3=new PageData();
							for(int z=0;z<userRoleArr.length;z++){
								pd3.put("ROLE_NAME", userRoleArr[z]);
								pd4=roleService.findByRoleName(pd3);
								if(pd4!=null){
									if(z==0){
										roleIdStr=pd4.getString("ROLE_ID");
									}else{
										roleIdStr=roleIdStr+","+pd4.getString("ROLE_ID");
									}
								}else{
									map.put("errmsg","角色名:"+userRoleArr[z]+",不存在！");
									rtnInfo="error";
									map.put("result",rtnInfo);
									CheckTag2=false;
									break;
								}
							}
							
							if(CheckTag2){
								pd.put("ROLE_ID",roleIdStr); 
							}
							
						}
						
						if(!CheckTag2){
							CheckTag=false;
							break;
						}
						
						dataList.add(pd);
					}
					
					//第二次验证成功 :批量保存
					if(CheckTag){
						staffService.saveBatch(dataList);
						rtnInfo="success";
						map.put("result",rtnInfo);
					}
					
				}
				
				
			} catch (Exception e) {
				logger.error(e.toString(), e);
				map.put("errmsg",e.getMessage());
				rtnInfo="error";
				map.put("result",rtnInfo);
			}
		}
		
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * @Title:exportExcel
	 * @Description: 导出员工信息
	 * @author yk
	 * @date 2018年2月11日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			
			
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("员工编码");  		
				titles.add("姓名");     
				titles.add("性别");		
				titles.add("部门");		
				titles.add("班组名称");		
				titles.add("职务名称");		
				titles.add("电话");		
				titles.add("ID卡号");
				titles.add("用户名");	
				titles.add("备注");	
				dataMap.put("titles", titles);
				
				List<PageData> staffList = staffService.listAll(pd);
				List<PageData> varList = new ArrayList<PageData>();
				PageData vpd=null;
				PageData pdTag=null;
				for(int i=0;i<staffList.size();i++){
					vpd = new PageData();
					pdTag=staffList.get(i);
					vpd.put("var1", pdTag.getString("BIANMA"));		
					vpd.put("var2", pdTag.getString("NAME"));		
					vpd.put("var3", pdTag.getString("SEX"));		
					vpd.put("var4", pdTag.getString("DEPARTMENT_NAME"));
					vpd.put("var5", pdTag.getString("teamName"));
					vpd.put("var6", pdTag.getString("dutyName"));
					vpd.put("var7", pdTag.getString("TEL"));		
					vpd.put("var8", pdTag.getString("IdCard"));	
					vpd.put("var9", pdTag.getString("USERNAME"));	
					vpd.put("var10", pdTag.getString("BZ"));			
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
				mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
}
