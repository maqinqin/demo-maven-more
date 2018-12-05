package com.git.cloud.taglib.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.ActionContext;

/**
 * 国际化语言获取
 * @author SunHailong
 */
public class Internation {
	private static Logger logger = LoggerFactory.getLogger(Internation.class);

	/**
	 * 根据对应的key值来去对应的国际化字符
	 * @param key
	 * @return
	 */
	public static String language(String key) {
		return language("messages", key);
	}

	public static String language(final String itype, final String key) {
		String internamtiontype = "";
		if(itype != null) {
			internamtiontype = itype;
		}
		if ("".equals(internamtiontype)) {
			internamtiontype = "messages";
		}
		String value = "";
		if (key == null || key.equals("")) {
			return "";
		}
		try {
			String laguage = getHl();
			Locale locale = null;
			if (laguage.equals("zh-cn"))
				locale = new Locale("zh", "CN");
			else if (laguage.equals("zh-tw"))
				locale = new Locale("zh", "TW");
			else if (laguage.equals("en"))
				locale = new Locale("en", "US");
			ResourceBundle rb = ResourceBundle.getBundle(internamtiontype, locale);
			value = rb.getString(key);
		} catch (Exception ex) {
			System.out.print("Internation.language  ");
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			value = key;
		}
		return value;
	}

	public static String getHl() {
		String hl = "";
		HttpServletRequest request = ActionContext.getRequest();
		try {
			if (request != null) {
				hl = request.getParameter("hl");
				if (hl == null) {
					hl = request.getHeader("Accept-Language");
				}
			}
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		// 默认中文
		if (hl == null)
			return "zh-cn";
		hl = hl.toLowerCase();
		if ("zh-cn".equals(hl))
			return hl;
		else if (hl.startsWith("en"))
			return "en";
		else if ("zh-tw".equals(hl) || "zh-hk".equals(hl) || "zh-sg".equals(hl) || "zh-mo".equals(hl))
			return "zh-tw";
		else
			return "zh-cn";
	}
}
