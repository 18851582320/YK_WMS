package com.iwfm.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName: MyExceptionResolver 
 * @Description: 异常提示
 * @author yk
 * @date 2017年8月1日
 */
public class MyExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		
		System.out.println("==============系统异常开始=============");
		ex.printStackTrace();
		System.out.println("==============系统异常结束=============");
		
		ModelAndView mv=new ModelAndView("error");
		mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
		
		return null;
	}
	
}
