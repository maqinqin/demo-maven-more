package com.git.cloud.cloudservice.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

/** 
 * 类说明 
 * @author SunHailong 
 * @version 版本号 2017-3-25
 */
public class CloudFlavorPo extends BaseBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private Integer cpu;
	private Integer mem;
	private Integer disk;
	private String type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCpu() {
		return cpu;
	}
	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
	public Integer getMem() {
		return mem;
	}
	public void setMem(Integer mem) {
		this.mem = mem;
	}
	public Integer getDisk() {
		return disk;
	}
	public void setDisk(Integer disk) {
		this.disk = disk;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
