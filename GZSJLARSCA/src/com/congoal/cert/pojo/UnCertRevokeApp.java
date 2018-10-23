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
 * 证书吊销申请
 * 
 * @author charles
 * 
 */
@SuppressWarnings("all")
@Entity
@Table(name = "UN_CERT_REVOKE_APP")
public class UnCertRevokeApp implements Serializable{
	
	public static class UnCertRevokeAppStatus{
		public static class Status{
			public final static int approve = 1;//通过
			public final static int deny = 2;//拒绝
			public final static int common=0;//待审核
		}
	}
	// Fields
	@Id
	@GeneratedValue(generator = "UnCertRevokeAppGenerator")
	@GenericGenerator(name = "UnCertRevokeAppGenerator", strategy = "native")
	private Integer id;
	private Integer certId;
	private String serialnumber;
	private Integer status;
	private String reason;
	private Integer checkOperator;
	private String checkOperators;
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkdate;
	private String checkResult;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private Integer createOperator;
	private String createOperators;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "certId")
	public Integer getCertId() {
		return certId;
	}

	public void setCertId(Integer certId) {
		this.certId = certId;
	}

	@Column(name = "serialnumber")
	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "reason")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "checkOperator")
	public Integer getCheckOperator() {
		return checkOperator;
	}

	public void setCheckOperator(Integer checkOperator) {
		this.checkOperator = checkOperator;
	}

	@Column(name = "checkdate")
	public Date getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(Date checkdate) {
		this.checkdate = checkdate;
	}

	@Column(name = "checkResult")
	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
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
	
	@Column(name = "checkOperators")
	public String getCheckOperators() {
		return checkOperators;
	}

	public void setCheckOperators(String checkOperators) {
		this.checkOperators = checkOperators;
	}
	
	@Column(name = "createOperators")
	public String getCreateOperators() {
		return createOperators;
	}

	public void setCreateOperators(String createOperators) {
		this.createOperators = createOperators;
	}
	
	

	public UnCertRevokeApp() {

	}

	public UnCertRevokeApp(Integer id, Integer certId, String serialnumber,
			Integer status, String reason, Integer checkOperator,
			Date checkdate, String checkResult, Date createTime,
			Integer createOperator) {
		super();
		this.id = id;
		this.certId = certId;
		this.serialnumber = serialnumber;
		this.status = status;
		this.reason = reason;
		this.checkOperator = checkOperator;
		this.checkdate = checkdate;
		this.checkResult = checkResult;
		this.createTime = createTime;
		this.createOperator = createOperator;
	}

}
