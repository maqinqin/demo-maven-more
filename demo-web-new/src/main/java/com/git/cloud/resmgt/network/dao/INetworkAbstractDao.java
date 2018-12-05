package com.git.cloud.resmgt.network.dao;

import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;

public interface INetworkAbstractDao extends ICommonDAO {
	public String findAllIpNumById(Map<String, String> id);
	
	public String findUsableIpNumById(Map<String, String> id);

	public Map<String, Object> findNetworkCount(Map<String, String> id);
}
