package com.git.cloud.request.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;

/**
 * 资源申请设备数据层接口
 * @ClassName:IBmSrRrVmRefDao
 * @Description:TODO
 * @author sunhailong
 * @date 2014-9-30 上午10:26:15
 */
public interface IBmSrRrVmRefDao extends ICommonDAO {

	/**
	 * 新建资源设备关系表
	 * @Title: insertBmSrRrVmRef
	 * @Description: TODO
	 * @field: @param rrVmRefList
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void insertBmSrRrVmRef(List<BmSrRrVmRefPo> rrVmRefList) throws RollbackableBizException;
	
	/**
	 * 更新资源设备关系信息，主要更新IsEnough本机资源是否充足字段
	 * @Title: updateBmSrRrVmRef
	 * @Description: TODO
	 * @field: @param vmRef
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void updateBmSrRrVmRef(BmSrRrVmRefPo vmRef) throws RollbackableBizException;
	
	/**
	 * 根据服务申请Id删除资源设备信息
	 * @Title: deleteBmSrRrVmRefBySrId
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void deleteBmSrRrVmRefBySrId(String srId) throws RollbackableBizException;
	
	/**
	 * 根据资源申请Id删除资源设备信息
	 * @Title: deleteBmSrRrVmRefByRrinfoId
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void deleteBmSrRrVmRefByRrinfoId(String rrinfoId) throws RollbackableBizException;
	
	/**
	 * 根据资源请求Id获取资源申请下的机器关系
	 * @Title: findBmSrRrVmRefListByRrinfoId
	 * @Description: TODO
	 * @field: @param rrinfoId
	 * @field: @return
	 * @return List<BmSrRrVmRefPo>
	 * @throws
	 */
	public List<BmSrRrVmRefPo> findBmSrRrVmRefListByRrinfoId(String rrinfoId) throws RollbackableBizException;
	
	/**
	 * 根据服务请求Id获取资源申请下的机器关系
	 * @Title: findBmSrRrVmRefListBySrId
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @return
	 * @return List<BmSrRrVmRefPo>
	 * @throws
	 */
	public List<BmSrRrVmRefPo> findBmSrRrVmRefListBySrId(String srId) throws RollbackableBizException;
	
	/**
	 * 根据资源申请Id和设备Id获取对象
	 * @Title: findBmSrRrVmRefByRrinfoIdAndVmId
	 * @Description: TODO
	 * @field: @param rrinfoId
	 * @field: @param deviceId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return BmSrRrVmRefPo
	 * @throws
	 */
	public BmSrRrVmRefPo findBmSrRrVmRefByRrinfoIdAndVmId(String rrinfoId, String deviceId) throws RollbackableBizException;
	
	/**
	 * 通过设备ID，查询设备对应的卷名称
	 * @param deviceId 设备ID
	 * @return
	 * @throws RollbackableBizException
	 */
	public BmSrRrVmRefPo findBmSrRrVmVolumeTypeByDeviceId(String deviceId)throws RollbackableBizException;
}
