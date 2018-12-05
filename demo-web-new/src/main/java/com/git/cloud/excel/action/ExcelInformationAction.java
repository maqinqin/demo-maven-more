package com.git.cloud.excel.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.excel.model.po.ExcelInfoPo;
import com.git.cloud.excel.service.IExcelInfoService;

@SuppressWarnings({ "serial", "rawtypes" })
public class ExcelInformationAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(ExcelInformationAction.class);
	
	private IExcelInfoService iExcelInfoService;
	
	private InputStream inputStream;
	
	private String fileName;
	
	private String fileContentType;

	
	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String getFileContentType() {
		return fileContentType;
	}



	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}



	public InputStream getInputStream() {
		return inputStream;
	}



	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}



	public IExcelInfoService getiExcelInfoService() {
		return iExcelInfoService;
	}



	public void setiExcelInfoService(IExcelInfoService iExcelInfoService) {
		this.iExcelInfoService = iExcelInfoService;
	}



	public void excelInfomationAction()throws Exception{
		List<ExcelInfoPo> excelList = iExcelInfoService.showExcelInfoList();
		arrayOut(excelList);
	}
	

	@SuppressWarnings("unchecked")
	public String outPutExcelAction()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileType = request.getParameter("fileType");
		HashMap map = new HashMap();
		map.put("fileType", fileType);
		//跟据文件类型查询数据库中是否已经存在该信息
		ExcelInfoPo excelInfoPo = iExcelInfoService.showExcelInfoByType(map);
		if(excelInfoPo != null){
			String fileName = new String(excelInfoPo.getFileName().getBytes("iso8859-1"),"UTF-8");
			this.setFileName(fileName);
			setFileContentType("application/vnd.ms-excel");
			 //上传的文件都是保存在/WEB-INF/uploadexcel目录下的
	         String fileSaveRootPath= ServletActionContext.getServletContext().getRealPath("/uploadexcel"); 
	         String path = fileSaveRootPath +File.separator+fileName;
			File file = new File(path); 
			try {
				
				setInputStream(new FileInputStream(file));
			} catch (IOException e) {
				logger.error("io异常",e);
				return ERROR;
			}

			
		}
		return SUCCESS; 

	}
	
	
	
}
