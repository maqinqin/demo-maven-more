package com.git.cloud.bill.model.vo;

public class BillInfoVo {

	private String tenantId;		//租户id
	private String tenantName;		//租户名称
	private String billMonth;		//账单月
	private String billMoney;		//账单应缴金额
	private String realIncomeMoney;	//实收金额
	private String paymentState;	//支付状态
	private String remark;			//备注
	private String serviceType;			//服务类型
	private String startTime;			//开始时间
	private String instanceId;			//实例ID
	private String createDate;			//创建日期
	private String balance;				//代金券面值
	private String effectDate;			//生效时间
	private String expireDate;			//失效时间
	private String status;			//状态
	
	private String optType;//充值类型
	private String payStatus;//充值状态
	private String totalAmount;//充值金额
	
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getBillMonth() {
		return billMonth;
	}
	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}
	public String getBillMoney() {
		return billMoney;
	}
	public void setBillMoney(String billMoney) {
		this.billMoney = billMoney;
	}
	public String getRealIncomeMoney() {
		return realIncomeMoney;
	}
	public void setRealIncomeMoney(String realIncomeMoney) {
		this.realIncomeMoney = realIncomeMoney;
	}
	public String getPaymentState() {
		return paymentState;
	}
	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	@Override
	public String toString() {
		return "BillInfoVo [tenantId=" + tenantId + ", tenantName=" + tenantName + ", billMonth=" + billMonth
				+ ", billMoney=" + billMoney + ", realIncomeMoney=" + realIncomeMoney + ", paymentState=" + paymentState
				+ ", remark=" + remark + "]";
	}
}
