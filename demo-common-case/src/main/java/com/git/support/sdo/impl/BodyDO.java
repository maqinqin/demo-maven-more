package com.git.support.sdo.impl;

public class BodyDO extends DataObject { 
	private static final long serialVersionUID = 1L;
	
	private BodyDO() {
		
	}
	
	public static BodyDO  CreateBodyDO() {
		return new BodyDO();
	}
}
