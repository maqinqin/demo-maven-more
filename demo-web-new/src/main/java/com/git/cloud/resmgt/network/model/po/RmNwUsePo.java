package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmNwUsePo.java 
 * @Package 	com.git.cloud.resmgt.network.model.po
 * @author 		make
 * @date 		2015-3-6下午14:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwUsePo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 11L;
	private String useId;//网络内聚Id
	private String useCode;//网络内聚名称
	private String useName;//数据中心Id
	private String isActive;//是否激活

	// Constructors

	/** default constructor */
	public RmNwUsePo() {
	}
	
	/** minimal constructor */
	public RmNwUsePo(String useId) {
		this.useId = useId;
	}
	
	/** full constructor */

	public RmNwUsePo(String useId, String useCode,
			String useName, String isActive) {
		super();
		this.useId = useId;
		this.useCode = useCode;
		this.useName = useName;
		this.isActive = isActive;
	}

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public String getUseCode() {
		return useCode;
	}

	public void setUseCode(String useCode) {
		this.useCode = useCode;
	}

	public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
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
