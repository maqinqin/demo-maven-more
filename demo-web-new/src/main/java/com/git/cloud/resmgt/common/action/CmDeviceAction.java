package com.git.cloud.resmgt.common.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.enums.OperationType;
import com.git.cloud.common.enums.ResourceType;
import com.git.cloud.common.enums.Source;
import com.git.cloud.common.enums.Type;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.log.model.po.NotificationPo;
import com.git.cloud.log.service.INotificationService;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmLocalDiskPo;
import com.git.cloud.resmgt.common.model.vo.CmDatastoreVo;
import com.git.cloud.resmgt.common.model.vo.CmDeviceAndModelVo;
import com.git.cloud.resmgt.common.model.vo.CmDeviceVo;
import com.git.cloud.resmgt.common.model.vo.CmHostVo;
import com.git.cloud.resmgt.common.model.vo.CmStorageVo;
import com.git.cloud.resmgt.common.model.vo.CmVmVo;
import com.git.cloud.resmgt.common.model.vo.DeviceDiagramVo;
import com.git.cloud.resmgt.common.model.vo.DeviceVmTypeNumDiagramVo;
import com.git.cloud.resmgt.common.model.vo.ResPoolHostVmInfoVo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.common.service.ICmHostDatastoreRefService;
import com.git.cloud.resmgt.common.service.IResComputeDiagramService;
import com.git.cloud.resmgt.compute.handler.PmHandlerService;
import com.git.cloud.resmgt.compute.handler.VmControllerServiceImpl;
import com.git.cloud.shiro.model.CertificatePo;
import com.git.license.ExtraParamModel;
import com.git.license.ILicenseVerifyService;
import com.git.license.LicenseVerifyService;
import com.gitcloud.tankflow.service.IProcessInstanceService;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * CopyRight(c) 2014 by GIT
 * 
 * @Title: CmDeviceAction.java
 * @Package com.git.cloud.resmgt.common.action
 * @Description: 根据主机id，查询物理机详细信息，并将结果返回给页面。
 * @author lizhizhong
 * @date 2014-9-15 下午1:54:13
 * @version V1.0
 */
public class CmDeviceAction extends BaseAction<Object> {
	private static Logger logger = LoggerFactory.getLogger(CmDeviceAction.class);

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -503957960040477927L;
	private ICmDeviceService iCmDeviceService;
	private IResComputeDiagramService computDiagramService;
	private IProcessInstanceService processInstanceService;
	private ParameterService  parameterServiceImpl;
	private CmDevicePo cmDevicePo; 
	private VmControllerServiceImpl vmControllers;
	private List<CmHostVo> cmHostVos;
	private INotificationService notiServiceImpl;
	@Resource(name="pmHandlerServiceImpl")
	private PmHandlerService pmHandlerService;
	private ICmHostDatastoreRefService cmHostDatastoreRefServiceImpl;
	private String volumeId;
	private String volumeSize;
	private String share;
	private String isCreate;
	private String vmName;
	private String projectId;
	
	private String volumeType;
	
	
	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}
	
	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getVolumeSize() {
		return volumeSize;
	}

	public void setVolumeSize(String volumeSize) {
		this.volumeSize = volumeSize;
	}

	private AutomationService automationService;
	
	
	public AutomationService getAutomationService() {
		return automationService;
	}

	public void setAutomationService(AutomationService automationService) {
		this.automationService = automationService;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public ICmHostDatastoreRefService getCmHostDatastoreRefServiceImpl() {
		return cmHostDatastoreRefServiceImpl;
	}

	public void setCmHostDatastoreRefServiceImpl(
			ICmHostDatastoreRefService cmHostDatastoreRefServiceImpl) {
		this.cmHostDatastoreRefServiceImpl = cmHostDatastoreRefServiceImpl;
	}

	public INotificationService getNotiServiceImpl() {
		return notiServiceImpl;
	}

	public void setNotiServiceImpl(INotificationService notiServiceImpl) {
		this.notiServiceImpl = notiServiceImpl;
	}

	public VmControllerServiceImpl getVmControllers() {
		return vmControllers;
	}

	public void setVmControllers(VmControllerServiceImpl vmControllers) {
		this.vmControllers = vmControllers;
	}

	public CmDevicePo getCmDevicePo() {
		return cmDevicePo;
	}

	public void setCmDevicePo(CmDevicePo cmDevicePo) {
		this.cmDevicePo = cmDevicePo;
	}

	public List<CmHostVo> getCmHostVos() {
		return cmHostVos;
	}

	public void setCmHostVos(List<CmHostVo> cmHostVos) {
		this.cmHostVos = cmHostVos;
	}

	/* 集群id */
	private String cluster_id;
	/* 主机id */
	private String hostId;
	/* 虚机id */
	private String vmId;
	
	private String availableZoneId;

	private String hostName;

	private String deviceIds;

	public String getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(String deviceIds) {
		this.deviceIds = deviceIds;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getAvailableZoneId() {
		return availableZoneId;
	}

	public void setAvailableZoneId(String availableZoneId) {
		this.availableZoneId = availableZoneId;
	}

	public CmDeviceAction() {
		super();
	}


	/**
	 * @throws Exception 
	 * @Title: getCmDeviceHostInfo
	 * @Description: 从页面获取主机id，并根据此id查询物理机详细信息。
	 * @return void 返回类型
	 * @throws
	 */
	public void getCmDeviceHostInfo() throws Exception {
		CmDeviceHostShowBo cmDeviceHostShowBo = iCmDeviceService.getCmDeviceHostInfo(getHostId());
		// 将查询结果反映到前台页面。
		try {
			jsonOut(cmDeviceHostShowBo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}

	}
	/**
	 * @Title: getCmDeviceVMInfo
	 * @Description: 从页面获取虚机id，并根据此id查询虚拟机详细信息。
	 * @return void 返回类型
	 * @throws
	 */
	public void getCmDeviceVMInfo() {
		try {
			CmDeviceVMShowBo cmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(getVmId());
			jsonOut(cmDeviceVMShowBo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}
	}

	public ICmDeviceService getiCmDeviceService() {
		return iCmDeviceService;
	}

	public void setiCmDeviceService(ICmDeviceService iCmDeviceService) {
		this.iCmDeviceService = iCmDeviceService;
	}

	private CmDeviceVo cmDevicevo;
	private CmHostVo cmHostVo;
	private CmStorageVo cmStorageVo;
	private CmDatastoreVo cmDatastoreVo;
	private CmDeviceAndModelVo cmDeviceAndModelVo;
	private String id;
	private String[] ids;
	private CmVmVo cmVmVo;
	private String data;
	private CmLocalDiskPo cmLocalDiskPo;

	/**
	 * 
	 * @return
	 * @Description访问action
	 */
	public String cmDeviceView() {
		return "success";
	}

	/**
	 * 查询所有的设备信息，分页展示到前台
	 * 
	 * @throws Exception
	 */
	public void getDeviceList() throws Exception {
		this.jsonOut(iCmDeviceService.getDevicePagination(this
				.getPaginationParam()));
	}

	public void addCmDevicePage() {
		CmStorageVo cmStorageVo1 = new CmStorageVo();
		cmStorageVo1.setId(com.git.cloud.foundation.util.UUIDGenerator
				.getUUID());
		try {
			jsonOut(cmStorageVo1);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}

	}

	/**
	 * 以下是set get
	 * 
	 * @return
	 */

	public CmDeviceVo getCmDevicevo() {
		return cmDevicevo;
	}

	public void setCmDevicevo(CmDeviceVo cmDevicevo) {
		this.cmDevicevo = cmDevicevo;
	}

	public CmHostVo getCmHostVo() {
		return cmHostVo;
	}

	public void setCmHostVo(CmHostVo cmHostVo) {
		this.cmHostVo = cmHostVo;
	}

	public CmStorageVo getCmStorageVo() {
		return cmStorageVo;
	}

	public void setCmStorageVo(CmStorageVo cmStorageVo) {
		this.cmStorageVo = cmStorageVo;
	}

	public CmDatastoreVo getCmDatastoreVo() {
		return cmDatastoreVo;
	}

	public void setCmDatastoreVo(CmDatastoreVo cmDatastoreVo) {
		this.cmDatastoreVo = cmDatastoreVo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getCluster_id() {
		return cluster_id;
	}

	public void setCluster_id(String cluster_id) {
		this.cluster_id = cluster_id;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public CmDeviceAndModelVo getCmDeviceAndModelVo() {
		return cmDeviceAndModelVo;
	}

	public void setCmDeviceAndModelVo(CmDeviceAndModelVo cmDeviceAndModelVo) {
		this.cmDeviceAndModelVo = cmDeviceAndModelVo;
	}

	public CmVmVo getCmVmVo() {
		return cmVmVo;
	}

	public void setCmVmVo(CmVmVo cmVmVo) {
		this.cmVmVo = cmVmVo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public CmLocalDiskPo getCmLocalDiskPo() {
		return cmLocalDiskPo;
	}

	public void setCmLocalDiskPo(CmLocalDiskPo cmLocalDiskPo) {
		this.cmLocalDiskPo = cmLocalDiskPo;
	}

	public IResComputeDiagramService getComputDiagramService() {
		return computDiagramService;
	}

	public void setComputDiagramService(
			IResComputeDiagramService computDiagramService) {
		this.computDiagramService = computDiagramService;
	}

	/**
	 * @Title: getDeviceDiagramInfo
	 * @Description: 获取最新入库记录
	 * @field:
	 * @return void
	 * @throws
	 */
	public void getDeviceDiagramInfo() {
		try {
			List<DeviceDiagramVo> l = computDiagramService
					.getDeviceDialgramInfo();
			arrayOut(l);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}
	}

	/**
	 * @Title: getVmTypeNumInfo
	 * @Description: 获取虚拟机类型对应的虚拟机数量
	 * @field:
	 * @return void
	 * @throws
	 */
	public void getVmTypeNumInfo() {
		try {
			List<DeviceVmTypeNumDiagramVo> l = computDiagramService
					.getVmNumberInfo();
			arrayOut(l);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}
	}

	/**
	 * @Title: getPoolHostAndVmInfo
	 * @Description: 获取资源池对应物理机和虚拟机的cpu，memory信息
	 * @field:
	 * @return void
	 * @throws
	 */
	public void getPoolHostAndVmInfo() {
		try {
			List<ResPoolHostVmInfoVo> l = computDiagramService
					.getResPoolHostVmInfo();
			arrayOut(l);

		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}
	}
	

	// 新增加物理机验证工作
	public void validateNaguan() throws Exception {
		CertificatePo certificatePo = iCmDeviceService.findCertificatePath();
		if (certificatePo != null) {
			ILicenseVerifyService verifyService = new LicenseVerifyService();
			String storeName = certificatePo.getCertificateName().split("\\.")[0];
			String storePath = certificatePo.getCertificatePath() + "/"
					+ certificatePo.getCertificateName();
			ExtraParamModel paramModel = verifyService.getExtraParam(storeName,
					storePath);
			// 获取配置文件中纳管机器的数量
			int number1 = paramModel.getServerCount();

			// 获取当前系统中实际纳管机器的数量
			List<CmDeviceVo> list = iCmDeviceService.getCmDevicePoNumber();
			int number2 = list.size();
			if (number2 >= number1) {
				this.stringOut("false");
			} else {
				this.stringOut("true");
			}
		} else {
			this.stringOut("true");
		}
	}

	// 判断集群下，是否有纳管的物理机
	public void selectCmClusterHostInfoss() throws Exception {
		// false,没有纳管主机，true，有纳管主机
		boolean flag = iCmDeviceService
				.selectCmClusterHostInfos(getCluster_id());
		this.stringOut(String.valueOf(flag));
	}

	public IProcessInstanceService getProcessInstanceService() {
		return processInstanceService;
	}

	public void setProcessInstanceService(
			IProcessInstanceService processInstanceService) {
		this.processInstanceService = processInstanceService;
	}

	public ParameterService getParameterServiceImpl() {
		return parameterServiceImpl;
	}

	public void setParameterServiceImpl(ParameterService parameterServiceImpl) {
		this.parameterServiceImpl = parameterServiceImpl;
	}

	//保存缺省的datastore
	public void saveDefaultDatastore(){
		try {
			iCmDeviceService.saveDefaultDatastore(cmDevicePo);
		} catch (RollbackableBizException e) {
			logger.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}
	}
	//获取物理机缺省的datastore
	public void getDefaultDatastore(){
		try {
			CmDevicePo cmDevicePo = iCmDeviceService.getDefaultDatastore(hostId);
			this.jsonOut(cmDevicePo);
		} catch (RollbackableBizException e) {
			logger.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}
	}
	public void getPmRunningState() throws Exception{
		String result = iCmDeviceService.getPmRunningState(hostId);
		stringOut(result);
	}
	public void isCmVm()throws Exception{
		boolean flag = iCmDeviceService.isCmVm(hostId);
		stringOut(String.valueOf(flag));
	}
	public void getHostConfigure()throws Exception{
		this.jsonOut(iCmDeviceService.getHostConfigure(this.getPaginationParam()));
	}
	public void getRmDeviceVolumesRefPoList()throws Exception{
		this.jsonOut(iCmDeviceService.getRmDeviceVolumesRefPoList(this.getPaginationParam()));
	}
	
	
	//卸载时----获取机器当前的挂载卷列表
	public void getMountedServerVolume()throws Exception{
		this.jsonOut(iCmDeviceService.getRmDeviceVolumesRefPoList(this.getPaginationParam()));
	}
	
	/**根据可用分区查询卷类型
	 * */
	public void findVolumeTypeList() throws Exception{
		List<String> volume = iCmDeviceService.findVolumeTypeList(availableZoneId);
		arrayOut(volume);
	}
	
}
