package com.git.support.invoker.test.impl;

import org.apache.activemq.pool.PooledConnectionFactory;


import com.git.support.invoker.common.impl.AbstractDestinationRouteKey;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.inf.IDataObject;

public class TestResAdptInvoker extends AbstractDestinationRouteKey implements
		IResAdptInvoker {

	public IDataObject invoke(IDataObject reqData, int timeOut) {
		System.out.println("TEST Invoker");
		return DataObject.CreateDataObject();
	}
}
