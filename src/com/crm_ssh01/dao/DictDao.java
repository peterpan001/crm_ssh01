package com.crm_ssh01.dao;

import java.util.List;

import com.crm_ssh01.domain.Dict;

public interface DictDao {

	List<Dict> findByCode(String dict_type_code);

}
