package net.qingsoft.crm.vo;

public class RequestInfo {
private int id;//主键
private String conndate;//联系时间
private String cont;//内容
private String method;//沟通方式
private AccountInfo accountInfo;//accountID外键表
private CustomerInfo customerInfo;//customerID外键表
private int enable;//是否删除
private int num;//虚拟编号，不存在
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getConndate() {
	return conndate;
}
public void setConndate(String conndate) {
	this.conndate = conndate;
}
public String getCont() {
	return cont;
}
public void setCont(String cont) {
	this.cont = cont;
}
public String getMethod() {
	return method;
}
public void setMethod(String method) {
	this.method = method;
}
public AccountInfo getAccountInfo() {
	return accountInfo;
}
public void setAccountInfo(AccountInfo accountInfo) {
	this.accountInfo = accountInfo;
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
