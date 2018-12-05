package com.git.cloud.resmgt.compute.action;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.compute.handler.VmControllerServiceImpl;

public class VmControllerAction extends BaseAction<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VmControllerServiceImpl vmControllers;
	private String vmName;
	private String hostId;
	private String vmId;
	private ICmDeviceService iCmDeviceService;
	private String name;//指标名称
	private String ip;//虚机ip


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}



	public void vmRunningState() throws Exception {
		String result = "";
		result = vmId + ":" + vmControllers.vmRunningState(vmId);
		stringOut(result);
	}

	public void setVmControllers(VmControllerServiceImpl vmControllers) {
		this.vmControllers = vmControllers;
	}

	public ICmDeviceService getiCmDeviceService() {
		return iCmDeviceService;
	}

	public void setiCmDeviceService(ICmDeviceService iCmDeviceService) {
		this.iCmDeviceService = iCmDeviceService;
	}
	
}
