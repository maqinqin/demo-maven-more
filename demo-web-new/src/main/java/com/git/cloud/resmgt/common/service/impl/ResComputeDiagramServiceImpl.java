package com.git.cloud.resmgt.common.service.impl;

import java.util.List;

import com.git.cloud.resmgt.common.dao.IResComputeDiagramDAO;
import com.git.cloud.resmgt.common.dao.impl.ResComputeDiagramDAO;
import com.git.cloud.resmgt.common.model.vo.DeviceDiagramVo;
import com.git.cloud.resmgt.common.model.vo.DeviceVmTypeNumDiagramVo;
import com.git.cloud.resmgt.common.model.vo.ResPoolHostVmInfoVo;
import com.git.cloud.resmgt.common.service.IResComputeDiagramService;

/**
 * @ClassName:ResComputeDiagramServiceImpl
 * @Description:计算资源图标信息
 * @author chengbin
 * @date 2014-12-17 下午1:42:29
 *
 *
 */
public class ResComputeDiagramServiceImpl implements IResComputeDiagramService{

	private IResComputeDiagramDAO resComputeDiagramDAO;

	public IResComputeDiagramDAO getResComputeDiagramDAO() {
		return resComputeDiagramDAO;
	}

	public void setResComputeDiagramDAO(IResComputeDiagramDAO resComputeDiagramDAO) {
		this.resComputeDiagramDAO = resComputeDiagramDAO;
	}

	@Override
	public List<DeviceDiagramVo> getDeviceDialgramInfo() {
		return resComputeDiagramDAO.findDeviceDiagramInfo();
	}
	
	public List<DeviceVmTypeNumDiagramVo> getVmNumberInfo(){
		return resComputeDiagramDAO.findVmNumberInfo();
	}
	@Override
	public List<ResPoolHostVmInfoVo> getResPoolHostVmInfo() {
		
		return resComputeDiagramDAO.findResPoolHostVmInfo();
	}
	
	
	
	
}
