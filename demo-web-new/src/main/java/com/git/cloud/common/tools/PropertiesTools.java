package com.git.cloud.common.tools;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesTools {
	private static Logger logger = LoggerFactory.getLogger(PropertiesTools.class);
	public static String getPropertiesMailValue(String key) {
		return getPropertiesValue("/mail.properties", key);
	}
	
	public static String getPropertiesLdapValue(String key) {
		return getPropertiesValue("/ldap.properties", key);
	}
	
	public static String getPropertiesParam(String key) {
		return getPropertiesValue("/param.properties", key);
	}
	
	public static String getPropertiesValue(String filePath, String key) {
		Properties prop = new Properties();
		String value = "";
		try {
			prop.load(PropertiesTools.class.getResourceAsStream(filePath));
			value = prop.getProperty(key);
		} catch (IOException e) {
			logger.error("获取配置属性值异常",e);
		}
		return value;
	}
}
