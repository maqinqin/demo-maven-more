package com.git.cloud.resmgt.compute.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
/**
 * 
 * @author 王成辉
 * @Description 集群维护dao
 * @date 2014-12-17
 *
 */
public interface IRmClusterDAO extends ICommonDAO{
	public <T extends RmClusterPo> List<T> findListByFieldsAndOrder(
			String method, HashMap<String, String> params) throws RollbackableBizException ;
	
	public void saveRmClusterPo(RmClusterPo clusterPo) throws RollbackableBizException;

	public RmClusterPo findRmClusterPoById(String clusterId) throws RollbackableBizException;

	public void updateRmClusterPo(RmClusterPo clusterPo) throws RollbackableBizException;

	public void deleteRmClusterPoById(String clusterId) throws RollbackableBizException;
	
	public int findCmHostVoByClusterId(String clusterId) throws RollbackableBizException;
	
	public RmClusterPo findRmClusterPoByName(Map<String, String> map) throws RollbackableBizException;
	
	public RmClusterPo findRmClusterPoByEname(String ename) throws RollbackableBizException;
	
	public List<RmClusterPo> findAllCluster() throws RollbackableBizException;

	List<RmClusterPo> findClusterByRmResPoolId(String resPoolId) throws RollbackableBizException;
	
}
