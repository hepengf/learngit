package com.congoal.cert.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.annotations.GenericGenerator;
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
	@GeneratedValue(generator = "UserGenerator")       
    @GenericGenerator(name = "UserGenerator", strategy = "native")   
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
	private String orgid;
	private Date lastdisableddate;
	private Integer disabledcout;
	private String lastdisabledoperator;
	private String staffNo;
	
	public User(){
		
	}
	
	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getLastdisabledoperator() {
		return lastdisabledoperator;
	}

	public void setLastdisabledoperator(String lastdisabledoperator) {
		this.lastdisabledoperator = lastdisabledoperator;
	}

	public Integer getDisabledcout() {
		return disabledcout;
	}

	public void setDisabledcout(Integer disabledcout) {
		this.disabledcout = disabledcout;
	}

	public Date getLastdisableddate() {
		return lastdisableddate;
	}

	public void setLastdisableddate(Date lastdisableddate) {
		this.lastdisableddate = lastdisableddate;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

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

	public User(Integer id, String name, String password, String disabled,
			String fullname, Date createdate, String telephone, String phone,
			String zipcode, String address, String fax, String engname,
			String orgid, Date lastdisableddate, Integer disabledcout,
			String lastdisabledoperator, String staffNo, Set<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.disabled = disabled;
		this.fullname = fullname;
		this.createdate = createdate;
		this.telephone = telephone;
		this.phone = phone;
		this.zipcode = zipcode;
		this.address = address;
		this.fax = fax;
		this.engname = engname;
		this.orgid = orgid;
		this.lastdisableddate = lastdisableddate;
		this.disabledcout = disabledcout;
		this.lastdisabledoperator = lastdisabledoperator;
		this.staffNo = staffNo;
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((createdate == null) ? 0 : createdate.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result
				+ ((disabledcout == null) ? 0 : disabledcout.hashCode());
		result = prime * result + ((engname == null) ? 0 : engname.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result
				+ ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((lastdisableddate == null) ? 0 : lastdisableddate.hashCode());
		result = prime
				* result
				+ ((lastdisabledoperator == null) ? 0 : lastdisabledoperator
						.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orgid == null) ? 0 : orgid.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((staffNo == null) ? 0 : staffNo.hashCode());
		result = prime * result
				+ ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (createdate == null) {
			if (other.createdate != null)
				return false;
		} else if (!createdate.equals(other.createdate))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (disabledcout == null) {
			if (other.disabledcout != null)
				return false;
		} else if (!disabledcout.equals(other.disabledcout))
			return false;
		if (engname == null) {
			if (other.engname != null)
				return false;
		} else if (!engname.equals(other.engname))
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastdisableddate == null) {
			if (other.lastdisableddate != null)
				return false;
		} else if (!lastdisableddate.equals(other.lastdisableddate))
			return false;
		if (lastdisabledoperator == null) {
			if (other.lastdisabledoperator != null)
				return false;
		} else if (!lastdisabledoperator.equals(other.lastdisabledoperator))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orgid == null) {
			if (other.orgid != null)
				return false;
		} else if (!orgid.equals(other.orgid))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (staffNo == null) {
			if (other.staffNo != null)
				return false;
		} else if (!staffNo.equals(other.staffNo))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (zipcode == null) {
			if (other.zipcode != null)
				return false;
		} else if (!zipcode.equals(other.zipcode))
			return false;
		return true;
	}
	
	

}
