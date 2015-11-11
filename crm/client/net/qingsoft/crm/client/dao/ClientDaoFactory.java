package net.qingsoft.crm.client.dao;

import net.qingsoft.crm.client.dao.imp.CustomerInfoDaoimp;

public class ClientDaoFactory {
private static CustomerInfoDaoimp cdi;
public static CustomerInfoDaoimp createClientDao(){
	if(cdi==null){
		cdi=new CustomerInfoDaoimp();
	}
	return cdi;
}
}
