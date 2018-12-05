package com.git.cloud.resmgt.network.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.network.dao.INetworkAbstractDao;
import com.git.cloud.resmgt.network.service.INetworkAbstaractService;

public class NetworkAbstaractServiceImpl implements INetworkAbstaractService {
	private INetworkAbstractDao networkAbstractDao ;

	public INetworkAbstractDao getNetworkAbstractDao() {
		return networkAbstractDao;
	}

	public void setNetworkAbstractDao(INetworkAbstractDao networkAbstractDao) {
		this.networkAbstractDao = networkAbstractDao;
	}

	@Override
	public Map<String, String> findNetworkAbstract(String dataCenterId,String bClassId,String cClassId)throws RollbackableBizException {
		Map<String, String> id = new HashMap<String, String>();
		id.put("dataCenterId", dataCenterId);
		id.put("bClassId", bClassId);
		id.put("cClassId", cClassId);
		String ipNum = networkAbstractDao.findAllIpNumById(id);
		String usableipNum = networkAbstractDao.findUsableIpNumById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map = networkAbstractDao.findNetworkCount(id);
		String bCount = String.valueOf(map.get("bCount"));
		String cCount = String.valueOf(map.get("cCount"));
		String poolCount = String.valueOf(map.get("poolCount"));
		Map<String, String> ip = new HashMap<String, String>();
		ip.put("ipNum", ipNum);
		ip.put("usableipNum", usableipNum);
		ip.put("bCount", bCount);
		ip.put("cCount", cCount);
		ip.put("poolCount", poolCount);
		return ip;
	}
	
}
