package com.git.cloud.request.dao;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.model.po.BmSrTypeWfRefPo;

/**
 * 服务申请类别和流程关系
 * @ClassName:IBmSrTypeWfRefDao
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-13 下午4:10:56
 */
public interface IBmSrTypeWfRefDao extends ICommonDAO {
	
	/**
	 * 根据申请类别获取与流程关系
	 * @Title: findBmSrTypeWfRefBySrTypeMark
	 * @Description: TODO
	 * @field: @param srTypeMark
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return BmSrTypeWfRefPo
	 * @throws
	 */
	public BmSrTypeWfRefPo findBmSrTypeWfRefBySrTypeMark(String srTypeMark) throws RollbackableBizException;
}
