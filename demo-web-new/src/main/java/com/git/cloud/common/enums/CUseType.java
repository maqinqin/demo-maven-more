package com.git.cloud.common.enums;

public enum CUseType {
	MGMT("M"),		/**< 管理 */
	PROD("P"),		/**< 生产 */
	ILO("ILO"),	    /**< ILO */
	VMO("VMO"),		/**< vMotion */
	PRI("PRI"),	    /**< 私有 */
	FSP1("FSP1"),	/**< 带外管理1 */
	FSP2("FSP2"),	/**< 带外管理2 */
	;
    
    private final String value;

    private CUseType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static CUseType fromString(String value ) {
		if (value != null) {
			for (CUseType c : CUseType.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }
}
