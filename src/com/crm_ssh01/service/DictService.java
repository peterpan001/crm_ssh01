package com.crm_ssh01.service;

import java.util.List;

import com.crm_ssh01.domain.Dict;

public interface DictService {

	List<Dict> findByCode(String dict_type_code);

}
