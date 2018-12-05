package com.git.cloud.handler.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.appmgt.model.po.AppSysPO;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.handler.po.CmRoutePo;
import com.git.cloud.handler.vo.OpenstackVmParamVo;
import com.git.cloud.handler.vo.VmDeviceVo;
import com.git.cloud.request.model.po.BmSrPo;
import com.git.cloud.resmgt.common.model.po.CmLocalDiskPo;
import com.git.cloud.resmgt.common.model.po.CmSeatPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmGeneralServerPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;


public class AutomationDAOImpl extends CommonDAOImpl implements
		AutomationDAO {
	@Override
	public RmDatacenterPo getDatacenter(String rrInfoId) throws BizException {
		return this.findObjectByID("handler_getDatacenter", rrInfoId);
	}

	@Override
	public RmDatacenterPo getDatacenterByDeviceId(String deviceId)
			throws BizException {
		return this.findObjectByID("handler_getDatacenterByDeviceId", deviceId);
	}
	
	@Override
	public RmDatacenterPo getDatacenterByDeviceIdInResPool(String deviceId)
			throws BizException {
		return this.findObjectByID("handler_getDatacenterByDeviceIdByResPool", deviceId);
	}

	@Override
	public String getDatastoreName(String vmId) throws BizException {
		CmLocalDiskPo disk = this.findObjectByID("handler_getDatastoreName", vmId);
		return disk == null ? "" : disk.getName();
	}

	@Override
	public List<CmRoutePo> getRoutes(String datacenterId) throws BizException {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("datacenterId", datacenterId);
		return this.findListByParam("handler_getRoutesByDcId", paramMap);
	}
	@Override
	public BmSrPo getSrById(String srId) throws BizException {
		return this.findObjectByID("handler_getBmSrPo",srId);
	}

	@Override
	public RmVmManageServerPo getVmManagementServer(String datacenterId,
			String type) throws BizException {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("datacenterId", datacenterId);
		paramMap.put("type", type);
		List<RmVmManageServerPo> vmsList = this.findListByParam("handler_getVmManagementServers", paramMap);
		RmVmManageServerPo vms = null;
		if(vmsList != null && vmsList.size() > 0) {
			vms = vmsList.get(0);
		}
		return vms;
	}

	@Override
	public List<RmVmManageServerPo> getVmManagementServers(String datacenterId,
			String type) throws BizException {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("datacenterId", datacenterId);
		paramMap.put("vmType", type);
		return this.findListByParam("handler_getVmManagementServers", paramMap);
	}

	@Override
	public List<CmVmPo> getVms(List<String> deviceIdList) throws BizException {
		StringBuffer condition = new StringBuffer();
		int len = deviceIdList == null ? 0 : deviceIdList.size();
		if(len > 0) {
			condition.append("(");
			for(int i=0 ; i<len ; i++) {
				if(i>0) {
					condition.append(",");
				}
				condition.append("'");
				condition.append(deviceIdList.get(i));
				condition.append("'");
			}
			condition.append(")");
			Map<String, String> paramMap = new HashMap<String, String> ();
			paramMap.put("deviceIds", condition.toString());
			return this.findListByParam("handler_getVms", paramMap);
		} else {
			throw new BizException("传入的设备列表Id为空");
		}
	}
	
	public List<RmGeneralServerPo> getGaneralServers(String datacenterId, String type) throws BizException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("datacenterId", datacenterId);
		map.put("type", type);
		return this.findListByParam("handler_getGeneralServers", map);
	}
	
	public List<VmDeviceVo> getVmVcenter(String rrinfoId) throws BizException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("rrinfoId", rrinfoId);
		return this.findListByParam("handler_getVmVcenter", map);
	}
	
	public String getSeatCodeByVmId(String deviceId) throws BizException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("deviceId", deviceId);
		List<CmSeatPo> seatList = this.findListByParam("handler_getSeatCodeByVmId", map);
		String seatCode = "";
		if(seatList != null && seatList.size() > 0) {
			seatCode = seatList.get(0).getSeatCode();
		}
		return seatCode;
	}
	
	public String getSeatCodeByHostId(String deviceId) throws BizException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("deviceId", deviceId);
		List<CmSeatPo> seatList = this.findListByParam("handler_getSeatCodeByHostId", map);
		String seatCode = "";
		if(seatList != null && seatList.size() > 0) {
			seatCode = seatList.get(0).getSeatCode();
		}
		return seatCode;
	}
	
	public String getParentSeatCodeByCode(String seatCode) throws BizException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("seatCode", seatCode);
		List<CmSeatPo> seatList = this.findListByParam("handler_getParentSeatCodeByCode", map);
		String parentSeatCode = "";
		if(seatList != null && seatList.size() > 0) {
			parentSeatCode = seatList.get(0).getParentCode();
		}
		return parentSeatCode;
	}
	
	public RmGeneralServerVo findDeviceServerInfo(String deviceId) throws BizException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("deviceId", deviceId);
		List<RmGeneralServerVo> serverList = this.findListByParam("handler_findDeviceServerInfo", map);
		RmGeneralServerVo server = null;
		if(serverList != null && serverList.size() > 0) {
			server = serverList.get(0);
		}
		return server;
	}
	
	public AppSysPO findAppInfoBySrId(String srId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("srId", srId);
		List<AppSysPO> appList = this.findListByParam("handler_findAppInfoBySrId", map);
		if(appList != null && appList.size() > 0) {
			return appList.get(0);
		}
		return null;
	}
	
	public OpenstackVmParamVo findOpenstackVmParamByVmId(String vmId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("vmId", vmId);
		List<OpenstackVmParamVo> list = this.findListByParam("handler_findOpenstackVmParamByVmId", map);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
}