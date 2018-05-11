package com.crm_ssh01.web.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.crm_ssh01.domain.Customer;
import com.crm_ssh01.domain.Dict;
import com.crm_ssh01.service.CustomerService;
import com.crm_ssh01.utils.FastJsonUtil;
import com.crm_ssh01.utils.PageBean;
import com.crm_ssh01.utils.UploadUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{

	
	private static final long serialVersionUID = 3008797353388927482L;
	//set注入
	private CustomerService customerService;	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	private Customer customer = new Customer();
	public Customer getModel() {
		return customer;
	}

	
	 /**
	  * 跳转到初始化添加客户页面
	  * @return
	  */
	public String initAddUI(){
		
		 return "initAddUI";
	}
	/**
	 * 保存客户之上传附件功能
	 */
	private File upload;//表示要上传的文件
	private String uploadFileName;//表示要上传文件的名称
	@SuppressWarnings("unused")
	private String uploadContentType;//表示上传文件的类型为MIME类型
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	/**
	 * 保存客户功能
	 */
	public String save(){
		
		//判断客户是否要上传文件
		if(uploadFileName != null){
			try {
				//处理文件名，使其全局唯一
				String uuidName = UploadUtils.getUUIDName(uploadFileName);
				//声明保存的路径
				String filePath = "D:\\program\\eclipse_kepler_project\\tomcat\\apache-tomcat-7.0.53\\webapps\\upload\\";
				//创建File对象
				File file = new File(filePath + uuidName);
				//简单方式
				FileUtils.copyFile(upload, file);
				customer.setCust_file_path(filePath+uuidName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		customerService.save(customer);
		return "save";
	}
	
	/**
	 * 分页查询之分页
	 */
	private Integer pageCode = 1;
	public Integer getPageCode(){
		return pageCode;
	}
	public void setPageCode(Integer pageCode){
		if(pageCode == null){
			pageCode = 1;
		}
		this.pageCode = pageCode;
	}
	//pageSize设置为5，表示系统默认每页显示5条
	private Integer pageSize = 5;
	public Integer getPageSize(){
		return pageSize;
	}
	public void setPageSize(Integer pageSize){
		if(pageSize == null){
			pageSize = 5;
		}
		this.pageSize = pageSize;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public String findByPage(){
		//QBC进行查询
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
		//拼接查询条件
		
		//客户姓名的查询
		String cust_name = customer.getCust_name();
		if(cust_name != null && !cust_name.trim().isEmpty()){
			criteria.add(Restrictions.like("cust_name", "%"+cust_name+"%"));
		}
		
		//客户级别
		Dict level = customer.getLevel();
		if(level!=null && !level.getDict_id().trim().isEmpty()){
			criteria.add(Restrictions.eq("level.dict_id", level.getDict_id()));
		}
		
		//客户来源
		Dict source = customer.getSource();
		if(source!=null && !source.getDict_id().isEmpty()){
			criteria.add(Restrictions.eq("source.dict_id", source.getDict_id()));
		}
		
		//客户行业
		Dict industry = customer.getIndustry();
		if(industry!=null && !industry.getDict_id().isEmpty()){
			criteria.add(Restrictions.eq("industry.dict_id", industry.getDict_id()));
		}
		
		PageBean<Customer> pageBean = customerService.findByPage(this.getPageCode(), this.getPageSize(), criteria);
		//数据进行压栈
		ActionContext.getContext().getValueStack().set("page", pageBean);
		return "page";
	}
	
	/**
	 * 修改之初始化跳转到编辑页面
	 * @return
	 */
	public String initUpdate(){
		//默认customer压栈的了，Action默认压栈，model是Action类的书写， getModel(返回customer对象)
		customer = customerService.findCustomerById(customer.getCust_id());
		return "initUpdate";
	}
	
	/**
	 * 编辑保存客户
	 * @throws IOException 
	 */
	public String update() throws IOException{
		//判断是否修改了文件,如果修改了,原来没有文件直接保存,否则删除原来文件再保存,没有修改可以直接忽略它
		if(uploadFileName!=null){
			//先删除旧的照片
			String oldFile = customer.getCust_file_path();
			if(oldFile!=null && !oldFile.trim().isEmpty()){
				File file = new File(oldFile);
				file.delete();
			}
			//上传新的图片
			String uuidName = UploadUtils.getUUIDName(uploadFileName);
			String filePath = "D:\\program\\eclipse_kepler_project\\tomcat\\apache-tomcat-7.0.53\\webapps\\upload\\";
			File file = new File(filePath + uuidName);
			FileUtils.copyFile(upload, file);
			customer.setCust_file_path(filePath + uuidName);
		}
		customerService.update(customer);
		return "update";
	}
	
	/**
	 * 根据id删除客户
	 * @return
	 */
	public String delete(){
		Customer customer2 = customerService.findCustomerById(customer.getCust_id());
		String filePath = customer2.getCust_file_path();
		customerService.delete(customer2);
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		return "delete";
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public String findAll(){
		List<Customer> lists = customerService.findAdd();
		//将Json格式转化为字符串
		String jsonStr = FastJsonUtil.toJSONString(lists);
		HttpServletResponse response = ServletActionContext.getResponse();
		//将字符串输出到前台页面
		FastJsonUtil.write_json(response, jsonStr);
		return NONE;
	}
}
