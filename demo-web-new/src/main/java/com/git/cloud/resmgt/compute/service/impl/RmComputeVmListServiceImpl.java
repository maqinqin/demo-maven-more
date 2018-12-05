package com.git.cloud.resmgt.compute.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.policy.service.IRmVmParamService;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.compute.dao.IRmComputeVmListDao;
import com.git.cloud.resmgt.compute.model.vo.PhysicsMachineVo;
import com.git.cloud.resmgt.compute.service.IRmComputeVmListService;

@Service
public class RmComputeVmListServiceImpl implements IRmComputeVmListService {
	@Autowired
	private ICmDeviceDAO cmDeviceDAO ;
	private IRmComputeVmListDao iRmComputeVmListDao;

	public void setiRmComputeVmListDao(IRmComputeVmListDao iRmComputeVmListDao) {
		this.iRmComputeVmListDao = iRmComputeVmListDao;
	}
	@Autowired
	private IRmVmParamService rmVmParamService ;
	@Autowired
	private ICmDeviceService iCmDeviceService;
	
	@Override
	public void updateHostStorage(PhysicsMachineVo physicsMachineVo) throws Exception {
		 iRmComputeVmListDao.updateHostStorage(physicsMachineVo);
	}
	
	@Override
	public void updateVCMVMId(PhysicsMachineVo physicsMachineVo) throws Exception {
		iRmComputeVmListDao.updateVCMVMId(physicsMachineVo);
	}
	
	public void setCmDeviceDAO(ICmDeviceDAO cmDeviceDAO) {
		this.cmDeviceDAO = cmDeviceDAO;
	}
}