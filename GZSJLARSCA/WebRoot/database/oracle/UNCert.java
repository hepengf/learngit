package com.congoal.cert.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "UN_CERT")
public class UNCert {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_UNCERT_GEN")
    @SequenceGenerator(name="SEQ_UNCERT_GEN", sequenceName = "SEQ_UNCERT")
	private Integer id;
	
	private String subjectdn;
	private String issuredn;
	private String serialnumber;
	private Date begindate;
	private Date enddate;
	private String publickey;
	private String privatekey;
	private String sigalgname;
	private Integer version;
	private String ctype;
	private String fname;
	private String userid;
	private String batch;
	
	public UNCert(){}
	
	public UNCert(Integer id, String subjectdn, String issuredn,
			String serialnumber, Date begindate, Date enddate,
			String publickey, String privatekey, String sigalgname,
			Integer version, String ctype, String fname, String userid,
			String batch) {
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
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
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
