package com.git.cloud.excel.service;

import java.util.HashMap;
import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.excel.model.po.ExcelInfoPo;

public interface IExcelInfoService extends IService {

	public void insertExcelInfo(ExcelInfoPo excelInfoPo)throws RollbackableBizException;
	
	public ExcelInfoPo showExcelInfoByType(HashMap map)throws Exception;
	
	public void updateExcelInfo(ExcelInfoPo excelInfoPo)throws RollbackableBizException;
	
	public List<ExcelInfoPo> showExcelInfoList()throws Exception;
}
