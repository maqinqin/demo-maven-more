package com.git.cloud.common.action;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ConvertPwdPropertyConfigurer extends PropertyPlaceholderConfigurer {

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if(propertyName.equals("jdbc.username")){
			return propertyValue;
		}
		if(propertyName.equals("jdbc.password")){
			return propertyValue;
		}
		return propertyValue;
	}

}
