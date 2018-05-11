package com.crm_ssh01.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm_ssh01.dao.TotalsDao;

@Service(value="totalsService")
@Transactional
public class TotalsServiceImpl implements TotalsService {

	@Resource(name="totalsDao")
	private TotalsDao totalsDao;

	/**
	 * 查询列表
	 */
	public List<Object[]> findSource() {
		return totalsDao.findSource();
	}

	/**
	 * 查询列表
	 */
	public List<Object[]> findVocations() {
		// TODO Auto-generated method stub
		return totalsDao.findVocations();
	}
}
