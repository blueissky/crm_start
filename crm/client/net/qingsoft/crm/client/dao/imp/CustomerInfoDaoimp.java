package net.qingsoft.crm.client.dao.imp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import net.qingsoft.crm.client.dao.CustomerInfoDao;
import net.qingsoft.crm.util.DBHelper;
import net.qingsoft.crm.util.SystemTime;
import net.qingsoft.crm.vo.AccountInfo;
import net.qingsoft.crm.vo.CompanyInfo;
import net.qingsoft.crm.vo.CustomerInfo;
import net.qingsoft.crm.vo.RequestInfo;
import net.qingsoft.crm.vo.WayInfo;

public class CustomerInfoDaoimp implements CustomerInfoDao{
	/**
	 *  返回客户简介资料数组
	 * @param pageNo 开始页数
	 * @param pageSize 分页大小
	 * @param account 获取当前用户的ID
	 * @param customerInfo 获取搜索用户的姓名和公司名
	 */
	public  ArrayList<CustomerInfo> getCustomerMessage(int pageNo,int pageSize,AccountInfo account,CustomerInfo customerInfo) {
		DBHelper db=DBHelper.getDBHelper();
		CustomerInfo cui=null;//客户资料表
		CompanyInfo coi=null;//客户公司表
		WayInfo wi=null;//信息来源表
		ArrayList<CustomerInfo> list=new ArrayList<CustomerInfo>();//LIST表存放客户信息 
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		String searchname=customerInfo.getName();
		String searchcompany=customerInfo.getCompanyInfo().getCompany();
		int accountID=account.getId();
		int level=account.getLvl();
		String levelStr="and customer.accountID="+accountID;//只取当前用户的客户资料
		if(level==0){
			levelStr="";//如果是管理员取出全部资料
		}
		/**
		 * 页的计算start开始end结束
		 */
		int start=(pageNo-1)*pageSize+1;
		int end=pageNo*pageSize;
		StringBuffer sb=new StringBuffer();//数据库查询语句
		sb.append("with info as(select ROW_NUMBER() over(order by customer.firstTime desc)as num,")
		.append("customer.id,customer.name,customer.sex,customer.age,customer.tel,")
		.append("customer.qq,customer.email,company.company,company.range,company.address,way.method from customer")
		.append(" left join company on customer.companyID=company.id")
		.append(" left join way on customer.wayID=way.id")
		.append(" where (customer.enable=1 and company.enable=1 ")
		.append(levelStr)
		.append("  and company.company like '%")
		.append(searchcompany)
		.append("%' and customer.name like '%").append(searchname).append("%')")
		.append(")select * from info where num between "+start+" and "+end);
		String sql=sb.toString();
		/**
		 * 连接数据库查询数据
		 */
		try {
			con=db.getConn();
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();//开始查询
			while(rs.next()){
				cui=new CustomerInfo();///创建用户表
				coi=new CompanyInfo();//创建公司表
				wi=new WayInfo();//消息来源表
				cui.setWayInfo(wi);//将消息来源表放入用户表
				cui.setCompanyInfo(coi);//将公司表放入用户表
				cui.setName(rs.getString("name"));
				cui.setSex(rs.getString("sex"));
				/**
				 * 在if中将年份转换为年龄
				 */
				if(rs.getString("age")!=null){//将年份转换为年龄
					String temp=rs.getString("age");
					temp=temp.substring(0,4);
					int sqlyear=Integer.parseInt(temp);//将数据库的年份转换为整数
					Calendar calendar = Calendar.getInstance();
					int year=calendar.get(Calendar.YEAR);//取出系统当前年份
					year=year-sqlyear;
					cui.setAge(year+"");//得到实际年龄 
				}else{//如果年龄是空的数据
					cui.setAge("未录入");
				}
				cui.setNum(Integer.parseInt(rs.getString("num")));//从数据库取得虚拟字段
				cui.setId(Integer.parseInt(rs.getString("id")));
				cui.setTel(rs.getString("tel"));
				cui.setQq(rs.getString("qq"));
				cui.setEmail(rs.getString("email"));
				coi.setCompany(rs.getString("company"));
				coi.setRange(rs.getString("range"));
				coi.setAddress(rs.getString("address"));
				wi.setMethod(rs.getString("method"));
				list.add(cui);//将客户表添加到list容器里
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(rs, ps, con);//关闭连接释放资源
		return list;
	}
/**
 * 取出符合搜索条件的客户数量
 */
	public int getSearchCount(CustomerInfo customerInfo) {
		int num=0;
		DBHelper db=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String customerName=customerInfo.getName();//获得搜索的用户名
		String customerCompany=customerInfo.getCompanyInfo().getCompany();//获得搜索的公司名
		if(customerName==null){
			customerName="";
		}
		if(customerCompany==null){
			customerCompany="";
		}
		StringBuffer sb=new StringBuffer();
		sb.append("select COUNT(customer.enable) as num from customer")
		.append(" left join company on customer.companyID=company.id ")
		.append(" left join way on customer.wayID=way.id") 
		.append(" where customer.name like '%")
		.append(customerName)
		.append("%' and company.company like '%")
		.append(customerCompany)
		.append("%'");
		String sql=sb.toString();
		try {
			conn=db.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			rs.next();
			num=Integer.parseInt(rs.getString("num"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(rs, ps, conn);//关闭数据库释放资源
		return num;
	}
/**
 * 取出所有客户总数
 */
	public int getCount(AccountInfo account) {
		int count=0;
		int accid=account.getId();//获得当前用户的ID
		String accountID="and customer.accountID="+accid;
		if(account.getLvl()==0){//如果是管理员查看所有
			accountID="";
		}
		DBHelper db=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		sb.append("select count(customer.id)as num from customer")
		.append(" left join company on customer.companyID=company.id") 
		.append(" left join way on customer.wayID=way.id")
		.append(" where customer.enable=1 and company.enable=1 ")
		.append(accountID);
		String sql=sb.toString();
		try {
			conn=db.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			rs.next();
			String num=rs.getString("num");
			count=Integer.parseInt(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(rs, ps, conn);
		return count;
	}
	/**
	 * 存储客户信息
	 */
	public void saveMessage(CustomerInfo customer,RequestInfo request) {
		DBHelper db=DBHelper.getDBHelper();
		Connection conn=null;
		CallableStatement call=null;
		String sql="{call newcustomer(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			conn=db.getConn();//连接数据库
			call=conn.prepareCall(sql);//调用存储过程
			call.setString(1, ""+customer.getAccountInfo().getId());//accountID
			call.setString(2,customer.getWayInfo().getMethod());//wayINFO
			call.setString(3,request.getConndate());//requestInfo
			call.setString(4, request.getCont());//requestInfo
			call.setString(5,request.getMethod());//requestInfo
			call.setString(6,customer.getCompanyInfo().getCompany());//companyINFO
			call.setString(7,customer.getCompanyInfo().getRange());//companyINFO
			call.setString(8,customer.getCompanyInfo().getAddress());//companyINFO
			call.setString(9,customer.getName());//customerInfo
			call.setString(10,customer.getSex());//customerInfo
			call.setString(11,customer.getAge());//customerInfo
			call.setString(12,customer.getTel());//customerInfo
			call.setString(13,customer.getFirstTime());//customerInfo
			call.setString(14,customer.getEmail());//customerInfo
			call.setString(15,customer.getQq());//customerInfo
			call.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(null, call, conn);//关闭 
	}
/**
 * 删除客户信息
 */
	public int delCustomerInfo(CustomerInfo customerInfo) {
		int num=0;
		DBHelper db=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="update customer set enable=0 where customer.id=?";
		try {
			conn=db.getConn();
			ps=conn.prepareStatement(sql);
			ps.setString(1,customerInfo.getId()+"");//获取需要删除用户的ID 
			num=ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(null, ps, conn);
		return num;
	}
/**
 * 获得用户信息
 * 进入该页面时已经制定了管理员ID所有不需要在此再次判断
 */
public CustomerInfo getCustomerCompany(CustomerInfo customerInfo) {
	CustomerInfo cuinfo=null;//取数据库的信息
	CompanyInfo coinfo=null;//公司表
	DBHelper db=DBHelper.getDBHelper();
	Connection conn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	StringBuffer sb=new StringBuffer();
	sb.append("select * from customer")
	.append(" left join company on customer.companyID=company.id")
	.append(" where ")
	.append(" customer.enable=1 and customer.id=?");
	String sql=sb.toString();
	try {
		conn=db.getConn();
		ps=conn.prepareStatement(sql);
		ps.setString(1, ""+customerInfo.getId());
		rs=ps.executeQuery();
		while(rs.next()){
			cuinfo=new CustomerInfo();
			coinfo=new CompanyInfo();
			cuinfo.setName(rs.getString("name"));
			if(rs.getString("age")!=null){
				String temp=rs.getString("age");
				temp=temp.substring(0, 4);
				SystemTime st=new SystemTime();
				int age=st.getYear();
				age=age-Integer.parseInt(temp);
				cuinfo.setAge(""+age);
			}else{
				cuinfo.setAge("");
			}
			cuinfo.setSex(rs.getString("sex"));
			cuinfo.setTel(rs.getString("tel"));
			coinfo.setAddress(rs.getString("address"));
			coinfo.setCompany(rs.getString("company"));
			coinfo.setRange(rs.getString("range"));
			cuinfo.setCompanyInfo(coinfo);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	db.close(rs, ps, conn);
	return cuinfo;
}
}









