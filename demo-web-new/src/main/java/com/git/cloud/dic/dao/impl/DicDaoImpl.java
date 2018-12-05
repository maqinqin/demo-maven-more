package com.git.cloud.dic.dao.impl;


import java.util.HashMap;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.dic.dao.IDicDao;
import com.git.cloud.dic.model.po.DicPo;
import com.git.cloud.dic.model.po.DicTypePo;

public class DicDaoImpl extends CommonDAOImpl implements IDicDao{
	
	public Pagination<DicTypePo> findDicTypePage(PaginationParam paginationParam) throws RollbackableBizException{
		return this.pageQuery("queryDicTypeListTotal","queryDicTypeListPage", paginationParam);
	}
	public void saveDicType(DicTypePo dicTypePo) throws RollbackableBizException{
		this.save("insertDicType", dicTypePo);
	}
	public void updateDicType(DicTypePo dicTypePo) throws RollbackableBizException{
		this.update("updateDicType", dicTypePo);
	}
	public void deleteDicType(String id) throws RollbackableBizException{
		this.delete("deleteDicType", id);
	}
	public boolean dicTypeNameExist(String dicTypeName,String dicTypeCode) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("dicTypeName", dicTypeName);
		map.put("dicTypeCode", dicTypeCode);
		int size = this.findListByParam("queryDicTypeByName", map).size();
		if(size==0)
			return false;
		else
			return true;
	}
	public DicTypePo findDicTypeByCode(String dicTypeCode) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("dicTypeCode", dicTypeCode);
		return this.findObjectByMap("queryDicTypeByParams", map);
	}
	
	
	
	public Pagination<DicPo> findDicPage(PaginationParam paginationParam) throws RollbackableBizException{
		return this.pageQuery("queryDicListTotal","queryDicListPage", paginationParam);
	}
	public void saveDic(DicPo dicPo) throws RollbackableBizException{
		this.save("insertDic", dicPo);
	}
	public void updateDic(DicPo dicPo) throws RollbackableBizException{
		this.update("updateDic", dicPo);
	}
	public void deleteDic(String id) throws RollbackableBizException{
		this.delete("deleteDic", id);
	}
	public boolean dicNameExist(String dicName,String dicCode,String dicTypeCode) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("dicName", dicName);
		map.put("dicCode", dicCode);
		map.put("dicTypeCode", dicTypeCode);
		int size = this.findListByParam("queryDicByName", map).size();
		if(size==0)
			return false;
		else
			return true;
	}
	public DicPo findDicById(String dicId) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("dicId", dicId);
		return this.findObjectByMap("queryDicByParams", map);
	}
	@Override
	public boolean haveDic(String dicTypeCode) throws RollbackableBizException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("dicTypeCode", dicTypeCode);
		int size = this.findListByParam("queryDicByParams", map).size();
		if(size==0)
			return true;
		else
			return false;
	}
	@Override
	public DicTypePo validateDicTypeName(String name)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("dicTypeName", name);
		return this.findObjectByMap("validateDicTypeName", map);
	}
	@Override
	public void updateDicTypeCode(DicTypePo dicTypePo)
			throws RollbackableBizException {
		this.update("updateDicTypeCode",dicTypePo);
	}
	@Override
	public String findDicNameByDicCode(String dicCode)
			throws RollbackableBizException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("dicCode", dicCode);
		DicPo  dicPo =  this.findObjectByMap("findDicNameByDicCode", map);
		return dicPo == null ? "" : dicPo.getDicName();
	}
}
