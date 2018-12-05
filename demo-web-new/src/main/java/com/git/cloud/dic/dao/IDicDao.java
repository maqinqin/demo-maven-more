package com.git.cloud.dic.dao;


import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.dic.model.po.DicTypePo;
import com.git.cloud.dic.model.po.DicPo;

public interface IDicDao extends ICommonDAO{
	/**wmy
	 * 修改字典类型时，数据字典表中的名称也相应进行更改
	 * @param map
	 * @throws RollbackableBizException
	 */
	public void updateDicTypeCode(DicTypePo dicTypePo)throws RollbackableBizException;
	/**
	 * 查询字典类型列表
	 * @Title: findDicTypePage
	 * @Description: TODO
	 * @field: @param paginationParam
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return Pagination<DicTypePo>
	 * @throws
	 */
	public Pagination<DicTypePo>  findDicTypePage(PaginationParam paginationParam) throws RollbackableBizException;
	/**
	 * 保存字典类型
	 * @Title: saveDicType
	 * @Description: TODO
	 * @field: @param dicTypePo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void saveDicType(DicTypePo dicTypePo) throws RollbackableBizException;
	/**
	 * 更新字典类型
	 * @Title: updateDicType
	 * @Description: TODO
	 * @field: @param dicTypePo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void updateDicType(DicTypePo dicTypePo) throws RollbackableBizException;
	/**
	 * 删除字典类型
	 * @Title: deleteDicType
	 * @Description: TODO
	 * @field: @param id
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void deleteDicType(String id) throws RollbackableBizException;
	/**
	 * 根据字典类型ID判断该字典类型下有无字典
	 * @Title: haveDic
	 * @Description: TODO
	 * @field: @param dicTypeCode
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public boolean haveDic(String dicTypeCode) throws RollbackableBizException;
	/**
	 * 判断字典类型名称是否存在
	 * @Title: dicTypeNameExist
	 * @Description: TODO
	 * @field: @param dicTyepName
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return boolean
	 * @throws
	 */
	public boolean dicTypeNameExist(String dicTyepName,String dicTypeCode) throws RollbackableBizException;
	/**
	 * 根据dicTypeCode 查询字典类型
	 * @Title: findDicTypeByCode
	 * @Description: TODO
	 * @field: @param dicTypeCode
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List
	 * @throws
	 */
	public DicTypePo findDicTypeByCode(String dicTypeCode) throws RollbackableBizException;
	
	
	/**
	 * 查询字典列表
	 * @Title: findDicPage
	 * @Description: TODO
	 * @field: @param paginationParam
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return Pagination<DicPo>
	 * @throws
	 */
	public Pagination<DicPo>  findDicPage(PaginationParam paginationParam) throws RollbackableBizException;
	/**
	 * 保存字典
	 * @Title: saveDic
	 * @Description: TODO
	 * @field: @param dicPo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void saveDic(DicPo dicPo) throws RollbackableBizException;
	/**
	 * 更新字典
	 * @Title: updateDic
	 * @Description: TODO
	 * @field: @param dicPo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void updateDic(DicPo dicPo) throws RollbackableBizException;
	/**
	 * 删除字典
	 * @Title: deleteDic
	 * @Description: TODO
	 * @field: @param id
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void deleteDic(String id) throws RollbackableBizException;
	/**
	 * 判断字典名称是否存在
	 * @Title: dicNameExist
	 * @Description: TODO
	 * @field: @param dicTyepName
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return boolean
	 * @throws
	 */
	public boolean dicNameExist(String dicName,String dicCode,String dicTypeCode) throws RollbackableBizException;
	/**
	 * 根据dicCode 查询字典
	 * @Title: findDicByCode
	 * @Description: TODO
	 * @field: @param dicCode
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List
	 * @throws
	 */
	public DicPo findDicById(String dicId) throws RollbackableBizException;
	
	/**
	 * wmy,验证字典名称不能重复
	 * @param name
	 * @return
	 * @throws RollbackableBizException
	 */
	public DicTypePo validateDicTypeName(String name)throws RollbackableBizException;
	
	public String findDicNameByDicCode(String dicCode)throws RollbackableBizException;
}