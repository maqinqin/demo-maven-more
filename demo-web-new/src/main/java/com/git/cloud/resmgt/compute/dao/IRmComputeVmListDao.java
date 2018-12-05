package com.git.cloud.resmgt.compute.dao;

import java.util.HashMap;
import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.compute.model.vo.PhysicsMachineVo;
import com.git.cloud.resmgt.compute.model.vo.RmComputeVmListVo;

public interface IRmComputeVmListDao extends ICommonDAO {

	/**
	 * 更新源物理机CPU MEM
	 * @param pagination
	 * @throws Exception
	 */
	public void updateHostStorage(PhysicsMachineVo physicsMachineVo) throws Exception;
	
	/**
	 * 更新虚拟机ID
	 * @param pagination
	 * @throws Exception
	 */
	public void updateVCMVMId(PhysicsMachineVo physicsMachineVo) throws Exception;
	
}