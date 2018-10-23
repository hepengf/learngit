package com.congoal.cert.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 判断用户是否有角色访问对应的链接
 * 
 * @author Administrator
 * 
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
//		System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<MyAccessDecisionManager AccessDecisionManager>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		System.out.println("configAttributes===================="
				+ configAttributes);
		//configAttributes.size()==0表示已在数据库配置的资源，但未分配相应的角色
		if (configAttributes == null || configAttributes.size() == 0) {
			System.out.println("公共资源!");
			return;//return代表允许访问
		}
		for (Iterator<ConfigAttribute> iterator = configAttributes.iterator(); iterator
				.hasNext();) {
			ConfigAttribute configAttribute = iterator.next();
			String needRole = configAttribute.getAttribute();
			System.out.println("needRole: "+needRole);
			System.err.println("============need role is >> "+needRole);
			// 用户所用户的权限是否和当前权限匹配
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				System.out.println("list of Authorities: "+ga.getAuthority());
				if (needRole.equals(ga.getAuthority())) {
					return;
				}
			}
		}
		return;
	}

	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

}
