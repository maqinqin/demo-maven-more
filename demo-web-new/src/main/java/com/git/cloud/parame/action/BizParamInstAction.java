package com.git.cloud.parame.action;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.po.BizParamInstPo;
import com.git.cloud.handler.service.BizParamInstService;



public class BizParamInstAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BizParamInstService bizParamInstServiceImpl;
	private BizParamInstPo bizParamInstPo;
	
	
	public BizParamInstPo getBizParamInstPo() {
		return bizParamInstPo;
	}


	public void setBizParamInstPo(BizParamInstPo bizParamInstPo) {
		this.bizParamInstPo = bizParamInstPo;
	}


	public BizParamInstService getBizParamInstServiceImpl() {
		return bizParamInstServiceImpl;
	}


	public void setBizParamInstServiceImpl(
			BizParamInstService bizParamInstServiceImpl) {
		this.bizParamInstServiceImpl = bizParamInstServiceImpl;
	}


	public void getAllBizParamInst() throws RollbackableBizException, Exception{
		this.jsonOut(bizParamInstServiceImpl.findBizParamInstPage(this.getPaginationParam()));
	}
	
	public void updateParamValue() throws Exception{
		bizParamInstServiceImpl.updateParamValue(bizParamInstPo);
	}
	
	public void getBizParamInstById() throws RollbackableBizException{
		bizParamInstServiceImpl.getBizParamInstById(bizParamInstPo.getId());
	}
	
	public void addBizParamInst() throws BizException{
		bizParamInstPo.setId(UUIDGenerator.getUUID());
		bizParamInstServiceImpl.savePara(bizParamInstPo);
	}
}
