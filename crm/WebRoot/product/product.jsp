<%@ page language="java" import="java.util.*" import="net.qingsoft.crm.vo.CategoryInfo" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<jsp:include page="/inc/header.jsp"></jsp:include>
<br/><br/><br/>
<div class="container-fluid">
	<div class="row">
	  <div class="col-md-2">
	  	<jsp:include page="/inc/sidebar.jsp"></jsp:include>
	  	<embed src="<%=basePath%>images/clock.swf" width="200" height="200">
	  </div>
	  <div class="col-md-10">

		<!-- Tab切换 -->
		<ul class="nav nav-tabs" role="tablist">
		  <li class="active"><a href="#viewproduct" role="tab" data-toggle="tab">商品查看</a></li>
		  <li><a href="#addproduct" role="tab" data-toggle="tab">新商品添加</a></li>
		</ul>
		
		<!-- Tab 面板 -->
		<div class="tab-content">
		  <div class="tab-pane active" id="viewproduct">
				<!-- 表格 -->
				<table class="table table-striped table-hover text-center">
					<tr >
						<th class="text-center">编号</th>
						<th class="text-center">品牌</th>
						<th class="text-center">型号</th>
						<th class="text-center">颜色</th>
						<th class="text-center">数量</th>
						<th class="text-center">操作</th>
					</tr>
					
					<jsp:include page="/servlet/ProductInfoServlet?action=viewAll"></jsp:include>
					<%		
					 ArrayList<CategoryInfo> list = (ArrayList<CategoryInfo>)request.getAttribute("list");
					 for(int i=0;i<list.size();i++){
						 CategoryInfo categoryInfo=list.get(i);
						 %>
						 <tr>
							 <td><span class="label label-success"><%=categoryInfo.getId()%></span></td>
							 <td><%=categoryInfo.getBrand()%></td>
							 <td><%=categoryInfo.getName()%></td>
							 <td><%=categoryInfo.getColor()%></td>
							 <td><%=categoryInfo.getAmount()%></td>
							 <td>
							 	<button class="btn btn-default btnview" data-toggle="modal" data-target="#viewProduct" value = "<%=categoryInfo.getId()%>">图片</button>
							 	<button class="btn btn-warning btnupdate" data-toggle="modal" data-target="#updateAmount" value = "<%=categoryInfo.getId()%>">修改</button>
							 </td>
						 </tr>
						 <%
						 	} 
						 %>
				</table>
				<!-- 分页 -->
				<jsp:include page="/servlet/ProductInfoServlet?action=pageCount"></jsp:include>
				
				
					<ul class="pagination">
						<li><a>&laquo;</a></li>
							<li>
								<%	
									int count=(Integer)request.getAttribute("count");
									for(int i=1;i<=count;i++){
								%>
							<a href="<%=basePath%>product/product.jsp?pageNo=<%=i%>">
							<%=i%>
							</a>
							<%
								}
							 %>
							</li>
							
						<li><a>&raquo;</a></li>
					</ul>
				
		  </div>
		  
		  <script>
			/**
			 * 取hiddenProductID值
			 */
			function getProductID(x){
				document.getElementById("hiddenProductID").value=x;/*修改隐藏hidden的值*/
			}
		  </script>
		  
		  <div class="tab-pane" id="addproduct">
				<!-- 添加新商品 -->
			<form enctype="multipart/form-data" method="post" action = "<%=basePath %>servlet/ProductInfoServlet?action=addProduct">
				<table class="table table-bordered table-striped table-hover">
					<tr>
						<td><span class="label label-success">品牌</span></td>
						<td>
							<input type="text" id = "addBrand" class="form-control" name="addBrand"></input>
						</td>
						<td><span id="spanbrand" style = "display:block; width:300px;">请输入新商品的品牌 例:诺基亚</span></td>
					</tr>
					<tr>	
						<td><span class="label label-success">型号</span></td>
						<td>
							<input type="text" id = "addName" class="form-control" name="addName"></input>
						</td>
						<td><span id="spanname" style = "display:block; width:300px;">请输入新商品的型号，例:LUMIA 830</span></td>
					</tr>
					<tr>	
						<td><span class="label label-success">颜色</span></td>
						<td>
							<input type="text" id = "addColor" class="form-control" name = "addColor"></input>
						</td>
						<td><span id="spancolor" style = "display:block; width:300px;">请输入新商品的颜色，例:red</span></td>
					</tr>
					<tr>	
						<td><span class="label label-success">数量</span></td>
						<td>
							<input type="text" id = "addAmount" class="form-control" name = "addAmount"></input>
						</td>
						<td><span id="spanamount" style = "display:block; width:300px;">请输入新商品的数量</span></td>
					</tr>
					<tr>	
						<td><span class="label label-success">图片</span></td>
						<td>
							<input type="file" id = "addPic" class="form-control" name = "addPic"></input>
						</td>
						<td><span id="spanPic" style = "display:block; width:300px;">请为新商品添加图片</span></td>
					</tr>
					
				</table>
				<center>
					<input type = "submit" id ="addpro" class="btn btn-warning" value ="添加新商品"  disabled ></input>
				</center>
				</form>
		  </div>
		</div>
		<!-- Tab切换 结束-->	 
		
		<!-- 修改数量弹出框-->
				<div class="modal fade" id="updateAmount" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <form >
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal">
						        <span aria-hidden="true">&times;</span>
						        <span class="sr-only">Close</span>
					        </button>
					        <h4 class="modal-title" id="myModalLabel">修改产品数量</h4>
					      </div>
					      <div class="modal-body">
					        <label>请输入产品新数量:</label>
					        <input id="productAmount" type="text" name="productAmount"><span id = "spanProductAmount"></span>
					        <input id="hiddenProductID" class="form-control" type = "hidden" name="hiddenProductID">
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					        <button type="button" class="btn btn-default btn-warning" id="delete" >删除</button>
					        <input type="submit" class="btn btn-primary" id="update" disabled>
					      </div>
				      </form>
				    </div>
				  </div>
				</div> 	
		<!--修改数量弹出框结束  -->
		<!--查看图片弹出框  -->
		<div class="modal fade" id="viewProduct" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div><img id="viewImg" src="" width="100%" height="100%"></div> 
				    </div>
				  </div>
		</div> 	
		<!--查看图片弹出框结束  -->
	  	
	  </div>
	</div>
</div>



<jsp:include page="/inc/footer.jsp"></jsp:include>

<script language="javascript" type="text/javascript">
	var b = 0;
	var n = 0;
	var c = 0;
	var a = 0;
	var check = 0;
		/* 判断录入是否可用 */
  		$("#addBrand").blur(function(){
  			var addBrand = $("#addBrand").val();
  			if(addBrand ==""){
  				$("#spanbrand").html("<font color='Red'>请填写品牌</font>");
  				b = 0;
  				return;
  			}
  			else if(addBrand != ""){
  				$("#spanbrand").html("<font color='Green'>√</font>");
  				b = 1;
  			}
  			if(b+n+c+a == 4){
  				$("#addpro").attr("disabled",false);
  				return;
  			}
  			
	  	})
	  	$("#addName").blur(function(){
	  		var addName = $("#addName").val();
  			if(addName ==""){
  				$("#spanname").html("<font color='Red'>请填写型号</font>");
  				n = 0;
  				return;
  			}
  			else if(addName != ""){
  				$("#spanname").html("<font color='Green'>√</font>");
  				n = 1;
  			}
  			if(b+n+c+a == 4){
  				$("#addpro").attr("disabled",false);
  				return;
  			}
	  	})
	  	$("#addColor").blur(function(){
  			var addColor = $("#addColor").val();
  			if(addColor ==""){
  				$("#spancolor").html("<font color='Red'>请填写颜色</font>");
  				c = 0;
  				return;
  			}
  			else if(addColor != ""){
  				$("#spancolor").html("<font color='Green'>√</font>");
  				c = 1;
  			}
  			if(b+n+c+a == 4){
  				$("#addpro").attr("disabled",false);
  				return;
  			}
	  	})
	  	$("#addAmount").blur(function(){
  			var addAmount = $("#addAmount").val();
  			var regAmount = /^[0-9]*$/; //验证数字
  			if(addAmount ==""){
  				$("#spanamount").html("<font color='Red'>请填写数量</font>");
  				a = 0;
  				return;
  			}
  			else if(!regAmount.test(addAmount)){
  				$("#spanamount").html("<font color='Red'>请输入数字</font>");
  				a = 0;
  				return;
  			}
  			else {
  				$("#spanamount").html("<font color='Green'>√</font>");
  				a = 1;
  			}
  			if(b+n+c+a == 4){
  				$("#addpro").attr("disabled",false);
  				return;
  			}
	  	})
  		/* 文本框获得焦点，按钮失效 */
  		$("#addBrand").focus(function(){
  			$("#addpro").attr("disabled",true);
  			b = 0;
  		})
  		$("#addName").focus(function(){
  			$("#addpro").attr("disabled",true);
  			n = 0;
  		})
  		$("#addColor").focus(function(){
  			$("#addpro").attr("disabled",true);
  			c = 0;
  		})
  		$("#addAmount").focus(function(){
  			$("#addpro").attr("disabled",true);
  			a = 0;
  		})
	  	
	</script>
	
	
	
	<script language="javascript" type="text/javascript">
		var arrupdate = $(".btnupdate");
		for(var i = 0; i<arrupdate.length;i++){
			arrupdate[i].onclick = function(){
				getProductID(this.value);
			}
		}
		
		var arrview = $(".btnview");
		for(var i = 0; i<arrview.length; i++){
			arrview[i].onclick = function(){
			var productID = this.value;
			$.ajax({
				type:"post",
				async:false,
				data:"action=findImg&id="+productID,
				url:"servlet/ProductInfoServlet",
				success:function(message){
					if(message=="null"){
						$("#viewImg").attr("src","<%=basePath%>images/productImages/noresult.jpg");
					}
					else{
						$("#viewImg").attr("src","<%=basePath%>images/productImages/"+message);
					}
				}
				});
			}
		}
	</script>
	
	
	<script language="javascript" type="text/javascript">
		$("#productAmount").blur(function(){
			var a = $("#productAmount").val();
			var aAmount = /^[0-9]*$/;
			if(a == ""){
				$("#spanProductAmount").html("<font color='Red'>请输入一个数量</font>");
			}
			else if(!aAmount.test(a)){
				$("#spanProductAmount").html("<font color='Red'>请输入正确的数量</font>");
			}
			else{
				$("#spanProductAmount").html("<font color='Green'>√</font>");
				$("#update").attr("disabled",false);
			}
		});
		
		$("#productAmount").focus(function(){
			$("#update").attr("disabled",true);
		});
	
		$("#update").click(function(){
			var amount = $("#productAmount").val();
			var productID = $("#hiddenProductID").val();

			$.ajax({
			type:"post",
			async:false,
			data:"action=updateAmount&id="+productID+"&amount="+amount,
			url:"servlet/ProductInfoServlet",
			success:function(message){
				if(message=="true"){
					alert("修改数量成功！");
				}
				else{
					alert("修改数量失败！");
				}
			}
			});
		});
		
		$("#delete").click(function(){
			var productID = $("#hiddenProductID").val();

			$.ajax({
			type:"post",
			async:false,
			data:"action=deleteProduct&id="+productID,
			url:"servlet/ProductInfoServlet",
			success:function(message){
				if(message=="true"){
					alert("删除产品成功！");
					window.location.href="<%=basePath%>product/product.jsp";
				}
				else{
					alert("删除产品失败！");
				}
			}
			});
		});
	</script>

	
	
	