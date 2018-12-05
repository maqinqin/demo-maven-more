package com.git.cloud.resmgt.network.service;

import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;

public interface INetworkAbstaractService extends IService {
	
	public Map<String, String> findNetworkAbstract(String dataCenterId,String bClassId,String cClassId)throws RollbackableBizException;
}
