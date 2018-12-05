package com.git.cloud.resmgt.network.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.network.dao.IVirtualNetworkDao;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.po.VirtualNetworkPo;
import com.git.cloud.resmgt.network.service.IVirtualNetworkService;



public class VirtualNetworkServiceImpl implements IVirtualNetworkService{
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private IVirtualNetworkDao virtualNetworkDao;

	public IVirtualNetworkDao getVirtualNetworkDao() {
		return virtualNetworkDao;
	}

	public void setVirtualNetworkDao(IVirtualNetworkDao virtualNetworkDao) {
		this.virtualNetworkDao = virtualNetworkDao;
	}
	@Override
	public VirtualNetworkPo selectVirtualNetwork(String networkId)throws Exception {
		return virtualNetworkDao.selectVirtualNetwork(networkId);
	}
	@Override
	public List<OpenstackIpAddressPo> selectIpAddressByNetwork(String networkId)throws Exception {
		return virtualNetworkDao.selectIpAddressByNetwork(networkId);
	}
	@Override
	public OpenstackIpAddressPo selectIpAddressByInstanceIdAndIp(String instanceId, String ip)throws Exception {
		return virtualNetworkDao.selectIpAddressByInstanceIdAndIp(instanceId, ip);
	}
	@Override
	public OpenstackIpAddressPo selectIpAddressByNetworkAndIp(String networkId, String ip)throws Exception {
		return virtualNetworkDao.selectIpAddressByNetworkAndIp(networkId, ip);
	}
	@Override
	public void updateIpAddressByNetwork(OpenstackIpAddressPo ipAddress) throws RollbackableBizException {
		virtualNetworkDao.updateIpAddressByNetwork(ipAddress);
	}

	@Override
	public List<OpenstackIpAddressPo> selectIpAddressByDeviceId(String deviceId) throws Exception {
		return virtualNetworkDao.selectIpAddressByDeviceId(deviceId);
	}
}
