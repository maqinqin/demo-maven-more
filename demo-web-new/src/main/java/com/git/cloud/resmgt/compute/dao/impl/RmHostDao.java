package com.git.cloud.resmgt.compute.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.cxf.binding.corba.wsdl.Array;
import org.springframework.stereotype.Repository;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.vo.IpRuleInfoVo;
import com.git.cloud.resmgt.compute.dao.IRmHostDao;
import com.git.cloud.resmgt.compute.model.po.DuPoByRmHost;
import com.git.cloud.resmgt.compute.model.vo.CloudServiceVoByRmHost;
import com.git.cloud.resmgt.compute.model.vo.IpRules;
import com.git.cloud.resmgt.compute.model.vo.VmVo;
@Repository("rmHostDao")
@SuppressWarnings("unchecked")
public class RmHostDao extends CommonDAOImpl implements IRmHostDao {

	@Override
	public void saveVm(VmVo vm) throws RollbackableBizException {
		getSqlMapClientTemplate().insert("rmHostVmSaveVm", vm);
		getSqlMapClientTemplate().insert("rmHostVmSaveDevice",vm);
	}

	@Override
	public List<IpRules> getIpRules(VmVo vs) {
		List <IpRules> list = getSqlMapClientTemplate().queryForList("queryIpRuleList", vs);
		return list;
	}

	@Override
	public List<CloudServiceVoByRmHost> getCloudServices(VmVo vm) {
		//System.out.println(vm.getHostId());
		List <CloudServiceVoByRmHost> list =  getSqlMapClientTemplate().queryForList("getCloudServiceList", vm);
			return list;
	}

	@Override
	public boolean checkVmIsExist(String vmName) {
		int Count = (Integer)getSqlMapClientTemplate().queryForObject("checkVmIsExist", vmName);
		if (Count==0)
			return false;
		else
			return true;
	}

	@Override
	public List<DuPoByRmHost> getDuList(VmVo vm) {
		String cloudService = vm.getCloudService();
		List <DuPoByRmHost> list =  getSqlMapClientTemplate().queryForList("getDuListByRmHost", cloudService);
		return list;
	}
	
	//查询没有关联serviceId的服务器角色
	@Override
	public List<DuPoByRmHost> getDuListNoServiceId() {
		List <DuPoByRmHost> returnList = new ArrayList<DuPoByRmHost>();
		List <DuPoByRmHost> list =  getSqlMapClientTemplate().queryForList("getDuListNoServiceId");
		if(list.size()>0){
			for(DuPoByRmHost l : list){
				List<String> count=getSqlMapClientTemplate().queryForList("findAppSysByduid",l.getAppDuId());	
				if(count!=null && count.size()>0 && !"0".equals(count.get(0))){
					//1,服务器角色被使用
				}else{
					//0,服务器角色未被使用
					returnList.add(l);
				}
			}
		}
		return returnList;
	}

	@Override
	public boolean checkDataStore(String dataStoreName,String hostId) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("dataStoreName", dataStoreName);
		param.put("hostId", hostId);
		int Count = (Integer)getSqlMapClientTemplate().queryForObject("checkdataStoreIsExist", param);
		if (Count==0)
			return false;
		else
			return true;
	}
	
	@Override
	public HashMap<String,String> findDatastoreId(String dataStoreName,String hostId) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("dataStoreName", dataStoreName);
		param.put("hostId", hostId);
		String datastoreId = (String) getSqlMapClientTemplate().queryForObject("selectDatastoreId", param);
		HashMap<String, String> resultMap =  new HashMap<String, String>();
		if(datastoreId == null || datastoreId.isEmpty()){
			String shareId = (String) this.getSqlMapClientTemplate().queryForObject("selectShareDatastoreId", param);
			resultMap.put("datastoreId", shareId);
			resultMap.put("datastoreType", "NAS_DATASTORE");
		}else {
			resultMap.put("datastoreId", datastoreId);
			resultMap.put("datastoreType", "LOCAL_DISK");
		}
		return resultMap;
	}
	
	@Override
	public HashMap<String, String> findDeviceInfoByHostId(String hostId) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("hostId", hostId);
		return (HashMap<String, String>) getSqlMapClientTemplate().queryForObject("selectDeviceInfoByHostId", param);
	}

	@Override
	public List<IpRuleInfoVo> findIpRuleInfoByHostIdAndCloSerId(String hostId,String cloudService) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("hostId", hostId);
		param.put("cloudService",cloudService);
		return this.getSqlMapClientTemplate().queryForList("selectIpRuleInfoByHostIdAndCloSerId", param);
	}

	@Override
	public RmDatacenterPo findDatacenterInfoByHostId(String hostId) {
		return (RmDatacenterPo) this.getSqlMapClientTemplate().queryForObject("findDatacenterInfoByHostId", hostId);
	}

}
