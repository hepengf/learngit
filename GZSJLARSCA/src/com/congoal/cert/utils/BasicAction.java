package com.congoal.cert.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.congoal.cert.pojo.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings({ "serial", "rawtypes" })
public class BasicAction extends ActionSupport {

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected Map getSession() {
		return ActionContext.getContext().getSession();
	}

	protected ActionContext getContext() {
		return ActionContext.getContext();
	}

	protected User getUser() {
		return (User) this.getRequest().getSession().getAttribute("user");
	}

}
