package com.git.cloud.resmgt.common.service;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;

public interface ICheckResource  extends IService {
	/**
	 * 根据虚机ID与vcenter验证是否是正确的机器
	 * @param vmId
	 * @return
	 */
	public boolean checkVmInVc(String vmId)  throws RollbackableBizException;
}
