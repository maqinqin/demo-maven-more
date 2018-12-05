package com.git.cloud.reports.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.reports.dao.IReportDao;
import com.git.cloud.reports.model.po.ConProParamPo;
import com.git.cloud.reports.model.po.CreateReportParamPo;
import com.git.cloud.reports.model.po.PropertyParamPo;
import com.git.cloud.reports.model.po.SqlParamPo;
import com.sun.xml.bind.v2.TODO;

public class ReportDaoImpl extends CommonDAOImpl implements IReportDao{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void save(CreateReportParamPo crpo) throws RollbackableBizException {
		try{
			//插入REPORT表
			this.getSqlMapClientTemplate().insert("Report.insert", crpo);
			
			//插入REPORT_CONDITION表
			List<ConProParamPo> conList = crpo.getConProList();
			this.getSqlMapClientTemplate().insert("ReportCondition.insert", conList);
			 
			//插入REPORT_CONDITION_PROPERTY表(todo:batchinsert)
			List<PropertyParamPo> proList = null;
			for(ConProParamPo cp : crpo.getConProList()){
				proList = cp.getProList();
				if(proList.size() > 0){		//若条件为text类型，则不插入属性。
					this.getSqlMapClientTemplate().insert("ReportConditionProperty.insert",proList);
				}
			}
			
			//插入REPORT_SQL表
			List<SqlParamPo> sqlList = crpo.getSqlList();
			this.getSqlMapClientTemplate().insert("ReportSql.insert",sqlList);
			
		}catch(Exception e){
			logger.error("异常exception",e);
		}
	}

	@Override
	public CreateReportParamPo selectReportParam(String id) throws RollbackableBizException {
		return (CreateReportParamPo) this.getSqlMapClientTemplate().queryForObject("selectReportInfo", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConProParamPo> selectConParam(String id) throws RollbackableBizException {
		return this.getSqlMapClientTemplate().queryForList("selectReportInfoOfCondition", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PropertyParamPo> selectProParam(String id) throws RollbackableBizException {
		return this.getSqlMapClientTemplate().queryForList("selectReportInfoOfProperty", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SqlParamPo> selectSqlParam(String id) throws RollbackableBizException {
		return this.getSqlMapClientTemplate().queryForList("selectReportInfoOfSql", id);
	}
	@Override
	public void update(CreateReportParamPo crpo) throws RollbackableBizException {
		this.getSqlMapClientTemplate().update("Report.update", crpo);
		
		List<ConProParamPo> conList = crpo.getConProList();
		for( ConProParamPo cppo : conList){
			this.getSqlMapClientTemplate().insert("ReportCondition.update", cppo);
			List<PropertyParamPo> proList = cppo.getProList();
			if(proList.size() > 0){
				for(PropertyParamPo pppo : proList){
					this.getSqlMapClientTemplate().insert("ReportConditionProperty.update",pppo);
				}
			}
		}
		
		List<SqlParamPo> sqlList = crpo.getSqlList();
		for(SqlParamPo sPo : sqlList){
			this.getSqlMapClientTemplate().insert("ReportSql.update",sPo);
		}
	}

	@Override
	public CreateReportParamPo selectReportByName(String reportName)
			throws RollbackableBizException {
		return (CreateReportParamPo) this.getSqlMapClientTemplate().queryForObject("selectReportByName", reportName);
	}
	
	@Override
	public void updateReport(CreateReportParamPo crpo)
			throws RollbackableBizException {
		this.getSqlMapClientTemplate().update("Report.update", crpo);
		
	}

	@Override
	public void updateReportCondition(ConProParamPo cppo)
			throws RollbackableBizException {
		this.getSqlMapClientTemplate().insert("ReportCondition.update", cppo);
		
	}

	@Override
	public void updateReportProperty(PropertyParamPo pppo)
			throws RollbackableBizException {
		this.getSqlMapClientTemplate().insert("ReportConditionProperty.update",pppo);
		
	}

	@Override
	public void updateReportSql(SqlParamPo sPo)
			throws RollbackableBizException {
		this.getSqlMapClientTemplate().insert("ReportSql.update",sPo);
	}

	@Override
	public void saveReport(CreateReportParamPo crpo)
			throws RollbackableBizException {
		this.getSqlMapClientTemplate().insert("Report.insert", crpo);
		
	}

	@Override
	public void saveReportCondition(ConProParamPo cppo)
			throws RollbackableBizException {
		List<ConProParamPo> conList = new ArrayList<ConProParamPo>();
		conList.add(cppo);
		this.getSqlMapClientTemplate().insert("ReportCondition.insert", conList);
		
	}

	@Override
	public void saveReportProperty(PropertyParamPo pppo)
			throws RollbackableBizException {
		List<PropertyParamPo> proList = new ArrayList<PropertyParamPo>();
		proList.add(pppo);
		this.getSqlMapClientTemplate().insert("ReportConditionProperty.insert",proList);
		
	}

	@Override
	public void saveReportSql(SqlParamPo sPo) throws RollbackableBizException {
		List<SqlParamPo> sqlList = new ArrayList<SqlParamPo>();
		sqlList.add(sPo);
		this.getSqlMapClientTemplate().insert("ReportSql.insert",sqlList);
		
	}
	
	@Override
	public void resetReport(String id) throws RollbackableBizException {
		this.getSqlMapClientTemplate().insert("resetReport.insert",id);
	}
	
	@Override
	public void resetReportCondition(String id) throws RollbackableBizException {
		this.getSqlMapClientTemplate().insert("resetReportCondition.insert",id);
	}
	
	@Override
	public void resetReportConditionProperty(String id) throws RollbackableBizException {
		this.getSqlMapClientTemplate().insert("resetReportConditionProperty.insert",id);
	}
	
	@Override
	public void resetReportSql(String id) throws RollbackableBizException {
		this.getSqlMapClientTemplate().insert("resetReportSql.insert",id);
	}
}
