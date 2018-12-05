package com.git.cloud.resmgt.network.model.vo;

import com.git.cloud.resmgt.network.model.po.RmNwUsePo;
/**
 * @Description 
 * @author 		 make
 * @version 	 v1.0  2015-3-6
 */
public class RmNwUseVo extends RmNwUsePo implements java.io.Serializable{
	/** default constructor */
	public RmNwUseVo() {
	}

	/** minimal constructor */
	public RmNwUseVo(String useId) {
		super(useId);
	}
	
	/** full constructor */
	public RmNwUseVo(String useId, String useCode, String useName,
			String isActive) {
		super(useId, useName, useName,	isActive);
	}
}
