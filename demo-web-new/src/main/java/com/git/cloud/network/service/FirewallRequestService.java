package com.git.cloud.network.service;

import com.git.cloud.common.exception.RollbackableBizException;

public interface FirewallRequestService {

	/**
	 * 更新云防火墙申请状态
	 * @param firewallRequestId
	 * @param status
	 * @throws RollbackableBizException
	 */
	void updateFirewallRequestStatus(String firewallRequestId, String status, String isActive) throws RollbackableBizException;
}
