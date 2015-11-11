package net.qingsoft.crm.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.qingsoft.crm.user.dao.AccountInfoDao;
import net.qingsoft.crm.user.dao.UserDaoFactory;
import net.qingsoft.crm.util.BaseServlet;
import net.qingsoft.crm.vo.AccountInfo;
import net.qingsoft.crm.vo.CustomerInfo;
import net.qingsoft.crm.vo.SalegoodsInfo;

public class AccountInfoServlet extends BaseServlet {
	private AccountInfoDao accountInfoDao = UserDaoFactory.createAccountInfoDao();
	
	public void login(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		String username = request.getParameter("account");
		String password = request.getParameter("password");
		
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setUsername(username);
		accountInfo.setPassword(password);
		
		AccountInfo acc = accountInfoDao.checkLogin(accountInfo);
		
		if(acc == null){
			response.sendRedirect("/crm/index.jsp");
		}
		
		else{
			int enable = acc.getEnable();
			
			if(enable == 0){
				response.sendRedirect("/crm/index.jsp");
			}
			
			else{
				HttpSession session = request.getSession();
				session.setAttribute("account", acc);
					
				RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/userlogin/userlogin.jsp");
			
				rd.forward(request, response);
			}
		}
		
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		request.getSession().invalidate();
		
		response.sendRedirect("/crm/index.jsp");
	}
	
	public void view(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		
		response.sendRedirect("/crm/userlogin/userview.jsp");
	
		
	}
	
	public void viewAll(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		String pgNo=request.getParameter("pageNo");
		
		
		int pgSize = 5;
		int pgStart=0;
		if(pgNo==null){
			pgStart=1;
		}
		else{
			pgStart=Integer.parseInt(pgNo);
		}
		ArrayList<AccountInfo> list =accountInfoDao.viewAllInfo(pgStart, pgSize);//获得所有客户资料list
		request.setAttribute("list",list); 
	}
	
	public void updatePass(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		String password = request.getParameter("newpass");
		
		HttpSession session = request.getSession();
		
		AccountInfo accountInfo = (AccountInfo)(session.getAttribute("account"));
		
		int id = accountInfo.getId();
		
		AccountInfo acc = accountInfoDao.updatePassword(id, password);
		
		session.setAttribute("account", acc);
		
		response.sendRedirect("/crm/userlogin/userview.jsp");

	}
	
	public void updateInfo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String name = request.getParameter("newname");
		
		name = java.net.URLDecoder.decode(name,"utf-8");
		
		String tel = request.getParameter("newtel");
		
		HttpSession session = request.getSession();
		
		AccountInfo accountInfo = (AccountInfo)(session.getAttribute("account"));
		
		int id = accountInfo.getId();
		
		AccountInfo acc = accountInfoDao.updateInfo(id, name, tel);
		
		session.setAttribute("account", acc);
		
		response.sendRedirect("/crm/userlogin/userview.jsp");
	}
	
	public void pageCount(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		int pgSize = 5;
		
		int count=accountInfoDao.countAccount();
//		request.setAttribute("num", count);
		count=(count+pgSize-1)/pgSize;
		request.setAttribute("count",count);
	}
	
	public void isExist(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		String username = request.getParameter("username");
		
		boolean bool = accountInfoDao.isExist(username);
		
		PrintWriter out = response.getWriter();
		
		out.write(bool+"");
	}
	
	public void addUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		name = java.net.URLDecoder.decode(name,"utf-8");
		
		String tel = request.getParameter("tel");
		String lvl = request.getParameter("lvl");
		
		accountInfoDao.addUser(username, name, tel, lvl);
		
		response.sendRedirect("/crm/user/user.jsp");
	}
	
	public void delUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		int id =Integer.parseInt(request.getParameter("id"));
		accountInfoDao.delUser(id);
		
		response.sendRedirect("/crm/user/user.jsp");
	}
	
	public void changeLvl(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		int id =Integer.parseInt(request.getParameter("id"));
		accountInfoDao.changeLvl(id);
		
		response.sendRedirect("/crm/user/user.jsp");
	}
	
	public void orderView(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		String pgNo=request.getParameter("pageNo");
		String customername = request.getParameter("customername");
		
		System.out.println(id);
		System.out.println(customername);
		
		int pgSize = 8;
		int pgStart=0;
		if(pgNo==null){
			pgStart=1;
		}
		else{
			pgStart=Integer.parseInt(pgNo);
		}
		ArrayList<SalegoodsInfo> list =accountInfoDao.orderview(pgStart, pgSize,id,customername);
		request.setAttribute("order",list); 
		request.setAttribute("id", id);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/order/userorder.jsp");
		
		rd.forward(request, response);
		
	}
	
	public void counts(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		int pgSize = 8;
		int count=accountInfoDao.counts(id);
		count=(count+pgSize-1)/pgSize;
		request.setAttribute("count",count);
	}
	
public void delOrder(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		int id =Integer.parseInt(request.getParameter("id"));
		accountInfoDao.delOrder(id);
		
		response.sendRedirect("/crm/user/user.jsp");
	}
}
