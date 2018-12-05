package com.git.cloud.cloudservice.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.appmgt.model.vo.DeployUnitVo;
import com.git.cloud.appmgt.service.IDeployunitService;
import com.git.cloud.cloudservice.model.CloudSrvStsEnum;
import com.git.cloud.cloudservice.model.po.CloudFile;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.cloudservice.model.po.CloudServiceVo;
import com.git.cloud.cloudservice.service.ICloudFileService;
import com.git.cloud.cloudservice.service.ICloudServiceService;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.tools.PropertiesTools;

import net.sf.json.JSONObject;


public class CloudServiceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1461015574729711914L;

	private ICloudServiceService cloudServiceService;
	private ICloudFileService cloudFileService;
	private CloudServicePo cloudServicePo;
	private CloudServiceVo cloudServiceVo;
	private CloudFile cloudFile;

	
	
	private File file; //得到上传的文件
    private String fileContentType; //得到文件的类型
    private String fileFileName; //得到文件的名称
    
    public String uploads(){
        
        //获取要保存文件夹的物理路径(绝对路径)
        String realPath=ServletActionContext.getServletContext().getRealPath("/upload");
        File file = new File(realPath);
        
        //测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
        if(!file.exists())file.mkdirs();
        
        try {
            //保存文件
            FileUtils.copyFile(file, new File(file,fileFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    

	public File getFile() {
		return file;
	}



	public void setFile(File file) {
		this.file = file;
	}



	public String getFileContentType() {
		return fileContentType;
	}



	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}



	public String getFileFileName() {
		return fileFileName;
	}



	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}



	public CloudFile getCloudFile() {
		return cloudFile;
	}

	public void setCloudFile(CloudFile cloudFile) {
		this.cloudFile = cloudFile;
	}

	public void setCloudFileService(ICloudFileService cloudFileService) {
		this.cloudFileService = cloudFileService;
	}

	@Autowired
	private IDeployunitService deployunitServiceImpl;
	public CloudServiceVo getCloudServiceVo() {
		return cloudServiceVo;
	}

	public void setCloudServiceVo(CloudServiceVo cloudServiceVo) {
		this.cloudServiceVo = cloudServiceVo;
	}
	
	private Pagination pagination;
	private Map<String, String> pageParams;
	private Map<String, Object> result;

	public String index() {
		return SUCCESS;
	}

	public String search() {
		pagination = cloudServiceService.queryPagination(this.getPaginationParam());
		if(pagination != null){
			return SUCCESS;
		}
		return null;
	}

	public String save() throws Exception {
		if (cloudServicePo != null) {
			if (cloudServicePo.getServiceId() == null || "".equals(cloudServicePo.getServiceId())) {
				cloudServiceService.save(cloudServicePo);
			} else {
				cloudServiceService.update(cloudServicePo);
			}
		}
		return SUCCESS;
	}

	public String stop() throws Exception {
		if (cloudServicePo != null) {
			if (cloudServicePo.getServiceId() != null && !"".equals(cloudServicePo.getServiceId())) {
				cloudServicePo = cloudServiceService.findById(cloudServicePo.getServiceId());
				if (this.getPageParams() != null && this.getPageParams().containsKey("start")) {
					cloudServicePo.setServiceStatus(CloudSrvStsEnum.OPEN.getValue());
					cloudServiceService.update(cloudServicePo);
				} else {
					cloudServicePo.setServiceStatus(CloudSrvStsEnum.CLOSED.getValue());
					cloudServiceService.update(cloudServicePo);
				}
//				cloudServiceService.update(cloudServicePo);
			}
		}
		return SUCCESS;
	}
	public void queryVmType() throws Exception {
		String platFormId = this.getRequest().getParameter("platFormId");
		List<Map<String, Object>> result = cloudServiceService.queryVmType(platFormId);
		arrayOut(result);
	}
	
	/**
	 * 级联删除云服务定义
	 * @Title: delete
	 * @Description: TODO
	 * @field: @return
	 * @field: @throws Exception
	 * @return String
	 * @throws
	 */
	public void delete() throws Exception {
		String result = "";
		if (cloudServicePo != null) {
			if (cloudServicePo.getServiceId() != null && !"".equals(cloudServicePo.getServiceId())) {
				String[] ids = new String[] { cloudServicePo.getServiceId() };
				List<DeployUnitVo> DeployUnitVoList = deployunitServiceImpl.deployUnitCheckByServiceId(cloudServicePo.getServiceId());
				if(DeployUnitVoList.size()>0){
					for(DeployUnitVo vo:DeployUnitVoList){
						result += vo.getAppCname()+"-"+vo.getEname()+",";
					}
					result += "正在使用该服务";
				}else{
					cloudServiceService.deleteById(ids);
					result = "SUCCESS";
				}
			}
		}
		stringOut(result);
	}
	
	/**
	 * 进行文件的导出
	 * @return
	 * @throws Exception
	 */
	public void cloudleading() throws Exception {
		String serverId = this.getRequest().getParameter("serverId");
		cloudFile = cloudFileService.cloudleading(serverId);
		String serviceFile = PropertiesTools.getPropertiesParam("serviceFileUrl");
        // 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String realPath = ServletActionContext.getServletContext().getRealPath(
				serviceFile);
        File tmpDir = new File(realPath);
        // 判断上传文件的保存目录是否存在
        if (!tmpDir.exists() && !tmpDir.isDirectory())
        {
            tmpDir.createNewFile();
            // 创建目录
            tmpDir.mkdir();
        }
        //获取String类型的时间
        String fileWriteTime = new Date().getTime()+"";
        String tmpFileName = realPath + fileWriteTime + ".txt";
        File file = new File(tmpFileName);
        // 判断上传文件的保存目录是否存在
        if (!file.exists())
        {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, true);
        // 流的方式
        BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
        bufferWriter.write(JSONObject.fromObject(cloudFile).toString());
        bufferWriter.close();
        
        fileWriter.close();
      //将文件读入文件流
    	InputStream inStream = new FileInputStream(tmpFileName);
    	//获得浏览器代理信息
    	final String userAgent = super.getRequest().getHeader("USER-AGENT");
    	//判断浏览器代理并分别设置响应给浏览器的编码格式
    	String finalFileName = null;
    	if(StringUtils.contains(userAgent, "MSIE")||StringUtils.contains(userAgent,"Trident")){//IE浏览器
    	finalFileName = URLEncoder.encode("service.txt","UTF8");
    	}else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
    	finalFileName = new String("service.txt".getBytes(), "ISO8859-1");
    	}else{
    	finalFileName = URLEncoder.encode("service.txt","UTF8");//其他浏览器
    	}
    	//设置HTTP响应头
    	super.getResponse().reset();//重置 响应头
    	super.getResponse().setContentType("application/x-download");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
    	super.getResponse().addHeader("Content-Disposition" ,"attachment;filename=\"" +finalFileName+ "\"");//下载文件的名称
    	byte[] b = new byte[1024];
    	int len;
    	while ((len = inStream.read(b)) > 0){
    	super.getResponse().getOutputStream().write(b, 0, len);
    	}
    	inStream.close();
    	super.getResponse().getOutputStream().close();
    }
	
	
	
	public void checkCloudServices()throws Exception{
		super.ObjectOut(cloudServiceService.checkCloudServices(cloudServicePo));
	}
	public void checkCloudTypeCode()throws Exception{
		super.ObjectOut(cloudServiceService.checkCloudTypeCode(cloudServicePo));
	}

	public String load() throws Exception {
		cloudServicePo = cloudServiceService.findById(cloudServicePo.getServiceId());
		return SUCCESS;
	}
	
	public String loadAll() throws Exception {
		cloudServiceVo = cloudServiceService.findAllById(cloudServicePo.getServiceId());
		return SUCCESS;
	}
	public ICloudServiceService getCloudServiceService() {
		return cloudServiceService;
	}
	

	public void setCloudServiceService(ICloudServiceService cloudServiceService) {
		this.cloudServiceService = cloudServiceService;
	}

	public CloudServicePo getCloudServicePo() {
		return cloudServicePo;
	}

	public void setCloudServicePo(CloudServicePo cloudServicePo) {
		this.cloudServicePo = cloudServicePo;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Map<String, String> getPageParams() {
		return pageParams;
	}

	public void setPageParams(Map<String, String> pageParams) {
		this.pageParams = pageParams;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
}
