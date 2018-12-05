package com.git.cloud.common.model;

import com.git.cloud.resmgt.common.model.ResPoolTypeEnum;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-23
 */
public enum SortTypeEnum{
	ASC("0"),
	DESC("1");
	private final String value;

	public String getValue() {
		return value;
	}
	private SortTypeEnum(String value) {
		this.value = value;
	}
	
	public static SortTypeEnum fromString(String value ) {
		if (value != null) {
			for (SortTypeEnum sortTypeEnum : SortTypeEnum.values()) {
				if (value.equalsIgnoreCase(sortTypeEnum.value)) {
					return sortTypeEnum;
				}
			}
		}
		return null;
	}
}
