package net.qingsoft.crm.vo;

public class CompanyInfo {
private int id;//主键	
private String company;//公司名称
private String range;//经营范围
private String address;//地址
private int enable;//是否删除
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCompany() {
	return company;
}
public void setCompany(String company) {
	this.company = company;
}
public String getRange() {
	return range;
}
public void setRange(String range) {
	this.range = range;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public int getEnable() {
	return enable;
}
public void setEnable(int enable) {
	this.enable = enable;
}
}
