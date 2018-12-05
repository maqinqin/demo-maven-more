package com.git.cloud.sys.model.po;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class SysMailMsgPo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String type;
	private String receives;
	private String carbonCopy;
	private String title;
	private String content;
	private String sendStatus;
	private Date createDateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReceives() {
		return receives;
	}

	public void setReceives(String receives) {
		this.receives = receives;
	}

	public String getCarbonCopy() {
		return carbonCopy;
	}

	public void setCarbonCopy(String carbonCopy) {
		this.carbonCopy = carbonCopy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
