package com.git.cloud.appmgt.model;


/**
 * 应用管理的枚举类
 * @author liangshuang
 * @date 2014-9-23 下午5:25:33
 * @version v1.0
 *
 */
public enum AppMgtEnum {

	DIC_TYPE_CODE_DU_SERVICE_TYPE("DU_SEV_TYPE"),//服务器角色服务类型
	DIC_TYPE_CODE_USE_STATUS("USE_STATUS");//服务器角色服务状态
	
	private final String value;
	
	private AppMgtEnum(String value){
		this.value=value;
	}

	public String getValue() {
		return value;
	}
	
	public static AppMgtEnum fromString(String value ) {
		if (value != null) {
			for (AppMgtEnum c : AppMgtEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}
	
}
