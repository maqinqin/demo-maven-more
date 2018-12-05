package com.git.cloud.resmgt.compute.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.excel.model.vo.HostVo;
import com.git.cloud.resmgt.compute.dao.IOpenstackSynckDao;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
import com.google.common.collect.Maps;
//@Service
@Repository
public class OpenstackSynckDaoImpl extends CommonDAOImpl  implements IOpenstackSynckDao{

	
	@Override
	public RmClusterPo selectPlatByClusterId(String id)throws RollbackableBizException {
		Map<String, String> maps=Maps.newHashMap();
		maps.put("id", id);
		return super.findObjectByMap("selectPlatByClusterId", maps);
	}

	public HostVo selectHostByHostId(String hostId)throws RollbackableBizException{
		Map<String, String> maps=Maps.newHashMap();
		maps.put("id", hostId);
		return super.findObjectByMap("selectHostByHostId", maps);
	}

	
}
