package com.git.cloud.request.model;

/**
 * @ClassName:SrTreeParentEnum
 * @Description:TODO
 * @author dongjinquan
 * @date 2014-10-10
 */
public enum SrTreeParentEnum {
	
	SR_TREE_PARENT("0");
	
    private final String value;
	
	private SrTreeParentEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
