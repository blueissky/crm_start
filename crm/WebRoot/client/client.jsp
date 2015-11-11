<%@ page language="java" import="java.util.*" import="net.qingsoft.crm.vo.*" import="net.qingsoft.crm.util.CookieUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/inc/header.jsp"></jsp:include>
<br/><br/><br/>
<div  class="container-fluid">
	<div class="row">
		<div class="col-md-2">
			<jsp:include page="/inc/sidebar.jsp"></jsp:include>
			<embed src="<%=basePath%>images/clock.swf" width="200" height="200">
		</div>
		<!--项目开始---------------------------------------------------------------------------------->
		<link href="<%=basePath%>css/client.css" rel="stylesheet">
		<div class="col-md-10">
		<!-- Tab切换 -->
			<ul class="nav nav-tabs" role="tablist">
				<li class="active"><a href="#message" role="tab" data-toggle="tab">信息查看</a></li>
				<li><a href="#newCustomer" role="tab" data-toggle="tab">新增客户</a></li>
			</ul>
		<!-- Tab面板 -->
		<div class="tab-content">
			<!-- 客户信息表 -->
			<div class="tab-pane active" id="message"> 
			<!-- 搜索位置 -->
				<table class="table table-bordered table-striped table-hover">
					<tr>
						<td>
						<form class="navbar-form navbar-left" method="post" action="<%=basePath%>client/client.jsp">
						<span class="label label-info">姓名</span><input class="form-control" type="text" name="searchname">        
						<span class="label label-info">公司</span><input class="form-control" type="text" name="searchcompany">
						<input type="submit" value="搜索" class="btn btn-default btn-lg" id="searchbutton">
						</form>
						</td>
					</tr> 
				</table>
			<!-- 搜索结束 -->
			<script>
			/**
			 * 取hiddenCustomerID值
			 */
			function getCustomerID(x){
				document.getElementById("hiddenCustomerID").value=x;/*修改隐藏hidden的值*/
			}
			</script>
				<table class="table table-hover bottomTable" id="customermessage">
					<tr >
					<th>编号</th><th>姓名</th><th>性别</th><th>年龄</th><th>联系方式</th><th>公司信息</th><th>客户来源</th><th>资料修改</th><th>删除</th>
                  
					</tr>
					 <jsp:include page="/servlet/ClientServlet?action=getCustomerInfo"></jsp:include>
					 <%		/*分页显示客户资料*/
					 ArrayList<CustomerInfo> list=(ArrayList<CustomerInfo>)request.getAttribute("list");
					 for(int i=0;i<list.size();i++){
						 CustomerInfo ci=list.get(i);
						 %>
						 <tr>
						 <td><span class="label label-success"><%=ci.getNum()%></span></td>
						 <td>
						 <!-- 下拉列表 -->
							<div class="btn-group"> 
							  <label  class=" dropdown-toggle " data-toggle="dropdown">
							    <%=ci.getName()%>
							  </label>
							  <ul class="dropdown-menu" role="menu">
							<li><a class="textaligh-center" href="<%=basePath%>servlet/ClientServlet?action=businessOrder&customerId=<%=ci.getId()%>">查看</a></li>
							  </ul>
		<!-- 				  <ul class="dropdown-menu" role="menu"> -->	
		<!--    			    <li><a href="<%=basePath%>order/order.jsp?customername=<%=ci.getName()%>">查看订单</a></li>-->
	  <!-- 获得点击行用户的ID   <li><a href="<%=basePath%>servlet/BusinessServlet?action=findAllRequest&customerName=<%=ci.getName()%>">查看业务</a></li>-->
							<!-- <li class="divider"></li>-->
    	<!-- 					<li><a class="btn btn-primary" data-toggle="modal" data-target="#newOrder" >新增订单</a></li> -->	
      <!-- 获得点击行用户的ID <li><a onclick=getCustomerID("<%=ci.getId()%>") class="btn btn-primary" data-toggle="modal" data-target="#myRequest">新增业务</a></li>
							  </ul>-->
							</div>
						<!-- 下拉列表 -->	
						 </td>
						 <td><%=ci.getSex()%></td>
						 <td><%=ci.getAge()%></td>
						 <td>
						 	<div class="btn-group"> 
							  <label  class=" dropdown-toggle " data-toggle="dropdown">
							    <%=ci.getTel()%>
							  </label>
							  <ul class="dropdown-menu" role="menu">
							    <li>QQ:<%=ci.getQq()%></li>
							     <li class="divider"></li>
							    <li>email:<%=ci.getEmail()%></li>
							  </ul>
							</div>
						 </td>
						 <td>
						 	<div class="btn-group"> 
							  <label  class=" dropdown-toggle " data-toggle="dropdown">
							    <%=ci.getCompanyInfo().getCompany()%>
							  </label>
							  <ul class="dropdown-menu" role="menu">
							    <li>概述:<%=ci.getCompanyInfo().getRange()%></li>
							     <li class="divider"></li>
							    <li>地址:<%=ci.getCompanyInfo().getAddress()%></li>
							  </ul>
							</div>
						 <td><%=ci.getWayInfo().getMethod()%></td>
						 <td></td>
						 <td><button class="btn btn-warning delCustomer" onclick=getCustomerID(<%=ci.getId()%>)>删除</button></td>
						 </tr>
						 <%
					 }
					 %>
				</table>
				<!-- 分页 -->
				<jsp:include page="/servlet/ClientServlet?action=getPageCount"></jsp:include>
				<ul class="pagination" id="customerpage">
					<li><a>&laquo;</a></li>
				<%int count=(Integer)request.getAttribute("count");
				for(int i=1;i<=count;i++){
					%>
					<li >
					<a href="<%=basePath%>client/client.jsp?pageNo=<%=i%>"><!-- 回调jsp文件并将pageNo参数传送到servlet -->
					<%=i%><!-- 页面显示的页数 -->
					</a>
					</li>
					<% 
				} %>
					<li><a>&raquo;</a></li>
				</ul>
				<!-- 分页结束 -->
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
					        	<input id="requestMethod" type="text" name="method">
					        	<input id="hiddenCustomerID" class="form-control" type="hidden" name="customerID">
					        </fieldset>
					        <fieldset>
					        	<label class="label label-info">沟通内容:</label>
					        	<textarea id="requestContent" class="form-control" rows="12" cols="18" name="content"></textarea>
					        </fieldset>
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					        <input type="submit" class="btn btn-primary" id="newRequest"></button><!-- 提交按钮 -->
					      </div>
				      </form>
				    </div>
				  </div>
				</div>
			   <!--新增业务弹出框结束 -->
			   <!-- 新增订单模态对话框 -->
				<div class="modal fade" id="newOrder" tabindex="-1" role="dialog"aria-hidden="true">
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
			</div>
			<!-- 新增客户表 -->
			<div class="tab-pane" id="newCustomer">
				<form  action="" name="newformname" onsubmit="return thispage()"  method="post" class="newform"> <!-- servlet/ClientServlet?action=saveMessage -->
					<fieldset>
							<div class="lab">
                        	<label class="label label-success lab2">姓名</label>
                        	</div>
                        	<div class="intext">
                            	<input type="text" class="inp username" name="customername">
                            </div>	
                    </fieldset>
                    <fieldset>
                    		<div class="lab">
                        	<label class="label label-success lab2">性别</label>
                        	</div>
                        	<div class="intext">
                                	<input type="radio" name="usersex" value="male"><span class="badge badge-success">男</span>
                                	<input class="inline" type="radio" name="usersex" value="female"><span class="badge badge-warning">女</span>
                            </div>    	
                    </fieldset>
                    <fieldset>
                    		<div class="lab">    	
                        	<label class="label label-success lab2">年龄</label>
                        	</div>
                            <div class="intext">	
                            	<input type="text" class="inp userage" name="userage">
                            	<span class="spanage">0-99之间</span>
                            </div>	
                    </fieldset>
                    <fieldset>    
                    		<div class="lab">	
                        	<label class="label label-success lab2">电话</label>
                        	</div>
                        	<div class="intext">	
                        		<input type="text" class="inp usertel" name="tel">
                        		<span class="spantel">数字和-组合</span>
                        	</div>	
                    </fieldset>
                    <fieldset>    	
                    		<div class="lab">
                        	<label class="label label-success lab2">Email</label>
                        	</div>
                        	<div class="intext">
                                    <input type="text" class="inp useremail" name="email">
                                    <span class="spanemail">xxx@xxx.xxx</span>
                            </div>        
                     </fieldset>
                    <fieldset> 
                    		<div class="lab">  	
                        	<label class="label label-success lab2">QQ</label>
                        	</div>
                        	<div class="intext">
                            	<input type="text" class="inp userqq" name="qq">
                            	<span class="spanqq">5到15位之间</span>
                            </div>	
                     </fieldset>
                    <fieldset>   	
                    		<div class="lab">
                        	<label class="label label-success lab2">公司名称</label>
                        	</div>
                        	<div class="intext">
                            	<input type="text" class="inp" name="company">
                            </div>	
                     </fieldset>
                    <fieldset>   	
                    		<div class="lab">
                        	<label class="label label-success lab2">经营范围</label>
                        	</div>
                        	<div class="intext">
                            	<input type="text" class="inp inparea" name="tradearea">
                            </div>	
                     </fieldset>
                    <fieldset>   	
                    		<div class="lab">
                        	<label class="label label-success lab2">地址</label>
                        	</div>
                        	<div class="intext">
                            	<input type="text" class="inp inpaddress" name="address">
                            </div>	
                     </fieldset>
                    <fieldset>   
                    		<div class="lab">	
                        	<label class="label label-success lab2">客户来源</label>
                        	</div>
                        	<div class="intext">
                            	<input type="text" class="inp" name="source">
                            </div>	
                     </fieldset>
                    <fieldset>   
                    		<div class="lab">	
                        	<label class="label label-success lab2">沟通方式/场所</label>
                        	</div>
                        	<div class="intext">
                            	<input type="text" class="inp" name="way">
                            </div>	
                   </fieldset>
                    <fieldset>     	
                    		<div class="lab">
                        	<label class="label label-success lab2">内容</label>
                        	</div>
                        	<div class="intext">
                            	<textarea rows="5" cols="5" name="textarea">
                            	</textarea>
                            </div>	
                	</fieldset>
                	<fieldset>
                		<input type="submit" value="提交" class="btn btn-warning btn-lg subbtn">
                		<input type="reset" value="重置" class="btn btn-warning btn-lg">
                	</fieldset>
                </form>
			</div>
		 </div>
		</div>
	<!-- Tab切换结束 -->
    <!--jquery样式弹出修改对话框-->
<!--项目结束------------------------------------------------------------------------------------>

	</div>
</div>
		<script src="<%=basePath %>js/jquery-1.8.2.min.js"></script>
		<script src="<%=basePath %>js/client.js"></script>
<jsp:include page="/inc/footer.jsp"></jsp:include>




















