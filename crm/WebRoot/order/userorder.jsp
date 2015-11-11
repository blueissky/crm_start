<%@page import="net.qingsoft.crm.vo.*"%>

<%@ page language="java" import="java.lang.*" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 头标题部分 -->
<link href="../css/order.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>js/jquery-1.8.2.min.js"></script>
	  


<jsp:include page="/inc/header.jsp"></jsp:include>
<div class="nll"></div>
<div class="container-fluid">
	<div class="row">
	<!-- 右边工具栏 -->
	 <div class="col-md-2">
	  	<jsp:include page="/inc/sidebar.jsp"></jsp:include>
	  </div>


	  <div class="col-md-10">
	  
		<!-- Tab切换 -->
		<ul class="nav nav-tabs" role="tablist">
		  <li class="active"><a href="#select" role="tab" data-toggle="tab">订单查询</a></li>
		</ul>
		
		<!-- Tab 面板 -->
		<div class="tab-content">
		  <div class="tab-pane active" id="select">
		  
		  
		  <!--  搜索栏-->
		   <div class="baner">
		     <form  action="<%=basePath %>servlet/AccountInfoServlet?action=orderView&id=<%=request.getAttribute("id") %>" method="post" >
		       		<div class="cp"><span class="label label-info">客户名</span></div>
		       		<div class="cpcontent"><input name="customername" class=" form-control"></div>
		       		<div><input type="submit" class="btn btn-default" value="搜索"></div>
		     </form>
		       		
		   </div>
		   <!--  搜索栏结束-->
		   
		   <!-- 订单显示栏 -->
		  				<table class="table table-striped table-hover text-center">
					<tr >
					    <th class="text-center">序号</th>
						<th class="text-center">订单编号</th>
        				<th class="text-center">客户名称</th>
				        <th class="text-center">品牌</th>
				        <th class="text-center">型号</th>
				        <th class="text-center">颜色</th>
				        <th class="text-center">数量</th>
				        <th class="text-center">单价</th>
				        <th class="text-center">金额</th>
				        <th class="text-center">开单日期</th>
				        <th class="text-center">操作员</th>
				        <th class="text-center">删除</th>
					</tr>
					
					
					<!-- 订单显示 -->
				<%  
				   ArrayList<SalegoodsInfo> list=(ArrayList<SalegoodsInfo>)request.getAttribute("order");
				      for(int i=0;i<list.size();i++)
				      {
					   SalegoodsInfo salegoodsinfo=(SalegoodsInfo)list.get(i);
					   int n=salegoodsinfo.getAmount();
			      	 double p=salegoodsinfo.getPrice();
					double sum=n*p;
						%>		
					<tr>
					    <td><span class="label label-success"> <%=salegoodsinfo.getSerialnb()%></span></td>
						<td><%=salegoodsinfo.getSaleInfo().getOdernum() %></td>
						<td><%=salegoodsinfo.getSaleInfo().getCustomerInfo().getName() %></td>
						<td><%=salegoodsinfo.getCategoryInfo().getBrand() %></td>
						<td><%=salegoodsinfo.getCategoryInfo().getName() %></td>
						<td><%=salegoodsinfo.getCategoryInfo().getColor() %></td>
						<td><%=salegoodsinfo.getAmount() %></td>
						<td><%=salegoodsinfo.getPrice() %></td>
						<td><%=sum %></td>
						<td><%=salegoodsinfo.getSaledate()%></td>
						<td><%=salegoodsinfo.getAccountInfo().getName() %></td>
					    <td ><button class="btn btn-danger btndel" value = "<%=salegoodsinfo.getId()%>" data-toggle="modal" data-target="#adbt">删除</button></td>
					</tr>
					<% }%>
				</table>
				
				
				<!-- 分页 -->
			  <jsp:include page="/servlet/AccountInfoServlet?action=counts"></jsp:include>
			       <ul class="pagination">
			       	<li><a>&laquo;</a></li>
			      
					<li>
				<%	
					int count=(Integer)request.getAttribute("count");
				   
					for(int i=1;i<=count;i++){
				%>
							<a href=
							"<%=basePath %>servlet/AccountInfoServlet?action=orderView&id=<%=request.getAttribute("id")%>&pageNo=<%=i%>">
							<%=i%></a>
					<%} %>
					</li>
					<li><a>&raquo;</a></li>
					</ul>	  
		  </div>
		  
		<!-- Tab切换 结束-->	  	

</div>
</div>
</div>
<!-- 结束模式对话框 -->

<script type="text/javascript" >

</script>

 



<jsp:include page="/inc/footer.jsp"></jsp:include>

<script language="javascript" type = "text/javascript">
	var arrdel = $(".btndel");
	for(var i = 0; i<arrdel.length;i++){
		arrdel[i].onclick = function(){
			if(window.confirm("你确定要删除该订单吗?")){
				window.location.href="<%=basePath%>servlet/AccountInfoServlet?action=delOrder&id="+this.value;
				alert("即将删除"+this.value+"订单");
				return true;
			}
			else{
				return false;
			}
		}
	}
</script>
</div>