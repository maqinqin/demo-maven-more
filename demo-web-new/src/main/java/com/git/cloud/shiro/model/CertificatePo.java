package com.git.cloud.shiro.model;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class CertificatePo extends BaseBO implements java.io.Serializable{
	private String id;
	private String certificateName;
	private String certificatePath;
	private Date lastLoginTime;

	
	public CertificatePo(){
		
	}
	public CertificatePo(String id,String certificateName,String certificatePath,Date lastLoginTime ){
		super();
		this.id=id;
		this.certificateName=certificateName;
		this.certificatePath=certificatePath;
		this.lastLoginTime=lastLoginTime;
		
		
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	public String getCertificatePath() {
		return certificatePath;
	}
	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
