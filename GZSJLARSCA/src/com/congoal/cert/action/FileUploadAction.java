package com.congoal.cert.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.congoal.cert.utils.BasicAction;
import com.congoal.cert.utils.CertCommonUtils;

import common.Logger;

@Scope("prototype")
@Controller("fileUploadAction")
public class FileUploadAction extends BasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(FileUploadAction.class);
	private File upload;
	private String uploadFileName;
	private String uploadFileContent;
	private List<HashMap<String, Object>> pubInfo;
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadFileContent() {
		return uploadFileContent;
	}
	public void setUploadFileContent(String uploadFileContent) {
		this.uploadFileContent = uploadFileContent;
	}
	
	public String upload() {
		
		return "upload";
	}
	
}
