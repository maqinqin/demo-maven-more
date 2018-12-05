package com.git.cloud.resmgt.network.model.po;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title RmNwResPoolPo.java
 * @Package com.git.cloud.resmgt.network.model.po
 * @author syp
 * @date 2014-9-15下午4:32:26
 * @version 1.0.0
 * @Description
 * 
 */
public class RmNwResPoolPo extends BaseBO {

	private static final long serialVersionUID = 1L;
	private String resPoolId;// 资源池ID
	private String nwResPoolTypeCode;// 网络资源池类型
	
	private String cclassId;
	private int ipStart;
	private int ipEnd;
	private int ipTotalCnt;
	private int ipAvailCnt;
	private String convergeId;
	private String subnetMask;
	private String gateway;
	private String vlanId;
	private String secureAreaId;
	private String secureTierId;
	private String platformId;
	private String virtualTypeId;
	private String hostTypeId;
	private String useId;
	private String datacenterId;
	private String isActive;
	private String isEnable;
	private String updateUser;
	private Date updateTime;
	private String projectId;
	
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * <p>Title:</p>
	 * <p>Description:</p>
	 */
	public RmNwResPoolPo() {
		super();
	}
	/**
	 * <p>Title:</p>
	 * <p>Description:</p>
	 * @param resPoolId
	 * @param nwResPoolTypeCode
	 * @param cclassId
	 * @param ipStart
	 * @param ipEnd
	 * @param ipTotalCnt
	 * @param ipAvailCnt
	 * @param convergeId
	 * @param subnetMask
	 * @param gateway
	 * @param vlanId
	 * @param secureAreaId
	 * @param secureTierId
	 * @param platformId
	 * @param virtualTypeId
	 * @param hostTypeId
	 * @param useId
	 * @param isActive
	 * @param isEnable
	 */
	public RmNwResPoolPo(String resPoolId, String nwResPoolTypeCode, String cclassId, int ipStart, int ipEnd, int ipTotalCnt, int ipAvailCnt, String convergeId, String subnetMask, String gateway,
			String vlanId, String secureAreaId, String secureTierId, String platformId, String virtualTypeId, String hostTypeId, String useId, String isActive, String isEnable) {
		super();
		this.resPoolId = resPoolId;
		this.nwResPoolTypeCode = nwResPoolTypeCode;
		this.cclassId = cclassId;
		this.ipStart = ipStart;
		this.ipEnd = ipEnd;
		this.ipTotalCnt = ipTotalCnt;
		this.ipAvailCnt = ipAvailCnt;
		this.convergeId = convergeId;
		this.subnetMask = subnetMask;
		this.gateway = gateway;
		this.vlanId = vlanId;
		this.secureAreaId = secureAreaId;
		this.secureTierId = secureTierId;
		this.platformId = platformId;
		this.virtualTypeId = virtualTypeId;
		this.hostTypeId = hostTypeId;
		this.useId = useId;
		this.isActive = isActive;
		this.isEnable = isEnable;
	}
	public String getResPoolId() {
		return resPoolId;
	}
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}
	public String getNwResPoolTypeCode() {
		return nwResPoolTypeCode;
	}
	public void setNwResPoolTypeCode(String nwResPoolTypeCode) {
		this.nwResPoolTypeCode = nwResPoolTypeCode;
	}
	
	public String getCclassId() {
		return cclassId;
	}
	public void setCclassId(String cclassId) {
		this.cclassId = cclassId;
	}
	public int getIpStart() {
		return ipStart;
	}
	public void setIpStart(int ipStart) {
		this.ipStart = ipStart;
	}
	public int getIpEnd() {
		return ipEnd;
	}
	public void setIpEnd(int ipEnd) {
		this.ipEnd = ipEnd;
	}
	public int getIpTotalCnt() {
		return ipTotalCnt;
	}
	public void setIpTotalCnt(int ipTotalCnt) {
		this.ipTotalCnt = ipTotalCnt;
	}
	public int getIpAvailCnt() {
		return ipAvailCnt;
	}
	public void setIpAvailCnt(int ipAvailCnt) {
		this.ipAvailCnt = ipAvailCnt;
	}
	public String getConvergeId() {
		return convergeId;
	}
	public void setConvergeId(String convergeId) {
		this.convergeId = convergeId;
	}
	public String getSubnetMask() {
		return subnetMask;
	}
	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	
	public String getDatacenterId() {
		return datacenterId;
	}
	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/* (non-Javadoc)
	 * <p>Title:getBizId</p>
	 * <p>Description:</p>
	 * @return
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		return resPoolId;
	}
}
