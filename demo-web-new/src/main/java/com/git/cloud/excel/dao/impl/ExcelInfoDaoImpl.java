package com.git.cloud.excel.dao.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.excel.dao.IExcelInfoDao;
import com.git.cloud.excel.model.po.ExcelInfoPo;

public class ExcelInfoDaoImpl extends CommonDAOImpl implements IExcelInfoDao {

	@Override
	public void insertExcelInfo(ExcelInfoPo excelInfoPo)
			throws RollbackableBizException {
	
		save("insertExcelInfo", excelInfoPo);
	}

	@Override
	public void updateExcelInfo(ExcelInfoPo excelInfoPo)
			throws RollbackableBizException {
		update("updateExcelInfo", excelInfoPo);
		
	}

}
