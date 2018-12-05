package com.git.cloud.foundation.common;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.tools.SysStartInit;

public class WebApplicationListener implements ServletContextListener{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
	/**
	 * 获取ServletContext对象
	 */
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext=sce.getServletContext();
		WebApplicationManager.setServletContext(servletContext);
		// 系统启动初始化
		SysStartInit sysStartInit = new SysStartInit();
		try {
			sysStartInit.init();
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("error",e);
		}
	}
}
