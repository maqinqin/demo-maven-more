package com.git.cloud.parame.service;

import java.util.HashMap;
import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.AdminParamPo;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.parame.model.po.ParameterPo;

public interface ParameterService {
    public void save(ParameterPo paramPo) throws RollbackableBizException;
	
	public Pagination<HashMap<String, String>> queryPagination(PaginationParam pagination);

	public ParameterPo view(String paramId) throws RollbackableBizException;

	public void delete(String paramId) throws RollbackableBizException;
	//判断参数名是否一样

	public  List<ParameterPo> getParamName(String paramName) throws RollbackableBizException;

	public void update(ParameterPo paramPo) throws RollbackableBizException;

	/**
	 * 查询参数信息，查询条件包括参数ID、参数名、参数值
	 * @param po 查询条件
	 * @return
	 * @throws Exception
	 */
	public <T extends AdminParamPo> List<T> getParams(AdminParamPo po) throws Exception;
	
	/**
	 * 根据参数名称获取参数值
	 * @Title: getParamValueByName
	 * @Description: TODO
	 * @field: @param paramName
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return String
	 * @throws
	 */
	public String getParamValueByName(String paramName) throws RollbackableBizException;
	
	public ParameterPo getbpm(String bmpName)throws RollbackableBizException;
	
	public List<ParameterPo> getAdminParamList() throws RollbackableBizException;

	String updateLog(ParameterPo paramPo) throws RollbackableBizException;
}
