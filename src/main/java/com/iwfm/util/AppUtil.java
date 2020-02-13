package com.iwfm.util;

import java.util.Map;

import net.sf.json.JSONObject;

import org.codehaus.jackson.map.util.JSONPObject;

import com.iwfm.util.Const;
import com.iwfm.util.Logger;
import com.iwfm.util.PageData;

/**
 * ClassName: AppUtil 
 * @Description: 接口校验参数
 * @author yk
 * @date 2017年8月2日
 */
public class AppUtil {
protected static Logger logger = Logger.getLogger(AppUtil.class);
	
	/**
	 * @Title: checkParam
	 * @Description: 检查参数是否完整
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param method
	 * @param @param pd
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean checkParam(String method, PageData pd){
		boolean result = false;
		

		int falseCount = 0;
		String[] paramArray = new String[20];
		String[] valueArray = new String[20];
		String[] tempArray  = new String[20];  //临时数组
		
		if("registerSysUser".equals(method)){// 注册
			paramArray = Const.SYSUSER_REGISTERED_PARAM_ARRAY;  //参数
			valueArray = Const.SYSUSER_REGISTERED_VALUE_ARRAY;  //参数名称
			
		}else if("getAppuserByUsernmae".equals(method)){//根据用户名获取会员信息
			paramArray = Const.APP_GETAPPUSER_PARAM_ARRAY;  
			valueArray = Const.APP_GETAPPUSER_VALUE_ARRAY;
		}
		int size = paramArray.length;
		for(int i=0;i<size;i++){
			String param = paramArray[i];
			if(!pd.containsKey(param)){
				tempArray[falseCount] = valueArray[i]+"--"+param;
				falseCount += 1;
			}
		}
		if(falseCount>0){
			logger.error(method+"接口，请求协议中缺少 "+falseCount+"个 参数");
			for(int j=1;j<=falseCount;j++){
				logger.error("   第"+j+"个："+ tempArray[j-1]);
			}
		} else {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * @Title: setPageParam
	 * @Description: 设置分页的参数
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param pd
	 * @param @return
	 * @return PageData
	 * @throws
	 */
	public static PageData setPageParam(PageData pd){
		String page_now_str = pd.get("page_now").toString();
		int pageNowInt = Integer.parseInt(page_now_str)-1;
		String page_size_str = pd.get("page_size").toString(); //每页显示记录数
		int pageSizeInt = Integer.parseInt(page_size_str);
		String page_now = pageNowInt+"";
		String page_start = (pageNowInt*pageSizeInt)+"";
		pd.put("page_now", page_now);
		pd.put("page_start", page_start);
		return pd;
	}
	
	
	/**
	 * @Title: returnObject
	 * @Description: TODO
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param pd
	 * @param @param map
	 * @param @return
	 * @return Object
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static Object returnObject(PageData pd, Map map){
		if(pd.containsKey("callback")){
			String callback = pd.get("callback").toString();
			return new JSONPObject(callback, map);
		}else{
			String rtn=JSONObject.fromObject(map).toString();
			return rtn;
		}
	}
}
