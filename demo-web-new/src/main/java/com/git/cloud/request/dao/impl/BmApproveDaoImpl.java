package com.git.cloud.request.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.dao.IBmApproveDao;
import com.git.cloud.request.model.vo.BmApproveVo;

/**
 * 云服务审批数据层实现类
 * @ClassName:BmApproveDaoImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class BmApproveDaoImpl extends CommonDAOImpl implements IBmApproveDao {
	public List<BmApproveVo> findApproveResult(String srId) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("srId", srId);
		return this.findListByParam("findApproveResult", paramMap);
	}
}
