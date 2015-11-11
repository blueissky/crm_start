package net.qingsoft.crm.vo;

public class WayInfo {
private int id;//主键
private String method;//来源方式
private int enable;//是否删除
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getMethod() {
	return method;
}
public void setMethod(String method) {
	this.method = method;
}
public int getEnable() {
	return enable;
}
public void setEnable(int enable) {
	this.enable = enable;
}

}
