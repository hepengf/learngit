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
@Table(name = "UN_CERT_APP")
public class UnCertApp implements java.io.Serializable {

	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_UNCERTAPP_GEN")
	@SequenceGenerator(name = "SEQ_UNCERTAPP_GEN", sequenceName = "SEQ_UNCERTAPP")
	private Integer id;
	private String c;
	private String cn;
	private String l;
	private String st;
	private String o;
	private String ou;
	private String net;
	private String email;
	private Date createdate;
	private Integer isSign;
	private String signOperator;
	private Date signdate;
	private String userid;
	private String batch;
	
	
	@Column(name = "batch") 
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Column(name = "userid") 
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Column(name = "signdate") 
	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	@Column(name = "createdate") 
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "issign") 
	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	@Column(name = "signoperator") 
	public String getSignOperator() {
		return signOperator;
	}

	public void setSignOperator(String signOperator) {
		this.signOperator = signOperator;
	}

	@Column(name = "id") 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "c") 
	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	@Column(name = "cn") 
	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	@Column(name = "l") 
	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	@Column(name = "st") 
	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	@Column(name = "o") 
	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

	@Column(name = "ou") 
	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	@Column(name = "net") 
	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	public UnCertApp() {
	}

	public UnCertApp(Integer id, String c, String cn, String l, String st,
			String o, String ou, String net, String email, Date createdate,
			Integer isSign, String signOperator, Date signdate, String userid,String batch) {
		super();
		this.id = id;
		this.c = c;
		this.cn = cn;
		this.l = l;
		this.st = st;
		this.o = o;
		this.ou = ou;
		this.net = net;
		this.email = email;
		this.createdate = createdate;
		this.isSign = isSign;
		this.signOperator = signOperator;
		this.signdate = signdate;
		this.userid = userid;
		this.batch = batch;
	}

	@Column(name = "email") 
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}