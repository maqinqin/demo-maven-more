package com.git.cloud.resmgt.compute.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.compute.model.po.DuPoByRmHost;
import com.git.cloud.resmgt.compute.model.vo.CloudServiceVoByRmHost;
import com.git.cloud.resmgt.compute.model.vo.IpRules;
import com.git.cloud.resmgt.compute.model.vo.ScanVmResultVo;
import com.git.cloud.resmgt.compute.model.vo.VmVo;

public interface IRmHostService {

	public List<ScanVmResultVo> scanVmList(VmVo vm) throws Exception;
	
	public List<CloudServiceVoByRmHost> vmCloudServiceList(VmVo vm);
	
	public List <IpRules> getIpRules(VmVo vm);
	
	public boolean checkVmIsExsit(String vmName);
	
	public List<DuPoByRmHost> getDuList(VmVo vm);
	
	public boolean checkDataStore(String dataStoreName,String hostId);
	
}
