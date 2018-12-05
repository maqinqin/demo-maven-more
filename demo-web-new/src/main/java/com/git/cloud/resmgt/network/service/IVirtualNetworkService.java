package com.git.cloud.resmgt.network.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.po.VirtualNetworkPo;

public interface IVirtualNetworkService {
	
	public VirtualNetworkPo selectVirtualNetwork(String networkId)throws Exception;
	
	public List<OpenstackIpAddressPo> selectIpAddressByNetwork(String networkId)throws Exception;
	
	public OpenstackIpAddressPo selectIpAddressByInstanceIdAndIp(String instanceId, String ip)throws Exception;
	
	public OpenstackIpAddressPo selectIpAddressByNetworkAndIp(String networkId, String ip)throws Exception;
	
	public void updateIpAddressByNetwork(OpenstackIpAddressPo ipAddress) throws RollbackableBizException;
	/**
	 * 获取openstack的ip地址信息
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	public List<OpenstackIpAddressPo> selectIpAddressByDeviceId(String deviceId) throws Exception;
	
	
}
