package com.congoal.cert.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 证书颁发对象
 * 
 * @author charles
 * 
 */
@SuppressWarnings("all")
@Entity
@Table(name = "UN_CERT_AWARD")
public class UnCertAward implements Serializable{
	public static class UnCertAwardStatus{
		/**
		 * 是否需要创建新密钥对
		 *
		 */
		public static class Status{
			/**
			 * 启用
			 */
			public static final int enable = 1;
			/**
			 * 作废
			 */
			public static final int disable = 2;
		}
		/**
		 * 
		 * 证书是否被冻结
		 *
		 */
		public static class Lock{
			/**
			 * 启用
			 */
			public static final int unlock = 1;
			/**
			 * 冻结
			 */
			public static final int lock = 2;
		}
		
		/**
		 * 证书标识
		 *
		 */
		public static class CertType{
			/**
			 * 根证书
			 */
			public static final int root = 1;
			/**
			 * 子证书
			 */
			public static final int child = 2;
		}
		
		/**
		 * 是否已导出证书
		 *
		 */
		public static class ExportCert{
			/**
			 * 否
			 */
			public static final int no = 0;
			/**
			 * 是
			 */
			public static final int yes = 1;
		}
	}
	
	@Id
	@GeneratedValue(generator = "UnCertAwardGenerator")
	@GenericGenerator(name = "UnCertAwardGenerator", strategy = "native")
	private Integer id;
	private Integer ctype;
	private Integer status;
	private String path;
	private String fname;
	private Integer userid;
	private String username;
	private Integer signOperator;
	private String signOperators;
	private Date awardDate;
	private String batch;
	private Integer disabledoperator;
	private String disabledoperators;
	private Date disableddate;
	private String serialnumber;
	private String effect;
	private String remark;
	private Integer certType;
	private String alias;
	private Integer isExportCert;
	private String fileType;
	private Integer enabled;
	private String uniqBatch;
	private String company;
	
	@Column(name = "uniqBatch")
	public String getUniqBatch() {
		return this.uniqBatch;
	}
	
	public void setUniqBatch(String uniqBatch) {
		this.uniqBatch = uniqBatch;
	}
	
	@Column(name = "enabled")
	public Integer getEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Column(name = "company")
	public String getCompany() {
		return company;
	}
	
	@Column(name = "fileType")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "isExportCert")
	public Integer getIsExportCert() {
		return isExportCert;
	}

	public void setIsExportCert(Integer isExportCert) {
		this.isExportCert = isExportCert;
	}

	@Column(name = "certType")
	public Integer getCertType() {
		return certType;
	}

	public void setCertType(Integer certType) {
		this.certType = certType;
	}

	@Column(name = "alias")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Column(name = "effect")
	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
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

	@Column(name = "disabledoperator")
	public Integer getDisabledoperator() {
		return disabledoperator;
	}

	public void setDisabledoperator(Integer disabledoperator) {
		this.disabledoperator = disabledoperator;
	}

	@Column(name = "disableddate")
	public Date getDisableddate() {
		return disableddate;
	}

	public void setDisableddate(Date disableddate) {
		this.disableddate = disableddate;
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

	@Column(name = "ctype")
	public Integer getCtype() {
		return ctype;
	}

	public void setCtype(Integer ctype) {
		this.ctype = ctype;
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

	@Column(name = "userid")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "signOperator")
	public Integer getSignOperator() {
		return signOperator;
	}

	public void setSignOperator(Integer signOperator) {
		this.signOperator = signOperator;
	}

	@Column(name = "awardDate")
	public Date getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(Date awardDate) {
		this.awardDate = awardDate;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name = "signOperators")
	public String getSignOperators() {
		return signOperators;
	}

	public void setSignOperators(String signOperators) {
		this.signOperators = signOperators;
	}
	
	@Column(name = "disabledoperators")
	public String getDisabledoperators() {
		return disabledoperators;
	}

	public void setDisabledoperators(String disabledoperators) {
		this.disabledoperators = disabledoperators;
	}

	public UnCertAward() {
	}

	public UnCertAward(Integer id, Integer ctype, Integer status, String path,
			String fname, Integer userid, Integer signOperator, Date awardDate,
			String batch, Integer disabledoperator, Date disableddate,
			String serialnumber, String effect, String remark,String alias,Integer certType
			,Integer isExportCert,String fileType) {
		super();
		this.id = id;
		this.ctype = ctype;
		this.status = status;
		this.path = path;
		this.fname = fname;
		this.userid = userid;
		this.signOperator = signOperator;
		this.awardDate = awardDate;
		this.batch = batch;
		this.disabledoperator = disabledoperator;
		this.disableddate = disableddate;
		this.serialnumber = serialnumber;
		this.effect = effect;
		this.remark = remark;
		this.alias = alias;
		this.certType = certType;
		this.isExportCert = isExportCert;
		this.fileType = fileType;
	}

}
