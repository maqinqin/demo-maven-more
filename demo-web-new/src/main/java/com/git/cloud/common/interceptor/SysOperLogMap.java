package com.git.cloud.common.interceptor;

import java.util.HashMap;
import java.util.List;

public class SysOperLogMap {
	
	private HashMap<String, ModuleModel> moduleMap = null;

	private static SysOperLogMap sysOperLogMap;
	
	public static SysOperLogMap getSysOperLogMap() {
		if(sysOperLogMap == null) {
			sysOperLogMap = new SysOperLogMap();
		}
		return sysOperLogMap;
	}
	
	public void clearModuleMap() {
		if(moduleMap != null) {
			moduleMap.clear();
			moduleMap = null;
		}
	}
	
	public void put(String clazz, ModuleModel module) throws Exception {
		if(moduleMap == null) {
			moduleMap = new HashMap<String, ModuleModel> ();
		}
		if(moduleMap.get(clazz) != null) {
			throw new Exception("业务类【" + clazz + "】重复配置！");
		}
		moduleMap.put(clazz, module);
	}
	
	public ModuleModel getModuleModel(String clazz) {
		if(moduleMap == null) {
			return null;
		}
		return moduleMap.get(clazz);
	}
	
	public OperateModel getOperateModel(ModuleModel module, String methodName) {
		OperateModel operate = null;
		List<OperateModel> operateList = module.getOperateList();
		for(int i=0 ; i<operateList.size() ; i++) {
			if(methodName.equals(operateList.get(i).getInterceptMethod())) {
				operate = operateList.get(i);
				break;
			}
			if(!"".equals(operateList.get(i).getStartsWith()) && methodName.startsWith(operateList.get(i).getStartsWith())) {
				operate = operateList.get(i);
				break;
			}
		}
		return operate;
	}

	public String getRootPath() {
		String rootPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		if(rootPath.endsWith(".class")) {
			rootPath = rootPath.substring(0, rootPath.indexOf("/com"));
		} else {
			rootPath = System.getProperty("user.dir") + "/src";
		}
		return rootPath;
	}
}
