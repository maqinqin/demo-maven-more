package com.git.cloud.resmgt.common.dao.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.resmgt.common.dao.IRmVmwareLicenseDAO;

public class RmVmwareLicenseDAO extends CommonDAOImpl implements
		IRmVmwareLicenseDAO {

	@Override
	public String findVmwareLincseByHostCPU(int cupNum) {
		return (String)this.getSqlMapClientTemplate().queryForObject("findVmwareLincseByHostCPU",cupNum);
		
	}

}
