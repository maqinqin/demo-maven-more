package com.git.cloud.resmgt.compute.handler;

import java.util.List;

import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;

public interface ClusterService {
	
	/**
	 * 获取所有集群信息
	 * @return
	 * @throws Exception
	 */
	public List<RmClusterPo> findAllCluster() throws Exception;
	
	/**
	 * 获取所有集群信息
	 * @return
	 * @throws Exception
	 */
	public List<RmClusterPo> findClusterListByPoolId(String poolId) throws Exception;
	
	/**
	 * 根据集群ID，获得集群下所有的物理机信息
	 * @param clusterId	集群Id
	 * @return
	 * @throws Exception
	 */
	public List<CmDevicePo> findAllHostDevice(String clusterId) throws Exception;
	
	/**
	 * 根据集群ID，获得集群下所有的虚拟机信息
	 * @param clusterId	集群Id
	 * @return
	 * @throws Exception
	 */
	public List<CmDevicePo> findAllVmDevice(String clusterId)throws Exception;
}
