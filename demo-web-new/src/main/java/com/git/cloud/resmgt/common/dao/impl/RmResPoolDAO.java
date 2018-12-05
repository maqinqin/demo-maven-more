package com.git.cloud.resmgt.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.dao.IRmResPoolDAO;
import com.git.cloud.resmgt.common.model.po.RmResPoolPo;
import com.git.cloud.resmgt.common.model.vo.RmResPoolVo;
import com.git.cloud.resmgt.network.model.vo.RmNwResPoolFullVo;

public class RmResPoolDAO extends CommonDAOImpl implements IRmResPoolDAO {

	@Override
	public <T extends RmResPoolPo> List<T> findListByFieldsAndOrder(
			String method, HashMap<String, String> params) throws RollbackableBizException {
		
		List<T> list = getSqlMapClientTemplate().queryForList(method, params);
		return list;
	}
	
	public <T extends RmNwResPoolFullVo> List<T> findListById(
			String method, HashMap<String, String> params) throws RollbackableBizException {
		
		List<T> list = getSqlMapClientTemplate().queryForList(method, params);
		return list;
	}
	
	@Override
	public RmResPoolVo findRmResPoolVoById(String id)
			throws RollbackableBizException {
		RmResPoolVo rmResPoolVo = super.findObjectByID("findRmResPoolVoById", id);
		return rmResPoolVo;
	}

	@Override
	public void saveRmResPoolVo(RmResPoolVo rmResPoolVo)
			throws RollbackableBizException {

		super.save("saveRmResPoolVo", rmResPoolVo);
		super.save("saveRmHostResPoolPo", rmResPoolVo);
	}

}
