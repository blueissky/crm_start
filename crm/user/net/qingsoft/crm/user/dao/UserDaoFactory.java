package net.qingsoft.crm.user.dao;

import net.qingsoft.crm.user.dao.imp.AccountInfoDaoImpl;

public class UserDaoFactory {
	private static AccountInfoDao accountInfoDao;
	
	public static AccountInfoDao createAccountInfoDao(){
		if(accountInfoDao==null){
			accountInfoDao = new AccountInfoDaoImpl();
		}
		return accountInfoDao;
	}
}
