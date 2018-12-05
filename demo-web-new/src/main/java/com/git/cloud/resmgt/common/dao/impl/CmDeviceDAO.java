package com.git.cloud.resmgt.common.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.CollectionUtil;
import com.git.cloud.policy.model.vo.AllocIpParamVo;
import com.git.cloud.request.model.SrStatusCodeEnum;
import com.git.cloud.request.model.SrTypeMarkEnum;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.model.DeviceStatusEnum;
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
import com.google.common.collect.Maps;
import com.ibatis.sqlmap.client.SqlMapClient;
//@Service
@Repository
public class CmDeviceDAO extends CommonDAOImpl implements ICmDeviceDAO {
	private static Logger logger = LoggerFactory.getLogger(CmDeviceDAO.class);

	public void insertCmDevice(List<CmDevicePo> deviceList) throws RollbackableBizException {
		if (CollectionUtil.hasContent(deviceList)) {
			this.batchInsert("cmDeviceBatchInsert", deviceList);
		}
	}

	@Override
	public int updateDeviceOfBatch1(List<HashMap<String, String>> relevanceInfo) throws RollbackableBizException {
		logger.info("CmDeviceDAO---------------updateDeviceOfBatch1 start---------------------");

		// 注意使用同一个SqlMapClient会话
		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();

		// 开始批处理
		try {
			sqlMapClient.startBatch();

			for (HashMap<String, String> map : relevanceInfo) {
				sqlMapClient.update("updateCmHost", map);
			}
			// 执行批处理
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RollbackableBizException(" 将指定物理机关联到指定的集群 失败");
		}
		logger.info("CmDeviceDAO---------------updateDeviceOfBatch1 end---------------------");
		return 0;
	}

	@Override
	public int updateDeviceOfBatch2(List<HashMap<String, String>> relevanceInfo) throws RollbackableBizException {
		logger.info("CmDeviceDAO---------------updateDeviceOfBatch2 start---------------------");
		// 注意使用同一个SqlMapClient会话
		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();

		try {
			// 开始批处理
			sqlMapClient.startBatch();
			for (HashMap<String, String> map : relevanceInfo) {
				sqlMapClient.update("batchUpdateDevice", map);
			}
			// 执行批处理
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RollbackableBizException("更新设备名称和所属资源池失败");
		}
		logger.info("CmDeviceDAO---------------updateDeviceOfBatch2 end---------------------");
		return 0;
	}

	@Override
	public int updateDeviceOfBatch3(List<HashMap<String, String>> relevanceInfo) throws RollbackableBizException {
		logger.info("CmDeviceDAO---------------updateDeviceOfBatch3 start---------------------");

		// 注意使用同一个SqlMapClient会话
		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
		try {
			// 开始批处理
			sqlMapClient.startBatch();
			for (HashMap<String, String> map : relevanceInfo) {
				sqlMapClient.update("setPassword", map);
			}
			// 执行批处理
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RollbackableBizException("设置用户名和密码失败");
		}
		logger.info("CmDeviceDAO---------------updateDeviceOfBatch3 end---------------------");
		return 0;
	}

	@Override
	public int updateDeviceOfBatch4(List<HashMap<String, String>> relevanceInfo) throws RollbackableBizException {
		logger.info("CmDeviceDAO---------------updateDeviceOfBatch4 start---------------------");

		// 注意使用同一个SqlMapClient会话
		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
		try {
			// 开始批处理
			sqlMapClient.startBatch();
			for (HashMap<String, String> map : relevanceInfo) {
				sqlMapClient.update("insertHostDatastoreRef", map);
			}
			// 执行批处理
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RollbackableBizException("保存主机，和数据存储设备的对应关系失败");
		}
		logger.info("CmDeviceDAO---------------updateDeviceOfBatch4 end---------------------");
		return 0;
	}

	@Override
	public <T extends BaseBO> List<T> getAllHostCanRelevanceInfo(String method) throws RollbackableBizException {
		logger.info("CmDeviceDAO---------------getAllHostCanRelevanceInfo start---------------------");
		List<T> list = this.findAll(method);
		logger.info("CmDeviceDAO---------------getAllHostCanRelevanceInfo end---------------------");
		return list;
	}

	@Override
	public <T extends BaseBO> List<T> getCmClusterHostInfo(String method, String bizId) throws RollbackableBizException {
		logger.info("CmDeviceDAO---------------getCmClusterHostInfo start---------------------");
		List<T> list = this.findByID(method, bizId);
		logger.info("CmDeviceDAO---------------getCmClusterHostInfo end---------------------");
		return list;
	}

	@Override
	public <T extends BaseBO> List<T> getCmClusterHostNetInfo(String method, String bizId) throws RollbackableBizException {
		logger.info("CmDeviceDAO---------------getCmClusterHostNetInfo start---------------------");
		List<T> list = this.findByID(method, bizId);
		logger.info("CmDeviceDAO---------------getCmClusterHostNetInfo end---------------------");
		return list;
	}

	@Override
	public <T extends BaseBO> List<T> getCmDeviceHostInfo(String method, Map map) throws RollbackableBizException {

		logger.info("CmDeviceDAO---------------getCmDeviceHostInfo Map start---------------------");
		List<T> list = this.findListByParam(method, map);
		logger.info("CmDeviceDAO---------------getCmDeviceHostInfo Map end---------------------");
		return list;
	}

	@Override
	public <T extends BaseBO> List<T> getCmDeviceHostNetInfo(String method, Map map) throws RollbackableBizException {

		logger.info("CmDeviceDAO---------------getCmDeviceHostNetInfo Map start---------------------");
		List<T> list = this.findListByParam(method, map);
		logger.info("CmDeviceDAO---------------getCmDeviceHostNetInfo Map end---------------------");
		return list;
	}

	@Override
	public <T extends BaseBO> T getCmDeviceHostInfo(String method, String bizId) throws RollbackableBizException {
		logger.info("CmDeviceDAO---------------getCmDeviceHostInfo start---------------------");
		BaseBO hostInfo = (BaseBO) getSqlMapClientTemplate().queryForObject(method, bizId);
		logger.info("CmDeviceDAO---------------getCmDeviceHostInfo end---------------------");
		return (T) hostInfo;
	}

	@Override
	public <T extends BaseBO> List<T> getCmDeviceVMInfo(String method, String bizId) throws RollbackableBizException {
		logger.info("CmDeviceDAO---------------getCmDeviceVMInfo start---------------------");
		List<T> list = this.findByID(method, bizId);
		logger.info("CmDeviceDAO---------------getCmDeviceVMInfo end---------------------");
		return list;
	}

	@Override
	public int getHostOrderNum() throws RollbackableBizException {
		logger.info("CmDeviceDAO---------------getHostOrderNum start---------------------");
		List count = getSqlMapClientTemplate().queryForList("selectHostOrderNum");
		logger.info("CmDeviceDAO---------------getHostOrderNum end---------------------");
		return (Integer) count.get(0);
	}

	@Override
	public int getCountCmAllHost(String method, String bizId) throws RollbackableBizException {
		logger.info("CmDeviceDAO---------------getCountCmAllHost start---------------------");
		List count = getSqlMapClientTemplate().queryForList(method, bizId);
		logger.info("CmDeviceDAO---------------getCountCmAllHost end---------------------");
		return (Integer) count.get(0);
	}

	@Override
	public <T extends CmDevicePo> List<T> findListByFieldsAndOrder(String method, HashMap<String, String> params) {
		List<T> list = getSqlMapClientTemplate().queryForList(method, params);
		return list;
	}

	@Override
	public List<CmDeviceVo> findDeviceListByDuId(String duId) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("duId", duId);
		paramMap.put("isActive", IsActiveEnum.YES.getValue());
		paramMap.put("deviceStatus", DeviceStatusEnum.DEVICE_STATUS_ONLINE.getValue());
		paramMap.put("srType", SrTypeMarkEnum.VIRTUAL_RECYCLE.getValue());
		paramMap.put("srStatus", SrStatusCodeEnum.REQUEST_DELETE);
		return this.findListByParam("selectDeviceListByDuId", paramMap);
	}

	@Override
	public List<CmIpShowBo> selectCmIpInfo(String device_id) throws RollbackableBizException {
		List<CmIpShowBo> list = new ArrayList<CmIpShowBo>();
		list = this.findByID("selectCmIpInfo", device_id);
		return list;
	}
	

	@Override
	public int getCountCmAllVM(String method, HashMap<String, String> params) throws RollbackableBizException {
		int count = (Integer) this.getSqlMapClientTemplate().queryForObject(method, params);
		return count;
	}

	@Override
	public int getCountByDeviceName(String method, String fullName) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getCountByDeviceNameNew(String deviceName) {
		HashMap<String, String> params = Maps.newHashMap();
		params.put("deviceName", deviceName);
		List<CmDeviceVo> deviceList = this.findListByParam("getCountByDeviceNameNew", params);
		if(deviceList != null && deviceList.size() > 0) {
			return deviceList.size();
		}
		return 0;
	}
	
	public CmDevicePo findCmDeviceById(String id) throws RollbackableBizException {
		return this.findObjectByID("findCmDeviceById", id);
	}

	@Override
	public AllocIpParamVo selectAllocIpParam(String cluster_id) throws RollbackableBizException {
		return this.findObjectByID("selectAllocIpParam", cluster_id);
	}

	@Override
	public int findCmVmCount(String hostID) {
		List list = new ArrayList();
		list = getSqlMapClientTemplate().queryForList("selectcmVmCountByHostId", hostID);
		return (Integer) list.get(0);
	}

	@Override
	public List<RmResPoolVo> getRmResPoolByName(String devName) throws RollbackableBizException {
		return super.findByID("selectRmResPoolByName", devName);
	}

	@Override
	public List<RmClusterPo> getRmResClusterByName(String devName) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return super.findByID("selectRmClusterByName", devName);
	}

	@Override
	public List<CmDeviceVo> getCmDeviceHostByName(String devName) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return super.findByID("selectCmDeviceHostByName", devName);
	}

	@Override
	public List<CmDeviceVo> getCmDeviceVmByName(String devName) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return super.findByID("selectCmDeviceVmtByName", devName);
	}

	public void updateCmDeviceState(CmDevicePo vmDevice) throws RollbackableBizException {
		this.update("updateCmDeviceState", vmDevice);
	}
	
	public void updateCmDeviceServerId(CmDevicePo vmDevice) throws RollbackableBizException {
		this.update("updateCmDeviceServerId", vmDevice);
	}
	
	public Pagination<DeviceInfoVo> findHostDeviceList(PaginationParam pageParam) throws RollbackableBizException {
		return this.pageQuery("findHostDeviceListTotal", "findHostDeviceListPage", pageParam);
	}
	
	public void updateCmDeviceInvcState(HashMap<String,String> map) throws RollbackableBizException{
		this.getSqlMapClientTemplate().update("updateCmDeviceInVcState", map);
	}
	
	public List<CmHostRefVo> findHostRefInfoById(List<String> ids){
		return this.getSqlMapClientTemplate().queryForList("queryHostRefInfoById",ids);
	}
	
	public List<CmHostRefVo> findHostManageInfo(String deviceId){
		return this.getSqlMapClientTemplate().queryForList("queryHostManagerInfo",deviceId);		
	}
	
	public void updateHostClusterInfo(List<String> ids){
		this.getSqlMapClientTemplate().update("updateHostClusterId", ids);
	}
	
	public void updateHostResPoolInfo(List<String> ids){
		this.getSqlMapClientTemplate().update("updateHostResPoolId", ids);
	}
	public void updateHostVCStatusInfo(List<String> ids){
		this.getSqlMapClientTemplate().update("updateHostVCStatus", ids);
	}

	@Override
	public CertificatePo findCertificatePath() throws RollbackableBizException {
		// TODO Auto-generated method stub
		CertificatePo certificatePo = this.findObjectByID("selectCertificate", "1");
		return certificatePo;
		
	}

	@Override
	public List<CmDeviceVo> getCmDevicePoNumber()
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		List<CmDeviceVo>  list=super.findAll("findAllCmDevice");
		return list;
	}

	@Override
	public List<CmClusterHostShowBo> selectCmClusterHostInfos(String id)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList("selectCmClusterHostInfos",id);
	}

	@Override
	public List<String> selectBpmModelId(String modelName) throws RollbackableBizException {
		List aa = this.getSqlMapClientTemplate().queryForList("selectBpmModelId",modelName);
		return aa;
	}


	/*@Override
	public CmDatastoreVo findStorageDeviceName(Map<String, String> map)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		CmDatastoreVo vp = super.findObjectByMap("findStorageDeviceName", map);
		return vp;
	}*/

	public String findDatastoreTypeById(String hostId)
			throws RollbackableBizException {
		List list = new ArrayList();
		list = getSqlMapClientTemplate().queryForList("findDatastoreTypeById", hostId);
		return (String) list.get(0);
	}

	@Override
	public List<String> selectModelName() throws RollbackableBizException {
		List<String> aa = this.getSqlMapClientTemplate().queryForList("selectModelName");
		return aa;
	}

	@Override
	public CmDevicePo selectCmDevicePoById(Map<String, String> map)
			throws RollbackableBizException {
		return null;
	}

	@Override
	public CmDevicePo isCmHost(Map<String, String> map)
			throws RollbackableBizException {
		CmDevicePo cmPo =  super.findObjectByMap("isCmHost", map);
		return cmPo;
	}

	@Override
	public CmDevicePo isCmVm(Map<String, String> map)
			throws RollbackableBizException {
		CmDevicePo cmPo =  super.findObjectByMap("isCmVm", map);
		return cmPo;
	}

	@Override
	public void updateCmdeviceRunningState(CmDevicePo cmDevicePo)
			throws RollbackableBizException {
		this.update("updateCmdeviceRunningState", cmDevicePo);
	}

	@Override
	public void saveDatastoreInfo(CmHostDatastoreRefPo poList)
			throws RollbackableBizException {
		this.save("saveDatastoreInfo",poList);
	}

	@Override
	public void deleteDatastoreInfo(Map<String, Object> map)
			throws RollbackableBizException {
		 this.deleteForParam("deleteDatastoreInfo", map);
	}

	@Override
	public void saveDefaultDatastore(CmDevicePo cmDevicePo)
			throws RollbackableBizException {
		this.update("saveDefaultDatastore", cmDevicePo);
	}

	@Override
	public CmDevicePo getDefaultDatastore(Map<String, String> map)
			throws RollbackableBizException {
		CmDevicePo cmPo =  super.findObjectByMap("getDefaultDatastore", map);
		return cmPo;
	}

	@Override
	public String findVmIdByName(String deviceName)
			throws RollbackableBizException {
		return (String) this.getSqlMapClientTemplate().queryForObject("findVmIdByName", deviceName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CmVmPo findPowerInfoByVmId(String vmId)throws RollbackableBizException {
		return (CmVmPo) this.getSqlMapClientTemplate().queryForObject("findPowerInfoByVmId", vmId);
	}

	@Override
	public String findHostIpById(String id) throws RollbackableBizException {
		
		return (String)this.getSqlMapClientTemplate().queryForObject("findHostIpById",id);
	}

	@Override
	public String getPmRunningState(String hostId)
			throws RollbackableBizException {
		return (String) this.getSqlMapClientTemplate().queryForObject("getPmRunningState", hostId);
	}

	@Override
	public List<CmDevicePo> findDeviceDefaultDatastore(String datastoreId)throws RollbackableBizException {
		return this.getSqlMapClientTemplate().queryForList("findDeviceDefaultDatastore",datastoreId);	
	}

	@Override
	public List<CmHostDatastoreRefPo> findCmHostDatastoreRefPoByStorageId(
			String cmStorageId) throws RollbackableBizException {
		return this.getSqlMapClientTemplate().queryForList("findCmHostDatastoreRefPoByStorageId",cmStorageId);
	}

	@Override
	public CmDevicePo findNewDevcieByHostId()
			throws RollbackableBizException {
		return (CmDevicePo) this.getSqlMapClientTemplate().queryForObject("findNewDevcieByHostId");
	}

	@Override
	public CmHostPo findDistribHost(String ipInfo)
			throws RollbackableBizException {
		return (CmHostPo) this.getSqlMapClientTemplate().queryForObject("findDistribHost", ipInfo);
		
	}

	@Override
	public CmDistributePortGroupPo findDisPortGroupId(Map<String, String> map) {
		return (CmDistributePortGroupPo) this.getSqlMapClientTemplate().queryForObject("findDisPortGroupId", map);  
	}

	@Override
	public Pagination<CmHostPo> getHostConfigure(PaginationParam paginationParam) throws RollbackableBizException {
		return this.pageQuery("getHostConfigureTotal", "getHostConfigurePage", paginationParam);
	}

	@Override
	public Pagination<RmDeviceVolumesRefPo> getRmDeviceVolumesRefPoList(PaginationParam paginationParam)
			throws RollbackableBizException {
		return this.pageQuery("findRmDeviceVolumesRefPoTotal", "findRmDeviceVolumesRefPoPage", paginationParam);
	}

	@Override
	public void saveRmDeviceVolumesRefPo(List<RmDeviceVolumesRefPo> rmDeviceVolumesRefPoList)
			throws RollbackableBizException {
		if(CollectionUtil.hasContent(rmDeviceVolumesRefPoList)){
			this.batchInsert("rmDeviceVolumesPoBatchInsert", rmDeviceVolumesRefPoList);
		}
	}

	@Override
	public void updateMountStatus(RmDeviceVolumesRefPo rmDeviceVolumesRefPo) throws RollbackableBizException {
		this.update("updateDeviceMountStatus", rmDeviceVolumesRefPo);
	}
	
	public void updateDeviceActiveStatusToN(String deviceId) throws RollbackableBizException {
		this.deleteForIsActive("updateDeviceActiveStatusToN", deviceId);
	}

	@Override
	public void saveRmDeviceVolumesRefPo(RmDeviceVolumesRefPo rmDeviceVolumesRefPo)
			throws RollbackableBizException {
		this.save("insertRmDeviceVolumesRefPo", rmDeviceVolumesRefPo);
		
	}

	@Override
	public void deleteRmDeviceVolumesRef(RmDeviceVolumesRefPo ref) throws RollbackableBizException {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("targetVolumeId", ref.getTargetVolumeId());
		map.put("deviceId", ref.getDeviceId());
		this.deleteForParam("deleteRmDeviceVolumesRef", map);
		
	}

	@Override
	public RmDeviceVolumesRefPo getRmDeviceVolumesRefByMap(HashMap<String, String> map)
			throws RollbackableBizException {
		return super.findObjectByMap("getRmDeviceVolumesRefByMap",map);
	}

	@Override
	public void updateRmDvRefVolumeId(RmDeviceVolumesRefPo ref) throws RollbackableBizException {
		this.update("updateRmDvRefVolumeId", ref);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CmDevicePo> getVmIdByProjectId(String projectId)
			throws RollbackableBizException {
		return  this.getSqlMapClientTemplate().queryForList("getVmIdByProjectId", projectId);
	}
	
	@Override
	public void saveOpenstackVolume(VolumeDetailVo volumeVo){
		this.getSqlMapClientTemplate().insert("saveOpenstackVolume", volumeVo);
	}
	
	@Override
	public void deleteOpenstackVolume(String volumeId){
		this.getSqlMapClientTemplate().delete("deleteOpenstackVolume", volumeId);
	}
	@Override
	public List<VolumeDetailVo> getOpenstackVolume(String projectId){
		return this.getSqlMapClientTemplate().queryForList("getOpenstackVolume", projectId);
	}
	
	@Override
	public void updateVmName(String deviceId,String deviceName) throws RollbackableBizException {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("deviceId", deviceId);
		map.put("deviceName", deviceName);
		getSqlMapClientTemplate().update("updateVmName", map);
	}
	@Override
	public void updateVmHost(String deviceId,String host) throws RollbackableBizException {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("deviceId", deviceId);
		map.put("host", host);
		getSqlMapClientTemplate().update("updateVmHost", map);
	}
	@Override
	public void updateVmPowerStatus(String deviceId,String powerStatus) throws RollbackableBizException {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("deviceId", deviceId);
		map.put("powerStatus", powerStatus);
		getSqlMapClientTemplate().update("updateVmPowerStatus", map);
	}
	@Override
	public List<String> findVolumeTypeList(String availableZoneId){
		return this.getSqlMapClientTemplate().queryForList("getVolumeTypeList", availableZoneId);
	}
	public void updateVmProjectId(String vmId, String projectId) throws RollbackableBizException {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("vmId", vmId);
		map.put("projectId", projectId);
		getSqlMapClientTemplate().update("updateVmProjectId", map);
	}	
	@Override
	public void saveServiceInstanceNode(ServiceInstanceNode node){ 
		this.getSqlMapClientTemplate().insert("saveServiceInstanceNode", node);
	}
	
	@Override
	public ServiceInstanceNode getServiceInstanceNodeById(String deviceId){
		return (ServiceInstanceNode) this.getSqlMapClientTemplate().queryForObject("getServiceInstanceNodeById", deviceId);
	}
	
	@Override
	public List<ServiceInstanceNode> getServiceInstanceNodeByClusterId(Map<String,String> map){
		return this.getSqlMapClientTemplate().queryForList("getServiceInstanceNodeByClusterId", map);
	}
	
	@Override
	public void deleteInstanceNodeById(String deviceId){
		this.getSqlMapClientTemplate().delete("deleteInstanceNodeById", deviceId);
	}
	@Override
	public void deleteInstanceNodeRefById(String deviceId){
		this.getSqlMapClientTemplate().delete("deleteInstanceNodeRefById", deviceId);
	}
	@Override
	public void deleteServiceInstanceById(String serviceInstanceId){
		this.getSqlMapClientTemplate().delete("deleteServiceInstanceById", serviceInstanceId);
	}
	@Override
	public void deleteServiceInstanceRefById(String serviceInstanceId){
		this.getSqlMapClientTemplate().delete("deleteServiceInstanceRefById", serviceInstanceId);
	}
	
}
