package com.git.cloud.foundation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �����ļ�������
 * @author Spring.Cao
 * @version v1.0 2013-03-22
 */
public class ConfigUtil {
	private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	/**
	 * ��ȡproperties�ļ�
	 * 
	 * @return
	 */
	private static Properties getProperty() {
		// ��ȡproperties�ļ�
		InputStream in = ConfigUtil.class
				.getResourceAsStream("/com/ccb/montior/cfg/properties/service.properties");
		Properties var = new Properties();
		try {
			var.load(in);
		} catch (IOException e) {
			logger.error("异常exception",e);
		}
		return var;
	}
}
