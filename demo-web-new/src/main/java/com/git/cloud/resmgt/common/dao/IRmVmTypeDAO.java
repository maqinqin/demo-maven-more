package com.git.cloud.resmgt.common.dao;

import java.util.HashMap;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;

public interface IRmVmTypeDAO  extends ICommonDAO {
	
	public RmVirtualTypePo findRmVirtualTypeInfo(String method,HashMap<String,String> params) throws RollbackableBizException;
}
