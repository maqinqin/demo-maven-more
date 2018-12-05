package com.git.cloud.resmgt.common.service;

import java.util.List;

import com.git.cloud.common.service.IService;
import com.git.cloud.resmgt.common.model.vo.DeviceDiagramVo;
import com.git.cloud.resmgt.common.model.vo.DeviceVmTypeNumDiagramVo;
import com.git.cloud.resmgt.common.model.vo.ResPoolHostVmInfoVo;

public interface IResComputeDiagramService  extends IService {

	/**
	 * @Title: getDeviceDialgramInfo
	 * @Description: 获取最新五条入库设备信息
	 * @field: @return
	 * @return List<DeviceDiagramVo>
	 * @throws
	 */
	public List<DeviceDiagramVo> getDeviceDialgramInfo();
	
	
	/**
	 * @Title: getVmNumberInfo
	 * @Description: 获取相关虚拟机类型对应虚拟机数量信息
	 * @field: @return
	 * @return List<DeviceVmTypeNumDiagramVo>
	 * @throws
	 */
	public List<DeviceVmTypeNumDiagramVo> getVmNumberInfo();


	/**
	 * @Title: getResPoolHostVmInfo
	 * @Description: 获取资源池包含的主机和虚拟机cpu、memory信息
	 * @field: @return
	 * @return List<ResPoolHostVmInfoVo>
	 * @throws
	 */
	public List<ResPoolHostVmInfoVo> getResPoolHostVmInfo();
}
