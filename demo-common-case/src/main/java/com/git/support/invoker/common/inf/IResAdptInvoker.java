package com.git.support.invoker.common.inf;

import com.git.support.sdo.inf.IDataObject;

public interface IResAdptInvoker {
	IDataObject invoke(IDataObject reqData, int timeOut) throws Exception; 
}
