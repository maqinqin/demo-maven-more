package com.git.cloud.taglib.common;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.taglib.util.Internation;

/**
 * 国际化标签
 * @author SunHailong
 */
public class LabelTag extends TagSupport {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 1L;
	
	private String name; // 名称
	private String itype; // 国际化文件类别

	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		try {
			String strText = Internation.language(itype, name);
			if (strText == null || "".equals(strText)) {
				strText = name;
			}
			out.print(strText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItype() {
		return itype;
	}
	public void setItype(String itype) {
		this.itype = itype;
	}
}
