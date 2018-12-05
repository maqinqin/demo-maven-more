package com.git.cloud.resmgt.common.service;

import com.git.cloud.common.exception.RollbackableBizException;

public interface IScanVcService {

	/**
	 * 读取vc信息，启动扫描vc线程和db读取线程
	 * @throws RollbackableBizException 
	 * @throws Exception
	 */
	public void saveOrUpdateOrDelSyncData() throws RollbackableBizException ;
}
