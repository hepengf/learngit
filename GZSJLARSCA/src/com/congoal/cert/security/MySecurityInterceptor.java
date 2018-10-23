package com.congoal.cert.security;

/**
 * org.acegisecurity.intercept.AbstractSecurityInterceptor
AbstractSecurityInterceptor确保security interceptor得到正确的启动配置，它将同样实现的对安全对象的操作有:

从SecurityContextHolder对象中获得Authentication对象
依靠对ObjectDefinitionSource查询获得的安全对象访问来判断请求涉及的是一个受保护的对象或是一个公用的对象


 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class MySecurityInterceptor extends AbstractSecurityInterceptor implements Filter{
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	
	
	public void setSecurityMetadataSource(
			FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<MySecurityInterceptor doFilter>>>>>>>>>>>>>>>>>>>>>>>>>");
		HttpServletResponse resp=(HttpServletResponse)response;
		HttpServletRequest req=(HttpServletRequest)request;
		FilterInvocation fi = new FilterInvocation(request,response,chain);
		InterceptorStatusToken token = null;
		token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请重新登陆！");
			String proName=req.getContextPath();
			resp.sendRedirect(proName+"/login.jsp");
			//req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}finally{
			super.afterInvocation(token, null);
		}
	}
	@Override
	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void destroy() {
		
	}

	

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
