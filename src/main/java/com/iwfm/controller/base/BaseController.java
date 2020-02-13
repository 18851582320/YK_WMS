package com.iwfm.controller.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.entity.Page;
import com.iwfm.util.DateUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.Logger;
import com.iwfm.util.PageData;
import com.iwfm.util.UuidUtil;


/**
 * ClassName: BaseController 
 * @Description: TODO
 * @author yk
 * @date 2017年8月2日
 */
public class BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 6357869213649815390L;
	
	/**
	 * @Title:getListPageDataFromJsonStr
	 * @Description: TODO
	 * @author yk
	 * @date 2018年2月5日
	 * @param @param jsonStr
	 * @param @param type:inserted,deleted,updated
	 * @param @param mainKeyName  :表头主键名称
	 * @param @param mainKeyValue :表头主键值
	 * @param @param bodyKeyName  :表体主键名称
	 * @param @return   
	 * @return List<PageData>  
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public List<PageData> getListPageDataFromJsonStr(String jsonStr,String type,String mainKeyName,String mainKeyValue,String bodyKeyName){
		List<PageData> pdLst=new ArrayList<PageData>();
		
		PageData pd=null;
		String key="";
		String keyValue="";
		
		if(StringUtils.isNotEmpty(jsonStr) && !"[]".equals(jsonStr)){
			JSONArray jsonAry=new JSONArray();
			jsonAry=JSONArray.fromObject(jsonStr);
			if(jsonAry!=null && jsonAry.size()>0){
				JSONObject jo=null;
				for(int i=0;i<jsonAry.size();i++){
					pd=new PageData();
					
					jo=jsonAry.getJSONObject(i);
					//获取json对象中所有的键值
					
					//新增的数据保存表头的主键、和生成自身的主键
					if(StringUtils.isNotEmpty(type) && "inserted".equals(type)){
						pd.put(mainKeyName,mainKeyValue);
						pd.put(bodyKeyName,this.get32UUID());
						pd.put("createUser", Jurisdiction.getSTAFF_ID());
						pd.put("createTime",DateUtil.getTime());
						pd.put("updateUser", Jurisdiction.getSTAFF_ID());
						pd.put("updateTime",DateUtil.getTime());
					}
					if(StringUtils.isNotEmpty(type) && "updated".equals(type)){
						pd.put("updateUser", Jurisdiction.getSTAFF_ID());
						pd.put("updateTime",DateUtil.getTime());
					}
					
					Iterator iterator=jo.keys();
					while(iterator.hasNext()){
						key=(String)iterator.next();
						keyValue=jo.getString(key);
						
						if(StringUtils.isNotEmpty(type) && type.equals("inserted")){
							if(StringUtils.isNotEmpty(key)){
								if(key.equals(bodyKeyName)){
									
								}else{
									pd.put(key, keyValue);
								}
							}
						}else{
							pd.put(key, keyValue);
						}
					}
					pdLst.add(pd);
				}
			}
		}
		return pdLst;
	}
	
	
	
	/**
	 * @Title: getPageData
	 * @Description: new PageData对象
	 * @author yk
	 * @date 2017年8月2日
	 * @param @return
	 * @return PageData
	 * @throws
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 
	 * @Title:getPageData
	 * @Description: 获取上传时的整个pageData
	 * @author AaronKevin
	 * @date 2018年5月7日
	 * @param @return   
	 * @return PageData  
	 * @throws
	 */
	public PageData getPageDataByReq(HttpServletRequest request){
		return new PageData(request);
	}
	
	
	/**
	 * @Title: getModelAndView
	 * @Description: 获得ModelAndView
	 * @author yk
	 * @date 2017年8月2日
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * @Title: getRequest
	 * @Description: 获得request对象
	 * @author yk
	 * @date 2017年8月2日
	 * @param @return
	 * @return HttpServletRequest
	 * @throws
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * @Title: get32UUID
	 * @Description: 获得32位的uuid
	 * @author yk
	 * @date 2017年8月2日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}
	
	/**
	 * @Title:getLoginIP
	 * @Description: 获取登录的ip
	 * @author yk
	 * @date 2018年6月15日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public String getLoginIP(){
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {  
			ip = request.getRemoteAddr();  
	    }else{
	    	ip = request.getHeader("x-forwarded-for");  
	    }
		return ip;
	}
	
	/**
	 * @Title: getPage
	 * @Description: 获得分页列表的信息
	 * @author yk
	 * @date 2017年8月2日
	 * @param @return
	 * @return Page
	 * @throws
	 */
	public Page getPage(){
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("日志：start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("日志：end");
		logger.info("");
	}
}
