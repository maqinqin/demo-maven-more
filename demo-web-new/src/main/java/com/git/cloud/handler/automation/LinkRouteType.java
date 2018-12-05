package com.git.cloud.handler.automation;

/**
 * Link路由的枚举定义
 * 
 * 该枚举定义了Link路由字段编码
 */
public enum LinkRouteType {
	DATACENTER_QUEUE_IDEN("DATACENTER_QUEUE_IDEN"), /** <数据中心路由标识 */
	RESOURCE_CLASS("RESOURCE_CLASS"), /** < 资源类型 */
	;

	private final String value;

	private LinkRouteType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static LinkRouteType fromString(String value) {
		if (value != null) {
			for (LinkRouteType v : LinkRouteType.values()) {
				if (value.equalsIgnoreCase(v.value)) {
					return v;
				}
			}
		}
		return null;
	}
}