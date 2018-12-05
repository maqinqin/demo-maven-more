package com.git.support.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalMap {
	
	private final static Map<Thread, HashMap<String, Object>> threadDataMap = new HashMap<Thread, HashMap<String, Object>> ();
	
	public static void setValue(String key, Object value) {
		getThreadDataMap().put(key, value);
	}
	
	public static Object getValue(String key) {
		return getThreadDataMap().get(key);
	}
	
	private static HashMap<String, Object> getThreadDataMap() {
		Thread thread = Thread.currentThread();
		if(threadDataMap.get(thread) == null) {
			threadDataMap.put(thread, new HashMap<String, Object> ());
		}
		return threadDataMap.get(thread);
	}
	
	public class ThreadLocalKey {
		public final static String FLOW_INST_ID = "FLOW_INST_ID"; // 流程实例ID
	}
}
