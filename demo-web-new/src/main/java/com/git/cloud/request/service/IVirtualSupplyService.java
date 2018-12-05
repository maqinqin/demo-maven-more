package com.git.cloud.request.service;

import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.model.vo.VirtualSupplyVo;
/**
 * 云服务供给申请接口类
 * @ClassName:IVirtualSupplyService
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public interface IVirtualSupplyService {
	
	public VirtualSupplyVo findVirtualSupplyById(String srId) throws RollbackableBizException;
	/**
	 * 虚拟机供给资源分配接口
	 * @param srId
	 */
	public String doUpdateResourceAssignNew(String srId) throws BizException;
	
	public void doUpdateResourceRecycle(String srId, String rrinfoId) throws RollbackableBizException, Exception;
	
}