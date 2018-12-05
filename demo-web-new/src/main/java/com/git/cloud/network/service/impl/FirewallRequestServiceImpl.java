package com.git.cloud.network.service.impl;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.network.dao.FirewallRequestDao;
import com.git.cloud.network.service.FirewallRequestService;

public class FirewallRequestServiceImpl implements FirewallRequestService {

	private FirewallRequestDao firewallRequestDaoImpl;
	
	public void setFirewallRequestDaoImpl(FirewallRequestDao firewallRequestDaoImpl) {
		this.firewallRequestDaoImpl = firewallRequestDaoImpl;
	}

	@Override
	public void updateFirewallRequestStatus(String firewallRequestId, String status , String isActive) throws RollbackableBizException {
		firewallRequestDaoImpl.updateFirewallRequestStatus(firewallRequestId, status, isActive);
	}
}
