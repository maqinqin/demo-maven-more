package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;

public class CloudProjectPo  extends BaseBO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String vpcId;
	private String vpcName;
	private String remark;
	private String idActive;
	private String datacenterId; //数据中心id
	private String dataCenterName; //数据中心名称
	
	private String vmMsId;//所属服务器id
	private String vmMsName;//所属服务器名称
	
	private String projectId;
	private String manageIp;//所属服务器ip
	private String domainName;
	private String projectName;
	private String tenantId;
	private String iaasUuid;
	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	//数据中心
	private RmDatacenterPo rmDatacenterPo;
	//服务区管理
	private RmVmManageServerPo rmVmManageServerPo;
	
	
	
	
	public String getDatacenterId() {
		return datacenterId;
	}
	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}
	public String getVmMsId() {
		return vmMsId;
	}
	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}
	public String getIdActive() {
		return idActive;
	}
	public void setIdActive(String idActive) {
		this.idActive = idActive;
	}
	public RmDatacenterPo getRmDatacenterPo() {
		return rmDatacenterPo;
	}
	public void setRmDatacenterPo(RmDatacenterPo rmDatacenterPo) {
		this.rmDatacenterPo = rmDatacenterPo;
	}
	public RmVmManageServerPo getRmVmManageServerPo() {
		return rmVmManageServerPo;
	}
	public void setRmVmManageServerPo(RmVmManageServerPo rmVmManageServerPo) {
		this.rmVmManageServerPo = rmVmManageServerPo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVpcId() {
		return vpcId;
	}
	public void setVpcId(String vpcId) {
		this.vpcId = vpcId;
	}
	public String getVpcName() {
		return vpcName;
	}
	public void setVpcName(String vpcName) {
		this.vpcName = vpcName;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getManageIp() {
		return manageIp;
	}
	public void setManageIp(String manageIp) {
		this.manageIp = manageIp;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getVmMsName() {
		return vmMsName;
	}
	public void setVmMsName(String vmMsName) {
		this.vmMsName = vmMsName;
	}
	public String getDataCenterName() {
		return dataCenterName;
	}
	public void setDataCenterName(String dataCenterName) {
		this.dataCenterName = dataCenterName;
	}
	public String getIaasUuid() {
		return iaasUuid;
	}
	public void setIaasUuid(String iaasUuid) {
		this.iaasUuid = iaasUuid;
	}
	
	
}
