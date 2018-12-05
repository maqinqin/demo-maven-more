package com.git.cloud.policy.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.git.cloud.cloudservice.model.HaTypeEnum;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.policy.dao.IRmNwRuleDao;
import com.git.cloud.policy.model.po.RmNwRuleListPo;
import com.git.cloud.policy.model.po.RmVmParamPo;
import com.git.cloud.policy.model.vo.AllocIpParamVo;
import com.git.cloud.resmgt.common.model.bo.CmHostBo;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.po.RmNwCclassFLPo;
import com.git.cloud.resmgt.network.model.po.RmNwCclassPo;
import com.git.cloud.resmgt.network.model.po.RmNwIpAddressPo;

/**
 * @Title 		RmNwRuleDaoImpl.java 
 * @Package 	com.git.cloud.policy.dao.impl 
 * @author 		wxg
 * @date 		2014-9-16上午10:04:47
 * @version 	1.0.0
 * @Description 
 *
 */
@Service
public class RmNwRuleDaoImpl extends CommonDAOImpl implements IRmNwRuleDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<RmNwRuleListPo> findRuleList(AllocIpParamVo param) {
		if (param.getCloudServiceHATypeCode() == null) {
			param.setCloudServiceHATypeCode(HaTypeEnum.SINGLE.toString());
		}
		List<RmNwRuleListPo> nwRuleListPos = this.getSqlMapClientTemplate().queryForList("ippolicy.findRuleList.includeHaType", param);
		if (nwRuleListPos == null || nwRuleListPos.size() <= 0) {
			param.setCloudServiceHATypeCode(null);
			nwRuleListPos = this.getSqlMapClientTemplate().queryForList("ippolicy.findRuleList", param);
		}
		return nwRuleListPos;
	}
	
	@Override
	public List<OpenstackIpAddressPo> findTotalIpListByDeviceIDs(List<String> deviceIDs) {
		StringBuilder builder = new StringBuilder();
		for(String deviceID:deviceIDs){
			builder.append(",'");
			builder.append(deviceID);
			builder.append("'");
		}
		String hostId = builder.toString().replaceFirst(",","");
		return this.getSqlMapClientTemplate().queryForList("findTotalIpList",hostId);
	}

	@Override
	public CmHostBo qryIPForHost(String deviceId) throws RollbackableBizException {
		return super.findObjectByID("qryIPForHost", deviceId);
	}

}

