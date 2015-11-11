package net.qingsoft.crm.product.dao;

import java.util.ArrayList;

public interface ProductInfoDao {
	/**
	 * 查看所有商品信息
	 * @param pgNo 页码总数
	 * @param pgSize 
	 * @return
	 */
	public ArrayList viewAllInfo(int pgNo, int pgSize);
	
	/**
	 * 查看有几种商品种类
	 * @return
	 */
	public int countAccount();
	
	/**
	 * 新增产品DAO
	 * @param brand 品牌
	 * @param name	型号
	 * @param color	颜色
	 * @param amount 数量
	 * @param path 文件名
	 */
	public void addProduct(String brand, String name, String color, int amount, String path);
	
	/**
	 * 判断产品是否已经存在
	 * @param brand 品牌
	 * @param name	型号
	 * @param color	颜色
	 */
	public boolean isExist(String brand, String name, String color);
	
	/**
	 * 更新某种商品的数量
	 * @param id 商品的id
	 * @param amount 该商品的数量
	 * @return 判断是否已经修改
	 */
	public int updateAmount(int id, int amount);
	
	/**
	 * 找到图片文件名
	 * @param id 商品id
	 * @return 返回文件名
	 */
	public String findImg(int id);
	
	/**
	 * 删除产品
	 * @param id 产品id
	 * @return 判断是否已经删除
	 */
	public int deleteProduct(int id);
	
	
}
