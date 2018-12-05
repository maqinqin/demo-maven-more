package com.git.cloud.handler.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	
	public final static String FILE_SEPARATOR = System.getProperty("file.separator");
	public final static String BR = System.getProperty("line.separator");
	public final static String JAVA_IO_TMPDIR = System.getProperty("java.io.tmpdir");
	public final static String TAB = "\t";
	
	private static Logger log = LoggerFactory.getLogger(Utils.class);
	
	/**
	 * 替换字符串中[$key]的内容为context的value
	 * @param str
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String replaceString(String str, Map<String, String> context) throws Exception {
		String oneExecComm = str;
		while (oneExecComm.indexOf("[$") >= 0) {
			int p1 = oneExecComm.indexOf("[$");
			int p2 = oneExecComm.indexOf("]", p1 + 2);
			String key = oneExecComm.substring(p1 + 2, p2);
			if (context.containsKey(key)) {
				String value = context.get(key);
				if (value == null) {
					value = "";
				}
				oneExecComm = oneExecComm.substring(0, p1) + value + oneExecComm.substring(p2 + 1);
			} else {
				throw new Exception("拼装脚本及参数时，不存在参数: " + key);
			}
		}
		return oneExecComm;
	}

	public static Object depthClone(Object srcObj) {
		Object cloneObj = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(out);
			oo.writeObject(srcObj);
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(in);
			cloneObj = oi.readObject();
		} catch (IOException e) {
			log.error("异常exception",e);
		} catch (ClassNotFoundException e) {
			log.error("异常exception",e);
		}
		return cloneObj;
	}
	
	public static List<String> splitString(String content, int unitlen) {
		if (content == null || content.trim().length() == 0) {
			return null;
		}
		List<String> units = new ArrayList<String>();
		String unit = null;
		String temp = content;
		int len = 0;
		while (temp.length() > 0) {
			if (temp.length() > unitlen) {
				len = unitlen;
			} else {
				len = temp.length();
			}
			unit = temp.substring(0, len);
			units.add(unit);
			temp = temp.substring(len);
		}
		return units;
	}
	
	public static String invokeLocalCmd(String cmd) throws Exception {
		BufferedReader br = null;
		StringBuffer result = new StringBuffer();
		try {
			Process rt = Runtime.getRuntime().exec(cmd);
			br = new BufferedReader(new InputStreamReader(rt.getInputStream()));
			String line=null;
			while((line = br.readLine()) != null) {
				result.append(line);
			}
			br.close(); 
			br = null;
		} catch (IOException e) {
			throw new Exception("调用本地命令错误" + cmd + "，" + e.getMessage(),e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e2) {
				throw new Exception("调用本地命令错误" + cmd + "，" + e2.getMessage());
			}
		}
		return result.toString();
	}
	
	/**
	 * 输出异常堆栈信息
	 * 
	 * @param e
	 */
	public static void printExceptionStack(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();
		for (StackTraceElement stackTraceElement : ste) {
			log.error(stackTraceElement.toString());
		}
		log.error("异常exception",e);
	}
	
	/**
	 * 字符串list转为字符串
	 * @param list
	 * @return
	 */
	public static String listToString(List<String> list) {
		StringBuffer sb = new StringBuffer();
		for (String str : list) {
			sb.append(str).append(Utils.BR);
		}
		return sb.toString();
	}
	
	/**
	 * Map转换为Stirng。
	 * @param map
	 * @return
	 */
	public static String map2String(Map<?,?> map){
		StringBuffer sb = new StringBuffer();
		Set<?> keys = map.keySet();
		sb.append("{");
		for(Object key : keys){
			sb.append("[");
			sb.append(key.toString());
			sb.append(":");
			sb.append(map.get(key).toString());
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 截取字符串
	 * @param str
	 * @param byteLength
	 * @param isFillNeeded
	 * @return
	 */
	public static String truncateString(String str, int byteLength,
			boolean isFillNeeded) {
		try {
			if (str.getBytes().length < byteLength) {
				if (isFillNeeded) {
					int spaceNeeded = byteLength - str.getBytes().length;
					StringBuffer sb = new StringBuffer(byteLength);
					sb.append(str);
					for (int i = 0; i < spaceNeeded; i++) {
						sb.append(" ");
					}
					return sb.toString();
				} else {
					return str;
				}
			} else {
				while (str.getBytes().length > byteLength) {
					str = str.substring(0, str.length() - 1);
				}
				StringBuffer sb = new StringBuffer(byteLength);
				sb.append(str);
				return sb.toString();
			}
		} catch (Exception e) {
			log.error("异常exception",e);
			return "";
		}
	}
	
	/**
	 * 将字符串List转换为以指定分隔符分隔的字符串。
	 * @param list
	 * @param symbol
	 * @return
	 */
	public static String list2StringSplitBySymbol(List<String> list, String symbol){
		StringBuffer sb = new StringBuffer();
		for(String string : list) {
			sb.append(string);
			sb.append(symbol);
		}
		String s = sb.toString();
		String retStr = s.substring(0, s.length()-symbol.length());
		return retStr;
	}
	
	
	/**
	 * 将以指定分隔符分隔的字符串转换为字符串List
	 * @param s
	 * @param symbol
	 * @return
	 */
	public static List<String> stringSplitBySymbol2List(String s, String symbol){
		List<String> list = new ArrayList<String>();
		String[] sArray = s.split(symbol);
		for(String x : sArray){
			list.add(x);
		}
		return list;
	}
	
	/**
	 * 处理字符串，将字符串“[$xxx]”形式的变量替换成实际值
	 * @Title: makeReqlStr
	 * @Description: TODO
	 * @field: @param sql
	 * @field: @return
	 * @field: @throws Exception
	 * @return String
	 * @throws
	 */
	public static String getRealStr(String decorateStr, Map<String, Object> contextParams, Map<String, String> deviceParams) throws Exception {
		String realStr = decorateStr;
		// 解析命令行添加参数
		while (realStr.indexOf("[$") >= 0) {
			int p1 = realStr.indexOf("[$");
			int p2 = realStr.indexOf("]", p1 + 2);
			String key = realStr.substring(p1 + 2, p2);
			if (deviceParams.containsKey(key) && !deviceParams.isEmpty()) {
				String value = (String) deviceParams.get(key);
				if (value == null) {
					value = "";
				}
				realStr = realStr.substring(0, p1) + value + realStr.substring(p2 + 1);
			} else if (contextParams.containsKey(key)) {
				String value = (String) contextParams.get(key);
				if (value == null) {
					value = "";
				}
				realStr = realStr.substring(0, p1) + value + realStr.substring(p2 + 1);
			} else {
				throw new Exception("拼装字符串时，不存在参数: " + key);
			}
		}
		return realStr;
	}
	
	/**
	 * 获取当前时间
	 * @Title: getCurrentDate
	 * @Description: TODO
	 * @field: @return
	 * @field: @throws ParseException
	 * @return Date
	 * @throws
	 */
	public static Date getCurrentDate() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String dateNowStr = sdf.format(d);
		Date nowTime = null;
		try {
			nowTime = sdf.parse(dateNowStr);
		} catch (ParseException e) {
			throw e;
		}
		return nowTime;
	}
	
	public static List<String> getStringList(String ... args) {
		List<String> list = new ArrayList<String>();
		for (String arg : args) {
			list.add(arg);
		}
		return list;
	}
	
	public static void main(String[] args) {
		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("ttt", "ddd");
//		map.put("asfaf", "value");
//		Map<String, String> copy = (Map<String, String>)Utils.depthClone(map);
//		System.out.println(map2String(map));
//		System.out.println(copy.get("ttt"));
//		System.out.println("0123".substring(0, 2));
//		System.out.println("0123".substring(2));
//		List<String> strings = Utils.splitString("123456789012345678901234567890", 2);
//		System.out.println(strings);
//		System.out.println("\tsdfsdfsdf\ndsfds");
//		String str = "abc测试字符串";
//		System.out.println(Utils.truncateString(str, 2, false));
		List<String> list = new ArrayList<String>();
		list.add("asf");
		list.add("撒旦56789");
		list.add("sdfggg撒打发士大夫123456");
		System.out.println(list2StringSplitBySymbol(list,"@#￥"));
		String osVerBrief = "6.0.1.9".substring(0, 1);
		System.out.println(osVerBrief);
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "2");
		map.put("key2", "value2");
		String str = "'[$key2]' == 'value2'";
		try {
			System.out.println(Utils.replaceString(str, map));
			ScriptEngineManager factory = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName("JavaScript");
			if ((Boolean) engine.eval(Utils.replaceString(str, map))) {
				System.out.println("ok");
			} else {
				System.out.println("false");
			}
			System.out.println(engine.eval("2+3"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("异常exception",e);
		}
	}
}