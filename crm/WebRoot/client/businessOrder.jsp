<%@ page language="java" import="java.util.*" import="net.qingsoft.crm.vo.*" import="net.qingsoft.crm.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <script src="<%=basePath %>js/jquery-1.8.2.min.js"></script>
<script src="<%=basePath%>js/businessorder.js"> </script> 
<jsp:include page="/inc/header.jsp"></jsp:include>
<br/><br/><br/>
<link href="<%=basePath%>css/businessorder.css" rel="stylesheet">
<div  class="container-fluid">
	<div class="row">
		<div class="col-md-2">
			<jsp:include page="/inc/sidebar.jsp"></jsp:include>
			<embed src="<%=basePath%>images/clock.swf" width="200" height="200">
		</div>
 		<div class="col-md-10">
 		<%
 		  CustomerInfo	info=(CustomerInfo)request.getAttribute("customerInfo");//客户ID和acountID
    	  CustomerInfo customerInfo=(CustomerInfo)request.getAttribute("customerList"); //客户资料
 		  ArrayList<RequestInfo> requestList=(ArrayList<RequestInfo>)request.getAttribute("requesList");//业务资料
 		%>
 			<table class="table  table-hover">
 				<tr><th><h1><%=customerInfo.getCompanyInfo().getCompany() %></h1>
 						<h5>地址：<%=customerInfo.getCompanyInfo().getAddress() %></h5>
 						<span><%=customerInfo.getName() %></span>
						<h5><%=customerInfo.getSex()%>,年龄：<%=customerInfo.getAge()%>,电话：<%=customerInfo.getTel() %></h5> 	
 					</th>
 				</tr>
 			</table>
 			<!-- Tab切换 -->
			<ul class="nav nav-tabs" role="tablist">
				<li class="active"><a href="#business" role="tab" data-toggle="tab">业务信息</a></li>
				<li><a href="#order" role="tab" data-toggle="tab">订单信息</a></li>
			</ul>
			<!-- Tab面板 -->
			<div class="tab-content">
			<div class="tab-pane active" id="business"> 
				<table class="table table-hover bottomTable">
				<tr>
					<th>方式</th><th>内容</th><th>时间</th>
				</tr>
				<%
					for(int i=0;requestList!=null&&i<requestList.size();i++){ 
					RequestInfo ri=(RequestInfo)requestList.get(i);
					%>
				<tr>
					<td><%=ri.getMethod()%></td>
					<td><%=ri.getCont()%></td>
					<td><%=ri.getConndate()%></td>
					</td><td>
				</tr>
				<%
					}
				%>
				<tr>
					<td></td><td></td><td></td>
					<td><a class="btn btn-primary" data-toggle="modal" data-target="#myRequest">新增业务</a></td>
				</tr>
				</table>
			</div>
			
			
			

			
			
			
		<!-- 订单部分 -->
			<div class="tab-pane" id="order"> 
				<table class="table table-hover bottomTable">
				<tr>
						<th>订单编号</th>
				        <th>品牌</th>
				        <th>型号</th>
				        <th>颜色</th>
				        <th>数量</th>
				        <th>单价</th>
				        <th>金额</th>
				        <th>开单日期</th>
				</tr>
		  				<%ArrayList<SalegoodsInfo> list=(ArrayList<SalegoodsInfo>)request.getAttribute("saleList");
					 	  for(int u=0;u<list.size();u++){
					  	   SalegoodsInfo salegoodsInfo=list.get(u);
					       int n=salegoodsInfo.getAmount();
			      	       double p=salegoodsInfo.getPrice();
					       double sum=n*p;

					       %>
			    <tr>

				     	<th><%=salegoodsInfo.getSaleInfo().getOdernum() %></th> 
				        <th><%=salegoodsInfo.getCategoryInfo().getBrand() %></th>
				        <th><%=salegoodsInfo.getCategoryInfo().getName() %></th>
				        <th><%=salegoodsInfo.getCategoryInfo().getColor() %></th>
				        <th><%=salegoodsInfo.getAmount() %></th>
				        <th><%=salegoodsInfo.getPrice() %></th> 
				        <th><%=sum %></th>
				        <th><%=salegoodsInfo.getSaledate() %></th>
				        <th></th>
				</tr>
					<%} %>
				</table>

					  <button class="btn btn-warning btn-sm delebutton  " data-toggle="modal" data-target="#adbt" value=""  >添加</button>

				</div>

			 <!-- 新增订单对话框 -->

				<div class="modal fade" id="adbt" tabindex="-1" role="dialog"aria-hidden="true">
				<div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
				        <h4 class="modal-title" id="4">添加订单</h4>
				      </div>
				      <div class="modal-body saleel">
				        <p>
							<form  role="form" action="servlet/OrderServlet?action=addsale" method="post" >

							 <div class="form-group">

							    <%--<% CustomerInfo custinfo=(CustomerInfo)request.getAttribute("customerinfo"); --%>
							    <input type="hidden" name="hiddenCustId" value="<%=info.getId()%>">

							  </div>
							  <div class="form-group">
							    <label for="pp" class="fbb"><span>商品</span></label>
							      <select id="bd" name="brand"> 


							    <% ArrayList<CategoryInfo> categorylist=(ArrayList<CategoryInfo>)request.getAttribute("categoryList");

							    		for(int k=0;k<categorylist.size();k++){
							    		 CategoryInfo categoryinfo=categorylist.get(k);%>
							        <option value="<%=categoryinfo.getId()%>"><%=categoryinfo.getBrand() %>  <%=categoryinfo.getName() %>  <%=categoryinfo.getColor() %> </option>
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
							    <input name="jine" class="form-control jine" id="g" >
							  </div> 
							  
							    <div class="form-group">
							    <label for="jine" class="fbb"><span>日期</span></label>
							    <input name="date" class="form-control" id="jine" type="date">
							  </div>
							  
						      <% CustomerInfo customerInfo3=(CustomerInfo)request.getAttribute("customerInfo"); %>
							    <div class="form-group">
							    <label  for="yh" class="fbb"><span>用户</span></label>
							    <input  name="user" class="form-control"  id="yh" value="<%=customerInfo3.getAccountInfo().getName() %>">
							    <input  type="hidden" name="hiddenUserId" value=<%=customerInfo3.getAccountInfo().getId() %>>
							  </div>

							   <input type="submit" class="btn btn-primary " id="save"  value="保存"> 
							   <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				      </div>
					       </form>
  
  
  
				    </div>
				</div>
				</div>
				</div>


				<!-- 新增订单模态对话框结束--> 

 <!-- 新增业务弹出框-->
				<div class="modal fade" id="myRequest" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <form ><!-- 新增业务信息 -->
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal">
						        <span aria-hidden="true">&times;</span>
						        <span class="sr-only">Close</span>
					        </button>
					        <h4 class="modal-title" id="myModalLabel">新增业务信息</h4>
					      </div>
					      <div class="modal-body">
					        <fieldset >
					        	<label class="label label-info">沟通场所:</label>
					        	<input id="requestMethod" type="text" >
					        	<input id="hiddenCustomerID" class="form-control" type="hidden" value="<%=info.getId()%>">
					        </fieldset>
					        <fieldset>
					        	<label class="label label-info">沟通内容:</label>
					        	<textarea id="requestContent" class="form-control" rows="12" cols="18"></textarea>
					        </fieldset>
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					        <button type="button" class="btn btn-primary" data-dismiss="modal" id="newRequest">提交</button><!-- 提交按钮 -->
					      </div>
				      </form>
				    </div>
				  </div>
				</div>
				
				
			</div>
 		</div>
 	</div>
 </div>
 
			   <!--新增业务弹出框结束 -->
		  <script type="text/javascript" >
			   $(document).ready(function(){
					//通过option改变商品信息
					$("#bd").change(function(){
						var x=$("#bd").val();
						$("#pp").attr("value",x);
					});
					
					$("#e").blur(function(){
			  			var amt = $("#e").val;
			  			
			  			
			  			$.ajax({
			  				type:"post",
			  				async:false,
			  				data:"method=isexist&amount="+amt,
			  				url:"<%=basePath%>servlet/UserinfoServlet",
			  				success:function(message){
			  					if(message=="true")
			  					{
			  						$("#usernameError").html("<font color='Green'>√</font>");
			  					}
			  					else
			  					{
			  						$("#usernameError").html("<font color='red'>x</font>");
			  					}
			  				}
			  			});
			  			
			  			
			  		});
					
			   });
		  </script>


<jsp:include page="/inc/footer.jsp"></jsp:include>
