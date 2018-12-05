package com.git.cloud.resmgt.network.action;

import java.util.List;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.service.IVirtualNetworkService;

public class VirtualNetworkAction extends BaseAction{
	
	private IVirtualNetworkService virtualNetworkService;
	
	private String deviceId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public void selectIpAddressByDeviceId() throws Exception{
		List<OpenstackIpAddressPo> list = virtualNetworkService.selectIpAddressByDeviceId(deviceId);
		jsonOut(list); 		
	}
	public IVirtualNetworkService getVirtualNetworkService() {
		return virtualNetworkService;
	}
	public void setVirtualNetworkService(IVirtualNetworkService virtualNetworkService) {
		this.virtualNetworkService = virtualNetworkService;
	}

}
