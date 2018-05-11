package com.crm_ssh01.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.crm_ssh01.domain.User;
import com.crm_ssh01.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 用户的Action层
 * @author Peter
 */
public class UserAction extends ActionSupport implements ModelDriven<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4306814819285497216L;

	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	private User user = new User();
	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	
	public String loginout(){
		ServletActionContext.getRequest().getSession().removeAttribute("existUser");
		return LOGIN;
	}
	
	/**
	 * 用户登录
	 * @return
	 */
	public String login(){
		User existUser = userService.login(user);
		if(existUser==null){
			return LOGIN;
		}else{
			ServletActionContext.getRequest().getSession().setAttribute("existUser", existUser);
			return "loginOk";
		}
	}

	/**
	 * 用户注册
	 * @return
	 */
	public String regist(){
		//接受请求参数
		userService.regist(user);
		return LOGIN;
	}
	/**
	 * 查询用户名是否存在
	 * @return
	 */
	public String checkCode(){
		//调用业务层通过用户名查询用户
		User u = userService.checkCode(user.getUser_code());
		//获取response对象
		HttpServletResponse response = ServletActionContext.getResponse();
		//设置字符集
		response.setContentType("text/html;charset=utf-8");
		try {
			//获取输出流
			PrintWriter writer = response.getWriter();
			if(u!=null){
				//如果User不为空，说明数据库已经存在，就不可以注册
				writer.print("no");
			}else{
				//如果为空，可以注册
				writer.print("yes");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}
	
	
}
