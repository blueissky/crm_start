package net.qingsoft.crm.business.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import net.qingsoft.crm.business.dao.BusinessInfoDao;
import net.qingsoft.crm.util.DBHelper;
import net.qingsoft.crm.util.SystemTime;
import net.qingsoft.crm.vo.CompanyInfo;
import net.qingsoft.crm.vo.CustomerInfo;
import net.qingsoft.crm.vo.RequestInfo;

public class BusinessInfoDaoImp implements BusinessInfoDao {
	/**
	 * 新增业务信息
	 */
	public int newRequest(RequestInfo requestInfo) {
		int num=0;
		DBHelper db=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		SystemTime st=new SystemTime();//获得当前系统时间
		String sql="insert into request values(?,?,?,?,?,1)";
		try {System.out.println(requestInfo.getMethod().length());
			conn=db.getConn();
			ps=conn.prepareStatement(sql);
			ps.setString(1,st.getSystemTime());
			ps.setString(2,requestInfo.getCont());
			ps.setString(3,requestInfo.getMethod());
			ps.setString(4,requestInfo.getCustomerInfo().getId()+"");
			ps.setString(5,requestInfo.getAccountInfo().getId()+"");
			num=ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(null, ps, conn);
		return num;
	}
	/**
	 * 分页查询业务信息
	 * @param requestInfo
	 * @return
	 */
	public ArrayList<RequestInfo> findAllRequest(RequestInfo requestInfo,int start,int pageSize) {
		ArrayList<RequestInfo> list=new ArrayList<RequestInfo>();//存放业务表容器
		RequestInfo ri=null;//业务需求表
		CustomerInfo ci=null;//客户表
		DBHelper db=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int begin=(start-1)*pageSize+1;
		int end=(start)*pageSize;
		String accountId=requestInfo.getAccountInfo().getId()+"";//获得当前用户ID
		String customerName=requestInfo.getCustomerInfo().getName();//获得模糊查询客户的姓名
		String method=requestInfo.getMethod();//获得模糊查询地点 
		String lvl=" and request.accountID="+accountId;
		if(requestInfo.getAccountInfo().getLvl()==0){
			lvl="";//如果是管理员查看显示全部信息
		}
		StringBuffer sb=new StringBuffer();
		sb.append("with info as(select ROW_NUMBER() over(order by request.id desc)as num,")
		.append(" customer.name,customer.tel,request.id,request.cont,request.method,request.conndate from request")
		.append(" left join customer on request.customerID=customer.id")
		.append(" where request.enable=1 and request.enable=1")
		.append(lvl)
		.append(" and customer.name like '%").append(customerName).append("%'")//模糊姓名
		.append(" and request.method like '%").append(method).append("%'")//模糊地点
		.append(" and request.conndate like '%").append("").append("%')")//模糊查询日期，待定
		.append(" select * from info where num between ").append(begin+"")
		.append(" and ").append(end+"");
		String sql=sb.toString();
		try {
			conn=db.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				ci=new CustomerInfo();
				ri=new RequestInfo();
				ri.setCustomerInfo(ci);
				ci.setName(rs.getString("name"));
				ci.setTel(rs.getString("tel"));
				ri.setCont(rs.getString("cont"));
				ri.setMethod(rs.getString("method"));
				ri.setConndate(rs.getString("conndate"));
				ri.setNum(Integer.parseInt(rs.getString("num")));
				ri.setId(Integer.parseInt(rs.getString("id")));
				list.add(ri);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(rs, ps, conn);
		return list;
	}
	@Override
	public ArrayList<RequestInfo> viewRequest(RequestInfo requestInfo) {
		return null;
	}
	/**
	 * 返回分页数量
	 */
	public int findCount(RequestInfo requestInfo) {
		DBHelper db=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String num="";
		try {
		StringBuffer sb=new StringBuffer();
		sb.append("select COUNT(request.id)as num from request")
		.append(" left join customer on request.customerID=customer.id")
		.append(" where customer.enable=1 and request.enable=1 and request.accountID=").append(requestInfo.getAccountInfo().getId());
		String sql=sb.toString();
			conn=db.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			rs.next();
			num=rs.getString("num");
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(rs, ps, conn);
		try {
			return Integer.parseInt(num);//如果报异常返回0不处理
		} catch (Exception e) {
			return 0;
		}
	}
/**
 * 删除业务信息
 */
	public int delRequest(RequestInfo requestInfo) {
		int num=0;
		DBHelper db=DBHelper.getDBHelper();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="update request set enable=0 where id=?";
		try {
			conn=db.getConn();
			ps=conn.prepareStatement(sql);
			ps.setString(1, ""+requestInfo.getId());
			num=ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(rs, ps, conn);
		return num;
	}
	/**
	 * 获得指定用户的业务信息
	 * 进入该页面时已经制定了管理员ID所有不需要在此再次判断
	 */
public ArrayList<RequestInfo> getRequestInfo(CustomerInfo customerInfo) {
	ArrayList<RequestInfo> list=new ArrayList<RequestInfo>();
	RequestInfo info=null;
	DBHelper db=DBHelper.getDBHelper();
	Connection conn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	StringBuffer sb=new StringBuffer();
	sb.append("select * from request")
	.append(" where request.customerID=?")
	.append(" and request.enable=1");
	String sql=sb.toString();
	try {
		conn=db.getConn();
		ps=conn.prepareStatement(sql);
		ps.setString(1, ""+customerInfo.getId());
		rs=ps.executeQuery();
		while(rs.next()){
			info=new RequestInfo();
			info.setMethod(rs.getString("method"));
			info.setCont(rs.getString("cont"));
			info.setConndate(rs.getString("conndate"));
			list.add(info);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	db.close(rs, ps, conn);
	return list;
}
}










