package com.git.cloud.sys.action;

import java.io.ByteArrayInputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.model.AdminParamPo;
import com.git.cloud.parame.model.po.ParameterPo;
import com.git.cloud.sys.model.po.MenuPo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.service.MenuService;
import com.git.cloud.sys.service.UpdateLogoService;

/**
 * 修改Logo
 * 
 * @author
 *
 */
@SuppressWarnings("unchecked")
public class UpdateLogoAction extends BaseAction {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 1L;

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public UpdateLogoService getUpdateLogoService() {
		return updateLogoService;
	}

	public void setUpdateLogoService(UpdateLogoService updateLogoService) {
		this.updateLogoService = updateLogoService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private UpdateLogoService updateLogoService;

	public UpdateLogoAction() {
		super();
	}

	/**
	 * 获取logo
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getLogo() throws Exception {
		List<ParameterPo> list = updateLogoService.getLogo();
		this.jsonOut(list);
	}

	/**
	 * 修改Logo
	 * 
	 * @return
	 * @throws Exception
	 */
	public void updateLogo() throws Exception {
		ParameterPo parameterPo = new ParameterPo();
		String paramName = this.getRequest().getParameter("paramName");
		String paramImg = this.getRequest().getParameter("paramImg");
		parameterPo.setParamName(paramName);
		parameterPo.setParamLogo(paramImg);
		String result = updateLogoService.insertLogo(parameterPo);
		this.stringOut(result);
		//this.jsonOut(result);
	}

}
