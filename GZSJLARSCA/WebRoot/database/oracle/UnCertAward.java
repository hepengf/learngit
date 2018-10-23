package com.congoal.cert.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "UN_CERT_AWARD")
public class UnCertAward {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_UNCERTAWARD_GEN")
    @SequenceGenerator(name="SEQ_UNCERTAWARD_GEN", sequenceName = "SEQ_UNCERTAWARD")
	private Integer id;
	private Integer ctype;
	private Integer status;
	private String path;
	private String fname;
	private String userid;
	private String signOperator;
	private Date awardDate;
	private String batch;
	private String disabledoperator;
	private Date disableddate;
	
	
	
	
	public String getDisabledoperator() {
		return disabledoperator;
	}
	public void setDisabledoperator(String disabledoperator) {
		this.disabledoperator = disabledoperator;
	}
	public Date getDisableddate() {
		return disableddate;
	}
	public void setDisableddate(Date disableddate) {
		this.disableddate = disableddate;
	}
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
	public Integer getCtype() {
		return ctype;
	}
	public void setCtype(Integer ctype) {
		this.ctype = ctype;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSignOperator() {
		return signOperator;
	}
	public void setSignOperator(String signOperator) {
		this.signOperator = signOperator;
	}
	public Date getAwardDate() {
		return awardDate;
	}
	public void setAwardDate(Date awardDate) {
		this.awardDate = awardDate;
	}
	public UnCertAward(){}
	public UnCertAward(Integer id, Integer ctype, Integer status, String path,
			String fname, String userid, String signOperator, Date awardDate,String batch,String disabledoperator,
			Date disableddate
			) {
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
		
	}
	
	
}
