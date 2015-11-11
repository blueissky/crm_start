package net.qingsoft.crm.user.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import net.qingsoft.crm.user.dao.AccountInfoDao;
import net.qingsoft.crm.util.DBHelper;
import net.qingsoft.crm.vo.AccountInfo;
import net.qingsoft.crm.vo.CategoryInfo;
import net.qingsoft.crm.vo.CustomerInfo;
import net.qingsoft.crm.vo.SaleInfo;
import net.qingsoft.crm.vo.SalegoodsInfo;

public class AccountInfoDaoImpl implements AccountInfoDao{

	@Override
	public AccountInfo checkLogin(AccountInfo accountInfo) {
		// TODO Auto-generated method stub
		AccountInfo temp = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			DBHelper dbhelper = DBHelper.getDBHelper();
			conn = dbhelper.getConn();
			
			String sql = "select * from account where username = ? and password = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, accountInfo.getUsername());
			ps.setString(2, accountInfo.getPassword());
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				temp = new AccountInfo();
				temp.setId(rs.getInt("id"));
				temp.setUsername(rs.getString("username"));
				temp.setPassword(rs.getString("password"));
				temp.setName(rs.getString("name"));
				temp.setTel(rs.getString("tel"));
				temp.setLvl(rs.getInt("lvl"));
				temp.setEnable(rs.getInt("enable"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			DBHelper.getDBHelper().close(rs, ps, conn);
		}
		return temp;
	}

	

	@Override
	public AccountInfo updateInfo(int id, String name, String tel) {
		// TODO Auto-generated method stub
			AccountInfo temp = null;
			Connection conn = null;
			PreparedStatement ps = null;
			PreparedStatement ps1 = null;
			ResultSet rs = null;
			
		
		
		try {
			DBHelper dbhelper = DBHelper.getDBHelper();
			conn = dbhelper.getConn();
			
			String sql = "update account SET name = ?, tel = ? where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, tel);
			ps.setInt(3, id);
			
			ps.executeUpdate();
			
			String sql1 = "select * from account where id = ?";
			ps1 = conn.prepareStatement(sql1);
			ps1.setInt(1, id);
			
			rs = ps1.executeQuery();
			
			if(rs.next()){
				temp = new AccountInfo();
				temp.setId(rs.getInt("id"));
				temp.setUsername(rs.getString("username"));
				temp.setPassword(rs.getString("password"));
				temp.setName(rs.getString("name"));
				temp.setTel(rs.getString("tel"));
				temp.setLvl(rs.getInt("lvl"));
				temp.setEnable(rs.getInt("enable"));
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			DBHelper.getDBHelper().close(rs, ps, conn);
		}
		return temp;
	}

	@Override
	public AccountInfo viewInfo(AccountInfo accountInfo) {
		return accountInfo;
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList viewAllInfo(int pgNo, int pgSize) {
		// TODO Auto-generated method stub
		
		ArrayList<AccountInfo> list = new ArrayList<AccountInfo>();
		
		DBHelper dbhelper = DBHelper.getDBHelper();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		StringBuffer sb = new StringBuffer();
		
		int start=(pgNo-1)*pgSize+1;
		int end=pgNo*pgSize;
		sb.append("with accounts as(select ROW_NUMBER() over(order by account.id)as number,")
		.append("account.id,account.name,account.tel,account.username,account.lvl from account ")
		.append("where account.enable = 1)")
		.append(" select * from accounts where number between " + start +"and " + end);
		String sql=sb.toString();
		
		try {
			conn = dbhelper.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next()){
				AccountInfo accountInfo = new AccountInfo();
				accountInfo.setId((rs.getInt("id")));
				accountInfo.setName(rs.getString("name"));
				accountInfo.setTel(rs.getString("tel"));
				accountInfo.setUsername(rs.getString("username"));
				accountInfo.setLvl(rs.getInt("lvl"));
				list.add(accountInfo);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBHelper.getDBHelper().close(rs, ps, conn);
		}
		return list;
		
	}

	@Override
	public int countAccount() {
		// TODO Auto-generated method stub
		
		DBHelper dbhelper = DBHelper.getDBHelper();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int count = 0;
		
		StringBuffer sb = new StringBuffer();
		sb.append("select count(account.id)as number from account")
		.append(" where account.enable=1");
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
			DBHelper.getDBHelper().close(rs, ps, conn);
		}
		return count;
	}



	@Override
	public AccountInfo updatePassword(int id, String newpass) {
		// TODO Auto-generated method stub
			AccountInfo temp = null;
			Connection conn = null;
			PreparedStatement ps = null;
			PreparedStatement ps1 = null;
			ResultSet rs = null;
			
			
			try {
				DBHelper dbhelper = DBHelper.getDBHelper();
				conn = dbhelper.getConn();
				
				String sql = "update account SET password = ? where id = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, newpass);
				ps.setInt(2, id);
				
				ps.executeUpdate();
				
				String sql1 = "select * from account where id = ?";
				ps1 = conn.prepareStatement(sql1);
				ps1.setInt(1, id);
				
				rs = ps1.executeQuery();
				
				if(rs.next()){
					temp = new AccountInfo();
					temp.setId(rs.getInt("id"));
					temp.setUsername(rs.getString("username"));
					temp.setPassword(rs.getString("password"));
					temp.setName(rs.getString("name"));
					temp.setTel(rs.getString("tel"));
					temp.setLvl(rs.getInt("lvl"));
					temp.setEnable(rs.getInt("enable"));
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally{
				DBHelper.getDBHelper().close(rs, ps, conn);
			}
			return temp;
	}



	@Override
	public boolean isExist(String username) {
		
		DBHelper dbhelper = DBHelper.getDBHelper();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try 
		{
			conn = dbhelper.getConn();
			
			ps = conn.prepareStatement("select * from account where username=?");
			ps.setString(1, username);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				return false;
			}
			else
			{
				return true;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			// TODO: handle exception
		}
		finally
		{
			dbhelper.close(rs, ps, conn);
		}
		
		
		return false;
	}



	@Override
	public void addUser(String username, String name, String tel, String lvl) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			DBHelper dbhelper = DBHelper.getDBHelper();
			conn = dbhelper.getConn();
			
			String sql = "insert into account (username,password,name,tel,lvl) values (?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, "1234");
			ps.setString(3, name);
			ps.setString(4, tel);
			ps.setString(5, lvl);
			
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			DBHelper.getDBHelper().close(rs, ps, conn);
		}
	}



	@Override
	public void delUser(int id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			DBHelper dbhelper = DBHelper.getDBHelper();
			conn = dbhelper.getConn();
			
			String sql = "update account SET enable = 0 where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			DBHelper.getDBHelper().close(rs, ps, conn);
		}
	}



	@Override
	public void changeLvl(int id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int lvl = 0;
		
		try {
			DBHelper dbhelper = DBHelper.getDBHelper();
			conn = dbhelper.getConn();
			
			String sql = "select lvl from account where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				lvl = rs.getInt("lvl");
			}
			
			if(lvl == 0){
				lvl = 1;
			}
			else{
				lvl = 0;
			}
			
			sql = "update account SET lvl = ? where id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, lvl);
			ps.setInt(2, id);
			
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			DBHelper.getDBHelper().close(rs, ps, conn);
		}
	}



	@Override
	public ArrayList orderview(int pgNo, int pgSize,int id,String customername) {
		// TODO Auto-generated method stub
		ArrayList<SalegoodsInfo> list = new ArrayList<SalegoodsInfo>();
		
		DBHelper dbhelper = DBHelper.getDBHelper();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		 if(customername==null)
		{
			customername="%";
		}
		StringBuffer sb = new StringBuffer();
		
		int start=(pgNo-1)*pgSize+1;
		int end=pgNo*pgSize;
		sb.append("with salegds as")
		.append("(select ROW_NUMBER() over(order by salegoods.id)as nb,")
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
		.append(" AND sale.customerID=customer.id")
		.append(" AND salegoods.accountID=account.id")
		.append(" AND salegoods.accountID=")
		.append(id)
		.append(" AND salegoods.categoryID=category.id")
		.append(" AND salegoods.enable=1")
		.append(" AND customer.name like '%")
		.append(customername)
		.append("%')")
		.append("select * from salegds where nb between "+start+" and "+end);
		String sql=sb.toString();
		System.out.println(sql);
		
		try {
			conn = dbhelper.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next()){
				//设置订单编号
				SaleInfo saleInfo=new SaleInfo();
				saleInfo.setOdernum(rs.getInt("ordernum")+"");
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
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBHelper.getDBHelper().close(rs, ps, conn);
		}
		return list;
	}



	@Override
	public int counts(int id) {
		int count = 0;
		DBHelper dbhelper = DBHelper.getDBHelper();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//获取当前用户的ID
		
		StringBuffer sb = new StringBuffer();
		//按照用户的权限进行查询所显示的订单
		sb.append("select count(salegoods.id)as number from salegoods,account ")
		.append(" where salegoods.enable=1")
		.append(" and salegoods.accountID=account.id ")
		.append(" AND salegoods.accountID=")
		.append(id);
		//如果用户的权限为业务员则把条件插入sql语句中
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



	@Override
	public void delOrder(int id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			DBHelper dbhelper = DBHelper.getDBHelper();
			conn = dbhelper.getConn();
			
			String sql = "update salegoods SET enable = 0 where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			DBHelper.getDBHelper().close(rs, ps, conn);
		}
	}
}
