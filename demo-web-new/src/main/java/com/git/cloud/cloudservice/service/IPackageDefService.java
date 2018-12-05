package com.git.cloud.cloudservice.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.cloudservice.model.vo.ModelModelVO;
import com.git.cloud.cloudservice.model.vo.PackageModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptParamModelVO;
import com.git.cloud.common.exception.RollbackableBizException;

/**
 * 脚本管理
 * @ClassName: IPackageDefService
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public interface IPackageDefService {

	public Object load(String id, String type) throws RollbackableBizException;
	
	public PackageModelVO loadPackageForLog(String id) throws RollbackableBizException;
	
	public ModelModelVO loadModelForLog(String id) throws RollbackableBizException;
	
	public ScriptModelVO loadScriptForLog(String id) throws RollbackableBizException;
	
	public ScriptParamModelVO loadScriptParamForLog(String id) throws RollbackableBizException;

	public PackageModelVO load(String id);

	//public void delete(String id, String type) throws RollbackableBizException;
	
	public void deletePackageForLog(String id) throws RollbackableBizException;
	
	public void deleteModelForLog(String id) throws RollbackableBizException;
	
	public void deleteScriptForLog(String id) throws RollbackableBizException;
	
	public void deleteScriptParamForLog(String id) throws RollbackableBizException;

	//public Object save(Object packageModelVO) throws RollbackableBizException;

	public PackageModelVO savePackageForLog(PackageModelVO object) throws RollbackableBizException;
	
	public ModelModelVO saveModelForLog(ModelModelVO object) throws RollbackableBizException;
	
	public ScriptModelVO saveScriptForLog(ScriptModelVO object) throws RollbackableBizException;
	
	public ScriptParamModelVO savescriptParamForLog(ScriptParamModelVO object) throws RollbackableBizException;
	
	public PackageModelVO updatePackageForLog(PackageModelVO object) throws RollbackableBizException;
	
	public ModelModelVO updateModelForLog(ModelModelVO object) throws RollbackableBizException;
	
	public ScriptModelVO updateScriptForLog(ScriptModelVO object) throws RollbackableBizException;
	
	public ScriptParamModelVO updateScriptParamForLog(ScriptParamModelVO object) throws RollbackableBizException;

	public Map search(Map map);

	public List loadTree();

	public List loadDict(java.util.Map<String, String> params);

	public Map loadScriptParamsByScript(String scriptId);

	public List<ModelModelVO> getParamByPackageId(String id)throws RollbackableBizException;//验证包下面是否有模板

	public List<ScriptModelVO> getParamByModelId(String id) throws RollbackableBizException;//验证模板下面是否有脚本
   //新增加 代码 
	public Object save(ModelModelVO modelModelVO)throws RollbackableBizException;

	public Object save(ScriptModelVO scriptModelVO)throws RollbackableBizException;

	public Object save(ScriptParamModelVO scriptParamModelVO)throws RollbackableBizException;

	public Object save(PackageModelVO packageModelVO)throws RollbackableBizException;

	public String checkPackageName(String packageName);

	public boolean checkModelName(String modelName, String packageId);

	public boolean checkScriptName(String scriptName, String modelId); 

} 
