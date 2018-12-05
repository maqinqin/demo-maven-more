package com.git.cloud.excel.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.excel.model.vo.DataStoreVo;
import com.git.cloud.excel.model.vo.HostVo;
import com.git.cloud.excel.model.vo.VmVo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;

public interface IExcelWriteDataBaseService extends IService {

	public String saveDataCenterByCode(List<HostVo> hostVoList,List<VmVo> vmList,List<DataStoreVo> dsList,String fileName)throws Exception;
	public String selecthostIdByName(String name)throws RollbackableBizException;	
}
