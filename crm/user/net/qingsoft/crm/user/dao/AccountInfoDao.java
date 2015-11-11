package net.qingsoft.crm.user.dao;

import java.util.ArrayList;

import net.qingsoft.crm.vo.AccountInfo;

public interface AccountInfoDao {
	/**
	 * 判断用户是否存在
	 * @param accountInfo 账户信息
	 */
	public AccountInfo checkLogin(AccountInfo accountInfo);
	
	/**
	 * 更新用户密码
	 * @param id 用户id
	 * @param oldPass 用户旧密码
	 * @param newPass 用户新密码
	 */
	public AccountInfo updatePassword (int id, String newpass);
	
	/**
	 * 更新用户信息
	 * @param id 用户id
	 * @param name 用户姓名
	 * @param tel 用户电话
	 */
	public AccountInfo updateInfo(int id, String name, String tel);
	
	/**
	 * 查看用户信息
	 * @param accountInfo 账户信息
	 */
	public AccountInfo viewInfo(AccountInfo accountInfo);
	
	/**
	 * 查看所有用户信息
	 * @param pgNo 页码总数
	 * @param pgSize
	 * @return
	 */
	public ArrayList viewAllInfo(int pgNo, int pgSize);
	
	/**
	 * 查看用户数量
	 * @return
	 */
	public int countAccount();
	
	/**
	 * 判断是否用户名是否存在
	 * @param username 用户名
	 * @return 返回布尔值
	 */
	public boolean isExist(String username);
	
	/**
	 * 添加新用户
	 * @param username 用户用户名
	 * @param name 用户姓名
	 * @param tel 用户电话
	 * @param lvl 用户权限
	 */
	public void addUser(String username, String name, String tel, String lvl);
	
	/**
	 * 删除用户 其实是改变用户的enable的值
	 * @param id 用户id
	 */
	public void delUser(int id);
	
	/**
	 * 修改用户权限
	 * @param id 用户id
	 */
	public void changeLvl(int id);
	/**
	 * 查看本用户订单
	 * @param pgNo 第几页
	 * @param pgSize	一页的数量
	 * @return
	 */
	public ArrayList orderview(int pgNo, int pgSize,int id,String customername);
	
	/**
	 * 数具体某个业务员有多少个订单
	 * @param id 业务员id
	 * @return
	 */
	public int counts(int id);
	
	/**
	 * 删除某条订单
	 * @param id 订单id
	 */
	public void delOrder(int id);
		
	
}
