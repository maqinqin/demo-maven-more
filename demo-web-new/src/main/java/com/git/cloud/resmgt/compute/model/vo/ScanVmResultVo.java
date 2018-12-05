package com.git.cloud.resmgt.compute.model.vo;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.cloudservice.model.po.CloudServiceVo;

public class ScanVmResultVo {
		private String vmId;
		private	String vmName;
		private	String cpu;
		private	String memory;
		private	String DataStore;
		private	String cloudServiceId;
		private String cloudServiceName;
		private String addDuName;
		private	String addDuId;
		private	String importResult;
		private String ip;
		private List<CloudServiceVoByRmHost> cloudServiceList;
		private List<IpObj> ipList;
		private int isExist;
		private List<IpRules> rules ;
		//所属主机类型
		private String hostType;
		private String powerState;
		
		public String getPowerState() {
			return powerState;
		}
		public void setPowerState(String powerState) {
			this.powerState = powerState;
		}
		public String getHostType() {
			return hostType;
		}
		public void setHostType(String hostType) {
			this.hostType = hostType;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public List<IpRules> getRules() {
			return rules;
		}
		public void setRules(List<IpRules> rules) {
			this.rules = rules;
		}
		public List<IpObj> getIpList() {
			return ipList;
		}
		public void setIpList(List<IpObj> ipList) {
			this.ipList = ipList;
		}
		public String getVmName() {
			return vmName;
		}
		public void setVmName(String vmName) {
			this.vmName = vmName;
		}
		public String getCpu() {
			return cpu;
		}
		public void setCpu(String cpu) {
			this.cpu = cpu;
		}
		public String getMemory() {
			return memory;
		}
		public void setMemory(String memory) {
			this.memory = memory;
		}
		public String getDataStore() {
			return DataStore;
		}
		public void setDataStore(String dataStore) {
			DataStore = dataStore;
		}
		public String getCloudServiceId() {
			return cloudServiceId;
		}
		public void setCloudServiceId(String cloudServiceId) {
			this.cloudServiceId = cloudServiceId;
		}
		public String getAddDuId() {
			return addDuId;
		}
		public void setAddDuId(String addDuId) {
			this.addDuId = addDuId;
		}
		public String getImportResult() {
			return importResult;
		}
		public void setImportResult(String importResult) {
			this.importResult = importResult;
		}
		public String getCloudServiceName() {
			return cloudServiceName;
		}
		public void setCloudServiceName(String cloudServiceName) {
			this.cloudServiceName = cloudServiceName;
		}
		public String getAddDuName() {
			return addDuName;
		}
		public void setAddDuName(String addDuName) {
			this.addDuName = addDuName;
		}
		public int getIsExist() {
			return isExist;
		}
		public void setIsExist(int isExist) {
			this.isExist = isExist;
		}
		public List<CloudServiceVoByRmHost> getCloudServiceList() {
			return cloudServiceList;
		}
		public void setCloudServiceList(List<CloudServiceVoByRmHost> cloudServiceList) {
			this.cloudServiceList = cloudServiceList;
		}
		public String getVmId() {
			return vmId;
		}
		public void setVmId(String vmId) {
			this.vmId = vmId;
		}
		
}
