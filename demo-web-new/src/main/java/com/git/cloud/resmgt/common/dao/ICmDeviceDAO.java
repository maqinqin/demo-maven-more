package com.git.cloud.resmgt.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.policy.model.vo.AllocIpParamVo;
import com.git.cloud.resmgt.common.model.bo.CmClusterHostShowBo;
import com.git.cloud.resmgt.common.model.bo.CmIpShowBo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastoreRefPo;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDeviceVolumesRefPo;
import com.git.cloud.resmgt.common.model.vo.CmDatastoreVo;
import com.git.cloud.resmgt.common.model.vo.CmDeviceVo;
import com.git.cloud.resmgt.common.model.vo.CmHostRefVo;
import com.git.cloud.resmgt.common.model.vo.CmHostVo;
import com.git.cloud.resmgt.common.model.vo.CmStorageVo;
import com.git.cloud.resmgt.common.model.vo.DeviceInfoVo;
import com.git.cloud.resmgt.common.model.vo.RmResPoolVo;
import com.git.cloud.resmgt.compute.model.po.CmDistributePortGroupPo;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
import com.git.cloud.resmgt.compute.model.vo.RmCdpVo;
import com.git.cloud.resmgt.compute.model.vo.ServiceInstanceNode;
import com.git.cloud.resmgt.network.model.po.RmNwIpAddressPo;
import com.git.cloud.resmgt.openstack.model.vo.VolumeDetailVo;
import com.git.cloud.rest.model.CmHostAndCmVm;
import com.git.cloud.shiro.model.CertificatePo;

public interface ICmDeviceDAO extends ICommonDAO {

	/**
	 * 批量插入设备信息
	 * 
	 * @Title: insertCmDevice
	 * @Description: TODO
	 * @field: @param deviceList
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void insertCmDevice(List<CmDevicePo> deviceList) throws RollbackableBizException; 

	/**
	 * @Title: updateDeviceOfBatch
	 * @Description: 将物理机关联到指定集群。
	 * @param relevanceInfo
	 * @return
	 * @throws RollbackableBizException
	 * @throws SQLException
	 *             int 返回类型
	 * @throws
	 */
	public int updateDeviceOfBatch1(List<HashMap<String, String>> relevanceInfoMapList) throws RollbackableBizException;

	public int updateDeviceOfBatch2(List<HashMap<String, String>> relevanceInfoMapList) throws RollbackableBizException;

	public int updateDeviceOfBatch3(List<HashMap<String, String>> relevanceInfoMapList) throws RollbackableBizException;

	public int updateDeviceOfBatch4(List<HashMap<String, String>> relevanceInfoMapList) throws RollbackableBizException;

	/**
	 * @Title: getAllHostCanRelevanceInfo
	 * @Description: 获取可关联的所有物理机信息。
	 * @return
	 * @throws RollbackableBizException
	 *             List<T> 返回类型
	 * @throws
	 */
	public <T extends BaseBO> List<T> getAllHostCanRelevanceInfo(String method) throws RollbackableBizException;

	/**
	 * @Title: getCmClusterHostInfo
	 * @Description: 根据集群id，查询该集群下，关联的所有主机的信息。
	 * @param method
	 *            操作方法
	 * @param bizId
	 *            集群id
	 * @return
	 * @throws RollbackableBizException
	 *             List<T> 返回类型
	 * @throws
	 */
	public <T extends BaseBO> List<T> getCmClusterHostInfo(String method, String bizId) throws RollbackableBizException;

	/**
	 * @Title: selectAllocIpParam
	 * @Description: 获取网络地址区间信息。
	 * @param cluster_id
	 * @return
	 * @throws RollbackableBizException
	 *             AllocIpParamVo 返回类型
	 * @throws
	 */
	public AllocIpParamVo selectAllocIpParam(String cluster_id) throws RollbackableBizException;

	/**
	 * @Title: getCmClusterHostNetInfo
	 * @Description: 根据集群id，查询该集群下，关联的所有主机的对应的网络信息。
	 * @param method
	 *            操作方法
	 * @param bizId
	 *            集群id
	 * @return
	 * @throws RollbackableBizException
	 *             List<T> 返回类型
	 * @throws
	 */
	public <T extends BaseBO> List<T> getCmClusterHostNetInfo(String method, String bizId) throws RollbackableBizException;

	/**
	 * @Title: getCmDeviceHostInfo
	 * @Description: 通过物理机名和序列号，查询主机信息。
	 * @param method
	 *            操作方法
	 * @param map
	 *            物理机名， 序列号
	 * @return
	 * @throws RollbackableBizException
	 *             List<T> 返回类型
	 * @throws
	 */
	public <T extends BaseBO> List<T> getCmDeviceHostInfo(String method, Map map) throws RollbackableBizException;

	/**
	 * @Title: getCmDeviceHostNetInfo
	 * @Description: 通过物理机名和序列号，查询主机对应的网络信息。
	 * @param method
	 *            操作方法
	 * @param map
	 *            物理机名， 序列号
	 * @return
	 * @throws RollbackableBizException
	 *             List<T> 返回类型
	 * @throws
	 */
	public <T extends BaseBO> List<T> getCmDeviceHostNetInfo(String method, Map map) throws RollbackableBizException;

	/**
	 * @Title: getCmDeviceHostInfo
	 * @Description: 根据主机id，查询主机信息
	 * @param method
	 *            操作方法
	 * @param bizId
	 *            主机id
	 * @return
	 * @throws RollbackableBizException
	 *             T 返回类型
	 * @throws
	 */
	public <T extends BaseBO> T getCmDeviceHostInfo(String method, String bizId) throws RollbackableBizException;

	/**
	 * @Title: getCmDeviceVMInfo
	 * @Description: 根据虚机id，查询虚机信息
	 * @param method
	 *            操作方法
	 * @param bizId
	 *            虚机id
	 * @return
	 * @throws RollbackableBizException
	 *             List<T> 返回类型
	 * @throws
	 */
	public <T extends BaseBO> List<T> getCmDeviceVMInfo(String method, String bizId) throws RollbackableBizException;

	/**
	 * @Title: getHostOrderNum
	 * @Description: 获取主机表中，排序号最大的号码。
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int getHostOrderNum() throws RollbackableBizException;

	public int getCountCmAllHost(String method, String bizId) throws RollbackableBizException;

	public <T extends CmDevicePo> List<T> findListByFieldsAndOrder(String method, HashMap<String, String> params);

	public List<CmDeviceVo> findDeviceListByDuId(String duId) throws RollbackableBizException;

	public int getCountCmAllVM(String method, HashMap<String, String> params) throws RollbackableBizException;

	public int getCountByDeviceName(String method, String fullName);
	
	public int getCountByDeviceNameNew(String deviceName);

	public CmDevicePo findCmDeviceById(String id) throws RollbackableBizException;

	public int findCmVmCount(String hostID);

	/**
	 * @param devName
	 * @return
	 * @throws RollbackableBizException
	 * @Description通过名字对资源池模糊查询
	 */
	public List<RmResPoolVo> getRmResPoolByName(String devName) throws RollbackableBizException;

	/**
	 * @param devName
	 * @return
	 * @throws RollbackableBizException
	 * @Description通过名字对集群模糊查询
	 */
	public List<RmClusterPo> getRmResClusterByName(String devName) throws RollbackableBizException;

	/**
	 * @param devName
	 * @return
	 * @throws RollbackableBizException
	 * @Description通过名字对物理机模糊查询
	 */
	public List<CmDeviceVo> getCmDeviceHostByName(String devName) throws RollbackableBizException;

	/**
	 * @param devName
	 * @return
	 * @throws RollbackableBizException
	 * @Description通过名字对虚拟机模糊查询
	 */
	public List<CmDeviceVo> getCmDeviceVmByName(String devName) throws RollbackableBizException;

	/**
	 * 更新设备信息
	 * 
	 * @Title: updateCmDevice
	 * @Description: TODO
	 * @field: @param vmDevice
	 * @return void
	 * @throws
	 */
	public void updateCmDeviceState(CmDevicePo vmDevice) throws RollbackableBizException;

	public List<CmIpShowBo> selectCmIpInfo(String device_id) throws RollbackableBizException;
	public void updateCmDeviceInvcState(HashMap<String,String> map) throws RollbackableBizException;
	
	public List<CmHostRefVo> findHostRefInfoById(List<String> deviceId);
	public void updateHostClusterInfo(List<String> deviceIds_sql);
	public void updateHostResPoolInfo(List<String> deviceIds_sql);
	public void updateHostVCStatusInfo(List<String> deviceIds_sql);
	public List<CmHostRefVo> findHostManageInfo(String deviceId);

	public CertificatePo findCertificatePath()throws RollbackableBizException;

	public List<CmDeviceVo> getCmDevicePoNumber()throws RollbackableBizException;
	
	public List<CmClusterHostShowBo> selectCmClusterHostInfos(String id)throws RollbackableBizException;
	/**
	 * 查询最新的工作流模板ID 
	 * @return
	 * @throws RollbackableBizException
	 */
	List<String> selectBpmModelId(String modelName)throws RollbackableBizException;	
	/**
	 * 查询最新的工作流模板名称
	 * @return
	 * @throws RollbackableBizException
	 */    
	public List<String> selectModelName()throws RollbackableBizException;
	
	public CmDevicePo selectCmDevicePoById(Map<String, String> map)throws RollbackableBizException;
	
	public CmDevicePo isCmHost(Map<String, String> map)throws RollbackableBizException;
	
	public CmDevicePo isCmVm(Map<String, String> map)throws RollbackableBizException;
	
	public void updateCmdeviceRunningState(CmDevicePo cmDevicePo)throws RollbackableBizException;
	
	public void saveDatastoreInfo(CmHostDatastoreRefPo poList) throws RollbackableBizException;
	 
	public void deleteDatastoreInfo(Map<String,Object> map) throws RollbackableBizException;
	 
	public void saveDefaultDatastore(CmDevicePo cmDevicePo) throws RollbackableBizException;
	
	public CmDevicePo getDefaultDatastore(Map<String, String> map)throws RollbackableBizException;

	public CmVmPo findPowerInfoByVmId(String vmId)throws RollbackableBizException;

	public String findVmIdByName(String deviceName)throws RollbackableBizException;

	public String findHostIpById(String id) throws RollbackableBizException;
	/**
	 * 主要是物理机进入维护模式/退出维护模式时使用
	 * @throws RollbackableBizException
	 */
	public String getPmRunningState(String hostId) throws RollbackableBizException;

	public List<CmDevicePo> findDeviceDefaultDatastore(String datastoreId)throws RollbackableBizException;
	
	public List<CmHostDatastoreRefPo> findCmHostDatastoreRefPoByStorageId(String cmStorageId)throws RollbackableBizException;
	
	public CmDevicePo findNewDevcieByHostId()throws RollbackableBizException;
	/**
	 * 根据传递的ip信息，找到分配的物理机信息
	 * @param ipInfo
	 * @return
	 * @throws RollbackableBizException
	 */
	public CmHostPo findDistribHost(String ipInfo)throws RollbackableBizException;
	
	public CmDistributePortGroupPo findDisPortGroupId(Map<String, String> map);
	
	/**
	 *  获取物理机配置信息cpu、mem、disk
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 */
	public Pagination<CmHostPo> getHostConfigure(PaginationParam paginationParam)throws RollbackableBizException;
	/**
	 * 保存机器挂载的磁盘信息
	 * @param rmDeviceVolumesRefPo
	 * @throws RollbackableBizException
	 */
	public void saveRmDeviceVolumesRefPo(List<RmDeviceVolumesRefPo> rmDeviceVolumesRefPoList)throws RollbackableBizException;
	/**
	 * 保存单个，机器挂载的磁盘信息
	 * @param rmDeviceVolumesRefPoList
	 * @throws RollbackableBizException
	 */
	public void saveRmDeviceVolumesRefPo(RmDeviceVolumesRefPo rmDeviceVolumesRefPo)throws RollbackableBizException;
	
	public Pagination<RmDeviceVolumesRefPo> getRmDeviceVolumesRefPoList(PaginationParam paginationParam)throws RollbackableBizException;

	public void updateMountStatus(RmDeviceVolumesRefPo rmDeviceVolumesRefPo)throws RollbackableBizException;
	
	public void updateDeviceActiveStatusToN(String deviceId) throws RollbackableBizException;
	public void deleteRmDeviceVolumesRef(RmDeviceVolumesRefPo ref) throws RollbackableBizException;
	/**
	 * 虚机id，挂载卷状态为未挂载，卷大小等于挂载的数据卷大小，并且卷id为空，查找数据
	 * @param map
	 * @return
	 * @throws RollbackableBizException
	 */
	public RmDeviceVolumesRefPo getRmDeviceVolumesRefByMap( HashMap<String,String> map)throws RollbackableBizException;
	/**
	 * 更新磁盘对应的卷Id
	 * @param ref
	 * @throws RollbackableBizException
	 */
	public void updateRmDvRefVolumeId(RmDeviceVolumesRefPo ref)throws RollbackableBizException;

	/**
	 * 查询project下的虚机id
	 * @param projectId
	 * @return
	 * @throws RollbackableBizException
	 */
	List<CmDevicePo> getVmIdByProjectId(String projectId) throws RollbackableBizException;

	/**
	 * 保存openstack数据卷
	 * @param volumeVo
	 */
	void saveOpenstackVolume(VolumeDetailVo volumeVo);

	/**
	 * 删除openstack数据卷
	 * @param volumeId
	 */
	void deleteOpenstackVolume(String volumeId);

	/**
	 * 获取openstack数据卷列表
	 * @param projectId
	 * @return
	 */
	List<VolumeDetailVo> getOpenstackVolume(String projectId);

	/**
	 * 修改虚机名称
	 * @param deviceId
	 * @param deviceName
	 * @throws RollbackableBizException
	 */
	void updateVmName(String deviceId, String deviceName) throws RollbackableBizException;

	/**
	 * 更新虚机所在主机
	 * @param deviceId
	 * @param host
	 * @throws RollbackableBizException
	 */
	void updateVmHost(String deviceId, String host) throws RollbackableBizException;

	/**
	 * 更新虚机电源状态
	 * @param deviceId
	 * @param powerStatus
	 * @throws RollbackableBizException
	 */
	void updateVmPowerStatus(String deviceId, String powerStatus) throws RollbackableBizException;

	List<String> findVolumeTypeList(String availableZoneId);
	/**
	 * 更新虚拟机所在的projectId
	 * @param vmId
	 * @param projectId
	 * @throws RollbackableBizException
	 */
	public void updateVmProjectId(String vmId, String projectId) throws RollbackableBizException;
	void saveServiceInstanceNode(ServiceInstanceNode node);

	ServiceInstanceNode getServiceInstanceNodeById(String deviceId);

	List<ServiceInstanceNode> getServiceInstanceNodeByClusterId(Map<String,String> map);

	void deleteInstanceNodeById(String deviceId);

	void deleteInstanceNodeRefById(String deviceId);

	void deleteServiceInstanceById(String serviceInstanceId);

	void deleteServiceInstanceRefById(String serviceInstanceId);
}
