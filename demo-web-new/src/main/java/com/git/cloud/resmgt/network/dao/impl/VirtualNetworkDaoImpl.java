package com.git.cloud.resmgt.network.dao.impl;



import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.network.dao.IVirtualNetworkDao;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.po.VirtualNetworkPo;

public class VirtualNetworkDaoImpl extends CommonDAOImpl implements IVirtualNetworkDao{
	public VirtualNetworkPo selectVirtualNetwork(String id)throws Exception {
		return (VirtualNetworkPo) getSqlMapClientTemplate().queryForObject("selectVirtualNetwork", id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OpenstackIpAddressPo> selectIpAddressByNetwork(String networkId) throws Exception{
		return getSqlMapClientTemplate().queryForList("selectIpAddressNetwork", networkId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public OpenstackIpAddressPo selectIpAddressByInstanceIdAndIp(String instanceId, String ip) throws Exception{
		OpenstackIpAddressPo ipAddress = new OpenstackIpAddressPo();
		ipAddress.setInstanceId(instanceId);
		ipAddress.setIp(ip);
		List<OpenstackIpAddressPo> ipList = getSqlMapClientTemplate().queryForList("selectIpAddressByInstanceIdAndIp", ipAddress);
		if(ipList != null && ipList.size() == 1) {
			return ipList.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public OpenstackIpAddressPo selectIpAddressByNetworkAndIp(String networkId, String ip) throws Exception{
		OpenstackIpAddressPo ipAddress = new OpenstackIpAddressPo();
		ipAddress.setNetworkId(networkId);
		ipAddress.setIp(ip);
		List<OpenstackIpAddressPo> ipList = getSqlMapClientTemplate().queryForList("selectIpAddressByNetworkAndIp", ipAddress);
		if(ipList != null && ipList.size() == 1) {
			return ipList.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OpenstackIpAddressPo> selectIpAddressByDeviceId(String deviceId) throws Exception{
		return getSqlMapClientTemplate().queryForList("selectIpAddressByDeviceId", deviceId);
	}
	@Override
	public void updateIpAddressByNetwork(OpenstackIpAddressPo ipAddress) throws RollbackableBizException {
		super.update("updateIpAddressByNetwork", ipAddress);
	}
	@Override
	public void updateIpAddressRecycleByVm(OpenstackIpAddressPo ipAddress) throws RollbackableBizException {
		super.update("updateIpAddressRecycleByVm", ipAddress);
	}
}
