<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<table class="table table-hover bottomTable">
				<tr>
					<th>方式</th><th>内容</th><th>时间</th>
				</tr>
				<c:forEach items="${requestScope.requestInfo}" var="requestInfo" varStatus="stat">
				 <tr>
					 <td>${requestInfo.method}</td>
					 <td>${requestInfo.cont}</td>
					 <td>${requestInfo.conndate }</td>				 
				 </tr>
				</c:forEach>
				<tr>
					<td></td><td></td><td></td>
					<td><a class="btn btn-primary" data-toggle="modal" data-target="#myRequest">新增业务</a></td>
				</tr>
				</tbody>
</table>