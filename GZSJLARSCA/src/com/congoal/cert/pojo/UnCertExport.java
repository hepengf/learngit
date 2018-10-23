package com.congoal.cert.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 证书导出对象
 * 
 * @author charles
 * 
 */
@SuppressWarnings("all")
@Entity
@Table(name = "UN_CERT_EXPORT")
public class UnCertExport implements Serializable {
	public static class UnCertExportStatus {
		/**
		 * 导出文件标识
		 * 
		 */
		public static class ExportFlag {
			/**
			 * 公钥
			 */
			public static final int publicKey = 1;
			/**
			 * 私钥
			 */
			public static final int privateKey = 2;
		}

	}

	@Id
	@GeneratedValue(generator = "UnCertExportGenerator")
	@GenericGenerator(name = "UnCertExportGenerator", strategy = "native")
	private Integer id;
	private Integer exportFlag;
	@Temporal(TemporalType.TIMESTAMP)
	private Date exportDate;
	private String batch;
	private Integer status;
	private String fileType;
	private String path;
	private String fname;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastDownLoadTime;
	private Integer lastDownLoadOperator;
	private String lastDownLoadOperators;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private Integer createOperator;
	private String createOperators;
	private Integer downLoadCount;

	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "exportFlag")
	public Integer getExportFlag() {
		return exportFlag;
	}

	public void setExportFlag(Integer exportFlag) {
		this.exportFlag = exportFlag;
	}

	@Column(name = "exportDate")
	public Date getExportDate() {
		return exportDate;
	}

	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	@Column(name = "batch")
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "fileType")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "fname")
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "lastDownLoadTime")
	public Date getLastDownLoadTime() {
		return lastDownLoadTime;
	}

	public void setLastDownLoadTime(Date lastDownLoadTime) {
		this.lastDownLoadTime = lastDownLoadTime;
	}

	@Column(name = "lastDownLoadOperator")
	public Integer getLastDownLoadOperator() {
		return lastDownLoadOperator;
	}

	public void setLastDownLoadOperator(Integer lastDownLoadOperator) {
		this.lastDownLoadOperator = lastDownLoadOperator;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "createOperator")
	public Integer getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(Integer createOperator) {
		this.createOperator = createOperator;
	}

	@Column(name = "downLoadCount")
	public Integer getDownLoadCount() {
		return downLoadCount;
	}

	public void setDownLoadCount(Integer downLoadCount) {
		this.downLoadCount = downLoadCount;
	}

	@Column(name = "lastDownLoadOperators")
	public String getLastDownLoadOperators() {
		return lastDownLoadOperators;
	}

	public void setLastDownLoadOperators(String lastDownLoadOperators) {
		this.lastDownLoadOperators = lastDownLoadOperators;
	}

	@Column(name = "createOperators")
	public String getCreateOperators() {
		return createOperators;
	}

	public void setCreateOperators(String createOperators) {
		this.createOperators = createOperators;
	}

	public UnCertExport() {
	}

	public UnCertExport(Integer id, Integer exportFlag, Date exportDate,
			String batch, Integer status, String fileType, String path,
			String fname, Date lastDownLoadTime, Integer lastDownLoadOperator,
			Date createTime, Integer createOperator, Integer downLoadCount) {
		super();
		this.id = id;
		this.exportFlag = exportFlag;
		this.exportDate = exportDate;
		this.batch = batch;
		this.status = status;
		this.fileType = fileType;
		this.path = path;
		this.fname = fname;
		this.lastDownLoadTime = lastDownLoadTime;
		this.lastDownLoadOperator = lastDownLoadOperator;
		this.createTime = createTime;
		this.createOperator = createOperator;
		this.downLoadCount = downLoadCount;
	}

}
