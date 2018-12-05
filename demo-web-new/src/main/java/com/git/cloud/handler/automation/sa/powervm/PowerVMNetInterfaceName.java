package com.git.cloud.handler.automation.sa.powervm;

/** 网络接口卡名称的枚举定义   
*/
public enum PowerVMNetInterfaceName {
	EN0("en0"),		/**< 生产地址 */
	EN1("en1"),		/**< 心跳地址 */
	EN2("en2"),	/**< 管理地址 */
	;
    
    private final String value;

    private PowerVMNetInterfaceName(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static PowerVMNetInterfaceName fromString(String value ) {
		if (value != null) {
			for (PowerVMNetInterfaceName n : PowerVMNetInterfaceName.values()) {
				if (value.equalsIgnoreCase(n.value)) {
					return n;
				}
			}
		}
		return null;
	}
}
