package com.git.cloud.resmgt.common.dao.impl;

import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.dao.ICmAlarmDAO;
import com.git.cloud.resmgt.common.model.vo.VCenterAlarmVo;

public class CmAlarmDAOImpl extends CommonDAOImpl implements ICmAlarmDAO {

	@Override
	public void insertAlarmInfo(List<VCenterAlarmVo> voList) throws RollbackableBizException {
		this.batchInsert("insertCmAlarm", voList);
	}

}
