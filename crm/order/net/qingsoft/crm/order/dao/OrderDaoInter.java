package net.qingsoft.crm.order.dao;

import java.util.ArrayList;

import net.qingsoft.crm.vo.CategoryInfo;
import net.qingsoft.crm.vo.CustomerInfo;
import net.qingsoft.crm.vo.SaleInfo;
import net.qingsoft.crm.vo.SalegoodsInfo;

public interface OrderDaoInter {
	public int addorder(SalegoodsInfo salegoodsInfo,String ordernum);
	public int deleorder(int id);
	public ArrayList<SalegoodsInfo> selectorder(int pgNo,int pgSize,int lvl,String customername,String accountname);
	public int counts(int lvl);
	public int selectcounts(int lvl,String customername,String accountname);
	public ArrayList<CustomerInfo> getoptioncontent(int x);
	//获得商品类型
	public ArrayList<CategoryInfo> getType();
	//获得订单信息
	public SaleInfo getSale(CustomerInfo customerInfo);
	/**
	 * 查询指定客户订单信息
	 */
	public ArrayList<SalegoodsInfo> getSaleGoods(CustomerInfo customerInfo);
}