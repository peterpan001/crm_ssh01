----------------------------CRM-SSH框架整合------（Struts+Spring+Hibernate）-------------------------------------------
1：CRM整合前奏
	1:创建数据库，crm_ssh;
	2:创建表，base_dict,cst_customer,cst_linkman,sale_visit,sys_user;
	3:创建包结构，com.crm_ssh01.web.action,com.crm_ssh01.service,com.crm_ssh01.dao,com.crm_ssh01.domain;
	          com.crm_ssh01.web.action.interceptor
	4:在com.crm_ssh01.domain包下创建数据库表对应的持久化类,基本类型数据都用包装类型
	5:导入UI界面;
2：CRM整合前戏
	1:导入Jar包;
	2:创建config资源包，开始配置配置文件;
		WEB层：(Action层，Struts框架)
			struts.xml  config目录下创建struts的核心配置文件,引入头文件
			web.xml     Struts的前端控制器，核心过滤器
		业务层：（Service层，Spring框架）
			applicationContext.xml   config目录下创建spring的核心配置文件,引入头文件
									   加载hibernate.cfg.xml核心配置文件,配置事务管理器平台,开启事物注解
			web.xml                  配置Spring的监听器，加载Spring的核心配置文件
									   配置session在web层开启,在web层关闭
			log4j.properties         config目录下创建日志的配置文件
		持久层：（Dao层，Hibernate框架）
			hibernate.cfg.xml        config目录下创建hibernate的核心配置文件：引入头文件,
									   配置数据库的4大参数,配置数据库方言,配置可选参数，配置C3P0连接池,加载资源映射文件
			Xxx.hbm.xml              在domain目录下,创建与持久化类对应的资源映射文件
								             在配置客户的资源映射文件时,来源,行业,级别这三个单方面的配成一对多的关系
								             在配置客户联系人时,一方客户用set去配,多方联系人用many-to-one去配置
								             主外键的维护关系交给多方去维护
---------------------CRM整合中戏配置文件（以用户注册登录为例）-----------------------------------------------------------------
1：在web层创建UserAction,继承ActionSupport,实现ModelDriven<User>,set注入userService;
2：在Service层创建UserService接口,UserServiceImpl实现类,在实现类中set注入customerDao,开启@Transactional注解
3：在dao层创建CustomerDao接口,CustomerDaoImpl实现类继承HibernateDaoSupport;
4：在struts.xml配置文件中配置用户的action,但是创建UserAction交由Spring管理,所以action中的class写spring创建Action的id;
5：在applicationContext.xml的配置文件中配置Action,配置Service,配置Dao;
---------------------CRM整合高潮（实现用户注册、登录和退出）-------------------------------------------------------------------
用户注册功能：需要利用ajax异步进行用户名的验证，看数据库中是否有这个用户名吗，它利用了鼠标失去焦点事件去触发用户名的检查,为了确认在提示用户在注册时如果有不能提交，
		    使用开了表单验证事件onsubmit="return checkForm()",返回false就不能提交;
用户登录功能：用户登录在验证成功后，需要把用户放到session缓存中，方便下面实现的用户是否登录的过滤功能。
用户退出功能：清空session里面的缓存，将页面跳转到登录页面。

1：用户注册功能.首先先要用Ajax异步判段注册名字是否相同，如果相同必须换用户名字，异步判断设置url为user_checkCode.action,提交给action接收。
2：在UserAction编写checkCode去实现判断用户名是否相同,调用UserServiceImpl,业务层再调用持久层UserDaoImpl,去数据库中查询是否有
      相同的名字。有在action返回no字符串告诉不可以用这个用户名注册,否则可以用这个用户名注册。
3：注册实现,在页面form表单的action提交动作出改为user_regist.action,然后在UserAction中去接收用户名密码等信息,调用Service的方法,在业务层要设置
      密码加密,设置用户状态等,调用持久层把数据插入到数据库.
4：在UserAction中做跳转,跳转到登录页面.
5：用户注册完成,进行测试调试.
6：用户登录功能.首先查看login.jsp的用户名和密码字段是否和持久化类的字段名称是否一致,不一致的话修改一致,然后修改form表单的action.
7：在Action编写用户登录的方法login(),调用业务层的登录方法,业务层将密码加密,用户状态封装到User对象中,再调用持久层的login(user)方法,
      进行有条件的查询,查询到返回User对象,否则返回null,在Action判断如果为空,跳转到登录页面,否则,将用户封装到Session中,跳转到主页.
8：用户退出功能。修改用户退出超链接为user_loginout.action.
9：在Action中编写loginout(),获取session,从session中移除存在的用户,返回登录页面.


-------------------------CRM整合中戏（以客户模块为例）---------------------------------------------------------------------
1：在Web层创建CustomerAction,继承ActionSupport,实现ModelDriven<Customer>,set注入customerService;
2：在Service层创建CustomerService接口,CustomerServiceImpl实现,在实现类中set注入customerDao,开启@Transactional注解
3：在dao层创建CustomerDao接口,CustomerDaoImpl实现继承HibernateDaoSupport；
4：在struts.xml配置文件中配置客户的action,但是创建客户CustomerAction交由Spring管理,所以action中的class写spring创建action的id;
5：在applicationContext.xml的配置文件中配置Action（多例）,配置Service,配置Dao;
----------------------------------CRM整合高潮（实现客户模块的功能）----------------------------------------------------------
客户添加功能：首先要在Action中做一次跳转，跳转到添加页面；然后实现客户的保存，其中客户来源,客户级别,客户行业是异步请求，在加载时利用Ajax的异步请求，形成下拉框列
		     表。其中客户添加中有一个特色，就是上传附件的功能。其中在form中要添加一个enctype="multipart/form-data",
		  <input type="file" name="upload"/>在action中声明upload,uploadFileName,uploadContentType;并提供set和get方法,
		      最后实现客户的保存。其中在做文件上传时，有一个注意点在struts.xml中配置文件上传的大小，以及文件上传的类型。
客户带条件的分页查询功能：先做列表显示和分页，然后再做搜索功能。首先要利用PageBean的Utils工具类;声明PageBean<T>泛型,然后设置pageCode,pageSize,pageBeanList等等,利用QBC查询,实现
				     客户分页查询的功能，然后再做搜索框，客户姓名，客户资源，行业，级别的搜索框，实现搜索功能。其中name属性用的是source.dict_id,
				  level.dict_id,industry.dict_id;
客户修改功能：其中要利用id查询到对应的客户,再修改客户信息,其中关键一点是文件修改(没有的话就添加;有的话要先删除,再添加)
客户删除功能：根据id查询到客户删除客户，并删除客户对应的文件。其中关联关系在配置文件中已经配置，这里不用考虑。

1：实现添加客户的功能。
注：
	1:在创建持久化类的时候，因为用到了字典表，而客户中的客户来源，客户行业，客户级别是从字典表得来的，故这三个字段属性为Dict对象，名称为source，industry，
	  level。
	2:在创建持久化类的时候，Customer客户类和Linkman联系人类有一对多的关系，故客户表需要声明Set<Linkman> linkmans = new HashSet<Linkman>();
	      联系人表需要声明Customer customer属性；并且提供他们的getter和setter方法；
	3:在开始先跳转到初始化的添加页面，只在action中操作，就仅仅是跳转到初始化页，然后再写ajax的异步请求，实现客户来源，行业，级别的下拉框。然后再写action，
	  service，dao； 实现客户的添加。
	      

---------------------------------------CRM整合高潮（实现客户模块的功能）-----------------------------------------------------
联系人模块实现和客户模块实现类似；
联系人添加功能：首先在Action中做一次跳转，跳转到添加页面，然后实现联系人的保存；其中的所属客户是一个下拉框列表，也是用Ajax实现。其他就正常的操作保存联系人。
联系人分页查询的功能：先做列表显示和分页功能，然后再做搜索功能。在前台页面修改好list.jsp;然后在Action中声明PageBean<T>泛型,然后设置pageCode,
               pageSize,beanList,totalCount等等，利用分页查询的模板进行查询。
联系人修改功能：要利用id进行联系人的查询，查询到对应的联系人显示在联系人修改页面，然后在做修改，传到action进行修改操作。
联系人删除功能：要利用id查询到联系人对象然后再进行联系人的删除操作。

--------------------------------------CRM整合尾声（实现拦截器进行拦截）-----------------------------------------------------
创建UserInterceptor类，继承MethodFilterInterceptor父类，获取session中的existUser的值，判断如果为空返回到登录页面，否则调用invoke()方法，
执行下一个拦截器器。在struts.xml的配置文件需要对字典表模块，客户模块，联系人模块配置这个拦截器，并且还要继承defaultStack的默认拦截器。在用户登录模块，也要
配置这个拦截器，但是要配置一个标签，在login,regist,checkCode方法不能进行拦截。

--------------------------------------CRM整合心声-------------------------------------------------------------------
CRM权限管理系统SSH整合中最重要的点就是配置文件的配置。struts.xml，applicationContext.xml，hibernate.cfg.xml，以及Xxx.hbm.xml配置文件

在用户登录模块要用到拦截器的功能，对于后面的其他模块都要拦截判断用户是否登录，登录继续执行，否则跳转到登录页面进行登录，当然了还要继承默认的拦截器栈，不然默认的
拦截器栈不执行了。不拦截的方法有三个登录，注册，注册时用户名检查是否已存在，这个方法要用到Ajax异步操作。在用户名文本域失去焦点时就要传给后台进行操作验证。

在客户模块重点的一个是要用到了一个字典表，里面存放了一些信息与客户表进行对应，比如：客户来源，客户行业，客户级别信息，然后在配置文件中进行配置，由客户去维护这个
配置。然后就是客户中的一个特色客户保存时有一个文件上传保存功能，其保存时保存在tomcat服务器的webapp目录下。修改删除时不要忘记了对文件的操作。搜索时有一个特色
就是异步查询数据库显示三个下拉列表。

在联系人模块中它和客户模块功能类似，没有什么特别突出的新点。




		

