package com.git.cloud.sys.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class SysUserLimitPo extends BaseBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	// 数据库字段
	private String userId;
	private Integer limitCpu;
	private Integer limitMem;
	// 扩展字段
	private Integer availableCpu; // 可用的CPU
	private Integer availableMem; // 可用的内存
	
	public SysUserLimitPo() {
		
	}
	
	public SysUserLimitPo(String userId, Integer limitCpu, Integer limitMem) {
		this.userId = userId;
		this.limitCpu = limitCpu;
		this.limitMem = limitMem;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getLimitCpu() {
		return limitCpu;
	}
	public void setLimitCpu(Integer limitCpu) {
		this.limitCpu = limitCpu;
	}
	public Integer getLimitMem() {
		return limitMem;
	}
	public void setLimitMem(Integer limitMem) {
		this.limitMem = limitMem;
	}
	public Integer getAvailableCpu() {
		return availableCpu;
	}
	public void setAvailableCpu(Integer availableCpu) {
		this.availableCpu = availableCpu;
	}
	public Integer getAvailableMem() {
		return availableMem;
	}
	public void setAvailableMem(Integer availableMem) {
		this.availableMem = availableMem;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
