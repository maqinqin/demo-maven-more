package com.git.cloud.resmgt.common.dao;


import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.RmHostTypePo;

public interface IRmHostTypeDAO extends ICommonDAO{
	/**
	 * 通过host_type_id查询机器类型（H:物理机，V：虚拟机）
	 * @param hostTypeId
	 * @return
	 * @throws Exception
	 * @author wangmingyue
	 */
	public RmHostTypePo getRmHostTypeById(String hostTypeId)throws RollbackableBizException;
}
