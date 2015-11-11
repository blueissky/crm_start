package net.qingsoft.crm.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseServlet extends HttpServlet{
	/**
	 * POST传输方式
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//编码转换
		response.setContentType("text/html");//编码转换
		response.setCharacterEncoding("UTF-8");//编码转换
		this.doGet(request, response);
	}	
	/**
	 * GET传输方式
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//编码转换
		response.setContentType("text/html");//编码转换
		response.setCharacterEncoding("UTF-8");//编码转换
		String action = request.getParameter("action");//获得页面请求参数
		if(action==null) {
			return;
		}
		Class clazz = this.getClass();//反射本身
		try {
			Method method = clazz.getMethod(action, HttpServletRequest.class,HttpServletResponse.class); 
			//调用action方法
			method.invoke(this,request,response);
			//执行actino方法
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	/**
	 *转发 路径
	 */
	protected void forward(String path,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		this.getServletContext().getRequestDispatcher(path).forward(request, response);
	}
	/**
	 * 跳转路径
	 */
	protected void redirect(String path,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		if(path.startsWith("http://")){
			response.sendRedirect(path);
		}
		else {
			String p = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+p;
			if(path.startsWith("/"))
				response.sendRedirect(basePath+path);
			else
				response.sendRedirect(basePath+"/"+path);
		}
	}
}
