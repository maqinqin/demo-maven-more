package com.git.cloud.excel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.excel.dao.IExcelInfoDao;
import com.git.cloud.excel.model.po.ExcelInfoPo;
import com.git.cloud.excel.service.IExcelInfoService;
import com.git.cloud.foundation.util.UUIDGenerator;

public class ExcelInfoServiceImpl implements IExcelInfoService {

	private IExcelInfoDao iExcelInfoDao;
	
	private ICommonDAO iCommonDAO;
	
	
	
	public void setiCommonDAO(ICommonDAO iCommonDAO) {
		this.iCommonDAO = iCommonDAO;
	}



	public void setiExcelInfoDao(IExcelInfoDao iExcelInfoDao) {
		this.iExcelInfoDao = iExcelInfoDao;
	}



	@Override
	public void insertExcelInfo(ExcelInfoPo excelInfoPo)throws RollbackableBizException {
		excelInfoPo.setId(UUIDGenerator.getUUID());
		iExcelInfoDao.insertExcelInfo(excelInfoPo);

	}



	@Override
	public ExcelInfoPo showExcelInfoByType(HashMap map) throws Exception {
		List<ExcelInfoPo> excelList = new ArrayList<ExcelInfoPo>();
		ExcelInfoPo excelInfo = null;
		excelList = iCommonDAO.findListByParam("selectExcelInfoByType", map);
		for(ExcelInfoPo excelInfoPo : excelList){
			excelInfo = excelInfoPo;
		}
		return excelInfo;
	}



	@Override
	public void updateExcelInfo(ExcelInfoPo excelInfoPo)
			throws RollbackableBizException {
		iExcelInfoDao.updateExcelInfo(excelInfoPo);
		
	}



	@Override
	public List<ExcelInfoPo> showExcelInfoList() throws Exception {
		List<ExcelInfoPo> excelList = iCommonDAO.findAll("selectExcelInfoAll");
		return excelList;
	}

}
