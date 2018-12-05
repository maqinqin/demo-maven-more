package com.git.cloud.log.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.common.enums.OperationType;
import com.git.cloud.common.enums.ResourceType;
import com.git.cloud.common.enums.State;
import com.git.cloud.common.enums.Type;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.dic.service.IDicService;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.log.dao.INotificationDao;
import com.git.cloud.log.model.po.NotificationPo;
import com.git.cloud.log.service.INotificationService;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.taglib.util.Internation;
import com.git.cloud.tenant.dao.ITenantAuthoDao;

public class NotificationServiceImpl implements INotificationService{
	private INotificationDao notiDaoImpl;
	private ICmDeviceService iCmDeviceService;
	private IDicService dicService;
	@Autowired
	private ITenantAuthoDao tenantDao;
	public IDicService getDicService() {
		return dicService;
	}

	public void setDicService(IDicService dicService) {
		this.dicService = dicService;
	}

	public INotificationDao getNotiDaoImpl() {
		return notiDaoImpl;
	}

	public ICmDeviceService getiCmDeviceService() {
		return iCmDeviceService;
	}

	public void setiCmDeviceService(ICmDeviceService iCmDeviceService) {
		this.iCmDeviceService = iCmDeviceService;
	}

	public void setNotiDaoImpl(INotificationDao notiDaoImpl) {
		this.notiDaoImpl = notiDaoImpl;
	}

	@Override
	public Pagination<NotificationPo> findNotificationsPage(
			PaginationParam paginationParam) throws RollbackableBizException {
		return notiDaoImpl.findNotificationsPage(paginationParam);
	}

	@Override
	public void insertNotification(NotificationPo notiPo)
			throws RollbackableBizException {
		notiPo.setId(UUIDGenerator.getUUID());
		notiPo.setCreateTime(new Date());
		notiPo.setState(State.UNREAD.getValue());
		String resourceId = notiPo.getResourceId();
		String resourceType  = notiPo.getResourceType();
		String dicOperationName = dicService.findDicNameByDicCode(notiPo.getOperationType());
		/*if(notiPo.getType().equals(Type.TIP.getValue())){
			if(CommonUtil.isEmpty(notiPo.getCreator())){
				notiPo.setCreator("admin");
			}
			SysUserPo loginUser = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
			if(loginUser == null){
				notiPo.setCreator("admin");
			}else{
				String loginName = loginUser.getLoginName();
				notiPo.setCreator(loginName);	
			}
		}*/
		try {
			if(notiPo.getUserId() == null || "".equals(notiPo.getUserId())) {
				SysUserPo loginUser = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
				if(loginUser != null) {
					String userId = loginUser.getUserId();
					notiPo.setUserId(userId);
					String tenantId = tenantDao.selectTenantByUserId(userId);
					notiPo.setTenantId(tenantId);
				}
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}	
		
		String content = "";
		if(resourceType.equals(ResourceType.PHYSICAL.getValue())){
			//PHYSICAL("H"),  		/**< 物理机 */
			CmDeviceHostShowBo cmDeviceHostShowBo = iCmDeviceService.getCmDeviceHostInfo(resourceId);
			content = cmDeviceHostShowBo.getDatacenter_cname()+"."+cmDeviceHostShowBo.getPool_name()+"."+cmDeviceHostShowBo.getCluster_name()+"."+cmDeviceHostShowBo.getDevice_name()+Internation.language("res_pool_device_pm")+dicOperationName;
		}else if(resourceType.equals(ResourceType.VIRTUAL.getValue())){
			//VIRTUAL("V"),    		/**< 虚拟机 */
			if(notiPo.getOperationType().equals(OperationType.RECYCLE.getValue())){
				content = notiPo.getOperationContent()+Internation.language("res_pool_device_operate")+dicOperationName;
			}else if(notiPo.getOperationType().equals(OperationType.IMPORT_VM.getValue())){
				content = notiPo.getOperationContent()+Internation.language("res_pool_device_vm")+dicOperationName;
			}else if(notiPo.getOperationType().equals(OperationType.SYNC_VM_INFO.getValue())) {
				content = notiPo.getOperationContent();		// +Internation.language("res_pool_device_vm")+dicOperationName;
			}else if(notiPo.getOperationType().equals(OperationType.IMPORT_VSWITCH.getValue())){
				content = notiPo.getOperationContent();
			}else if(notiPo.getOperationType().equals(OperationType.SYNC_VM_INFO.getValue())){
				content = notiPo.getOperationContent();		// +Internation.language("res_pool_happpen")+dicOperationName;
			}else{
				CmDeviceVMShowBo cmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(resourceId);
				if(StringUtils.isNotBlank(cmDeviceVMShowBo.getAppInfo_name())){
					content += cmDeviceVMShowBo.getAppInfo_name()+".";
				}else if(StringUtils.isNotBlank(cmDeviceVMShowBo.getDu_name())){
					content += cmDeviceVMShowBo.getDu_name()+".";
				}else if(StringUtils.isNotBlank(cmDeviceVMShowBo.getVm_name())){
					content += cmDeviceVMShowBo.getVm_name()+".";
				}
				content += Internation.language("res_pool_device_operate")+dicOperationName;
			}
		}
		else if(resourceType.equals(ResourceType.VIRTUALMANAGE.getValue())){
			//VIRTUALMANAGE("VM"),	/**< 虚拟机管理机*/
			
		}else if(resourceType.equals(ResourceType.HMC.getValue())){
			//HMC("HMC"),				/**< HMC */
			
		}else if(resourceType.equals(ResourceType.NIM.getValue())){
			//NIM("NIM"),				/**< NIM */
			
		}else if(resourceType.equals(ResourceType.SCRIPTSERVER.getValue())){
			//SCRIPTSERVER("SCRIPT"),	/**< 脚本服务器 */
			
		}else if(resourceType.equals(ResourceType.IMAGESERVER.getValue())){
			//IMAGESERVER("IMAGE") 	/**< 镜像服务器 */
		}else{
			content = notiPo.getOperationContent();
		}
		content = content.replace("null.", "");
		notiPo.setOperationContent(content);
		notiDaoImpl.insertNotification(notiPo);
	}

	@Override
	public NotificationPo findNotificationById(String id)
			throws RollbackableBizException {
		return notiDaoImpl.findNotificationById(id);
	}

	@Override
	public void updateNotiState(NotificationPo notiPo) throws RollbackableBizException {
		notiPo.setState(State.READ.getValue());
		notiDaoImpl.updateNotiState(notiPo);
	}

	@Override
	public String findNotificationIdByAlarmKey(String alarmKey) throws RollbackableBizException {
		return notiDaoImpl.findNotificationIdByAlarmKey(alarmKey);
	}

	@Override
	public void updateAlarmState(NotificationPo notiPo) throws RollbackableBizException {
		notiPo.setState(State.UNREAD.getValue());
		notiPo.setCreateTime(new Date());
		notiDaoImpl.updateAlarmState(notiPo);
	}

}
