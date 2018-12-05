package com.git.cloud.resmgt.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDeviceVolumesRefPo;
import com.git.cloud.resmgt.common.model.vo.CmSnapshotVo;
import com.git.cloud.resmgt.common.model.vo.CmVmVo;
import com.git.cloud.resmgt.common.model.vo.SyncVmInfoVo;
import com.git.cloud.resmgt.compute.model.vo.VmMonitorVo;
import com.git.cloud.resmgt.network.model.po.RmNwIpAddressPo;
import com.git.cloud.resmgt.openstack.model.vo.FloatingIpVo;

public interface ICmVmDAO extends ICommonDAO {
	public <T extends CmVmPo> List<T> findListByFieldsAndOrder(String method, HashMap<String, String> params);

	/**
	 * 批量插入虚拟机信息
	 * 
	 * @Title: insertCmVm
	 * @Description: TODO
	 * @field: @param cmVmList
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void insertCmVm(List<CmVmPo> cmVmList) throws RollbackableBizException;
	
	
	/**
	 * 批量插入原虚拟机信息
	 * 
	 * @Title: insertOldCmVm
	 * @Description: TODO
	 * @field: @param cmVmList
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void insertOldCmVm(List<CmVmPo> cmVmList) throws RollbackableBizException;

	/**
	 * 更新虚机配置
	 * 
	 * @param cmVm
	 * @throws RollbackableBizException
	 */
	public void updateCmVmModel(CmVmPo cmVm) throws RollbackableBizException;

	/**
	 * 更新虚拟机上线时间
	 * 
	 * @param cmVm
	 * @throws RollbackableBizException
	 */
	public void updateCmVmOnlineTime(CmVmPo cmVm) throws RollbackableBizException;

	/**
	 * 清空虚拟机所属服务器角色
	 * 
	 * @param cmVm
	 * @throws RollbackableBizException
	 */
	public void updateCmVmDuId(CmVmPo cmVm) throws RollbackableBizException;

	/**
	 * 根据Id查询虚拟机配置
	 * 
	 * @Title: findCmVmById
	 * @Description: TODO
	 * @field: @param id
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return CmVmPo
	 * @throws
	 */
	public CmVmPo findCmVmById(String id) throws RollbackableBizException;

	/**
	 * 根据服务器角色获取服务器角色下的虚机
	 * 
	 * @Title: findCmVmByDuId
	 * @Description: TODO
	 * @field: @param duId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<CmVmPo>
	 * @throws
	 */
	public List<CmVmPo> findCmVmByDuId(String duId) throws RollbackableBizException;

	/**
	 * 根据虚机Id获取平台类型和虚拟化类型
	 * 
	 * @param vmId
	 * @return
	 * @throws RollbackableBizException
	 */
	public CmVmVo findPlatformTypeAndVmTypeByVmId(String vmId) throws RollbackableBizException;

	/**
	 * 修改虚拟机的服务器角色
	 * 
	 * @param cmvm
	 * @throws RollbackableBizException
	 */
	public void updateVmDuId(CmVmVo cmvm) throws RollbackableBizException;

	/**
	 * 更新虚拟机电源状态
	 * 
	 * @param vmInfo
	 * @return
	 * @throws RollbackableBizException
	 */
	public int updateVmStatus(SyncVmInfoVo vmInfo) throws RollbackableBizException;

	/**
	 * 更新虚拟机所在物理机ID
	 * 
	 * @param vmInfo
	 * @return
	 * @throws RollbackableBizException
	 */
	public int updateVmHostId(SyncVmInfoVo vmInfo) throws RollbackableBizException;

	/**
	 * 查询虚拟机ID
	 * 
	 * @param vmName
	 * @return
	 */
	public String findIDByVmName(String vmName);

	/**
	 * 查询虚拟机当前的运行状态
	 * 
	 * @param vmName
	 * @return
	 */
	public String findVmPowerStateByVmName(String vmName);
	/**
	 * 
	 * 根据虚拟机id，获取快照列表
	 * @param vmId
	 * @return
	 * @throws Exception
	 */
	public List<CmSnapshotVo> getCmSnapshotVoList(String vmId) throws Exception;
	
	/**
	 * 根据应用系统ID获取指定应用下所有虚机的CPU总数和内存总数
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public CmVmPo getCpuMemSumByAppIds(String appIds) throws Exception;

	/**
	 * 更新虚拟机disk
	 * 
	 * @param vmInfo
	 * @return
	 * @throws RollbackableBizException
	 */
	public int updateVmDisk(CmVmPo vm) throws RollbackableBizException;
	
	
	public List<CmVmVo> findVmInfoListByParams(Map<String,String> params);
	
	/**
	 * 获取虚机监控信息
	 * @param params
	 * @return
	 */
	List<VmMonitorVo> getVmMonitor(Map<String, String> params);
	
	public List<RmDeviceVolumesRefPo> getDeviceVolumeRefList(String deviceId) throws Exception;
	
	public List<FloatingIpVo> getFloatIpList(String deviceId) throws Exception;
	
	public void updateVmOfflineTime(CmVmPo cmvm) throws RollbackableBizException;

	/**
	 * 根据租户ID查询虚机个数
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 */
	Integer selectCountByTenantId(String id) throws RollbackableBizException;

	/**
	 * 根据租户ID查询CPU内存使用总量
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 */
	CmVmPo selectCpuMemSumByTenantId(String id) throws RollbackableBizException;
	/**
	 * 通过主键id,修改设备的iaasUuid
	 * @param cmvm
	 * @throws RollbackableBizException
	 */
	void updateCmVm(CmVmPo cmvm) throws RollbackableBizException;
}
