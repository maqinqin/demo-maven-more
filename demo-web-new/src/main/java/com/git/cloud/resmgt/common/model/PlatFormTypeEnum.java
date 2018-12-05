package com.git.cloud.resmgt.common.model;
/**
 * 云服务定义：类型枚举
 * @author git
 *
 */
public enum PlatFormTypeEnum {
	X("X","X86平台"),
	P("P","POWER平台"),
	H("H","惠普平台"),
	O("O","OpenstackX86平台"),
	PV("PV","PowerVC平台")
	;
	
	private String code;
	private String cnName;
	
	private PlatFormTypeEnum(String code,String cnName){
		this.code = code;
		this.cnName = cnName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	
	
}
