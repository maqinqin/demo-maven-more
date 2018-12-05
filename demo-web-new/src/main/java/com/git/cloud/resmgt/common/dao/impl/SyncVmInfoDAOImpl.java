package com.git.cloud.resmgt.common.dao.impl;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.dao.ISyncVmInfoDAO;
import com.git.cloud.resmgt.common.model.po.CmVmSynInfoPo;

public class SyncVmInfoDAOImpl extends CommonDAOImpl implements ISyncVmInfoDAO {

	@Override
	public void saveSyncVmRunningState(Map<String, String> pMap) throws RollbackableBizException {
		this.getSqlMapClientTemplate().update("updateDeviceRunningState", pMap);
	}

	@Override
	public List<CmVmSynInfoPo> findSyncVmInfoByManageIp(String manageIp)
			throws RollbackableBizException {
		return this.findListByParam("findVmSynInfoByManageIp", manageIp);
	}

	@Override
	public void saveSyncVmDatastore(Map<String, String> pMap)
			throws RollbackableBizException {
		this.getSqlMapClientTemplate().update("updateVmDatastore", pMap);
	}

	
}
