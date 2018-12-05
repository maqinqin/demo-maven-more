package com.git.cloud.request.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.appmgt.model.vo.AppSysKpiVo;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.request.model.po.BmSrPo;
import com.git.cloud.request.model.po.BmToDoPo;
import com.git.cloud.request.model.vo.BmApproveVo;
import com.git.cloud.request.model.vo.BmSrAttrValVo;
import com.git.cloud.request.model.vo.BmSrRrinfoVo;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.model.vo.BmToDoVo;
import com.git.cloud.resmgt.openstack.model.vo.FloatingIpVo;
import com.git.cloud.sys.model.po.SysUserLimitPo;

/**
 * 云服务申请公共接口类
 * @ClassName:IRequestBaseService
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public interface IRequestBaseService extends IService {
	
	/**
	 * 服务申请列表查询
	 * @Title: getCloudReqeustPagination
	 * @Description: TODO
	 * @field: @param pagination
	 * @field: @return
	 * @return Pagination<BmSrVo>
	 * @throws
	 */
	public Pagination<BmSrVo> getCloudReqeustPagination(PaginationParam pagination);
	/**
	 * 服务申请查询
	 * @param srId
	 * @return
	 */
	public BmToDoVo getCloudRequestWaitDealBySrId(String srId);
	/**
	 * 服务申请待办列表查询
	 * @Title: getCloudRequestWaitDealPagination
	 * @Description: TODO
	 * @field: @param pagination
	 * @field: @return
	 * @return Pagination<BmToDoVo>
	 * @throws
	 */
	public Pagination<BmToDoVo> getCloudRequestWaitDealPagination(PaginationParam pagination);
	/**
	 * 插入待办
	 * @param bmTodo
	 * @throws RollbackableBizException
	 */
	public void insertBmToDo(BmToDoPo bmTodo) throws RollbackableBizException;
	/**
	 * @throws RollbackableBizException 
	 * 更新服务申请代码
	 * @Title: updateBmSrStatus
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @param srStatusCode
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
	 * 根据服务申请Id获取服务申请
	 * @Title: findBmSrVoById
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return BmSrVo
	 * @throws
	 */
	public BmSrVo findBmSrVoById(String srId);
	/**
	 * 查询服务请求对象by待办Id
	 * @param id
	 * @return BmSrVo
	 */
	public BmSrVo getSrByToDoId(String id);
	/**
	 * 查询服务请求对象by待办Id,如删除服务器角色
	 * @param id
	 * @return BmSrVo
	 */
	public BmSrVo selectBmSrForDelToDoId(String id);
	/**
	 * 待办开始处理
	 * @throws RollbackableBizException 
	 * @Title: todoStartDeal
	 * @Description: TODO
	 * @field: @param todoId
	 * @return void
	 * @throws
	 */
	public void todoStartDeal(String todoId) throws RollbackableBizException;
	/**
	 * 查询资源请求列表
	 * @param pagination
	 * @return Pagination
	 */
	public Pagination<BmSrRrinfoVo> queryBmSrRrinfoList(PaginationParam pagination);
	/**
	 * 查询资源请求列表（服务交付页面使用）
	 * @param pagination
	 * @return Pagination
	 */
	public Pagination<BmSrRrinfoVo> queryBmSrRrinfoAffirmList(PaginationParam pagination);
	/**
	 * 查询网络地址
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getVmNetIp(String rrinfoId,String vmdeviceId) throws BizException;
	/**
	 * 确认审核的日志记录方法
	 * @param obj
	 * @return
	 * @throws RollbackableBizException
	 */
	public String saveApproveLog(BmApproveVo bmApproveVo) throws RollbackableBizException;
	/**
	 * 根据srId获取审批结果
	 * @param srId
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<BmApproveVo> findApproveResult(String srId) throws RollbackableBizException;
	/**
	 * @throws Exception 
	 * @throws RollbackableBizException 
	 * 作废服务申请
	 * @Title: deleteApprove
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @param todoId
	 * @return void
	 * @throws
	 */
	public void deleteApprove(String srId, String todoId) throws Exception;
	/**
	 * 查询云服务资源请求相关信息（确认实施、实施完成页面使用）（供给）
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<Map> queryBmSrRrinfoByParam(PaginationParam pagination) ;
	/**
	 * 查询云服务资源请求相关信息（确认实施、实施完成页面使用）（回收）
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<Map> queryBmSrRrinfoRecycleByParam(PaginationParam pagination) ;
	/**
	 * 查询云服务资源请求相关信息（确认实施、实施完成页面使用）（扩容）
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<Map> queryBmSrRrinfoExtendByParam(PaginationParam pagination) ;
	/**
	 * 资源分配（供给）
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<Map> queryBmSrRrinfoResoureList(PaginationParam pagination) ;
	
	
	@SuppressWarnings("rawtypes")
	public Pagination<Map> queryBmSrRrinfoResoureListAuto(PaginationParam pagination) ;
	/**
	 * 资源分配（扩容）
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<Map> findExpandVirtualDeviceBySrId(PaginationParam pagination) ;
	/**
	 * 子流程列表
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryWorkflowLinkList(Map paramMap)  ;
	/**
	 * 获取最新的服务申请
	 * @return
	 * @throws RollbackableBizException
	 */
	public BmSrPo findBmSrNewestRecord() throws RollbackableBizException;
	/**
	 * 创建子流程
	 * @Title: createInstance
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @param flowIds
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return String
	 * @throws
	 */
	public String createInstance(String srId, String flowIds) throws RollbackableBizException;
	/**
	 * 自动创建流程子流的handler调用
	 * @param srId
	 * @param flowId
	 * @return
	 * @throws RollbackableBizException
	 */
	public String createInstanceAuto(String srId, String flowId) throws RollbackableBizException;
	
	/**
	 * @throws Exception 
	 * 启动子流程
	 * @Title: startInstance
	 * @Description: TODO
	 * @field: @param instanceId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return String
	 * @throws
	 */
	public String startInstance(String instanceId) throws Exception;
	/**
	 * 获得服务参数
	 * @Title: getBmSrAttr
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @return
	 * @return String
	 * @throws
	 */
	public List<BmSrAttrValVo> getBmSrAttr(String srId) throws RollbackableBizException;
	/**
	 * 完成实施接口
	 * @param srId
	 * @param todoId
	 * @param srType
	 * @throws RollbackableBizException
	 * @throws Exception 
	 */
	public void saveOperateEnd(String srId, String todoId, String srType) throws Exception;
	/**
	 * 验证关单接口
	 * @param srId
	 * @param todoId
	 * @throws RollbackableBizException
	 * @throws Exception 
	 */
	public void closeRequestSr(String srId, String todoId) throws Exception;
	/**
	 * @throws Exception 
	 * 更新云服务状态并且驱动流程
	 * @Title: saveAndDriveWorkflow
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @param srStatus
	 * @field: @param todoId
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void saveAndDriveWorkflow(String srId, String srStatus, String todoId) throws Exception;
	/**
	 * 记录日志
	 * @param srId
	 * @return
	 * @throws RollbackableBizException
	 */
	public String recordLog(String srId) throws RollbackableBizException;
	/**
	 * @throws Exception 
	 * 更新云服务状态并且驱动流程
	 * @Title: saveAndDriveWorkflow
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @param srStatus
	 * @field: @param todoId
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void saveAndDriveWorkflow(String srId, String srStatus, String todoId, String driveWfType) throws Exception;
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
	 * @return List<BmToDoVo>
	 * @throws
	 */
	public List<BmToDoVo> findNewestWaitDealRequest(int num, String creatorId) throws RollbackableBizException;
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
	
	/**
	 * 根据服务申请Id查询有服务参数的资源申请Id
	 * @param srId
	 * @return
	 * @throws RollbackableBizException
	 */
	public String getHasAttrRrinfoIdBySrId(String srId) throws RollbackableBizException;
	/**
	 * 删除申请的资源
	 * @param srId
	 * @throws RollbackableBizException
	 */
	public void deleteRrinfoById(String rrinfoId) throws RollbackableBizException;
	/**
	 * 删除申请的资源
	 * @param srId
	 * @throws RollbackableBizException
	 */
	public void deleteRrinfoByIds(String rrinfoIds) throws RollbackableBizException;

	/**
	 * 保存虚机所在物理机
	 * @param vmId
	 * @param hostId
	 * @throws Exception
	 */
	public void saveHostName(String vmId, String hostId) throws Exception;
	
	/**
	 * 根据单号作废服务申请单
	 * @param srCode
	 * @throws Exception
	 */
	public void closeServiceRequest(String srCode) throws Exception;
	
	/**
	 * 根据单号作废服务申请单并回收资源
	 * @param srCode
	 * @throws Exception
	 */
	public void closeServiceRequest(String srCode, boolean deleteResource) throws Exception;
	
	/**
	 * 服务申请列表查询,不需要对建单人进行过滤
	 * @Title: getCloudReqeustPagination
	 * @Description: TODO
	 * @field: @param pagination
	 * @field: @return
	 * @return Pagination<BmSrVo>
	 * @throws
	 */
	public Pagination<BmSrVo> getCloudReqeustPagination2(PaginationParam paginationParam);
	public void updateVmProjectId(String vmId,String projectId,String virtualTypeCode)throws RollbackableBizException;
}