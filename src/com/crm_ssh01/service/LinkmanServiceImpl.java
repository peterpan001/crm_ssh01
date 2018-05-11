package com.crm_ssh01.service;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.crm_ssh01.dao.CustomerDao;
import com.crm_ssh01.dao.LinkmanDao;
import com.crm_ssh01.domain.Customer;
import com.crm_ssh01.domain.Linkman;
import com.crm_ssh01.utils.PageBean;
/**
 * 联系人Service层
 * @author Peter
 */
@Transactional
public class LinkmanServiceImpl implements LinkmanService {

	private LinkmanDao linkmanDao;
	public void setLinkmanDao(LinkmanDao linkmanDao) {
		this.linkmanDao = linkmanDao;
	}
	
	private CustomerDao customerDao;
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}


	/**
	 * 保存联系人
	 */
	public void save(Linkman linkman) {
		Customer customer = customerDao.findCustomerById(linkman.getCustomer().getCust_id());
		linkman.setCustomer(customer);
		linkmanDao.save(linkman);
	}


	/**
	 * 分页查询联系人
	 */
	public PageBean<Linkman> findByPage(Integer pageCode, Integer pageSize,
			DetachedCriteria criteria) {
		return linkmanDao.findByPage(pageCode, pageSize, criteria);
	}


	/**
	 * 通过id查找联系人
	 */
	public Linkman findLinkmanById(Long lkm_id) {
		return linkmanDao.findLinkmanById(lkm_id);
	}


	/**
	 * 保存修改的联系人信息
	 */
	public void edit(Linkman linkman) {
		Customer customer = customerDao.findCustomerById(linkman.getCustomer().getCust_id());
		linkman.setCustomer(customer);
		linkmanDao.edit(linkman);
	}


	/**
	 * 删除选中的联系人
	 */
	public void delete(Linkman linkman) {
		linkmanDao.delete(linkman);
	}
	
}
