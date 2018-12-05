package com.git.cloud.resmgt.common.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.excel.model.vo.DataStoreVo;
import com.git.cloud.resmgt.common.model.po.CmLocalDiskPo;

public interface ICmLocalDiskDAO extends ICommonDAO{
	public List<CmLocalDiskPo> findLocalDiskListByHostId(String hostId);
	
	public void saveLocalDisk(DataStoreVo vo)  throws RollbackableBizException;
	
	public void deleteLocalDiskList(List<? extends BaseBO> localDiskList)  throws RollbackableBizException;
}
