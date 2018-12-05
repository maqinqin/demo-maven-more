package com.git.cloud.parame.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.parame.model.po.ParameterPo;

public interface IParameterDao {
	public void insertParameter(ParameterPo paramPo) throws RollbackableBizException;

	public void updateParameter(ParameterPo paramPo) throws RollbackableBizException;

	public void update(ParameterPo paramPo) throws RollbackableBizException;

	public Pagination<HashMap<String, String>> queryPagination(PaginationParam pagination);

	public ParameterPo view(String paramId) throws RollbackableBizException;

	public List<ParameterPo> getParamName(String paramName) throws RollbackableBizException;

	public ParameterPo getbpm(String bmpName) throws RollbackableBizException;

	public List<ParameterPo> getAdminParamList() throws RollbackableBizException;
	
	/**
	 * 插入Logo图片
	 */
	public void insertParameterLogo(ParameterPo paramPo) throws RollbackableBizException;
	
	
	/**
	 * 插入Logo图片
	 */
	public void updateParameterLogo(ParameterPo paramPo) throws RollbackableBizException;
	
	/**
	 * 查询Logo图片
	 */
	public List<ParameterPo> getParameterLogo() throws RollbackableBizException;


}