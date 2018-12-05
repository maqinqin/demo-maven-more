package com.git.cloud.resmgt.common.model.po;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="cmMacsPo")
@XmlAccessorType(XmlAccessType.FIELD)
public class CmMacsPo {
	private int count;
	private List<CmMacPo> list;

	public CmMacsPo() {

	}

	public CmMacsPo(List<CmMacPo> list) {
		this.list = list;
		this.count = list.size();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<CmMacPo> getList() {
		return list;
	}

	public void setList(List<CmMacPo> list) {
		this.list = list;
	}

}
