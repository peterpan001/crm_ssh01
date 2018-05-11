package com.crm_ssh01.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.crm_ssh01.domain.User;
/**
 * 持久层
 * @author Peter
 */
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	/**
	 * 通过user_code查询用户
	 */
	@SuppressWarnings("unchecked")
	public User checkCode(String user_code) {
		List<User> list = (List<User>) this.getHibernateTemplate().find("from User where user_code = ?", user_code);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 进行注册
	 */
	public void regist(User user) {

		this.getHibernateTemplate().save(user);
	}

	/**
	 * 用户登录
	 */
	@SuppressWarnings("unchecked")
	public User login(User user) {
		//QBC离线条件查询
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("user_code", user.getUser_code()));
		criteria.add(Restrictions.eq("user_password", user.getUser_password()));
		criteria.add(Restrictions.eq("user_state", "1"));
		List<User> list = (List<User>) this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过id查找用户
	 */
	public User findUserById(Long user_id) {
		return this.getHibernateTemplate().get(User.class, user_id);
	}

	
}
