package com.git.cloud.excel.dao;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.excel.model.po.ExcelInfoPo;

public interface IExcelInfoDao extends ICommonDAO {

	public void insertExcelInfo(ExcelInfoPo excelInfoPo)throws RollbackableBizException;
	
	public void updateExcelInfo(ExcelInfoPo excelInfoPo)throws RollbackableBizException;
}
