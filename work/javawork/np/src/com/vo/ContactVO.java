package com.vo;

public class ContactVO {
	String X;
	String Y;
	
	public ContactVO() {
	}
	
	public ContactVO(String X) {
		this.X = X;
	
	}
	public ContactVO(String X, String Y) {
		super();
		this.X = X;
		this.Y = Y;
	}
	
	public String toString() {
		return "ContactVO [X=" + X +"Y="+Y+ "]";
	}
	
	public String getX() {
		return X;
	}
	public void setX(String x) {
		X = x;
	}
	public String getY() {
		return Y;
	}
	public void setY(String y) {
		Y = y;
	}
	
	
	
	
}
