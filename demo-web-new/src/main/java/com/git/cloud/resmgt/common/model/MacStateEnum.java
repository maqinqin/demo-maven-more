package com.git.cloud.resmgt.common.model;

public enum MacStateEnum {
	PENDING("P"),INIT("I") ;
	private String value ;
	public String getValue(){
		return value ;
	}
	private MacStateEnum(String value){
		this.value=value ;
	}
	
	

}
