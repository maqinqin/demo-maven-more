package com.git.cloud.resmgt.common.model;
/**
 * 资源池枚举类型
 * @author ygm
 *
 */
public enum ResPoolTypeEnum {
	RES_POOL_TYPE_COMPUTE("COMPUTE"),//计算资源池
	RES_POOL_TYPE_STORAGE("STORAGE"),//存储资源池
	RES_POOL_TYPE_NETWORK("NETWORK")//网络资源池
	;
    
    private final String value;

    private ResPoolTypeEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static ResPoolTypeEnum fromString(String value ) {
		if (value != null) {
			for (ResPoolTypeEnum c : ResPoolTypeEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}
}
