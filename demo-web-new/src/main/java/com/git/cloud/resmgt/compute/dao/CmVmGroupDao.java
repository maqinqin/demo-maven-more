package com.git.cloud.resmgt.compute.dao;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.compute.model.po.CmVmGroupPo;

public interface CmVmGroupDao {
	
	CmVmGroupPo selectByPrimaryKey(String id) throws RollbackableBizException;
}
