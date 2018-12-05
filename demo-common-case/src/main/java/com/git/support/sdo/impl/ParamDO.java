package com.git.support.sdo.impl;

public class ParamDO extends DataObject { 
	private static final long serialVersionUID = 1L;
	
	private ParamDO() {
		
	}
	
	public static ParamDO  CreateParamDO() {
		return new ParamDO();
	}
}
