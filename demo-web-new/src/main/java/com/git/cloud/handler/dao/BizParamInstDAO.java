package com.git.cloud.handler.dao;

import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.handler.po.BizParamInstPo;

public interface BizParamInstDAO extends ICommonDAO {
	public Pagination<BizParamInstPo> findBizParamInstPage(PaginationParam paginationParam) throws RollbackableBizException;
	public void updateParamValue(BizParamInstPo bizParamInstPo) throws RollbackableBizException;
	public BizParamInstPo getBizParamInstById(String id)throws RollbackableBizException;
}
