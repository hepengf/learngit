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
@Table(name = "UN_CERT_BULK")
public class UnCertBulk implements Serializable {
	@Id
	@GeneratedValue(generator = "UnCertBulkGenerator")
	@GenericGenerator(name = "UnCertBulkGenerator", strategy = "native")
	private Integer id;
	private String fname;
	private String downpath;
	private Integer createOperator;
	private String createOperators;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	/**
	 * 证书类型 1-终端证书 2-服务器证书
	 */
	private Integer ftype;
	private String downFname;
	private Integer count;
	
	@Column(name = "type")
	public Integer getFtype() {
		return this.ftype;
	}
	
	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}
	
	@Column(name = "downpath")
	public String getDownpath() {
		return downpath;
	}

	public void setDownpath(String downpath) {
		this.downpath = downpath;
	}

	@Column(name = "downFname")
	public String getDownFname() {
		return downFname;
	}

	public void setDownFname(String downFname) {
		this.downFname = downFname;
	}

	@Column(name = "count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
		
	@Column(name = "createOperators")
	public String getCreateOperators() {
		return createOperators;
	}
	
	public void setCreateOperators(String createOperators) {
		this.createOperators = createOperators;
	}
	

	public UnCertBulk() {
	}

	public UnCertBulk(Integer id, String fname, String downpath,
			Integer createOperator, String createOperators, Date createTime,
			Integer ftype, String downFname, Integer count) {
		super();
		this.id = id;
		this.fname = fname;
		this.downpath = downpath;
		this.createOperator = createOperator;
		this.createOperators = createOperators;
		this.createTime = createTime;
		this.ftype = ftype;
		this.downFname = downFname;
		this.count = count;
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


	@Column(name = "FNAME")
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	
	
}
