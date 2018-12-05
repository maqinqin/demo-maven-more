package com.git.cloud.dic.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.dic.dao.IDicDao;
import com.git.cloud.dic.model.po.DicPo;
import com.git.cloud.dic.model.po.DicTypePo;
import com.git.cloud.dic.service.IDicService;
import com.git.cloud.foundation.util.DateUtil;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.sys.model.po.SysUserPo;

/**
 * 字典类型管理
 * 
 * @description:
 * @author: wangdy
 * @Date: Dec 29, 2014
 * @modify：
 * @version: 1.0
 * @Company: 高伟达软件股份有限公司
 */

public class DicServiceImpl implements IDicService {
	
	private static Logger logger = LoggerFactory.getLogger(DicServiceImpl.class);
	private IDicDao dicDao;
	private static String mat = "yyyy-MM-dd HH:mm:ss";
	
	public Pagination<DicTypePo> findDicTypePage(PaginationParam paginationParam)
			throws RollbackableBizException {
		return dicDao.findDicTypePage(paginationParam);
	}


	@Override
	public String deleteDicType(String id,String dicTypeCode) throws RollbackableBizException {
		String result = "1";
		try {
		  if(dicDao.haveDic(dicTypeCode)){
		     dicDao.deleteDicType(id);
		  }else{
			  result = "2";
		  }
		} catch (Exception e) {
			result = "0";
			logger.error("删除字典类型时发生异常:"+e);
		}
		return result;
	}


	@Override
    public String insertDicType(DicTypePo dicTypePo) throws RollbackableBizException {
    	try{
			String time = DateUtil.format(new Date(), mat);
			SysUserPo shiroUser = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
			 dicTypePo.setDicTypeCode(UUIDGenerator.getUUID());
			 dicTypePo.setCreateUser(shiroUser == null ? "" : shiroUser.getLoginName());
			 dicTypePo.setCreateDateTime(DateUtil.parse(time, mat));
		     dicDao.saveDicType(dicTypePo);
		} catch (Exception e) {
			logger.error("保存字典类型时发生异常!");
			throw new RollbackableBizException(e.getMessage());
		}
    	return "success";
    }

	@Override
	public String updateDicType(DicTypePo dicTypePo) throws RollbackableBizException {
		try{
			String time = DateUtil.format(new Date(), mat);
			SysUserPo shiroUser = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
			String userName = shiroUser == null ? "" : shiroUser.getLoginName();
			dicTypePo.setUpdateUser(userName);
			dicTypePo.setUpdateDateTime(DateUtil.parse(time, mat));
			dicDao.updateDicType(dicTypePo);
		} catch (Exception e) {
			logger.error("保存字典类型时发生异常!");
			throw new RollbackableBizException(e.getMessage());
		}
		return "success";
	}
	
	public DicTypePo findDicTypeByCode(String dicTypeCode) throws RollbackableBizException {
	     return dicDao.findDicTypeByCode(dicTypeCode);
	}
	
	@Override
	public String deleteDic(String id) throws RollbackableBizException {
		String result = "1";
		try {
		  dicDao.deleteDic(id);
		} catch (Exception e) {
			result = "0";
			logger.error("删除字典信息时发生异常:"+e);
		}
		return result;
	}


	@Override
	public DicPo findDicById(String dicId) throws RollbackableBizException {
		return dicDao.findDicById(dicId);
	}


	@Override
	public Pagination<DicPo> findDicPage(PaginationParam paginationParam)
			throws RollbackableBizException {
		return dicDao.findDicPage(paginationParam);
	}

	@Override
	public String insertDic(DicPo dicPo) throws RollbackableBizException {
		try{
			String time = DateUtil.format(new Date(), mat);
			SysUserPo shiroUser = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
			dicPo.setDicId(UUIDGenerator.getUUID());
			dicPo.setCreateUser(shiroUser == null ? "" : shiroUser.getLoginName());
			dicPo.setCreateDateTime(DateUtil.parse(time, mat));
		    dicDao.saveDic(dicPo);
		} catch (Exception e) {
			logger.error("保存字典信息时发生异常!");
			throw new RollbackableBizException(e.getMessage());
		}
		return "success";
	}

	@Override
	public String updateDic(DicPo dicPo) throws RollbackableBizException {
		try{
			String time = DateUtil.format(new Date(), mat);
			SysUserPo shiroUser = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
			String userName = shiroUser == null ? "" : shiroUser.getLoginName();
			dicPo.setUpdateUser(userName);
			dicPo.setUpdateDateTime(DateUtil.parse(time, mat));
			dicDao.updateDic(dicPo);
		} catch (Exception e) {
			logger.error("保存字典信息时发生异常!");
			throw new RollbackableBizException(e.getMessage());
		}
		return "success";
	}
	
	/************************************setter&&getter*******************************/
	public void setDicDao(IDicDao dicDao) {
		this.dicDao = dicDao;
	}


	@Override
	public boolean dicNameExist(DicPo dicPo) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return dicDao.dicNameExist(dicPo.getDicName(),dicPo.getDicCode(),dicPo.getDicTypeCode());
	}


	@Override
	public boolean dicTypeNameExist(DicTypePo dicTypePo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return dicDao.dicTypeNameExist(dicTypePo.getDicTypeName(),dicTypePo.getDicTypeCode());
	}


	@Override
	public DicTypePo validateDicTypeName(String name)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return dicDao.validateDicTypeName(name);
	}


	@Override
	public void updateDicTypeCode(DicTypePo dicTypePo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		dicDao.updateDicTypeCode(dicTypePo);
	}
	public String findDicNameByDicCode(String dicCode)throws RollbackableBizException{
		return dicDao.findDicNameByDicCode(dicCode);
	}

}
