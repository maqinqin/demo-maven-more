package com.git.cloud.resmgt.compute.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
/**
 * 
 * @author 王成辉
 * @Description 集群维护
 * @date 2014-12-17
 *
 */
public interface IRmClusterService extends IService {
	public void saveCluster(RmClusterPo clusterPo) throws RollbackableBizException, Exception;

	public RmClusterPo findClusterById(String clusterId) throws RollbackableBizException;

	public void updateCluster(RmClusterPo clusterPo) throws RollbackableBizException, Exception;

	public String deleteCluster(String clusterId) throws RollbackableBizException;
	
	public RmClusterPo findRmClusterPoByName(Map<String, String> map) throws RollbackableBizException;
	
	public RmClusterPo findRmClusterPoByEname(String ename) throws RollbackableBizException;
	
	public List<RmClusterPo> findAllCluster() throws RollbackableBizException;
	
	public void saveClusterBySample(RmClusterPo clusterPo) throws RollbackableBizException;
	
	public void deleteClusterListByExistIds(List<String> isExistIds) throws RollbackableBizException;
}
