package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmNwConvergePo.java 
 * @Package 	com.git.cloud.resmgt.network.model.po
 * @author 		make
 * @date 		2015-3-6下午14:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwConvergePo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;
	private String convergeId;//网络内聚Id
	private String convergeName;//网络内聚名称
	private String datacenterId;//数据中心Id
	private String isActive;//是否激活

	// Constructors

	/** default constructor */
	public RmNwConvergePo() {
	}
	
	/** minimal constructor */
	public RmNwConvergePo(String convergeId) {
		this.convergeId = convergeId;
	}
	
	/** full constructor */

	public RmNwConvergePo(String convergeId, String convergeName,
			String datacenterId, String isActive) {
		super();
		this.convergeId = convergeId;
		this.convergeName = convergeName;
		this.datacenterId = datacenterId;
		this.isActive = isActive;
	}
	// Property accessors

	public String getConvergeId() {
		return convergeId;
	}


	public void setConvergeId(String convergeId) {
		this.convergeId = convergeId;
	}


	public String getConvergeName() {
		return convergeName;
	}


	public void setConvergeName(String convergeName) {
		this.convergeName = convergeName;
	}


	public String getDatacenterId() {
		return datacenterId;
	}


	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
