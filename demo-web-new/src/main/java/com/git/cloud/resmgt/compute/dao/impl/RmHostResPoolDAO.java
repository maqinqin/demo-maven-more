package com.git.cloud.resmgt.compute.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.resmgt.common.model.po.RmHostResPo;
import com.git.cloud.resmgt.compute.dao.IRmHostResPoolDAO;

import com.git.cloud.resmgt.compute.model.po.RmHostResPoolPo;
//@Service
@Repository
public class RmHostResPoolDAO extends CommonDAOImpl implements IRmHostResPoolDAO {
	@Override
	public RmHostResPoolPo getRmHostPoByAzId(String azId){
		RmHostResPoolPo po = (RmHostResPoolPo) getSqlMapClientTemplate().queryForObject("getRmHostPoByAzId",azId);
		return po;
	}

}
