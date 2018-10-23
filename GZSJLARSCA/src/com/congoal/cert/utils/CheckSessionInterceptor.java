package com.congoal.cert.utils;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.congoal.cert.action.LoginAction;
import com.congoal.cert.pojo.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckSessionInterceptor extends AbstractInterceptor {

    private static Log logger = LogFactory.getLog(CheckSessionInterceptor.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -8899616218427088204L;
	
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception 
	{
		Map<String,Object> session = actionInvocation.getInvocationContext().getSession();
		
		User user = (User)session.get(LoginAction.SESSION_ATTRIBUTE);
		
		//检查Session,判断用户是否登录
		if(null == user)
		{
			return "timeout";
		}
		
		
		//int userCount = (Integer)userDetailMap.get(UserDetailMap.USERCOUNT);
		//System.out.println("当前用户："+user.getName()+",【会话数】："+userCount);
		//logger.debug("当前用户："+user.getName()+",【角色】："+user.getRolesString());
		
		return actionInvocation.invoke();
	}
}
