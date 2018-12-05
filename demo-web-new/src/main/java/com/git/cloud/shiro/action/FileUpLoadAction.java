package com.git.cloud.shiro.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.shiro.model.CertificatePo;
import com.git.cloud.shiro.service.IFileUpLoadService;


@SuppressWarnings("rawtypes")
public class FileUpLoadAction extends BaseAction{
	

	private static final long serialVersionUID = 1L;
	private File myFile;
	private String contentType;//文件类型
	private String myFileFileName;
	private CertificatePo certificatePo;
	private IFileUpLoadService fileUpLoadService;
	public File getMyFile() {
		return myFile;
	}
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getMyFileFileName() {
		return myFileFileName;
	}
	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}
	
	
	public CertificatePo getCertificatePo() {
		return certificatePo;
	}
	public void setCertificatePo(CertificatePo certificatePo) {
		this.certificatePo = certificatePo;
	}
	
	public IFileUpLoadService getFileUpLoadService() {
		return fileUpLoadService;
	}
	public void setFileUpLoadService(IFileUpLoadService fileUpLoadService) {
		this.fileUpLoadService = fileUpLoadService;
	}
	public String fileUpload()throws Exception{
			String root=ServletActionContext.getServletContext().getRealPath("");
			//System.out.println(root);
			InputStream inputStream;
			File destFile;
			OutputStream os;
			inputStream=new FileInputStream(myFile);//读入上传的文件
			destFile=new File(root,this.getMyFileFileName());
			os=new FileOutputStream(destFile);
			byte[] buffer=new byte[400];
			 int length=0;
			 while((length=inputStream.read(buffer))>0){
				 os.write(buffer,0,length);
				 }
			inputStream.close();
			os.close();
			 CertificatePo  certificatePo=new  CertificatePo ();
			             certificatePo.setId("1");
			             certificatePo.setCertificateName(this.getMyFileFileName());
			             certificatePo.setCertificatePath(root.replaceAll("\\\\", "/"));
			           //  certificatePo.setUserName(userName);
			           // certificatePo.setCreateDatetime(new Date());
			             fileUpLoadService.saveCertificate(certificatePo);
			             
			
			
			HttpServletRequest request=ServletActionContext.getRequest();
			request.setAttribute("uploadResult", "上传成功");
			return  SUCCESS ;
		
		
	}
}
