package com.git.cloud.parame.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.AdminParamPo;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.parame.dao.IParameterDao;
import com.git.cloud.parame.model.po.ParameterPo;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.taglib.util.Internation;


@Service
public class ParameterServiceImpl implements ParameterService{
	private IParameterDao  parameterDaoImpl=null;
	

	public IParameterDao getParameterDaoImpl() {
		return parameterDaoImpl;
	}


	public void setParameterDaoImpl(IParameterDao parameterDaoImpl) {
		this.parameterDaoImpl = parameterDaoImpl;
	}


	@Override
	public void save(ParameterPo paramPo) throws RollbackableBizException {
		
			parameterDaoImpl.insertParameter(paramPo);
		}


	@Override
	public Pagination<HashMap<String, String>> queryPagination(PaginationParam pagination) {
		Pagination<HashMap<String, String>> pagtion = parameterDaoImpl.queryPagination(pagination);
		for(HashMap<String, String> map : pagtion.getDataList()){
			if("Y".equals(map.get("isEncryption"))){
				map.put("isEncryption", Internation.language("isden"));
			}
			if("N".equals(map.get("isEncryption"))){
				map.put("isEncryption", Internation.language("notden"));
			}
		}
		return pagtion;
	}


	@Override
	public ParameterPo view(String paramId) throws RollbackableBizException {
		
		return parameterDaoImpl.view(paramId);
	}


	@Override
	public void delete(String paramId) throws RollbackableBizException {
		ParameterPo  paramPo=new ParameterPo();
		  
		 paramPo.setParamId(paramId);
		 paramPo.setIsEncryption("N");
		 paramPo.setIsActive(IsActiveEnum.NO.getValue());
		 parameterDaoImpl.update(paramPo);
		
	}

   //判断是否有相同参数
	
	@Override
	public List<ParameterPo> getParamName(String paramName)  throws RollbackableBizException {
		
		List<ParameterPo> list=parameterDaoImpl.getParamName( paramName);
		
		return list;
	}

	/**
	 * 修改系统参数日志
	 *
	 * @param args
	 * @return
	 * @throws RollbackableBizException
	 */
	@Override
	public String updateLog(ParameterPo paramPo) {
		ParameterPo oldParamPo = null;
		try {
			oldParamPo = parameterDaoImpl.view(paramPo.getParamId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "将参数[" + paramPo.getParamName() + "]的值从【" + (oldParamPo == null ? "" : oldParamPo.getParamValue()) + "】修改为【" + paramPo.getParamValue() + "】";
	}

	@Override
	public void update(ParameterPo paramPo) throws RollbackableBizException {
		parameterDaoImpl.updateParameter(paramPo);
		
	}
	
	public ICommonDAO comDAO;

	public ICommonDAO getComDAO() {
		return comDAO;
	}

	public void setComDAO(ICommonDAO comDAO) {
		this.comDAO = comDAO;
	}

	private static Logger logger = LoggerFactory.getLogger(ParameterServiceImpl.class);

	@Override
	public <T extends AdminParamPo> List<T> getParams(AdminParamPo po)
			throws Exception {
		logger.info("获取参数信息 Service Begin......");
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("paramId", po.getParamId());
		map.put("paramName", po.getParamName());
		map.put("paramValue", po.getParamValue());
		map.put("encryptParamValue", PwdUtil.encryption(po.getParamValue()));
		List<T> paramList=comDAO.findListByParam("getParams", map);
		for(T param:paramList){
			if(param.getIsEncryption()!=null && 
				!param.getIsEncryption().equals("") && 
				param.getIsEncryption().equals("Y")){
				String strDcpt=PwdUtil.decryption(param.getParamValue());
				param.setParamValue(strDcpt);
			}
		}
		return paramList;
	}
	
	public String getParamValueByName(String paramName) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("paramName", paramName);
		List<AdminParamPo> paramList = comDAO.findListByParam("getParams", paramMap);
		String paramValue = null;
		if(paramList != null && paramList.size() > 0) {
			paramValue = paramList.get(0).getParamValue();
//			if("Y".equals(paramList.get(0).getIsEncryption())){
//				paramValue = PwdUtil.decryption(paramValue);
//			}
		}
		return paramValue;
	}


	@Override
	public ParameterPo getbpm(String bmpName) throws RollbackableBizException {
		return parameterDaoImpl.getbpm(bmpName);
	}


	@Override
	public List<ParameterPo> getAdminParamList() throws RollbackableBizException {
		return parameterDaoImpl.getAdminParamList();
	}

}