package com.git.cloud.resmgt.compute.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.git.cloud.common.action.BaseController;
import com.git.cloud.resmgt.common.service.ICmHostService;
import com.git.cloud.resmgt.compute.handler.VmControllerService;
import com.git.cloud.resmgt.compute.model.vo.PhysicsMachineVo;
import com.git.cloud.resmgt.compute.service.IRmComputeVmListService;
@Controller
@SuppressWarnings({ "rawtypes", "serial" })
public class RmComputeVmListAction extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IRmComputeVmListService iRmComputeVmListService;
	@Autowired
	private ICmHostService cmHostServiceImpl;

	@Resource(name="vmControllers")
	private VmControllerService vmControllerService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateHostStorage.mvc")
	public String updateHostStorage(HttpServletRequest req){
		String ret = "";
		try {
			String sourceCId = req.getParameter("devceId");
			String sourceCPU = req.getParameter("sourceCPU");
			String sourceMEM = req.getParameter("sourceMEM");
			String vid = req.getParameter("vid");
			PhysicsMachineVo physicsMachineVo = new PhysicsMachineVo();
			physicsMachineVo.setCpuUsed(sourceCPU);
			physicsMachineVo.setRamUsed(sourceMEM);
			physicsMachineVo.setDevceId(sourceCId);
			physicsMachineVo.setVid(vid);
			iRmComputeVmListService.updateVCMVMId(physicsMachineVo);
			//更新物理机已用cpu和已用内存
			cmHostServiceImpl.updateCmHostUsed();
			//iRmComputeVmListService.updateHostStorage(physicsMachineVo);
		} catch (Exception e) {
			ret = "失败";
			logger.error("异常exception",e);
		}
		return ret;
	}
}