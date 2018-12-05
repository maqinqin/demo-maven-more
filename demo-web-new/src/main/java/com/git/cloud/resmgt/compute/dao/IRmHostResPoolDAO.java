package com.git.cloud.resmgt.compute.dao;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.resmgt.compute.model.po.RmHostResPoolPo;

public interface IRmHostResPoolDAO extends ICommonDAO{

	RmHostResPoolPo getRmHostPoByAzId(String azId);

}
