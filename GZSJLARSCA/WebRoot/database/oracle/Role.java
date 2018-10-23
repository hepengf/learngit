package com.congoal.cert.pojo;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "S_Role")
public class Role {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String description;
	private Long status;
	private Date createdate;
	private String operator;

	@ManyToMany(targetEntity = Resource.class, fetch = FetchType.EAGER)
	@JoinTable(name = "S_ROLE_RESOURCE", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
	private Set<Resource> resources;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
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

}
