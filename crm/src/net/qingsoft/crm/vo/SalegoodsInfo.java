package net.qingsoft.crm.vo;

public class SalegoodsInfo {
private int id;//主键
private SaleInfo saleInfo;//saleID外键表
private CategoryInfo categoryInfo;//categoryID外键表
private int amount;//数量
private double price;//价格
private String saledate;//交易时间
private AccountInfo accountInfo;//accountID外键表
private int enable;//是否删除
private int  serialnb;
public int getSerialnb() {
	return serialnb;
}
public void setSerialnb(int serialnb) {
	this.serialnb = serialnb;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public SaleInfo getSaleInfo() {
	return saleInfo;
}
public void setSaleInfo(SaleInfo saleInfo) {
	this.saleInfo = saleInfo;
}
public CategoryInfo getCategoryInfo() {
	return categoryInfo;
}
public void setCategoryInfo(CategoryInfo categoryInfo) {
	this.categoryInfo = categoryInfo;
}
public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
public double getPrice() {
	return price;
}
public void setPrice(double d) {
	this.price = d;
}
public String getSaledate() {
	return saledate;
}
public void setSaledate(String saledate) {
	this.saledate = saledate;
}
public AccountInfo getAccountInfo() {
	return accountInfo;
}
public void setAccountInfo(AccountInfo accountInfo) {
	this.accountInfo = accountInfo;
}
public int getEnable() {
	return enable;
}
public void setEnable(int enable) {
	this.enable = enable;
}
}
