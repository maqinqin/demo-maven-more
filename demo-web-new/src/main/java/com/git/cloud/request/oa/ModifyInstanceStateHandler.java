package com.git.cloud.request.oa;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.dao.IBmSrRrVmRefDao;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.model.SrStatusCodeEnum;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.vo.BmSrRrinfoVo;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.model.DeviceStatusEnum;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.support.constants.PubConstants;

/**
 * @author 作者 :
 * @version 创建时间：2018年6月22日 下午3:24:41 
 * 类说明 ：
 */
public class ModifyInstanceStateHandler extends OpenstackCommonHandler {
	private static Logger logger = LoggerFactory.getLogger(ModifyInstanceStateHandler.class);
	IBmSrRrinfoDao bmSrRrinfoDao = (IBmSrRrinfoDao) WebApplicationManager.getBean("bmSrRrinfoDaoImpl");
	IBmSrRrVmRefDao bmSrRrVmRefDao = (IBmSrRrVmRefDao) WebApplicationManager.getBean("bmSrRrVmRefDaoImpl");
	IBmSrDao bmSrDao = (IBmSrDao) WebApplicationManager.getBean("bmSrDaoImpl");
	@SuppressWarnings("unchecked") 
	public String execute(HashMap<String, Object> reqMap) throws Exception {

		try {
			String deviceId;
			String srId = (String) reqMap.get("srvReqId");
			logger.info("ModifyInstanceStateHandler srId:" + srId);
			List<BmSrRrinfoVo> srRrinfoList = bmSrRrinfoDao.findBmSrRrinfoListBySrId(srId);
			for (BmSrRrinfoVo srRrinfoVo : srRrinfoList) {
				List<BmSrRrVmRefPo> vmRefList = bmSrRrVmRefDao.findBmSrRrVmRefListByRrinfoId(srRrinfoVo.getRrinfoId());
				int len = vmRefList == null ? 0 : vmRefList.size();
				for(int i=0 ; i<len ; i++) {
					deviceId = vmRefList.get(i).getDeviceId();
					if(!CommonUtil.isEmpty(deviceId)) {
						CmDevicePo vmDevice = cmDeviceDao().findCmDeviceById(deviceId);
						logger.info("更新的设备为：" + vmDevice.toString());
						vmDevice.setDeviceStatus(DeviceStatusEnum.DEVICE_STATUS_ONLINE.getValue());
						cmDeviceDao().updateCmDeviceState(vmDevice);
						logger.info("更新设备状态完成，设备id：" + deviceId);
					}else {
						logger.debug("ModifyInstanceStateHandler 设备id为空");
					}
				}
			}
			logger.info("srId:["+srId+"],关单开始");
			bmSrDao.updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_CLOSED.getValue());
			logger.info("srId:["+srId+"],关单结束");
		} catch (RollbackableBizException e) {
			logger.error("审批失败，回滚状态失败：", e);
			throw new RollbackableBizException(e);
		}

		return PubConstants.EXEC_RESULT_SUCC;
	}
	private ICmDeviceDAO cmDeviceDao() throws Exception {
		return (ICmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
	}
}
