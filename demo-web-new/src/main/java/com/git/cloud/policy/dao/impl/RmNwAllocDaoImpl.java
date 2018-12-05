/**
 * @Title:RmNwAllocDaoImpl.java
 * @Package:com.git.cloud.policy.dao.impl
 * @Description:TODO
 * @author zhuzy
 * @date 2015-10-9 下午4:44:20
 * @version V1.0
 */
package com.git.cloud.policy.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.policy.dao.IRmNwAllocDao;
import com.git.cloud.policy.model.vo.AllocIpParamVo;
import com.git.cloud.resmgt.network.model.po.RmNwIpAddressPo;
import com.git.cloud.resmgt.network.model.po.RmNwResPoolPo;

/**
 * @ClassName:RmNwAllocDaoImpl
 * @Description:TODO
 * @author zhuzy
 * @date 2015-10-9 下午4:44:20
 *
 *
 */
@Service
public class RmNwAllocDaoImpl extends CommonDAOImpl implements IRmNwAllocDao {

	/* (non-Javadoc)
	 * <p>Title:findNwResPool</p>
	 * <p>Description:</p>
	 * @param param
	 * @return
	 * @throws Exception
	 * @see com.git.cloud.policy.dao.IRmNwAllocDao#findNwResPool(com.git.cloud.policy.model.vo.AllocIpParamVo)
	 */
	@Override
	public List<RmNwResPoolPo> findNwResPool(AllocIpParamVo param) throws Exception {
		@SuppressWarnings("unchecked")
		List<RmNwResPoolPo> resPoolPos = this.getSqlMapClientTemplate().queryForList("findRmNwResPoolPoListByAllocIpParamVoSQL", param);
		return resPoolPos;
	}

	/* (non-Javadoc)
	 * <p>Title:findIpAddr</p>
	 * <p>Description:</p>
	 * @param nwResPoolId
	 * @param allocedStatusCode
	 * @return
	 * @throws Exception
	 * @see com.git.cloud.policy.dao.IRmNwAllocDao#findIpAddr(java.lang.String, java.lang.String)
	 */
	@Override
	public List<RmNwIpAddressPo> findIpAddr(String nwResPoolId, String allocedStatusCode) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("nwResPoolId", nwResPoolId);
		param.put("allocedStatusCode", allocedStatusCode);
		@SuppressWarnings("unchecked")
		List<RmNwIpAddressPo> ipAddressPos = this.getSqlMapClientTemplate().queryForList("selectIpAddressListByNwResPoolId", param);
		return ipAddressPos;
	}
	
	@Override
	public List<RmNwIpAddressPo> selectIpAddressListByUserCode(String projectId, String allocedStatusCode) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("projectId", projectId);
		param.put("allocedStatusCode", allocedStatusCode);
		@SuppressWarnings("unchecked")
		List<RmNwIpAddressPo> ipAddressPos = this.getSqlMapClientTemplate().queryForList("selectIpAddressListByUserCode", param);
		return ipAddressPos;
	}

}
