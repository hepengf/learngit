package com.congoal.cert.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.xwork.StringUtils;

@Entity
@Table(name = "S_Resource")
public class Resource {
	@Id
	@GeneratedValue
	private Integer id;
	private String type;
	private String value;
	private String name;
	private String describe;
	private Long status;
	private Date createdate;
	private String operator;
	private Integer pid;

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

	private Resource(Integer id, String type, String value, String name,
			String describe, Long status, Date createdate, String operator,
			Integer pid, Set<Role> roles) {
		super();
		this.id = id;
		this.type = type;
		this.value = value;
		this.name = name;
		this.describe = describe;
		this.status = status;
		this.createdate = createdate;
		this.operator = operator;
		this.pid = pid;
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
