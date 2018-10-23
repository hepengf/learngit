package com.congoal.cert.security.interfaces.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.memory.InMemoryDaoImpl;

import com.congoal.cert.security.interfaces.IChangePassword;

/**
 * 提供基于内存的用户数据修改密码
 * 
 * @author huang
 * 
 */
public class InMemoryChangePasswordDaoImpl extends InMemoryDaoImpl implements
		IChangePassword {

	@Override
	public void changePassword(String username, String password) {
		User userDetails = (User) getUserMap().getUser(username);

		User newUserDetails = new User(userDetails.getUsername(), password,
				userDetails.isEnabled(), userDetails.isAccountNonExpired(),
				userDetails.isCredentialsNonExpired(),
				userDetails.isAccountNonLocked(), userDetails.getAuthorities());
		getUserMap().addUser(newUserDetails);
	}
}
