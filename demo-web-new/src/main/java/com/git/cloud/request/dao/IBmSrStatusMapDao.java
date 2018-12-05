package com.git.cloud.request.dao;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.request.model.po.BmSrStatusMapPo;

public interface IBmSrStatusMapDao extends ICommonDAO {
	
	/**
	 * 根据服务申请Id和流程节点名称获取服务申请状态映射的对象
	 * @param srId
	 * @param nodeName
	 * @return
	 */
	public BmSrStatusMapPo findBmSrStatusMap(String srId, String nodeName);
}
