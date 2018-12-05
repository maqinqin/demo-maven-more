package com.git.cloud.resmgt.network.dao;


import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.po.VirtualNetworkPo;

public interface IVirtualNetworkDao extends ICommonDAO{
	
	public VirtualNetworkPo selectVirtualNetwork(String id)throws Exception;
	
	public List<OpenstackIpAddressPo> selectIpAddressByNetwork(String networkId) throws Exception;
	
	public OpenstackIpAddressPo selectIpAddressByInstanceIdAndIp(String instanceId, String ip) throws Exception;
	
	public OpenstackIpAddressPo selectIpAddressByNetworkAndIp(String networkId, String ip) throws Exception;
	
	public List<OpenstackIpAddressPo> selectIpAddressByDeviceId(String deviceId) throws Exception;
	
	public void updateIpAddressByNetwork(OpenstackIpAddressPo ipAddress) throws RollbackableBizException;
	
	public void updateIpAddressRecycleByVm(OpenstackIpAddressPo ipAddress) throws RollbackableBizException;
}
