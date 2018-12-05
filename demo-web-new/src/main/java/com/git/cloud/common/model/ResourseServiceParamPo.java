package com.git.cloud.common.model;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="resourseServiceParam")
public class ResourseServiceParamPo {
	//服务供给申请:应用系统、数据中心、申请类型、服务器角色、CPU、MEM、DISK、云服务目录、服务套数
	private String appId;
	private String dataCenterId;
	private String operModelType;
	private String duId;
	private String CPU;
	private String MEM;
	private String DISK;
	private String serviceId;
	private String service_num;
	private String serviceTypeCode;
	private String secureAreaCode;
	private String sevureTierCode;
	
	public String getServiceTypeCode() {
		return serviceTypeCode;
	}
	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}
	public String getSecureAreaCode() {
		return secureAreaCode;
	}
	public void setSecureAreaCode(String secureAreaCode) {
		this.secureAreaCode = secureAreaCode;
	}
	public String getSevureTierCode() {
		return sevureTierCode;
	}
	public void setSevureTierCode(String sevureTierCode) {
		this.sevureTierCode = sevureTierCode;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getDataCenterId() {
		return dataCenterId;
	}
	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}
	public String getOperModelType() {
		return operModelType;
	}
	public void setOperModelType(String operModelType) {
		this.operModelType = operModelType;
	}
	public String getDuId() {
		return duId;
	}
	public void setDuId(String duId) {
		this.duId = duId;
	}
	public String getCPU() {
		return CPU;
	}
	public void setCPU(String cPU) {
		CPU = cPU;
	}
	public String getMEM() {
		return MEM;
	}
	public void setMEM(String mEM) {
		MEM = mEM;
	}
	public String getDISK() {
		return DISK;
	}
	public void setDISK(String dISK) {
		DISK = dISK;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getService_num() {
		return service_num;
	}
	public void setService_num(String service_num) {
		this.service_num = service_num;
	}
	
}