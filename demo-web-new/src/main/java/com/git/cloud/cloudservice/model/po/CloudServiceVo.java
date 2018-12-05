/**
 * @Title:CloudServiceVo.java
 * @Package:com.git.cloud.cloudservice.model.po
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-15
 * @version V1.0
 */
package com.git.cloud.cloudservice.model.po;

@SuppressWarnings("serial")
public class CloudServiceVo extends CloudServicePo {

	private String serviceTypeName;
	private String haTypeName;
	private String platformTypeName;
	private String systemTypeName;
	private String storageDataTypeName;
	private String imageName;
	private String vmTypeName;
	private String hostTypeName;
	private String diskCapacity;//镜像容量大小
	// 镜像的uuid
	private String iaasUuid;

    public String getIaasUuid() {
         return iaasUuid;
    }
    public void setIaasUuid(String iaasUuid) {
         this.iaasUuid = iaasUuid;
    }


	public String getDiskCapacity() {
		return diskCapacity;
	}
	public void setDiskCapacity(String diskCapacity) {
		this.diskCapacity = diskCapacity;
	}
	public String getHostTypeName() {
		return hostTypeName;
	}
	public void setHostTypeName(String hostTypeName) {
		this.hostTypeName = hostTypeName;
	}
	public String getVmTypeName() {
		return vmTypeName;
	}
	public void setVmTypeName(String vmTypeName) {
		this.vmTypeName = vmTypeName;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getSystemTypeName() {
		return systemTypeName;
	}
	public void setSystemTypeName(String systemTypeName) {
		this.systemTypeName = systemTypeName;
	}
	public String getStorageDataTypeName() {
		return storageDataTypeName;
	}
	public void setStorageDataTypeName(String storageDataTypeName) {
		this.storageDataTypeName = storageDataTypeName;
	}
	/**
	 * @return the serviceTypeName
	 */
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	/**
	 * @param serviceTypeName the serviceTypeName to set
	 */
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	/**
	 * @return the haTypeName
	 */
	public String getHaTypeName() {
		return haTypeName;
	}
	/**
	 * @param haTypeName the haTypeName to set
	 */
	public void setHaTypeName(String haTypeName) {
		this.haTypeName = haTypeName;
	}
	
	/**
	 * @return the platformTypeName
	 */
	public String getPlatformTypeName() {
		return platformTypeName;
	}
	
	/**
	 * @param platformTypeName the platformTypeName to set
	 */
	public void setPlatformTypeName(String platformTypeName) {
		this.platformTypeName = platformTypeName;
	}

}