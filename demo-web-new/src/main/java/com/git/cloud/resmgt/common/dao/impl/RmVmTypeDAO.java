package com.git.cloud.resmgt.common.dao.impl;

import java.util.HashMap;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.dao.IRmVmTypeDAO;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;

public class RmVmTypeDAO extends CommonDAOImpl implements IRmVmTypeDAO{

	@Override
	public RmVirtualTypePo findRmVirtualTypeInfo(String method,
			HashMap<String, String> params) throws RollbackableBizException {
		return (RmVirtualTypePo)this.getSqlMapClientTemplate().queryForObject(method, params);
	}

}
