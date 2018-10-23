package com.congoal.cert.security.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 定义接口提供用户修改密码的方法
 * @author huang
 *
 */
public interface IChangePassword extends UserDetailsService{
	
	void changePassword(String username,String password);
}
