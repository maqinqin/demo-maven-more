package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class RmVmManageServerPo extends BaseBO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2193678511459352906L;
	private String id;
	private String serverName;
	private String platformType;
	private String platformName;
	private String platformCode;
	private String vmType;
	private String virtualTypeName;
	private String domainName;
	private String version;
	private String manageOneIp;
	private String manageOneOcIp;
	private String manageProjectId;

	
	public String getManageOneOcIp() {
		return manageOneOcIp;
	}

	public void setManageOneOcIp(String manageOneOcIp) {
		this.manageOneOcIp = manageOneOcIp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getManageOneIp() {
		return manageOneIp;
	}

	public void setManageOneIp(String manageOneIp) {
		this.manageOneIp = manageOneIp;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	private String manageIp;
	private String isActive;
	private String cmPasswordid;
	private String userName;
	private String password;
	private String subMask;
	private String datacenterId;
	private String dataCenterName;
	
	/* 添加虚机管理服务器时输入的用户名和密码 */
	private CmPasswordPo cmPasswordPo;

	// Constructors

	/** default constructor */
	public RmVmManageServerPo(){
	}

	/** minimal constructor */
	public RmVmManageServerPo(String id){
		this.id = id;
	}

	/** full constructor */
	public RmVmManageServerPo(String id, String serverName, String platformType, String vmType, String manageIp, String isActive){
		this.id = id;
		this.serverName = serverName;
		this.platformType = platformType;
		this.vmType = vmType;
		this.manageIp = manageIp;
		this.isActive = isActive;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getPlatformType() {
		return this.platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getVmType() {
		return this.vmType;
	}

	public void setVmType(String vmType) {
		this.vmType = vmType;
	}

	public String getManageIp() {
		return this.manageIp;
	}

	public void setManageIp(String manageIp) {
		this.manageIp = manageIp;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCmPasswordid() {
		return cmPasswordid;
	}

	public void setCmPasswordid(String cmPasswordid) {
		this.cmPasswordid = cmPasswordid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubMask() {
		return subMask;
	}

	public void setSubMask(String subMask) {
		this.subMask = subMask;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	public CmPasswordPo getCmPasswordPo() {
		return cmPasswordPo;
	}

	public void setCmPasswordPo(CmPasswordPo cmPasswordPo) {
		this.cmPasswordPo = cmPasswordPo;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getVirtualTypeName() {
		return virtualTypeName;
	}

	public void setVirtualTypeName(String virtualTypeName) {
		this.virtualTypeName = virtualTypeName;
	}

	public String getDataCenterName() {
		return dataCenterName;
	}

	public void setDataCenterName(String dataCenterName) {
		this.dataCenterName = dataCenterName;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getManageProjectId() {
		return manageProjectId;
	}

	public void setManageProjectId(String manageProjectId) {
		this.manageProjectId = manageProjectId;
	}

}
