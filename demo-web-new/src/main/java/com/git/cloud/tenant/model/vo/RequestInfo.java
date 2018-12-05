package com.git.cloud.tenant.model.vo;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.request.model.vo.BmSrAttrValVo;

public class RequestInfo extends BaseBO implements java.io.Serializable {
	private String operModelType;	//操作类型
	private Integer cpu;			//请求的总cpu
	private Integer mem;			//请求的总内存
	private Integer disk;			//请求的总磁盘
	private Integer vmNum;			//机器数
	private String tenantId;		// 租户ID
	private String platformTypeCode;//平台类型编码
	private String quotaCode;		//配额编码
	
	public RequestInfo(String tenantId) {
		this.tenantId = tenantId ;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getOperModelType() {
		return operModelType;
	}
	public void setOperModelType(String operModelType) {
		this.operModelType = operModelType;
	}
	public Integer getCpu() {
		return cpu;
	}
	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
	public Integer getMem() {
		return mem;
	}
	public void setMem(Integer mem) {
		this.mem = mem;
	}
	public Integer getDisk() {
		return disk;
	}
	public void setDisk(Integer disk) {
		this.disk = disk;
	}
	public Integer getVmNum() {
		return vmNum;
	}
	public void setVmNum(Integer vmNum) {
		this.vmNum = vmNum;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getPlatformTypeCode() {
		return platformTypeCode;
	}
	public void setPlatformTypeCode(String platformTypeCode) {
		this.platformTypeCode = platformTypeCode;
	}
	public String getQuotaCode() {
		return quotaCode;
	}
	public void setQuotaCode(String quotaCode) {
		this.quotaCode = quotaCode;
	}
	
}