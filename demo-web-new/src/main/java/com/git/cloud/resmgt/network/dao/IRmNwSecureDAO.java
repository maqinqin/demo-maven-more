package com.git.cloud.resmgt.network.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.network.model.po.RmNwSecurePo;
import com.git.cloud.resmgt.network.model.vo.RmNwSecureVo;
import com.git.cloud.resmgt.storage.model.vo.StorageDeviceVo;
/**
 * @Title 		IRmNwSecureDAO.java 
 * @Package 	com.git.cloud.resmgt.network.dao
 * @author 		mijia
 * @date 		2015-3-5
 * @version 	1.0.0
 * @Description  
 *
 */
public interface IRmNwSecureDAO extends ICommonDAO {

  public Pagination<RmNwSecureVo> queryPagination(PaginationParam pagination)throws RollbackableBizException;

public void insertSecureArea(RmNwSecurePo rmNwSecurePo)throws RollbackableBizException;

public RmNwSecurePo getSecureAreaById(String id)throws RollbackableBizException;

public void updateSecureArea(RmNwSecurePo rmNwSecurePo)throws RollbackableBizException;

public Pagination<RmNwSecureVo> queryPaginationTier(PaginationParam pagination)throws RollbackableBizException;

public void insertSecureTier(RmNwSecureVo rmNwSecureVo)throws RollbackableBizException;

public RmNwSecureVo getSecureTierById(String secureTierId)throws RollbackableBizException;

public void updateSecureTier(RmNwSecureVo rmNwSecureVo)throws RollbackableBizException;



public void updateSecureTierById(RmNwSecureVo rmNwSecureVo)throws RollbackableBizException;

public List<RmNwSecureVo> checkSecureAreaSon(String waitForCheckId)throws RollbackableBizException;

public List<RmNwSecureVo> checkNetIp(String waitForCheckId)throws RollbackableBizException;

public List<RmNwSecureVo> checkAppDu(String waitForCheckId)throws RollbackableBizException;

public List<RmNwSecureVo> checkResPoll(String waitForCheckId)throws RollbackableBizException;

public void updateSecureAreaById(String[] ids)throws RollbackableBizException;

public List<RmNwSecureVo> checkTierAppDu(String tierId)throws RollbackableBizException;

public List<RmNwSecureVo> checkTierPoll(String tierId)throws RollbackableBizException;
	
	
}
