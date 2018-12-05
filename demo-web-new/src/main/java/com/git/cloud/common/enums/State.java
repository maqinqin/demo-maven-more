package com.git.cloud.common.enums;

public enum State {
	/**< 已关闭 */
	//CLOSED("CLOSED"),
	READ("READ"),
	/**< 未关闭 */
	//NOTCLOSE("NOTCLOSE")  
	UNREAD("UNREAD")
	;
    
    private final String value;

    private State(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static State fromString(String value ) {
		if (value != null) {
			for (State c : State.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }


}
