package com.git.cloud.foundation.util;

import com.opensymphony.xwork2.util.profiling.UtilTimerStack;

public class ProfilingUtil {
	private static boolean enableProfiling = true;
	public static void startProfiling(String token) {
		if (enableProfiling) {
			System.out.println("_____________________"
					+ UtilTimerStack.isActive());
			UtilTimerStack.push(token);
		}
	}

	public static void stopProfiling(String token) {
		if (enableProfiling) {
			UtilTimerStack.pop(token);
		}
	}
}
