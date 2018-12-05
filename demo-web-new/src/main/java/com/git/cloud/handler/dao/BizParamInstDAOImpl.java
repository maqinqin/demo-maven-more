package com.git.cloud.handler.dao;

import java.util.HashMap;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.handler.po.BizParamInstPo;

public class BizParamInstDAOImpl extends CommonDAOImpl implements BizParamInstDAO {
	
	@Override
	public Pagination<BizParamInstPo> findBizParamInstPage(
			PaginationParam paginationParam) throws RollbackableBizException {
		return this.pageQuery("bizParamInstTotal","bizParamInstPage", paginationParam);
	}

	@Override
	public void updateParamValue(BizParamInstPo bizParamInstPo)
			throws RollbackableBizException {
		this.update("updateParamValue", bizParamInstPo);
	}

	@Override
	public BizParamInstPo getBizParamInstById(String id) throws RollbackableBizException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		return this.findObjectByMap("getBizParamInstById", map);
		
	}
}
