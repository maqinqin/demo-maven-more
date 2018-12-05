package com.git.cloud.policy.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.policy.model.po.RmNwRuleListPo;
import com.git.cloud.policy.model.vo.AllocIpParamVo;
import com.git.cloud.resmgt.common.model.bo.CmHostBo;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;

/**
 * @Title 		IRmNwRuleDao.java 
 * @Package 	com.git.cloud.policy.dao 
 * @author 		wxg
 * @date 		2014-9-16上午10:04:02
 * @version 	1.0.0
 * @Description 
 *
 */
public interface IRmNwRuleDao extends ICommonDAO {

	public List<RmNwRuleListPo> findRuleList(AllocIpParamVo param);
	
	List<OpenstackIpAddressPo> findTotalIpListByDeviceIDs(List<String> deviceIDs);
	
	CmHostBo qryIPForHost(String deviceId) throws  RollbackableBizException;
}