package com.git.cloud.resmgt.network.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.network.model.po.RmNwUsePo;
import com.git.cloud.resmgt.network.model.po.RmNwUseRelPo;
import com.git.cloud.resmgt.network.model.vo.RmNwUseRelVo;
import com.git.cloud.resmgt.network.model.vo.RmNwUseVo;
/**
 * @Title 		IRmNwUseDAO.java 
 * @Package 	com.git.cloud.resmgt.network.dao
 * @author 		make
 * @date 		2015-3-6上午10:55:21
 * @version 	1.0.0
 * @Description  
 */
public interface IRmNwUseDAO extends ICommonDAO {
	public Pagination<RmNwUseVo> queryRmNwUsePoPagination(PaginationParam pagination);
	
	public List<Map<String, Object>> queryUseNameList() throws RollbackableBizException;
	
	public List<Map<String, Object>> queryDatacenterList() throws RollbackableBizException;
	
	public Integer findRmNwUsePosByUse(RmNwUsePo rmNwUsePo)
			throws RollbackableBizException;
	
	public Integer findRmNwUseRelPosByUseRel(RmNwUseRelPo rmNwUseRelPo)
			throws RollbackableBizException;
	
	public  void saveRmNwUseRelPo(RmNwUseRelPo rmNwUseRelPo) throws RollbackableBizException;
	
	public  void saveRmNwUsePo(RmNwUsePo rmNwUsePo) throws RollbackableBizException;

	public  void updateRmNwUsePo(RmNwUsePo rmNwUsePo) throws RollbackableBizException;
	
	public  void updateRmNwUseRelPo(RmNwUseRelPo rmNwUseRelPo) throws RollbackableBizException;

	public RmNwUsePo findRmNwUsePoById(String id) throws RollbackableBizException;
	
	public RmNwUseRelPo findRmNwUseRelPoById(String id) throws RollbackableBizException;
	
	public List<RmNwUseRelPo> findRmNwUseRelsByUseId(String useId) throws RollbackableBizException;
	
	public void deleteRmNwUsePoById(String id) throws RollbackableBizException;
	
	public void deleteRmNwUseRelPoById(String id) throws RollbackableBizException;
	
	public String findOccupiedUseNamePoById(String id) throws RollbackableBizException;
	
	public Pagination<RmNwUseRelVo> queryRmNwUseRelPoPagination(
			PaginationParam pagination) throws Exception;
	
	public List<Map<String, Object>> selectRmHostTypeSel() throws RollbackableBizException;
}
