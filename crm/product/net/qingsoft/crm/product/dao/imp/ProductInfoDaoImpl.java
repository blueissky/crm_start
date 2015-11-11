package net.qingsoft.crm.product.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import net.qingsoft.crm.product.dao.ProductInfoDao;
import net.qingsoft.crm.util.DBHelper;
import net.qingsoft.crm.vo.CategoryInfo;

public class ProductInfoDaoImpl implements ProductInfoDao{

	@Override
	public ArrayList viewAllInfo(int pgNo, int pgSize) {
		// TODO Auto-generated method stub
		ArrayList<CategoryInfo> list = new ArrayList<CategoryInfo>();
		
		DBHelper dbhelper = DBHelper.getDBHelper();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		StringBuffer sb = new StringBuffer();
		
		int start=(pgNo-1)*pgSize+1;
		int end=pgNo*pgSize;
		sb.append("with categorys as(select ROW_NUMBER() over(order by category.id)as number,")
		.append("category.id,category.brand,category.name,category.color,category.amount from category ")
		.append("where category.enable = 1)")
		.append(" select * from categorys where number between " + start +"and " + end);
		String sql=sb.toString();
		
		try {
			conn = dbhelper.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next()){
				CategoryInfo categoryInfo = new CategoryInfo();
				categoryInfo.setId((rs.getInt("id")));
				categoryInfo.setBrand(rs.getString("brand"));
				categoryInfo.setName(rs.getString("name"));
				categoryInfo.setColor(rs.getString("color"));
				categoryInfo.setAmount(rs.getInt("amount"));
				list.add(categoryInfo);
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
		sb.append("select count(category.id)as number from category")
		.append(" where category.enable=1");
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
	public void addProduct(String brand, String name, String color, int amount, String path) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			DBHelper dbhelper = DBHelper.getDBHelper();
			conn = dbhelper.getConn();
			
			String sql = "insert into category (brand,name,color,amount,picture) values (?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, brand);
			ps.setString(2, name);
			ps.setString(3, color);
			ps.setInt(4, amount);
			ps.setString(5, path);
			
//删除数据用语句
//			String sql = "delete from category where id between 20 and 34";
//			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			DBHelper.getDBHelper().close(rs, ps, conn);
		}
	}

	@Override
	public boolean isExist(String brand, String name, String color) {
		// TODO Auto-generated method stub
		DBHelper dbhelper = DBHelper.getDBHelper();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try 
		{
			conn = dbhelper.getConn();
			
			ps = conn.prepareStatement("select * from category where brand=? and name=? and color=?");
			ps.setString(1, brand);
			ps.setString(2, name);
			ps.setString(3, color);
			
			
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
	public int updateAmount(int id, int amount) {
		// TODO Auto-generated method stub
		DBHelper dbHelper=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
	    int message=-1;
		try {
			conn=dbHelper.getConn();
			ps=conn.prepareStatement("update category set amount=? where id=? ");
			ps.setInt(1, amount);
			ps.setInt(2, id);
			message= ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 finally{
	    	  dbHelper.close(null, ps, conn);
	      }
		return message;
	}

	@Override
	public String findImg(int id) {
		DBHelper dbHelper=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		
	    String path = null;
		try {
			conn=dbHelper.getConn();
			ps=conn.prepareStatement("select * from category where id = ?");
			ps.setInt(1, id);
			rs= ps.executeQuery();
			rs.next();
			path = rs.getString("picture");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 finally{
	    	  dbHelper.close(null, ps, conn);
	      }
		return path;
	}

	@Override
	public int deleteProduct(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int message=-1;
		try {
			DBHelper dbhelper = DBHelper.getDBHelper();
			conn = dbhelper.getConn();
			
			String sql = "delete from category where id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			message = ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			DBHelper.getDBHelper().close(rs, ps, conn);
		}
		return message;
	}

}
