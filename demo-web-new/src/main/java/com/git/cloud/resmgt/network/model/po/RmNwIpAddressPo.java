package com.git.cloud.resmgt.network.model.po;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmNwIpAddressPo.java 
 * @Package 	com.git.cloud.resmgt.network.model.po
 * @author 		syp
 * @date 		2014-9-15下午4:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwIpAddressPo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;
	private String ip;
	private String cclassId;
	private Long seq;
	private String useRelCode;
	private String resPoolId;
	private String resCdpId;
	private String appDuId;
	private String resClusterId;
	private String deviceId;
	private String allocedStatusCode;
//	private String updateUser;
	private Date allocedTime;
	//回收
	private String releaseId;
    private Date releaseTime;
    
    // 资源池id
    private String nwResPoolId;
    // 规则明细id
    private String nwRuleListId;
    //端口组id
    private String portGroupId;
    
    private String projectId;
    
    private String virtualTypeCode;
    
    
    private String instanceId;
    
    private String netWorkId;
    
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getVirtualTypeCode() {
		return virtualTypeCode;
	}

	public void setVirtualTypeCode(String virtualTypeCode) {
		this.virtualTypeCode = virtualTypeCode;
	}

	public String getPortGroupId() {
		return portGroupId;
	}

	public void setPortGroupId(String portGroupId) {
		this.portGroupId = portGroupId;
	}

	public RmNwIpAddressPo() {
	}

	/** full constructor */

	public RmNwIpAddressPo(String ip, String cclassId, Long seq,
			String useRelCode, String resPoolId, String resCdpId,
			String appDuId, String resClusterId, String deviceId,
			String allocedStatusCode, Date allocedTime, String releaseId,
			Date releaseTime) {
		super();
		this.ip = ip;
		this.cclassId = cclassId;
		this.seq = seq;
		this.useRelCode = useRelCode;
		this.resPoolId = resPoolId;
		this.resCdpId = resCdpId;
		this.appDuId = appDuId;
		this.resClusterId = resClusterId;
		this.deviceId = deviceId;
		this.allocedStatusCode = allocedStatusCode;
		this.allocedTime = allocedTime;
		this.releaseId = releaseId;
		this.releaseTime = releaseTime;
	}

	public String getNwRuleListId() {
		return nwRuleListId;
	}

	public void setNwRuleListId(String nwRuleListId) {
		this.nwRuleListId = nwRuleListId;
	}

	public String getNwResPoolId() {
		return nwResPoolId;
	}

	public void setNwResPoolId(String nwResPoolId) {
		this.nwResPoolId = nwResPoolId;
	}

	public String getReleaseId() {
		return releaseId;
	}


	public Date getAllocedTime() {
		return allocedTime;
	}

	public void setAllocedTime(Date allocedTime) {
		this.allocedTime = allocedTime;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getCclassId() {
		return cclassId;
	}

	public void setCclassId(String cclassId) {
		this.cclassId = cclassId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getUseRelCode() {
		return useRelCode;
	}

	public void setUseRelCode(String useRelCode) {
		this.useRelCode = useRelCode;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getResCdpId() {
		return resCdpId;
	}

	public void setResCdpId(String resCdpId) {
		this.resCdpId = resCdpId;
	}

	public String getAppDuId() {
		return appDuId;
	}

	public void setAppDuId(String appDuId) {
		this.appDuId = appDuId;
	}

	public String getResClusterId() {
		return resClusterId;
	}

	public void setResClusterId(String resClusterId) {
		this.resClusterId = resClusterId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAllocedStatusCode() {
		return allocedStatusCode;
	}

	public void setAllocedStatusCode(String allocedStatusCode) {
		this.allocedStatusCode = allocedStatusCode;
	}
	
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getNetWorkId() {
		return netWorkId;
	}

	public void setNetWorkId(String netWorkId) {
		this.netWorkId = netWorkId;
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
