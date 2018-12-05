package com.git.cloud.resmgt.compute.dao.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.compute.dao.CmVmGroupDao;
import com.git.cloud.resmgt.compute.model.po.CmVmGroupPo;

public class CmVmGroupDaoImpl extends CommonDAOImpl implements CmVmGroupDao {

	@Override
	public CmVmGroupPo selectByPrimaryKey(String id) throws RollbackableBizException {
	
		return (CmVmGroupPo) super.findObjectByID("findCmVmGroupById", id);
	}

}
