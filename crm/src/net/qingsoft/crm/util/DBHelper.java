package net.qingsoft.crm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBHelper {
	/**
	 * 单一实例数据库助手
	 */
	private DBHelper() {}
	private static DBHelper dbhelper;
	public static DBHelper getDBHelper()
	{
		if(dbhelper==null)
		{
			dbhelper = new DBHelper();
		}
		return dbhelper;
	}
	/**
	 *关闭数据库 
	 */
	public void close(ResultSet rs,PreparedStatement ps,Connection conn)
	{
		try 
		{
			if(rs!=null)
			{
				rs.close();
			}
			if(ps!=null)
			{
				ps.close();
			}
			if(conn!=null)
			{
				conn.close();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			rs = null;
			ps = null;
			conn = null;
		}
	}
	/**
	 *连接数据库 
	 */
	public Connection getConn()
	{
		Connection conn = null;
		try 
		{	ResourceBundle db=ResourceBundle.getBundle("db");//导入配置文件
			Class.forName(db.getString("driver"));
			String url = db.getString("url");
			String user = db.getString("uid");
			String password = db.getString("upw");
			conn = DriverManager.getConnection(url, user, password);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}

