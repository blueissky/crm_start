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
		     <form  action="order/order.jsp" method="post" >
		       		<div class="cp"><span class="label label-info">客户名</span></div>
		       		<div class="cpcontent"><input name="customername" class="form-control"></div>
		       		<div class="odnum"><span class="label label-info">操作员</span></div>
		       		<div class="odnumcontent"><input name="accountname" class="form-control"></div>
		       		<div><input type="submit" class="btn btn-default" value="搜索"></div>
		       		<div><a herf="order/order.jsp"></a><button>显示全部</button></a></div>
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
				        <th class="text-center">操作</th>
				       
					</tr>
					
					
					<!-- 订单显示 -->
				<jsp:include page="/servlet/OrderServlet?action=selectsale"></jsp:include>
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
					    <td><span class="label label-success"><%=salegoodsinfo.getSerialnb()%></span></td>
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
					    <td ><button class="btn btn-warning btn-sm delebutton" data-toggle="modal" data-target="" value="<%=salegoodsinfo.getId()%>"  >删除</button></td>
					</tr>
					<% }%>
				</table>
				
				
				<!-- 分页 -->
			  <jsp:include page="/servlet/OrderServlet?action=counts"></jsp:include> 
			       <div class="page">
			      
					<ul class="pagination">
					<li><a>&laquo;</a></li>
				<%	
					int count=(Integer)request.getAttribute("counts");
				   
					for(int i=1;i<=count;i++){
				%>
							<li><a href="<%=basePath%>order/order.jsp?pageNo=<%=i%>">
							<%=i%></a></li>
					<%} %>
					<li><a>&raquo;</a></li>
					</ul>
					</div>	 
         	<!-- 分页结束 -->
		      </div>
		
		 	
		<!-- Tab切换 结束-->	  	
				<!-- 模态对话框1 -->
				<div class="modal fade" id="" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">删除对话框</h4>
				      </div>
				      <div class="modal-body">
				      <span id="isdelete">是否要删除此条数据Y/N	</span> 	
	     		      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-primary">Yes</button>
				        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
				      </div>
				    </div>
				  </div>
				</div>
				<!-- 模态对话框1结束 -->
				  <!-- 新增订单模态对话框 -->
				<div class="modal fade" id="adbt" tabindex="-1" role="dialog"aria-hidden="true">
				<div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
				        <h4 class="modal-title" id="4">添加订单</h4>
				      </div>
				      <div class="modal-body saleel">
				        <p>
							<form role="form" action="servlet/OrderServlet?ation=addorder" id="fm">
							 <div class="form-group">
							    <label for="k" class="fbb"><span >编号</span></label>
							    <input name="number" class="form-control" id="k" >
							  </div>
							  <div class="form-group">
							    <label for="mv" class="fbb"><span>名称</span></label>
							    <input name="name" class="form-control" id="mc" >
							       <select id="nm">
							        <option>请选择</option>
							   <jsp:include page="/servlet/OrderServlet?action=selectoption"></jsp:include>
							   <%
							     ArrayList<CustomerInfo> list6=(ArrayList<CustomerInfo>)request.getAttribute("op");
							     for(int k=0;k<list6.size();k++){
							    		CustomerInfo cs=list6.get(k);
							   %>
							<option><%=cs.getName() %></option>
							     <%} %>
							     </select>
							  </div> 
							  <div class="form-group">
							  <jsp:include page="/servlet/OrderServlet?action=selectType"></jsp:include>
							    <label for="pp" class="fbb"><span>品牌</span></label>
							    <input  name="brand" class="form-control" id="pp">
							      <select id="bd">
							        <option>请选择</option>
							         <%
							         ArrayList<CategoryInfo> listType=(ArrayList<CategoryInfo>)request.getAttribute("type");
							    	 ArrayList brandlist=new ArrayList();
							         ArrayList  stylelist=new ArrayList();	
							         ArrayList  colorlist=new ArrayList();
							         for(int m=0;m<listType.size();m++){
							     	    CategoryInfo cate=listType.get(m);
							        %>
							        <option><%=cate.getBrand() %></option>
							        <%} %>
							      </select>
							  </div>
							    <div class="form-group">
							    <label for="xh" class="fbb"><span>型号</span></label>
							    <input name="xinghao"  class="form-control" id="xh" >
							       <select id="stl">
							      	 <option>请选择</option>
							        <% for(int m=0;m<listType.size();m++){
							    		CategoryInfo cate=listType.get(m);
							   %>
							      	  <option><%=cate.getName() %></option>
							        <%} %>
							       </select>
							    </div>
							    <div class="form-group">
							    <label for="ys" class="fbb"><span>颜色</span></label>
							    <input name="color" class="form-control" id="ys" >
							      <select id="cl">
							      	<option>请选择</option>
							          <% 
							          for(int c=0;c<listType.size();c++){
							    		CategoryInfo cate=listType.get(c);
							   %>
							       	<option><%=cate.getColor()%></option>
							        <%} %>
							     </select>
							  </div>
							    <div class="form-group">
							    <label for="e" class="fbb"><span>数量</span></label>
							    <input name="amount" class="form-control" id="e" >
							  </div>
							    <div class="form-group">
							    <label for="f" class="fbb"><span>单价</span></label>
							    <input name="price" class="form-control" id="f" >
							  </div>
							    <div class="form-group">
							    <label for="g" class="fbb"><span>金额</span></label>
							    <input name="jine" class="form-control" id="g" >
							  </div>
							    <div class="form-group">
							    <label  for="yh" class="fbb"><span>用户</span></label>
							    <%AccountInfo accountif=(AccountInfo)session.getAttribute("account"); %>
							    <input  name="user" class="form-control"  id="yh" value="<%=accountif.getName() %>">
							  </div>
					       </form>
					       </p>
						<div class="modal-footer">
						 <input type="submit" class="btn btn-primary " id="save"  value="保存"> 
				        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				      </div>
				    </div>
				</div>
				</div>
				</div>
				<!-- 新增订单模态对话框结束-->
			
				<!-- 结束模式对话框 -->

<script type="text/javascript" >

	$(document).ready(function(){

		var arraylist=$(".delebutton");
		
		for(var i=0;i<arraylist.length;i++){
			arraylist[i].onclick=function(){
				var judge=confirm("确认删除？");
				if(judge==false){
					return;
				}
				var a=this.value;
			$.ajax({
				 type:"post",
				 async:false,
				 data:"action=deleorder&id="+a,
				 url:"<%=basePath %>servlet/OrderServlet",
				 success:function(message){
					 if(message=="0"||message=="-1"){
						alert("无法删除订单");
					 }
					 else{
						 alert("删除订单成功！");
					}
				 }
			   });
			window.location.href="<%=basePath%>order/order.jsp";
			};
		}
		var a=100;
		//此方法实现在点击添加按钮时 模态对话框里面自动添加订单编号
		  $("#tj").click(function(){
			  a=a+1;
			  var c=a+"";
			  $("#k").attr("value",c);
		  });
		
		 //通过option改变客户名称
		$("#nm").change(function(){
			var x=$("#nm").val();
			$("#mc").attr("value",x);
		});
		 
	
		
		//通过option改变品牌
		$("#bd").change(function(){
			var x=$("#bd").val();
			$("#pp").attr("value",x);
		});
		
		//通过option改变型号
		$("#stl").change(function(){
			var x=$("#stl").val();
			$("#xh").attr("value",x);
		});
		 
		//通过option改变颜色
		$("#cl").change(function(){
			var x=$("#cl").val();
			$("#ys").attr("value",x);
		});
		//点击保存关闭窗口 并且向数据库插入数据
	

		});
</script>

 



<jsp:include page="/inc/footer.jsp"></jsp:include>
</div>