package com.git.cloud.common.service;

import java.util.List;

import com.git.cloud.common.model.AdminDicPo;

/**
 * 查询字典信息SERVICE层接口类
 * @author liangshuang
 * @date 2014-9-17 上午11:21:20
 * @version v1.0
 *
 */
public interface IDictionaryService extends IService {
	/**
	 * 根据字典类型编码查询字典信息
	 * @param dicTypeCode
	 * @return
	 * @throws Exception
	 */
	public <T extends AdminDicPo> List<T> getByTypeCode(String dicTypeCode) throws Exception;
	/**
	 * 根据字典编码、字典类型编码查询字典信息
	 * @param dicCode 字典编码
	 * @param dicTypeCode 字典类型编码
	 * @return
	 * @throws Exception
	 */
	public <T extends AdminDicPo> List<T> getByCode(String dicCode,String dicTypeCode) throws Exception;
	/**
	 * 查询全部字典信息
	 * @return
	 * @throws Exception
	 */
	public <T extends AdminDicPo> List<T> getAllDic() throws Exception;

}
