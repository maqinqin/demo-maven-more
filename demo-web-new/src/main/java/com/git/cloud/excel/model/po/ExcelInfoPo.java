package com.git.cloud.excel.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class ExcelInfoPo extends BaseBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String fileName;
	
	private String filePath;
	
	private String isInput;
	
	private String fileType;
	


	

	public ExcelInfoPo() {
		super();
		// TODO Auto-generated constructor stub
	}





	public ExcelInfoPo(String id, String fileName, String filePath,
			String isInput, String fileType) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.filePath = filePath;
		this.isInput = isInput;
		this.fileType = fileType;
	}





	public String getId() {
		return id;
	}





	public void setId(String id) {
		this.id = id;
	}





	public String getFileName() {
		return fileName;
	}





	public void setFileName(String fileName) {
		this.fileName = fileName;
	}





	public String getFilePath() {
		return filePath;
	}





	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}





	public String getIsInput() {
		return isInput;
	}





	public void setIsInput(String isInput) {
		this.isInput = isInput;
	}





	public String getFileType() {
		return fileType;
	}





	public void setFileType(String fileType) {
		this.fileType = fileType;
	}





	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
