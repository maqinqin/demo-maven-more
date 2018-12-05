package com.git.cloud.common.support;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
	public static long formatDateStringToInt(String strDate, String _format) {
		Date time;
		SimpleDateFormat format = new SimpleDateFormat(_format, Locale.getDefault());
		try {
			time = format.parse(strDate);
			return time.getTime() / 1000;
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static long formatDateToInt(Date p_date) {
		if (p_date != null) {
			return p_date.getTime() / 1000;
		}
		return 0;
	}
}
