package com.git.cloud.common.support;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 获取spring bean
 * @author zhuzhaoyong
 *
 */
public class ApplicationCtxUtil {
	
	private static ApplicationContext context = null;
	static {
		context = new ClassPathXmlApplicationContext("config/spring/applicationContext*.xml");
	}

	public static Object getBean(String beanName) throws Exception {
		return context.getBean(beanName);
	}
}
