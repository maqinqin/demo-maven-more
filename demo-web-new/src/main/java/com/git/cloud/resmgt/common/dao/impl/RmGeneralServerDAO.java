/**
 * @Title:RmGeneralServerDAO.java
 * @Package:com.git.cloud.resmgt.common.dao.impl
 * @Description:TODO
 * @author LINZI
 * @date 2015-3-9 上午10:55:58
 * @version V1.0
 */
package com.git.cloud.resmgt.common.dao.impl;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;

/**
 * @ClassName:RmGeneralServerDAO
 * @Description:TODO
 * @author LINZI
 * @date 2015-3-9 上午10:55:58
 *
 *
 */
public class RmGeneralServerDAO extends CommonDAOImpl implements IRmGeneralServerDAO {

	/* (non-Javadoc)
	 * <p>Title:deleteRmGeneralServer</p>
	 * <p>Description:</p>
	 * @param ids
	 * @see com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO#deleteRmGeneralServer(java.lang.String[])
	 */
	@Override
	public void deleteRmGeneralServer(String[] ids) throws RollbackableBizException {
		int count = 0;
		for (String id : ids) {
			count += this.deleteForIsActive("deleteGeneralServerById", id);
		}
	}

	/* (non-Javadoc)
	 * <p>Title:queryDataCenter</p>
	 * <p>Description:</p>
	 * @return
	 * @see com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO#queryDataCenter()
	 */
	@Override
	public List<Map<String, Object>> queryDataCenter() {
		return this.commonQuery("queryDataCenter");
	}

	/* (non-Javadoc)
	 * <p>Title:queryRmGeneralServerById</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @see com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO#queryRmGeneralServerById(java.lang.String)
	 */
	@Override
	public RmGeneralServerVo queryRmGeneralServerById(String id) throws RollbackableBizException {
		return this.findObjectByID("queryRmGeneralServerById", id);
	}

	/* (non-Javadoc)
	 * <p>Title:queryRmGeneralServerPagination</p>
	 * <p>Description:</p>
	 * @param paginationParam
	 * @return
	 * @see com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO#queryRmGeneralServerPagination(com.git.cloud.common.support.PaginationParam)
	 */
	@Override
	public Pagination<RmGeneralServerVo> queryRmGeneralServerPagination(PaginationParam paginationParam) {
		return this.pageQuery("queryRmGeneralServerCount", "queryRmGeneralServer", paginationParam);
	}

	/* (non-Javadoc)
	 * <p>Title:queryServerType</p>
	 * <p>Description:</p>
	 * @return
	 * @see com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO#queryServerType()
	 */
	@Override
	public List<Map<String, Object>> queryServerType() throws RollbackableBizException {
		return this.findForList("");
	}

	/* (non-Javadoc)
	 * <p>Title:saveRmGeneralServer</p>
	 * <p>Description:</p>
	 * @param rServerVo
	 * @see com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO#saveRmGeneralServer(com.git.cloud.resmgt.common.model.po.RmGeneralServerPo)
	 */
	@Override
	public void saveRmGeneralServer(RmGeneralServerVo rServerVo) throws RollbackableBizException {
		this.save("insertGeneralServer", rServerVo);
	}

	/* (non-Javadoc)
	 * <p>Title:updateRmGeneralServer</p>
	 * <p>Description:</p>
	 * @param rServerVo
	 * @see com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO#updateRmGeneralServer(com.git.cloud.resmgt.common.model.po.RmGeneralServerPo)
	 */
	@Override
	public void updateRmGeneralServer(RmGeneralServerVo rServerVo) throws RollbackableBizException {
		this.update("updateGeneralServer", rServerVo);
	}

	/* (non-Javadoc)
	 * <p>Title:checkServerName</p>
	 * <p>Description:</p>
	 * @param rServerVo
	 * @return
	 * @see com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO#checkServerName(com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo)
	 */
	@Override
	public Integer checkServerName(RmGeneralServerVo rServerVo) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("checkServerName", rServerVo);
	}
	@Override
	public List<RmGeneralServerVo> findRmGeneralServerBydcId(String dcId){
		return this.getSqlMapClientTemplate().queryForList("queryAutoServerBydcId", dcId);
	}

	@Override
	public List<RmGeneralServerVo> findRmGeneralServerByType(String type) throws RollbackableBizException {
		return this.getSqlMapClientTemplate().queryForList("findRmGeneralServerByType", type);
	}
}
