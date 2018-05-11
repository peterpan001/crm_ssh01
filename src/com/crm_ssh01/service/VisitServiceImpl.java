package com.crm_ssh01.service;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm_ssh01.dao.CustomerDao;
import com.crm_ssh01.dao.UserDao;
import com.crm_ssh01.dao.VisitDao;
import com.crm_ssh01.domain.Visit;
import com.crm_ssh01.utils.PageBean;

/**
 * 客户拜访的业务层
 * @author Peter
 */
@Service(value="visitService")
@Transactional
public class VisitServiceImpl implements VisitService {

	@Resource(name="visitDao")
	private VisitDao visitDao;
	
	@Resource(name="customerDao")
	private CustomerDao customerDao;
	
	@Resource(name="userDao")
	private UserDao userDao;

	/**
	 * 保存客户拜访记录
	 */
	public void save(Visit visit) {
		Long cust_id = visit.getCustomer().getCust_id();
		visit.setCustomer(customerDao.findCustomerById(cust_id));
		visitDao.save(visit);
	}

	/**
	 * 按条件分页查询客户记录
	 */
	public PageBean<Visit> findByPage(Integer pageCode, Integer pageSize,
			DetachedCriteria criteria) {
		
		return visitDao.findByPage(pageCode, pageSize, criteria);
	}
	
	
	
}
