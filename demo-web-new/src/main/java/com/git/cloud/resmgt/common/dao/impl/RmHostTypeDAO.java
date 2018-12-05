package com.git.cloud.resmgt.common.dao.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.dao.IRmHostTypeDAO;
import com.git.cloud.resmgt.common.model.po.RmHostTypePo;

public class RmHostTypeDAO extends CommonDAOImpl implements IRmHostTypeDAO{

	@Override
	public RmHostTypePo getRmHostTypeById(String hostTypeId) throws RollbackableBizException {
		return super.findObjectByID("getRmHostTypeById", hostTypeId);
	}

}
