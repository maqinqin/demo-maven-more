package com.git.cloud.common.interceptor;

import java.util.List;

public class ModuleModel {
	private String clazz; // 业务模块的实现类路径
	private String moduleName; // 模块名称
	private String moduleCode; // 模块编码
	private List<OperateModel> operateList; // 业务方法拦截的操作
	
	public ModuleModel() {
		
	}
	
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public List<OperateModel> getOperateList() {
		return operateList;
	}
	public void setOperateList(List<OperateModel> operateList) {
		this.operateList = operateList;
	}
}
