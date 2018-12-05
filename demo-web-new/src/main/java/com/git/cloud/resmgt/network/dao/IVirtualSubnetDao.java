package com.git.cloud.resmgt.network.dao;


import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.resmgt.network.model.po.VirtualNetworkPo;
import com.git.cloud.resmgt.network.model.po.VirtualSubnetPo;

public interface IVirtualSubnetDao extends ICommonDAO{
	
	public List<VirtualSubnetPo> selectSubnetByNetwork(String networkId) throws Exception;
	
	VirtualSubnetPo selectVirtualSubnetPoById(String virtualSubnetId) throws Exception;
}
