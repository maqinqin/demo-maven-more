package com.git.cloud.resmgt.compute.handler;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.compute.dao.IRmClusterDAO;
import com.git.cloud.resmgt.compute.dao.impl.RmClusterDAO;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;

public class ClusterServiceImpl implements ClusterService{
	
	private IRmClusterDAO rmClusterDAO;
	
	private ICmDeviceDAO icmDeviceDAO;
	
	public IRmClusterDAO getRmClusterDAO() {
		return rmClusterDAO;
	}
	public void setRmClusterDAO(IRmClusterDAO rmClusterDAO) {
		this.rmClusterDAO = rmClusterDAO;
	}
	public ICmDeviceDAO getIcmDeviceDAO() {
		return icmDeviceDAO;
	}
	public void setIcmDeviceDAO(ICmDeviceDAO icmDeviceDAO) {
		this.icmDeviceDAO = icmDeviceDAO;
	}
	@Override
	public List<RmClusterPo> findAllCluster() throws Exception {
		
		List<RmClusterPo> clusterList = rmClusterDAO.findAllCluster();
		return clusterList;
	}
	@Override
	public List<CmDevicePo> findAllHostDevice(String clusterId)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("clusterId", clusterId);
		return icmDeviceDAO.findListByFieldsAndOrder("findAllHostDevice",map);
	}

	@Override
	public List<CmDevicePo> findAllVmDevice(String clusterId) throws Exception {
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("clusterId", clusterId);
		return icmDeviceDAO.findListByFieldsAndOrder("findAllVmDevice",map);
	}
	@Override
	public List<RmClusterPo> findClusterListByPoolId(String poolId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
