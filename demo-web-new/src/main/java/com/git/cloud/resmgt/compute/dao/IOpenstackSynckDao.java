package com.git.cloud.resmgt.compute.dao;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.excel.model.vo.HostVo;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;

public interface IOpenstackSynckDao extends ICommonDAO{
	
	public RmClusterPo selectPlatByClusterId(String rmClusterId)throws RollbackableBizException;
	public HostVo selectHostByHostId(String hostId)throws RollbackableBizException;
	
}
