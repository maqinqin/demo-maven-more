package com.git.cloud.appmgt.tools;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具类
 * @author liangshuang
 * @date 2014-12-17 上午11:19:44
 * @version v1.0
 *
 */
public class MyDateUtil {
	
	public static final String DATE_STYLE_Date = "yyyy-MM-dd";
	
	public static final String DATE_STYLE_Datetime = "yyyy-MM-dd HH:mm:ss";
	
	public static String getCurrentDate() {
		
		return getDateTimeForStyle(new Date(), "yyyy-MM-dd");
	}
	
	public static String getCurrentDateForStyle(String style) {
		
		return getDateTimeForStyle(new Date(), style);
	}
	
	public static String getCurrentDateTime() {
		
		return getDateTimeForStyle(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getCurrentDateTimeForStyle(String style) {
		
		return getDateTimeForStyle(new Date(), style);
	}
	
	public static String getCurrentTime() {
		
		return getDateTimeForStyle(new Date(), "HH:mm:ss");
	}
	
	public static String getCurrentTimeForStyle(String style) {
		
		return getDateTimeForStyle(new Date(), style);
	}
	
	public static String getFormatDateTime(Date date) {
		if(date==null) return "";
		return getDateTimeForStyle(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getFormatDate(Date date) {
		
		return getDateTimeForStyle(date, "yyyy-MM-dd");
	}
	
	public static String getFormatTime(Date date) {
		
		return getDateTimeForStyle(date, "HH:mm:ss");
	}
	
	public static String getDateTimeForStyle(Date date, String style) { 
		
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		return sdf.format(date);
	}
	
	public static Timestamp getDateTimeForDelay(int days) {
		
		long second = System.currentTimeMillis() + 1000L*60*60*24*days;
		return new Timestamp(second);
	}
	
	public static String converSecondsToTime(long seconds, String style) {
		
		if(seconds < 0) {
			return null;
		}
		String res = style;
		seconds = seconds/1000;
		String second = String.valueOf(seconds%60);
		String minute = String.valueOf(seconds/60%60);
		String hour = String.valueOf(seconds/(60*60));
		res = res.replaceAll("hh", hour).replaceAll("mm", minute).replaceAll("ss", second);
		return res;
	}
	
	public static String converSecondsToTime(long seconds) {
		
		if(seconds < 0) {
			return null;
		}
		return converSecondsToTime(seconds, "hh:mm:ss");
	}
	
	public static Date convertStringToDate(String str){
		
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_STYLE_Datetime);
		Date date = null;
		try{
			date = formatter.parse(str);
		} catch(ParseException e) {}
		return date;
	}
	
	public static Date convertStringToDate(String str, String style) {
		
		if(null == str || "".equals(str)) {
			return null;
		}
		if(null == style || "".equals(style)) {
			style = DATE_STYLE_Datetime;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(style);
		Date date = null;
		try{
			date = formatter.parse(str);
		} catch(ParseException e) {}
		return date;
	}
}
