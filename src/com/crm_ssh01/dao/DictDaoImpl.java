package com.crm_ssh01.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.crm_ssh01.domain.Dict;
/**
 * Dict的Dao
 * @author Peter
 */
public class DictDaoImpl extends HibernateDaoSupport implements DictDao {

	/**
	 * 通过dict_type_code找到dict_item_name
	 */
	@SuppressWarnings("unchecked")
	public List<Dict> findByCode(String dict_type_code) {
		List<Dict> lists = (List<Dict>) this.getHibernateTemplate().find("from Dict where dict_type_code = ? ", dict_type_code);
		return lists;
	}

}
