package net.qingsoft.crm.product.dao;

import net.qingsoft.crm.product.dao.imp.ProductInfoDaoImpl;

public class ProductDaoFactory {
	private static ProductInfoDao productInfoDao;
	
	public static ProductInfoDao createProductInfoDao(){
		if(productInfoDao==null){
			productInfoDao = new ProductInfoDaoImpl();
		}
		return productInfoDao;
	}
}