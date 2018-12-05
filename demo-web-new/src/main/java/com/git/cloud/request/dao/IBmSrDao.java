package com.git.cloud.request.dao;


import java.util.List;


import com.git.cloud.appmgt.model.po.AppStatPo;
import com.git.cloud.appmgt.model.vo.AppStatVo;
import com.git.cloud.appmgt.model.vo.AppSysKpiVo;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.model.vo.BmToDoVo;


/**
 * 服务申请数据层接口
 * @ClassName:IBmSrDao
 * @Description:TODO
 * @author sunhailong
 * @date 2014-9-30 上午10:02:47
 */
public interface IBmSrDao extends ICommonDAO {
	
	/**
	 * 新建服务申请
	 * @Title: insertBmSr
	 * @Description: TODO
	 * @field: @param bmSrVo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void insertBmSr(BmSrVo bmSrVo) throws RollbackableBizException;
	
	/**
	 * 更新服务申请
	 * @Title: insertBmSr
	 * @Description: TODO
	 * @field: @param bmSrVo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void updateBmSr(BmSrVo bmSrVo) throws RollbackableBizException;
	
	/**
	 * 更新服务申请状态
	 * @Title: updateBmSrStatus
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @param statusCode
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void updateBmSrStatus(String srId, String srStatusCode) throws RollbackableBizException;
	
	/**
	 * 更新分配结果
	 * @Title: updateAssignResult
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @param assignResult
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void updateAssignResult(String srId, String assignResult) throws RollbackableBizException;
	
	/**
	 * 根据Id获取服务申请信息
	 * @Title: findBmSrVoById
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return BmSrVo
	 * @throws
	 */
	public BmSrVo findBmSrVoById(String srId) throws RollbackableBizException;
	
	/**
	 * 根据服务单号获取服务申请Id
	 * @Title: findBmSrVoBySrCode
	 * @Description: TODO
	 * @field: @param srCode
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return BmSrVo
	 * @throws
	 */
	public BmSrVo findBmSrVoBySrCode(String srCode) throws RollbackableBizException;
	
	/**
	 * 获取最新完成的服务申请
	 * @Title: findNewestCompleteRequest
	 * @Description: TODO
	 * @field: @param num
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<BmSrVo>
	 * @throws
	 */
	public List<BmSrVo> findNewestCompleteRequest(int num) throws RollbackableBizException;
	
	/**
	 * 我最新创建的服务申请
	 * @Title: findNewestCreateRequest
	 * @Description: TODO
	 * @field: @param num
	 * @field: @param creatorId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<BmSrVo>
	 * @throws
	 */
	public List<BmSrVo> findNewestCreateRequest(int num, String creatorId) throws RollbackableBizException;
	
	/**
	 * 我最新的待办
	 * @Title: findNewestWaitDealRequest
	 * @Description: TODO
	 * @field: @param num
	 * @field: @param creatorId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<BmSrVo>
	 * @throws
	 */
	public List<BmToDoVo> findNewestWaitDealRequest(int num, String creatorId, String roleIds) throws RollbackableBizException;
	
	/**
	 * 获取应用系统的虚拟服务器指标
	 * @Title: findAppSysVirtualServer
	 * @Description: TODO
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<AppSysKpiVo>
	 * @throws
	 */
	public List<AppSysKpiVo> findAppSysVirtualServer() throws RollbackableBizException;
	
	/**
	 * 获取应用系统月完成的服务申请指标
	 * @Title: findAppSysCompleteRequest
	 * @Description: TODO
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<AppSysKpiVo>
	 * @throws
	 */
	public List<AppSysKpiVo> findAppSysCompleteRequest() throws RollbackableBizException;

	public void updateDeviceState(String srId) throws RollbackableBizException;
	
	public List<AppStatVo> findAppStatBySrId(String srId) throws RollbackableBizException;

	public String findTenatIdByUserId(String userId) throws RollbackableBizException;
	/**
	 * 服务申请查询
	 * @param srId
	 * @return
	 */
	public BmToDoVo getCloudRequestWaitDealBySrId(String srId);
}