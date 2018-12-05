/**
 * @Title:IRmNwAllocDao.java
 * @Package:com.git.cloud.policy.dao
 * @Description:TODO
 * @author zhuzy
 * @date 2015-10-9 下午4:38:52
 * @version V1.0
 */
package com.git.cloud.policy.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.policy.model.vo.AllocIpParamVo;
import com.git.cloud.resmgt.network.model.po.RmNwCclassPo;
import com.git.cloud.resmgt.network.model.po.RmNwIpAddressPo;
import com.git.cloud.resmgt.network.model.po.RmNwResPoolPo;

/**
 * @ClassName:IRmNwAllocDao
 * @Description: ip分配和查询dao
 * @author zhuzy
 * @date 2015-10-9 下午4:38:52
 *
 *
 */
public interface IRmNwAllocDao extends ICommonDAO {

	/**
	 * 查询可以提供分配的ip地址资源池
	 * @Title: findNwResPool
	 * @Description: TODO
	 * @field: @param param
	 * @field: @return
	 * @field: @throws Exception
	 * @return List<RmNwCclassPo>
	 * @throws
	 */
	public List<RmNwResPoolPo> findNwResPool(AllocIpParamVo param) throws Exception;
	
	/**
	 * 查询ip地址列表
	 * @Title: findIpAddr
	 * @Description: TODO
	 * @field: @param nwResPoolId
	 * @field: @param allocedStatusCode
	 * @field: @return
	 * @field: @throws Exception
	 * @return List<RmNwIpAddressPo>
	 * @throws
	 */
	public List<RmNwIpAddressPo> findIpAddr(String nwResPoolId, String allocedStatusCode) throws Exception;

	List<RmNwIpAddressPo> selectIpAddressListByUserCode(String useCode, String allocedStatusCode) throws Exception;
}
