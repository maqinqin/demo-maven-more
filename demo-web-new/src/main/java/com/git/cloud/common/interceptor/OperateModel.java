package com.git.cloud.common.interceptor;

public class OperateModel {
	private String operateType; // 操作类型
	private String operateName; // 操作名称
	private String startsWith; // 拦截方法前缀
	private String interceptMethod; // 拦截的方法名
	private String logMethod; // 查询日志内容的方法名，和interceptMethod一起使用
	private String paramOrder; // 查询日志内容的方法需要第几个参数，如果为空则为默认所有参数
	private String pkId; // 业务对象的主键Id
	private String getBeanMethod; // 根据Id获取对象的方法名称
	private String businessName; // 业务对象的业务名称
	
	public OperateModel() {
		
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getStartsWith() {
		return startsWith;
	}

	public void setStartsWith(String startsWith) {
		this.startsWith = startsWith;
	}

	public String getInterceptMethod() {
		return interceptMethod;
	}

	public void setInterceptMethod(String interceptMethod) {
		this.interceptMethod = interceptMethod;
	}

	public String getLogMethod() {
		return logMethod;
	}

	public void setLogMethod(String logMethod) {
		this.logMethod = logMethod;
	}

	public String getParamOrder() {
		return paramOrder;
	}

	public void setParamOrder(String paramOrder) {
		this.paramOrder = paramOrder;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getGetBeanMethod() {
		return getBeanMethod;
	}

	public void setGetBeanMethod(String getBeanMethod) {
		this.getBeanMethod = getBeanMethod;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
}
