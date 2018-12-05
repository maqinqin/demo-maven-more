package com.git.cloud.resmgt.network.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.network.model.po.RmNwUsePo;
import com.git.cloud.resmgt.network.model.po.RmNwUseRelPo;
import com.git.cloud.resmgt.network.model.vo.RmNwUseRelVo;
import com.git.cloud.resmgt.network.model.vo.RmNwUseVo;
/**
 * @Title 		IRmNwUseService.java 
 * @Package 	com.git.cloud.resmgt.network.service
 * @author 		make
 * @date 		2015-3-6下午14:24:21
 * @version 	1.0.0
 * @Description  
 *
 */
public interface IRmNwUseService {
	/**
	 * 分页方法
	 * @param pagination
	 * @return
	 */
	public Pagination<RmNwUseVo> queryRmNwUsePoPagination(PaginationParam pagination);
	
	/**
	 * 查询IP用途
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryUseNameList() throws Exception;
	
	/**
	 * 查询数据中心
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<Map<String, Object>> queryDatacenterList() throws RollbackableBizException;
	/**
	 * 保存IP用途
	 * @param rmNwUsePo
	 * @throws RollbackableBizException
	 * @throws Exception
	 */
	public void saveRmNwUsePo(RmNwUsePo rmNwUsePo) throws RollbackableBizException, Exception;
	/**
	 * 保存IP用途关系
	 * @param rmNwUseRelPo
	 * @throws RollbackableBizException
	 * @throws Exception
	 */
	public void saveRmNwUseRelPo(RmNwUseRelPo rmNwUseRelPo) throws RollbackableBizException, Exception;
	
	/**
	 * 修改IP用途
	 * @param rmNwUsePo
	 * @throws RollbackableBizException
	 * @throws Exception
	 */
	public void updateRmNwUsePo(RmNwUsePo rmNwUsePo) throws RollbackableBizException, Exception;
	
	/**
	 * 修改IP用途关系
	 * @param rmNwUseRelPo
	 * @throws RollbackableBizException
	 * @throws Exception
	 */
	public void updateRmNwUseRelPo(RmNwUseRelPo rmNwUseRelPo) throws RollbackableBizException, Exception;
	
	/**
	 * 检查数据库是否已经存在当前IP用途
	 * @param rmNwUsePo
	 * @return
	 * @throws RollbackableBizException
	 */
	public boolean checkRmNwUsePo(RmNwUsePo rmNwUsePo)
			throws RollbackableBizException;
	
	/**
	 * 检查数据库是否已经存在当前IP用途关系
	 * @param rmNwUseRelPo
	 * @return
	 * @throws RollbackableBizException
	 */
	public boolean checkRmNwUseRelPo(RmNwUseRelPo rmNwUseRelPo)
			throws RollbackableBizException;
	/**
	 * 根据ID查询用途
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 */
	public RmNwUsePo findRmNwUsePoById(String id) throws RollbackableBizException;
	/**
	 * 根据ID查询用途关系
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 */
	public RmNwUseRelPo findRmNwUseRelPoById(String id) throws RollbackableBizException;
	
	/**
	 * 根据useId查询用途关系（查询IP用途下所有用途关系）
	 * @param useId
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<RmNwUseRelPo> findRmNwUseRelsByUseId(String useId) throws RollbackableBizException;
	
	/**
	 * 删除IP用途
	 * @param ids
	 * @throws RollbackableBizException
	 */
	public String deleteRmNwUsePoById(String[] ids) throws RollbackableBizException;
	
	/**
	 * 删除IP用途关系
	 * @param ids
	 * @throws RollbackableBizException
	 */
	public void deleteRmNwUseRelPoById(String[] ids) throws RollbackableBizException;
	
	/**
	 * 查询IP用途关系分页数据
	 */
	public Pagination<RmNwUseRelVo> queryRmNwUseRelPoPagination(
			PaginationParam pagination) throws Exception;
	
	public List<Map<String, Object>> selectRmHostTypeSel() throws RollbackableBizException;
}
