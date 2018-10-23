package com.congoal.cert.action;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import com.congoal.cert.utils.BasicAction;
@SuppressWarnings("all" )
public class SpringSecurityErrorAction extends BasicAction {

	public String accessDenied() {
		HttpServletRequest request = this.getRequest();
		AccessDeniedException ex = (AccessDeniedException) request
				.getAttribute(AccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY);
		StringWriter sw = new StringWriter();

		request.setAttribute("errorDetails", ex.getMessage());
		ex.printStackTrace(new PrintWriter(sw));
		request.setAttribute("errorTrace", sw.toString());

		return SUCCESS;
	}
}
