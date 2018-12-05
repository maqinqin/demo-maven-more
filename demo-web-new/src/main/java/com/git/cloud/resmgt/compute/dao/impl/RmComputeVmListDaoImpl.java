package com.git.cloud.resmgt.compute.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.compute.dao.IRmComputeVmListDao;
import com.git.cloud.resmgt.compute.model.vo.PhysicsMachineVo;
import com.git.cloud.resmgt.compute.model.vo.RmComputeVmListVo;
@Repository
public class RmComputeVmListDaoImpl extends CommonDAOImpl implements
		IRmComputeVmListDao {

	@Override
	public void updateHostStorage(PhysicsMachineVo physicsMachineVo) throws Exception {
		update("updateHostStorage",physicsMachineVo);
	}
	
	@Override
	public void updateVCMVMId(PhysicsMachineVo physicsMachineVo) throws Exception {
		update("updateVCMVMId",physicsMachineVo);
	}
	
}