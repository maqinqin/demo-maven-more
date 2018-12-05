package com.git.cloud.resmgt.compute.dao;

import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.compute.model.po.CmPortGroupPo;

public interface ICmPortGroupDao {
	/**
	 * 通过物理机id和vlanid，查询端口组下的网络标签（若vlanid相同，默认将虚机接在标签中含有虚机较少的那个端口组下）
	 * @param pMap
	 * @return
	 * @throws RollbackableBizException
	 */
	public CmPortGroupPo findCmPortGroupPoByVmHostId(Map<String,String> pMap)throws RollbackableBizException;
	
	CmPortGroupPo getCmPortGroupPoByVmHostId(Map<String,String> pMap)
			throws RollbackableBizException;
}
