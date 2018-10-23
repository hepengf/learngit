package com.congoal.cert.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "S_USER")
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String password;
	private String disabled;
	private String fullname;
	private Date createdate;
	private String telephone;
	private String phone;
	private String zipcode;
	private String address;
	private String fax;
	private String engname;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
	@JoinTable(name = "S_USER_ROLE", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String isDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEngname() {
		return engname;
	}

	public void setEngname(String engname) {
		this.engname = engname;
	}

	public String getDisabled() {
		return disabled;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(
				roles.size());
		for (Role role : roles) {
			grantedAuthorities.add(new GrantedAuthorityImpl(role.getName()));
		}
		return grantedAuthorities;
	}

	public String getRolesString() {
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority authority : this.getAuthorities()) {
			roles.add(authority.getAuthority());
		}
		return StringUtils.join(roles, ",");
	}

	public String getUsername() {
		return this.name;
	}

	/**
	 * 
	 * String getUsername()：获取用户名；
	 * 
	 * 　　String getPassword()：获取密码；
	 * 
	 * 　　boolean isAccountNonExpired()：用户帐号是否过期；
	 * 
	 * 　　boolean isAccountNonLocked()：用户帐号是否锁定；
	 * 
	 * 　　boolean isCredentialsNonExpired()：用户的凭证是否过期；
	 * 
	 * 　　boolean isEnabled()：用户是否处于激活状态。
	 */
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		if ("1".equals(disabled)) {
			return true;
		} else {
			return false;
		}
	}

}
