package com.git.cloud.request.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.model.vo.BmSrAttrValVo;

/**
 * 云服务参数数据层接口
 * @ClassName:IBmSrAttrValDao
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public interface IBmSrAttrValDao extends ICommonDAO {
	/**
	 * 新建服务参数
	 * @Title: insertBmSrAttrList
	 * @Description: TODO
	 * @field: @param attrList
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void insertBmSrAttrList(List<BmSrAttrValVo> attrList) throws RollbackableBizException;
	/**
	 * 根据资源请求Id删除服务参数
	 * @Title: deleteBmSrAttrByRrinfoId
	 * @Description: TODO
	 * @field: @param rrinfoId
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void deleteBmSrAttrByRrinfoId(String rrinfoId) throws RollbackableBizException;
	
	/**
	 * 根据资源申请Id获取服务参数
	 * @Title: findBmSrAttrValListByRrinfoId
	 * @Description: TODO
	 * @field: @param rrinfoId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<BmSrAttrValVo>
	 * @throws
	 */
	public List<BmSrAttrValVo> findBmSrAttrValListByRrinfoId(String rrinfoId) throws RollbackableBizException;
	
	public List<BmSrAttrValVo> findBmSrDeviceAttrValListByRrinfoId(String rrinfoId) throws RollbackableBizException;

	/**
	 * @Title: findBmSrNotVisibleAttrValListByRrinfoId
	 * @Description: TODO
	 * @field: @param rrinfoId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<BmSrAttrValVo>
	 * @throws
	 */
	List<BmSrAttrValVo> findBmSrNotVisibleAttrValListByRrinfoId(String rrinfoId) throws RollbackableBizException;
	
	/**
	 * 根据资源申请ID和设备ID获取服务申请参数
	 * @param rrinfoId
	 * @param deviceId
	 * @return
	 */
	public List<BmSrAttrValVo> findBmSrAttrAutoListByDeviceId(String rrinfoId, String deviceId);
	List<BmSrAttrValVo> haveFloatingIp(String rrinfoId, String serviceId);
}
