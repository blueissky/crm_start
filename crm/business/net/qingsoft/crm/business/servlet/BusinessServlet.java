package net.qingsoft.crm.business.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.qingsoft.crm.business.dao.BusinessInfoDaoFactory;
import net.qingsoft.crm.business.dao.imp.BusinessInfoDaoImp;
import net.qingsoft.crm.util.BaseServlet;
import net.qingsoft.crm.vo.AccountInfo;
import net.qingsoft.crm.vo.CustomerInfo;
import net.qingsoft.crm.vo.RequestInfo;

public class BusinessServlet extends BaseServlet {
	private BusinessInfoDaoImp bidi=BusinessInfoDaoFactory.getBusinessInfoDaoImp();
	/**
	 * 查询所有业务信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findAllRequest(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		RequestInfo requestInfo=new RequestInfo();//业务需求表
		CustomerInfo customerInfo=new CustomerInfo();//客户信息表
		AccountInfo accountInfo=(AccountInfo)request.getSession().getAttribute("account");//当前账户表
		/**
		 * 判断参数是否为空
		 */
		String referMethod=request.getMethod();//判段客户端是POST还是GET
		if(request.getParameter("customerName")==null){
			customerInfo.setName("");
		}else{
			if(referMethod.equals("GET")){
				String name=request.getParameter("customerName");//GET处理
				byte[]bs=name.getBytes("iso8859-1");
				name=new String(bs,"utf-8");
				customerInfo.setName(name);
			}else{
				customerInfo.setName(request.getParameter("customerName"));//POST处理	
			}
		}
		if(request.getParameter("method")==null){
			requestInfo.setMethod("");
		}else{
			requestInfo.setMethod(request.getParameter("method"));
		}
		requestInfo.setCustomerInfo(customerInfo);
		requestInfo.setAccountInfo(accountInfo);
		
		int pageNo=1;//初始页面
		try {
			int no=Integer.parseInt(request.getParameter("pageNo"));//当前页码
			pageNo=no;//如果页面开始点击时启用
		} catch (Exception e) {}
		int pageSize=8;//每页显示的大小
		
		int num=(bidi.findCount(requestInfo)+pageSize-1)/pageSize;//获得分页的数量
		request.setAttribute("num", num);
		
		ArrayList<RequestInfo> list=bidi.findAllRequest(requestInfo,pageNo,pageSize);//获得数据库返回数据可能为空
		request.setAttribute("findAllRequest",list);
		this.forward("/business/business.jsp", request, response);
	}
	/**
	 * 新增业务信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void newRequest(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		AccountInfo accountInfo=(AccountInfo)request.getSession().getAttribute("account");//获得登陆用户ID
		CustomerInfo customerInfo=new CustomerInfo();
		customerInfo.setId(Integer.parseInt(request.getParameter("customerID")));//获取当前客户ID
		RequestInfo requestInfo=new RequestInfo();
		requestInfo.setMethod(request.getParameter("method"));//获得谈论场所
		requestInfo.setCont(request.getParameter("content"));//获得谈论内容
		requestInfo.setAccountInfo(accountInfo);
		requestInfo.setCustomerInfo(customerInfo);
		/**
		 * 调用 dao方法
		 */
		int num=bidi.newRequest(requestInfo);
		/**
		 * 向ajax返回结果
		 */
		request.setAttribute("requestInfo", bidi.getRequestInfo(customerInfo));
		this.forward("/business/ajaxBusiness.jsp", request, response);
	}
	public void delRequest(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		String requestId=request.getParameter("delRequest");
		RequestInfo requestInfo=new RequestInfo();
		requestInfo.setId(Integer.parseInt(requestId));
		int num=bidi.delRequest(requestInfo);//判断数据库删除是否成功
		//this.redirect("business/business.jsp", request, response);
		PrintWriter out = response.getWriter();
		out.write("true");
	}
}
