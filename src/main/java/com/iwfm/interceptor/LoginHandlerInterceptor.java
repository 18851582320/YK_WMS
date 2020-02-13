package com.iwfm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.iwfm.entity.system.User;
import com.iwfm.util.Const;
import com.iwfm.util.Jurisdiction;


/**
 * ClassName: LoginHandlerInterceptor 
 * @Description: 登录过滤，权限验证
 * @author yk
 * @date 2017年8月1日
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String path=request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){//不对匹配该值的访问路径拦截（正则）
			return true;
		}else{
			//从shiro管理的session中的User
			User user=(User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if(user!=null){
				/*path = path.substring(1, path.length());
				boolean hasQx=Jurisdiction.hasJurisdiction(path);//访问权限验证
				if(!hasQx){
					response.sendRedirect(request.getContextPath()+Const.LOGIN);
				}
				return hasQx;*/
				return true;
			}else{
				//登陆过滤(未获取到用户信息)
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;
			}
		}
	}
	
}
