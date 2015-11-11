package net.qingsoft.crm.vo;

public class ProductInfo {
private int id;//主键
private String brand;//品牌
private String name;//型号
private String color;//颜色
private int enbable;//是否删除
private int amount;//商品数量
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getBrand() {
	return brand;
}
public void setBrand(String brand) {
	this.brand = brand;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getColor() {
	return color;
}
public void setColor(String color) {
	this.color = color;
}
public int getAmount() {
	return amount;
}
public int getEnbable() {
	return enbable;
}
public void setEnbable(int enbable) {
	this.enbable = enbable;
}
public void setAmount(int amount) {
	this.amount = amount;
}
}
