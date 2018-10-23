package com.congoal.cert.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.congoal.cert.service.UserService;
import com.congoal.cert.utils.LoginUtils;
import com.congoal.cert.utils.SQL;
import com.congoal.cert.pojo.User;

/**
 * 验证用户名，密码的类
 * 
 * @author Administrator
 * 
 */
public class MyUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	public static final String UERNAME = "username";
	public static final String PASSWORD = "password";
	@Autowired
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
//		System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<MyUsernamePasswordAuthenticationFilter attemptAuthentication>>>>>>>>>>>>>>>>>>>>>>>>>");
		if (!request.getMethod().toUpperCase().equals("POST")) {  
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());  
        }  
		
		String username = obtainUsername(request).trim();
		String password = obtainPassword(request).trim();

		// 验证用户账号与登录密码是否对应
		//String hql = "from User where name='" + username + "'";
		String hql = SQL.User.findUserByName(username);
		
		List<User> userList = (List<User>) this.userService.findAll(hql);
		User user = null;
		if (!userList.isEmpty()) {
			user = userList.get(0);
		}
		
		if (user == null) {
			throw new AuthenticationServiceException("用户名不存在！");
		}
		if (!"1".equals(user.getDisabled())) {
			throw new AuthenticationServiceException("用户已停用！");
		}
		if(!LoginUtils.verificationPassword(username, password, user.getPassword()))
		//if(!user.getPassword().equals(password))
		{
			throw new AuthenticationServiceException("密码错误！");
		}

		UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(
				username, user.getPassword());
		// 设置详细信息
		setDetails(request, authenticationRequest);
		// 用户登录成功，运行MyUserDetailsServiceImpl中的loadUserByUsername方法给用户授权
		return this.getAuthenticationManager().authenticate(
				authenticationRequest);

	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(UERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}
}
