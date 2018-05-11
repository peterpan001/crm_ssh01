package com.crm_ssh01.web.action;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.crm_ssh01.domain.Customer;
import com.crm_ssh01.domain.Linkman;
import com.crm_ssh01.service.LinkmanService;
import com.crm_ssh01.utils.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 联系人Web层
 * @author Peter
 */
public class LinkmanAction extends ActionSupport implements ModelDriven<Linkman>{

	private static final long serialVersionUID = 8372161920035493926L;
	private LinkmanService linkmanService;
	public void setLinkmanService(LinkmanService linkmanService) {
		this.linkmanService = linkmanService;
	}
	private Linkman linkman = new Linkman();
	public Linkman getModel() {
		return linkman;
	}

	/**
	 * 初始化跳转到增加联系人页面
	 * @return
	 */
	public String initAddUI(){
		return "initAddUI";
	}
	
	/**
	 * 保存联系人
	 */
	public String save(){
		linkmanService.save(linkman);
		return "save";
	}
	
	/**
	 * 分页查询联系人列表
	 */
	private Integer pageCode = 1;
	private Integer pageSize = 5;
	public void setPageCode(Integer pageCode){
		if(pageCode == null){
			pageCode = 1;
		}
		this.pageCode = pageCode;
	}
	public Integer getPageCode(){
		return pageCode;
	}
	public void setPageSize(Integer pageSize){
		if(pageSize == null){
			pageSize = 5;
		}
		this.pageSize = pageSize;
	}
	public Integer getPageSize(){
		return pageSize;
	}
	
	public String findByPage(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Linkman.class);
		//封装查询条件
		String lkm_name = linkman.getLkm_name();
		if(lkm_name!=null && !lkm_name.trim().isEmpty()){
			criteria.add(Restrictions.like("lkm_name", "%"+lkm_name+"%"));
		}
		Customer c1 = linkman.getCustomer();
		if(c1!=null && c1.getCust_id()!=null){
			criteria.add(Restrictions.eq("customer.cust_id", c1.getCust_id()));
		}
		PageBean<Linkman> pageBean = linkmanService.findByPage(this.getPageCode(), this.getPageSize(), criteria);
		//把数据进行压栈
		ActionContext.getContext().getValueStack().set("page", pageBean);
		return "page";
	}
	
	//初始化到修改页面,通过id查询联系人
	public String initUpdate(){
		linkman = linkmanService.findLinkmanById(linkman.getLkm_id());
		return "initUpdate";
	}
	
	//保存修改的页面
	public String edit(){
		linkmanService.edit(linkman);
		return "edit";
	}
	
	//删除联系人
	public String delete(){
		linkmanService.delete(linkman);
		return "delete";
	}
}
