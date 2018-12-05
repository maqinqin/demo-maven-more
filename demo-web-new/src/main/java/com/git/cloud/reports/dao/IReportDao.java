package com.git.cloud.reports.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.reports.model.po.ConProParamPo;
import com.git.cloud.reports.model.po.CreateReportParamPo;
import com.git.cloud.reports.model.po.PropertyParamPo;
import com.git.cloud.reports.model.po.SqlParamPo;

public interface IReportDao extends ICommonDAO {
	
	public void save(CreateReportParamPo crpo) throws RollbackableBizException;

	public CreateReportParamPo selectReportParam(String id) throws RollbackableBizException;

	public List<ConProParamPo> selectConParam(String id) throws RollbackableBizException;

	public List<PropertyParamPo> selectProParam(String id) throws RollbackableBizException;

	public List<SqlParamPo> selectSqlParam(String id) throws RollbackableBizException;

	public void update(CreateReportParamPo crpo) throws RollbackableBizException;
	
	public void saveReport(CreateReportParamPo crpo) throws RollbackableBizException;
	
	public void saveReportCondition(ConProParamPo cppo) throws RollbackableBizException;
	
	public void saveReportProperty(PropertyParamPo pppo) throws RollbackableBizException;
	
	public void saveReportSql(SqlParamPo sPo) throws RollbackableBizException;
	
	public void updateReport(CreateReportParamPo crpo) throws RollbackableBizException;
	
	public void updateReportCondition(ConProParamPo cppo) throws RollbackableBizException;
	
	public void updateReportProperty(PropertyParamPo pppo) throws RollbackableBizException;
	
	public void updateReportSql(SqlParamPo sPo) throws RollbackableBizException;
	
	public CreateReportParamPo selectReportByName(String reportName) throws RollbackableBizException;

	public void resetReport(String id) throws RollbackableBizException;

	public void resetReportCondition(String id) throws RollbackableBizException;

	public void resetReportConditionProperty(String id) throws RollbackableBizException;

	public void resetReportSql(String id) throws RollbackableBizException;

}
