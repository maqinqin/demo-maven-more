package com.git.cloud.resmgt.common.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.vo.SyncVmInfoVo;
import com.git.cloud.resmgt.common.model.vo.VCenterAlarmVo;
import com.git.cloud.resmgt.common.service.IRmDatacenterService;
import com.git.cloud.resmgt.common.service.ISyncVmInfoService;
import com.git.cloud.resmgt.common.service.IVCenterAlarmService;
import com.git.cloud.resmgt.common.util.JsonUtil;

/**
 * 同步VCenter信息到云平台
 * 
 * @author WangJingxin
 * @date 2016年5月4日 下午4:17:25
 *
 */
@Controller
public class RmVCenterAction {
	@Autowired
	private IRmDatacenterService rmDataCenterHandle;
	@Autowired
	private IVCenterAlarmService syncLogInfoHandle;
	@Autowired
	private ISyncVmInfoService syncVmInfoHandle;

	private static Logger logger = LoggerFactory.getLogger(RmVCenterAction.class);

	/**
	 * 获取Datacenter的url,username,password
	 * 
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getVcInfo", produces = MediaType.APPLICATION_JSON)
	public void getVcUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<RmDatacenterPo> poList = rmDataCenterHandle.getDataCenterAccessData();
		StringBuffer jsonString = new StringBuffer();
		jsonString.append("[");
		if(poList !=null && poList.size()>0){
			for (RmDatacenterPo po : poList) {
				jsonString.append("{");
				jsonString.append("\"URL\":" + "\"" + po.getUrl() + "\",");
				jsonString.append("\"USERNAME\":" + "\"" + po.getUsername() + "\",");
				jsonString.append("\"PASSWORD\":" + "\"" + po.getPassword() + "\"");
				jsonString.append("},");
			}
			jsonString.deleteCharAt(jsonString.length() - 1);
		}
		jsonString.append("]");
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(jsonString.toString());
	}

	/**
	 * 接收vCenter报警信息
	 * 
	 * @throws Exception
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/syncLogInfo", produces = MediaType.APPLICATION_JSON)
	public void syncLogInfo(HttpServletRequest request, HttpServletResponse response) {
		String output = null;
		BufferedReader reader = null;
		StringBuffer dataString = new StringBuffer();
		try {
			request.setCharacterEncoding("UTF-8");
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			while ((output = reader.readLine()) != null) {
				dataString.append(output);
			}
			List<VCenterAlarmVo> voList = JsonUtil.jsonToObject(dataString.toString(),
					new TypeReference<List<VCenterAlarmVo>>() {
					});
			syncLogInfoHandle.saveLogInfo(voList);
		} catch (Exception e) {
			logger.error("异常exception",e);
			logger.error("报警信息数据错误 : " + e);
		}
	}

	/**
	 * 接收虚拟机信息的接口
	 * 
	 * @throws Exception
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/syncVmInfo", produces = MediaType.APPLICATION_JSON)
	public void syncVmInfo(HttpServletRequest request, HttpServletResponse response) {
		logger.info("sync vm info from vmware automation");
		String output = null;
		BufferedReader reader = null;
		StringBuffer dataString = new StringBuffer();
		try {
			request.setCharacterEncoding("UTF-8");
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			while ((output = reader.readLine()) != null) {
				dataString.append(output);
			}
			List<SyncVmInfoVo> voList = JsonUtil.jsonToObject(dataString.toString(),
					new TypeReference<List<SyncVmInfoVo>>() {
			});
			syncVmInfoHandle.saveVMInfo(voList);
		} catch (Exception e) {
			logger.error("异常exception",e);
			logger.error("接收虚拟机信息错误 : " + e);
		}
	}
}
