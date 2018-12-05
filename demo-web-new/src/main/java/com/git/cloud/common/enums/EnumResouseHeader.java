package com.git.cloud.common.enums;

/**
 * @author zhangbh
 * @version 创建时间：2015-8-18 下午2:05:23 类说明
 */
public enum EnumResouseHeader {
	
	PV_RES_TYPE("PW"),
	PV_RES_CLASS("PW"),
	VM_RES_TYPE("VMWARE"), 
	VM_RES_CLASS("VM"),
	SSH_RES_TYPE("SSH"),
	SA_RES_CLASS("SA"),
	NET_RES_TYPE("NETAPP"),
	SE_RES_CLASS("SE"),
	KVM_RES_TYPE("KVM"),
	KVM_RES_CLASS("KVM"),
	NA_RES_CLASS("NA"), 
	;
	
	private final String value;

	private EnumResouseHeader(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static EnumResouseHeader fromString(String value) {
		if (value != null) {
			for (EnumResouseHeader c : EnumResouseHeader.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}
}
