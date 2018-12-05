package com.git.cloud.resmgt.common.service.impl;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.dao.ICmHostDatastoreRefDAO;
import com.git.cloud.resmgt.common.model.po.CmHostDatastoreRefPo;
import com.git.cloud.resmgt.common.service.ICmHostDatastoreRefService;

public class CmHostDatastoreRefServiceImpl implements ICmHostDatastoreRefService{
	
	private ICmHostDatastoreRefDAO cmHostDatastoreRefDAO;
	public ICmHostDatastoreRefDAO getCmHostDatastoreRefDAO() {
		return cmHostDatastoreRefDAO;
	}
	public void setCmHostDatastoreRefDAO(
			ICmHostDatastoreRefDAO cmHostDatastoreRefDAO) {
		this.cmHostDatastoreRefDAO = cmHostDatastoreRefDAO;
	}

	@Override
	public List<CmHostDatastoreRefPo> getAllDatastoreRef(String datastoreId) throws Exception {
		return cmHostDatastoreRefDAO.getAllDatastoreRef(datastoreId);
	}
	
}
