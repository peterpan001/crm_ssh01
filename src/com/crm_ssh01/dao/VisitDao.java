package com.crm_ssh01.dao;

import org.hibernate.criterion.DetachedCriteria;

import com.crm_ssh01.domain.Visit;
import com.crm_ssh01.utils.PageBean;

public interface VisitDao{

	void save(Visit visit);

	PageBean<Visit> findByPage(Integer pageCode, Integer pageSize,
			DetachedCriteria criteria);
	
}
