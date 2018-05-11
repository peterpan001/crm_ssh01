package com.crm_ssh01.dao;

import com.crm_ssh01.domain.User;

public interface UserDao {

	User checkCode(String user_code);

	void regist(User user);

	User login(User user);

	User findUserById(Long user_id);

}
