package com.git.cloud.resmgt.compute.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.compute.dao.ICmPortGroupDao;
import com.git.cloud.resmgt.compute.model.po.CmPortGroupPo;

public class CmPortGroupDaoImpl extends CommonDAOImpl implements ICmPortGroupDao{

	@Override
	public CmPortGroupPo findCmPortGroupPoByVmHostId(Map<String,String> pMap)
			throws RollbackableBizException {
		List<CmPortGroupPo> list = this.getSqlMapClientTemplate().queryForList("findCmPortGroupPoByVmHostId", pMap);
		if(list.size() > 0){
			return list.get(0);
		}
		else{
			return null;
		}
	}
	@Override
	public CmPortGroupPo getCmPortGroupPoByVmHostId(Map<String, String> pMap) throws RollbackableBizException {
		List<CmPortGroupPo> list = this.getSqlMapClientTemplate().queryForList("listCmPortGroupPoByVmHostId", pMap);
		for (CmPortGroupPo cmPortGroupPo:list) {
			//如果分布式存在分布式交换机，则直接返回，否则
			if (StringUtils.isNotEmpty(cmPortGroupPo.getDvcSwitchHostId())) {
				return cmPortGroupPo;
			}
		}
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
