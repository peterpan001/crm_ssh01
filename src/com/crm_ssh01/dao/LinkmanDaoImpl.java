package com.crm_ssh01.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.crm_ssh01.domain.Linkman;
import com.crm_ssh01.utils.PageBean;
/**
 * 联系人的Dao层
 * @author Peter
 */
public class LinkmanDaoImpl extends HibernateDaoSupport implements LinkmanDao{

	/**
	 * 保存联系人
	 */
	public void save(Linkman linkman) {
		this.getHibernateTemplate().save(linkman);
	}

	/**
	 * 分页查询联系人
	 */
	@SuppressWarnings("unchecked")
	public PageBean<Linkman> findByPage(Integer pageCode, Integer pageSize,
			DetachedCriteria criteria) {
		PageBean<Linkman> pageBean = new PageBean<Linkman>();
		pageBean.setPageCode(pageCode);
		pageBean.setPageSize(pageSize);
		criteria.setProjection(Projections.rowCount());
		List<Number> list = (List<Number>) this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null && list.size() > 0){
			int totalCount = list.get(0).intValue();
			pageBean.setTotalCount(totalCount);

		}
		criteria.setProjection(null);
		List<Linkman> list2 = (List<Linkman>) this.getHibernateTemplate().findByCriteria(criteria,(pageCode-1)*pageSize, pageSize);
		pageBean.setBeanList(list2);
		return pageBean;
	}

	/**
	 * 通过id查询联系人
	 */
	public Linkman findLinkmanById(Long lkm_id) {
		return this.getHibernateTemplate().get(Linkman.class, lkm_id);
	}

	/**
	 * 保存修改后的联系人
	 */
	public void edit(Linkman linkman) {
		this.getHibernateTemplate().update(linkman);
	}

	/**
	 * 删除选中的联系人
	 */
	public void delete(Linkman linkman) {
		this.getHibernateTemplate().delete(linkman);
	}
}
