package com.git.cloud.resmgt.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.RmResPoolPo;
import com.git.cloud.resmgt.common.model.vo.RmResPoolVo;
import com.git.cloud.resmgt.network.model.po.RmNwConvergePo;
import com.git.cloud.resmgt.network.model.vo.RmNwResPoolFullVo;

public interface IRmResPoolDAO extends ICommonDAO{
	public RmResPoolVo findRmResPoolVoById(String id) throws RollbackableBizException;
	public void saveRmResPoolVo(RmResPoolVo rmResPoolVo) throws RollbackableBizException;
	
	public <T extends RmResPoolPo> List<T> findListByFieldsAndOrder(
			String method, HashMap<String, String> params) throws RollbackableBizException ;
	}
