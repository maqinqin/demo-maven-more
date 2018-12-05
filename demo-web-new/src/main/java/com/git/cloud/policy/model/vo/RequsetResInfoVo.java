/**
 * @Title:RequsetResInfoVo.java
 * @Package:com.git.cloud.policy.model.vo
 * @Description:查询可用物理机资源条件，供扩容使用
 * @author yangzhenhai
 * @date 2014-10-24 下午2:06:11
 * @version V1.0
 */
package com.git.cloud.policy.model.vo;

import com.git.cloud.cloudservice.model.HaTypeEnum;
import com.git.cloud.resmgt.common.model.DeviceTypeEnum;
import com.git.cloud.resmgt.common.model.VmDistriTypeEnum;

/**
 * @ClassName:RequsetResInfoVo
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-24 下午2:06:11
 *
 *
 */
public class RequsetResInfoVo {
	
	
    private String vmId;

    private String hostId;

    private String clusterId;

    private String cdpId;

    private String poolId;
    
    private String datacenterId;
    
    private String serviceType;
    
    private String secureAreaType;
    
    private String vmType;
    
	private String haType;
	
	private String playFormType;
	
	private String vmDistriType;
	
	private String deviceType;
	
	public RequsetResInfoVo(){
		this.deviceType=DeviceTypeEnum.DEVICE_TYPE_SERVER.getValue();
	}

	public RequsetResInfoVo(String vmId, String hostId, String clusterId,
			String cdpId, String poolId, String datacenterId,String serviceType,
			String secureAreaType, String haType, String playFormType,
			String vmDistriType, String deviceType,String vmType) {
		this.vmId = vmId;
		this.hostId = hostId;
		this.clusterId = clusterId;
		this.cdpId = cdpId;
		this.poolId = poolId;
		this.datacenterId= datacenterId;
		this.serviceType = serviceType;
		this.secureAreaType = secureAreaType;
		this.haType = haType;
		this.playFormType = playFormType;
		this.vmDistriType = vmDistriType;
		this.deviceType = deviceType;
		this.vmType= vmType;
	}
	public String getVmType() {
		return vmType;
	}

	public void setVmType(String vmType) {
		this.vmType = vmType;
	}

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

	/**
	 * @return the datacenterId
	 */
	public String getDatacenterId() {
		return datacenterId;
	}

	/**
	 * @param datacenterId the datacenterId to set
	 */
	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the secureAreaType
	 */
	public String getSecureAreaType() {
		return secureAreaType;
	}

	/**
	 * @param secureAreaType the secureAreaType to set
	 */
	public void setSecureAreaType(String secureAreaType) {
		this.secureAreaType = secureAreaType;
	}

	/**
	 * @return the haType
	 */
	public String getHaType() {
		return haType;
	}

	/**
	 * @param haType the haType to set
	 */
	public void setHaType(String haType) {
		if(haType.equals(HaTypeEnum.SINGLE.toString())){
			this.vmDistriType = VmDistriTypeEnum.VM_DISTRI_TYPE_SINGLE.getValue();
		}else{
			this.vmDistriType = VmDistriTypeEnum.VM_DISTRI_TYPE_CLUSTER.getValue();
		}
		this.haType = haType;
	}

	/**
	 * @return the playFormType
	 */
	public String getPlayFormType() {
		return playFormType;
	}

	/**
	 * @param playFormType the playFormType to set
	 */
	public void setPlayFormType(String playFormType) {
		this.playFormType = playFormType;
	}

	/**
	 * @return the vmDistriType
	 */
	public String getVmDistriType() {
		return vmDistriType;
	}

	/**
	 * @param vmDistriType the vmDistriType to set
	 */
	public void setVmDistriType(String vmDistriType) {
		this.vmDistriType = vmDistriType;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	
}
