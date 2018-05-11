package com.crm_ssh01.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.crm_ssh01.service.TotalsService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller(value="totalsAction")
public class TotalsAction extends ActionSupport{


	private static final long serialVersionUID = -4905743449234454929L;
	
	@Resource(name="totalsService")
	private TotalsService totalsService;
	
	public String findSource(){
		
		List<Object[]> list = totalsService.findSource();
		ActionContext.getContext().getValueStack().set("list", list);
		return "findSource";
	}
	
	public String findVocations(){
		List<Object[]> list = totalsService.findVocations();
		ActionContext.getContext().getValueStack().set("list", list);
		return "findVocations";
	}
}
