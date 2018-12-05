package com.git.cloud.cloudservice.action;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudSoftware;
import com.git.cloud.cloudservice.model.po.CloudSoftwareVer;
import com.git.cloud.cloudservice.model.vo.CloudSoftwareVersVo;
import com.git.cloud.cloudservice.service.ISoftWareService;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.support.Pagination;

/**
 * 
 * @ClassName:SoftWareAction
 * @Description:TODO
 * @author 聂海洋
 * @date 2014-12-17 下午2:38:05
 *
 *
 */
public class SoftWareAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CloudSoftware> cloudSoftwares;
	private ISoftWareService softWareService;
	private CloudSoftware cloudSoftware;
	private List<CloudSoftwareVer> cloudSoftwareVers;
	private CloudSoftwareVer cloudSoftwareVer;
	
	
	public CloudSoftwareVer getCloudSoftwareVer() {
		return cloudSoftwareVer;
	}

	public void setCloudSoftwareVer(CloudSoftwareVer cloudSoftwareVer) {
		this.cloudSoftwareVer = cloudSoftwareVer;
	}

	public List<CloudSoftwareVer> getCloudSoftwareVers() {
		return cloudSoftwareVers;
	}

	public void setCloudSoftwareVers(List<CloudSoftwareVer> cloudSoftwareVers) {
		this.cloudSoftwareVers = cloudSoftwareVers;
	}

	public CloudSoftware getCloudSoftware() {
		return cloudSoftware;
	}

	public void setCloudSoftware(CloudSoftware cloudSoftware) {
		this.cloudSoftware = cloudSoftware;
	}

	public ISoftWareService getSoftWareService() {
		return softWareService;
	}

	public void setSoftWareService(ISoftWareService softWareService) {
		this.softWareService = softWareService;
	}

	public List<CloudSoftware> getCloudSoftwares() {
		return cloudSoftwares;
	}

	public void setCloudSoftwares(List<CloudSoftware> cloudSoftwares) {
		this.cloudSoftwares = cloudSoftwares;
	}

	public String showSoft(){
		return SUCCESS;
	}
	
	/**
	 * 镜像管理点击新增时查询所有软件信息
	 * @return
	 */
	public void showSoftWareAll() throws Exception{
		List<CloudSoftware> cloudSoftwares = softWareService.showCloudSoftwareAll();
		this.arrayOut(cloudSoftwares);
	}
	
	/**
	 * 软件管理查询所有软件信息
	 * @return
	 */
	public void showSoftAll() throws Exception{
		Pagination<CloudSoftware> pagination = softWareService.showSoftwareAll(this.getPaginationParam());
		this.jsonOut(pagination);
	}

	
	/**
	 * 新增
	 * @return
	 */
	public void insertSoftWare() throws Exception{
		softWareService.insertCloudSoftware(cloudSoftware);
	}
	
	/**
	 * 修改时查询对象信息
	 * @return
	 */
	public void showSoftWareBysoftWareId() throws Exception{
		String softId = cloudSoftware.getSoftwareId();
		cloudSoftware = softWareService.showSoftWareBysoftWareId(softId);
		this.jsonOut(cloudSoftware);
	}
	
	/**
	 * 修改
	 * @return
	 */
	public void updateSoftWare() throws Exception{
	
		softWareService.updateCloudSoftware(cloudSoftware);
	}
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void deleteSoftWare() throws Exception{
		softWareService.deleteCloudSoftware(cloudSoftware.getSoftwareId().split(","));
	}
	
	/**
	 * 根据软件信息ID查询版本信息,镜像模块使用
	 * @return
	 */
	public void showSoftWareVerAllBySoftWareId() throws Exception{
		String softWareVerId = this.getRequest().getParameter("softWareSelect");
	
		List<CloudSoftwareVer> cloudSoftwareVers = softWareService.showSoftwareVerAll(softWareVerId);
		
		this.arrayOut(cloudSoftwareVers);
	}
	
	/**
	 * 根据软件信息ID显示所有的版本信息,在软件信息页面点击版本按钮时调用
	 * @throws Exception
	 */
	public String showVerBysoftWareIdAll() {
		return SUCCESS;
	}
	
	
	/**
	 * 查询所有软件版本信息
	 * @return
	 */
	public void showVerAllBySoftId() throws Exception{
		Pagination<CloudSoftwareVer> pagination = softWareService.findSoftwareVerAllBySoftId(this.getPaginationParam());
		this.jsonOut(pagination);
	}
	
	
	/**
	 * 新增软见版本
	 * @return
	 */
	public void insertSoftWareVer() throws Exception{
		softWareService.insertSoftWareVer(cloudSoftwareVer);
	}
	
	
	/**
	 * 修改时查询软件版本对象信息
	 * @throws Exception
	 */
	public void showSoftWareVerUpdate() throws Exception{
		String verId = cloudSoftwareVer.getSoftwareVerId();
		cloudSoftwareVer = softWareService.findSoftWareVerByVerId(verId);
		this.jsonOut(cloudSoftwareVer);
	}
	
	/**
	 * 更新软件版本对象信息
	 * @throws Exception
	 */
	public void updateSoftWareVer() throws Exception{
		softWareService.updateSoftWareVer(cloudSoftwareVer);
	}
	
	/**
	 * 删除软件版本对象信息
	 * @throws Exception
	 */
	public void deleteSoftWareVer() throws Exception{
		softWareService.deleteSoftWareVer(cloudSoftwareVer.getSoftwareVerId().split(","));
	}
	
	/**
	 * 通过镜像查询软件版本信息
	 * @Title: findSoftsByImageId
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void findSoftsByImageId()throws Exception{
		Pagination<CloudSoftwareVersVo> pagination = softWareService.findSoftsByImageId(getPaginationParam());
		jsonOut(pagination);
	}
	
	public void checkSoftwareName() throws Exception{
		ObjectOut(softWareService.findCloudSoftwaresByParam(cloudSoftware));
	}
	
	public void checkSoftwareVerName() throws Exception{
		ObjectOut(softWareService.findCloudSoftwareVersByParam(cloudSoftwareVer));
	}
	
}
