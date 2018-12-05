/**
 * @Title:SystemTypeEnum.java
 * @Package:com.git.cloud.cloudservice.model
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-29 下午4:43:52
 * @version V1.0
 */
package com.git.cloud.cloudservice.model;

/**
 * @ClassName:SystemTypeEnum
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-29 下午4:43:52
 *
 *
 */
public enum SystemTypeEnum {
	AIX("Y"),HPUX("N"),Linux("LINUX"),Windows("WINDOWS");
	private final String value;
	public String getValue() {
		return value;
	}

	private SystemTypeEnum(String value) {
		this.value = value;
	}
}
