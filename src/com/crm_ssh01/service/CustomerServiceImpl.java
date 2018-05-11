package com.crm_ssh01.service;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.crm_ssh01.dao.CustomerDao;
import com.crm_ssh01.domain.Customer;
import com.crm_ssh01.utils.PageBean;

/**
 * 客户的业务层
 * @author Peter
 */
@Transactional
public class CustomerServiceImpl implements CustomerService {

	//set注入
	private CustomerDao customerDao;
	public void setCustomerDao(CustomerDao customerDao){
		this.customerDao = customerDao;
	}

	/**
	 * 保存客户的功能
	 */
	public void save(Customer customer) {
		customerDao.save(customer);
	}

	/**
	 * 分页查询
	 */
	public PageBean<Customer> findByPage(Integer pageCode, Integer pageSize,
			DetachedCriteria criteria) {
		return customerDao.findByPage(pageCode, pageSize, criteria);
	}

	/**
	 * 根据客户id查询客户
	 */
	public Customer findCustomerById(Long cust_id) {
		return customerDao.findCustomerById(cust_id);
	}

	/**
	 * 编辑保存客户
	 */
	public void update(Customer customer) {
		customerDao.update(customer);
	}

	/**
	 * 删除客户
	 */
	public void delete(Customer customer) {
		customerDao.delete(customer);
	}

	/**
	 * 查询所有客户
	 */
	public List<Customer> findAdd() {
		return customerDao.findAll();
	}

	
	
	
}
