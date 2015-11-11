package net.qingsoft.crm.order.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.qingsoft.crm.order.dao.OrderDaoFactory;
import net.qingsoft.crm.order.dao.OrderDaoInter;
import net.qingsoft.crm.util.BaseServlet;
import net.qingsoft.crm.util.SystemTime;
import net.qingsoft.crm.vo.AccountInfo;
import net.qingsoft.crm.vo.CategoryInfo;
import net.qingsoft.crm.vo.CustomerInfo;
import net.qingsoft.crm.vo.SaleInfo;
import net.qingsoft.crm.vo.SalegoodsInfo;

public class OrderServlet extends BaseServlet{
	private 	int pgSize = 8;
	private  OrderDaoInter orderDao=OrderDaoFactory.getOrderdaointer();
	/**
	 * 获得前台提交的表单内容
	 * 让dao执行数据插入
	 */
	public void addsale(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException
			{   
		
		       ///新建对应订单的信息类
		        SaleInfo saleInfo=new SaleInfo();//存放订单资料
		        CustomerInfo customerInfo=new CustomerInfo();//存放客户资料	
		        CategoryInfo categoryInfo=new CategoryInfo();//存放商品资料
		        AccountInfo accountInfo=new AccountInfo();//存放用户id信息	
		        SalegoodsInfo salegoodsInfo=new SalegoodsInfo();//存放订单信息
		        SystemTime time=new SystemTime();//获取时间用于生成订单号
		        
		          //把用户提交的信息放入用户信息表里 传递到dao层里
		         customerInfo.setId(Integer.parseInt(request.getParameter("hiddenCustId")));//获取 客户 id
		         saleInfo.setCustomerInfo(customerInfo); //客户 id 放入saleinfo表里	
		         salegoodsInfo.setSaleInfo(saleInfo);
		         
 			     salegoodsInfo.setSaleInfo(saleInfo);
 			     categoryInfo.setId(Integer.parseInt(request.getParameter("brand")));//获取商品 id
			     salegoodsInfo.setCategoryInfo(categoryInfo);
			     salegoodsInfo.setAmount(Integer.parseInt(request.getParameter("amount")));//获取输入的数量
		
			     salegoodsInfo.setPrice(Integer.parseInt(request.getParameter("price"))); //获取输入的价格
			     salegoodsInfo.setSaledate(request.getParameter("date")); //获取输入的日期
			     accountInfo.setId(Integer.parseInt(request.getParameter("hiddenUserId")));//用户id
			     salegoodsInfo.setAccountInfo(accountInfo);
			     
			     
			     
			     //获取系统时间的时间 精确到秒生成订单编号
				String ordernum = time.getSystemTime();
				//替换日期里的特殊字符
				ordernum = ordernum.replaceAll("[: ]", "0");
				ordernum=ordernum.replace("-", "0");
				
			     
			     //定义一个整形变量 用于存放数据库插入后的结果
			    int isaddsuccess=-1;
			    String path=request.getContextPath()+"/client/client.jsp";
			    isaddsuccess=orderDao.addorder(salegoodsInfo,ordernum);
			      
			    //如大于0则执行成功
			       
			      if(isaddsuccess==1){
			    response.sendRedirect(path);
			   
			      }
			    	
			      //小于等于0失败
			      else
			    	 
			          request.setAttribute("iffail", "fail");
			          /*RequestDispatcher dpDispatcher=this.getServletContext().getRequestDispatcher(path);
			    		 dpDispatcher.forward(request, response);*/
			     
			}
	 /**
	  * 查询订单的条数 
	  * @param 
	  */
	public void  counts(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException
			{
		
		//获取用户输入的查询条件的值
				String customername=request.getParameter("customername");
				String accountname=request.getParameter("accountname");
				
		//获取当前用户权限
		 HttpSession session=request.getSession();
	     AccountInfo accountInfo=(AccountInfo)session.getAttribute("account");
	     int lvl=accountInfo.getLvl();
		 int counts;
		 if((null!=customername&&!"".equals(customername))||(null!=accountname&&!"".equals(accountname)))
		 { counts=orderDao.selectcounts(lvl,customername, accountname);}
		 else {
			 counts=orderDao.counts(lvl);
		}
		 
		counts=(counts+pgSize-1)/pgSize;
		
		request.setAttribute("counts",counts);
		
	  
			}
	/**
	 * 订单查询显示
	 */
	public void selectsale(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException
			{
		
		String pgNo=request.getParameter("pageNo");		
		int pgStart=0;   
		//如果起始页为空则在第一页显示
		if(pgNo==null){
			pgStart=1;
		}
		else{
			pgStart=Integer.parseInt(pgNo);
		}
		
		//获取用户输入的查询条件的值
		String customername=request.getParameter("customername");//POST方式处理
		String accountname=request.getParameter("accountname");
		try {
			String referMethod=request.getMethod();//判断客户端是POST还是GET
			if(referMethod.equals("GET")){
				String name=request.getParameter("customername");//GET方式处理
				byte[] bt=name.getBytes("iso8859-1");
				name=new String(bt,"UTF-8");
				customername=name;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		//获得当前用户的session并取得权限
		 HttpSession session=request.getSession();
	     AccountInfo accountInfo=(AccountInfo)session.getAttribute("account");
	     int lvl=accountInfo.getLvl();
	     
	     //把用户的权限传递给orderdao orderDAO根据权限进行取要显示的订单
	    ArrayList<SalegoodsInfo> list=orderDao.selectorder(pgStart,pgSize,lvl,customername,accountname);
	    request.setAttribute("order", list);
			}
	
	/**
	 * 根据用户的id获取所对应的客户信息	
	 */

	public void  selectoption(HttpServletRequest request,HttpServletResponse response)
	    throws ServletException,IOException
	{
		//获取session里的用户信息
	   AccountInfo accountInfo=(AccountInfo)request.getSession().getAttribute("account");
	   //获取当前登录用户的Id
	  int x=accountInfo.getId();
	  //获取当前用户信息放入list里
		ArrayList<CustomerInfo> list=orderDao.getoptioncontent(x);		
       request.setAttribute("op", list);
	}
	
	/**
	 * 查询添加订单里 option 的值
	 */
		public void  selectType(HttpServletRequest request,HttpServletResponse response)
		    throws ServletException,IOException
		{
			ArrayList<CategoryInfo> list=orderDao.getType();		
	        request.setAttribute("type", list);
		}
		
		
		public void deleorder(HttpServletRequest request,HttpServletResponse response)
			    throws ServletException,IOException
		{
			int id=Integer.parseInt(request.getParameter("id"));
			
			int message= orderDao.deleorder(id);
			PrintWriter out=response.getWriter();
			out.write(message+"");
			 
		}
		
		/*
		 * 查询 商品库存
		 */
		public void selectcategory(HttpServletRequest request,HttpServletResponse response)
			    throws ServletException,IOException
		{
			int id=Integer.parseInt(request.getParameter("id"));
			
			int message= orderDao.deleorder(id);
			PrintWriter out=response.getWriter();
			out.write(message+"");
			 
		}
		
	
	
}