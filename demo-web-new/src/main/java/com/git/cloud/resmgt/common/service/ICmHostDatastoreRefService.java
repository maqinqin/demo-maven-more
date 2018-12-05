package com.git.cloud.resmgt.common.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.resmgt.common.model.po.CmHostDatastoreRefPo;

public interface ICmHostDatastoreRefService extends IService {
	
	public List<CmHostDatastoreRefPo> getAllDatastoreRef(String datastoreId)throws Exception;
}
