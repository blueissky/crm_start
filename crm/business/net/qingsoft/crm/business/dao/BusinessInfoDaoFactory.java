package net.qingsoft.crm.business.dao;

import net.qingsoft.crm.business.dao.imp.BusinessInfoDaoImp;

public class BusinessInfoDaoFactory {

	private BusinessInfoDaoFactory() {
	}
	private static BusinessInfoDaoImp bidi;
	public static BusinessInfoDaoImp getBusinessInfoDaoImp(){
		if(bidi==null){
			bidi=new BusinessInfoDaoImp();
			return bidi;
		}
		return bidi;
	}
}
