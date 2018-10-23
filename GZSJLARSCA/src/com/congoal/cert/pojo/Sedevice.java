package com.congoal.cert.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Sedevice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_sedevice")
public class Sedevice implements java.io.Serializable {

	// Fields
	@Id
	@GeneratedValue(generator = "SedeviceGenerator")       
    @GenericGenerator(name = "SedeviceGenerator", strategy = "native")   
	private Integer id;
	private String senumber;
	private String publickey;
	private Integer status;
	private Integer blackstatus;
	private Integer creation;
	private Date createtime;
	private Integer lastModifier;
	private Date lastmodifytime;
	private Integer userid;
	private String remark;
	private String cacertificates;
	private Integer type;
	private String person;

	// Constructors

	/** default constructor */
	public Sedevice() {
	}

	/** minimal constructor */
	public Sedevice(String senumber, Integer status, Date createtime) {
		this.senumber = senumber;
		this.status = status;
		this.createtime = createtime;
	}

	/** full constructor */
	public Sedevice(String senumber, String publickey, Integer status,
			Integer blackstatus, Integer creation, Date createtime,
			Integer lastModifier, Date lastmodifytime, Integer userid,
			String remark, String cacertificates, Integer type, String person) {
		this.senumber = senumber;
		this.publickey = publickey;
		this.status = status;
		this.blackstatus = blackstatus;
		this.creation = creation;
		this.createtime = createtime;
		this.lastModifier = lastModifier;
		this.lastmodifytime = lastmodifytime;
		this.userid = userid;
		this.remark = remark;
		this.cacertificates = cacertificates;
		this.type = type;
		this.person = person;
	}

	// Property accessors
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "senumber")
	public String getSenumber() {
		return this.senumber;
	}

	public void setSenumber(String senumber) {
		this.senumber = senumber;
	}
	
	@Column(name = "publickey")
	public String getPublickey() {
		return this.publickey;
	}

	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "blackstatus")
	public Integer getBlackstatus() {
		return this.blackstatus;
	}

	public void setBlackstatus(Integer blackstatus) {
		this.blackstatus = blackstatus;
	}
	
	@Column(name = "creation")
	public Integer getCreation() {
		return this.creation;
	}

	public void setCreation(Integer creation) {
		this.creation = creation;
	}
	
	@Column(name = "creation")
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "lastModifier")
	public Integer getLastModifier() {
		return this.lastModifier;
	}

	public void setLastModifier(Integer lastModifier) {
		this.lastModifier = lastModifier;
	}

	@Column(name = "lastmodifytime")
	public Date getLastmodifytime() {
		return this.lastmodifytime;
	}

	public void setLastmodifytime(Date lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}

	@Column(name = "userid")
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "cacertificates")
	public String getCacertificates() {
		return this.cacertificates;
	}

	public void setCacertificates(String cacertificates) {
		this.cacertificates = cacertificates;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "person")
	public String getPerson() {
		return this.person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

}