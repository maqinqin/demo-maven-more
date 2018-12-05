package com.git.cloud.request.service;

import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.request.model.vo.BmSrRrinfoVo;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.model.vo.VirtualExtendVo;
import com.git.cloud.request.model.vo.VirtualSupplyVo;
import com.git.cloud.resmgt.common.model.vo.DeviceInfoVo;

/**
 * 云服务扩容申请接口类
 * @ClassName:IVirtualExtendService
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public interface IVirtualExtendService {
	
	
	public Pagination<BmSrRrinfoVo> queryVEBmSrRrinfoList(PaginationParam paginationParam);
	
	/**
	 * @throws Exception 
	 * 虚拟机扩容资源分配接口
	 * @Title: resourceAssign
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @throws BizException
	 * @return void
	 * @throws
	 */
	public void updateResourceAssign(String srId) throws BizException, Exception;

	public VirtualExtendVo findVirtualExtendById(String srId) throws RollbackableBizException;
	/**
	 * 根据deviceId查询主机名
	 * @Title: getDeviceName
	 * @Description: TODO
	 * @field: @param deviceId
	 * @field: @throws BizException
	 * @return void
	 * @throws
	 */
	public String getDeviceName(String deviceId) throws BizException;
	
	/**
	 * 获取当前资源下的虚拟机
	 * @param paginationParam
	 * @return
	 */
	public Pagination<BmSrRrinfoVo> queryVmSrDeviceinfoList(PaginationParam paginationParam);
	
}
