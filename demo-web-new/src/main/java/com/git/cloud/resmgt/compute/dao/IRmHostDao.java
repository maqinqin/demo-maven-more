package com.git.cloud.resmgt.compute.dao;

import java.util.HashMap;
import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.vo.IpRuleInfoVo;
import com.git.cloud.resmgt.compute.model.po.DuPoByRmHost;
import com.git.cloud.resmgt.compute.model.vo.CloudServiceVoByRmHost;
import com.git.cloud.resmgt.compute.model.vo.IpRules;
import com.git.cloud.resmgt.compute.model.vo.VmVo;

public interface IRmHostDao extends ICommonDAO {
	public void saveVm(VmVo vm) throws RollbackableBizException;
	
	public List <IpRules> getIpRules(VmVo vm );
	
	public List<CloudServiceVoByRmHost> getCloudServices(VmVo vm) ;
	
	public boolean checkVmIsExist(String vmName);
	
	public List<DuPoByRmHost> getDuList(VmVo vm);
	
	public boolean checkDataStore(String dataStoreName,String hostId);

	public HashMap<String, String> findDatastoreId(String dataStoreName, String hostId);

	public HashMap<String, String> findDeviceInfoByHostId(String hostId);

	public List<IpRuleInfoVo> findIpRuleInfoByHostIdAndCloSerId(String hostId,String cloudService);
	
	public RmDatacenterPo findDatacenterInfoByHostId(String hostId);

	public List<DuPoByRmHost> getDuListNoServiceId();
	
}
