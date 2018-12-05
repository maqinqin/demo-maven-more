package com.git.cloud.bill.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.bill.model.vo.BillInfoVo;
import com.git.cloud.bill.model.vo.BillPageParamVo;
import com.git.cloud.bill.model.vo.OrderRoot;
import com.git.cloud.bill.model.vo.VariableVOs;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.tenant.model.po.TenantPo;

public interface BillService {
	/**
	 * 构建订单信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public OrderRoot buildOrderData(Map<String,String> map) throws Exception;
	/**
	 * 根据传递的参数，调用生成订单的接口
	 * @param orderRoot
	 * @return
	 */
	public String createOrderInterface(OrderRoot orderRoot) throws RollbackableBizException;
	/**
	 * 显示账单信息
	 */
	public Pagination<BillInfoVo> buildBillingPage(PaginationParam paginationParam) throws Exception;
	/**
	 * 查询账单列表
	 * @param billPageParam
	 * @return
	 * @throws RollbackableBizException
	 */
	public Pagination<BillInfoVo> buildBillingList(BillPageParamVo billPageParam)throws RollbackableBizException;
	/**
	 * 详单查询
	 * @param paginationParam
	 * @return
	 * @throws Exception
	 */
	Pagination<BillInfoVo> buildBillingDetailPage(PaginationParam paginationParam) throws Exception;
	/**
	 * 代金券列表
	 * @param paginationParam
	 * @return
	 * @throws Exception
	 */
	Pagination<BillInfoVo> buildBillingVoucherPage(PaginationParam paginationParam) throws Exception;
	/**
	 * 充值记录列表
	 * @param paginationParam
	 * @return
	 * @throws Exception
	 */
	Pagination<BillInfoVo> buildFundSerialPage(PaginationParam paginationParam) throws Exception;
	
	Pagination<TenantPo> queryTenantBalancePage(PaginationParam paginationParam)throws Exception;
	/**
	 * 查询账户余额
	 * @param tenantId
	 * @return
	 * @throws Exception
	 */
	public int queryAccountBalance(String tenantId)throws Exception;
	/**
	 * 账户充值
	 * @param tenantId
	 * @param amount 前台传递的是元，需要转化为厘
	 * @param busiDesc
	 * @return
	 * @throws Exception
	 */
	public String accountRecharge(String tenantId,String amount,String busiDesc,String fundTypeCode)throws Exception;
	/**
	 * 保存或修改定价接口
	 * @param jsonParameter
	 * @throws Exception
	 */
	public String priceElementAddOrUpdate(String jsonParameter)throws Exception;
	/**
	 * 查询策略
	 * @param policyId
	 * @return
	 * @throws Exception
	 */
	public Pagination<VariableVOs> queryPriceStrategyById(PaginationParam pagination)throws Exception;
	
	/**
	 获取定价审批信息
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<Map> queryBmSrRrinfoPriceApproval(PaginationParam pagination) throws Exception;
	/**
	 * 根据子产品id查询主产品下子产品,主要是为了获取子产品名称categoryName，在页面上显示用
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public String getSubProductInfo(String categoryId)throws Exception;
	/**
	 * 查询可开发票总额
	 * @param tenantId
	 * @return
	 * @throws Exception
	 */
	public Pagination<TenantPo> queryTotalInvoicePage(PaginationParam paginationParam)throws Exception;
	/**
	 * 查询租户的可开发票
	 * @param tenantId
	 * @param billMonth
	 * @return
	 * @throws Exception
	 */
	public String queryTotalInvoice(String tenantId,String billMonth)throws Exception;
}
