package com.git.cloud.network.dao;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;

public interface FirewallRequestDao extends ICommonDAO {
	
	/**
	 * 更新云防火墙申请状态
	 * @param firewallRequestId
	 * @param status
	 * @throws RollbackableBizException
	 */
	void updateFirewallRequestStatus(String firewallRequestId, String status, String isActive) throws RollbackableBizException;
}
