package com.git.cloud.iaas.openstack.model;

public class FlavorRestModel {

	
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
	public String toString() {
		return "CloudFlavorPo [id=" + id + ", name=" + name + ", cpu=" + cpu + ", mem=" + mem + ", disk=" + disk
				+ ", type=" + type + "]";
	}

	


}
