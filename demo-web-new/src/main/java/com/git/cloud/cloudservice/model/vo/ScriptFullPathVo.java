/**
 * @Title:ScriptFullPathVo.java
 * @Package:com.git.cloud.cloudservice.model.vo
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-11 下午3:53:35
 * @version V1.0
 */
package com.git.cloud.cloudservice.model.vo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @ClassName:ScriptFullPathVo
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-11 下午3:53:35
 *
 *
 */
public class ScriptFullPathVo extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	private String scriptFileName;
	private String modelFilePath;
	private String packageFillPath;
	private String runUser;
	
	public ScriptFullPathVo() {
		
	}
	
	@Override
	public String toString() {
		return "ScriptFullPathVo [scriptFileName=" + scriptFileName + ", modelFilePath=" + modelFilePath + ", packageFillPath=" + packageFillPath + ", runUser=" + runUser + "]";
	}

	public String getScriptFileName() {
		return scriptFileName;
	}
	public void setScriptFileName(String scriptFileName) {
		this.scriptFileName = scriptFileName;
	}
	public String getModelFilePath() {
		return modelFilePath;
	}
	public void setModelFilePath(String modelFilePath) {
		this.modelFilePath = modelFilePath;
	}
	public String getPackageFillPath() {
		return packageFillPath;
	}
	public void setPackageFillPath(String packageFillPath) {
		this.packageFillPath = packageFillPath;
	}
	
	public String getRunUser() {
		return runUser;
	}

	public void setRunUser(String runUser) {
		this.runUser = runUser;
	}

	/* (non-Javadoc)
	 * <p>Title:getBizId</p>
	 * <p>Description:</p>
	 * @return
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
