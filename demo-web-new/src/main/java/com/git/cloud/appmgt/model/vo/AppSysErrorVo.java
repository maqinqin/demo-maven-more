package com.git.cloud.appmgt.model.vo;

/**
 * 应用系统返回结果VO
 * @author qiupj
 * @date 2014-12-17 上午11:18:04
 * @version v1.0
 *
 */
public class AppSysErrorVo {
	private String resultFlag;//0-失败;1-成功
	private String errormsg;
	private String appId;
	private String remark;
	
	public AppSysErrorVo() {
	}

	public AppSysErrorVo(String resultFlag, String errormsg, String appId,
			String remark) {
		super();
		this.resultFlag = resultFlag;
		this.errormsg = errormsg;
		this.appId = appId;
		this.remark = remark;
	}

	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
