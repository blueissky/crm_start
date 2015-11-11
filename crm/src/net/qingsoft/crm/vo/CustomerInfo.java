package net.qingsoft.crm.vo;

public class CustomerInfo {
private int id;//主键	
private String name;//姓名
private String sex;//性别
private String age;//年龄
private String tel;//电话
private String email;//电子邮件
private String qq;//QQ号码
private int num;//分页显示时候的编号(虚拟字段)
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getQq() {
	return qq;
}
public void setQq(String qq) {
	this.qq = qq;
}
private AccountInfo accountInfo;//accountID外键表
private String firstTime;//首次联系时间
private WayInfo wayInfo;//sourceID外键表
public WayInfo getWayInfo() {
	return wayInfo;
}
public void setWayInfo(WayInfo wayInfo) {
	this.wayInfo = wayInfo;
}
private CompanyInfo companyInfo;//companyID外检表
private int enable;//是否删除
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getAge() {
	return age;
}
public void setAge(String age) {
	this.age = age;
}
public String getTel() {
	return tel;
}
public void setTel(String tel) {
	this.tel = tel;
}
public AccountInfo getAccountInfo() {
	return accountInfo;
}
public void setAccountInfo(AccountInfo accountInfo) {
	this.accountInfo = accountInfo;
}
public String getFirstTime() {
	return firstTime;
}
public void setFirstTime(String firstTime) {
	this.firstTime = firstTime;
}
public CompanyInfo getCompanyInfo() {
	return companyInfo;
}
public void setCompanyInfo(CompanyInfo companyInfo) {
	this.companyInfo = companyInfo;
}
public int getEnable() {
	return enable;
}
public void setEnable(int enable) {
	this.enable = enable;
}
}
