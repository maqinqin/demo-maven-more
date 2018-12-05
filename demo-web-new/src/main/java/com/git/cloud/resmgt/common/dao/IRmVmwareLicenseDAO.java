package com.git.cloud.resmgt.common.dao;

import com.git.cloud.common.dao.ICommonDAO;

public interface IRmVmwareLicenseDAO  extends ICommonDAO {

	public String findVmwareLincseByHostCPU(int cupNum);
}
