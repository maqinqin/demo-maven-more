package com.git.cloud.policy.model.vo;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-23
 */
public class PolicyResultVo {
	
    private String vmId;

    private String hostId;

    private String clusterId;

    private String cdpId;

    private String poolId;
    
    private String portGroupId;

    private String ip;//gyrx
    
    private String secureAreaCode;//安全区id
    
	private String sevureTierCode;//安全层id
	private String dataStoreId ;//VMdataStore信息
    
    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPortGroupId() {
		return portGroupId;
	}

	public void setPortGroupId(String portGroupId) {
		this.portGroupId = portGroupId;
	}

	public PolicyResultVo() {

	}

	public PolicyResultVo(String vmId, String hostId, String clusterId, String cdpId,
			String poolId) {
		super();
		this.vmId = vmId;
		this.hostId = hostId;
		this.clusterId = clusterId;
		this.cdpId = cdpId;
		this.poolId = poolId;
	}

	/**
	 * @return the vmId
	 */
	public String getVmId() {
		return vmId;
	}

	/**
	 * @param vmId the vmId to set
	 */
	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	/**
	 * @return the hostId
	 */
	public String getHostId() {
		return hostId;
	}

	/**
	 * @param hostId the hostId to set
	 */
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	/**
	 * @return the clusterId
	 */
	public String getClusterId() {
		return clusterId;
	}

	/**
	 * @param clusterId the clusterId to set
	 */
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	/**
	 * @return the cdpId
	 */
	public String getCdpId() {
		return cdpId;
	}

	/**
	 * @param cdpId the cdpId to set
	 */
	public void setCdpId(String cdpId) {
		this.cdpId = cdpId;
	}

	/**
	 * @return the poolId
	 */
	public String getPoolId() {
		return poolId;
	}

	/**
	 * @param poolId the poolId to set
	 */
	public void setPoolId(String poolId) {
		this.poolId = poolId;
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

	public String getDataStoreId() {
		return dataStoreId;
	}

	public void setDataStoreId(String dataStoreId) {
		this.dataStoreId = dataStoreId;
	}
	
}
