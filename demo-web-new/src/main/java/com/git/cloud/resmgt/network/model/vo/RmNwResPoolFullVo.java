package com.git.cloud.resmgt.network.model.vo;

import java.sql.Timestamp;
import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwResPoolFullVo extends BaseBO implements java.io.Serializable{

	private static final long serialVersionUID = 6997663773772673432L;
	private String id;
	private String datacenterId;
	private String poolName;
	private String ename;
	private String status;
	private String poolType;
	private String isActive;
	private String remark;
	
//	private String resPoolId;//资源池ID
	private String nwResPoolTypeCode;//网络资源池类型
	private String nwResPoolTypeName;
//wmy
	private String resPoolId;
	private String cclassId;
	private String cclassName;
	private String platformId;
	private String platformName;
	private String virtualTypeId;
	private String virtualTypeName;
	private String hostTypeId;
	private String hostTypeName;
	private String useId;
	private String useName;
	private String secureAreaId;
	private String secureAreaName;
	private String secureTierId;
	private String secureTierName;
	private String convergeId;
	private String convergeName;
	private Long ipTotalCnt;
	private Long ipAvailCnt;
	private Long ipStart;
	private Long ipEnd;
	private String validateIp;//记录临时IP
	private String subnetmask;
	private String gateway;
	private String vlanId;
	private String isEnable;
	private String updateUser;
	private Date createTime;
	private Timestamp updateTime;
	
//选择openstack平台 保存的信息
	private String vmMsId;
	private String vpcId; //PROJECT_ID vpc id
	private String virtualRouterId;//ROUTER_ID 虚拟路由器 Id
	private String vmMsName;
	private String vpcName;
	private String virtualRouterName;
	private String networkType;
	private String physicalNetwork;
	private String subnetAddress;
	private String subnetEnd;
	private String maskBit;
	private String isExternal;
	
	public String getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(String isExternal) {
		this.isExternal = isExternal;
	}

	public String getSubnetAddress() {
		return subnetAddress;
	}

	public void setSubnetAddress(String subnetAddress) {
		this.subnetAddress = subnetAddress;
	}

	public String getSubnetEnd() {
		return subnetEnd;
	}

	public void setSubnetEnd(String subnetEnd) {
		this.subnetEnd = subnetEnd;
	}

	public String getMaskBit() {
		return maskBit;
	}

	public void setMaskBit(String maskBit) {
		this.maskBit = maskBit;
	}

	public String getPhysicalNetwork() {
		return physicalNetwork;
	}

	public void setPhysicalNetwork(String physicalNetwork) {
		this.physicalNetwork = physicalNetwork;
	}

	/** default constructor */
	public RmNwResPoolFullVo() {
	}

	/** minimal constructor */
	public RmNwResPoolFullVo(String id) {
		this.id = id;
	}


	/** full constructor */

	public RmNwResPoolFullVo(String id, String datacenterId, String poolName,
			String ename, String status, String poolType, String isActive,
			String remark, String nwResPoolTypeCode, String nwResPoolTypeName) {
		super();
		this.id = id;
		this.datacenterId = datacenterId;
		this.poolName = poolName;
		this.ename = ename;
		this.status = status;
		this.poolType = poolType;
		this.isActive = isActive;
		this.remark = remark;
		this.nwResPoolTypeCode = nwResPoolTypeCode;
		this.nwResPoolTypeName = nwResPoolTypeName;
	}
	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPoolName() {
		return this.poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPoolType() {
		return this.poolType;
	}

	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	public String getNwResPoolTypeCode() {
		return nwResPoolTypeCode;
	}

	public void setNwResPoolTypeCode(String nwResPoolTypeCode) {
		this.nwResPoolTypeCode = nwResPoolTypeCode;
	}
	

	public String getNwResPoolTypeName() {
		return nwResPoolTypeName;
	}

	public void setNwResPoolTypeName(String nwResPoolTypeName) {
		this.nwResPoolTypeName = nwResPoolTypeName;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCclassId() {
		return cclassId;
	}

	public void setCclassId(String cclassId) {
		this.cclassId = cclassId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getVirtualTypeId() {
		return virtualTypeId;
	}

	public void setVirtualTypeId(String virtualTypeId) {
		this.virtualTypeId = virtualTypeId;
	}

	public String getVirtualTypeName() {
		return virtualTypeName;
	}

	public void setVirtualTypeName(String virtualTypeName) {
		this.virtualTypeName = virtualTypeName;
	}

	public String getHostTypeId() {
		return hostTypeId;
	}

	public void setHostTypeId(String hostTypeId) {
		this.hostTypeId = hostTypeId;
	}

	public String getHostTypeName() {
		return hostTypeName;
	}

	public void setHostTypeName(String hostTypeName) {
		this.hostTypeName = hostTypeName;
	}

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
	}

	public String getSecureAreaId() {
		return secureAreaId;
	}

	public void setSecureAreaId(String secureAreaId) {
		this.secureAreaId = secureAreaId;
	}

	public String getSecureAreaName() {
		return secureAreaName;
	}

	public void setSecureAreaName(String secureAreaName) {
		this.secureAreaName = secureAreaName;
	}

	public String getSecureTierId() {
		return secureTierId;
	}

	public void setSecureTierId(String secureTierId) {
		this.secureTierId = secureTierId;
	}

	public String getSecureTierName() {
		return secureTierName;
	}

	public void setSecureTierName(String secureTierName) {
		this.secureTierName = secureTierName;
	}

	public Long getIpTotalCnt() {
		return ipTotalCnt;
	}

	public void setIpTotalCnt(Long ipTotalCnt) {
		this.ipTotalCnt = ipTotalCnt;
	}

	public Long getIpAvailCnt() {
		return ipAvailCnt;
	}

	public void setIpAvailCnt(Long ipAvailCnt) {
		this.ipAvailCnt = ipAvailCnt;
	}

	public Long getIpStart() {
		return ipStart;
	}

	public void setIpStart(Long ipStart) {
		this.ipStart = ipStart;
	}

	public Long getIpEnd() {
		return ipEnd;
	}

	public void setIpEnd(Long ipEnd) {
		this.ipEnd = ipEnd;
	}

	public String getSubnetmask() {
		return subnetmask;
	}

	public void setSubnetmask(String subnetmask) {
		this.subnetmask = subnetmask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getVlanId() {
		return vlanId;
	}

	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}

	public String getConvergeId() {
		return convergeId;
	}

	public void setConvergeId(String convergeId) {
		this.convergeId = convergeId;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}


	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCclassName() {
		return cclassName;
	}

	public void setCclassName(String cclassName) {
		this.cclassName = cclassName;
	}

	public String getConvergeName() {
		return convergeName;
	}

	public void setConvergeName(String convergeName) {
		this.convergeName = convergeName;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getValidateIp() {
		return validateIp;
	}

	public void setValidateIp(String validateIp) {
		this.validateIp = validateIp;
	}

	public String getVmMsId() {
		return vmMsId;
	}

	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}

	public String getVpcId() {
		return vpcId;
	}

	public void setVpcId(String vpcId) {
		this.vpcId = vpcId;
	}

	public String getVirtualRouterId() {
		return virtualRouterId;
	}

	public void setVirtualRouterId(String virtualRouterId) {
		this.virtualRouterId = virtualRouterId;
	}

	public String getVmMsName() {
		return vmMsName;
	}

	public void setVmMsName(String vmMsName) {
		this.vmMsName = vmMsName;
	}

	public String getVpcName() {
		return vpcName;
	}

	public void setVpcName(String vpcName) {
		this.vpcName = vpcName;
	}

	public String getVirtualRouterName() {
		return virtualRouterName;
	}

	public void setVirtualRouterName(String virtualRouterName) {
		this.virtualRouterName = virtualRouterName;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	
	
}
