package com.crm_ssh01.web.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.crm_ssh01.domain.Dict;
import com.crm_ssh01.service.DictService;
import com.crm_ssh01.utils.FastJsonUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 字典表的Action
 * @author Peter
 */
public class DictAction extends ActionSupport implements ModelDriven<Dict>{

	private static final long serialVersionUID = 1168210469285127434L;
	private DictService dictService;
	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}
	private Dict dict = new Dict();
	public Dict getModel() {
		return dict;
	}
	
	/**
	 * 通过Dict_type_code找到dict_item_name
	 * @return
	 */
	public String findByCode(){
		List<Dict> lists = dictService.findByCode(dict.getDict_type_code());
		//将Json格式转换为字符串
		String jsonStr = FastJsonUtil.toJSONString(lists);
		HttpServletResponse response = ServletActionContext.getResponse();
		//输出到前台页面
		FastJsonUtil.write_json(response, jsonStr);
		return NONE;
	}
	
}
