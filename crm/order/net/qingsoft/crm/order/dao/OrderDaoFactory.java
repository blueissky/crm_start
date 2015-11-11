package net.qingsoft.crm.order.dao;

import net.qingsoft.crm.order.dao.imp.OrderDaoImpl;

public class OrderDaoFactory {
	private static OrderDaoInter orderdaoint;
	public static OrderDaoInter getOrderdaointer()
	{
		if(orderdaoint==null)
			orderdaoint=(OrderDaoInter) new OrderDaoImpl();
			return orderdaoint;
	}
     
}
