package net.qingsoft.crm.client.dao;

import java.util.ArrayList;

import net.qingsoft.crm.vo.AccountInfo;
import net.qingsoft.crm.vo.CustomerInfo;
import net.qingsoft.crm.vo.RequestInfo;

public interface CustomerInfoDao {
	/**
	 * 从数据库取出客户基本信息放到ArrayList容器
	 */
	public ArrayList<CustomerInfo> getCustomerMessage(int pageNo,int pageSize,AccountInfo account,CustomerInfo customerInfo);
	/**
	 * 取出客户总数
	 */
	public int getCount(AccountInfo account);
	/**
	 * 保存新录入的客户信息
	 */
	public void saveMessage(CustomerInfo customer,RequestInfo request);
	/**
	 * 搜索客户信息
	 */
	public int getSearchCount(CustomerInfo customerInfo);
	//public ArrayList<CustomerInfo> searchCustomerInfo(CustomerInfo customerInfo);
	/**
	 * 删除客户信息
	 * @param customerInfo 
	 * @return
	 */
	public int delCustomerInfo(CustomerInfo customerInfo);
	/**
	 * 获得用户信息和公司信息
	 * @return
	 */
	public CustomerInfo getCustomerCompany(CustomerInfo customerInfo);
}
