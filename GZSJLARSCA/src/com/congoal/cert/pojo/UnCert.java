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
 * 证书对象
 * 
 * @author charles
 * 
 */
@SuppressWarnings("all")
@Entity
@Table(name = "UN_CERT")
public class UnCert implements Serializable {
	@Id
	@GeneratedValue(generator = "UnCertGenerator")
	@GenericGenerator(name = "UnCertGenerator", strategy = "native")
	private Integer id;
	private Integer enabled;
	private String subjectdn;
	private String issuredn;
	private String serialnumber;
	private Date begindate;
	private Date enddate;
	private String publickey;
	private String privatekey;
	private String sigalgname;
	private Integer version;
	private Integer ctype;
	private String fname;
	private String username;
	private Integer userid;
	private String batch;
	private String alias;
	private Integer status;
	private String secret;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private String createOperators;
	private Integer createOperator;
	private String company;
	/**
	 * 证书类型 1-根证书 2-子证书
	 */
	private Integer certType;
	private Integer createCrl;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createCrlTime;
	private Integer crl;
	
	@Column(name = "enabled")
	public Integer getEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	@Column(name = "createCrl")
	public Integer getCreateCrl() {
		return createCrl;
	}

	public void setCreateCrl(Integer createCrl) {
		this.createCrl = createCrl;
	}

	@Column(name = "createCrlTime")
	public Date getCreateCrlTime() {
		return createCrlTime;
	}

	public void setCreateCrlTime(Date createCrlTime) {
		this.createCrlTime = createCrlTime;
	}

	@Column(name = "crl")
	public Integer getCrl() {
		return crl;
	}

	public void setCrl(Integer crl) {
		this.crl = crl;
	}

	@Column(name = "secret")
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "certType")
	public Integer getCertType() {
		return certType;
	}

	public void setCertType(Integer certType) {
		this.certType = certType;
	}
	
	@Column(name = "username")
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "createOperators")
	public String getCreateOperators() {
		return createOperators;
	}
	
	@Column(name = "company")
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public void setCreateOperators(String createOperators) {
		this.createOperators = createOperators;
	}
	
	public static class UnCertStatus {
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
			 * 预吊销
			 */
			public static final int beforeDisable = 2;
			/**
			 * 吊销
			 */
			public static final int disable = -1;
		}

		public static class CertType {
			/**
			 * 根证书
			 */
			public static final int root = 1;
			/**
			 * 子证书
			 */
			public static final int child = 2;
		}
		
		public static class CRL {
			/**
			 * 已创建crl
			 */
			public static final int yes = 1;
			/**
			 * 未创建crl
			 */
			public static final int no = 0;
		}
	}

	@Column(name = "alias")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public UnCert() {
	}



	public UnCert(Integer id, String subjectdn, String issuredn,
			String serialnumber, Date begindate, Date enddate,
			String publickey, String privatekey, String sigalgname,
			Integer version, Integer ctype, String fname, Integer userid,
			String batch, String alias, Integer status, String secret,
			Date createTime, Integer createOperator, Integer certType,
			Integer createCrl, Date createCrlTime, Integer crl) {
		super();
		this.id = id;
		this.subjectdn = subjectdn;
		this.issuredn = issuredn;
		this.serialnumber = serialnumber;
		this.begindate = begindate;
		this.enddate = enddate;
		this.publickey = publickey;
		this.privatekey = privatekey;
		this.sigalgname = sigalgname;
		this.version = version;
		this.ctype = ctype;
		this.fname = fname;
		this.userid = userid;
		this.batch = batch;
		this.alias = alias;
		this.status = status;
		this.secret = secret;
		this.createTime = createTime;
		this.createOperator = createOperator;
		this.certType = certType;
		this.createCrl = createCrl;
		this.createCrlTime = createCrlTime;
		this.crl = crl;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "SUBJECTDN")
	public String getSubjectdn() {
		return subjectdn;
	}

	public void setSubjectdn(String subjectdn) {
		this.subjectdn = subjectdn;
	}

	@Column(name = "ISSUREDN")
	public String getIssuredn() {
		return issuredn;
	}

	public void setIssuredn(String issuredn) {
		this.issuredn = issuredn;
	}

	@Column(name = "SERIALNUMBER")
	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	@Column(name = "BEGINDATE")
	public Date getBegindate() {
		return begindate;
	}

	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}

	@Column(name = "ENDDATE")
	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	@Column(name = "PUBLICKEY")
	public String getPublickey() {
		return publickey;
	}

	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}

	@Column(name = "PRIVATEKEY")
	public String getPrivatekey() {
		return privatekey;
	}

	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	@Column(name = "SIGALGNAME")
	public String getSigalgname() {
		return sigalgname;
	}

	public void setSigalgname(String sigalgname) {
		this.sigalgname = sigalgname;
	}

	@Column(name = "VERSION")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "CTYPE")
	public Integer getCtype() {
		return ctype;
	}

	public void setCtype(Integer ctype) {
		this.ctype = ctype;
	}

	@Column(name = "FNAME")
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "USERID")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "BATCH")
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

}
