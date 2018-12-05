package com.git.cloud.resmgt.compute.dao.impl;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.RmHostVmInfoPo;
import com.git.cloud.resmgt.compute.dao.ICmVirtualSwitchDao;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;

public class CmVirtualSwitchDaoImpl extends CommonDAOImpl implements ICmVirtualSwitchDao{


	@Override
	public void updateRmNwOpstackIpAddress(OpenstackIpAddressPo rmNwIpAddressVo)
			throws Exception {
		super.update("updateRmNwOpstackIpAddress", rmNwIpAddressVo);
	}

	public RmHostVmInfoPo findHostVmInfoById(Map<String,String> pMap) throws RollbackableBizException {
		List<RmHostVmInfoPo> list = this.getSqlMapClientTemplate().queryForList("findHostVmInfoById", pMap);
		if(list.size() > 0){
			return list.get(0);
		}
		else{
			return null;
		}
	}
}
