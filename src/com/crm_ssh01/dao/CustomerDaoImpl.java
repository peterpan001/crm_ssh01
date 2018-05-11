package com.crm_ssh01.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.crm_ssh01.domain.Customer;
import com.crm_ssh01.utils.PageBean;
/**
 * 客户的持久层
 * @author Peter
 */
public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

	/**
	 * 客户的持久层
	 */
	public void save(Customer customer) {
		this.getHibernateTemplate().save(customer);
	}

	/**
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	public PageBean<Customer> findByPage(Integer pageCode, Integer pageSize,
			DetachedCriteria criteria) {
		//封装pageBean属性
		PageBean<Customer> pageBean = new PageBean<Customer>();
		pageBean.setPageCode(pageCode);
		pageBean.setPageSize(pageSize);
		//查询总的记录数
		criteria.setProjection(Projections.rowCount());
		List<Number> list = (List<Number>) this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null && list.size() > 0){
			Number number = list.get(0);
			//总的记录数
			pageBean.setTotalCount(number.intValue());
		}
		//查询所有记录,先把上面的条件清空，设置为null
		criteria.setProjection(null);
		//进行分页查询
		List<Customer> list2 = (List<Customer>) this.getHibernateTemplate().findByCriteria(criteria, (pageCode - 1)*pageSize, pageSize);
		//将结果封装到PageBean中
		pageBean.setBeanList(list2);
		return pageBean;
	}

	/**
	 * 根据客户id查询客户
	 */
	public Customer findCustomerById(Long cust_id) {
		return this.getHibernateTemplate().get(Customer.class, cust_id);
	}

	/**
	 * 编辑保存客户
	 */
	public void update(Customer customer) {
		this.getHibernateTemplate().update(customer);
	}

	/**
	 * 删除客户
	 */
	public void delete(Customer customer) {
		this.getHibernateTemplate().delete(customer);
	}

	/**
	 * 查询所有客户
	 */
	@SuppressWarnings("unchecked")
	public List<Customer> findAll() {
		List<Customer> list = (List<Customer>) this.getHibernateTemplate().find("from Customer");
		return list;
	}

	

	
	
}
