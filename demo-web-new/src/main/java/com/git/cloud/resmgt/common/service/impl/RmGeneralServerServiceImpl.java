/**
 * @Title:RmGeneralServerServiceImpl.java
 * @Package:com.git.cloud.resmgt.common.service.impl
 * @Description:TODO
 * @author LINZI
 * @date 2015-3-9 上午10:16:11
 * @version V1.0
 */
package com.git.cloud.resmgt.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.request.tools.SrDateUtil;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.git.cloud.resmgt.common.service.IRmGeneralServerService;
import com.git.support.util.PwdUtil;

/**
 * @ClassName:RmGeneralServerServiceImpl
 * @Description:TODO
 * @author LINZI
 * @date 2015-3-9 上午10:16:11
 *
 *
 */
public class RmGeneralServerServiceImpl implements IRmGeneralServerService {

	private IRmGeneralServerDAO rmGeneralServerDAO;
	private ICmPasswordDAO cmPasswordDAO;
	
	public ICmPasswordDAO getCmPasswordDAO() {
		return cmPasswordDAO;
	}

	public void setCmPasswordDAO(ICmPasswordDAO cmPasswordDAO) {
		this.cmPasswordDAO = cmPasswordDAO;
	}

	public IRmGeneralServerDAO getRmGeneralServerDAO() {
		return rmGeneralServerDAO;
	}

	public void setRmGeneralServerDAO(IRmGeneralServerDAO rmGeneralServerDAO) {
		this.rmGeneralServerDAO = rmGeneralServerDAO;
	}

	/* (non-Javadoc)
	 * <p>Title:queryDataCenter</p>
	 * <p>Description:</p>
	 * @return
	 * @see com.git.cloud.resmgt.common.service.IRmGeneralServerService#queryDataCenter()
	 */
	@Override
	public List<Map<String, Object>> queryDataCenter() {
		return rmGeneralServerDAO.queryDataCenter();
	}

	/* (non-Javadoc)
	 * <p>Title:queryRmGeneralServerPagination</p>
	 * <p>Description:</p>
	 * @param paginationParam
	 * @return
	 * @see com.git.cloud.resmgt.common.service.IRmGeneralServerService#queryRmGeneralServerPagination(com.git.cloud.common.support.PaginationParam)
	 */
	@Override
	public Pagination<RmGeneralServerVo> queryRmGeneralServerPagination(PaginationParam paginationParam) {
		return rmGeneralServerDAO.queryRmGeneralServerPagination(paginationParam);
	}

	/* (non-Javadoc)
	 * <p>Title:queryServerType</p>
	 * <p>Description:</p>
	 * @return
	 * @see com.git.cloud.resmgt.common.service.IRmGeneralServerService#queryServerType()
	 */
	@Override
	public List<Map<String, Object>> queryServerType() throws RollbackableBizException {
		return rmGeneralServerDAO.queryServerType();
	}

	/* (non-Javadoc)
	 * <p>Title:saveRmGeneralServer</p>
	 * <p>Description:</p>
	 * @param rServerVo
	 * @see com.git.cloud.resmgt.common.service.IRmGeneralServerService#saveRmGeneralServer(com.git.cloud.resmgt.common.model.po.RmGeneralServerPo)
	 */
	@Override
	public void saveRmGeneralServer(RmGeneralServerVo rServerVo) throws RollbackableBizException {
		CmPasswordPo cmPasswordPo = new CmPasswordPo();
		cmPasswordPo.setId(UUIDGenerator.getUUID());
		cmPasswordPo.setResourceId(rServerVo.getId());
		cmPasswordPo.setUserName(rServerVo.getUserName());
		cmPasswordPo.setPassword(PwdUtil.encryption(rServerVo.getPassword()));
		cmPasswordPo.setLastModifyTime(SrDateUtil.getSrFortime(new Date()));
		cmPasswordDAO.insertCmPassword(cmPasswordPo);
		rmGeneralServerDAO.saveRmGeneralServer(rServerVo);
	}

	/* (non-Javadoc)
	 * <p>Title:updateRmGeneralServer</p>
	 * <p>Description:</p>
	 * @param rServerVo
	 * @see com.git.cloud.resmgt.common.service.IRmGeneralServerService#updateRmGeneralServer(com.git.cloud.resmgt.common.model.po.RmGeneralServerPo)
	 */
	@Override
	public void updateRmGeneralServer(RmGeneralServerVo rServerVo) throws RollbackableBizException {
		rmGeneralServerDAO.updateRmGeneralServer(rServerVo);
		CmPasswordPo cmPasswordPo = cmPasswordDAO.findCmPasswordByResourceId(rServerVo.getId());
		if (cmPasswordPo!=null) {
			cmPasswordPo.setUserName(rServerVo.getUserName());
			if (!rServerVo.getPassword().equals(cmPasswordPo.getPassword())) {	//密码更改后才需要保存
				cmPasswordPo.setPassword(PwdUtil.encryption(rServerVo.getPassword()));
			}
			cmPasswordPo.setLastModifyTime(SrDateUtil.getSrFortime(new Date()));
			cmPasswordDAO.updateCmPassword(cmPasswordPo);
		}else {
			cmPasswordPo = new CmPasswordPo();
			cmPasswordPo.setId(UUIDGenerator.getUUID());
			cmPasswordPo.setResourceId(rServerVo.getId());
			cmPasswordPo.setUserName(rServerVo.getUserName());
			cmPasswordPo.setPassword(PwdUtil.encryption(rServerVo.getPassword()));
			cmPasswordPo.setLastModifyTime(SrDateUtil.getSrFortime(new Date()));
			cmPasswordDAO.insertCmPassword(cmPasswordPo);
		}
		rmGeneralServerDAO.updateRmGeneralServer(rServerVo);
	}

	/* (non-Javadoc)
	 * <p>Title:deleteRmGeneralServer</p>
	 * <p>Description:</p>
	 * @param ids
	 * @see com.git.cloud.resmgt.common.service.IRmGeneralServerService#deleteRmGeneralServer(java.lang.String[])
	 */
	@Override
	public void deleteRmGeneralServer(String[] ids) throws RollbackableBizException {
		rmGeneralServerDAO.deleteRmGeneralServer(ids);
	}

	/* (non-Javadoc)
	 * <p>Title:queryRmGeneralServerById</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.resmgt.common.service.IRmGeneralServerService#queryRmGeneralServerById(java.lang.String)
	 */
	@Override
	public RmGeneralServerVo queryRmGeneralServerById(String id) throws RollbackableBizException {
		return rmGeneralServerDAO.queryRmGeneralServerById(id);
	}

	/* (non-Javadoc)
	 * <p>Title:checkServerName</p>
	 * <p>Description:</p>
	 * @param rServerVo
	 * @return
	 * @see com.git.cloud.resmgt.common.service.IRmGeneralServerService#checkServerName(com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo)
	 */
	@Override
	public boolean checkServerName(RmGeneralServerVo rServerVo) {
		int count = rmGeneralServerDAO.checkServerName(rServerVo);
		if (count>0) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public List<RmGeneralServerVo> findRmGeneralServerByType(String type)
			throws RollbackableBizException {
		return rmGeneralServerDAO.findRmGeneralServerByType(type);
	}

}
