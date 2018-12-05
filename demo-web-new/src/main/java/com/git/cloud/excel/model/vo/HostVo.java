package com.git.cloud.excel.model.vo;

import java.io.Serializable;
import java.util.List;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.resmgt.common.model.po.CmLocalDiskPo;
import com.git.cloud.resmgt.common.model.vo.CmVmVo;

public class HostVo extends BaseBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String dataCenterCode;
	private String clusterCode;
	private String hostName;
	private String hostIp;
	private String hostMem;
	private String hostCpu;
	private String hostSn;
	private String nwRuleListId;
	private String userName;
	private String password;
	private String hostType;
	private String clusterId;
	private String deviceModelId;
	private String seatId;
	private String defaultDatastoreType;
	private String defaultDatastoreId;
	private String hostUser;
	private String hostPwd;
	private String usedCpu;
	private String usedMem;
	private String resPoolId;
	private String hostTargerId;//同步计算资源池用到的  外部主键
	private String disk;
	private CmLocalDiskPo cmLocalDiskPo;
	private List<CmVmVo> vmList;
	private List<DataStoreVo> ldDataStoreList;
	private List<DataStoreVo> sanDataStoreList;
	private List<CmLocalDiskPo> localDiskList;
	
	public HostVo(String id,String dataCenterCode, String resPoolId,String clusterCode,
			String hostName, String hostMem, String hostCpu,
			String usedCpu,String usedMem,String hostIp) {
		super();
		this.id = id;
		this.dataCenterCode = dataCenterCode;
		this.resPoolId = resPoolId;
		this.clusterCode = clusterCode;
		this.hostName = hostName;
		this.hostIp = hostIp;
		this.hostMem = hostMem;
		this.hostCpu = hostCpu;
		this.usedCpu = usedCpu;
		this.usedMem = usedMem;
	}
	
	public String getHostUser() {
		return hostUser;
	}
	public void setHostUser(String hostUser) {
		this.hostUser = hostUser;
	}
	public String getHostPwd() {
		return hostPwd;
	}
	public void setHostPwd(String hostPwd) {
		this.hostPwd = hostPwd;
	}
	public String getDefaultDatastoreType() {
		return defaultDatastoreType;
	}
	public void setDefaultDatastoreType(String defaultDatastoreType) {
		this.defaultDatastoreType = defaultDatastoreType;
	}
	public String getDefaultDatastoreId() {
		return defaultDatastoreId;
	}
	public void setDefaultDatastoreId(String defaultDatastoreId) {
		this.defaultDatastoreId = defaultDatastoreId;
	}
	public String getSeatId() {
		return seatId;
	}
	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
	public String getDeviceModelId() {
		return deviceModelId;
	}
	public void setDeviceModelId(String deviceModelId) {
		this.deviceModelId = deviceModelId;
	}
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	public String getHostType() {
		return hostType;
	}
	public void setHostType(String hostType) {
		this.hostType = hostType;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataCenterCode() {
		return dataCenterCode;
	}
	public void setDataCenterCode(String dataCenterCode) {
		this.dataCenterCode = dataCenterCode;
	}
	public String getClusterCode() {
		return clusterCode;
	}
	public void setClusterCode(String clusterCode) {
		this.clusterCode = clusterCode;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public String getHostMem() {
		return hostMem;
	}
	public void setHostMem(String hostMem) {
		this.hostMem = hostMem;
	}
	public String getHostCpu() {
		return hostCpu;
	}
	public void setHostCpu(String hostCpu) {
		this.hostCpu = hostCpu;
	}
	public String getHostSn() {
		return hostSn;
	}
	public void setHostSn(String hostSn) {
		this.hostSn = hostSn;
	}
	public String getNwRuleListId() {
		return nwRuleListId;
	}
	public void setNwRuleListId(String nwRuleListId) {
		this.nwRuleListId = nwRuleListId;
	}
	public HostVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HostVo(String id, String dataCenterCode, String clusterCode,
			String hostName, String hostIp, String hostMem, String hostCpu,
			String hostSn, String nwRuleListId) {
		super();
		this.id = id;
		this.dataCenterCode = dataCenterCode;
		this.clusterCode = clusterCode;
		this.hostName = hostName;
		this.hostIp = hostIp;
		this.hostMem = hostMem;
		this.hostCpu = hostCpu;
		this.hostSn = hostSn;
		this.nwRuleListId = nwRuleListId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUsedCpu() {
		return usedCpu;
	}

	public void setUsedCpu(String usedCpu) {
		this.usedCpu = usedCpu;
	}

	public String getUsedMem() {
		return usedMem;
	}

	public void setUsedMem(String usedMem) {
		this.usedMem = usedMem;
	}

	public List<CmVmVo> getVmList() {
		return vmList;
	}

	public void setVmList(List<CmVmVo> vmList) {
		this.vmList = vmList;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public List<DataStoreVo> getLdDataStoreList() {
		return ldDataStoreList;
	}

	public void setLdDataStoreList(List<DataStoreVo> ldDataStoreList) {
		this.ldDataStoreList = ldDataStoreList;
	}
	public List<DataStoreVo> getSanDataStoreList() {
		return sanDataStoreList;
	}

	public void setSanDataStoreList(List<DataStoreVo> sanDataStoreList) {
		this.sanDataStoreList = sanDataStoreList;
	}

	public List<CmLocalDiskPo> getLocalDiskList() {
		return localDiskList;
	}

	public void setLocalDiskList(List<CmLocalDiskPo> localDiskList) {
		this.localDiskList = localDiskList;
	}



	public String getHostTargerId() {
		return hostTargerId;
	}

	public void setHostTargerId(String hostTargerId) {
		this.hostTargerId = hostTargerId;
	}

	public CmLocalDiskPo getCmLocalDiskPo() {
		return cmLocalDiskPo;
	}

	public void setCmLocalDiskPo(CmLocalDiskPo cmLocalDiskPo) {
		this.cmLocalDiskPo = cmLocalDiskPo;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}


	
}
