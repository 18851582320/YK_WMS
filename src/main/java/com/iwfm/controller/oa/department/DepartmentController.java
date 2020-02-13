package com.iwfm.controller.oa.department;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.controller.base.BaseController;
import com.iwfm.entity.Page;
import com.iwfm.entity.oa.Department;
import com.iwfm.service.oa.department.DepartmentManager;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
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
 * ClassName: DepartmentController 
 * @Description: 组织架构
 * @author yk
 * @date 2018年1月18日
 */
@Controller
@RequestMapping(value="/department")
public class DepartmentController extends BaseController{
	
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	/**
	 * @Title:listAllDepartment
	 * @Description: 获取组织架构树结构信息
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param model
	 * @param @param DEPARTMENT_ID
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/listAllDepartment")
	public ModelAndView listAllDepartment(Model model)throws Exception{
		ModelAndView mv=this.getModelAndView();
		
		
		/*
		PageData pd=new PageData();
		pd=this.getPageData();
		try {
			
			JSONArray jsonAry=new JSONArray();
			JSONObject jo=null;
			Department depart=null;
			
			List<Department> departList=departmentService.listAllDepartment("0");
			if(departList!=null && departList.size()>0){
				for(int i=0;i<departList.size();i++){
					depart=departList.get(i);
					jo=new JSONObject();
					jo.put("id",depart.getDEPARTMENT_ID());
					jo.put("text",depart.getNAME());
					
					if(depart.getSubDepartment()!=null && depart.getSubDepartment().size()>0){
						jo.put("children", this.resolveDepartment(depart.getSubDepartment()));
					}
					
					jsonAry.add(jo);
				}
			}
			
			model.addAttribute("treeNodes",jsonAry.toString());
			mv.addObject("pd", pd);	
			mv.setViewName("oa/department/department_index");
			
		} catch (Exception e) {
			
		}
		*/
		
		
		//部门列表改为树结构
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("oa_department","oa_department");
		mv.addObject("hideLst", columnHideLst);
	    mv.setViewName("oa/department/department_list");
		
		return mv;
	}
	
	/**
	 * @Title:resolveDepartment
	 * @Description: 递归转换为tree
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param departList
	 * @param @return   
	 * @return JSONArray  
	 * @throws
	 */
	private JSONArray resolveDepartment(List<Department> departList) {
		JSONArray jsonAry=new JSONArray();
		JSONObject jo=null;
		if(departList!=null && departList.size()>0){
			Department depart=null;
			for(int i=0;i<departList.size();i++){
				depart=departList.get(i);
				
				jo=new JSONObject();
				jo.put("id",depart.getDEPARTMENT_ID());
				jo.put("text",depart.getNAME());
				
				if(depart.getSubDepartment()!=null && depart.getSubDepartment().size()>0){
					jo.put("children", this.resolveDepartment(depart.getSubDepartment()));
				}									
					
				jsonAry.add(jo);
			}
		}
		return jsonAry;
	}
	
	
	
	/**
	 * @Title:departmentListIndex
	 * @Description: 组织架构datagrid页面
	 * @author yk
	 * @date 2018年1月18日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping({"/departmentListIndex"})
	public ModelAndView departmentListIndex(@RequestParam String PARENT_ID)throws Exception
	{
	    ModelAndView mv = getModelAndView();
	    mv.addObject("PARENT_ID", PARENT_ID);
	    //获取隐藏列
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("oa_department","oa_department");
		mv.addObject("hideLst", columnHideLst);
	    mv.setViewName("oa/department/department_list");
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
			
			List<PageData> list=departmentService.listPage(page);
			
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
	 * @Description: 部门列表（datagrid分页查询）
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
			
			List<PageData> list=departmentService.querylistPage(page);
			
			
			//如果父级不存在list中，则_parentId设置为空
			boolean isExist=false;
			for(int i=0;i<list.size();i++) {
				pd=list.get(i);
				isExist=false;
				for(int z=0;z<list.size();z++) {
					if(StringUtils.isNotEmpty(pd.getString("_parentId"))) {
						if(pd.getString("_parentId").equals(list.get(z).getString("DEPARTMENT_ID"))){
							isExist=true;
							break;
						};
						
					}
				}
				
				if(!isExist) {
					pd.remove("_parentId");
				}
				
			}
			
			
			
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
		
		PageData pd = new PageData();
		pd = this.getPageData();
		String PARENT_ID = null == pd.get("PARENT_ID")?"":pd.get("PARENT_ID").toString();
		
		pd.put("DEPARTMENT_ID", PARENT_ID);//上级ID
		
		//获取编码规则
		String code[]=codeRuleService.getFormCodeByRule("oa_department","BIANMA");
		
		mv.addObject("pds",departmentService.findById(pd));	//传入上级所有信息
		mv.addObject("PARENT_ID", PARENT_ID);//传入ID，作为子级ID用
		mv.addObject("method", "add");
		mv.addObject("codeRuleType", code[0]);
		mv.addObject("code", code[1]);
		mv.setViewName("oa/department/department_add");
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
		logBefore(logger, Jurisdiction.getUsername()+"新增部门");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			String PARENT_ID=pd.getString("PARENT_ID");
			if(StringUtils.isEmpty(PARENT_ID)){
				pd.put("PARENT_ID", "0");
			}
			
			pd.put("DEPARTMENT_ID", this.get32UUID());	//主键
			
			if(departmentService.findByBianma(pd) != null){
				rtnInfo="codeRepeat";
			}else{
				departmentService.save(pd);
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
			
			pd=departmentService.findById(pd);
			
			mv.addObject("pd",pd);
			mv.addObject("method","edit");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("oa/department/department_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"修改部门名称");
		Map<String, String> map=new HashMap<String, String>();
		PageData pd=new PageData();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			departmentService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除部门");
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String,String>();
		String rtnInfo="";
		try {
			pd=this.getPageData();
			
			String department_IDS = pd.getString("department_IDS");
			if(StringUtils.isNotEmpty(department_IDS)){
				String ArrayDept_IDS[] = department_IDS.split(",");
				
				boolean checkTag=true;
				for(int i=0;i<ArrayDept_IDS.length;i++){
					//判断每个部门下是否有子集，有的话不允许删除
					if(departmentService.listSubDepartmentByParentId(ArrayDept_IDS[i]).size() > 0){//判断是否有子级，是：不允许删除
						checkTag=false;
						break;
					}
				}
				
				if(checkTag){
					departmentService.deleteAll(ArrayDept_IDS);
					
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
	 * @Title:readExcel
	 * @Description: 从excel中导入数据
	 * @author yk
	 * @date 2018年2月6日
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
				 * var0 :部门编码
				 * var1 :部门名称
				 * var2 :英文名称
				 * var3 :上级编码
				 * var4 :上级名称
				 * var5 :备注
				 */
				
				PageData pdTag=new PageData();
				PageData pdTag2=new PageData();
				PageData pd2=new PageData();
				
				//预先判断部门编码、部门名称是否为空，部门编码是否重复
				String BIANMA="";
				String NAME="";
				boolean preCheckTag=true;//预先验证是否成功
				for(int i=0;i<pdList.size();i++){		
					pdTag=pdList.get(i);
					
					BIANMA=pdTag.getString("var0");
					NAME=pdTag.getString("var1");
					
					if(StringUtils.isEmpty(pdTag.getString("var0"))){
						map.put("errmsg","第"+(i+1)+"行，部门编码为空");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					if(StringUtils.isEmpty(pdTag.getString("var1"))){
						map.put("errmsg","第"+(i+1)+"行，部门名称为空");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					
					
					for(int z=i + 1;z<pdList.size();z++){
						pdTag2=pdList.get(z);
						
						if(BIANMA.equals(pdTag2.getString("var0"))){
							map.put("errmsg","excel中存在重复部门编码："+BIANMA);
							rtnInfo="error";
							map.put("result",rtnInfo);
							preCheckTag=false;
							break;
						}
						if(NAME.equals(pdTag2.getString("var1"))){
							map.put("errmsg","excel中存在重复部门名称："+NAME);
							rtnInfo="error";
							map.put("result",rtnInfo);
							preCheckTag=false;
							break;
						}
					
					}
						
					if(!preCheckTag)break;	
					
				}
				
				//预先验证成功,再次验证编号/用户名是否和数据库里面的重复，以及上级部门是否存在
				boolean CheckTag=true;
				List<PageData> dataList=new ArrayList<PageData>();
				if(preCheckTag)
				{
					for(int i=0;i<pdList.size();i++){		
						pdTag=pdList.get(i);
						
						
						pd=new PageData();
						
						pd.put("DEPARTMENT_ID", this.get32UUID());	   //主键
						
						pd.put("BIANMA", pdTag.getString("var0"));   //部门编号
						pd.put("NAME", pdTag.getString("var1")); //部门名称
						pd.put("NAME_EN", pdTag.getString("var2")); //英文名称
						pd.put("ROLE_NAME", pdTag.getString("var2")); //用户名
						
						if(departmentService.findByBianma(pd) != null){//判断编号是否重复
							map.put("errmsg","部门编码:"+pdTag.getString("var0")+",和已有的重复！");
							rtnInfo="error";
							map.put("result",rtnInfo);
							CheckTag=false;
							break;
						}
						
						if(StringUtils.isNotEmpty(pdTag.getString("var3")))
						{
							//如果上级编码不为空
							pd.put("BIANMA", pdTag.getString("var3"));   //重新设置编码为上级编码，查找上级编码的id
							pd2=departmentService.findByBianma(pd);
							if(pd2!=null){
								pd.put("PARENT_ID", pd2.get("DEPARTMENT_ID"));
								pd.put("BIANMA", pdTag.getString("var0"));   //还原为编码
							}else{
								map.put("errmsg","上级编码:"+pdTag.getString("var3")+",不存在！");
								rtnInfo="error";
								map.put("result",rtnInfo);
								CheckTag=false;
								break;
							}
						}
						
						
						pd.put("BZ", pdTag.getString("var5"));       //备注
						
						dataList.add(pd);
					}
					
					//第二次验证成功 :批量保存
					if(CheckTag){
						departmentService.saveBatch(dataList);
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
	 * @Description: 导出部门
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
				titles.add("部门编码");  		//1
				titles.add("部门名称"); 		//2
				titles.add("英文名称");			//3
				titles.add("上级编码");			//4
				titles.add("上级名称");			//5
				titles.add("备注");		//6
				dataMap.put("titles", titles);
				
				List<PageData> deptList = departmentService.listAll(pd);
				
				List<PageData> varList = new ArrayList<PageData>();
				PageData vpd=null;
				PageData pdTag=null;
				for(int i=0;i<deptList.size();i++){
					vpd = new PageData();
					pdTag=deptList.get(i);
					vpd.put("var1", pdTag.getString("BIANMA"));		//1
					vpd.put("var2", pdTag.getString("NAME"));		//2
					vpd.put("var3", pdTag.getString("NAME_EN"));			//3
					vpd.put("var4", pdTag.getString("parentBIANMA"));	//4
					vpd.put("var5", pdTag.getString("parentNAME"));		//5
					vpd.put("var6", pdTag.getString("BZ"));	//6
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
	 * @Title:listAll
	 * @Description: 查找全部的数据
	 * @author yk
	 * @date 2018年2月2日
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
			List<PageData> list=departmentService.listAll(pd);
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
	 * @Title: listNoPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月24日
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	@RequestMapping(value="/listNoPage")
	@ResponseBody
	public String listNoPage(){
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			List<PageData> list=departmentService.listAll(pd);
			
			
			//如果父级不存在list中，则_parentId设置为空
			boolean isExist=false;
			for(int i=0;i<list.size();i++) {
				pd=list.get(i);
				isExist=false;
				for(int z=0;z<list.size();z++) {
					if(StringUtils.isNotEmpty(pd.getString("_parentId"))) {
						if(pd.getString("_parentId").equals(list.get(z).getString("DEPARTMENT_ID"))){
							isExist=true;
							
							System.out.println("测试1231");
							
							break;
						};
						
					}
				}
				
				if(!isExist) {
					pd.remove("_parentId");
				}
				
			}
			
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
	 * @Title:selectDepartment
	 * @Description: TODO
	 * @author yk
	 * @date 2018年2月6日
	 * @param @return   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/selectDepartment")
	public ModelAndView selectDepartment()throws Exception{
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
		
		mv.setViewName("oa/department/department_select");
		return mv;
	}
	
	/**
	 * @Title:getTree
	 * @Description: 获取结构树
	 * @author yk
	 * @date 2018年2月6日
	 * @param @return   
	 * @return Object  
	 * @throws
	 */
	@RequestMapping(value="/getTree")
	@ResponseBody
	public Object getTree()throws Exception{
		JSONArray jsonAry=new JSONArray();
		JSONObject jo=null;
		Department depart=null;
		
		List<Department> departList=departmentService.listAllDepartment("0");
		if(departList!=null && departList.size()>0){
			for(int i=0;i<departList.size();i++){
				depart=departList.get(i);
				jo=new JSONObject();
				jo.put("id",depart.getDEPARTMENT_ID());
				jo.put("text",depart.getNAME());
				
				if(depart.getSubDepartment()!=null && depart.getSubDepartment().size()>0){
					jo.put("children", this.resolveDepartment(depart.getSubDepartment()));
				}
				
				jsonAry.add(jo);
			}
		}
		
		return jsonAry;
	}
	
}
