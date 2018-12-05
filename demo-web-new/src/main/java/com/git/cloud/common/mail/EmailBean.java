package com.git.cloud.common.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 邮件基础信息
 * @author SunHailong
 */
public class EmailBean {

	// 邮件体参数
	private List<String> mailTo = new ArrayList<String>();// 收件人地址 (多个采用,隔开)
	private String subject;// 邮件主题
	private String msgContent;// 邮件内容
	private List<String> mailccTo = new ArrayList<String>();//邮件抄送 (多个采用,隔开)
	private Vector attachedFileList;//附件,待实现
	private String messageContentMimeType = "text/html; charset=gbk";
	
	public List<String> getMailTo() {
		return mailTo;
	}
	public void setMailTo(List<String> mailTo) {
		this.mailTo = mailTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public List<String> getMailccTo() {
		return mailccTo;
	}
	public void setMailccTo(List<String> mailccTo) {
		this.mailccTo = mailccTo;
	}
	public Vector getAttachedFileList() {
		return attachedFileList;
	}
	public void setAttachedFileList(Vector attachedFileList) {
		this.attachedFileList = attachedFileList;
	}
	public String getMessageContentMimeType() {
		return messageContentMimeType;
	}
	public void setMessageContentMimeType(String messageContentMimeType) {
		this.messageContentMimeType = messageContentMimeType;
	}
}
