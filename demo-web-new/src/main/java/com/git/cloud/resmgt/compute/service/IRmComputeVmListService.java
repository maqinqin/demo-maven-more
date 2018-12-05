package com.git.cloud.resmgt.compute.service;

import com.git.cloud.common.service.IService;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.compute.model.vo.PhysicsMachineVo;
import com.git.cloud.resmgt.compute.model.vo.RmComputeVmListVo;

public interface IRmComputeVmListService extends IService {

	/**
	 * 更新源物理机CPU MEM
	 * @param pagination
	 */
	public void updateHostStorage(PhysicsMachineVo physicsMachineVo) throws Exception;
	
	/**
	 * 更新源虚拟机ID
	 * @param pagination
	 */
	public void updateVCMVMId(PhysicsMachineVo physicsMachineVo) throws Exception;
	
}