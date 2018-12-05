package com.git.cloud.resmgt.network.action;

import java.util.Map;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.resmgt.network.service.INetworkAbstaractService;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class NetworkAbstractAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private INetworkAbstaractService networkAbstaractService;

	
	public INetworkAbstaractService getNetworkAbstaractService() {
		return networkAbstaractService;
	}


	public void setNetworkAbstaractService(
			INetworkAbstaractService networkAbstaractService) {
		this.networkAbstaractService = networkAbstaractService;
	}


	public void findNetworkAbstract() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String dataCenterId = request.getParameter("dataCenterId");
		String bClassId = request.getParameter("bClassId");
		String cClassId = request.getParameter("cClassId");
		Map<String, String> map = networkAbstaractService.findNetworkAbstract(dataCenterId, bClassId, cClassId);
		jsonOut(map);
	}
}
