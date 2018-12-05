package com.git.cloud.resmgt.common.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.vo.VCenterAlarmVo;

/**
 * 报警信息DAO
  * @author WangJingxin
  * @date 2016年5月9日 上午10:08:13
  *
 */
public interface ICmAlarmDAO extends ICommonDAO {
	
	public void insertAlarmInfo(List<VCenterAlarmVo> vo)throws RollbackableBizException;
}
