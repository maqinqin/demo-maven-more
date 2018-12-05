package com.git.cloud.parame.dao.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.parame.dao.IParameterDao;
import com.git.cloud.parame.model.po.ParameterPo;
import com.git.support.util.PwdUtil;



public class ParameterDaoImpl extends CommonDAOImpl implements IParameterDao {

	

	public void insertParameter(ParameterPo paramPo) throws RollbackableBizException {
		this.save("param.insert",paramPo);
	}
	
	public void updateParameter(ParameterPo paramPo) throws RollbackableBizException {
		this.save("param.update",paramPo);
		
	}

	@Override
	public Pagination<HashMap<String, String>> queryPagination(PaginationParam pagination) {
		return this.pageQuery("selectParamTotal", "selectParamtPage", pagination);
	}

	@Override
	public ParameterPo view(String paramId) throws RollbackableBizException {
		Map<String,String> map=new HashMap<String,String>();
		map.put("paramId", paramId);
		ParameterPo po=super.findObjectByMap("param.getParamById", map);
		//判断数据是否已经过加密，若已加密则解密后返回。
		if("Y".equals(po.getIsEncryption())){		
			po.setParamValue(PwdUtil.decryption(po.getParamValue()));
		}
		return po; 
	}

	@Override
	public void update(ParameterPo paramPo) throws RollbackableBizException {
		update("param.delete",paramPo);
		
	}

	@Override
	public List<ParameterPo> getParamName(String paramName)throws RollbackableBizException {
		List<ParameterPo> list = super.findByID("param.compare", paramName);
		return list;
	}

	@Override
	public ParameterPo getbpm(String bmpName) throws RollbackableBizException {
		Map<String,String> map=new HashMap<String,String>();
		map.put("bmpName", bmpName);
		ParameterPo po=super.findObjectByMap("getbpm", map);
		return po;
	}

	@Override
	public List<ParameterPo> getAdminParamList() throws RollbackableBizException {
		List<ParameterPo> list = super.findAll("selectAdminParamList");
		return list;
	}
	
	
	
	@Override
	public void insertParameterLogo(ParameterPo paramPo) throws RollbackableBizException {
		this.save("param.insertlogo",paramPo);
	}
	
	@Override
	public void updateParameterLogo(ParameterPo paramPo) throws RollbackableBizException {
		this.save("param.updatelogo",paramPo);
		
	}
	
	
	@Override
	public List<ParameterPo> getParameterLogo() throws RollbackableBizException {
		List<ParameterPo> list = super.findAll("param.selectlogo");
		return list;
	}
}
