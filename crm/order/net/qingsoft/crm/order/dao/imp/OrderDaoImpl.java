package net.qingsoft.crm.order.dao.imp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.sql.Types;
import net.qingsoft.crm.order.dao.OrderDaoInter;
import net.qingsoft.crm.util.DBHelper;
import net.qingsoft.crm.vo.*;

 /**
  *此类实现orderdaointer接口 并实现其方法
  * @author Administrator
  *
  */
public class OrderDaoImpl implements OrderDaoInter {
	/**
	 * 添加订单
	 */
	@Override
	public int addorder(SalegoodsInfo salegoodsInfo,String ordernum) {
	
		// TODO Auto-generated method stub
		//获取客户id
	     int customerid=salegoodsInfo.getSaleInfo().getCustomerInfo().getId();
	     //获得categoryid
	     int categoryId=salegoodsInfo.getCategoryInfo().getId();
	     //获得数量
	     int amount=salegoodsInfo.getAmount();
	     //获得商品单价
	     double price=salegoodsInfo.getPrice();

	     //获得订单成交时间
	     String dateString=salegoodsInfo.getSaledate();
	     //获得操作员id
	     int accountid=salegoodsInfo.getAccountInfo().getId(); 
	     //获得用户的id
	     
	    
	    
	     DBHelper dbHelper=DBHelper.getDBHelper();
	      Connection conn=null;
	      CallableStatement cs=null;
	      int isaddsuccess=-1;
	      ArrayList<SalegoodsInfo> list=new ArrayList<SalegoodsInfo>();
	      String sql="{call addorder(?,?,?,?,?,?,?,?,?)}";
		 try {
			 conn=dbHelper.getConn();
			 cs=conn.prepareCall(sql);
			 cs.setInt(1, customerid);
			 cs.setInt(2, categoryId);
			 cs.setInt(3, amount);
			 cs.setDouble(4, price);
			 cs.setString(5, dateString);
			 cs.setInt(6, accountid);
			 cs.setInt(7, 1);
			 cs.registerOutParameter(8,Types.INTEGER);
			 cs.setString(9, ordernum);
			 cs.execute();//执行
			//得到注册过的返回参数
			 isaddsuccess = cs.getInt(8);
				System.out.println(isaddsuccess);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			dbHelper.close(null,cs, conn);
			
		}
		return isaddsuccess;
		
	}
	/**
	 * 删除订单
	 */
	@Override
	public int deleorder(int id) {
		// TODO Auto-generated method stub
		DBHelper dbHelper=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
	    int message=-1;
		try {
			conn=dbHelper.getConn();
			ps=conn.prepareStatement("update salegoods set enable=0 where id=? ");
			ps.setInt(1, id);
			message= ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 finally{
	    	  dbHelper.close(null, ps, conn);
	      }
		return message;
		
	}
	/**
	 * 业务元界面查看的订单
	 */
	@Override
	public ArrayList<SalegoodsInfo> selectorder(int pgNo,int pgSize,int lvl,String customername,String accountname) {
		// TODO Auto-generated method stub
      DBHelper dbHelper=DBHelper.getDBHelper();
      Connection conn=null;
      PreparedStatement ps=null;
      ResultSet rs=null;
      ArrayList<SalegoodsInfo> list=new ArrayList<SalegoodsInfo>();
      //判断输入的查询内容是否为空 为空转换为%
      if(customername==null)
		{
			customername="%";
		}
		if(accountname==null)
		{
		   accountname="%";
		}
		String accountlvl;
		if(lvl==1)
		{
			accountlvl=" AND account.lvl="+lvl;
		}
		else {
			accountlvl="";
		}
        
      try {
		conn=dbHelper.getConn();
		
		StringBuffer stringBuffer=new StringBuffer();
		int start=(pgNo-1)*pgSize+1;
		int end=pgNo*pgSize;		
		stringBuffer.append("with salegds as")
				.append("(select ROW_NUMBER() over(order by salegoods.id,saledate)as nb,")
				.append("salegoods.id,")
		        .append("sale.ordernum,")
				.append("customer.name,")
				.append("category.brand,category.name number,")
				.append("category.color,salegoods.amount,")
				.append( "salegoods.price,")
				.append( "salegoods.saledate,")
				.append("account.name as accountname")
				.append( " from salegoods,category,customer,sale,account ")
				.append("where salegoods.saleID=sale.id")
				.append(accountlvl)
				.append(" AND sale.customerID=customer.id")
				.append(" AND salegoods.accountID=account.id")
				.append(" AND salegoods.categoryID=category.id")
				.append(" AND salegoods.enable=1")
				.append(" AND customer.name like '%")
				.append(customername)
				.append("%'")
				.append(" AND account.name like '%")
				.append(accountname)
				.append("%')")
				.append("select * from salegds where nb between "+start+" and "+end);
	
		String s=stringBuffer.toString();
		ps=conn.prepareStatement(s);
		rs=ps.executeQuery();
		while (rs.next()) {
			//设置订单编号
			SaleInfo saleInfo=new SaleInfo();
			saleInfo.setOdernum(rs.getString("ordernum")+"");
			//设置用户名
			CustomerInfo customerInfo=new CustomerInfo();
			customerInfo.setName(rs.getString("name"));
			//将订单资料表里对应的customerinfo放入sale里
			
			saleInfo.setCustomerInfo(customerInfo);
			//设置商品品牌
			CategoryInfo categoryinfo=new CategoryInfo();
			categoryinfo.setBrand(rs.getString("brand"));
			//设置商品型号
			categoryinfo.setName(rs.getString("number"));
			//设置商品的颜色
			categoryinfo.setColor(rs.getString("color"));
            //设置商品数量
	        SalegoodsInfo salegoodsinfo=new SalegoodsInfo();
	        salegoodsinfo.setAmount(rs.getInt("amount"));
	        //设置订单编号
	        salegoodsinfo.setId(rs.getInt("id"));
	        //设置商品单价
	        salegoodsinfo.setPrice(rs.getDouble("price"));
	        //设置录入订单日期
	        salegoodsinfo.setSaledate(rs.getString("saledate"));
	        //设置操作的用户名
	        AccountInfo accountInfo=new AccountInfo();
	        accountInfo.setName(rs.getString("accountname"));
	        //设置排列的序号
	        salegoodsinfo.setSerialnb(rs.getInt("nb"));
	        //把各表对象插入订单表
	        salegoodsinfo.setSaleInfo(saleInfo);
	        salegoodsinfo.setCategoryInfo(categoryinfo);
	        salegoodsinfo.setAccountInfo(accountInfo);
	        //将订单对象加入list里
	        list.add(salegoodsinfo);
		}
	} 
      catch (Exception e) {
		// TODO: handle exception
    	  e.printStackTrace();
	}
      //关闭数据库连接
      finally{
    	  dbHelper.close(rs, ps, conn);
      }
      return list;
      
	}
	

	@Override
	/**
	 * 按照用户的权限获取订单的总量
	 */
	public int counts(int lvl) {
		// TODO Auto-generated method stub
		int count = 0;
		DBHelper dbhelper = DBHelper.getDBHelper();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		//获取当前用户的ID
		String accountlvl=" and account.lvl="+lvl;
		if(lvl==0){
			accountlvl="";
		}
		StringBuffer sb = new StringBuffer();
		//按照用户的权限进行查询所显示的订单
		sb.append("select count(salegoods.id)as number from salegoods,account")
		.append(" where salegoods.enable=1")
		.append(" and salegoods.accountID=account.id ")
		//如果用户的权限为业务员则把条件插入sql语句中
		.append(accountlvl);
		String sql=sb.toString();

		
		try {
			conn=dbhelper.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			rs.next();
			String number=rs.getString("number");
			count=Integer.parseInt(number);
			 
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			dbhelper.close(rs, ps, conn);
		}
		return count;
	}
	/**
	 * 
	 */
	public int selectcounts(int lvl,String customername,String accountname) {
		// TODO Auto-generated method stub
		int count = 0;
		DBHelper dbhelper = DBHelper.getDBHelper();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//当有查询条件时的操作
		String custmersql,accountnamesql;
		if(customername!=null&&!"".equals(customername))
		{
		  custmersql=" and customer.name like '%"+customername+"%' ";
		}
		else
		{
			custmersql="";
		}
		if(accountname!=null&&!"".equals(accountname))
		{
			accountnamesql=" and account.name like '%"+customername+"%' ";
		}
		else {
			accountnamesql="";
		}
		String accountlvl=" and account.lvl="+lvl;
		if(lvl==0){
			accountlvl="";
		}
		
		StringBuffer sb = new StringBuffer();
		//按照用户的权限进行查询所显示的订单
		sb.append("select count(salegoods.id)as number from salegoods,account,customer,sale")
		.append(" where salegoods.enable=1")
		.append(" and salegoods.accountID=account.id ")
		.append(" and salegoods.saleID=sale.id")
		.append(" AND sale.customerID=customer.id ")
		.append(accountlvl)
		//如果用户的权限为业务员则把条件插入sql语句中
		.append(custmersql)
		.append(accountnamesql);
		String sql=sb.toString();
		
		
		try {
			conn=dbhelper.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			rs.next();
			String number=rs.getString("number");
			count=Integer.parseInt(number);
			 
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			dbhelper.close(rs, ps, conn);
		}
		return count;
	}
	
	
	/**
	 * 获取option项里的内容
	 * 返回customerinfo型的arraylist
	 * 传递一个用户id参数
	 */
	public ArrayList<CustomerInfo> getoptioncontent(int x)
	{
		DBHelper dbHelper=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ArrayList<CustomerInfo> list=new ArrayList<CustomerInfo>();
		
	   
		try {
			conn=dbHelper.getConn();
			ps=conn.prepareStatement("SELECT  DISTINCT customer.name from customer,account where customer.accountID=? AND customer.enable=1");
	    	ps.setInt(1, x);
			rs=ps.executeQuery();
			while (rs.next()) {	
				
		    CustomerInfo customerInfo=new CustomerInfo();
			customerInfo.setName(rs.getString("name"));
			list.add(customerInfo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();		}
		finally{
			dbHelper.close(rs, ps, conn);
		}
		return list;
	
	}
	/**
	 * 获取商品类型
	 */
	public ArrayList<CategoryInfo> getType()     
	{
		DBHelper dbHelper=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ArrayList<CategoryInfo> list=new ArrayList<CategoryInfo>();
		try {
			conn=dbHelper.getConn();
			ps=conn.prepareStatement("select * from category where enable=1");
			rs=ps.executeQuery();
			while (rs.next()) {	
				
		   CategoryInfo categoryInfo=new CategoryInfo();
		    categoryInfo.setId(rs.getInt("id"));
			categoryInfo.setBrand(rs.getString("brand"));
			categoryInfo.setName(rs.getString("name"));
			categoryInfo.setColor(rs.getString("color"));
			list.add(categoryInfo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();		}
		finally{
			dbHelper.close(rs, ps, conn);
		}
		return list;
	
	}
	/**
	 * 获得指定用户的订单信息----------------------------------------段彬彬
	 */
	public ArrayList<SalegoodsInfo> getSaleGoods(CustomerInfo customerInfo) {
		ArrayList<SalegoodsInfo> list=new ArrayList<SalegoodsInfo>(); 
		SalegoodsInfo salesInfo=null;//用于返回订单信息
		SaleInfo saleInfo=null;//订单主表
		CategoryInfo cateInfo=null;//商品表
		DBHelper db=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		sb.append("select sale.ordernum,salegoods.amount as amount,salegoods.price,")
		.append(" salegoods.saledate,category.brand,category.color,category.name from salegoods")
		.append(" left join sale on salegoods.saleID=sale.id")
		.append(" left join category on salegoods.categoryID=category.id")
		.append(" where  sale.customerID=? and")
		.append(" salegoods.accountID=? and salegoods.enable=1");
		String sql=sb.toString();
		try {
			conn=db.getConn();
			ps=conn.prepareStatement(sql);
			ps.setString(1, ""+customerInfo.getId());
			ps.setString(2, ""+customerInfo.getAccountInfo().getId());
			rs=ps.executeQuery();
			while(rs.next()){
				salesInfo=new SalegoodsInfo();
				saleInfo=new SaleInfo();
				cateInfo=new CategoryInfo();
				salesInfo.setAmount(Integer.parseInt(rs.getString("amount")));
			  salesInfo.setPrice(rs.getInt("price"));
				salesInfo.setSaledate(rs.getString("saledate"));
				saleInfo.setOdernum(rs.getString("ordernum"));
				cateInfo.setBrand(rs.getString("brand"));
				cateInfo.setName(rs.getString("name"));
				cateInfo.setColor(rs.getString("color"));
				salesInfo.setSaleInfo(saleInfo);//外键表
				salesInfo.setCategoryInfo(cateInfo);//外键表
				list.add(salesInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(rs, ps, conn);
		return list;
	}
	/*
	 * 根据customerID查找相应的saleinfo信息   dxx
	 * 返回值为 saleinfo
	 */
	public SaleInfo getSale(CustomerInfo customerInfo) {
		DBHelper db=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		SaleInfo saleInfo=new SaleInfo();
		//获取客户的id
		int id=customerInfo.getId();
	    
		try {
			conn=db.getConn();
			ps=conn.prepareStatement("select * from sale where customerID=? and enable=1");
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.next()){
				saleInfo.setOdernum(rs.getString("ordernum"));
				saleInfo.setId(rs.getInt("id"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			db.close(rs, ps, conn);
		}
	
		return saleInfo;
     }
}
