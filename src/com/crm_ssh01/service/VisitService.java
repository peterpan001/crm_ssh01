package com.crm_ssh01.service;

import org.hibernate.criterion.DetachedCriteria;

import com.crm_ssh01.domain.Visit;
import com.crm_ssh01.utils.PageBean;

public interface VisitService {

	void save(Visit visit);

	PageBean<Visit> findByPage(Integer pageCode, Integer pageSize,
			DetachedCriteria criteria);

}
