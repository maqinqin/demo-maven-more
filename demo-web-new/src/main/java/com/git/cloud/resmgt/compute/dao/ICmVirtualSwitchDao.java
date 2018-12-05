package com.git.cloud.resmgt.compute.dao;

import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.RmHostVmInfoPo;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;

public interface ICmVirtualSwitchDao {
	
	void updateRmNwOpstackIpAddress(OpenstackIpAddressPo rmNwIpAddressVo) throws Exception;
	/**
	 * 查询物理机或虚拟机的信息
	 * @param pMap
	 * @return
	 * @throws RollbackableBizException
	 */
	public RmHostVmInfoPo findHostVmInfoById(Map<String,String> pMap) throws RollbackableBizException;
}