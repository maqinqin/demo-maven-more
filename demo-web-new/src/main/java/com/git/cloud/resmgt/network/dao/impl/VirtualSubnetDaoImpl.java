package com.git.cloud.resmgt.network.dao.impl;



import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.resmgt.network.dao.IVirtualSubnetDao;
import com.git.cloud.resmgt.network.model.po.VirtualSubnetPo;

public class VirtualSubnetDaoImpl extends CommonDAOImpl implements IVirtualSubnetDao{
	@SuppressWarnings("unchecked")
	@Override
	public List<VirtualSubnetPo> selectSubnetByNetwork(String networkId) throws Exception {
		return getSqlMapClientTemplate().queryForList("selectSubnetByNetwork", networkId);
	}

	@Override
	public VirtualSubnetPo selectVirtualSubnetPoById(String virtualSubnetId) throws Exception {
		return (VirtualSubnetPo) getSqlMapClientTemplate().queryForObject("selectVirtualSubnetPoById", virtualSubnetId);
	}
}
