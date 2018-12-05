package com.git.cloud.appmgt.model;

import com.git.cloud.common.model.SortTypeEnum;
import com.git.cloud.resmgt.common.model.VmDistriTypeEnum;


/**
 * 服务类型枚举
 * @author liangshuang
 * @date 2014-10-14 上午11:10:50
 * @version v1.0
 *
 */
public enum DuSerTypeEnum {

	DU_SEV_TYPE_AP("AP"),//应用服务
	DU_SEV_TYPE_WB("WB"),//接入服务
	DU_SEV_TYPE_DB("DB");//数据服务
	
    private final String value;
	
	private DuSerTypeEnum(String value){
		this.value=value;
	}

	public String getValue() {
		return value;
	}
	
	public static DuSerTypeEnum fromString(String value ) {
		if (value != null) {
			for (DuSerTypeEnum c : DuSerTypeEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}
	
}
