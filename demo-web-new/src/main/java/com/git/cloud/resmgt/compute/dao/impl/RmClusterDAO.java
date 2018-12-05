package com.git.cloud.resmgt.compute.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.compute.dao.IRmClusterDAO;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
/**
 * 
 * @author 王成辉
 * @Description 集群维护dao
 * @date 2014-12-17
 *
 */
//@Service
@Repository
public class RmClusterDAO extends CommonDAOImpl implements IRmClusterDAO {

	@Override
	public <T extends RmClusterPo> List<T> findListByFieldsAndOrder(
			String method, HashMap<String, String> params) throws RollbackableBizException {
		List<T> list = getSqlMapClientTemplate().queryForList(method, params);
		return list;
	}

	@Override
	public void saveRmClusterPo(RmClusterPo clusterPo)
			throws RollbackableBizException {
		super.save("saveRmClusterPo", clusterPo);
		
	}

	@Override
	public RmClusterPo findRmClusterPoById(String clusterId)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return super.findObjectByID("selectClusterById", clusterId);
	}

	@Override
	public void updateRmClusterPo(RmClusterPo clusterPo)
			throws RollbackableBizException {
		super.update("updateRmClusterPo", clusterPo);
		
	}

	@Override
	public void deleteRmClusterPoById(String clusterId)
			throws RollbackableBizException {
		super.deleteForIsActive("deleteRmClusterPoById", clusterId);
		
	}

	@Override
	public int findCmHostVoByClusterId(String clusterId) throws RollbackableBizException {
		List list=new ArrayList();
		list= getSqlMapClientTemplate().queryForList("selectHostCountByClusterId", clusterId);
		return (Integer) list.get(0);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<RmClusterPo> findClusterByRmResPoolId(String resPoolId) throws RollbackableBizException {
		List<RmClusterPo> list=new ArrayList<RmClusterPo>();
		list= getSqlMapClientTemplate().queryForList("findClusterByRmResPoolId", resPoolId);
		return list;
	}

	@Override
	public RmClusterPo findRmClusterPoByName(Map<String, String> map) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return super.findObjectByMap("selectclusterNamefortrim", map);
	}

	@Override
	public RmClusterPo findRmClusterPoByEname(String ename) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return super.findObjectByID("selectclusterEnamefortrim", ename);
	}

	/*@Override
	public RmClusterPo findRmClusterPoStorageById(String clusterId)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return super.findObjectByID("findRmClusterPoStorageById", clusterId);
	}

	@Override
	public RmClusterPo findRmClusterPoDatastoreTypeById(String clusterId)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		RmClusterPo po = super.findObjectByID("findRmClusterPoDatastoreTypeById", clusterId);
		return po;
	}*/

	@Override
	public List<RmClusterPo> findAllCluster() throws RollbackableBizException {
		// TODO Auto-generated method stub
		HashMap<String,String> ha  = new HashMap<String, String>();
		return  getSqlMapClientTemplate().queryForList("findAllCluster", ha);
	}
	
}
