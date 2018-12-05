package com.git.cloud.resmgt.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.CollectionUtil;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDeviceVolumesRefPo;
import com.git.cloud.resmgt.common.model.vo.CmSnapshotVo;
import com.git.cloud.resmgt.common.model.vo.CmVmVo;
import com.git.cloud.resmgt.common.model.vo.SyncVmInfoVo;
import com.git.cloud.resmgt.compute.model.vo.VmMonitorVo;
import com.git.cloud.resmgt.network.model.po.RmNwIpAddressPo;
import com.git.cloud.resmgt.openstack.model.vo.FloatingIpVo;

@Repository
public class CmVmDAO extends CommonDAOImpl implements ICmVmDAO {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends CmVmPo> List<T> findListByFieldsAndOrder(String method, HashMap<String, String> params) {
		List<T> list = getSqlMapClientTemplate().queryForList(method, params);
		return list;
	}

	public void insertCmVm(List<CmVmPo> cmVmList) throws RollbackableBizException {
		if (CollectionUtil.hasContent(cmVmList)) {
			this.batchInsert("cmVmBatchInsert", cmVmList);
		}
	}

	public void insertOldCmVm(List<CmVmPo> cmVmList) throws RollbackableBizException {
		if (CollectionUtil.hasContent(cmVmList)) {
			this.batchInsert("cmVmOldBatchInsert", cmVmList);
		}
	}

	public void updateCmVmModel(CmVmPo cmVm) throws RollbackableBizException {
		this.update("updateCmVmModel", cmVm);
	}
	public void updateCmVmHostId(CmVmPo cmVm) throws RollbackableBizException {
		this.update("updateCmVmHostId", cmVm);
	}

	public void updateCmVmOnlineTime(CmVmPo cmVm) throws RollbackableBizException {
		this.update("updateCmVmOnlineTime", cmVm);
	}

	public void updateCmVmDuId(CmVmPo cmVm) throws RollbackableBizException {
		this.update("updateCmVmDuId", cmVm);
	}

	public CmVmPo findCmVmById(String id) throws RollbackableBizException {
		return this.findObjectByID("findCmVmById", id);
	}

	public List<CmVmPo> findCmVmByDuId(String duId) throws RollbackableBizException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("duId", duId);
		return this.findListByParam("findCmVmByDuId", paramMap);
	}

	public CmVmVo findPlatformTypeAndVmTypeByVmId(String vmId) throws RollbackableBizException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("vmId", vmId);
		List<CmVmVo> vmList = this.findListByParam("findPlatformTypeAndVmTypeByVmId", paramMap);
		CmVmVo vm = null;
		if (vmList != null && vmList.size() > 0) {
			vm = vmList.get(0);
		}
		return vm;
	}

	@Override
	public void updateVmDuId(CmVmVo cmvm) throws RollbackableBizException {
		this.update("updateDuId", cmvm);
	}

	@Override
	public int updateVmStatus(SyncVmInfoVo vmInfo) throws RollbackableBizException {
		return super.update("updateVmStatus", vmInfo);
	}

	@Override
	public int updateVmHostId(SyncVmInfoVo vmInfo) throws RollbackableBizException {
		return this.update("updateVmHostId", vmInfo);
	}

	@Override
	public String findIDByVmName(String vmName) {
		return (String) this.getSqlMapClientTemplate().queryForObject("findIDByVmName", vmName);
	}

	@Override
	public String findVmPowerStateByVmName(String vmName) {
		return (String) this.getSqlMapClientTemplate().queryForObject("findVmPowerStateByVmName", vmName);
	}

	@Override
	public List<CmSnapshotVo> getCmSnapshotVoList(String vmId) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("vmId", vmId);
		return this.findListByParam("getCmSnapshotVoList", paramMap);
	}
	
	@Override
	public CmVmPo getCpuMemSumByAppIds(String appIds) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("appIds", "'" + appIds.replaceAll(",", "','") + "'");
		List<CmVmPo> vmList = this.findListByParam("getCpuMemSumByAppIds", paramMap);
		CmVmPo vm = null;
		if(vmList != null && vmList.size() > 0) {
			vm = vmList.get(0);
		}
		return vm;
	}
	
	@Override
	public int updateVmDisk(CmVmPo vm) throws RollbackableBizException {
		return super.update("updateVmDisk", vm);
	}

	@Override
	public List<CmVmVo> findVmInfoListByParams(Map<String, String> params) {
		return (List<CmVmVo>) this.getSqlMapClientTemplate().queryForList("findVmInfoListByParams", params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<VmMonitorVo> getVmMonitor(Map<String, String> params){
		return getSqlMapClientTemplate().queryForList("getVmMonitor",params);
	}

	@Override
	public List<RmDeviceVolumesRefPo> getDeviceVolumeRefList(String deviceId) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("deviceId", deviceId);
		return this.findListByParam("getDeviceVolumeRefList", paramMap);
	}

	@Override
	public List<FloatingIpVo> getFloatIpList(String deviceId) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("deviceId", deviceId);
		return this.findListByParam("getFloatIpList", paramMap);
	}

	@Override
	public void updateVmOfflineTime(CmVmPo cmvm) throws RollbackableBizException {
		super.update("updateVmOfflineTime", cmvm);
	}
	
	@Override
	public Integer selectCountByTenantId(String id) throws RollbackableBizException {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("selectCountByTenantId", id);
	}

	@Override
	public CmVmPo selectCpuMemSumByTenantId(String id) throws RollbackableBizException {
		return (CmVmPo) this.getSqlMapClientTemplate().queryForObject("getCpuMemSumByTenantId", id);
	}

	@Override
	public void updateCmVm(CmVmPo cmvm) throws RollbackableBizException {
		super.update("updateCmVm", cmvm);
	}
}
