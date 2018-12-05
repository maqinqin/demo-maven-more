package com.git.cloud.resmgt.common.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.resmgt.common.model.vo.DeviceDiagramVo;
import com.git.cloud.resmgt.common.model.vo.DeviceVmTypeNumDiagramVo;
import com.git.cloud.resmgt.common.model.vo.ResPoolHostVmInfoVo;

public interface IResComputeDiagramDAO extends ICommonDAO {

	
	/**
	 * @Title: findDeviceDiagramInfo
	 * @Description: 查询最新的五条入库设备记录
	 * @field: @return
	 * @return List<DeviceDiagramVo>
	 * @throws
	 */
	public List<DeviceDiagramVo> findDeviceDiagramInfo();
	
	/**
	 * @Title: findVmNumberInfo
	 * @Description: 查询虚拟机类型对应的虚拟机数
	 * @field: @return
	 * @return List<DeviceVmTypeNumDiagramVo>
	 * @throws
	 */
	public List<DeviceVmTypeNumDiagramVo> findVmNumberInfo();

	/**
	 * @Title: findResPoolHostVmInfo
	 * @Description: 查询资源池包含的物理机和虚拟机对应的CPU,memory信息
	 * @field: @return
	 * @return List<ResPoolHostVmInfoVo>
	 * @throws
	 */
	public List<ResPoolHostVmInfoVo> findResPoolHostVmInfo();
}
