package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmNwCclassPo.java 
 * @Package 	com.git.cloud.resmgt.network.model.po
 * @author 		syp
 * @date 		2014-9-15下午4:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwCclassPo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;
	private String cclassId;
	private String platformId;
	private String platformName;
	private String virtualTypeId;
	private String virtualTypeName;
	private String hostTypeId;
	private String hostTypeName;
	private String useId;
	private String useName;
	private String bclassId;
	private String useRelCode;
	private String secureAreaId;
	private String secureAreaName;
	private String secureTierId;
	private String secureTierName;
	private String cclassName;
	private String subnetmask;
	private String gateway;
	private String vlanId;
	private Long ipStart;
	private Long ipEnd;
	private Long aclassIp;
	private Long bclassIp;
	private Long cclassIp;
	private Long ipTotalCnt;
	private Long ipAvailCnt;
	private String datacenterId;
	private String convergeId;
	private String convergeName;
	private String isActive;
	private Long usedNum;
	private String remark;
//	private Timestamp updateTime;



	// Constructors

	/** default constructor */
	public RmNwCclassPo() {
	}

	/** full constructor */
	public RmNwCclassPo(String cclassId, String platformId,
			String platformName, String virtualTypeId, String virtualTypeName,
			String hostTypeId, String hostTypeName, String useId,
			String useName, String bclassId, String useRelCode,
			String secureAreaId, String secureAreaName, String secureTierId,
			String secureTierName, String cclassName, String subnetmask,
			String gateway, String vlanId, Long ipStart, Long ipEnd,
			Long aclassIp, Long bclassIp, Long cclassIp, Long ipTotalCnt,
			Long ipAvailCnt, String datacenterId, String convergeId,
			String convergeName, String isActive, Long usedNum) {
		super();
		this.cclassId = cclassId;
		this.platformId = platformId;
		this.platformName = platformName;
		this.virtualTypeId = virtualTypeId;
		this.virtualTypeName = virtualTypeName;
		this.hostTypeId = hostTypeId;
		this.hostTypeName = hostTypeName;
		this.useId = useId;
		this.useName = useName;
		this.bclassId = bclassId;
		this.useRelCode = useRelCode;
		this.secureAreaId = secureAreaId;
		this.secureAreaName = secureAreaName;
		this.secureTierId = secureTierId;
		this.secureTierName = secureTierName;
		this.cclassName = cclassName;
		this.subnetmask = subnetmask;
		this.gateway = gateway;
		this.vlanId = vlanId;
		this.ipStart = ipStart;
		this.ipEnd = ipEnd;
		this.aclassIp = aclassIp;
		this.bclassIp = bclassIp;
		this.cclassIp = cclassIp;
		this.ipTotalCnt = ipTotalCnt;
		this.ipAvailCnt = ipAvailCnt;
		this.datacenterId = datacenterId;
		this.convergeId = convergeId;
		this.convergeName = convergeName;
		this.isActive = isActive;
		this.usedNum = usedNum;
	}
	// Property accessors

	public String getCclassId() {
		return cclassId;
	}



	public void setCclassId(String cclassId) {
		this.cclassId = cclassId;
	}



	public String getBclassId() {
		return bclassId;
	}



	public void setBclassId(String bclassId) {
		this.bclassId = bclassId;
	}


	public String getUseRelCode() {
		return useRelCode;
	}


	public void setUseRelCode(String useRelCode) {
		this.useRelCode = useRelCode;
	}

	public String getSecureAreaId() {
		return secureAreaId;
	}


	public void setSecureAreaId(String secureAreaId) {
		this.secureAreaId = secureAreaId;
	}


	public String getSecureTierId() {
		return secureTierId;
	}


	public void setSecureTierId(String secureTierId) {
		this.secureTierId = secureTierId;
	}


	public String getCclassName() {
		return cclassName;
	}



	public void setCclassName(String cclassName) {
		this.cclassName = cclassName;
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



	public Long getAclassIp() {
		return aclassIp;
	}



	public void setAclassIp(Long aclassIp) {
		this.aclassIp = aclassIp;
	}



	public Long getBclassIp() {
		return bclassIp;
	}



	public void setBclassIp(Long bclassIp) {
		this.bclassIp = bclassIp;
	}



	public Long getCclassIp() {
		return cclassIp;
	}



	public void setCclassIp(Long cclassIp) {
		this.cclassIp = cclassIp;
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



	public String getDatacenterId() {
		return datacenterId;
	}



	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}



	public String getConvergeId() {
		return convergeId;
	}



	public void setConvergeId(String convergeId) {
		this.convergeId = convergeId;
	}



	public String getIsActive() {
		return isActive;
	}



	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getPlatformId() {
		return platformId;
	}


	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}


	public String getVirtualTypeId() {
		return virtualTypeId;
	}


	public void setVirtualTypeId(String virtualTypeId) {
		this.virtualTypeId = virtualTypeId;
	}


	public String getHostTypeId() {
		return hostTypeId;
	}


	public void setHostTypeId(String hostTypeId) {
		this.hostTypeId = hostTypeId;
	}


	public String getUseId() {
		return useId;
	}


	public void setUseId(String useId) {
		this.useId = useId;
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


	public String getHostTypeName() {
		return hostTypeName;
	}


	public void setHostTypeName(String hostTypeName) {
		this.hostTypeName = hostTypeName;
	}


	public String getUseName() {
		return useName;
	}


	public void setUseName(String useName) {
		this.useName = useName;
	}


	public String getSecureAreaName() {
		return secureAreaName;
	}


	public void setSecureAreaName(String secureAreaName) {
		this.secureAreaName = secureAreaName;
	}


	public String getSecureTierName() {
		return secureTierName;
	}


	public void setSecureTierName(String secureTierName) {
		this.secureTierName = secureTierName;
	}


	public String getConvergeName() {
		return convergeName;
	}


	public void setConvergeName(String convergeName) {
		this.convergeName = convergeName;
	}


	public Long getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(Long usedNum) {
		this.usedNum = usedNum;
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
