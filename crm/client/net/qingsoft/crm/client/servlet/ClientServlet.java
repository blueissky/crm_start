package net.qingsoft.crm.client.servlet;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.qingsoft.crm.business.dao.BusinessInfoDao;
import net.qingsoft.crm.business.dao.BusinessInfoDaoFactory;
import net.qingsoft.crm.client.dao.ClientDaoFactory;
import net.qingsoft.crm.client.dao.CustomerInfoDao;
import net.qingsoft.crm.order.dao.OrderDaoFactory;
import net.qingsoft.crm.order.dao.OrderDaoInter;
import net.qingsoft.crm.util.BaseServlet;
import net.qingsoft.crm.util.FinalConstant;
import net.qingsoft.crm.util.SystemTime;
import net.qingsoft.crm.vo.AccountInfo;
import net.qingsoft.crm.vo.CategoryInfo;
import net.qingsoft.crm.vo.CompanyInfo;
import net.qingsoft.crm.vo.CustomerInfo;
import net.qingsoft.crm.vo.RequestInfo;
import net.qingsoft.crm.vo.SaleInfo;
import net.qingsoft.crm.vo.SalegoodsInfo;
import net.qingsoft.crm.vo.WayInfo;

public class ClientServlet extends BaseServlet {
	CustomerInfoDao cd=ClientDaoFactory.createClientDao();//客户 数据库操作实例
	OrderDaoInter oi=OrderDaoFactory.getOrderdaointer();//订单数据库
	BusinessInfoDao bi=BusinessInfoDaoFactory.getBusinessInfoDaoImp();//获得业务数据库
	SystemTime st=new SystemTime();//获得当前系统时间
	int pageSize=8;//每页显示的数量
	/**
	 * 获得客户简单资料
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getCustomerInfo(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		AccountInfo account=(AccountInfo)request.getSession().getAttribute("account");
		//获得当前用户的SESSION 
		String pageNo=request.getParameter("pageNo");
		int pageStart=0;
		if(pageNo==null){//初始化第一页
			pageStart=1;
		}
		else{
			pageStart=Integer.parseInt(pageNo);
		}
		//搜索内容存放表
		CustomerInfo customerInfo=new CustomerInfo();
		CompanyInfo companyInfo=new CompanyInfo();
		customerInfo.setCompanyInfo(companyInfo);
		String searchname=request.getParameter("searchname");//获得搜索字段名字
		String searchcompany=request.getParameter("searchcompany");//获得搜索字段公司
			if(searchcompany!=null){//判断系统传递的参数是否为空
				companyInfo.setCompany(searchcompany);
			}else{
				companyInfo.setCompany("");
			}
			if(searchname!=null){//判断系统传递的参数是否为空
				customerInfo.setName(searchname);
			}else{
				customerInfo.setName("");
			}
		/**
		 * 搜索结果分页重新获得开始--------------------------废弃
		 */
			String searchCompanyValue=request.getParameter("searchCompanyValue");///搜索页数返回参数
			if(searchCompanyValue!=null){
				byte[] bs=searchCompanyValue.getBytes("iso8859-1");
				searchCompanyValue=new String(bs,"utf-8");
				companyInfo.setCompany(searchCompanyValue);
			}
			String searchNameValue=request.getParameter("searchNameValue");///搜索页数返回参数
			if(searchNameValue!=null){
				byte[] bs=searchNameValue.getBytes("iso8859-1");
				searchNameValue=new String(bs,"utf-8");
				customerInfo.setName(searchNameValue);
			}
		/**
		 * 搜索结果分页重新获得结束
		 * */
		ArrayList<CustomerInfo> list=cd.getCustomerMessage(pageStart,pageSize,account,customerInfo);//获得所有客户资料list
		request.setAttribute("list",list);//将list带回网页 
	}
	/**
	 * 获得页数
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getPageCount(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		int count=0;//分页显示客户总数
		AccountInfo account=(AccountInfo)request.getSession().getAttribute("account");//获得当前用户的SESSION 
		//搜索内容存放表//////////////////
		CustomerInfo customerInfo=new CustomerInfo();
		CompanyInfo companyInfo=new CompanyInfo();
		customerInfo.setCompanyInfo(companyInfo);
		String searchname=request.getParameter("searchname");
		String searchcompany=request.getParameter("searchcompany");
		if(searchcompany!=null){//判断系统传递的参数是否为空
			companyInfo.setCompany(searchcompany);
		}else{
			companyInfo.setCompany("");
		}
		if(searchname!=null){//判断系统传递的参数是否为空
			customerInfo.setName(searchname);
		}else{
			customerInfo.setName("");
		}
		if(searchname==null&&searchcompany==null){//如果搜索框内容为空时执行
			count=cd.getCount(account);//分页显示客户总数
		}else{//内容不为空时执行
			count=cd.getSearchCount(customerInfo);//只显示搜索的数量
		}
		count=(count+pageSize-1)/pageSize;//分页显示
		request.setAttribute("count",count);//将数量存入account中
	}
	/**
	 * 存储用户提交的表单数据
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void saveMessage(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		AccountInfo accountInfo=(AccountInfo)request.getSession().getAttribute("account");
		//获得当前用户表
		CompanyInfo companyInfo=new CompanyInfo();//新建公司表
		WayInfo wayInfo=new WayInfo();//客户来源表
		RequestInfo requestInfo=new RequestInfo();//客户需求表
		CustomerInfo customerInfo=new CustomerInfo();//客户信息表
		String age=null;//customer表age参数
		String strAge=request.getParameter("userage");//获得用户提交的年龄
		if(strAge!=""){//如果页面不是空那么计算年龄对应的年份""
			try {
				int tempAge=Integer.parseInt(strAge);
				tempAge=st.getYear()-22;//系统年龄
				age=tempAge+"";//将年龄转换为字符串
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String sex="";
		if(request.getParameter("usersex")!=null){
			sex=request.getParameter("usersex");
			if(sex.equals("male")){
				sex="男";
			}
			else{
				sex="女";
			}
		}
		/**
		 * 客户信息表
		 */
		customerInfo.setName(request.getParameter("customername"));//姓名
		customerInfo.setAge(age);//年龄
		customerInfo.setSex(sex);//性别
		customerInfo.setTel(request.getParameter("tel"));//电话
		customerInfo.setEmail(request.getParameter("email"));//邮件
		customerInfo.setQq(request.getParameter("qq"));//QQ
		customerInfo.setAccountInfo(accountInfo);//AccountID外键表
		customerInfo.setCompanyInfo(companyInfo);//CompanyID外键表
		customerInfo.setFirstTime(st.getSystemTime());//当前时间
		customerInfo.setWayInfo(wayInfo);//WayID外键表
		/**
		 * 公司表
		 */
		companyInfo.setCompany(request.getParameter("company"));//公司名称
		companyInfo.setRange(request.getParameter("tradearea"));//经营范围
		companyInfo.setAddress(request.getParameter("address"));//地址
		/**
		 * 来源表
		 */
		wayInfo.setMethod(request.getParameter("source"));//客户来源方式
		/**
		 * 对话表 
		 */
		requestInfo.setAccountInfo(accountInfo);//业务员
		requestInfo.setCustomerInfo(customerInfo);//谈论客户
		requestInfo.setCont(request.getParameter("textarea").trim());//谈论内容
		requestInfo.setMethod(request.getParameter("way"));//谈论地点
		requestInfo.setConndate(st.getSystemTime());//谈论时间
		cd.saveMessage(customerInfo, requestInfo);//调用数据存储
		this.forward("/client/client.jsp", request, response);
	}
	public void delClient(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		CustomerInfo customerInfo=new CustomerInfo();
		String customerId=request.getParameter("customerId");//获得当前用户的ID
		customerInfo.setId(Integer.parseInt(customerId));
		int num=cd.delCustomerInfo(customerInfo);
		PrintWriter pw=response.getWriter();//获得输出流
		if(num!=0){//成功返回给浏览器true失败返回false
			pw.write("true");
		}else{
			pw.write("false");
		}
	}
	/**
	 * 公共访问区business,order
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void businessOrder(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		int customerId=Integer.parseInt(request.getParameter("customerId"));//页面参数用户ID 
		AccountInfo accountInfo =(AccountInfo)request.getSession().getAttribute("account");//获取当前用户
		CustomerInfo customerInfo=new CustomerInfo();//创建用户表
		customerInfo.setAccountInfo(accountInfo);
		customerInfo.setId(customerId);
		
		
		//将客户的信息放入sale里面----------dxx
	       SaleInfo saleInfo=new SaleInfo();
	      saleInfo=oi.getSale(customerInfo);
	       //将客户的信息放入sale里面----------dxx 
	  
	      
		////////////////////////////////////////////////////////////////////////////////////
		CustomerInfo customerList=cd.getCustomerCompany(customerInfo);//获得指定用户信息
		ArrayList<RequestInfo> requesList=bi.getRequestInfo(customerInfo);//获得指定用户业务信息
		ArrayList<CategoryInfo> categoryList=oi.getType();//获得商品类型
		ArrayList<SalegoodsInfo> saleList=oi.getSaleGoods(customerInfo);//获得指定用户的订单信息
		
		request.setAttribute("customerInfo",customerInfo );//返回JSP用户ID和accountID
		request.setAttribute("customerList",customerList);//返回JSP客户信息
		request.setAttribute("requesList",requesList);//返回JSP业务信息
		request.setAttribute("categoryList",categoryList);//返回JSP商品类型
		request.setAttribute("saleList",saleList);//返回JSP订单信息
		request.setAttribute("saleinfo", saleInfo);//返回sale的信息 对应客户id的订单编号 saleid
		this.forward("/client/businessOrder.jsp", request, response);
	}
}

