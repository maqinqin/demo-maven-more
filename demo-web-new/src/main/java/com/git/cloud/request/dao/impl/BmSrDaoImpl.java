package com.git.cloud.request.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.git.cloud.appmgt.model.po.AppStatPo;
import com.git.cloud.appmgt.model.vo.AppStatVo;
import com.git.cloud.appmgt.model.vo.AppSysKpiVo;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.DateUtil;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.model.SrStatusCodeEnum;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.model.vo.BmToDoVo;
import com.git.cloud.request.tools.SrDateUtil;
import com.git.cloud.resmgt.common.model.DeviceStatusEnum;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;

/**
 * 服务申请数据层实现类
 * @ClassName:BmSrDaoImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-9-30 上午10:02:47
 */
@Repository
public class BmSrDaoImpl extends CommonDAOImpl implements IBmSrDao {

	public void insertBmSr(BmSrVo bmSrVo) throws RollbackableBizException {
		this.save("insertBmSr", bmSrVo);
	}
	
	public void updateBmSr(BmSrVo bmSrVo) throws RollbackableBizException {
		this.update("updateBmSr", bmSrVo);
	}
	
	public void updateBmSrStatus(String srId, String srStatusCode) throws RollbackableBizException {
		BmSrVo bmSrVo = new BmSrVo();
		bmSrVo.setSrId(srId);
		if(srStatusCode.equals(SrStatusCodeEnum.REQUEST_CLOSED.getValue())) {
			bmSrVo.setCloseTime(SrDateUtil.getSrFortime(new Date()));
		}
		bmSrVo.setSrStatusCode(srStatusCode);
		this.update("updatetBmSrStatus", bmSrVo);
	}
	
	public void updateAssignResult(String srId, String assignResult) throws RollbackableBizException {
		BmSrVo bmSrVo = new BmSrVo();
		bmSrVo.setSrId(srId);
		bmSrVo.setAssignResult(assignResult);
		this.update("updateAssignResult", bmSrVo);
	}
	
	public BmSrVo findBmSrVoById(String srId) throws RollbackableBizException {
		return this.findObjectByID("findRrByIdBmSr",srId);
	}
	
	public BmSrVo findBmSrVoBySrCode(String srCode) throws RollbackableBizException {
		return this.findObjectByID("findBmSrVoBySrCode",srCode);
	}
	
	public List<BmSrVo> findNewestCompleteRequest(int num) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("srStatusClose", SrStatusCodeEnum.REQUEST_CLOSED.getValue());
		paramMap.put("num", num);
		return this.findListByParam("findNewestCompleteRequest", paramMap);
	}

	public List<BmSrVo> findNewestCreateRequest(int num, String creatorId) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("num", num);
		paramMap.put("userId", creatorId);
		return this.findListByParam("findNewestCreateRequest", paramMap);
	}

	public List<BmToDoVo> findNewestWaitDealRequest(int num, String creatorId, String roleIds) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("num", num);
		paramMap.put("userId", creatorId);
		paramMap.put("roleIds", roleIds);
		return this.findListByParam("findNewestWaitDealRequest", paramMap);
	}
	
	public List<AppSysKpiVo> findAppSysVirtualServer() throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("deviceStatus", DeviceStatusEnum.DEVICE_STATUS_ONLINE.getValue());
		return this.findListByParam("findAppSysVirtualServer", paramMap);
	}
	
	public List<AppSysKpiVo> findAppSysCompleteRequest() throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("srStatusClose", SrStatusCodeEnum.REQUEST_CLOSED.getValue());
		String year = DateUtil.getCurrentDataString("yyyy");
		String month = DateUtil.getCurrentDataString("MM");
		paramMap.put("closeStartTime", year + "-" + month + "-" + "01");
		paramMap.put("closeEndTime", year + "-" + (Integer.valueOf(month) + 1) + "-" + "01");
		return this.findListByParam("findAppSysCompleteRequest", paramMap);
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public void updateDeviceState(String srId) throws RollbackableBizException {
		List<String> devIds = this.getSqlMapClientTemplate().queryForList("findDeviceIdsBySrId", srId);
		CmDevicePo dev = null;
		if(devIds != null && devIds.size() != 0){
			dev = new CmDevicePo();
			for(String devId : devIds){
				dev.setId(devId);
				dev.setRunningState("poweron");
				this.getSqlMapClientTemplate().update("updateCmdeviceRunningState", dev);
			}
		}
	}
	@SuppressWarnings({ "unchecked"})
	@Override
	public List<AppStatVo> findAppStatBySrId(String srId)
			throws RollbackableBizException {
		List<AppStatVo> list = this.getSqlMapClientTemplate().queryForList("findAppStatBySrId", srId);
		return list;
	}

	@Override
	public String findTenatIdByUserId(String userId) throws RollbackableBizException{
		return  (String) this.getSqlMapClientTemplate().queryForObject("selectTenantByUserId", userId);
	}

	@Override
	public BmToDoVo getCloudRequestWaitDealBySrId(String srId) {
		return (BmToDoVo) this.getSqlMapClientTemplate().queryForObject("getCloudRequestWaitDealBySrId", srId);
	}

}