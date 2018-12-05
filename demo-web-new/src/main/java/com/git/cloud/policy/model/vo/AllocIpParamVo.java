package com.git.cloud.policy.model.vo;


import com.git.cloud.common.model.base.BaseBO;

//网络地址区间信息
public class AllocIpParamVo extends BaseBO {
	private static final long serialVersionUID = -1054771076756157750L;
	private String cdpId;
	private String resPoolId;
	private String platformType;//平台类型
	private String vmType;//虚拟化类型
	private String deviceType;//主机类型
	private String networkConvergence;//网络汇聚
	private String appDuId;
	private String clusterId;
	private String secureAreaType;//安全区域编码
	private String secureLayer;//安全分层编码,若没层，请传null
	private String datacenterId;
	
	private String useRelCode;//接口传参不用
	private int totalNum;//接口传参不用，用于计算可用个数
	private String cloudServiceHATypeCode;
	
	// 用途id，用于分配
	private String useId;
	
	public AllocIpParamVo() {
		super();
	}


	@Override
	public String toString() {
		return "AllocIpParamVo [cdpId=" + cdpId + ", resPoolId=" + resPoolId + ", platformType=" + platformType + ", vmType=" + vmType + ", deviceType=" + deviceType + ", networkConvergence="
				+ networkConvergence + ", appDuId=" + appDuId + ", clusterId=" + clusterId + ", secureAreaType=" + secureAreaType + ", secureLayer=" + secureLayer + ", datacenterId=" + datacenterId
				+ ", useRelCode=" + useRelCode + ", totalNum=" + totalNum + ", cloudServiceHATypeCode=" + cloudServiceHATypeCode + "]";
	}

	public AllocIpParamVo(String cdpId, String resPoolId, String platformType,
			String vmType, String deviceType, String networkConvergence,
			String appDuId, String clusterId, String secureAreaType,
			String secureLayer, String datacenterId) {
		super();
		this.cdpId = cdpId;
		this.resPoolId = resPoolId;
		this.platformType = platformType;
		this.vmType = vmType;
		this.deviceType = deviceType;
		this.networkConvergence = networkConvergence;
		this.appDuId = appDuId;
		this.clusterId = clusterId;
		this.secureAreaType = secureAreaType;
		this.secureLayer = secureLayer;
		this.datacenterId = datacenterId;
	}
	public AllocIpParamVo(String cdpId, String resPoolId, String platformType,
			String vmType, String deviceType, String networkConvergence,
			String appDuId, String clusterId, String secureAreaType,
			String secureLayer, String datacenterId, String useRelCode,
			int totalNum) {
		super();
		this.cdpId = cdpId;
		this.resPoolId = resPoolId;
		this.platformType = platformType;
		this.vmType = vmType;
		this.deviceType = deviceType;
		this.networkConvergence = networkConvergence;
		this.appDuId = appDuId;
		this.clusterId = clusterId;
		this.secureAreaType = secureAreaType;
		this.secureLayer = secureLayer;
		this.datacenterId = datacenterId;
		this.useRelCode = useRelCode;
		this.totalNum = totalNum;
	}


	public String getUseRelCode() {
		return useRelCode;
	}


	public void setUseRelCode(String useRelCode) {
		this.useRelCode = useRelCode;
	}


	public String getCdpId() {
		return cdpId;
	}
	public void setCdpId(String cdpId) {
		this.cdpId = cdpId;
	}
	public String getResPoolId() {
		return resPoolId;
	}
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public String getVmType() {
		return vmType;
	}
	public void setVmType(String vmType) {
		this.vmType = vmType;
	}
	public String getNetworkConvergence() {
		return networkConvergence;
	}
	public void setNetworkConvergence(String networkConvergence) {
		this.networkConvergence = networkConvergence;
	}
	public String getAppDuId() {
		return appDuId;
	}
	public void setAppDuId(String appDuId) {
		this.appDuId = appDuId;
	}
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	public String getSecureAreaType() {
		return secureAreaType;
	}
	public void setSecureAreaType(String secureAreaType) {
		this.secureAreaType = secureAreaType;
	}
	public String getSecureLayer() {
		return secureLayer;
	}
	public void setSecureLayer(String secureLayer) {
		this.secureLayer = secureLayer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	public String getCloudServiceHATypeCode() {
		return cloudServiceHATypeCode;
	}


	public void setCloudServiceHATypeCode(String cloudServiceHATypeCode) {
		this.cloudServiceHATypeCode = cloudServiceHATypeCode;
	}


	public String getUseId() {
		return useId;
	}


	public void setUseId(String useId) {
		this.useId = useId;
	}


	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
