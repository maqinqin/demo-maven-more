/**
 * @Title:CloudSrvStsEnum.java
 * @Package:com.git.cloud.cloudservice.model
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-15 下午3:03:15
 * @version V1.0
 */
package com.git.cloud.cloudservice.model;

/**
 * @ClassName:CloudSrvStsEnum
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-15 下午3:03:15
 *
 *
 */
public enum IsEditEnum {
	YES("Y"),NO("N");
	private final String value;
	public String getValue() {
		return value;
	}

	private IsEditEnum(String value) {
		this.value = value;
	}
}
