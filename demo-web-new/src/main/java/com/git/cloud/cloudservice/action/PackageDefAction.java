package com.git.cloud.cloudservice.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.cloudservice.model.vo.ModelModelVO;
import com.git.cloud.cloudservice.model.vo.PackageModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptParamModelVO;
import com.git.cloud.cloudservice.service.IPackageDefService;

/**
 * 脚本包管理
 * 
 * @ClassName: PackageDefAction
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class PackageDefAction extends BaseAction {

	private static final long serialVersionUID = -7799868382672725041L;
	private static Logger logger = LoggerFactory.getLogger(PackageDefAction.class);
	private IPackageDefService packageDefService;
	private PackageModelVO packageModelVO;
	private ModelModelVO modelModelVO;
	private ScriptModelVO scriptModelVO;
	private ScriptParamModelVO scriptParamModelVO;
	private Object String;
	private List treeList;
	private Map<String, String> params;
	private Map<String, Object> result;
	private String packageName;
	private String modelName;
	private String packageId;
	private String scriptName;
	private String modelId;

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getScriptName() {
		return scriptName;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String loadDict() {
		try {
			result = new HashMap<String, Object>();
			treeList = packageDefService.loadDict(params);
			result.put("code", "success");
			result.put("data", treeList);
		} catch (Exception e) {
			logger.error("加载字典异常",e);
			result = new HashMap<String, Object>();
			result.put("code", "error");
			result.put("message", "操作失败！");
		}
		return SUCCESS;
	}

	public String loadTree() {
		try {
			result = new HashMap<String, Object>();
			treeList = packageDefService.loadTree();
			String path = ServletActionContext.getRequest().getContextPath();
			if (treeList != null) {
				for (Object o : treeList) {
					Map map = (Map) o;
					if (map.get("id").equals("0")) {
						map.put("open", true);
					}
					if (((Long) map.get("type")) == 1L) {
						map.put("icon", path + "/css/zTreeStyle/img/icons/scriptpackage.png");
					}
					if (((Long) map.get("type")) == 2L) {
						map.put("icon", path + "/css/zTreeStyle/img/icons/script.png");
					}
					if (((Long) map.get("type")) == 3L) {
						map.put("icon", path + "/css/zTreeStyle/img/icons/scriptScript.png");
					}

				}
			}
			result.put("code", "success");
			result.put("data", treeList);
		} catch (Exception e) {
			logger.error("加载树异常",e);
			result = new HashMap<String, Object>();
			result.put("code", "error");
			result.put("message", "操作失败！");
		}
		return SUCCESS;
	}

	public String load() {
		try {
			result = new HashMap<String, Object>();
			result.put("code", "success");
			if (params != null && params.containsKey("id")) {
				String type = params.get("type");
				Object ret = packageDefService.load(params.get("id"), type);
				result.put("data", ret);
			}
		} catch (Exception e) {
			logger.error("加载异常",e);
			result = new HashMap<String, Object>();
			result.put("code", "error");
			result.put("message", "操作失败！");
		}
		return SUCCESS;
	}

	public String loadScriptParams() {

		try {
			result = new HashMap<String, Object>();
			if (params != null && params.containsKey("scriptId")) {
				Map result2 = this.packageDefService.loadScriptParamsByScript(params.get("scriptId"));
				result.put("data", result2.get("data"));
				result.put("code", "success");
			}
		} catch (Exception e) {
			logger.error("加载script参数异常",e);
			result = new HashMap<String, Object>();
			result.put("code", "error");
			result.put("message", "操作失败！");
		}
		return SUCCESS;

	}

	public String main() {
		return SUCCESS;
	}
	
	//检查包名是否存在
	public void checkScriptPackageName() throws Exception{
		String flag = packageDefService.checkPackageName(this.packageName);
		boolean result = ("1".equals(flag)) ? false : true;
		ObjectOut(result);
	}
	//检查模块是否存在
	public void checkScriptModelName() throws Exception{
		boolean result = packageDefService.checkModelName(this.modelName,this.packageId);
		ObjectOut(result);
	}
	//检查脚本名称是否存在
	public void checkScriptName() throws Exception{
		boolean result = packageDefService.checkScriptName(this.scriptName,this.modelId);
		ObjectOut(result);
	}

	public String save() {
		try {
			result = new HashMap<String, Object>();
			Object data = null;
			if (packageModelVO != null) {
				if (packageModelVO.getId() == null || "".equals(packageModelVO.getId())) {
					// return this.savePackageForLog(packageModelVO);
					packageModelVO = packageDefService.savePackageForLog(packageModelVO);
				} else {
					// return this.updatePackageForLog(packageModelVO);
					packageModelVO = packageDefService.updatePackageForLog(packageModelVO);
				}
				data = packageDefService.save(packageModelVO);
			} else if (modelModelVO != null) {
				if (modelModelVO.getId() == null || "".equals(modelModelVO.getId())) {
					// return this.saveModelForLog(modelModelVO);
					modelModelVO = packageDefService.saveModelForLog(modelModelVO);
				} else {
					// return this.updateModelForLog(modelModelVO);
					modelModelVO = packageDefService.updateModelForLog(modelModelVO);
				}
				data = packageDefService.save(modelModelVO);
			} else if (scriptModelVO != null) {
				if (scriptModelVO.getId() == null || "".equals(scriptModelVO.getId())) {
					scriptModelVO = packageDefService.saveScriptForLog(scriptModelVO);
				} else {
					scriptModelVO = packageDefService.updateScriptForLog(scriptModelVO);
				}
				data = packageDefService.save(scriptModelVO);
			} else if (scriptParamModelVO != null) {
				if (scriptParamModelVO.getId() == null || "".equals(scriptParamModelVO.getId())) {
					scriptParamModelVO = packageDefService.savescriptParamForLog(scriptParamModelVO);
				} else {
					scriptParamModelVO = packageDefService.updateScriptParamForLog(scriptParamModelVO);
				}
				data = packageDefService.save(scriptParamModelVO);
			}
			result.put("code", "success");
			result.put("data", data);
		} catch (Exception e) {
			logger.error("保存异常",e);
			result = new HashMap<String, Object>();
			result.put("code", "error");
			result.put("message", "操作失败！");
		}
		return SUCCESS;
	}

	public String delete() {
		try {
			result = new HashMap<String, Object>();

			if (params != null) {
				// packageDefService.delete(params.get("id"),
				// params.get("type"));
				String type = params.get("type");
				String id = params.get("id");
				if ("1".equals(type)) {
					//this.deletePackageForLog(id);
					packageDefService.deletePackageForLog(id);
				}
				if ("2".equals(type)) {
					//this.deleteModelForLog(id);
					packageDefService.deleteModelForLog(id);
				}
				if ("3".equals(type)) {
					packageDefService.deleteScriptForLog(id);
				}
				if ("4".equals(type)) {
					packageDefService.deleteScriptParamForLog(id);
				}
			}
			result.put("code", "success");
		} catch (Exception e) {
			logger.error("删除异常",e);
			result = new HashMap<String, Object>();
			result.put("code", "error");
			result.put("message", "操作失败！");
		}
		return SUCCESS;
	}

	// 删除前验证工作
	public void modelValidate() {

		try {
			if ("1".equals(params.get("type"))) {
				List<ModelModelVO> list = packageDefService.getParamByPackageId(params.get("id"));
				if (list.size() > 0) {
					this.stringOut("false");
				} else {
					this.stringOut("true");
				}
			} else if ("2".equals(params.get("type"))) {
				List<ScriptModelVO> list = packageDefService.getParamByModelId(params.get("id"));
				if (list.size() > 0) {
					this.stringOut("false");
				} else {
					this.stringOut("true");
				}
			} else if ("3".equals(params.get("type"))) {

				this.stringOut("true");
			} else if ("4".equals(params.get("type"))) {
				this.stringOut("true");
			}

		} catch (Exception e) {
			logger.error("验证异常",e);
		}
	}

	public String search() {
		return SUCCESS;
	}

	public PackageModelVO getPackageModelVO() {
		return packageModelVO;
	}

	public void setPackageModelVO(PackageModelVO packageModelVO) {
		this.packageModelVO = packageModelVO;
	}

	public IPackageDefService getPackageDefService() {
		return packageDefService;
	}

	public void setPackageDefService(IPackageDefService packageDefService) {
		this.packageDefService = packageDefService;
	}

	public List getTreeList() {
		return treeList;
	}

	public void setTreeList(List treeList) {
		this.treeList = treeList;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public ModelModelVO getModelModelVO() {
		return modelModelVO;
	}

	public void setModelModelVO(ModelModelVO modelModelVO) {
		this.modelModelVO = modelModelVO;
	}

	public ScriptModelVO getScriptModelVO() {
		return scriptModelVO;
	}

	public void setScriptModelVO(ScriptModelVO scriptModelVO) {
		this.scriptModelVO = scriptModelVO;
	}

	public ScriptParamModelVO getScriptParamModelVO() {
		return scriptParamModelVO;
	}

	public void setScriptParamModelVO(ScriptParamModelVO scriptParamModelVO) {
		this.scriptParamModelVO = scriptParamModelVO;
	}
}
