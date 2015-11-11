package net.qingsoft.crm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.qingsoft.crm.vo.AccountInfo;

public class SecurityFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
//		System.out.println("安全过滤器启动");
		request.setCharacterEncoding("utf-8");//编码转换
		response.setContentType("text/html");//编码转换
		response.setCharacterEncoding("utf-8");//编码转换
		HttpServletRequest userRequest = (HttpServletRequest)request;
		HttpServletResponse userResponse = (HttpServletResponse)response;
		
		AccountInfo accountInfo = null;
		
		accountInfo = (AccountInfo)userRequest.getSession().getAttribute("account");
		
		if(accountInfo == null){
			userResponse.sendRedirect("/crm/index.jsp");
		}
		
		else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
