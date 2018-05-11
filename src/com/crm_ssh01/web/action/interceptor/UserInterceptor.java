package com.crm_ssh01.web.action.interceptor;

import org.apache.struts2.ServletActionContext;

import com.crm_ssh01.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;


public class UserInterceptor extends MethodFilterInterceptor{

	
	private static final long serialVersionUID = 2001466877496239003L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		if(user == null){
			return "login";
		}
		return invocation.invoke();
	}

}
