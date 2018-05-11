package com.crm_ssh01.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.crm_ssh01.domain.Visit;
import com.crm_ssh01.utils.PageBean;

@Repository(value="visitDao")
public class VisitDaoImpl extends HibernateDaoSupport implements VisitDao {
	
	@Resource(name="sessionFactory")
	public void set2SessionFactory(SessionFactory sessionFactory){
		//关键，调用父类的方法
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 保存客户拜访记录
	 */
	public void save(Visit visit) {
		this.getHibernateTemplate().save(visit);
	}

	/**
	 * 按条件分页查询拜访客户
	 */
	public PageBean<Visit> findByPage(Integer pageCode, Integer pageSize,
			DetachedCriteria criteria) {
		PageBean<Visit> pageBean = new PageBean<Visit>();
		pageBean.setPageSize(pageSize);
		pageBean.setPageCode(pageCode);
		
		criteria.setProjection(Projections.rowCount());
		List<Number> list = (List<Number>) this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null && list.size() > 0){
			int totalCount = list.get(0).intValue();
			pageBean.setTotalCount(totalCount);
		}
		
		criteria.setProjection(null);
		List<Visit> list2 = (List<Visit>) this.getHibernateTemplate().findByCriteria(criteria);
		pageBean.setBeanList(list2);
		
		return pageBean;
	}
}
