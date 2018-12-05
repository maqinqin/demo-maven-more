package com.git.cloud.excel.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.git.cloud.common.action.BaseAction;

public class UploadExcelAction extends BaseAction {

	private File file;
	
	//提交过来的file的名字
    private String fileFileName;
    
  //提交过来的file的MIME类型
    private String fileContentType;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String uploadAction() throws Exception{
		 String root = ServletActionContext.getServletContext().getRealPath("/uploadexcel");
	        
        InputStream is = new FileInputStream(file);
        File f = new File(root, fileFileName);
        OutputStream os = new FileOutputStream(f);
        
        byte[] buffer = new byte[500];
        int length = 0;
        
        while(-1 != (length = is.read(buffer, 0, buffer.length)))
        {
            os.write(buffer);
        }
        
        os.close();
        is.close();
        return SUCCESS;
	}
	
	
}
