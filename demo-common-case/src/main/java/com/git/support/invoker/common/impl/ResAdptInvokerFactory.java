package com.git.support.invoker.common.impl;

import java.util.HashMap;

import org.springframework.beans.factory.ObjectFactory;

import com.git.support.invoker.common.inf.IResAdptInvoker;

public class ResAdptInvokerFactory {
	private HashMap<String, ObjectFactory> invokers;

	public void setInvokers(HashMap<String, ObjectFactory> invokers) {
		this.invokers = invokers;
	}
	
	public IResAdptInvoker findInvoker(String type) {
		if (invokers.containsKey(type) == false) {
			return null;
		}
		return (IResAdptInvoker) invokers.get(type).getObject();
	}
}
