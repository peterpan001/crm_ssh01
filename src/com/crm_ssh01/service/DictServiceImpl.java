package com.crm_ssh01.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.crm_ssh01.dao.DictDao;
import com.crm_ssh01.domain.Dict;

/**
 * Dict的Service
 * @author Peter
 */
@Transactional
public class DictServiceImpl implements DictService {

	private DictDao dictDao;
	public void setDictDao(DictDao dictDao) {
		this.dictDao = dictDao;
	}
	
	/**
	 * 通过dict_type_code找到dict_item_name
	 */
	public List<Dict> findByCode(String dict_type_code) {
		return dictDao.findByCode(dict_type_code);
	}
	
}
