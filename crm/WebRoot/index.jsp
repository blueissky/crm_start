<%@ page language="java" import="java.util.*" import="net.qingsoft.crm.vo.AccountInfo" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/inc/header.jsp?nomenu=true"></jsp:include>
	  	<div class="panel panel-primary loginform">
		  <div class="panel-heading panel-headingIndex">CRM客户关系管理系统</div>
		  <div class="panel-body">
		  <% AccountInfo accountInfo = (AccountInfo)session.getAttribute("account");
		  	 
		  	 if(accountInfo != null){
		  	 	response.sendRedirect("/crm/userlogin/userlogin.jsp");
		  	 }
		  	 
		   %>
			<form class="form-horizontal" role="form" method="post" action = "<%=basePath %>servlet/AccountInfoServlet?action=login">
				  <div class="form-group">
				    <label  class="col-sm-3 control-label">账号:</label>
				    <div class="col-sm-8">
				      <input type="text" class="form-control" name="account" placeholder="输入账号">
				    </div>
				  </div>
				  <div class="form-group">
				    <label  class="col-sm-3 control-label">密码:</label>
				    <div class="col-sm-8">
				      <input type="password" class="form-control" name="password" placeholder="密码">
				    </div>
				  </div>  
				  <div class="form-group">
				    <div class="col-sm-offset-3 col-sm-9">
				      <button type="submit" class="btn btn-primary">登录系统</button>
				    </div>
				  </div>		  
			</form>
		  </div>
		</div>
<jsp:include page="/inc/footer.jsp"></jsp:include>
