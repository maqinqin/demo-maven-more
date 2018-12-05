package com.git.cloud.policy.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmNwRulePo.java 
 * @Package 	com.git.cloud.policy.model.po 
 * @author 		wxg
 * @date 		2014-9-25 上午午10:00:09
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwRulePo  extends BaseBO  implements java.io.Serializable{

	private String rm_nw_rule_id;//主键
	private String host_type_id;
	private String use_rl_id;
	private String rule_name;//排序对象
	private String isActive;//是否有效

	// Constructors

	/** default constructor */
	public RmNwRulePo() {
	}

	public RmNwRulePo(String rm_nw_rule_id, String host_type_id,
			String use_rl_id, String rule_name, String isActive) {
		super();
		this.rm_nw_rule_id = rm_nw_rule_id;
		this.host_type_id = host_type_id;
		this.use_rl_id = use_rl_id;
		this.rule_name = rule_name;
		this.isActive = isActive;
	}

	public String getRm_nw_rule_id() {
		return rm_nw_rule_id;
	}

	public void setRm_nw_rule_id(String rm_nw_rule_id) {
		this.rm_nw_rule_id = rm_nw_rule_id;
	}

	public String getHost_type_id() {
		return host_type_id;
	}

	public void setHost_type_id(String host_type_id) {
		this.host_type_id = host_type_id;
	}

	public String getUse_rl_id() {
		return use_rl_id;
	}

	public void setUse_rl_id(String use_rl_id) {
		this.use_rl_id = use_rl_id;
	}

	public String getRule_name() {
		return rule_name;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "RmNwRulePo [rm_nw_rule_id=" + rm_nw_rule_id + ", host_type_id="
				+ host_type_id + ", use_rl_id=" + use_rl_id + ", rule_name="
				+ rule_name + ", isActive=" + isActive + "]";
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
