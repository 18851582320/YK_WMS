package com.iwfm.controller.system.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.util.ObjectExcelView;
import com.iwfm.util.FileUpload;
import com.iwfm.util.ObjectExcelRead;
import com.iwfm.util.PathUtil;
import com.iwfm.controller.base.BaseController;
import com.iwfm.entity.Page;
import com.iwfm.entity.system.Role;
import com.iwfm.entity.system.User;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.service.system.menu.MenuManager;
import com.iwfm.service.system.role.RoleManager;
import com.iwfm.service.system.user.UserManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.Const;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;

/**
 * ClassName: UserController 
 * @Description: 用户管理
 * @author yk
 * @date 2017年8月31日
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	
	/**
	 * @Title: listUsers
	 * @Description: user列表主页
	 * @author yk
	 * @date 2017年8月31日
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/listUsers")
	public ModelAndView listUsers(){
		ModelAndView mv=this.getModelAndView();
		try {
			//获取隐藏列
			List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("SYS_LOG","SYS_LOG");
			mv.addObject("hideLst", columnHideLst);
			mv.setViewName("system/user/user_list");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * @Title: rolelistPage
	 * @Description: （用户分页（组件））
	 * @author yk
	 * @date 2017年9月1日
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/userlistPage")
	@ResponseBody
	public String userlistPage(){
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
			
			List<PageData> userlist=userService.listUsers(page);
			
			String json=JsonUtil.list2json(userlist);
			JSONObject jo=new JSONObject();
			jo.put("total", Integer.valueOf(page.getTotalResult()));
			jo.put("rows", json);
			
			return jo.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
	/**
	 * @Title:querylistPage
	 * @Description: (用户分页查询（datagrid）)
	 * @author yk
	 * @date 2018年2月8日
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
			
			List<PageData> userlist=userService.querylistPage(page);
			
			String json=JsonUtil.list2json(userlist);
			JSONObject jo=new JSONObject();
			jo.put("total", Integer.valueOf(page.getTotalResult()));
			jo.put("rows", json);
			
			return jo.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * @Title: toAdd
	 * @Description: 访问增加
	 * @author yk
	 * @date 2017年9月1日
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd() throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		
		pd.put("ROLE_ID", "1");//角色默认父id=1
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统角色
		
		//获取编码规则
		String code[]=codeRuleService.getFormCodeByRule("sys_user","NUMBER");
		
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		mv.addObject("method","add");
		mv.addObject("codeRuleType", code[0]);
		mv.addObject("code", code[1]);

		mv.setViewName("system/user/user_edit");
		return mv;
	}
	
	/**
	 * @Title: add
	 * @Description: 保存
	 * @author yk
	 * @date 2017年9月1日
	 * @param @return
	 * @return Object
	 * @throws
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object add(){
		Map<String,String > map=new HashMap<String,String>();
		logBefore(logger, Jurisdiction.getUsername()+":新增用户");
		PageData pd=new PageData();
		String rtnInfo = "";
		try {
			pd=this.getPageData();
			
			pd.put("USER_ID", this.get32UUID());	//ID 主键
			pd.put("LAST_LOGIN", "");				//最后登录时间
			pd.put("IP", "");						//IP
			pd.put("STATUS", "0");					//状态
			pd.put("SKIN", "metro-seablue");        //mes默认皮肤
			pd.put("witSkin", "metro-wit-seablue"); //wit默认皮肤
			pd.put("RIGHTS", "");		
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());	//密码加密
			if(null == userService.findByUsername(pd)){	//判断用户名是否存在
				if(userService.findByUN(pd) != null){
					rtnInfo="numberRepeat";
				}else{
					userService.save(pd); 					//执行保存
					rtnInfo="success";
				}
			}else{
				rtnInfo="userNameRepeat";
			}
			map.put("result",rtnInfo);

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
	 * @Title: toEdit
	 * @Description: 访问修改
	 * @author yk
	 * @date 2017年9月1日
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit() throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		
		pd.put("ROLE_ID", "1");//角色默认父id=1
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统角色
		mv.addObject("roleList", roleList);
		pd = userService.findById(pd);								//根据ID读取
		mv.addObject("pd", pd);
		mv.addObject("method","edit");

		mv.setViewName("system/user/user_edit");
		return mv;
	}
	
	/**
	 * @Title: edit
	 * @Description: 修改保存
	 * @author yk
	 * @date 2017年9月1日
	 * @param @return
	 * @return Object
	 * @throws
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object edit(){
		Map<String,String > map=new HashMap<String,String>();
		logBefore(logger, Jurisdiction.getUsername()+":新增用户");
		PageData pd=new PageData();
		String rtnInfo = "";
		try {
			pd=this.getPageData();
			
			if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
				pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
			}
			userService.edit(pd);	//执行修改
			
			rtnInfo="success";
			map.put("result",rtnInfo);

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
	 * @Title: toUpdatepwd
	 * @Description: top修改个人密码
	 * @author yk
	 * @date 2017年9月1日
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/toPwd")
	public ModelAndView toUpdatepwd() throws Exception{
		ModelAndView mv=this.getModelAndView();
		mv.addObject("method","savepwd");
		mv.setViewName("system/user/user_updatepwd");
		return mv;
	}
	
	/**
	 * @Title: edit
	 * @Description: 修改个人密码
	 * @author yk
	 * @date 2017年9月1日
	 * @param @return
	 * @return Object
	 * @throws
	 */
	@RequestMapping(value="/savepwd",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object savepwd(){
		Map<String,String > map=new HashMap<String,String>();
		logBefore(logger, Jurisdiction.getUsername()+":修改密码");
		PageData pd=new PageData();
		String rtnInfo = "";
		try {
			pd=this.getPageData();
		
			Session session=Jurisdiction.getSession();
			User user=(User)session.getAttribute(Const.SESSION_USER);
			if(user!=null){
				pd.put("USER_ID", user.getUSER_ID());
				pd.put("USERNAME", user.getUSERNAME());
				if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
					pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
				}
				userService.updatePwd(pd);	//执行修改
				rtnInfo="success";
			}else{
				rtnInfo="error";
			}
			map.put("result",rtnInfo);

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
	 * 
	 * @Title:changeLanguage
	 * @Description: TODO
	 * @author yk
	 * @date 2018年9月19日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/changeLanguage")
	public ModelAndView changeLanguage()throws Exception{
		ModelAndView mv=this.getModelAndView();
		try {
			mv.addObject("language",Jurisdiction.getLanguage());
			mv.setViewName("system/user/user_changeLanguage");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 
	 * @Title:setLanguage
	 * @Description: TODO
	 * @author yk
	 * @date 2018年9月19日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/setLanguage",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object setLanguage(){
		Map<String,String > map=new HashMap<String,String>();
		PageData pd=new PageData();
		String rtnInfo = "";
		try {
			pd=this.getPageData();
			pd.put("USER_ID",Jurisdiction.getUserId());
			userService.updateLanguage(pd);
			
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);	//读取session中的用户信息(单独用户信息)
			if(user!=null){
				user.setUserLanguage(pd.getString("userLanguage"));
				session.setAttribute(Const.SESSION_USER, user); // 把用户信息放session中
			}
			
			rtnInfo="success";
			map.put("result",rtnInfo);

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
	 * @Title:readExcel
	 * @Description: 从excel中导入数据
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param file
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
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
				 * var0 :编号
				 * var1 :用户名
				 * var2 :角色
				 * var3 :邮箱
				 * var4 :备注
				 */
				
				PageData pdTag=new PageData();
				PageData pdTag2=new PageData();
				PageData pd2=new PageData();
				
				//预先判断用户名编号是否为空，用户名是否为空，角色是否为空，用户名是否重复，编号是否重复
				String USERNAME="";
				String NUMBER="";
				boolean preCheckTag=true;//预先验证是否成功
				for(int i=0;i<pdList.size();i++){		
					pdTag=pdList.get(i);
					
					NUMBER=pdTag.getString("var0");
					USERNAME=pdTag.getString("var1");
					
					if(StringUtils.isEmpty(pdTag.getString("var0"))){
						map.put("errmsg","第"+(i+1)+"行，编号为空");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					if(StringUtils.isEmpty(pdTag.getString("var1"))){
						map.put("errmsg","第"+(i+1)+"行，用户名为空");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					if(StringUtils.isEmpty(pdTag.getString("var2"))){
						map.put("errmsg","第"+(i+1)+"行，角色为空");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					
					
					for(int z=i + 1;z<pdList.size();z++){
						pdTag2=pdList.get(z);
						
						if(NUMBER.equals(pdTag2.getString("var0"))){
							map.put("errmsg","excel中存在重复编号："+NUMBER);
							rtnInfo="error";
							map.put("result",rtnInfo);
							preCheckTag=false;
							break;
						}
						if(USERNAME.equals(pdTag2.getString("var1"))){
							map.put("errmsg","excel中存在重复用户名："+USERNAME);
							rtnInfo="error";
							map.put("result",rtnInfo);
							preCheckTag=false;
							break;
						}
					
					}
						
					if(!preCheckTag)break;	
					
				}
				
				//预先验证成功,再次验证编号/用户名是否和数据库里面的重复，以及角色是否存在
				boolean CheckTag=true;
				List<PageData> dataList=new ArrayList<PageData>();
				if(preCheckTag)
				{
					for(int i=0;i<pdList.size();i++){		
						pdTag=pdList.get(i);
						
						/*
						if(pdTag.getString("var0").equals("end")){
							break;
						}
						*/
						
						pd=new PageData();
						
						pd.put("USER_ID", this.get32UUID());	   //主键
						
						
						pd.put("LAST_LOGIN", "");				//最后登录时间
						pd.put("IP", "");						//IP
						pd.put("STATUS", "0");					//状态
						pd.put("SKIN", "metro-seablue");        //默认皮肤
						pd.put("RIGHTS", "");
						
						pd.put("NUMBER", pdTag.getString("var0"));   //编号
						pd.put("USERNAME", pdTag.getString("var1")); //用户名
						pd.put("ROLE_NAME", pdTag.getString("var2")); //用户名
						
						if(userService.findByUN(pd) != null){//判断编号是否重复
							map.put("errmsg","编号:"+pdTag.getString("var0")+",和已有的重复！");
							rtnInfo="error";
							map.put("result",rtnInfo);
							CheckTag=false;
							break;
						}
						
						if(userService.findByUsername(pd) != null){//判断用户名是否重复
							map.put("errmsg","用户名:"+pdTag.getString("var1")+",和已有的重复！");
							rtnInfo="error";
							map.put("result",rtnInfo);
							CheckTag=false;
							break;
						}
						
						pd2=roleService.findByRoleName(pd);
						if(pd2!=null){
							pd.put("ROLE_ID", pd2.get("ROLE_ID"));
						}else{
							map.put("errmsg","角色:"+pdTag.getString("var2")+",不存在！");
							rtnInfo="error";
							map.put("result",rtnInfo);
							CheckTag=false;
							break;
						}
						
						pd.put("EMAIL",pdTag.getString("var3"));
						pd.put("BZ", pdTag.getString("var4"));       //备注
						pd.put("PASSWORD", new SimpleHash("SHA-1",pdTag.getString("var1"), "111111").toString());	//默认密码111111
						
						dataList.add(pd);
					}
					
					//第二次验证成功 :批量保存
					if(CheckTag){
						//userService.saveBatch(dataList);//批量增加的时候，数据量大：传入的请求具有过多的参数。该服务器支持最多 2100 个参数
						
						userService.saveBatch(dataList);
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
	 * @Title:delete
	 * @Description: 密码重置
	 * @author yk
	 * @date 2018年4月25日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/pwdreset",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object delete(){
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			String USER_IDS = pd.getString("USER_IDS");
			if(StringUtils.isNotEmpty(USER_IDS)){
				String ArrayUserIdS[] = USER_IDS.split(",");
					userService.savePwdReset(ArrayUserIdS);
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
	 * @Title:exportExcel
	 * @Description: 导出用户
	 * @author yk
	 * @date 2018年1月31日
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
				titles.add("编号");  		//1
				titles.add("用户名"); 		//2
				titles.add("姓名");			//3
				titles.add("角色");			//4
				titles.add("邮箱");			//5
				titles.add("最近登录");		//6
				titles.add("备注");	//7
				dataMap.put("titles", titles);
				
				List<PageData> userList = userService.listAllUser(pd);
				List<PageData> varList = new ArrayList<PageData>();
				PageData vpd=null;
				PageData pdTag=null;
				for(int i=0;i<userList.size();i++){
					vpd = new PageData();
					pdTag=userList.get(i);
					vpd.put("var1", pdTag.getString("NUMBER"));		//1
					vpd.put("var2", pdTag.getString("USERNAME"));		//2
					vpd.put("var3", pdTag.getString("NAME"));			//3
					vpd.put("var4", pdTag.getString("ROLE_NAME"));	//4
					vpd.put("var5", pdTag.getString("EMAIL"));		//6
					vpd.put("var6", pdTag.getString("LAST_LOGIN"));	//7
					vpd.put("var7", pdTag.getString("BZ"));			//8
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
	
	/**
	 * @Title:listUserForStaff
	 * @Description: 获取尚未绑定的用户
	 * @author yk
	 * @date 2018年2月24日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value="/listUserForStaff")
	@ResponseBody
	public String listUserForStaff(){
		PageData pd=new PageData();
		String json="";
		try {
			pd=this.getPageData();
			List<PageData> list=userService.listUserForStaff(pd);
			json=JsonUtil.list2json(list);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return json;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
}
