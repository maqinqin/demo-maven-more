package com.git.cloud.request.service;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.model.vo.VirtualRecycleVo;

/**
 * 云服务回收申请接口类
 * @ClassName:IVirtualRecycleService
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public interface IVirtualRecycleService {

	public VirtualRecycleVo getVirtualRecycleVoBySrId(String srId) throws RollbackableBizException, Exception;
}
