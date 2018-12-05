package com.git.cloud.bill.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.git.cloud.bill.service.BillService;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.tenant.model.po.TenantPo;
import com.git.cloud.tenant.service.ITenantAuthoService;
public class BillAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(BillAction.class);
	
	public BillService billServiceImpl;
	@Autowired
	private ITenantAuthoService tenantService;
	
	public BillService getBillServiceImpl() {
		return billServiceImpl;
	}


	public void setBillServiceImpl(BillService billServiceImpl) {
		this.billServiceImpl = billServiceImpl;
	}
	public String index(){
		return SUCCESS;
	}

	public void list() throws Exception {
		 this.jsonOut(billServiceImpl.buildBillingPage(this.getPaginationParam()));
	}
	
	public void detailList() throws Exception {
		 this.jsonOut(billServiceImpl.buildBillingDetailPage(this.getPaginationParam()));
	}
	public void voucherList() throws Exception {
		 this.jsonOut(billServiceImpl.buildBillingVoucherPage(this.getPaginationParam()));
	}
	public void fundSerialList() throws Exception {
		 this.jsonOut(billServiceImpl.buildFundSerialPage(this.getPaginationParam()));
	}

	//含有账户余额的租户列表
	public void queryTenantBalancePage() throws Exception{
		 this.jsonOut(billServiceImpl.queryTenantBalancePage(this.getPaginationParam()));
	}
	
	//账户充值
	public void accountRecharge()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String amount = request.getParameter("amount");
		String busiDesc = request.getParameter("busiDesc");
		String fundTypeCode = request.getParameter("fundTypeCode");
		String tenantId = request.getParameter("tenantId");
		String result = billServiceImpl.accountRecharge(tenantId, amount, busiDesc,fundTypeCode);
		this.stringOut(result);
	}
	//计费-获取定价申请信息
	public void queryBmSrRrinfoPriceApproval() throws Exception {
		this.jsonOut(billServiceImpl.queryBmSrRrinfoPriceApproval(this.getPaginationParam()));
	}
	
	public void queryPriceStrategyById() throws Exception{
		this.jsonOut(billServiceImpl.queryPriceStrategyById(this.getPaginationParam()));
	}
	/**
	 * 查询可开发票总额
	 * @param 
	 * @throws Exception
	 */
	public void queryTotalInvoicePage()throws Exception{
		this.jsonOut(billServiceImpl.queryTotalInvoicePage(this.getPaginationParam()));
	}
}
