package com.git.cloud.iaas.hillstone.service;

import com.git.cloud.iaas.hillstone.model.AddrBookModel;
import com.git.cloud.iaas.hillstone.model.DnatModel;
import com.git.cloud.iaas.hillstone.model.HillStoneModel;
import com.git.cloud.iaas.hillstone.model.PolicyModel;
import com.git.cloud.iaas.hillstone.model.ServiceBookModel;
import com.git.cloud.iaas.hillstone.model.SnatModel;

/**
 * 山石防火墙接口
 * @author shl
 */
public interface HillStoneFirewallService {

	/**
	 * 根据名字查询服务簿集合
	 * @return
	 * @throws Exception
	 */
	ServiceBookModel getServiceBookListByName(HillStoneModel hillStoneModel, String servicebookName) throws Exception;
	/**
	 * 创建服务簿
	 * @param serviceBookModel
	 * @return
	 * @throws Exception
	 */
	String createServiceBook(HillStoneModel hillStoneModel, ServiceBookModel serviceBookModel) throws Exception;
	/**
	 * 创建地址簿
	 * @param addrBookModel
	 * @return
	 * @throws Exception
	 */
	String createAddrBook(HillStoneModel hillStoneModel, AddrBookModel addrBookModel) throws Exception;
	/**
	 * 创建DNAT
	 * @param snatModel
	 * @return
	 * @throws Exception
	 */
	String createDnat(HillStoneModel hillStoneModel, DnatModel dnatModel) throws Exception;
	/**
	 * 删除DNAT
	 * @param idArr
	 * @throws Exception
	 */
	void deleteDnat(HillStoneModel hillStoneModel, String[] idArr) throws Exception;
	/**
	 * 创建SNAT
	 * @param snatModel
	 * @return
	 * @throws Exception
	 */
	String createSnat(HillStoneModel hillStoneModel, SnatModel snatModel) throws Exception;
	/**
	 * 删除SNAT
	 * @param idArr
	 * @throws Exception
	 */
	void deleteSnat(HillStoneModel hillStoneModel, String[] idArr) throws Exception;
	/**
	 * 创建安全策略
	 * @param policyModel
	 * @return
	 */
	String createPolicy(HillStoneModel hillStoneModel, PolicyModel policyModel) throws Exception;
	/**
	 * 删除安全策略
	 * @param idArr
	 */
	void deletePolicy(HillStoneModel hillStoneModel, String[] idArr) throws Exception;
}
