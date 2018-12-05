package com.git.cloud.request.oa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.bill.model.vo.OrderRoot;
import com.git.cloud.bill.service.BillService;
import com.git.cloud.bill.service.impl.BillServiceImpl;
import com.git.cloud.common.enums.BillingParamEnum;
import com.git.cloud.common.enums.CostType;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.request.dao.IBmSrRrVmRefDao;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.model.SrTypeMarkEnum;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.vo.BmSrRrinfoVo;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.service.IRequestBaseService;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDeviceVolumesRefPo;
import com.git.cloud.resmgt.openstack.model.vo.FloatingIpVo;
import com.git.support.constants.PubConstants;

public class CreateBillingHandler {
	private static Logger logger = LoggerFactory.getLogger(CreateBillingHandler.class);
	IRequestBaseService requestBaseService = (IRequestBaseService) WebApplicationManager.getBean("requestBaseServiceImpl");
	ICmVmDAO cmVmDao =  (ICmVmDAO) WebApplicationManager.getBean("cmVmDAO");
	IBmSrRrinfoDao bmSrRrinfoDao = (IBmSrRrinfoDao) WebApplicationManager.getBean("bmSrRrinfoDaoImpl");
	IBmSrRrVmRefDao bmSrRrVmRefDao = (IBmSrRrVmRefDao) WebApplicationManager.getBean("bmSrRrVmRefDaoImpl");
	BillService billService = (BillService) WebApplicationManager.getBean("billServiceImpl");
	private String userId;
	private String tenantId;
	private String srTypeMark;
	public String execute(HashMap<String, Object> contenxtParams) {
		String srId = (String) contenxtParams.get("srvReqId");
		BmSrVo bmSrVo = null;
		Boolean createFlavorFlag = true;
		Boolean createDiskFlag = true;
		Boolean createFloatIpFlag = true;
		//服务申请中的所有设备列表
		List<String> allDeviceIdList = new ArrayList<String>();
		//完成订单创建的设备列表
		List<String> finishBillingDeviceList = new ArrayList<String>();
		try {
			bmSrVo =requestBaseService.findBmSrVoById(srId);
			if(CommonUtil.isEmpty(bmSrVo)){
				throw new Exception("bmSrVo is null");
			}
			this.srTypeMark = bmSrVo.getSrTypeMark();
			this.userId = bmSrVo.getCreatorId();
			this.tenantId = bmSrVo.getTenantId();
			logger.info("服务申请srId："+srId+",申请类型："+srTypeMark+",userId:"+userId+",teanntId:"+tenantId);
			List<BmSrRrinfoVo> srRrinfoList = bmSrRrinfoDao.findBmSrRrinfoListBySrId(srId);
			String deviceId;
			if(srRrinfoList !=null && srRrinfoList.size()>0) {
				if(SrTypeMarkEnum.VIRTUAL_SUPPLY.getValue().equals(srTypeMark) || SrTypeMarkEnum.VIRTUAL_EXTEND.getValue().equals(srTypeMark)) {
					for (BmSrRrinfoVo srRrinfoVo : srRrinfoList) {
						List<BmSrRrVmRefPo> vmRefList = bmSrRrVmRefDao.findBmSrRrVmRefListByRrinfoId(srRrinfoVo.getRrinfoId());
						int len = vmRefList == null ? 0 : vmRefList.size();
						for(int i=0 ; i<len ; i++) {
							allDeviceIdList.add(vmRefList.get(i).getDeviceId());
							deviceId = vmRefList.get(i).getDeviceId();
							CmVmPo vm = cmVmDao.findCmVmById(deviceId);
							if(CommonUtil.isEmpty(vm)) {
								throw new Exception("vm is null");
							}
							//创建规格订单
							if(SrTypeMarkEnum.VIRTUAL_EXTEND.getValue().equals(srTypeMark)) {
								int cpuOld = vmRefList.get(i).getCpuOld();
								int memOld = vmRefList.get(i).getMemOld();
								if(cpuOld != vm.getCpu() || memOld != vm.getMem()) {
									//扩容的配置不相同，才会生成配置变更的订单
									createFlavorFlag = this.createFlavorBilling(vm);
								}
							}else {
								createFlavorFlag = this.createFlavorBilling(vm);
							}
							//创建卷订单
							createDiskFlag = this.createDiskBilling(deviceId);
							//创建浮动IP订单
							createFloatIpFlag = this.createFloatIpBilling(deviceId);
							if(createFlavorFlag && createDiskFlag && createFloatIpFlag) {
								finishBillingDeviceList.add(deviceId);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("生成计费订单失败",e);
			return PubConstants.EXEC_RESULT_FAIL;
		}
		if(allDeviceIdList.size() == finishBillingDeviceList.size()) {
			return PubConstants.EXEC_RESULT_SUCC;
		}else {
			return PubConstants.EXEC_RESULT_FAIL;
		}
	}
	/**
	 * 通过规格创建订单
	 * @param deviceId
	 * @return
	 * @throws RollbackableBizException
	 */
	public Boolean createFlavorBilling(CmVmPo vm) throws RollbackableBizException {
		Boolean createFlavorFlag = true;
		try {
			Map<String,String> flavorParamMap = new HashMap<>();
			flavorParamMap.put(BillingParamEnum.INSTANCE_ID.getValue(),vm.getId());
			flavorParamMap.put(BillingParamEnum.USER_ID.getValue(), this.userId);
			flavorParamMap.put(BillingParamEnum.TENANT_ID.getValue(), this.tenantId);
			flavorParamMap.put(BillingParamEnum.COST_TYPE.getValue(),CostType.FLAVOR.getValue());
			flavorParamMap.put(BillingParamEnum.SR_TYPE_MARK.getValue(),this.srTypeMark);
			flavorParamMap.put(BillingParamEnum.CPU_NUM.getValue(),vm.getCpu().toString());
			flavorParamMap.put(BillingParamEnum.MEM_NUM.getValue(),vm.getMem().toString());
			flavorParamMap.put(BillingParamEnum.SERVICE_ID.getValue(),vm.getServiceId());
			OrderRoot flavorOrderRoot = billService.buildOrderData(flavorParamMap);
			String createFlavorFlagStr = billService.createOrderInterface(flavorOrderRoot);
		if(!createFlavorFlagStr.equals("success")) {
			createFlavorFlag = false;
			logger.error("创建规格计费订单失败:"+createFlavorFlagStr);
			}
		} catch (Exception e) {
			throw new RollbackableBizException("创建规格计费订单失败");
		}
		return createFlavorFlag;
	}
	
	/**
	 * 通过磁盘创建订单
	 * @param deviceId
	 * @return
	 * @throws RollbackableBizException
	 */
	public boolean createDiskBilling(String deviceId)throws RollbackableBizException {
		Boolean createDiskBillingFlag = true;
		try {
			List<RmDeviceVolumesRefPo> volumesRefList = cmVmDao.getDeviceVolumeRefList(deviceId);
			if(volumesRefList != null && volumesRefList.size()>0) {
				for(RmDeviceVolumesRefPo p:volumesRefList) {
					Map<String,String> diskParamMap = new HashMap<>();
					diskParamMap.put(BillingParamEnum.INSTANCE_ID.getValue(),p.getId());
					diskParamMap.put(BillingParamEnum.USER_ID.getValue(), this.userId);
					diskParamMap.put(BillingParamEnum.TENANT_ID.getValue(), this.tenantId);
					diskParamMap.put(BillingParamEnum.SR_TYPE_MARK.getValue(),this.srTypeMark);
					diskParamMap.put(BillingParamEnum.COST_TYPE.getValue(),CostType.DISK.getValue());
					diskParamMap.put(BillingParamEnum.DISK_SIZE.getValue(),p.getDiskSize());
					OrderRoot diskOrderRoot = billService.buildOrderData(diskParamMap);
					String diskBillingFlagStr = billService.createOrderInterface(diskOrderRoot);
					if(!diskBillingFlagStr.equals("success")) {
						createDiskBillingFlag = false;
						logger.error("创建磁盘计费订单失败:"+diskBillingFlagStr);
					}
				}
			}
		} catch (Exception e) {
			throw new RollbackableBizException("创建磁盘计费订单失败");
		}
			return createDiskBillingFlag;
	}
	
	/**
	 * 通过浮动ip创建订单
	 * @param deviceId
	 * @return
	 * @throws RollbackableBizException
	 */
	public boolean createFloatIpBilling(String deviceId)throws RollbackableBizException {
		Boolean createFloatIpBillingFlag = true;
		try {
			List<FloatingIpVo>  floatingIpVoList = cmVmDao.getFloatIpList(deviceId);
			if(floatingIpVoList != null && floatingIpVoList.size()>0) {
				for(FloatingIpVo ip : floatingIpVoList) {
					Map<String,String> floatIpParamMap = new HashMap<>();
					floatIpParamMap.put(BillingParamEnum.INSTANCE_ID.getValue(),ip.getId());
					floatIpParamMap.put(BillingParamEnum.USER_ID.getValue(), this.userId);
					floatIpParamMap.put(BillingParamEnum.TENANT_ID.getValue(), this.tenantId);
					floatIpParamMap.put(BillingParamEnum.SR_TYPE_MARK.getValue(),this.srTypeMark);
					floatIpParamMap.put(BillingParamEnum.COST_TYPE.getValue(),CostType.FLOATIP.getValue());
					OrderRoot floatIpRoot = billService.buildOrderData(floatIpParamMap);
					String floatIpBillingStr = billService.createOrderInterface(floatIpRoot);
					if(!floatIpBillingStr.equals("success")) {
						createFloatIpBillingFlag = false;
						logger.error("创建浮动ip计费订单失败："+floatIpBillingStr);
					}
				}
			}
		} catch (Exception e) {
			throw new RollbackableBizException("创建浮动ip计费订单失败");
		}
		return createFloatIpBillingFlag;
	}
	
}	
