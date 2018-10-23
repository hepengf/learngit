package com.congoal.cert.pojo;

import java.io.Serializable;
import java.math.BigInteger;
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
 * 证书颁发对象
 * 
 * @author charles
 * 
 */
@SuppressWarnings("all")
@Entity
@Table(name = "UN_CERT_CRL")
public class UnCertCRL implements Serializable {
	public static class UnCertCRLStatus {
		/**
		 * 是否需要创建新密钥对
		 * 
		 */
		public static class Status {
			/**
			 * 启用
			 */
			public static final int enable = 1;
			/**
			 * 作废
			 */
			public static final int disable = 2;
		}
	}

	@Id
	@GeneratedValue(generator = "UnCertCRLGenerator")
	@GenericGenerator(name = "UnCertCRLGenerator", strategy = "native")
	private Integer id;
	private Integer status;
	private String issuredn;
	private String path;
	private String fname;
	private Date awardDate;
	private String batch;
	private String serialnumber;
	private String remark;
	@Temporal(TemporalType.TIMESTAMP)
	private Date nextUpdateTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private Integer createOperator;
	private String createOperators;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifyTime;
	private Integer lastModifyOperator;
	private String lastModifyOperators;
	private String fileType;
	

	@Column(name = "issuredn")
	public String getIssuredn() {
		return issuredn;
	}

	public void setIssuredn(String issuredn) {
		this.issuredn = issuredn;
	}

	@Column(name = "nextUpdateTime")
	public Date getNextUpdateTime() {
		return nextUpdateTime;
	}

	public void setNextUpdateTime(Date nextUpdateTime) {
		this.nextUpdateTime = nextUpdateTime;
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

	@Column(name = "lastModifyTime")
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Column(name = "lastModifyOperator")
	public Integer getLastModifyOperator() {
		return lastModifyOperator;
	}

	public void setLastModifyOperator(Integer lastModifyOperator) {
		this.lastModifyOperator = lastModifyOperator;
	}

	@Column(name = "fileType")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "serialnumber")
	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	@Column(name = "batch")
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Column(name = "awardDate")
	public Date getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(Date awardDate) {
		this.awardDate = awardDate;
	}

	@Column(name = "createOperators")
	public String getCreateOperators() {
		return createOperators;
	}
	
	public void setCreateOperators(String createOperators) {
		this.createOperators = createOperators;
	}

	@Column(name = "lastModifyOperators")
	public String getLastModifyOperators() {
		return lastModifyOperators;
	}

	public void setLastModifyOperators(String lastModifyOperators) {
		this.lastModifyOperators = lastModifyOperators;
	}

	public UnCertCRL() {
	}

	public UnCertCRL(Integer id, Integer status, String issuredn, String path,
			String fname, Date awardDate, String batch, String serialnumber,
			String remark, Date nextUpdateTime, Date createTime,
			Integer createOperator, Date lastModifyTime,
			Integer lastModifyOperator, String fileType) {
		super();
		this.id = id;
		this.status = status;
		this.issuredn = issuredn;
		this.path = path;
		this.fname = fname;
		this.awardDate = awardDate;
		this.batch = batch;
		this.serialnumber = serialnumber;
		this.remark = remark;
		this.nextUpdateTime = nextUpdateTime;
		this.createTime = createTime;
		this.createOperator = createOperator;
		this.lastModifyTime = lastModifyTime;
		this.lastModifyOperator = lastModifyOperator;
		this.fileType = fileType;
	}
	
	

}
