package com.git.cloud.resmgt.common.model.po;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.resmgt.common.model.vo.RmResPoolVo;

public class RmDatacenterPo extends BaseBO implements java.io.Serializable{


	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4525905759544097323L;
	private String id;
	private String datacenterCode;
	private String datacenterCname;
	private String ename;
	private String address;
	private String status;
	private String isActive;
	private String remark;
	private String queueIden;
	
	//private String datacenterCName;
	private String eName;
	private String createUser;
	private String updateUser;
	private String sort;
	
	private String url;
	private String username;
	private String password;

	// Constructors

	/** default constructor */
	public RmDatacenterPo() {
	}

	/** minimal constructor */
	public RmDatacenterPo(String id) {
		this.id = id;
	}

	/** full constructor */
	public RmDatacenterPo(String id, String datacenterCode,
			String datacenterCname, String ename, String address,
			String status, String isActive, String remark) {
		this.id = id;
		this.datacenterCode = datacenterCode;
		this.datacenterCname = datacenterCname;
		this.ename = ename;
		this.address = address;
		this.status = status;
		this.isActive = isActive;
		this.remark = remark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	/*public String getDatacenterCName() {
		return ;
	}

	public void setDatacedatacenterCNamenterCName(String datacenterCName) {
		this.datacenterCName = datacenterCName;
	}*/

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}


	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDatacenterCode() {
		return this.datacenterCode;
	}

	public void setDatacenterCode(String datacenterCode) {
		this.datacenterCode = datacenterCode;
	}

	public String getDatacenterCname() {
		return this.datacenterCname;
	}

	public void setDatacenterCname(String datacenterCname) {
		this.datacenterCname = datacenterCname;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getQueueIden() {
		return queueIden;
	}

	public void setQueueIden(String queueIden) {
		this.queueIden = queueIden;
	}
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<RmResPoolVo> getPoolList() {
		return poolList;
	}

	public void setPoolList(List<RmResPoolVo> poolList) {
		this.poolList = poolList;
	}

	private List<RmResPoolVo> poolList;

}
