package net.qingsoft.crm.vo;

public class SaleInfo {
private int id;//主键
private String odernum;//订单号
private CustomerInfo customerInfo;//customerID外键表
private int enable;//是否删除
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getOdernum() {
	return odernum;
}
public void setOdernum(String odernum) {
	this.odernum = odernum;
}
public CustomerInfo getCustomerInfo() {
	return customerInfo;
}
public void setCustomerInfo(CustomerInfo customerInfo) {
	this.customerInfo = customerInfo;
}
public int getEnable() {
	return enable;
}
public void setEnable(int enable) {
	this.enable = enable;
}
}
