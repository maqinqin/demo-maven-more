package com.git.cloud.sys.service.impl;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.common.model.AdminParamPo;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.parame.dao.IParameterDao;
import com.git.cloud.parame.model.po.ParameterPo;
import com.git.cloud.sys.model.vo.SelfMonitorVo;
import com.git.cloud.sys.service.UpdateLogoService;

/**
 * @author 作者 :
 * @date 创建时间：2018年8月27日 下午5:24:49 类说明 ：
 */
public class UpdateLogoServiceImpl implements UpdateLogoService {

	private IParameterDao parameterDaoImpl;

	public IParameterDao getParameterDaoImpl() {
		return parameterDaoImpl;
	}

	public void setParameterDaoImpl(IParameterDao parameterDaoImpl) {
		this.parameterDaoImpl = parameterDaoImpl;
	}

	@Override
	public List<ParameterPo> getLogo() throws Exception {
		return parameterDaoImpl.getParameterLogo();
	}

	@Override
	public String insertLogo(ParameterPo parameterPo) throws Exception {
		String uuid = UUIDGenerator.getUUID();
		String result = "success";
		int temp = 0;

		parameterPo.setIsActive(IsActiveEnum.YES.getValue());
		parameterPo.setIsEncryption("N");
		parameterPo.setParamValue("0");

		List<ParameterPo> list = this.getLogo();
		if (null != list || list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String name = list.get(i).getParamName();
				if (name.equals(parameterPo.getParamName())) {
					temp = 1;
					break;

				} else {
					temp = 0;
				}
			}
		} else {
			temp = 0;
		}

		if (temp == 1) {
			try {
				parameterDaoImpl.updateParameterLogo(parameterPo);
				return result;
			} catch (Exception e) {
				result = "fail";
				return result;
			}
		} else {
			try {
				parameterPo.setParamId(uuid);
				parameterDaoImpl.insertParameterLogo(parameterPo);
				return result;
			} catch (Exception e) {
				
				result = "fail";
				return result;
			}
		}
	}
}
