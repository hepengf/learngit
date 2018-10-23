package com.congoal.cert.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("all")
@Entity
@Table(name = "S_Resource")
public class Resource implements Serializable{
	@Id
	@GeneratedValue(generator = "ResourceGenerator")       
    @GenericGenerator(name = "ResourceGenerator", strategy = "native")   
	private Integer id;
	private String type;
	private String value;
	private String name;
	private String descval;
	private Long status;
	private Date createdate;
	private String operator;
	private Integer pid;
	private String imgUrl;
	private Integer ordernum;
	
	
	@Column(name = "ordernum")
	public Integer getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "imgUrl")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Column(name = "pid")
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@ManyToMany(mappedBy = "resources", targetEntity = Role.class, fetch = FetchType.EAGER)
	private Set<Role> roles;

	public Resource() {
	}

	public Resource(Integer id, String type, String value, String name,
			String descval, Long status, Date createdate, String operator,
			Integer pid,String imgUrl,Integer ordernum, Set<Role> roles) {
		super();
		this.id = id;
		this.type = type;
		this.value = value;
		this.name = name;
		this.descval = descval;
		this.status = status;
		this.createdate = createdate;
		this.operator = operator;
		this.pid = pid;
		this.roles = roles;
		this.imgUrl = imgUrl;
		this.ordernum = ordernum;
	}
	@Column(name = "name") 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "descval")
	public String getDescval() {
		return descval;
	}

	public void setDescval(String descval) {
		this.descval = descval;
	}
	@Column(name = "status")
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	@Column(name = "createdate")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	@Column(name = "operator")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	// 返回逗号分隔形式的资源的所有角色
	@Transient
	public String getRoleString() {
		List<String> roleList = new ArrayList<String>();
		for (Role role : roles) {
			roleList.add(role.getName());
		}
		return StringUtils.join(roleList, ",");
	}

}
