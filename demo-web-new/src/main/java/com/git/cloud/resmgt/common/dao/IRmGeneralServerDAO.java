/**
 * @Title:IRmGeneralServerDAO.java
 * @Package:com.git.cloud.resmgt.common.dao
 * @Description:TODO
 * @author LINZI
 * @date 2015-3-9 上午10:24:53
 * @version V1.0
 */
package com.git.cloud.resmgt.common.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.po.RmGeneralServerPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;

/**
 * @ClassName:IRmGeneralServerDAO
 * @Description:TODO
 * @author LINZI
 * @date 2015-3-9 上午10:24:53
 *
 *
 */
public interface IRmGeneralServerDAO {
	/**
	 * 
	 * @Title: queryRmGeneralServerPagination
	 * @Description: 查询通用服务器信息
	 * @field: @param paginationParam
	 * @field: @return
	 * @return Pagination<RmGeneralServerVo>
	 * @throws
	 */
	public Pagination<RmGeneralServerVo> queryRmGeneralServerPagination(PaginationParam paginationParam); 
	/**
	 * 
	 * @Title: queryServerType
	 * @Description: 查询服务器类型
	 * @field: @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> queryServerType() throws RollbackableBizException;
	/**
	 * 
	 * @Title: queryDataCenter
	 * @Description: 查询数据中心
	 * @field: @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> queryDataCenter() ;
	/**
	 * 
	 * @Title: saveRmGeneralServer
	 * @Description: 新增通用服务器
	 * @field: @param rServerPo
	 * @return void
	 * @throws
	 */
	public void saveRmGeneralServer(RmGeneralServerVo rServerVo) throws RollbackableBizException;
	/**
	 * 
	 * @Title: deleteRmGeneralServer
	 * @Description: 删除通用服务器
	 * @field: @param ids
	 * @return void
	 * @throws
	 */
	public void deleteRmGeneralServer(String[] ids) throws RollbackableBizException;
	/**
	 * 
	 * @Title: queryRmGeneralServerById
	 * @Description: 根据id查询通用服务器
	 * @field: @param id
	 * @field: @return
	 * @return RmGeneralServerVo
	 * @throws
	 */
	public RmGeneralServerVo queryRmGeneralServerById(String id) throws RollbackableBizException;
	/**
	 * 
	 * @Title: updateRmGeneralServer
	 * @Description: 更新通用服务器信息
	 * @field: @param rServerPo
	 * @return void
	 * @throws
	 */
	public void updateRmGeneralServer(RmGeneralServerVo rServerVo) throws RollbackableBizException;
	
	public Integer checkServerName(RmGeneralServerVo rServerVo);
	
	public List<RmGeneralServerVo> findRmGeneralServerBydcId(String dcId);
	
	public List<RmGeneralServerVo> findRmGeneralServerByType(String type) throws RollbackableBizException;
}
