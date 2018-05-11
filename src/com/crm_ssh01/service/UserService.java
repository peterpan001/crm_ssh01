package com.crm_ssh01.service;

import com.crm_ssh01.domain.User;

public interface UserService {

	User checkCode(String user_code);

	void regist(User user);

	User login(User user);

}
