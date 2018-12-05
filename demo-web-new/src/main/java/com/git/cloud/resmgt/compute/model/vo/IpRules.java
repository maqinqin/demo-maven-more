package com.git.cloud.resmgt.compute.model.vo;
/**
 * 
 * @author renxinlei
 * Ip规则Vo
 */
public class IpRules {
	 private	String	id;
	 private  	int	num;
	 private  	String	ipTypeName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getIpTypeName() {
		return ipTypeName;
	}
	public void setIpTypeName(String ipTypeName) {
		this.ipTypeName = ipTypeName;
	}

}
