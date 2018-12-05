package com.git.cloud.resmgt.compute.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.resmgt.compute.dao.ICmVirtualSwitchDao;
import com.git.cloud.resmgt.compute.service.ICmVirtualSwitchService;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
@SuppressWarnings("unchecked")
public class CmVirtualSwitchServiceImpl implements ICmVirtualSwitchService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ICmVirtualSwitchDao cmVirtualSwitchDao;
	public ICmVirtualSwitchDao getCmVirtualSwitchDao() {
		return cmVirtualSwitchDao;
	}

	public void setCmVirtualSwitchDao(ICmVirtualSwitchDao cmVirtualSwitchDao) {
		this.cmVirtualSwitchDao = cmVirtualSwitchDao;
	}
	@Override
	public void updateRmNwOpstackIpAddress(OpenstackIpAddressPo rmNwIpAddressVo) throws Exception{
		cmVirtualSwitchDao.updateRmNwOpstackIpAddress(rmNwIpAddressVo);
	}
	
	

}
