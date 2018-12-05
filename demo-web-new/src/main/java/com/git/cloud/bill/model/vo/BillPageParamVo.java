package com.git.cloud.bill.model.vo;

import java.util.List;

public class BillPageParamVo {

	private String billMonth;
    private Integer pageNo;
    private Integer pageSize;
    private List<String> custIdList;
    private List<String> extCustIds;
    private Integer billFeeMin;
    private Integer billFeeMax;
    private String startDate;
    private String endDate;
    private String serviceType;
    private String extCustId;
    private String tenantId;
    private String status;
    private PageInfo pageInfo;
    private String beginTime;
    private String endTime;
    private String optType;
    private String totalAmount;
    
    
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getExtCustId() {
		return extCustId;
	}

	public void setExtCustId(String extCustId) {
		this.extCustId = extCustId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getExtCustIds() {
		return extCustIds;
	}

	public void setExtCustIds(List<String> extCustIds) {
		this.extCustIds = extCustIds;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getBillMonth() {
		return billMonth;
	}

	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	public Integer getBillFeeMin() {
		return billFeeMin;
	}

	public void setBillFeeMin(Integer billFeeMin) {
		this.billFeeMin = billFeeMin;
	}

	public Integer getBillFeeMax() {
		return billFeeMax;
	}

	public void setBillFeeMax(Integer billFeeMax) {
		this.billFeeMax = billFeeMax;
	}

	public List<String> getCustIdList() {
		return custIdList;
	}

	public void setCustIdList(List<String> custIdList) {
		this.custIdList = custIdList;
	}

	@Override
	public String toString() {
		return "BillPageParamVo [billMonth=" + billMonth + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", custIdList=" + custIdList + ", billFeeMin=" + billFeeMin + ", billFeeMax=" + billFeeMax + "]";
	}

}
