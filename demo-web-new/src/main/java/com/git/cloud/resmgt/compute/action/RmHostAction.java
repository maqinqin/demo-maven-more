package com.git.cloud.resmgt.compute.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.git.cloud.cloudservice.model.po.CloudServiceVo;
import com.git.cloud.cloudservice.service.ICloudServiceService;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.enums.RmPlatForm;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.resmgt.compute.handler.PmHandlerService;
import com.git.cloud.resmgt.compute.model.po.DuPoByRmHost;
import com.git.cloud.resmgt.compute.model.vo.IpRules;
import com.git.cloud.resmgt.compute.model.vo.JqGridJsonVo;
import com.git.cloud.resmgt.compute.model.vo.SaveReturnVo;
import com.git.cloud.resmgt.compute.model.vo.ScanVmResultVo;
import com.git.cloud.resmgt.compute.model.vo.VmVo;
import com.git.cloud.resmgt.compute.service.IRmHostService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@Controller
@RequestMapping("/vmScan")
public class RmHostAction extends BaseAction {
	/**
	 * 
	 */
	private static Logger logger = LoggerFactory.getLogger(RmHostAction.class);
	private static final long serialVersionUID = 7601848955932972061L;
	private IRmHostService rmHostService ;
	private PmHandlerService ipmHandlerService;
	public PmHandlerService getPmHandlerService() {
		return ipmHandlerService;
	}
	@Autowired
	public void setPmHandlerService(PmHandlerService pmHandlerService) {
		this.ipmHandlerService = pmHandlerService;
	}
	public IRmHostService getRmHostService() {
		return rmHostService;
	}
	@Autowired
	public void setRmHostService(IRmHostService rmHostService) {
		this.rmHostService = rmHostService;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/listvm")
	public void ListVm (String hostId,String hostType,HttpServletRequest req , HttpServletResponse resp) throws Exception
	{
		try {
			PaginationParam paginationParam = this.getPaginationParamByReq(req);
			Integer pageSize = paginationParam.getPageSize();
			Integer currentPage = paginationParam.getCurrentPage();
			
			VmVo vm = new VmVo();
			vm.setHostType(hostType);
			vm.setHostId(hostId);
			
			List <ScanVmResultVo> dataList = rmHostService.scanVmList(vm);
			List <ScanVmResultVo> pageDataList = new ArrayList();
			int indexStart = paginationParam.getPageSize()*(paginationParam.getCurrentPage()-1);
			int indexEnd = paginationParam.getPageSize()*paginationParam.getCurrentPage()>dataList.size()?dataList.size():paginationParam.getPageSize()*paginationParam.getCurrentPage();
			if(paginationParam.getCurrentPage() != null && 1 != paginationParam.getCurrentPage()){
				for(int i = indexStart; i < indexEnd; i++){
					pageDataList.add(dataList.get(i));
				}
			}else{
				pageDataList.addAll(dataList);
			}
			
			JqGridJsonVo jqVo = new JqGridJsonVo();
			jqVo.setrows(pageDataList);
			jqVo.setPage(currentPage);
			jqVo.setRecord(dataList.size());
			int total = jqVo.getRecord() / pageSize + (jqVo.getRecord() % pageSize == 0 ? 0 : 1);
			jqVo.setTotal(total);
			
			String jsonString = JSONObject.fromObject(jqVo).toString();
			resp.setContentType("text/json;charset=utf-8");
			resp.getWriter().print(jsonString);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
	}
	
	
	@RequestMapping("/listrules")
	public void getIpRules(HttpServletResponse resp,String HostId,String cloudServiceId) throws IOException
	{
		VmVo vm = new VmVo();
		vm.setHostId(HostId);
		vm.setCloudService(cloudServiceId);
		List <IpRules> rules = rmHostService.getIpRules(vm);
		String ss =JSONArray.fromObject(rules).toString();
		resp.setContentType("text/json;charset=utf-8");
		resp.getWriter().print(ss);
	}
	@RequestMapping("/getdulist")
	public void getAppDuList(HttpServletResponse resp,String cloudServiceId)throws IOException
	{
		VmVo vm = new VmVo();
		vm.setCloudService(cloudServiceId);
		List<DuPoByRmHost> dulist = rmHostService.getDuList(vm);
		String json = JSONArray.fromObject(dulist).toString();
		resp.setContentType("text/json;charset=utf-8");
		resp.getWriter().print(json);
	}
	
	@RequestMapping("/getPmSensorInfo")
	public void getPmSensorInfo(HttpServletResponse resp,String pmId,String pmIp,String ipmiVer,String ipmiName,String ipmiPword) throws Exception{
		List<String[]> list=ipmHandlerService.getPmSensorInfo(pmId,pmIp,ipmiVer,ipmiName,ipmiPword);
		String hostStatus =JSONArray.fromObject(list).toString();
		resp.setContentType("text/json;charset=utf-8");
		resp.getWriter().print(hostStatus);
	}
	@RequestMapping("/getPmSensorInfoById")
	public void getPmSensorInfoById(HttpServletResponse resp,String hostId)throws Exception{
		List<String[]> list=ipmHandlerService.getPmSensorInfoById(hostId);
		String hostStatus =JSONArray.fromObject(list).toString();
		resp.setContentType("text/json;charset=utf-8");
		resp.getWriter().print(hostStatus);
	}
	
	@RequestMapping("/pmCloseByVc")
	public void pmCloseByVc(HttpServletResponse resp,String pmId) throws Exception{
		String result=ipmHandlerService.pmCloseByVc(pmId);
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().print(result);
	}
	@RequestMapping("/pmRebootByVc")
	public void pmRebootByVc(HttpServletResponse resp,String pmId) throws Exception{
		String result=ipmHandlerService.pmRebootByVc(pmId);
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().print(result);
	}
	@RequestMapping("/maintenanceMode")
	public void maintenanceMode(HttpServletResponse resp,String pmId) throws Exception{
		String result=ipmHandlerService.maintenanceMode(pmId);
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().print(result);
	}
	@RequestMapping("/exitMaintenanceMode")
	public void exitMaintenanceMode(HttpServletResponse resp,String pmId) throws Exception{
    	String result=ipmHandlerService.exitMaintenanceMode(pmId);
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().print(result);
	}
}
