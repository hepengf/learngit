package com.congoal.cert.security.interfaces.impl;

import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import com.congoal.cert.security.interfaces.IChangePassword;

/**
 * 提供基于内存的用户数据修改密码
 * 
 * @author huang
 * 
 */
public class CustomJdbcDaoImpl extends JdbcDaoImpl implements IChangePassword {

	@Override
	public void changePassword(String username, String password) {
		this.getJdbcTemplate().update(
				"UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?", username,
				password);

	}
}
