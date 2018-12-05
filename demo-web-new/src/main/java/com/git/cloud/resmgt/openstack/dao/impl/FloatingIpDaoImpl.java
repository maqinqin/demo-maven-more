package com.git.cloud.resmgt.openstack.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.resmgt.openstack.dao.IFloatingIpDao;
import com.git.cloud.resmgt.openstack.model.vo.FloatingIpVo;
@Service
public class FloatingIpDaoImpl extends CommonDAOImpl implements IFloatingIpDao {
	
	
	@Override
	public FloatingIpVo findFloatingIpByIp(String floatingIp) throws Exception {
		return (FloatingIpVo) getSqlMapClientTemplate().queryForObject("findFloatingIpByIp", floatingIp);
	}
	@Override
	public FloatingIpVo findFloatingIpByDeviceId(String deviceId) throws Exception {
		return (FloatingIpVo) getSqlMapClientTemplate().queryForObject("findFloatingIpByDeviceId", deviceId);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<FloatingIpVo> findFloatingIpUnUsed(String projectId) throws Exception {
		return (List<FloatingIpVo>) getSqlMapClientTemplate().queryForList("findFloatingIpUnUsed", projectId);
	}
	@Override
	public void updateFloatingIp(FloatingIpVo floatingIpVo) throws Exception {
		update("updateFloatingIp", floatingIpVo);
	}
}
