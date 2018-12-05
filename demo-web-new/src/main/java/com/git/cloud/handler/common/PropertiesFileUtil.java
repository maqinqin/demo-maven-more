/**
 * Copyright (c) 2014, Git Co., Ltd. All rights reserved.
 *
 * 审核人：
 */
package com.git.cloud.handler.common;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <p>
 * 
 * @author zhuzhaoyong
 * @version 1.0 2013-7-3 
 * @see
 */
public class PropertiesFileUtil {
	
	public final static String BR = System.getProperty("line.separator");

	public static String makePropertiesFileOneLine(String key, String value, boolean isNeedQuot, boolean isAllowNullOrBlank) throws Exception {
		StringBuilder line = new StringBuilder();
		
		if (!isAllowNullOrBlank && StringUtils.isEmpty(value)) {
			throw new Exception("参数：" + key + "为空。value=" + value);
		}
		
		if (isNeedQuot) {
			if (StringUtils.isEmpty(value)) {
				line.append(key).append("=").append(BR);
			} else {
				line.append(key).append("=").append("\"").append(value).append("\"").append(BR);
			}
		} else {
			line.append(key).append("=").append(value).append(BR);
		}
		return line.toString();
	}
	
	

}
