package net.qingsoft.crm.business.dao;

import java.util.ArrayList;

import net.qingsoft.crm.vo.CustomerInfo;
import net.qingsoft.crm.vo.RequestInfo;

public interface BusinessInfoDao {
	/**
	 * 搜索所有业务信息
	 * @param customerInfo
	 * @param requestInfo
	 */
	public ArrayList<RequestInfo> viewRequest(RequestInfo requestInfo);
	/**
	 * 增加新的业务信息
	 * @param requestInfo
	 */
	public int newRequest(RequestInfo requestInfo);
	/**
	 * 显示所有业务信息或者符合查找条件的业务信息
	 * @param requestInfo
	 * @return
	 */
	public ArrayList<RequestInfo> findAllRequest(RequestInfo requestInfo,int start,int pageSize);
	/**
	 * 显示分页数量
	 * @param requestInfo
	 * @return
	 */
	public int findCount(RequestInfo requestInfo);
	/**
	 * 删除业务信息
	 * @param requestInfo
	 * @return
	 */
	public int delRequest(RequestInfo requestInfo);
	/**
	 * 获得指定用户的业务信息
	 * @param requestInfo 业务信息表
	 * @return
	 */
	public ArrayList<RequestInfo> getRequestInfo(CustomerInfo customerInfo);
}
