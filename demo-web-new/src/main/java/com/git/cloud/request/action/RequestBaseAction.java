package com.git.cloud.request.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.request.model.SrStatusCodeEnum;
import com.git.cloud.request.model.po.BmSrPo;
import com.git.cloud.request.model.vo.BmApproveVo;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.service.IRequestBaseService;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.common.service.ICmHostService;
import com.git.cloud.resmgt.openstack.model.vo.FloatingIpVo;
import com.git.cloud.sys.model.po.SysUserLimitPo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.model.vo.SysUserVo;
import com.git.cloud.sys.service.IUserService;
import com.gitcloud.tankflow.service.IBpmInstanceService;

/**
 * 云服务公共Action
 * @ClassName:RequestBaseAction
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class RequestBaseAction extends BaseAction<BmSrPo> {  

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(RequestBaseAction.class);
	private static final long serialVersionUID = 1L;
	
	private IRequestBaseService requestBaseService;
	private IBpmInstanceService bpmInstanceService;
	private ICmDeviceService cmDeviceService;
	private IUserService userService;
	private ICmHostService cmHostServiceImpl;
	private BmApproveVo bmApproveVo;//代办Id
	private String srId;
	private String vmName;
	private String vmId;
	private String vmVmName;
	private String vmVpName;
	private String oldVmVmName;
	private String oldVmVpName;
	private String oldVmVmVpName;
	private String hostId;
	private String newIp;
	private String oldIp;
	private String rmVolumeTypeId;
	private String floatingIp;
	private String vmIp;
	private String projectId;
	private String virtualTypeCode;
	
	

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getVirtualTypeCode() {
		return virtualTypeCode;
	}

	public void setVirtualTypeCode(String virtualTypeCode) {
		this.virtualTypeCode = virtualTypeCode;
	}

	public String getVmIp() {
		return vmIp;
	}

	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}

	public String getFloatingIp() {
		return floatingIp;
	}

	public void setFloatingIp(String floatingIp) {
		this.floatingIp = floatingIp;
	}

	public String getRmVolumeTypeId() {
		return rmVolumeTypeId;
	}

	public void setRmVolumeTypeId(String rmVolumeTypeId) {
		this.rmVolumeTypeId = rmVolumeTypeId;
	}

	public String getNewIp() {
		return newIp;
	}

	public void setNewIp(String newIp) {
		this.newIp = newIp;
	}

	public String getOldIp() {
		return oldIp;
	}

	public void setOldIp(String oldIp) {
		this.oldIp = oldIp;
	}
	public ICmHostService getCmHostServiceImpl() {
		return cmHostServiceImpl;
	}

	public void setCmHostServiceImpl(ICmHostService cmHostServiceImpl) {
		this.cmHostServiceImpl = cmHostServiceImpl;
	}
	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getOldVmVmVpName() {
		return oldVmVmVpName;
	}

	public void setOldVmVmVpName(String oldVmVmVpName) {
		this.oldVmVmVpName = oldVmVmVpName;
	}

	public String getOldVmVmName() {
		return oldVmVmName;
	}

	public void setOldVmVmName(String oldVmVmName) {
		this.oldVmVmName = oldVmVmName;
	}

	public String getOldVmVpName() {
		return oldVmVpName;
	}

	public void setOldVmVpName(String oldVmVpName) {
		this.oldVmVpName = oldVmVpName;
	}

	public String getVmVmName() {
		return vmVmName;
	}

	public void setVmVmName(String vmVmName) {
		this.vmVmName = vmVmName;
	}

	public String getVmVpName() {
		return vmVpName;
	}

	public void setVmVpName(String vmVpName) {
		this.vmVpName = vmVpName;
	}
	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getVmName() {
		return vmName;
	}

	public String cloudRequestList() {
		return SUCCESS;
	}
	
	public String cloudRequestInfo() {
		String todoId = this.getRequest().getParameter("todoId");
		BmSrVo bmSrVo = requestBaseService.getSrByToDoId(todoId);
		if(bmSrVo.getSrId() == null) {
			bmSrVo = requestBaseService.selectBmSrForDelToDoId(todoId);
		}
		srId = bmSrVo.getSrId();
		this.getRequest().setAttribute("srCode", bmSrVo.getSrCode());
		return SUCCESS;
	}
	
	public void findInstanceIdBySrId() throws Exception {
		this.stringOut(bpmInstanceService.findInstanceIdByServiceReqId(srId));
	}
	
	public String cloudRequestWaitDeal() {
		return SUCCESS;
	}
	
	public void getCloudRequestWaitDeal() throws Exception {
		this.jsonOut(requestBaseService.getCloudRequestWaitDealPagination(this.getPaginationParam()));
	}
	
	public void getCloudReqeustList() throws Exception {
		this.jsonOut(requestBaseService.getCloudReqeustPagination(this.getPaginationParam()));
	}
	public void getCloudReqeustList2() throws Exception {
		this.jsonOut(requestBaseService.getCloudReqeustPagination2(this.getPaginationParam()));
	}
	
	
	public String getCloudReqeust(){
		BmSrVo bmSrVo = null;
		String todoId = this.getRequest().getParameter("todoId");
		String pageType = this.getRequest().getParameter("pageType");
		if(srId != null && !"".equals(srId)) {
			bmSrVo = requestBaseService.findBmSrVoById(srId);
		} else {
			bmSrVo = requestBaseService.getSrByToDoId(todoId);
		}
		if(bmSrVo.getCreatorId() != null) {
			try {
				SysUserVo user = userService.findUserById(bmSrVo.getCreatorId());
				if(user != null) {
					bmSrVo.setCreator(user.getFirstName() + user.getLastName());
				}
			} catch (RollbackableBizException e) {
				logger.error("异常exception",e);
			}
		}
		try {
			String rrinfoIds = requestBaseService.getHasAttrRrinfoIdBySrId(bmSrVo.getSrId());
			bmSrVo.setAttrRrinfoIds(rrinfoIds);
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
		}
		this.getRequest().setAttribute("todoId", todoId);
		this.getRequest().setAttribute("bmSrVo", bmSrVo);
		if("approve".equals(pageType)){
			pageType =  "cloudRequestApprove";
		}else if("index".equals(pageType)){
			pageType =  "cloudRequestIndex";
		}else if("affirm".equals(pageType)){
		    pageType =  "cloudRequestAffirm";
	    }else if("resource".equals(pageType)){
		    pageType =  "cloudRequestResource";
	    }else if("workflow".equals(pageType)){
		    pageType =  "cloudRequestWorkflow";
	    }else if("detail".equals(pageType)){
			pageType =  "cloudRequestDetail";
		}
		return pageType;
	}
	
	public void todoStartDeal() throws Exception {
		String todoId = this.getRequest().getParameter("todoId");
		requestBaseService.todoStartDeal(todoId);
		this.stringOut("success");
	}
	
	public void queryBmSrRrinfoList() throws Exception {
		this.jsonOut(requestBaseService.queryBmSrRrinfoList(this.getPaginationParam()));
	}
	
	public void queryBmSrRrinfoAffirmList() throws Exception {
		this.jsonOut(requestBaseService.queryBmSrRrinfoAffirmList(this.getPaginationParam()));
	}
	
	public void queryBmSrRrinfoResoureList() throws Exception {
		this.jsonOut(requestBaseService.queryBmSrRrinfoResoureList(this.getPaginationParam()));
	}
	public void queryBmSrRrinfoResoureListAuto() throws Exception {
		this.jsonOut(requestBaseService.queryBmSrRrinfoResoureListAuto(this.getPaginationParam()));
	}
	
	public void findExpandVirtualDeviceBySrId() throws Exception {
		this.jsonOut(requestBaseService.findExpandVirtualDeviceBySrId(this.getPaginationParam()));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void queryWorkflowLinkList() throws Exception {
		Map paramMap = new HashMap();
		paramMap.put("srId", srId);
		HttpServletRequest request = ServletActionContext.getRequest();
		String loadType = request.getParameter("loadType");
		Pagination<Map> pagination = new Pagination<Map>();
		pagination.setDataList(requestBaseService.queryWorkflowLinkList(paramMap));
		if("list".equals(loadType)){
			this.jsonOut(pagination);
		}else if("refresh".equals(loadType)){
			this.arrayOut(requestBaseService.queryWorkflowLinkList(paramMap));
		}
	}
	
	public void getVmNetIp() throws Exception {
		String vmdeviceId = this.getRequest().getParameter("vmdeviceId");
		CmDeviceVMShowBo cmDeviceVMShowBo = cmDeviceService.getCmDeviceVMInfo(vmdeviceId);
		this.jsonOut(cmDeviceVMShowBo);
	}
	/**
	 * 获取openstack的虚拟机ip
	 * @throws Exception
	 */
	public void getVmNetIpOpenstack() throws Exception {
		String vmdeviceId = this.getRequest().getParameter("vmdeviceId");
		CmDeviceVMShowBo cmDeviceVMShowBo = cmDeviceService.getCmDeviceVMInfoOpenstack(vmdeviceId);
		this.jsonOut(cmDeviceVMShowBo);
	}
	
	public void findApproveResult() throws Exception {
		this.arrayOut(requestBaseService.findApproveResult(srId));
	}
	
	public void deleteRequest() throws Exception {
		try{
			String todoId = this.getRequest().getParameter("todoId");
			requestBaseService.deleteApprove(srId, todoId);
			this.stringOut("success");
		}catch(RollbackableBizException e){
			this.stringOut("作废操作失败");
		}
	}
	
	public void resourceSubmit() throws Exception {
		HttpServletResponse response  = this.getResponse();
		response.setContentType("text/json;charset=utf-8");
		String todoId = this.getRequest().getParameter("todoId");
		try {
			requestBaseService.saveAndDriveWorkflow(srId, SrStatusCodeEnum.REQUEST_WAIT_OPERATE.getValue(), todoId);
			response.getWriter().print("{\"result\":\"success\"}");
		}catch(RollbackableBizException e){
			response.getWriter().print("{\"result\":\""+e.getLocalizedMessage()+"\"}");
		}
		
	}
	
	public void startInstance() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		String srId = request.getParameter("srId");
		String flowIds = request.getParameter("flowIds");
		try {
			String instanceId = requestBaseService.createInstance(srId, flowIds);
			String flag = requestBaseService.startInstance(instanceId);
			response.getWriter().print(flag);
		} catch (Exception e) {
			response.getWriter().print("无启动流程！");
		}
	}
	
	public void submitSubWorkflow() throws Exception{
		String todoId = this.getRequest().getParameter("todoId");
		requestBaseService.saveAndDriveWorkflow(srId, SrStatusCodeEnum.REQUEST_OPERATING.getValue(), todoId);
		this.stringOut("success");
	}
	
	public void queryBmSrRrinfoByParam() throws Exception{
		this.jsonOut(requestBaseService.queryBmSrRrinfoByParam(this.getPaginationParam()));
	}
	
	public void queryBmSrRrinfoRecycleByParam() throws Exception{
		this.jsonOut(requestBaseService.queryBmSrRrinfoRecycleByParam(this.getPaginationParam()));
	}
	
	public void queryBmSrRrinfoExtendByParam() throws Exception{
		this.jsonOut(requestBaseService.queryBmSrRrinfoExtendByParam(this.getPaginationParam()));
	}
	
	public void getBmSrAttr() throws Exception{
		String rrinfoId = this.getRequest().getParameter("rrinfoId");
		this.arrayOut(requestBaseService.getBmSrAttr(rrinfoId));
	}
	
	public void getBmSrVo() throws Exception{
		BmSrVo bmSrVo = null;
		String srId = this.getRequest().getParameter("srId");
		if(srId != null && !"".equals(srId)) {
			bmSrVo = requestBaseService.findBmSrVoById(srId);
		}		
		this.jsonOut(bmSrVo);
	}
	
	public void findNewestCompleteRequest() throws Exception {
		this.arrayOut(requestBaseService.findNewestCompleteRequest(5));
	}
	
	public void findNewestCreateRequest() throws Exception {
		SysUserPo shiroUser = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
		this.arrayOut(requestBaseService.findNewestCreateRequest(5, shiroUser == null ? "" : shiroUser.getUserId()));
	}
	
	public void findNewestWaitDealRequest() throws Exception {
		SysUserPo shiroUser = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
		this.arrayOut(requestBaseService.findNewestWaitDealRequest(5, shiroUser == null ? "" : shiroUser.getUserId()));
	}
	public void findAppSysVirtualServer() throws Exception {
		this.arrayOut(requestBaseService.findAppSysVirtualServer());
	}
	
	public void findAppSysCompleteRequest() throws Exception {
		this.arrayOut(requestBaseService.findAppSysCompleteRequest());
	}
	
	/*********************************setter||getter******************************/
	public void setRequestBaseService(IRequestBaseService requestBaseService) {
		this.requestBaseService = requestBaseService;
	}
	public void setBpmInstanceService(IBpmInstanceService bpmInstanceService) {
		this.bpmInstanceService = bpmInstanceService;
	}
	public void setCmDeviceService(ICmDeviceService cmDeviceService) {
		this.cmDeviceService = cmDeviceService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public BmApproveVo getBmApproveVo() {
		return bmApproveVo;
	}
	public void setBmApproveVo(BmApproveVo bmApproveVo) {
		this.bmApproveVo = bmApproveVo;
	}
	public String getSrId() {		
		return srId;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public void setSrId(String srId) {
		this.srId = srId;
	}
	public void cancelServiceRequest() throws Exception {
		String srCode = this.getRequest().getParameter("srCode");
		String result = "";
		try {
			requestBaseService.closeServiceRequest(srCode);
		} catch(Exception e) {
			logger.error("异常exception",e);
			result = e.getMessage();
		}
		this.stringOut(result);
	}
	
	public void backServiceRequest() throws Exception {
		String srCode = this.getRequest().getParameter("srCode");
		String result = "";
		try {
			requestBaseService.closeServiceRequest(srCode, true);
		} catch(Exception e) {
			logger.error("异常exception",e);
			result = e.getMessage();
		}
		this.stringOut(result);
	}
	
	public String cloudRequestInfo2(){
		return SUCCESS;
	}
	 public void findSubInstanceIdBySrId() throws Exception {
			this.stringOut(bpmInstanceService.findSubInstanceIdBySrId(srId));
		}
}