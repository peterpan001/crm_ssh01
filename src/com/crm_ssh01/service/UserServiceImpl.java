package com.crm_ssh01.service;

import org.springframework.transaction.annotation.Transactional;

import com.crm_ssh01.dao.UserDao;
import com.crm_ssh01.domain.User;
import com.crm_ssh01.utils.MD5Utils;

/**
 * 用户的业务层
 * @author Peter
 */
@Transactional
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	
	/**
	 * 根据用户名查询用户
	 */
	public User checkCode(String user_code) {
		return userDao.checkCode(user_code);
	}

	/**
	 * 注册用户
	 */
	public void regist(User user) {
		//给用户的密码加密
		String password = user.getUser_password();
		user.setUser_password(MD5Utils.md5(password));
		//设置用户状态为1
		user.setUser_state("1");
		//调用持久层进行注册
		userDao.regist(user);
		
	}

	/**
	 * 用户登录
	 */
	public User login(User user) {
		//获取用户密码
		String password = user.getUser_password();
		//给密码加密
		user.setUser_password(MD5Utils.md5(password));
		//调用UserDao进行登录判断
		return userDao.login(user);
	}
	
}
