package com.git.cloud.resmgt.common.action;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.resmgt.common.model.po.CmHostDatastoreRefPo;
import com.git.cloud.resmgt.common.service.ICmHostDatastoreRefService;

public class CmHostDatastoreRefAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ICmHostDatastoreRefService cmHostDatastoreRefServiceImpl;
	private CmHostDatastoreRefPo cmHostDatastoreRefPo;
	
	public ICmHostDatastoreRefService getCmHostDatastoreRefServiceImpl() {
		return cmHostDatastoreRefServiceImpl;
	}

	public void setCmHostDatastoreRefServiceImpl(
			ICmHostDatastoreRefService cmHostDatastoreRefServiceImpl) {
		this.cmHostDatastoreRefServiceImpl = cmHostDatastoreRefServiceImpl;
	}

	public CmHostDatastoreRefPo getCmHostDatastoreRefPo() {
		return cmHostDatastoreRefPo;
	}

	public void setCmHostDatastoreRefPo(CmHostDatastoreRefPo cmHostDatastoreRefPo) {
		this.cmHostDatastoreRefPo = cmHostDatastoreRefPo;
	}

}
