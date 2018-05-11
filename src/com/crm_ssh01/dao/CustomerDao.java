package com.crm_ssh01.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.crm_ssh01.domain.Customer;
import com.crm_ssh01.utils.PageBean;

public interface CustomerDao {

	void save(Customer customer);

	PageBean<Customer> findByPage(Integer pageCode, Integer pageSize,
			DetachedCriteria criteria);

	Customer findCustomerById(Long cust_id);

	void update(Customer customer);

	void delete(Customer customer);

	List<Customer> findAll();

}
