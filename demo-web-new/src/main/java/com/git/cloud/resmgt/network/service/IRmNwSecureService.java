package com.git.cloud.resmgt.network.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.network.model.po.RmNwSecurePo;
import com.git.cloud.resmgt.network.model.vo.RmNwCclassFullVo;
import com.git.cloud.resmgt.network.model.vo.RmNwResPoolFullVo;
import com.git.cloud.resmgt.network.model.vo.RmNwSecureVo;
/**
 * @Title 		IRmNwResPoolService.java 
 * @Package 	com.git.cloud.resmgt.network.service
 * @author 		sunyp
 * @date 		2014-9-19下午2:24:21
 * @version 	1.0.0
 * @Description  
 *
 */
public interface IRmNwSecureService {

	 public  Pagination<RmNwSecureVo> queryPagination(PaginationParam pagination)throws RollbackableBizException;

	public void saveSecureArea(RmNwSecurePo rmNwSecurePo) throws RollbackableBizException;

	public RmNwSecurePo getSecureAreaById(String id)throws RollbackableBizException;

	public void updateSecureArea(RmNwSecurePo rmNwSecurePo)throws RollbackableBizException;

	public Pagination<RmNwSecureVo> queryPaginationTier(PaginationParam pagination)throws RollbackableBizException;

	public void saveSecureTier(RmNwSecureVo rmNwSecureVo)throws RollbackableBizException;

	public RmNwSecureVo getSecureTierById(String secureTierId)throws RollbackableBizException;

	public void updateSecureTier(RmNwSecureVo rmNwSecureVo)throws RollbackableBizException;

	public void deleteSecureTier(String secureTierId)throws RollbackableBizException;

	public Map<String,String> deleteCheckArea(String[] ids)throws RollbackableBizException;//安全区域删除检查

	public void deleteSecureArea(String[] ids)throws RollbackableBizException;//安全区域验证成功后在进行删除

	public Map<String, String> deleteCheckTierById(String TierId)throws RollbackableBizException;//安全层删除前的验证
	
}
