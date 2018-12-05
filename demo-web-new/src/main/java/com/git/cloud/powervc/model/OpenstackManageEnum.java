package com.git.cloud.powervc.model;

public enum OpenstackManageEnum {
	
	OPENSTACK_MANAGE_USER("OPENSTACK_MANAGE_USER"),
	OPENSTACK_MANAGE_PWD("OPENSTACK_MANAGE_PWD"),
	OPENSTACK_MANAGE_PROJECT("OPENSTACK_MANAGE_PROJECT"),
	OPENSTACK_MANAGE_DOMAIN("OPENSTACK_MANAGE_DOMAIN"),
	
	OPENSTACK_PV_MANAGE_USER("OPENSTACK_PV_MANAGE_USER"),
	OPENSTACK_PV_MANAGE_PWD("OPENSTACK_PV_MANAGE_PWD"),
	OPENSTACK_PV_MANAGE_PROJECT("OPENSTACK_PV_MANAGE_PROJECT"),
	OPENSTACK_PV_MANAGE_DOMAIN("OPENSTACK_PV_MANAGE_DOMAIN");
	
	private final String value;

	public String getValue() {
		return value;
	}
	private OpenstackManageEnum(String value) {
		this.value = value;
	}
	
	public static OpenstackManageEnum fromString(String value ) {
		if (value != null) {
			for (OpenstackManageEnum openstackManageEnum : OpenstackManageEnum.values()) {
				if (value.equalsIgnoreCase(openstackManageEnum.value)) {
					return openstackManageEnum;
				}
			}
		}
		return null;
	}
}
