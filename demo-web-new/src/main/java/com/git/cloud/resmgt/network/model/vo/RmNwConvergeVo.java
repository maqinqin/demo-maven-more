package com.git.cloud.resmgt.network.model.vo;

import com.git.cloud.resmgt.network.model.po.RmNwConvergePo;
/**
 * @Description 
 * @author 		 make
 * @version 	 v1.0  2015-3-6
 */
public class RmNwConvergeVo extends RmNwConvergePo implements java.io.Serializable{
	/**
	 * 数据中心名称(中文)
	 */
	private String datacenterName;
	
	/** default constructor */
	public RmNwConvergeVo() {
	}

	/** minimal constructor */
	public RmNwConvergeVo(String convergeId) {
		super(convergeId);
	}
	
	/** full constructor */
	public RmNwConvergeVo(String convergeId, String convergeName, String dataCenter,
			String isActive) {
		super(convergeId, convergeName, dataCenter,	isActive);
	}

	public String getDatacenterName() {
		return datacenterName;
	}

	public void setDatacenterName(String datacenterName) {
		this.datacenterName = datacenterName;
	}	
}
