package com.git.support.sdo.impl;

import com.git.support.common.MesgFlds;

public class HeaderDO extends DataObject { 
	private static final long serialVersionUID = 1L;
	
	private HeaderDO() {
		
	}
	
	public static HeaderDO  CreateHeaderDO() {
		return new HeaderDO();
	}
	
	public String getResourceClass(){
		return getString(MesgFlds.RESOURCE_CLASS);
	}
	
	public void setResourceClass(String resourceClass){
		setString(MesgFlds.RESOURCE_CLASS, resourceClass);
	}
	
	public String getResourceType(){
		return getString(MesgFlds.RESOURCE_TYPE);
	}
	
	public void setResourceType(String resourceType){
		setString(MesgFlds.RESOURCE_TYPE, resourceType);
	}
	
	public int getOperation(){
		return getInt(MesgFlds.OPERATION);
	}
	
	public void setOperation(int Operation){
		setInt(MesgFlds.OPERATION, Operation);
	}
	
	public String getAction(){
		return getString(MesgFlds.OPERATION);
	}
	
	public void setAction(String Operation){
		setString(MesgFlds.ACTION, Operation);
	}
	
	public String getRetCode(){
		return getString(MesgFlds.RET_CODE);
	}
	
	public void setRetCode(String retCode){
		setString(MesgFlds.RET_CODE, retCode);
	}
	
	public String getRetMesg(){
		return getString(MesgFlds.RET_MESG);
	}
	
	public void setRetMesg(String retMsg){
		setString(MesgFlds.RET_MESG, retMsg);
	}
	
	public String getOperationBean(){
		return getString(MesgFlds.OPERATION_BEAN);
	}
	
	public void setOperationBean(String operationBean){
		setString(MesgFlds.OPERATION_BEAN, operationBean);
	}
	
}
