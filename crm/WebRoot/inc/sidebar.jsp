<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<div class="panel panel-primary " id="menuBar">
		  <div class="panel-heading"></div>
		  <div class="panel-body">
		  	<ul class="list-group" style="text-align:center">
			  <%-- <li class="list-group-item"><a href="<%=basePath%>user/user.jsp" onclick="return false" >用户管理</a></li> --%>
			  <li class="list-group-item "><a href="<%=basePath%>user/user.jsp">用户管理</a></li>
			  <li class="list-group-item "><a href="<%=basePath%>product/product.jsp">商品资料</a></li>
			  <li class="list-group-item"><a href="<%=basePath%>client/client.jsp">客户资料</a></li>
			  <li class="list-group-item"><a href="<%=basePath%>servlet/BusinessServlet?action=findAllRequest">客户业务</a></li>
			  <li class="list-group-item"><a href="<%=basePath%>order/order.jsp">订单管理</a></li>
<%-- 			  <li class="list-group-item"><%=basePath%>订单管理</a></li>
 --%>			  
			  
			</ul>     
		  </div>
</div>
