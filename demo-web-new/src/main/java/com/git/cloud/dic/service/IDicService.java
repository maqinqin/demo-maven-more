package com.git.cloud.dic.service;


import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.dic.model.po.DicPo;
import com.git.cloud.dic.model.po.DicTypePo;

/**
 * 字典管理
 * @description: 
 * @author: wangdy
 * @Date: Dec 29, 2014
 * @modify：
 * @version: 1.0
 * @Company: 高伟达软件股份有限公司
 */
public interface IDicService {
	
	/**
	 * wmy,验证字典名称不能重复
	 * @param name
	 * @return
	 * @throws RollbackableBizException
	 */
	public DicTypePo validateDicTypeName(String name)throws RollbackableBizException;
	/**
	 * wmy修改字典类型时，数据字典表中的名称也相应进行更改
	 * @param map
	 * @throws RollbackableBizException
	 */
	public void updateDicTypeCode(DicTypePo dicTypePo)throws RollbackableBizException;
	/**
	 * 查询字典类型
	 * @Title: findDicTypePage
	 * @Description: TODO
	 * @field: @param paginationParam
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return Pagination<DicTypePo>
	 * @throws
	 */
	public  Pagination<DicTypePo> findDicTypePage(PaginationParam paginationParam) throws RollbackableBizException;
	/**
	 * 保存字典类型
	 * @Title: saveUser
	 * @Description: TODO
	 * @field: @param dicTypePo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	/**
	 * 插入字典类型
	 * @param dicTypePo
	 * @return 
	 * @throws RollbackableBizException
	 */
	public String insertDicType(DicTypePo dicTypePo) throws RollbackableBizException;
	/**
	 * @return 
	 * 更新字典类型
	 * @Title: updateDicType
	 * @Description: TODO
	 * @field: @param dicTypePo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public String updateDicType(DicTypePo dicTypePo) throws RollbackableBizException;
	/**
	 * 删除字典类型
	 * @Title: deleteDicType
	 * @Description: TODO
	 * @field: @param id
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public String deleteDicType(String id,String dicTypeCode) throws RollbackableBizException;
	/**
	 * 根据 findDicTypeByCode 查询字典类型
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
	 * 查询字典
	 * @Title: findDicPage
	 * @Description: TODO
	 * @field: @param paginationParam
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return Pagination<DicPo>
	 * @throws
	 */
	public  Pagination<DicPo> findDicPage(PaginationParam paginationParam) throws RollbackableBizException;
	/**
	 * 保存字典
	 * @Title: saveUser
	 * @Description: TODO
	 * @field: @param dicPo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	//public String saveDic(DicPo dicPo) throws RollbackableBizException;
	/**
	 * 新增字典
	 * @param dicPo
	 * @throws RollbackableBizException
	 */
	public String insertDic(DicPo dicPo) throws RollbackableBizException;
	/**
	 * @return 
	 * 更新字典
	 * @Title: updateDic
	 * @Description: TODO
	 * @field: @param dicPo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public String updateDic(DicPo dicPo) throws RollbackableBizException;
	/**
	 * 删除字典
	 * @Title: deleteDic
	 * @Description: TODO
	 * @field: @param id
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public String deleteDic(String id) throws RollbackableBizException;
	/**
	 * 根据 findDicById 查询字典
	 * @Title: findDicById
	 * @Description: TODO
	 * @field: @param dicId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List
	 * @throws
	 */
	public DicPo findDicById(String dicId) throws RollbackableBizException;
	public boolean dicNameExist(DicPo dicPo)throws RollbackableBizException;
	public boolean dicTypeNameExist(DicTypePo dicTypePo)throws RollbackableBizException;
	/**
	 * 查询数据字典名称，根据数据dic-code
	 * @param dicCode
	 * @return
	 * @throws RollbackableBizException
	 */
	public String findDicNameByDicCode(String dicCode)throws RollbackableBizException;
}
