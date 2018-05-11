package com.crm_ssh01.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.crm_ssh01.domain.User;
import com.crm_ssh01.domain.Visit;
import com.crm_ssh01.service.VisitService;
import com.crm_ssh01.utils.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 客户拜访参数持久层
 * @author Peter
 */
@Controller(value="visitAction")
@Scope(value="prototype")
public class VisitAction extends ActionSupport implements ModelDriven<Visit>{

	private static final long serialVersionUID = -3299879252788115139L;
	
	@Resource(name="visitService")
	private VisitService visitService;
	
	private Visit visit = new Visit();
	public Visit getModel() {
		// TODO Auto-generated method stub
		return visit;
	}
	
	/**
	 * 跳转到新增拜访页面
	 * @return
	 */
	public String initAddUI(){
		return "initAddUI";
	}
	
	/**
	 * 保存客户拜访记录
	 */
	public String save(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		visit.setUser(user);
		visitService.save(visit);
		return NONE;
	}
	
	/**
	 * 分页查询客户拜访记录
	 */
	private Integer pageCode = 1;
	private Integer pageSize = 5;
	public Integer getPageCode() {
		return pageCode;
	}
	public void setPageCode(Integer pageCode) {
		if(pageCode==null){
			pageCode = 1;
		}
		this.pageCode = pageCode;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		if(pageSize == null){
			pageSize = 5;
		}
		this.pageSize = pageSize;
	}
	private String beginDate;
	private String endDate;
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String findByPage(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Visit.class);
		String visit_interviewee = visit.getVisit_interviewee();
		if(visit_interviewee!=null && !visit_interviewee.trim().isEmpty()){
			criteria.add(Restrictions.like("visit_interviewee", "%"+visit_interviewee+"%"));
		}
		// 拼接查询的条件
		if(beginDate != null && !beginDate.trim().isEmpty()){
			criteria.add(Restrictions.ge("visit_time", beginDate));
		}
		// select * from xxx where visit_time >= ? and visit_time <= ?
		if(endDate != null && !endDate.trim().isEmpty()){
			criteria.add(Restrictions.le("visit_time", endDate));
		}
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		criteria.add(Restrictions.eq("user.user_id", user.getUser_id()));
		PageBean<Visit> page = visitService.findByPage(this.getPageCode(), this.getPageSize(), criteria);
		ActionContext.getContext().getValueStack().set("page", page);
		return "page";
	}
}
