package com.git.cloud.resmgt.common.dao.impl;

import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.excel.model.vo.DataStoreVo;
import com.git.cloud.resmgt.common.dao.ICmLocalDiskDAO;
import com.git.cloud.resmgt.common.model.po.CmLocalDiskPo;

public class CmLocalDiskDAO extends CommonDAOImpl implements ICmLocalDiskDAO{

	@Override
	public List<CmLocalDiskPo> findLocalDiskListByHostId(String hostId) {
		return this.getSqlMapClientTemplate().queryForList("findLocalDiskListByHostId",hostId);
	}

	@Override
	public void saveLocalDisk(DataStoreVo vo) throws RollbackableBizException {
		this.getSqlMapClientTemplate().insert("saveLocalDisk", vo);
	}

	@Override
	public void deleteLocalDiskList(List<? extends BaseBO> localDiskList) throws RollbackableBizException {
		this.getSqlMapClientTemplate().delete("deleteLocalDiskList", localDiskList);
	}

}

