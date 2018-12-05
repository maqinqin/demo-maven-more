package com.git.cloud.foundation.common;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebApplicationManager {

	public static ApplicationContext webApplicationContext;
	public static ServletContext servletContext;
	
	/**
	 * 获取注入的对象实例
	 * @param beanid
	 * @return
	 */
	public static Object getBean(String beanid){
		if(webApplicationContext==null && servletContext!=null)
			webApplicationContext= WebApplicationContextUtils.getWebApplicationContext(servletContext);
		if(webApplicationContext!=null)
			return webApplicationContext.getBean(beanid);
		else
			return SpringContextHolder.getBean(beanid);
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		WebApplicationManager.servletContext = servletContext;
	}
	
	
}
