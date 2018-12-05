package com.git.cloud.foundation.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhangbh
 * @version 创建时间：2015-8-4 下午4:07:29 类说明
 */
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public SpringContextHolder() {
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		setApplicationContextValue(applicationContext);
	}
	
	public static void setApplicationContextValue(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) SpringContextHolder.applicationContext.getBean(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> clazz) {
		checkApplicationContext();
		return (T) SpringContextHolder.applicationContext.getBean(clazz);
	}

	private static void checkApplicationContext() {
		if (SpringContextHolder.applicationContext == null)
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		else
			return;
	}

}
