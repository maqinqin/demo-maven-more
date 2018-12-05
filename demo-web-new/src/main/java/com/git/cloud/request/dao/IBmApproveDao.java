package com.git.cloud.request.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.model.vo.BmApproveVo;

/**
 * 云服务审批数据层接口
 * @ClassName:IBmApproveDao
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public interface IBmApproveDao extends ICommonDAO {
	/**
	 * 根据srId获取审批结果
	 * @param srId
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<BmApproveVo> findApproveResult(String srId) throws RollbackableBizException;
}
