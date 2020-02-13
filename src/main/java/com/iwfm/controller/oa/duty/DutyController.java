package com.iwfm.controller.oa.duty;

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

import com.iwfm.util.Const;
import com.iwfm.util.ObjectExcelView;
import com.iwfm.util.PathUtil;
import com.iwfm.controller.base.BaseController;
import com.iwfm.entity.Page;
import com.iwfm.service.oa.duty.DutyManager;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.columnHideSet.SysColumnHideSetManager;
import com.iwfm.util.AppUtil;
import com.iwfm.util.DateUtil;
import com.iwfm.util.FileUpload;
import com.iwfm.util.JsonUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.ObjectExcelRead;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;

/**
 * ClassName: DutyController 
 * @Description: 职务管理
 * @author yk
 * @date 2018年3月1日
 */
@Controller
@RequestMapping(value="/duty")
public class DutyController extends BaseController{
	
	@Resource(name="dutyService")
	private DutyManager dutyService;
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
	@Resource(name="sysColumnHideSetService")
	private SysColumnHideSetManager sysColumnHideSetService;
	
	
	/**
	 * @Title:list
	 * @Description: 访问列表主页
	 * @author yk
	 * @date 2018年2月28日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/list")
	public ModelAndView list()throws Exception{
		ModelAndView mv=this.getModelAndView();
		//获取隐藏列
		List<PageData> columnHideLst=sysColumnHideSetService.getColumnHide("OA_duty","OA_duty");
		mv.addObject("hideLst", columnHideLst);
		mv.setViewName("oa/duty/duty_list");
		return mv;
	}
	
	/**
	 * @Title:listPage
	 * @Description: 列表
	 * @author yk
	 * @date 2018年2月28日
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
			
			List<PageData> list=dutyService.listPage(page);
			
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
	 * @Description: TODO
	 * @author yk
	 * @date 2018年2月28日
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
			
			List<PageData> list=dutyService.querylistPage(page);
			
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
	 * @date 2018年2月28日
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
			List<PageData> list=dutyService.listAll(pd);
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
	 * @date 2018年2月28日
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
		
		
		pd.put("createUser", Jurisdiction.getSTAFF_ID());
		pd.put("createUserName", Jurisdiction.getSTAFF_NAME());
		pd.put("createTime",DateUtil.getTime());
		
		//获取编码规则
		String code[]=codeRuleService.getFormCodeByRule("OA_duty","dutyCode");
		mv.addObject("codeRuleType", code[0]);
		mv.addObject("code", code[1]);	
		mv.addObject("pd",pd);
		mv.addObject("method", "add");
		mv.setViewName("oa/duty/duty_edit");
		return mv;
	}
	
	
	/**
	 * @Title:add
	 * @Description: 新增保存
	 * @author yk
	 * @date 2018年2月28日
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
			pd.put("dutyId", this.get32UUID());	//主键
			
			PageData pd2=dutyService.findByCode(pd);
			if(pd2!=null)
			{
				map.put("result", "codeRepeat");
				return AppUtil.returnObject(pd2, map);
			}
			dutyService.save(pd);
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
	 * @date 2018年2月28日
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
			
			pd=dutyService.findById(pd);			
			pd.put("updateUser", Jurisdiction.getSTAFF_ID());
			pd.put("updateUserName", Jurisdiction.getSTAFF_NAME());
			pd.put("updateTime",DateUtil.getTime());
			mv.addObject("pd",pd);
			mv.addObject("method","edit");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("oa/duty/duty_edit");
		return mv;
	}
	
	/**
	 * @Title:edit
	 * @Description: 修改保存
	 * @author yk
	 * @date 2018年2月28日
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
			dutyService.edit(pd);
			
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
	 * @date 2018年2月28日
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
			
			String dutyIdS = pd.getString("dutyIdS");
			if(StringUtils.isNotEmpty(dutyIdS)){
				String ArraydutyIdS[] = dutyIdS.split(",");
				boolean checkTag=true;
				for(int i=0;i<ArraydutyIdS.length;i++){
					//判断职务有没有被引用
				}
				if(checkTag){
					dutyService.deleteAll(ArraydutyIdS);
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
	
	/**
	 * @Title:listForSelect
	 * @Description: TODO
	 * @author yk
	 * @date 2018年3月1日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value="/listForSelect")
	@ResponseBody
	public String listForSelect(){
		PageData pd=new PageData();
		String json="";
		try {
			pd=this.getPageData();
			List<PageData> list=dutyService.listForSelect(pd);
			json=JsonUtil.list2json(list);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return json;
	}
	
	/**
	 * @Title:exportExcel
	 * @Description: 导出数据
	 * @author yk
	 * @date 2018年2月28日
	 * @param @return   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
							
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("职务编码"); //1
				titles.add("职务名称"); //2
				titles.add("备注");		//5
				titles.add("创建人员");	//6
				titles.add("创建时间");	//7
				titles.add("最后修改人");	//8
				titles.add("最后修改时间");	//9
				dataMap.put("titles", titles);
				List<PageData> custTypeList = dutyService.listAll(pd);
				List<PageData> varList = new ArrayList<PageData>();
				
				PageData vpd=null;
				PageData pdTag=null;
				
				for(int i=0;i<custTypeList.size();i++){
					vpd = new PageData();
					pdTag=custTypeList.get(i);
					
					vpd.put("var1", pdTag.getString("dutyCode"));		//1
					vpd.put("var2", pdTag.getString("dutyName"));		//2
					vpd.put("var3", pdTag.getString("dutyMemo"));		    //3
					vpd.put("var4", pdTag.getString("createUserName"));		//4
					
					if(pdTag.get("createTime")!=null){
						vpd.put("var5", pdTag.get("createTime").toString());	    //5
					}else{
						vpd.put("var5","");	    //5
					}
					
					vpd.put("var6", pdTag.getString("updateUserName"));	    //6
					if(pdTag.get("updateTime")!=null){
						vpd.put("var7", pdTag.get("updateTime").toString());	    //6
					}else{
						vpd.put("var7","");	    //7
					}
					
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
	 * @Title:readExcel
	 * @Description: 导入数据
	 * @author yk
	 * @date 2018年2月28日
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
				 * var0 :职务编码
				 * var1 :职务名称
				 * var2 :描述
				 * 
				 */
				
				PageData pdTag=new PageData();
				PageData pdTag2=new PageData();
				
				//预先判断职务编号是否为空，职务名称是否为空，职务编码是否重复
				String dutyCode="";
				boolean preCheckTag=true;//预先验证是否成功
				for(int i=0;i<pdList.size();i++){		
					pdTag=pdList.get(i);
					
					dutyCode=pdTag.getString("var0");
					
					if(StringUtils.isEmpty(pdTag.getString("var0"))){
						map.put("errmsg","第"+(i+1)+"行，职务编码为空");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					if(StringUtils.isEmpty(pdTag.getString("var1"))){
						map.put("errmsg","第"+(i+1)+"行，职务名称为空");
						rtnInfo="error";
						map.put("result",rtnInfo);
						preCheckTag=false;
						break;
					}
					
					for(int z=i + 1;z<pdList.size();z++){
						pdTag2=pdList.get(z);
						
						if(dutyCode.equals(pdTag2.getString("var0"))){
							map.put("errmsg","excel中存在重复编码："+dutyCode);
							rtnInfo="error";
							map.put("result",rtnInfo);
							preCheckTag=false;
							break;
						}
					
					}
						
					if(!preCheckTag)break;	
					
				}
				
				//预先验证成功,再次验证编码是否和数据库里面的重复，
				boolean CheckTag=true;
				List<PageData> dataList=new ArrayList<PageData>();
				if(preCheckTag)
				{
					for(int i=0;i<pdList.size();i++){		
						pdTag=pdList.get(i);
						
						pd=new PageData();
						
						pd.put("dutyId", this.get32UUID());	   //主键
						pd.put("createUser", Jurisdiction.getSTAFF_ID());
						pd.put("createTime",DateUtil.getTime());
						//pd.put("updateUser", Jurisdiction.getSTAFF_ID());
						//pd.put("updateTime",DateUtil.getTime());
						
						pd.put("dutyCode", pdTag.getString("var0")); //职务编码
						pd.put("dutyName", pdTag.getString("var1")); //职务名称
						pd.put("dutyMemo", pdTag.getString("var2")); //描述
						
						
						if(dutyService.findByCode(pd)!=null)
						{
							map.put("result", "error");
							map.put("errmsg", "职务编码:"+pdTag.getString("var0")+",和已有的重复！");
							CheckTag=false;
							break;
						}
						
						dataList.add(pd);
					}
					
					//第二次验证成功 :批量保存
					if(CheckTag){
						dutyService.saveBatch(dataList);
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
	
	
}
