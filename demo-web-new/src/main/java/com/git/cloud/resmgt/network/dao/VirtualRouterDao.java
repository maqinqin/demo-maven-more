package com.git.cloud.resmgt.network.dao;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.resmgt.network.model.po.VirtualRouterPo;

public interface VirtualRouterDao extends ICommonDAO{
	
	VirtualRouterPo selectVirtualRouterPrimaryKey(String virtualRouterId) throws Exception;
}
