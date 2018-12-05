package com.git.cloud.network.dao.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.handler.automation.fw.model.FwRequestStatusEnum;
import com.git.cloud.handler.automation.fw.model.RmNwFirewallRequestPo;
import com.git.cloud.network.dao.FirewallRequestDao;

public class FirewallRequestDaoImpl extends CommonDAOImpl implements FirewallRequestDao {
	
	@Override
	public void updateFirewallRequestStatus(String firewallRequestId, String status, String isActive) throws RollbackableBizException {
		RmNwFirewallRequestPo firewallRequest = new RmNwFirewallRequestPo();
		firewallRequest.setId(firewallRequestId);
		firewallRequest.setStatus(status);
	    firewallRequest.setIsActive(isActive);
		this.update("updateFirewallRequestStatus", firewallRequest);
	}
}
