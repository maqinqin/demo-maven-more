package com.git.cloud.policy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.policy.dao.IRmVmParamDao;
import com.git.cloud.policy.model.ObjectTypeEnum;
import com.git.cloud.policy.model.po.RmVmParamPo;
import com.git.cloud.policy.model.vo.RmVmParamVo;
import com.git.cloud.policy.service.IRmVmParamService;
import com.git.cloud.taglib.util.Internation;

/**
 * @Title 		RmVmParamServiceImpl.java 
 * @Package 	com.git.cloud.policy.service.impl 
 * @author 		yangzhenhai
 * @date 		2014-9-11下午4:41:35
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmVmParamServiceImpl implements IRmVmParamService {

	private IRmVmParamDao rmVmParamDao;
	
	/**
	 * rmVmParamDao
	 *
	 * @return  the rmVmParamDao
	 * @since   1.0.0
	 */
	public IRmVmParamDao getRmVmParamDao() {
		return rmVmParamDao;
	}

	/**
	 * @param rmVmParamDao the rmVmParamDao to set
	 */
	public void setRmVmParamDao(IRmVmParamDao rmVmParamDao) {
		this.rmVmParamDao = rmVmParamDao;
	}

	@Override
	public  void saveRmVmParamPo(RmVmParamPo rmVmParamPo) throws Exception {
		if(this.checkRmVmParamPo(rmVmParamPo)){
			rmVmParamPo.setParamId(UUIDGenerator.getUUID());
			rmVmParamPo.setIsActive(IsActiveEnum.YES.getValue());
			rmVmParamDao.saveRmVmParamPo( rmVmParamPo);
		}else{
			throw new Exception(Internation.language("this_para_exit_d"));
		}
		
	}

	@Override
	public void updateRmVmParamPo(RmVmParamPo rmVmParamPo) throws Exception {
		if(this.checkRmVmParamPo(rmVmParamPo)){
			rmVmParamDao.updateRmVmParamPo(rmVmParamPo);
		}else{
			throw new Exception(Internation.language("this_para_exit_d"));
		}
	}

	@Override
	public RmVmParamPo findRmVmParamPoById(String id) throws RollbackableBizException {

		RmVmParamPo rmVmParamPo = rmVmParamDao.findRmVmParamPoById( id);
		
		return rmVmParamPo;
	}

	@Override
	public void deleteRmVmParamPoById(String[] ids) throws RollbackableBizException {
		
		for(String id : ids){
			rmVmParamDao.deleteRmVmParamPoById(id);
		}
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.policy.service.IRmVmParamService#getCloudReqeustPagination(com.git.cloud.common.support.PaginationParam)
	 */
	@Override
	public Pagination<RmVmParamVo> queryRmVmParamPoPagination(
			PaginationParam pagination) {
		
		return rmVmParamDao.queryRmVmParamPoPagination(pagination);
	}

	/**
	 * @Description:findParamValue 读取cdp的分配参数，如果不存在本cdp的参数则向上层读取，直到全局参数
	 * @param poolId
	 * @param cdpId
	 * @param string
	 * @return
	*/
	@Override
	public String findParamValue(String poolId, String cdpId, String paramType ) {
		String value = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.hasText(cdpId)){
			map.put("objectType", ObjectTypeEnum.CDP);
			map.put("objectId", cdpId);
			map.put("paramType", paramType);
			value = rmVmParamDao.findParamValue(map);
			if(StringUtils.hasText(value)){
				return value;
			}
		}
		
		if(StringUtils.hasText(poolId)){
			map.put("objectType", ObjectTypeEnum.POOL);
			map.put("objectId", poolId);
			map.put("paramType", paramType);
			value = rmVmParamDao.findParamValue(map);
			if(StringUtils.hasText(value)){
				return value;
			}
		}

		map.clear();
		map.put("objectType", ObjectTypeEnum.GLOBAL);
		map.remove("objectId");
		map.put("paramType", paramType);
		value = rmVmParamDao.findParamValue(map);
		return value;
	}

	@Override
	public boolean checkRmVmParamPo(RmVmParamPo rmVmParamPo)
			throws RollbackableBizException {

		int count = rmVmParamDao.findRmVmParamPosByParam(rmVmParamPo);
		if(count > 0){
			return false;
		}else{
			return true;
		}
	}

	/* (non-Javadoc)
	 * <p>Title:queryPoolList</p>
	 * <p>Description:</p>
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.policy.service.IRmVmParamService#queryPoolList()
	 */
	@Override
	public List<Map<String, Object>> queryPoolList() throws RollbackableBizException {
		return rmVmParamDao.queryPoolList();
	}
}
