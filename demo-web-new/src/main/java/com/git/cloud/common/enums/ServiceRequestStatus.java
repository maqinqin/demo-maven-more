package com.git.cloud.common.enums;

public enum ServiceRequestStatus {
	/** 请求服务成功*/
	SUCCESS("SUCCESS"),
	/** 请求服务失败*/
	FAIL("FAIL"),	
	/** 等待审批*/
	REQUEST_WAIT_APPROVE("REQUEST_WAIT_APPROVE")
	;
    
    private final String value;

    private ServiceRequestStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static ServiceRequestStatus fromString(String value ) {
		if (value != null) {
			for (ServiceRequestStatus c : ServiceRequestStatus.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }


}
