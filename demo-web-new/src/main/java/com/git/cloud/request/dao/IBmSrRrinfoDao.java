package com.git.cloud.request.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.handler.automation.se.po.CmLun;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.request.model.vo.BmSrRrinfoVo;
import com.gitcloud.tankflow.model.po.BpmInstancePo;

/**
 * 资源申请数据层接口
 * @ClassName:IBmSrRrinfoDao
 * @Description:TODO
 * @author sunhailong
 * @date 2014-9-30 上午10:26:15
 */
public interface IBmSrRrinfoDao extends ICommonDAO {
	
	/**
	 * 新建资源申请
	 * @Title: insertBmSrRrinfo
	 * @Description: TODO
	 * @field: @param rrinfoVo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void insertBmSrRrinfo(BmSrRrinfoVo rrinfoVo) throws RollbackableBizException;
	
	/**
	 * 根据资源申请Id删除资源申请信息
	 * @Title: deleteBmSrRrinfoById
	 * @Description: TODO
	 * @field: @param rrinfoId
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void deleteBmSrRrinfoById(String rrinfoId) throws RollbackableBizException;
	
	/**
	 * 根据服务申请Id删除资源申请信息
	 * @Title: deleteBmSrRrinfoBySrId
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void deleteBmSrRrinfoBySrId(String srId) throws RollbackableBizException;
	
	/**
	 * 根据Id获取资源申请信息
	 * @Title: findBmSrRrinfoById
	 * @Description: TODO
	 * @field: @param rrinfoId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return BmSrRrinfoPo
	 * @throws
	 */
	public BmSrRrinfoPo findBmSrRrinfoById(String rrinfoId) throws RollbackableBizException;
	
	/**
	 * 根据服务申请Id获取资源申请列表
	 * @Title: findBmSrRrinfoListBySrId
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<BmSrRrinfoVo>
	 * @throws
	 */
	public List<BmSrRrinfoVo> findBmSrRrinfoListBySrId(String srId) throws RollbackableBizException;
	
	/**
	 * 根据服务申请Id获取未分配的资源申请信息
	 * @Title: findNoAssignRrinfoListBySrId
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<BmSrRrinfoPo>
	 * @throws
	 */
	public List<BmSrRrinfoPo> findNoAssignRrinfoListBySrId(String srId) throws RollbackableBizException;
	
	/**
	 * 根据服务申请Id查询有服务参数的资源申请Id
	 * @param srId
	 * @return
	 * @throws RollbackableBizException
	 */
	public String getHasAttrRrinfoIdBySrId(String srId) throws RollbackableBizException;
	
	/**
	 * 根据资源申请ID获取LUN信息
	 * @param rrinfoId
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<CmLun> getLunListByRrinfoId(String rrinfoId) throws RollbackableBizException;
	
	/**
	 * 根据服务请求ID获取资源请求信息
	 * @param srId
	 * @return
	 */
	List<BmSrRrinfoPo> findBmSrRrinfoBySrId(String srId);
}