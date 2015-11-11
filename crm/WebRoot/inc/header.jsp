<%@ page language="java" import="java.util.*" import="net.qingsoft.crm.vo.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>青软客户管理系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
    <!-- Bootstrap -->
    <link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="<%=basePath%>css/default.css" rel="stylesheet">
  </head>
  <body class="${(not empty(param.nomenu))?'bodybk':''}">
<c:if test="${empty(param.nomenu)}">
<nav class="navbar navbar-default navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" font-size: 20px;" href="<%=basePath %>userlogin/userlogin.jsp">客户管理系统</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

      <%
     	AccountInfo accountInfo = (AccountInfo)(session.getAttribute("account"));
       %>
      <ul class="nav navbar-nav navbar-right">
      	<li><a style="color:#FFF;">当前用户</a></li>
		<li><a style="color:#FFF;" href="<%=basePath %>servlet/AccountInfoServlet?action=view"><%out.println(accountInfo.getName()); %></a></li>
        <li><a style="color:#FFF;" href="<%=basePath %>servlet/AccountInfoServlet?action=logout">注销</a></li>
      </ul>
    </div>
  </div>
</nav>
</c:if>
