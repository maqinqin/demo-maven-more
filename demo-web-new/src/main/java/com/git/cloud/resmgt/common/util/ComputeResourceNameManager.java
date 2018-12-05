package com.git.cloud.resmgt.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.parame.service.ParameterService;

public class ComputeResourceNameManager {
	private static Logger logger = LoggerFactory.getLogger(ComputeResourceNameManager.class);

	public synchronized static List<String> getRecourceName(String paramKey,
			HashMap<String, Object> paramMap) throws Exception {
		// 根据paramKey查询ADMIN_PARAM表获取参数信息
		ParameterService paramService = (ParameterService) WebApplicationManager
				.getBean("parameterServiceImpl");
		String classInfo = "";
		try {
			classInfo = paramService.getParamValueByName(paramKey);
		} catch (RollbackableBizException e1) {
			e1.printStackTrace();
		}
		if (classInfo == null || classInfo.equals("")) {
			throw new Exception("ADMIN_PARAM表中无PARAM_NAME为：" + paramKey
					+ "相关信息");
		}
		int seperator = classInfo.lastIndexOf(".");
		String className = classInfo.substring(0, seperator);
		String methodName = classInfo.substring(seperator + 1);
		Class<?> c;
		try {
			c = Class.forName(className);
			Method m;
			try {
				m = c.getMethod(methodName, HashMap.class);
				Object obj;
				try {
					obj = m.invoke(c.newInstance(), paramMap);
					List<String> nameList = (List<String>) obj;
					List<String> nameListLowerCase = new ArrayList<String>();
					
					//确保生成的名称为英文小写字母
					for(String name : nameList){
						name = name.toLowerCase();
						nameListLowerCase.add(name);
					} 
					return nameListLowerCase;
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					logger.error("异常exception",e);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					logger.error("异常exception",e);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					logger.error("异常exception",e);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					logger.error("异常exception",e);
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				logger.error("异常exception",e);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				logger.error("异常exception",e);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return null;
	}

}
