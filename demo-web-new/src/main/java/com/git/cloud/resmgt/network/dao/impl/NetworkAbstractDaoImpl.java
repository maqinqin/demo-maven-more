package com.git.cloud.resmgt.network.dao.impl;

import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.resmgt.network.dao.INetworkAbstractDao;

public class NetworkAbstractDaoImpl extends CommonDAOImpl implements INetworkAbstractDao {

	@Override
	public String findAllIpNumById(Map<String, String> id) {
		String ipNum = (String)getSqlMapClientTemplate().queryForObject("findAllIpNumById", id);
		return ipNum;
	}

	@Override
	public String findUsableIpNumById(Map<String, String> id) {
		String usableipNum = (String)getSqlMapClientTemplate().queryForObject("findUsableIpNumById", id);
		return usableipNum;
	}
	@Override
	public Map<String, Object> findNetworkCount(Map<String, String> id){
		Map<String, Object> countMap = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("findNetworkCount", id);
		return countMap;
	}
	
}
