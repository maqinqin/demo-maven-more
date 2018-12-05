package com.git.cloud.resmgt.network.dao.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.resmgt.network.dao.VirtualRouterDao;
import com.git.cloud.resmgt.network.model.po.VirtualRouterPo;

public class VirtualRouterDaoImpl extends CommonDAOImpl implements VirtualRouterDao{

	@Override
	public VirtualRouterPo selectVirtualRouterPrimaryKey(String virtualRouterId) throws Exception {
		return (VirtualRouterPo) super.findByID("selectVirtualRouterPrimaryKey", virtualRouterId);
	}

}
