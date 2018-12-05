package com.git.cloud.common.enums;

public enum BillingParamEnum {
	/**服务申请类型  */
	SR_TYPE_MARK("srTypeMark"),
	/** 计费类型 */
	COST_TYPE("costType"),
	/**CPU数 */
	CPU_NUM("cpuNum"),
	/**MEM 数 */
	MEM_NUM("memNum"),
	/**磁盘大小*/
	DISK_SIZE("diskSize"),
	/**浮动ip数 */
	FLOATIP_NUM("floatIpNum"),
	/**租户id */
	TENANT_ID("tenantId"),
	/**用户id */
	USER_ID("userId"),
	/**实例id */
	INSTANCE_ID("instanceId"),
	/** */
	SERVICE_ID("serviceId")
	;
    
    private final String value;

    private BillingParamEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static BillingParamEnum fromString(String value ) {
		if (value != null) {
			for (BillingParamEnum c : BillingParamEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }


}
